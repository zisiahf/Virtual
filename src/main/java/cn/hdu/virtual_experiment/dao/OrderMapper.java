package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.vo.MyOrder;
import cn.hdu.virtual_experiment.vo.Order_Date_Vo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select oid,orderdate,exTime,ex_name,people_number,createTime from order_info oi where oi.tid = #{tid} order by orderdate desc,exTime asc")
    List<MyOrder> selectMyOrderListByTid(@Param("tid") String tid);

    @Select("select timeIndex,people_number,count from order_info oi,ex_info ei where oi.eid = ei.eid and orderdate = #{date}")
    List<Order_Date_Vo> selectOrderByDate(@Param("date") String date);

    @Insert("insert into order_info (oid,tid,eid,orderdate,exTime,createTime,people_number,ex_name,timeIndex) values (#{oid},#{tid},#{eid},#{orderdate},#{exTime},#{createTime},#{people_number},#{ex_name},#{timeIndex})")
    void insertOrder(Order_Info order_info);

    @Select("select * from order_info where orderdate = #{orderdate} and exTime = #{exTime}")
    Order_Info selectByOrderDateAndexTime(@Param("orderdate") Date orderdate, @Param("exTime")String exTime);

    @Delete("delete from order_info where oid = #{oid}")
    void deleteOrderByOid(@Param("oid") String oid);

    @Select("select * from order_info where oid = #{oid}")
    Order_Info selectOrderByOid(@Param("oid") String oid);
}
