package cn.hdu.virtual_experiment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.util.Date;

@ToString
public class Order_Info {

	private String ex_name;

	private String oid;//预约ID
	
	private String tid;//教师id 预约属于哪个教师

	private String eid;//实验预约号

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date orderdate; // 预约的实验在哪一天进行

	// 属性exTime的物理含义是啥
	private String exTime;	// 预约的实验在哪一天的哪个时间段进行的
	
	private Date createTime;	// 老师是什么时候开始预约的实验，相当于下单时间


	
	private Integer people_number;

	// 属性timeIndex的物理含义是啥
	private int timeIndex;

	public String getEx_name() {
		return ex_name;
	}

	public void setEx_name(String ex_name) {
		this.ex_name = ex_name;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getExTime() {
		return exTime;
	}

	public void setExTime(String exTime) {
		this.exTime = exTime;
	}

	public Integer getPeople_number() {
		return people_number;
	}

	public void setPeople_number(Integer people_number) {
		this.people_number = people_number;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getTimeIndex() {
		return timeIndex;
	}

	public void setTimeIndex(int timeIndex) {
		this.timeIndex = timeIndex;
	}


}
