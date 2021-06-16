package cn.edu.zstu.spyder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cn.edu.zstu.Consts;
import cn.edu.zstu.beans.Course;

//课程解析
@Component
public class CourseParser{

	public List<Course> parse(String htmlText) {
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		// 解析普通课
		// tr->td
		Elements elements = doc.select("#Table1 tr");
		List<Course> courses = new ArrayList<Course>();
		for (int i = 2; i < elements.size(); i++) {
			Elements tds = elements.get(i).select("td");
			for (int j = 0; j < tds.size(); j++) {
				String str = tds.get(j).toString();
				str = str.replaceAll("<td.+?>", "").replaceAll("</td>", "").replaceAll("<a.+?>", "")
						.replaceAll("</a>", "").replaceAll("<font.+?</font>", "");
				if (str.matches(".+周(.)第(.+?)节.+")) {
					// 为什么要按<br><br> split
					// 因为有的课程同一门课程时间不同地点不同/,一个td里有多个课程
					// 如<td align="Center" rowspan="2" width="7%">英语4<br>周五第1,2节{第1-16周|单周}<br>刘虹翠<br>2-N402(2015-2016-2)-73510-07111-3<br><br>英语4<br>周五第1,2节{第1-16周|双周}<br>刘虹翠<br>2-S242(2015-2016-2)-73510-07111-3</td>
					String[] stuCourses = str.split("<br><br>");
					for (int k = 0; k < stuCourses.length; k++) {
						
							//System.out.println(stuCourses[k]);
							Course cour = makeCourse(stuCourses[k]);
							courses.add(cour);				
				
					} // end for
				} // end if
			} // end for
		} // end for
		// 解析实践课
		Elements pracs1 = doc.select("#DataGrid1 tr");
		for (int i = 1; i < pracs1.size(); i++) {
			Course pra = new Course();
			Elements tds = pracs1.get(i).select("td");
			pra.setName(tds.get(0).text());
			pra.setTeacher(tds.get(1).text());
			pra.setType("实践课");
			courses.add(pra);
		}
		Elements pracs2 = doc.select("#Datagrid2 tr");
		for (int i = 1; i < pracs2.size(); i++) {
			Course pra = new Course();
			Elements tds = pracs2.get(i).select("td");
			pra.setName(tds.get(2).text());
			pra.setTeacher(tds.get(3).text());
			pra.setType("实践课");
			courses.add(pra);
		}
		return courses;

	}

	// 将课程字符串构造成course对象
	// courseStr如:信息系统分析、设计与实现A<br>周一第3,4,5节{第1-8周}<br>姜毅<br>25-204<br>2019年11月7日(14:00-16:00)
	private Course makeCourse(String courseStr) {
		Course cour = new Course();
		String[] info = courseStr.split("<br>");

		Matcher m = Pattern.compile("周(.)第(.+)节.+第(.+)周.+").matcher(info[1]);
		if (m.find()) {
			String[] period = m.group(2).split(",");
			cour.setDay(Consts.weekMap.get(m.group(1)));
			cour.setLength(String.valueOf(period.length));
			cour.setPeriod(period[0]);
			if (courseStr.contains("单周")) {
				cour.setWeek("单");
			}
			if (courseStr.contains("双周")) {
				cour.setWeek("双");
			} else {
				cour.setWeek(m.group(3));
			}
			cour.setName(info[0]);
			cour.setTeacher(info[2]);
			cour.setType("一般课");
			String room = info[3];
			cour.setRoom(room);
		}

		return cour;
	}
}
