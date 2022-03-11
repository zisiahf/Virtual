package cn.hdu.virtual_experiment.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class Student_Info implements Serializable {
	private String username;
	
	private String password;
	
	private String sid;//学生id
	
	private String name;//学生姓名
	
	private String tid;//教师ID
	
	private String School;
	
	private String major;

	// 属性oid代表什么含义
	private String oid;
	
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSchool() {
		return School;
	}

	public void setSchool(String school) {
		School = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}


	
	
}
