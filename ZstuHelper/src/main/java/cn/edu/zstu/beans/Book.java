package cn.edu.zstu.beans;

public class Book {
	private String bookName;
	private String bookKey;
	private String author;
	private String press; //出版社
	private String time;
	private int inventory;  //馆藏数量
	private int available;  //可借数量
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookKey() {
		return bookKey;
	}
	public void setBookKey(String bookKey) {
		this.bookKey = bookKey;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{");
		sf.append("bookname:"+this.bookName+",");
		sf.append("bookkey:"+this.bookKey+",");
		sf.append("author:"+this.author+",");
		sf.append("press:"+this.press+",");
		sf.append("tim:"+this.time+",");
		sf.append("inventory:"+this.inventory+",");
		sf.append("available:"+this.available);
		sf.append("}");
		sf.append("\n");
		return sf.toString();
	}
}
