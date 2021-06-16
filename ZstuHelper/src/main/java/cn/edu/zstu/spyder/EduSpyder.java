package cn.edu.zstu.spyder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Course;
import cn.edu.zstu.beans.Exam;
import cn.edu.zstu.beans.Grade;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.httpclient.HttpClientUtils;

//教务系统爬虫
@Component
public class EduSpyder {
	@Autowired
	private EduLoginer loginer;
	
	@Autowired
	private CourseParser courseParser;
	
	@Autowired
	private GradeParser gradeParser;
	
	@Autowired
	private ExamParser examParser;
	
	//获取课程信息
	public List<Course> getCourses(Student stu) throws ZstuHelperException{
		List<Course> data = null;
		try {
			if(loginer.login(stu)) {
				data = courseParser.parse(crawlCourses(stu));
			}
		} catch (ZstuHelperException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//抓取课程信息页面
	private String crawlCourses(Student stu) throws ClientProtocolException, IOException {
		String stuNumber = stu.getSid();
		String randomStr = stu.getUrlKey();
		String course_url = Consts.PROTOCOL+Consts.EDUHOST +"/"+randomStr + "/xskbcx.aspx";
		Map<String, String> header = new HashMap<>();
		String referer = Consts.PROTOCOL+Consts.EDUHOST +"/"+ randomStr + "/xs_main.aspx?xh=" + stuNumber;
		header.put("Referer", referer);
		Map<String, String> params = new HashMap<>();
		params.put("xh", stuNumber);
		params.put("xm", "");
		params.put("gnmkdm", "N121603");
		String response = HttpClientUtils.doGetRequest(course_url, header, params);
		//System.out.println("ssssss" + response);
		return response;	
	}

	//获取成绩信息
	public List<Grade> getGrades(Student stu) throws ZstuHelperException{
		List<Grade> data = null;
		try {
			if(loginer.login(stu)) {
				data = gradeParser.parse(crawlGrades(stu));
			} 
		} catch (ZstuHelperException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;		
	}
	
	//获取成绩信息页面
	private String crawlGrades(Student stu) throws ClientProtocolException, IOException {
		String stuNumber = stu.getSid();
		String randomStr = stu.getUrlKey();
		String course_url = Consts.PROTOCOL +Consts.EDUHOST+"/"+randomStr + "/xscjcx.aspx";
		Map<String, String> header = new HashMap<>();
		String referer = Consts.PROTOCOL +Consts.EDUHOST+"/"+ randomStr + "/xs_main.aspx?xh=" + stuNumber;
		header.put("Referer", referer);
		header.put("Host", Consts.EDUHOST);
		Map<String, String> params = new HashMap<>();
		params.put("xh", stuNumber);
		params.put("xm", "");
		params.put("gnmkdm","N121605");
		String htmlText = HttpClientUtils.doGetRequest(course_url, header, params);
		String viewstate = parseGradesViewState(htmlText);
		
		Map<String, String> header_ = new HashMap<>();
		String referer_ = Consts.PROTOCOL +Consts.EDUHOST + randomStr + "/xs_main.aspx?xh=" + stuNumber;
		header_.put("Referer", referer_);
		HttpEntity postEntity = new UrlEncodedFormEntity(getGradesPostData(viewstate), Consts.ENCODING);
		String new_course_url = Consts.PROTOCOL +Consts.EDUHOST+"/"+randomStr + "/xscjcx.aspx?xh=" + stuNumber +"&xm=&gnmkdm=N121605";
		String response = HttpClientUtils.doPostRequest(new_course_url, header_, postEntity);
		return response;		
	}
	
	//获取viewstate的值
	public String parseGradesViewState(String htmlText) {
		String res = "";
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements = doc.select("#Form1 input[name=__VIEWSTATE]");
		String str = elements.toString();
		Matcher m = Pattern.compile("^<input.*value=\"(.+)\".*$").matcher(str);
		while(m.find()) {
			res=m.group(1);
		}		
		return res;
	}
	
	//设置页面参数
	public List<NameValuePair> getGradesPostData(String viewstate) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("__EVENTTARGET", ""));
		pairs.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		pairs.add(new BasicNameValuePair("__VIEWSTATE", viewstate));
		pairs.add(new BasicNameValuePair("hidLanguage", ""));
		pairs.add(new BasicNameValuePair("ddlXN", ""));
		pairs.add(new BasicNameValuePair("ddlXQ", ""));
		pairs.add(new BasicNameValuePair("ddl_kcxz", ""));
		pairs.add(new BasicNameValuePair("btn_zcj", "历年成绩"));
		return pairs;
	}
	
	//获取考试信息
	public List<Exam> getExams(Student stu) throws ZstuHelperException{
		List<Exam> data = null;
		try {
			if(loginer.login(stu)) {
				data = examParser.parse(crawlExams(stu));
			}
		} catch (ZstuHelperException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;		
	}
	
	//抓取考试信息页面 
	private String crawlExams(Student stu) throws ClientProtocolException, IOException {
		String stuNumber = stu.getSid();
		String randomStr = stu.getUrlKey();
		String exam_url = Consts.PROTOCOL+Consts.EDUHOST +"/"+randomStr + "/xskscx.aspx";
		
		Map<String, String> header = new HashMap<>();
		String referer = Consts.PROTOCOL+Consts.EDUHOST +"/"+ randomStr + "/xs_main.aspx?xh=" + stuNumber;
		header.put("Referer", referer);
		Map<String, String> params = new HashMap<>();
		params.put("xh", stuNumber);
		params.put("xm", "");
		params.put("gnmkdm", "N121603");
		String response = HttpClientUtils.doGetRequest(exam_url, header, params);
		return response;		
	}
}
