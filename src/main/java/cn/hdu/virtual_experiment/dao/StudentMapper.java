package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Student_Info;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {


	@Select("select * from student_info where tid = #{tid} and oid = #{oid}")
	List<Student_Info> selectUserByTidAndOid(@Param("tid") String tid,@Param("oid") String oid);


	@Insert("insert into student_info (sid,username,password,name,tid,school,major,oid) values (#{sid},#{username},#{password},#{name},#{tid},#{school},#{major},#{oid})")
    void insertStudent(Student_Info student_info);

	@Delete("delete from student_info where sid = #{sid} and oid = #{oid} and tid = #{tid}")
	void deleteStudent(@Param("sid") String sid, @Param("oid") String oid, @Param("tid") String tid);

	@Select("select * from student_info where username = #{username} and oid = #{oid}")
	Student_Info selectStudentByName(@Param("username") String username, @Param("oid") String oid);

	@Delete("delete from student_info where oid = #{oid}")
	void deleteStudentByOid(@Param("oid") String oid);
}
