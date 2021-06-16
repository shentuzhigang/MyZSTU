package cn.edu.zstu;

public class ApiResponse {
	private String code;
	
	private String msg;
	
	private Object data;
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return this.msg;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getData() {
		return this.data;
	}
}
