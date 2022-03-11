package cn.hdu.virtual_experiment.service;

import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.util.PageResult;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    PageResult getMyOrderList(int pageNum, String tid);

    int[] getOrderByDate(String date);

    void addOrder(Order_Info order_info, String token, HttpServletRequest request);

    void deleteOrder(String oid);

    Order_Info getOrderByOid(String oid);
}
