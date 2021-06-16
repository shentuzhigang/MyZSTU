package cn.edu.zstu.beans;

public class Grade {
	private String year;
	private String term;
	private String courseName;
	private String credit;
	private String gpa;
	private String grade;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getGpa() {
		return gpa;
	}
	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{");
		sf.append("\"学年\":"+this.year+",");
		sf.append("\"学期\":"+this.term+",");
		sf.append("\"课程名称\":"+this.courseName+",");
		sf.append("\"学分\":"+this.credit+",");
		sf.append("\"绩点\":"+this.gpa+",");
		sf.append("\"成绩\":"+this.grade);
		sf.append("}");
		return sf.toString();	
	}
}

