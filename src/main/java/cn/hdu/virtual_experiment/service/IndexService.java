package cn.hdu.virtual_experiment.service;

import cn.hdu.virtual_experiment.util.PageResult;
import cn.hdu.virtual_experiment.vo.OrderList;

import java.util.List;

/**
 * Created by 26074 on 2019/12/28.
 */
public interface IndexService {
    PageResult list(int pageNum);
}
