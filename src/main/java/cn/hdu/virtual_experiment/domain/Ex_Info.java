package cn.hdu.virtual_experiment.domain;

import cn.hdu.virtual_experiment.util.SerialComm;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class Ex_Info implements Serializable {

    private String eid;      //  实验id
    private String ex_name;  //  实验名称
    private String page;     //  做该实验需要跳转到哪一个页面(只能做给定的实验，否者页面不能跳转)
    private int count;       //  实验时需要的节点数量
    private  int len;        //  数据长度

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
