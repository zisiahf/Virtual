package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Teacher_Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherLoginMapper {
    @Select("select * from teacher_info where username = #{username} and password =  #{password}")
    Teacher_Info selectTeacherByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Insert("insert into teacher_info (tid,username,password,teacherName,workplace) values (#{tid},#{username},#{password},#{teacherName},#{workplace})")
    void insert(Teacher_Info teacher_info);

    @Select("select * from teacher_info where username = #{username}")
    Teacher_Info selectTeacherByUsername(@Param("username") String username);

}
