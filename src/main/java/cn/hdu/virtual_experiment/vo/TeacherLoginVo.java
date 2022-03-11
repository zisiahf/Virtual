package cn.hdu.virtual_experiment.vo;

import lombok.ToString;

/*
登录提交的信息
 */
@ToString
public class TeacherLoginVo {

	private String username;//用户名

	private String password;//密码

	private String oid;//预约id

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

}
