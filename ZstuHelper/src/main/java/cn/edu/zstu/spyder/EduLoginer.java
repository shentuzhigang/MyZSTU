package cn.edu.zstu.spyder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.httpclient.HttpClientUtils;

//教务系统登录
@Component
public class EduLoginer{
	
	public boolean login(Student stu) throws ZstuHelperException, Exception {
		String eduLoginUrl = Consts.PROTOCOL+Consts.EDUHOST +"/"+stu.getUrlKey()+"/default2.aspx"; 
		Map<String, String> header = new HashMap<>();
		header.put("Origin", Consts.PROTOCOL+Consts.EDUHOST);
		header.put("Refer", eduLoginUrl);
		header.put("Host", Consts.EDUHOST);
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(inputLoginForm(stu.getSid(), stu.getEduPw()), Consts.ENCODING);
		String htmlText  = HttpClientUtils.doPostRequest(eduLoginUrl, header, postEntity);
		if (htmlText.contains("用户名不存在")) {
			throw new ZstuHelperException("201|学号不存在");
		}
		if (htmlText.contains("密码错误")) {
			throw new ZstuHelperException("202|密码错误");
		}
		if (htmlText.contains("errorpath")) {
			throw new ZstuHelperException("202|密码错误");
		}
		return true;
	}

	
	//填充请求信息
	private List<NameValuePair> inputLoginForm(String sid, String edupw) {
		List<NameValuePair> formData = new ArrayList<NameValuePair>();
		formData.add(new BasicNameValuePair("__VIEWSTATE", "dDw4ODUxNDk4MDQ7Oz7WaLo5r354ToP6u6DVYyrfVXcqHA=="));
		formData.add(new BasicNameValuePair("TextBox1", sid));
		formData.add(new BasicNameValuePair("RadioButtonList1", "学生"));
		formData.add(new BasicNameValuePair("TextBox2", edupw));
		formData.add(new BasicNameValuePair("Button1", ""));
		formData.add(new BasicNameValuePair("lbLanguage", ""));
		return formData;
	}
}
