package cn.edu.zstu.spyder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Consumption;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.httpclient.HttpClientUtils;

//一卡通爬虫
@Component
public class EcardSpyder {
	
	@Autowired
	private EcardLoginer loginer;
	
	@Autowired
	private EcardParser ecardParser;
	
	//登录一卡通获取校园卡基本信息和消费明细
	public Map<String, Object> getCardInfo(Student stu, String start, String end) throws ZstuHelperException {
		Map<String, Object> res = new HashMap<>();
		Map<String, Object> baseInfo = new HashMap<>();
		List<Consumption> consumptionList = new ArrayList<>();
		try {
			Map<String, Object> loginRes = loginer.login(stu);
			String cookie = (String)loginRes.get("cookie");
			if((boolean)loginRes.get("loginFlag") && (!"".equals(cookie))) {
				baseInfo = crawlAndParseBaseInfo(cookie);
				consumptionList = crawlAndParseConsumption(cookie, start, end);
			}
		} catch (ZstuHelperException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.put("sid", (String)baseInfo.get("sid"));
		res.put("sname", (String)baseInfo.get("sname"));
		res.put("balance", (Double)baseInfo.get("balance"));
		res.put("consumptions", consumptionList);
		return res;
	}

	//解析获取基本信息
	private Map<String, Object> crawlAndParseBaseInfo(String cookie) throws Exception {
		Map<String, Object> baseInfo = new HashMap<>();
		String balanceUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/User/baseinfo.aspx";
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Origin", Consts.PROTOCOL + Consts.YKTHOST);
		header.put("Host", Consts.YKTHOST);
		String referer = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/Index.aspx";
		header.put("Referer", referer);
		header.put("Cookie", cookie);
		String htmlText = HttpClientUtils.doGetRequest(balanceUrl, header, null);
		baseInfo = YktParser.parseBalance(htmlText);
		return baseInfo;
	}
	
	//解析获取消费明细
	private List<Consumption> crawlAndParseConsumption(String cookie, String start, String end) throws Exception {
		List<Consumption> list = new ArrayList<Consumption>();
		String consumptionUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/User/ConsumeInfo.aspx";
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Origin", Consts.PROTOCOL + Consts.YKTHOST);
		header.put("Host", Consts.YKTHOST);
		header.put("Referer", consumptionUrl);
		header.put("Cookie", cookie);
		String htmlText = HttpClientUtils.doPostRequest(consumptionUrl, header, null);
		Map<String, String> keys = YktParser.parseYktViewstateAndEventValidation(htmlText);
		HttpEntity postEntity = new UrlEncodedFormEntity(getYktPagePostData(keys.get("viewstate"), keys.get("eventvalidation"), start, end), Consts.ENCODING);
		htmlText = HttpClientUtils.doPostRequest(consumptionUrl, header, postEntity);
		int page = YktParser.parsePages(htmlText);
		keys = parseYktViewstateAndEventValidation(htmlText);
		for (int i = 1; i <= page; i++) {
			String html = crawlConsumptionByPage(cookie, keys.get("viewstate"), keys.get("eventvalidation"), start, end, i);
			keys = parseYktViewstateAndEventValidation(html);
			List<Consumption> temp = ecardParser.parse(html);
			Iterator<Consumption> iter = temp.iterator();
			while (iter.hasNext()) {
				Consumption consum = iter.next();
				list.add(consum);
			}
		}
		return list;
	}

	//消费明细解析（分页）
	private String crawlConsumptionByPage(String cookie, String viewstate, String eventvalidation,
			String start, String end, int page) throws Exception {
		String consumptionUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/User/ConsumeInfo.aspx";
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Origin", Consts.PROTOCOL + Consts.YKTHOST);
		header.put("Host", Consts.YKTHOST);
		header.put("Referer", consumptionUrl);
		header.put("Cookie", cookie);
		HttpEntity postEntity = new UrlEncodedFormEntity(inputConsumptionForm(page, viewstate, eventvalidation, start, end), Consts.ENCODING);
		String htmlText = HttpClientUtils.doPostRequest(consumptionUrl, header, postEntity);
		return htmlText;
	}
	
	//请求信息填充
	private List<NameValuePair> inputConsumptionForm(int page, String viewstate, String eventvalidation, String start, String end) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ContentPlaceHolder1$AspNetPager1"));
		pairs.add(new BasicNameValuePair("__EVENTARGUMENT", String.valueOf(page)));
		pairs.add(new BasicNameValuePair("__VIEWSTATE", viewstate));
		pairs.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$rbtnType", "0"));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtStartDate", start));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtEndDate", end));
		return pairs;
	} 
	
	//请求信息填充
	private List<NameValuePair> getYktPagePostData(String viewstate, String eventvalidation, String start, String end) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("__EVENTTARGET", ""));
		pairs.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		pairs.add(new BasicNameValuePair("__VIEWSTATE", viewstate));
		pairs.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$rbtnType", "0"));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtStartDate", start));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtEndDate", end));
		pairs.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$btnSearch", "查  询"));
		return pairs;
	}
	
	//解析获取viewstate和eventvalidation的值
	public static Map<String, String> parseYktViewstateAndEventValidation(String htmlText) {
		Map<String, String> res = new HashMap<String, String>();
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Elements elements = doc.select("#form1 input[name=__VIEWSTATE]");
		String str = elements.toString();
		Elements elements2 = doc.select("#__EVENTVALIDATION");
		String str2 = elements2.toString();
		Matcher m = Pattern.compile("^<input.*value=\"(.+)\".*$").matcher(str);
		Matcher m2 = Pattern.compile("^<input.*value=\"(.+)\".*$").matcher(str2);
		if (m.find()) {
			res.put("viewstate", m.group(1));
		}
		if (m2.find()) {
			res.put("eventvalidation", m2.group(1));
		}
		return res;
	}
}
