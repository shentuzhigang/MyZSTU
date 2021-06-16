package cn.edu.zstu.beans;

import java.util.Random;

public class Student {
	//学生基本信息
	
	private String openid;
	private String sid;
	private String sname;
	private String acadeemy;
	private String major;
	private String stuClass;
	private String eduPw; //教务管理系统密码
	private String libPw;
	private String ecardPw;
	private String ssoPw;
	private String urlKey; //访问教务系统时，url中会随机生成一个24位字符串
	private String eduLoginUrl; //该学生的教务系统登录地址
	public Student(){
		this.urlKey = get24RandomStr();
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAcadeemy() {
		return acadeemy;
	}
	public void setAcadeemy(String acadeemy) {
		this.acadeemy = acadeemy;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getStuClass() {
		return stuClass;
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	public String getEduPw() {
		return eduPw;
	}
	public void setEduPw(String eduPw) {
		this.eduPw = eduPw;
	}
	public String getLibPw() {
		return libPw;
	}
	public void setLibPw(String libPw) {
		this.libPw = libPw;
	}
	public String getEcardPw() {
		return ecardPw;
	}
	public void setEcardPw(String ecardPw) {
		this.ecardPw = ecardPw;
	}

	public String getSsoPw() {
		return ssoPw;
	}
	public void setSsoPw(String ssoPw) {
		this.ssoPw = ssoPw;
	}
	public String getUrlKey() {
		return urlKey;
	}
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	public void setEduLoginUrl(String eduLoginUrl) {
		this.eduLoginUrl = eduLoginUrl;
	}
	public String getEduLoginUrl() {
		return this.eduLoginUrl;
	}
	private static String get24RandomStr() {
		String res = "(";
		String source = "012345abcdefghigklmnopqrstuvwxyz";
		Random random = new Random();
		for (int i = 0; i < 24; i++) {
			res += source.charAt(random.nextInt(31));
		}
		res += ")";
		return res;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Student [openid=");
		builder.append(openid);
		builder.append(", sid=");
		builder.append(sid);
		builder.append(", sname=");
		builder.append(sname);
		builder.append(", acadeemy=");
		builder.append(acadeemy);
		builder.append(", major=");
		builder.append(major);
		builder.append(", stuClass=");
		builder.append(stuClass);
		builder.append(", eduPw=");
		builder.append(eduPw);
		builder.append(", libPw=");
		builder.append(libPw);
		builder.append(", ecardPw=");
		builder.append(ecardPw);
		builder.append(", ssoPw=");
		builder.append(ssoPw);
		builder.append(", urlKey=");
		builder.append(urlKey);
		builder.append(", eduLoginUrl=");
		builder.append(eduLoginUrl);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
