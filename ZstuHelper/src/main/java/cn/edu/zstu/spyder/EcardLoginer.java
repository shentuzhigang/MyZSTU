package cn.edu.zstu.spyder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.httpclient.HttpClientUtils;
import cn.edu.zstu.httpclient.SSLClientCustom;

//一卡通登录
@Component
public class EcardLoginer {
	private static final String EcardLoginUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/login.aspx";
	
	public Map<String, Object> login(Student stu) throws  ZstuHelperException, Exception {
		Map<String, Object> res = new HashMap<>();
		String cookie = "";
		res.put("loginFlag", false);
		res.put("cookie", "");
		
		//访问登录页面获取viewstate 和 eventvalidation的值
		Map<String, String> header1 = new HashMap<>();
		header1.put("User-Agent", Consts.AGENT);
		header1.put("Host", Consts.YKTHOST);
		String htmlText1 = HttpClientUtils.doGetRequest(EcardLoginUrl, header1, null);
		Map<String, String> keys = parseYktViewstateAndEventValidation0(htmlText1);
		
		//访问验证码页面
		String code_url = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/validateimage.ashx?";
		CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
		httpClient = SSLClientCustom.getHttpClient();
		HttpGet httpGet = new HttpGet(code_url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true).build();
        httpGet.setConfig(requestConfig);
		httpResponse = httpClient.execute(httpGet);
		Header[] headers1 = httpResponse.getAllHeaders();
		for(Header h1 : headers1) {
			if(h1.getName().contains("Set-Cookie")) {
				cookie = h1.getValue();
			}
		}
		HttpEntity httpEntity = httpResponse.getEntity();
		String imgUrl = ResourceUtils.getURL("classpath:static").getPath()+ File.separator + "codeimg" + File.separator;
		imgUrl += cookie.substring(18,41) + "-" + System.currentTimeMillis() + ".jpg";
		FileOutputStream fileOut = new FileOutputStream(imgUrl);
		httpEntity.writeTo(fileOut);
		fileOut.close();
		httpClient.close();
		httpResponse.close();
		keys.put("vaildateCode", Consts.tess(imgUrl));
		
		//访问登录页面，进行登录
		HttpPost httpPost = new HttpPost(EcardLoginUrl);
		Map<String, String> header3 = new HashMap<>();
		header3.put("User-Agent", Consts.AGENT);
		header3.put("Origin", Consts.PROTOCOL+Consts.EDUHOST);
		header3.put("Referer", EcardLoginUrl);
		header3.put("Host", Consts.YKTHOST);
		header3.put("Cookie", cookie);
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(inputLoginForm(stu, keys), Consts.ENCODING);
		for (Map.Entry<String, String> headerEntry : header3.entrySet()) {
            httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
        }
		httpPost.setEntity(postEntity);
        httpResponse = httpClient.execute(httpPost);
        HttpEntity httpResponseEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
            Header[] headers = httpResponse.getAllHeaders();
            for(Header h3 : headers) {
            	if(h3.getName().contains("Set-Cookie")) {
    				cookie += h3.getValue() + ";";
    			}
    		}
            res.put("loginFlag", true);
			res.put("cookie", cookie);
        }else {
        	String htmlText3 = EntityUtils.toString(httpResponseEntity);
    		Document doc = Jsoup.parse(htmlText3, "UTF-8");
    		Elements elements=doc.select(".loginerror");
    		if (elements.toString().contains("验证码错误")) {
    			throw new ZstuHelperException("202|验证码错误");
			} 
			if ( elements.toString().contains("密码错误")) {
				throw new ZstuHelperException("201|学号或密码错误");
			}
        }
        httpClient.close();
		httpResponse.close();
		return res;
	}
	
	//请求信息填充
	private List<NameValuePair> inputLoginForm(Student stu, Map<String, String> keys) {
		List<NameValuePair> formData = new ArrayList<NameValuePair>();
		formData.add(new BasicNameValuePair("__LASTFOCUS", ""));
		formData.add(new BasicNameValuePair("__EVENTTARGET", "btnLogin"));
		formData.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		formData.add(new BasicNameValuePair("__VIEWSTATE", keys.get("viewstate")));
		formData.add(new BasicNameValuePair("__EVENTVALIDATION", keys.get("eventvalidation")));
		formData.add(new BasicNameValuePair("txtUserName", stu.getSid()));
		formData.add(new BasicNameValuePair("txtPassword", stu.getEcardPw()));
		formData.add(new BasicNameValuePair("txtVaildateCode", keys.get("vaildateCode")));
		formData.add(new BasicNameValuePair("hfIsManager", "0"));
		return formData;
		}
	
	//解析获取viewstate 和 eventvalidation的值
	private  Map<String,String> parseYktViewstateAndEventValidation0(String htmlText) {
		Map<String,String> res=new HashMap<String,String>();
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements=doc.select("#form2 input[name=__VIEWSTATE]");
		String str=elements.toString();
		Elements elements2=doc.select("#__EVENTVALIDATION");
		String str2=elements2.toString();
		Matcher m = Pattern.compile("^<input.*value=\"(.+)\".*$").matcher(str);
		Matcher m2 = Pattern.compile("^<input.*value=\"(.+)\".*$").matcher(str2);
		if(m.find()) {
			res.put("viewstate", m.group(1));	
		}	
		if(m2.find()) {
			res.put("eventvalidation", m2.group(1));		
		}
		return res;
	}

}
