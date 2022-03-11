package cn.hdu.virtual_experiment.vo;

/*
登录提交的信息
 */
public class LoginVo {

	private String node_number;//节点号

	private String username;//用户名

	private String password;//密码

	private String oid;//预约id
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getNode_number() {
		return node_number;
	}

	public void setNode_number(String node_number) {
		this.node_number = node_number;
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

	@Override
	public String toString() {
		return "LoginVo{" +
				"oid='" + oid + '\'' +
				", node_number='" + node_number + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
