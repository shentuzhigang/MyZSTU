package cn.edu.zstu.spyder;

import java.util.LinkedList;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cn.edu.zstu.beans.Exam;

//考试解析
@Component
public class ExamParser{
	public  List<Exam> parse(String htmlText) {
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements = doc.select("#DataGrid1 tr");
		List<Exam> res = new LinkedList<Exam>();
		for (int i = 1; i < elements.size(); i++) {
			try {
				Elements exams = elements.get(i).select("td");
				Exam exam = new Exam();
				exam.setCourseName(exams.get(1).text());
				exam.setExamTime(exams.get(3).text());
				exam.setExamPlace(exams.get(4).text());
				exam.setSeatNumber(exams.get(6).text());
				res.add(exam);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
		return res;
	}

}
