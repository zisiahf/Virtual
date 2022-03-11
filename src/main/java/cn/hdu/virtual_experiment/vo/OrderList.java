package cn.hdu.virtual_experiment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 26074 on 2019/12/28.
 */
public class OrderList implements Serializable{

    private String oid;//预约id

    private String ex_name;//预约实验名称

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date orderdate;//实验日期

    private String exTime;

    private String workplace;//教师工作单位

    private String teacherName;//教师姓名

    public String getEx_name() {
        return ex_name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getExTime() {
        return exTime;
    }

    public void setExTime(String exTime) {
        this.exTime = exTime;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
