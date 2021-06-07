package club.zstuca.myzstu.spyder.ezstu;

import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 15:20
 */
public class FineReportUtil {

    public static String getSessionID(String url, String username, String viewlet) throws Exception {
        String sessionid = "";
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Consts.AGENT);
        header.put("Host", Consts.EZLHOST);
        Map<String, String> params = new HashMap<>();
        params.put("viewlet", viewlet);
        params.put("__pi__", "true");
        params.put("op", "h5");
        params.put("username", username);
        String htmlText = HttpUtil.doGet(url, header, params).getContent();
        System.out.println(htmlText);
        Document doc = Jsoup.parse(htmlText, "UTF-8");
//          Element element = doc
//                .select("script")
//                .get(3);
//        System.out.println(element);
//        sessionid = element
//                .toString()
//                .split("get")[7].split("'")[1];
        return sessionid;
    }


}
