package cn.hdu.virtual_experiment.dao;

import cn.hdu.virtual_experiment.domain.Node;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 26074 on 2020/1/6.
 */
@Mapper
public interface NodeMapper {
    @Insert("insert into node_info (node_id,node_name) values (#{node_id},#{node_name})")
    void insertNode(Node node);

    @Delete("delete from node_info")
    void deleteNode();

    @Update("update node_info set num =  #{num} where node_name = #{port}")
    void updataNode(@Param("port") String port, @Param("num") String num);

    @Select("select * from node_info")
    List<Node> selectNode();
}
