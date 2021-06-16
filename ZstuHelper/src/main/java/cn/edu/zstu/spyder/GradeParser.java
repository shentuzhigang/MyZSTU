package cn.edu.zstu.spyder;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cn.edu.zstu.beans.Grade;

//成绩解析
@Component
public class GradeParser{

	public List<Grade> parse(String htmlText) {	
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements = doc.select("#Datagrid1 tr");
		List<Grade> res = new LinkedList<Grade>();
		for (int i = 1; i < elements.size(); i++) {
			Elements grades = elements.get(i).select("td");
			Grade grade = new Grade();
			try {
				grade.setYear(grades.get(0).text());
				grade.setTerm(grades.get(1).text());
				grade.setCourseName(grades.get(3).text());
				grade.setCredit(grades.get(6).text());
				String gr = "";
				// 有补考成绩 则算补考成绩
				gr = new String(grades.get(10).text().getBytes("GBK")).replace("?","");
				if(!gr.equals("")) {
					grade.setGrade(grades.get(10).text());
				}
				else {
					grade.setGrade(grades.get(8).text());
				}
				grade.setGpa(grades.get(7).text());
				res.add(grade);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

}
