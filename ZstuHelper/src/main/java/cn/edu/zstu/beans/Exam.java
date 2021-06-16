package cn.edu.zstu.beans;

public class Exam {

	private String courseName;
	private String examTime;
	private String examPlace;
	private String seatNumber;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getExamPlace() {
		return examPlace;
	}
	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{");
		sf.append("\"课程名称\":"+"\""+this.courseName+"\",");
		sf.append("\"考试时间\":"+"\""+this.examTime+"\",");
		sf.append("\"考试地点\":"+"\""+this.examPlace+"\",");
		sf.append("\"座位号\":"+"\""+this.seatNumber+"\"");
		sf.append("}");
		return sf.toString();	
	}
	
}

