package club.zstuca.myzstu.spyder.lib.entity;

import lombok.Data;

@Data
public class BookRecord {
	private String number;
	private String bookName;
	private String author;
	private String borrowDate;
	private String returnDate;
	private String location;
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
