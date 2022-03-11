package cn.hdu.virtual_experiment.service.impl;

import cn.hdu.virtual_experiment.context.Const;
import cn.hdu.virtual_experiment.dao.IndexMapper;
import cn.hdu.virtual_experiment.service.IndexService;
import cn.hdu.virtual_experiment.util.PageResult;
import cn.hdu.virtual_experiment.util.PageUtils;
import cn.hdu.virtual_experiment.vo.OrderList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 26074 on 2019/12/28.
 */
@Service
public class IndexServiceImpl implements IndexService{

    @Autowired
    IndexMapper indexMapper;

    //预约列表
    public PageResult list(int pageNum) {
        PageHelper.startPage(pageNum, Const.PAGESIZE);
        List<OrderList> orderList = indexMapper.selectOrderList();
        PageInfo pageInfo = new PageInfo<OrderList>(orderList);
        return PageUtils.getPageResult(pageInfo);
    }
}
