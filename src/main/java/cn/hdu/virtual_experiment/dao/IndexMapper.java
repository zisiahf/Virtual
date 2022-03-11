package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.vo.OrderList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndexMapper{

	// exTime是String类型 可以排序吗 感觉这段代码有问题
	@Select("select oid,orderdate,exTime,ex_name,teacherName,workplace from order_info oi,teacher_info ti where oi.tid=ti.tid order by orderdate desc,exTime asc")
	List<OrderList> selectOrderList();
}
