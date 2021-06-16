package cn.edu.zstu;

import java.util.List;
import java.util.Set;

import cn.edu.zstu.beans.Grade;

public class GradeDTO {
	
	private String code;
	private String msg;
	private List<Grade> data;
	private Set<String> years;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(List<Grade> data) {
		this.data = data;
	}
	public Set<String> getYears() {
		return years;
	}
	public void setYears(Set<String> years) {
		this.years = years;
	}
}
