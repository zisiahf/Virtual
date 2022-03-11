package cn.hdu.virtual_experiment.controller.teacher;

import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.domain.Teacher_Info;
import cn.hdu.virtual_experiment.exception.GlobalException;
import cn.hdu.virtual_experiment.result.CodeMsg;
import cn.hdu.virtual_experiment.result.Result;
import cn.hdu.virtual_experiment.service.OrderService;
import cn.hdu.virtual_experiment.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/startOrder")
    public String startOrder(String token,HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        return "pages/teacher/myOrder";
    }

    //分页获取教师的所有预约
    @RequestMapping("/getMyOrderList")
    @ResponseBody
    public Result<PageResult> getMyOrderList(int pageNum, String token, HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

        String tid = teacher_info.getTid();

        PageResult pageResult = orderService.getMyOrderList(pageNum,tid);

        return Result.success(pageResult);
    }

    //跳转预约界面
    @RequestMapping("/toAddOrder")
    public String toAddOrder(String token,HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }
        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);
        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        return "pages/teacher/addOrder";
    }

    //查看当天预约情况
    @RequestMapping("/getOrderByDate")
    @ResponseBody
    public Result getOrderByDate(String date, String token, HttpServletRequest request){

        // res：表示12个时间段中，每个时间段还有的剩余的节点数量
        // 难度允许同一个时间段两个老师进行预约时间吗
        int[] res = orderService.getOrderByDate(date);

        return Result.success(res);
    }

    //添加预约
    @RequestMapping("/addOrder")
    @ResponseBody
    public Result addOrder(Order_Info order_info, String token, HttpServletRequest request){
        orderService.addOrder(order_info,token,request);

        return Result.success("添加成功");
    }

    //删除预约
    @RequestMapping("/deleteOrder")
    @ResponseBody
    public Result deleteOrder(String  oid){
        orderService.deleteOrder(oid);
        return Result.success("删除成功");
    }

    //跳转管理预约界面
    @RequestMapping("/toEditOrder")
    public String toEditOrder(String oid,String token,HttpServletRequest request){
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(CodeMsg.DATA_ERROR);
        }

        Teacher_Info teacher_info = (Teacher_Info) request.getSession().getAttribute(token);

        if(teacher_info == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

        return "pages/teacher/editOrder";
    }

    //修改页面渲染数据
    @RequestMapping("/getOrderByOid")
    @ResponseBody
    public Result getOrderByOid(String oid){
        Order_Info order_info = orderService.getOrderByOid(oid);
        return Result.success(order_info);
    }
}
