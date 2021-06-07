package club.zstuca.myzstu.spyder.lib;


import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.spyder.lib.entity.Book;
import club.zstuca.myzstu.spyder.lib.entity.BookRecord;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.*;


@Component
public class LibSpyder {
	@Autowired
	private LibLoginer loginer;

	public List<BookRecord> getBorrowList(String username, String password) throws Exception {
		List<BookRecord> res = new ArrayList<>();
		String htmlText = "";
		try {
			Map<String, Object> loginRes = loginer.login(username,password);
			System.out.println("获取的cookie："+(String)loginRes.get("cookie"));
			//String cookie = "PHPSESSID=ST-3194--REFUiPh4pmrF4EUbwSKjtjs-I0rg-sso-5b475b7697-lk65f; path=/; HttpOnly";
			String cookie = (String)loginRes.get("cookie");
			if((boolean)loginRes.get("loginFlag") && (!"".equals(cookie))) {
				htmlText = crawlBorrowList(cookie);
				System.out.println("yyyyyyyyyyyy"+htmlText);
				res = LibParser.parseBorrowList(htmlText);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;

//		CloseableHttpClient httpClient = HttpClients.createDefault();	
//		String htmlText = "";
//		List<BookRecord> res = null;
//		try {
//			loginer.login(stu);
//			htmlText = crawlBorrowList(stu);
//			res = LibParser.parseBorrowList(htmlText);
//		}
//		catch (ClientProtocolException e) {
//			e.printStackTrace();
//			throw new ZstuHelperException("cc");
//		}catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return res;
	}

	public List<Book> getBookInfo(String key) {
		List<Book> res = new ArrayList<>();
		String htmlText = "";
		try {
			htmlText = crawlBookInfo(key);
			res = LibParser.parseBookInfo(htmlText);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
//		CloseableHttpClient httpClient= HttpClients.createDefault();
//		String htmlText = "";
//		List<Book> res = null;
//		try {
//			htmlText = crawlBookInfo(key,httpClient);
//			res = LibParser.parseBookInfo(htmlText);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return res;
	}

	private String crawlBorrowList(String cookie) throws Exception {
		String ssoLoginUrl = Consts.PROTOCOLS + Consts.SSOHOST + "/login";
		String libLoginUrl = Consts.PROTOCOL + Consts.LIBHOST + "/reader/hwthau.php";
		String searchBookUrl = Consts.PROTOCOL + Consts.LIBHOST +"/reader/book_lst.php";
		Map<String, String> header = new HashMap<>();
		System.out.println("我的cookie："+cookie);
		//header.put("Origin", Consts.PROTOCOL + Consts.LIBHOST);
		//header.put("Referer", ssoLoginUrl + "?service=" + libLoginUrl);
		header.put("Host", Consts.LIBHOST);
		header.put("cookie", cookie);
		header.put("upgrade-insecure-requests", "1");
		String htmlText = HttpUtil.doGet(searchBookUrl, header, null).getContent();
		return htmlText;


//		CloseableHttpResponse response = null;
//		HttpGet httpGet = null;
//		String res = "";
//		String url = "http://10.11.35.23:8080/reader/book_lst.php";
//		httpGet = new HttpGet(url);
//		response = httpClient.execute(httpGet);
//		res = Consts.printResponse(response);
//		return res;	
	}

	private String crawlBookInfo(String key) throws Exception {
		System.out.println(key);
		String searchBookUrl = Consts.PROTOCOL + Consts.LIBHOST +"/opac/openlink.php";
		Map<String, String> params = new LinkedHashMap<>();
		params.put("strSearchType", "title");
		params.put("match_flag", "forward");
		params.put("historyCount", "1");
		params.put("strText", URLEncoder.encode(key,"utf-8"));
		params.put("doctype", "ALL");
		params.put("displaypg", "20");
		params.put("showmode", "list");
		params.put("sort", "CATA_DATE");
		params.put("orderby", "desc");
		params.put("location", "ALL");
		params.put("csrf_token", "IYJm-iw=(j");
		String htmlText = HttpUtil.doGet(searchBookUrl, null, params).getContent();
		return htmlText;
	}
}
