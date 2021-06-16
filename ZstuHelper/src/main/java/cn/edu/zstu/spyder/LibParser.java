package cn.edu.zstu.spyder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.zstu.beans.Book;
import cn.edu.zstu.beans.BookRecord;
/**
 * 图书系统信息解析类(暂时放弃)
 *
 */
public class LibParser {
	/**
	 * 借书记录解析
	 * @param htmlText
	 * @return
	 */
	public static List<BookRecord> parseBorrowList(String htmlText){
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements=doc.select("#mylib_content .table_line tr");
		List<BookRecord> res=new ArrayList<BookRecord>();
		for(int i=1;i<elements.size();i++) {
			Elements tds=elements.get(i).select("td");
			BookRecord item=new BookRecord();
			item.setNumber(tds.get(0).text());
			String[] nameAddAuthor=tds.get(1).text().split("/");
			item.setBookName(nameAddAuthor[0]);
			item.setAuthor(nameAddAuthor[1]);
			item.setBorrowDate(tds.get(2).text());
			item.setReturnDate(tds.get(3).text());
			item.setLocation(tds.get(5).text());
			res.add(item);
		}
		return res;
	}
	/**
	 * 图书查询解析
	 * @param htmlText
	 * @return
	 */
	public static List<Book> parseBookInfo(String htmlText){
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements=doc.select("#search_book_list li");
		List<Book> res=new ArrayList<Book>();
		for(int i=0;i<elements.size();i++) {
			Book book=new Book();
			Element elem=elements.get(i);
			Elements h3=elem.select("h3");
			String name=h3.get(0).select("a").text();
			book.setBookName(name);
			String[] info=h3.get(0).toString().split("</a>");
			String key=info[1].replace("</h3>", "").replace(" ", "");
			book.setBookKey(key);	
			Elements p=elem.select("p");
			Elements span=p.select("span");
			String[] info1=span.toString().replace("<span>","").replace("</span>","").split("<br>");
			String inventory=info1[0].replace("馆藏复本：", "").replace(" ", "");
			book.setInventory(Integer.parseInt(inventory));		
			String available=info1[1].replace("可借复本：", "").replace(" ", "");
			book.setAvailable(Integer.parseInt(available));		
			Matcher m = Pattern.compile("^.+</span>(.+)<br>(.+)<br>.+$").matcher(p.toString());
			if(m.find()) {
				String author=m.group(1).replace(" ", "");
				book.setAuthor(author);
				String[] info2=m.group(2).split("&nbsp;");
				String press=info2[0].replace(" ", "");
				book.setPress(press);
				String time=info2[1].replace(" ", "");
				book.setTime(time);	
			}
			res.add(book);				
		}
		return res;
	}
}
