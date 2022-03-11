package cn.hdu.virtual_experiment.result;

public class CodeMsg {
	
	private int code;
	private String msg;
	
	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
	public static CodeMsg DATA_ERROR = new CodeMsg(5001023, "数据异常");
	public static CodeMsg UPLOAD_ERROR= new CodeMsg(500104, "上传错误");
	public static CodeMsg NODE_ISNOTEXIST= new CodeMsg(500105, "节点不存在");
	public static CodeMsg NODE_ISUSED= new CodeMsg(500106, "节点被使用，请重新选择");
	public static CodeMsg NODE_ISNOTOPEN= new CodeMsg(500107, "端口未打开");
	public static CodeMsg NODE_ISOPNE= new CodeMsg(500108, "端口已打开");

	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg USER_ISNOTLOGIN = new CodeMsg(500212, "请先登陆");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "用户名或密码错误");
	public static CodeMsg LOGIN_ERROR = new CodeMsg(500216, "该预约下无此学生信息");
	public static CodeMsg USERNAME_EXIST = new CodeMsg(500216, "用户名已存在");
	
	//预约模块 5003XX
	public static CodeMsg DATE_ERROR = new CodeMsg(500310, "日期不能小于今天");
	public static CodeMsg ORDER_ERROR = new CodeMsg(500311, "该时间段已被预约");

	//上传EXCEL失败 5004XX
	public static CodeMsg FORMAT_ERROR = new CodeMsg(500410, "上传文件格式不正确");
	public static CodeMsg SHEET_ISNULL = new CodeMsg(500411, "文件内容为空");
	public static CodeMsg USERNAME_ISNULL = new CodeMsg(500412, "用户名不能为空");
	public static CodeMsg PASSWORD_ISNULL = new CodeMsg(500413, "密码不能为空");
	public static CodeMsg NAME_ISNULL = new CodeMsg(500413, "姓名不能为空");

	//学生 5004XX
	public static CodeMsg USERNAME_ISUSED = new CodeMsg(500410, "该用户名已存在");
	
	private CodeMsg( ) {
	}
			
	private CodeMsg( int code,String msg ) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}
