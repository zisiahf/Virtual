package cn.hdu.virtual_experiment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MyOrder {

    private String oid;//预约id

    private String ex_name;//预约实验名称

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date orderdate;//实验日期

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:ss")
    private Date createTime;//实验日期

    private String exTime;//实验时间

    private Integer people_number;

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

    public Integer getPeopleNum() {
        return people_number;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.people_number = peopleNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPeople_number() {
        return people_number;
    }

    public void setPeople_number(Integer people_number) {
        this.people_number = people_number;
    }
}
