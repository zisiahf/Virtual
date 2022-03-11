package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Ex_Info;
import cn.hdu.virtual_experiment.domain.Order_Info;
import cn.hdu.virtual_experiment.domain.Student_Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 26074 on 2020/1/2.
 */
@Mapper
public interface LoginMapper {

    @Select("select * from student_info where username = #{username} and password =  #{password}")
    Student_Info selectByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from order_info where oid = #{oid}")
    Order_Info selectOrderByOid(@Param("oid") String oid);

    @Select("select * from ex_info where eid = #{eid}")
    Ex_Info selectExByEid(@Param("eid") String eid);
}
