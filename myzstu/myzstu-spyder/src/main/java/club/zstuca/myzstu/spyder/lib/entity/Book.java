package club.zstuca.myzstu.spyder.lib.entity;

import lombok.Data;

@Data
public class Book {
	private String bookName;
	private String bookKey;
	private String author;
	private String press; //出版社
	private String time;
	private int inventory;  //馆藏数量
	private int available;  //可借数量
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
