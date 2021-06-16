package cn.edu.zstu.beans;

public class Consumption {
	private String time;
	private String content;
	private String money;
	private String workstation;
	private String balance;
	private String place;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getWorkstation() {
		return workstation;
	}
	public void setWorkstation(String workstation) {
		this.workstation = workstation;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{");
		sf.append("time:"+this.time+",");
		sf.append("content:"+this.content+",");
		sf.append("money:"+this.money+",");
		sf.append("workstation:"+this.workstation+",");
		sf.append("place:"+this.place);
		sf.append("}");
		sf.append("\n");
		return sf.toString();
	}

}
