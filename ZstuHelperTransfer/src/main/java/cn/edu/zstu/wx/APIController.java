package cn.edu.zstu.wx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("wx")
public class APIController {
	
    private static final String REQUESTURL = "http://mcrd.zstu.edu.cn/ZstuHelper/wx";
	
	@RequestMapping(value="/openid",method= {RequestMethod.GET})
	@ResponseBody
	public Object getOpenid(@RequestParam("code") String code) {
		Map<String,Object> res=new HashMap<String,Object>();
		System.out.println("code="+code);
		try {
			String openId=WeChatTool.getOpenId(code);
			res.put("code","200");
			res.put("msg", "获取openid成功");
			res.put("data", openId);
		}catch(Exception e) {
			res.put("code","301");
			res.put("msg", "获取openid失败");
			res.put("data", null);
			e.printStackTrace();
		}
		return res;
	}
	@RequestMapping(value="/courses",method= {RequestMethod.GET})
	@ResponseBody
	public Object getCourses(@RequestParam("openid") String openid) {
		String url= REQUESTURL +  "/courses?openid="+openid;
		return getDataFromZstu(url);	
	}
	@RequestMapping(value="/grades",method= {RequestMethod.GET})
	@ResponseBody
	public Object getGrades(@RequestParam("openid") String openid) {
		String url= REQUESTURL + "/grades?openid="+openid;
		return getDataFromZstu(url);
	}
	@RequestMapping(value="/exams",method= {RequestMethod.GET})
	@ResponseBody
	public Object getExams(@RequestParam("openid") String openid) {
		String url= REQUESTURL + "/exams?openid="+openid;
		return getDataFromZstu(url);
	}
	@RequestMapping(value="/cardinfo",method= {RequestMethod.GET})
	@ResponseBody
	public Object getConsumption(@RequestParam("openid") String openid,@RequestParam("start") String start,@RequestParam("end") String end) {
		String url= REQUESTURL + "/cardinfo?openid="+openid;
		url+="&start="+start;
		url+="&end="+end;
		return getDataFromZstu(url);
	}
	@RequestMapping(value="/borrows",method= {RequestMethod.GET})
	@ResponseBody
	public Object getBorrows(@RequestParam("openid") String openid) {
		String url= REQUESTURL + "/borrows?openid="+openid;
		return getDataFromZstu(url);
	}
	@RequestMapping(value="/book",method= {RequestMethod.GET})
	@ResponseBody
	public Object getBooks(@RequestParam("key") String key) {
		String url= REQUESTURL + "/book?key="+key;
		return getDataFromZstu(url);
	}
	@RequestMapping(value="/student",method= {RequestMethod.POST})
	@ResponseBody
	public Object addStudent(@RequestParam("openid") String openid,@RequestParam("sid") String sid,
			@RequestParam("edupw") String edupw, @RequestParam("ecardpw") String ecardpw) {
		String url= REQUESTURL + "/student";
		JSONObject res=null;
		String errorStr= "{\"code\":\"500\",\"msg\":\"访问服务器出错\"}";
		res=JSON.parseObject(errorStr);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		try {
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(addStudentPostData(sid,openid,edupw,ecardpw), "UTF-8");
			httpPost.setEntity(postEntity);
			response=httpClient.execute(httpPost);
			String jsonStr=printResponse(response);
			res=JSON.parseObject(jsonStr);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(response!=null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;	
	}
	@RequestMapping(value="/firstday",method= {RequestMethod.GET})
	@ResponseBody
	public Object getFirstDay() {
		String url= REQUESTURL + "/firstday";
		return getDataFromZstu(url);	 
	}
	@RequestMapping(value="/phones",method= {RequestMethod.GET})
	@ResponseBody
	public Object getPhones() {	
		String url= REQUESTURL + "/phones";
		return getDataFromZstu(url);
	}
	private static List<NameValuePair> addStudentPostData(String sid,String openid,String edupw,String ecardpw){
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("sid", sid));
		pairs.add(new BasicNameValuePair("openid", openid));
		pairs.add(new BasicNameValuePair("edupw",edupw));
		pairs.add(new BasicNameValuePair("ecardpw", ecardpw));
		return pairs;
	}
	private static JSONObject getDataFromZstu(String url) {
		JSONObject res = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		String errorStr= "{\"code\":\"500\",\"msg\":\"访问服务器出错\"}";
		res = JSON.parseObject(errorStr);
		try {
			response = httpClient.execute(httpGet);
			String jsonStr = printResponse(response);
			res = JSON.parseObject(jsonStr);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(response!=null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;			
	}

	private static String printResponse(CloseableHttpResponse httpResponse) throws ParseException, IOException {
		String responseString = "";
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 判断响应实体是否为空
		if (entity != null) {
			responseString = EntityUtils.toString(entity,"UTF-8");
			httpResponse.close();
		}
		return responseString;
	}
}
