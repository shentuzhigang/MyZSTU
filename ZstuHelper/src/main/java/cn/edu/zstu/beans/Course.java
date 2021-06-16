package cn.edu.zstu.beans;

public class Course {
	private String day;    //周几
	private String period; //第几节
	private String length; //上课长度 2/3
	private String name;
	private String type;  //1为普通课 2为实践课
	private String teacher;
	private String room;
	private String week;  //单周 双周 前八周 后八周 正常(1-16周)
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{name:"+this.name+",");
		sf.append("room:"+this.room+",");
		sf.append("teacher:"+this.teacher+",");
		sf.append("week:"+this.week+",");
		sf.append("teacher:"+this.teacher+",");
		sf.append("period:"+this.period+",");
		sf.append("length:"+this.length+"}");
		return sf.toString();
	}
	
}

