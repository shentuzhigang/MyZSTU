package cn.edu.zstu.spyder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.httpclient.HttpClientUtils;
import cn.edu.zstu.httpclient.SSLClientCustom;

//图书馆登录（暂时废弃）
@Component
public class LibLoginer {
	public Map<String, Object> login(Student stu) throws ZstuHelperException, Exception {
		Map<String, Object> res = new HashMap<>();
		return res;
	}
	
	//填充请求信息
	private List<NameValuePair> inputLoginForm(Student stu, String execution) {
		List<NameValuePair> formData = new ArrayList<NameValuePair>();
		formData.add(new BasicNameValuePair("username", stu.getSid()));
		formData.add(new BasicNameValuePair("password", stu.getSsoPw()));
		formData.add(new BasicNameValuePair("passwordPre", stu.getSsoPw()));
		formData.add(new BasicNameValuePair("type", "UsernamePassword"));
		formData.add(new BasicNameValuePair("_eventId", "submit"));
		formData.add(new BasicNameValuePair("execution", execution));
		formData.add(new BasicNameValuePair("geolocation", ""));
		formData.add(new BasicNameValuePair("captcha_code", ""));
		return formData;
	}
}
