package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.context.Const;
import cn.hdu.virtual_experiment.dao.OrderMapper;
import cn.hdu.virtual_experiment.dao.StudentMapper;
import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.service.OrderService;
import cn.hdu.virtual_experiment.service.StudentService;
import cn.hdu.virtual_experiment.util.PageResult;
import cn.hdu.virtual_experiment.util.PageUtils;
import cn.hdu.virtual_experiment.util.UUIDUtil;
import cn.hdu.virtual_experiment.vo.MyOrder;
import cn.hdu.virtual_experiment.vo.Order_Date_Vo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static cn.hdu.virtual_experiment.context.Const.PAGESIZE;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    StudentService studentService;

    @Autowired
    OrderMapper orderMapper;

    public PageResult getMyOrderList(int pageNum, String tid) {
        // 1、分页参数设置，调用mapper查询数据
        PageHelper.startPage(pageNum, PAGESIZE);
        List<MyOrder> myOrders = orderMapper.selectMyOrderListByTid(tid);

        // 2、将查询的数据封装到PageInfo当中
        // 数据的封装过程： order_info ---> List<MyOrder> ---> pageInfo ---> PageUtils(pageResult)
        PageInfo pageInfo = new PageInfo<MyOrder>(myOrders);

        return PageUtils.getPageResult(pageInfo);
    }

    //
    public int[] getOrderByDate(String date) {
        // 开辟的数组代表什么
        int [] res = new int[12];
        List<Order_Date_Vo> orderList = orderMapper.selectOrderByDate(date);

        // 双重for循环计算出每个已经被预约的时间段中每个时间段有多少人共占用了多少个节点
        for(int i = 0; i < res.length; i++){
            for(Order_Date_Vo list:orderList){
                if(list.getTimeIndex() == i){
                    res[i] += list.getCount() * list.getPeople_number();
                }
            }
        }

        // 计算出每个时间段空闲的节点数量
        for(int i = 0; i < res.length;i++){
            res[i] = Const.TOTAL_NODE_COUNT - res[i];
        }

        return res;
    }

    //添加预约
    @Transactional
    public void addOrder(Order_Info order_info, String token, HttpServletRequest request) {
            //校验数据
            checkForm(order_info);

            Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);

            // 设置order_info的参数
            order_info.setTid(teacher_info.getTid());
            order_info.setOid(UUIDUtil.uuid());
            order_info.setCreateTime(new Date());

            orderMapper.insertOrder(order_info);
    }

    //删除预约
    @Transactional
    public void deleteOrder(String oid) {
        //删除所有学生
        orderMapper.deleteOrderByOid(oid);
        studentService.deleteStudentByOid(oid);

    }

    //通过oid查询
    public Order_Info getOrderByOid(String oid) {
        Order_Info order_info = orderMapper.selectOrderByOid(oid);
        return order_info;
    }


    private void checkForm(Order_Info order_info) {
        //1.判断日期是否小于今天
        Date today = new Date();
        Date orderdate = order_info.getOrderdate();
        if(orderdate.before(today)){
            throw new GlobalException(CodeMsg.DATE_ERROR);
        }
        //2.判断当前时间是否被预约
        Order_Info o = orderMapper.selectByOrderDateAndexTime(orderdate,order_info.getExTime());
        if(o != null){
            throw new GlobalException(CodeMsg.ORDER_ERROR);
        }
    }
}
