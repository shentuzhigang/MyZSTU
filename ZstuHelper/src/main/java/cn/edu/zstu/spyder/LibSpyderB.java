package cn.edu.zstu.spyder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.edu.zstu.Consts;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.BookRecord;
import cn.edu.zstu.httpclient.HttpClientUtils;

@Component
public class LibSpyderB {
	private static final String ezlUrl = Consts.PROTOCOL + Consts.EZLHOST +"/webroot/decision/view/report";
	
	private String getSessionID(String sid) throws Exception {
		String sessionid = "";
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Host", Consts.EZLHOST);
		Map<String, String> params = new HashMap<>();
		params.put("viewlet", "/yiban/S图书借阅情况查询.cpt");
		params.put("__pi__", "true");
		params.put("op", "h5");
		params.put("username", sid);
		String htmlText = HttpClientUtils.doGetRequest(ezlUrl, header, params);
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Element element = doc.select("script").first();
		sessionid = element.toString().split("get")[7].split("'")[1];
		return sessionid;
	}
	
	public Map<String, List<BookRecord>> getBorrowList(String sid) throws ZstuHelperException, Exception{
		Map<String, List<BookRecord>> res = new HashMap<>();
		List<BookRecord> borrowlist = new ArrayList<>();
		List<BookRecord> historylist = new ArrayList<>();
		String sessionid = getSessionID(sid);
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Host", Consts.EZLHOST);
		header.put("sessionID", sessionid);
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(inputGetForm(sessionid, "1"), Consts.ENCODING);
		String htmlText = HttpClientUtils.doPostRequest(ezlUrl, header, postEntity);
		JSONObject resjson = JSONObject.parseObject(htmlText);
		JSONArray bookjson = resjson.getJSONObject("pageContent").getJSONArray("detail").getJSONObject(0).getJSONObject("cellData").getJSONArray("rows");
		String sname = bookjson.getJSONObject(4).getJSONArray("cells").getJSONObject(2).getString("text");
		if(sname.equals("")) {
			throw new ZstuHelperException("201|学号错误");
		}
		int pagenum = resjson.getInteger("reportTotalPage");
		int borrownum = Integer.parseInt(bookjson.getJSONObject(6).getJSONArray("cells").getJSONObject(2).getString("text"));
		int size =  pagenum == 1 ? bookjson.size() - 5 : bookjson.size();
		for(int k = 15; k < size; k ++) {
			JSONArray bookinfo = bookjson.getJSONObject(k).getJSONArray("cells");
			String bookname = bookinfo.getJSONObject(2).getString("text");
			String borrowdate = bookinfo.getJSONObject(5).getString("text");
			BookRecord bookRecord = new BookRecord();
			bookRecord.setBookName(bookname);
			bookRecord.setBorrowDate(borrowdate);
			if(borrowlist.size() < borrownum || k - 14 <= 2* borrownum) {
				if(borrowlist.size() == 0 || (borrowlist.size() > 0 && (!bookname.equals(borrowlist.get(borrowlist.size() - 1).getBookName()) || !borrowdate.equals(borrowlist.get(borrowlist.size() - 1).getBorrowDate())))) {
					borrowlist.add(bookRecord);
				}
				else continue;
			}
			else{
				historylist.add(bookRecord);
			}
			
		}
		for(int i = 1; i < pagenum; i ++) {
			postEntity = new UrlEncodedFormEntity(inputGetForm(sessionid, i+1+""), Consts.ENCODING);
			htmlText = HttpClientUtils.doPostRequest(ezlUrl, header, postEntity);
			resjson = JSONObject.parseObject(htmlText);
			bookjson = resjson.getJSONObject("pageContent").getJSONArray("detail").getJSONObject(0).getJSONObject("cellData").getJSONArray("rows");
			size = i + 1 < pagenum ? bookjson.size() : bookjson.size() - 4;
			for(int k = 0; k < size; k ++) {
				JSONArray bookinfo = bookjson.getJSONObject(k).getJSONArray("cells");
				String bookname = bookinfo.getJSONObject(2).getString("text");
				String borrowdate = bookinfo.getJSONObject(5).getString("text");
				BookRecord bookRecord = new BookRecord();
				bookRecord.setBookName(bookname);
				bookRecord.setBorrowDate(borrowdate);
				if(borrowlist.size() < borrownum) {
					if(borrowlist.size() == 0 || (borrowlist.size() > 0 && (!bookname.equals(borrowlist.get(borrowlist.size() - 1).getBookName()) || !borrowdate.equals(borrowlist.get(borrowlist.size() - 1).getBorrowDate())))) {
						borrowlist.add(bookRecord);
					}
				}
				else {
					historylist.add(bookRecord);
				}
				
			}
		}
		res.put("borrowlist", borrowlist);
		res.put("historylist", historylist);
		return res;
	}
	
	//填充请求信息
	private List<NameValuePair> inputGetForm(String sessionid, String pagenum) {
		List<NameValuePair> formData = new ArrayList<NameValuePair>();
		formData.add(new BasicNameValuePair("toVanCharts", "true"));
		formData.add(new BasicNameValuePair("dynamicHyperlink", "true"));
		formData.add(new BasicNameValuePair("op", "page_content"));
		formData.add(new BasicNameValuePair("cmd", "json"));
		formData.add(new BasicNameValuePair("sessionID", sessionid));
		formData.add(new BasicNameValuePair("pn", pagenum));
		formData.add(new BasicNameValuePair("__fr_locale__", "zh_CN"));
		return formData;
	}
	
}
