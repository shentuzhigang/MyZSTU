package cn.edu.zstu.beans;

public class BookRecord {
	private String number;
	private String bookName;
	private String author; 
	private String borrowDate;
	private String returnDate;
	private String location;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		StringBuffer sf=new StringBuffer();
		sf.append("{");
		sf.append("number:"+this.number+",");
		sf.append("bookName:"+this.bookName+",");
		sf.append("author:"+this.author+",");
		sf.append("borrowDate:"+this.borrowDate+",");
		sf.append("returnDate:"+this.returnDate+",");
		sf.append("locarion:"+this.location);
		sf.append("}");
		sf.append("\n");
		return sf.toString();
	}

}
