package club.zstuca.myzstu.spyder.lib;


import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.spyder.lib.entity.BookRecord;
import club.zstuca.myzstu.utils.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
		String htmlText = HttpUtil.doGet(ezlUrl, header, params).getContent();
		Document doc = Jsoup.parse(htmlText, "UTF-8");
		Element element = doc.select("script").get(1);
		sessionid = element.toString().split("get")[7].split("'")[1];
		return sessionid;
	}

	public Map<String, List<BookRecord>> getBorrowList(String sid) throws Exception, Exception{
		Map<String, List<BookRecord>> res = new HashMap<>();
		List<BookRecord> borrowlist = new ArrayList<>();
		List<BookRecord> historylist = new ArrayList<>();
		String sessionid = getSessionID(sid);
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		header.put("Host", Consts.EZLHOST);
		header.put("sessionID", sessionid);
		String htmlText = HttpUtil.doPost(ezlUrl, header, inputGetForm(sessionid, "1")).getContent();
		//System.out.println(htmlText);
		JSONObject resjson = JSONObject.parseObject(htmlText);
		JSONArray bookjson = resjson.getJSONObject("pageContent").getJSONArray("detail").getJSONObject(0).getJSONObject("cellData").getJSONArray("rows");
		String sname = bookjson.getJSONObject(4).getJSONArray("cells").getJSONObject(2).getString("text");
		if(sname.equals("")) {
			throw new Exception("201|学号错误");
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
			htmlText = HttpUtil.doPost(ezlUrl, header, inputGetForm(sessionid, i+1+"")).getContent();
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
	private Map<String,String> inputGetForm(String sessionid, String pagenum) {
		Map<String,String > formData = new HashMap<>();
		formData.put("toVanCharts", "true");
		formData.put("dynamicHyperlink", "true");
		formData.put("op", "page_content");
		formData.put("cmd", "json");
		formData.put("sessionID", sessionid);
		formData.put("pn", pagenum);
		formData.put("__fr_locale__", "zh_CN");
		return formData;
	}

}
