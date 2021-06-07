package club.zstuca.myzstu.spyder.ecard;

import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.utils.captcha.CaptchaUtil;
import club.zstuca.myzstu.utils.http.HttpResponse;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//一卡通登录
@Component
public class EcardLoginer {
    private static final String EcardLoginUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/login.aspx";

    public Map<String, Object> login(String username, String password) throws Exception {
        Map<String, Object> res = new HashMap<>();
        //String cookie = "";
        res.put("loginFlag", false);
        res.put("cookie", "");

        //访问登录页面获取viewstate 和 eventvalidation的值
        Map<String, String> header1 = new HashMap<>();
        header1.put("Host", Consts.YKTHOST);
        String htmlText1 = HttpUtil.doGet(EcardLoginUrl, header1, null).getContent();
        Map<String, String> keys = parseYktViewstateAndEventValidation0(htmlText1);

        //访问验证码页面
        String code_url = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/validateimage.ashx?";
        String imgUrl = ResourceUtils.getURL("classpath:static").getPath() + File.separator + "codeimg" + File.separator;
        imgUrl += username + "-" + System.currentTimeMillis() + ".jpg";
        if (HttpUtil.download(code_url, imgUrl)) {
            keys.put("vaildateCode", CaptchaUtil.tess(imgUrl));
        } else {
            keys.put("vaildateCode", "");
        }
        System.out.println(keys);
        //访问登录页面，进行登录
//		HttpPost httpPost = new HttpPost(EcardLoginUrl);
        Map<String, String> header3 = new HashMap<>();
        header3.put("User-Agent", Consts.AGENT);
        header3.put("Origin", Consts.PROTOCOL + Consts.EDUHOST);
        header3.put("Referer", EcardLoginUrl);
        header3.put("Host", Consts.YKTHOST);
//		//header3.put("Cookie", cookie);
//		//System.out.println(cookie);
//		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(inputLoginForm(stu, keys), Consts.ENCODING);
//		for (Map.Entry<String, String> headerEntry : header3.entrySet()) {
//            httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
//        }
//		//System.out.println(inputLoginForm(stu, keys));
//		httpPost.setEntity(postEntity);
//		//System.out.println(httpPost);
//		httpResponse = httpClient.execute(httpPost);

		//请求信息填充
		Map<String, String> formData = new HashMap<>();
		formData.put("__LASTFOCUS", "");
		formData.put("__EVENTTARGET", "btnLogin");
		formData.put("__EVENTARGUMENT", "");
		formData.put("__VIEWSTATE", keys.get("viewstate"));
		formData.put("__EVENTVALIDATION", keys.get("eventvalidation"));
		formData.put("txtUserName", username);
		formData.put("txtPassword", password);
		formData.put("txtVaildateCode", keys.get("vaildateCode"));
		formData.put("hfIsManager", "0");

        HttpResponse response = HttpUtil.doPost(EcardLoginUrl, header3, keys);
//        HttpEntity httpResponseEntity = httpResponse.getEntity();
        int statusCode = response.getStatusCode();
        if (statusCode == 302 || statusCode == 301) {
            res.put("loginFlag", true);
            res.put("cookie", "");
        } else {
            //String htmlText3 = EntityUtils.toString(httpResponseEntity);
            //System.out.println(htmlText3);
            Document doc = Jsoup.parse(response.getContent(), "UTF-8");
            Elements elements = doc.select(".loginerror");
            System.out.println(elements);
            if (elements.toString().contains("验证码错误")) {
                throw new RuntimeException("202|验证码错误");
            } else if (elements.toString().contains("密码错误")) {
                throw new RuntimeException("201|学号或密码错误");
            } else if (elements.toString().contains("输入的密码有误！")) {
                throw new RuntimeException("201|学号或密码错误");
            }
        }
        return res;
    }

    Pattern p = Pattern.compile("^<input.*value=\"(.+)\".*$");

    /**
     * 解析获取viewstate 和 eventvalidation的值
     *
     * @param htmlText
     * @return
     */
    private Map<String, String> parseYktViewstateAndEventValidation0(String htmlText) {
        Map<String, String> res = new HashMap<String, String>();
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements elements = doc.select("#form2 input[name=__VIEWSTATE]");
        String str = elements.toString();
        Elements elements2 = doc.select("#__EVENTVALIDATION");
        String str2 = elements2.toString();
        Matcher m = p.matcher(str);
        Matcher m2 = p.matcher(str2);
        if (m.find()) {
            res.put("viewstate", m.group(1));
        }
        if (m2.find()) {
            res.put("eventvalidation", m2.group(1));
        }
        return res;
    }

}
