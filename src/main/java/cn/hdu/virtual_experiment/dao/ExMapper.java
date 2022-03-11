package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Ex_Info;
import cn.hdu.virtual_experiment.vo.MyOrder;
import cn.hdu.virtual_experiment.vo.Order_Date_Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExMapper {
    @Select("select * from ex_info")
    List<Ex_Info> selectEx();
}
