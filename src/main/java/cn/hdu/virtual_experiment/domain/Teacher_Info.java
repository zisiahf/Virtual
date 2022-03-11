package cn.hdu.virtual_experiment.domain;

import lombok.ToString;

@ToString
public class Teacher_Info {
	private String tid;
	
	private String username;
	
	private String password;
	
	private String workplace;
	
	private String teacherName;

	// 属性idenity的物理含义是啥
	private String idenity;
	

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
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

	public void setPassword(String passowrd) {
		this.password = passowrd;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getName() {
		return teacherName;
	}

	public void setName(String name) {
		this.teacherName = name;
	}

	public String getIdenity() {
		return idenity;
	}

	public void setIdenity(String idenity) {
		this.idenity = idenity;
	}

	
	
}
