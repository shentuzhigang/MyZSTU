package club.zstuca.myzstu.spyder.ecard;

import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.spyder.ecard.entity.Consumption;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//一卡通爬虫
@Component
public class EcardSpyder {

    @Autowired
    private EcardLoginer loginer;

    @Autowired
    private EcardParser ecardParser;

    static Pattern p = Pattern.compile("^<input.*value=\"(.+)\".*$");

    //解析获取viewstate和eventvalidation的值
    public static Map<String, String> parseYktViewstateAndEventValidation(String htmlText) {
        Map<String, String> res = new HashMap<String, String>();
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements elements = doc.select("#form1 input[name=__VIEWSTATE]");
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

    //登录一卡通获取校园卡基本信息和消费明细
    public Map<String, Object> getCardInfo(String username, String password, String start, String end) throws Exception {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> baseInfo = new HashMap<>();
        List<Consumption> consumptionList = new ArrayList<>();
        try {
            Map<String, Object> loginRes = loginer.login(username, password);
            System.out.println(loginRes);
            String cookie = (String) loginRes.get("cookie");
            if ((boolean) loginRes.get("loginFlag")) {
                baseInfo = crawlAndParseBaseInfo(cookie);
                consumptionList = crawlAndParseConsumption(cookie, start, end);
            }
        } catch (Exception e) {
            throw e;
        }
        res.put("sid", (String) baseInfo.get("sid"));
        res.put("sname", (String) baseInfo.get("sname"));
        res.put("balance", (Double) baseInfo.get("balance"));
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
        //header.put("Cookie", cookie);
        String htmlText = HttpUtil.doGet(balanceUrl, header, null).getContent();
        baseInfo = YktParser.parseBalance(htmlText);
        return baseInfo;
    }

    //解析获取消费明细
    private List<Consumption> crawlAndParseConsumption(String cookie, String start, String end)
            throws Exception {
        List<Consumption> list = new ArrayList<Consumption>();
        String consumptionUrl = Consts.PROTOCOL + Consts.YKTHOST + "/SelfSearch/User/ConsumeInfo.aspx";
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Consts.AGENT);
        header.put("Origin", Consts.PROTOCOL + Consts.YKTHOST);
        header.put("Host", Consts.YKTHOST);
        header.put("Referer", consumptionUrl);
        //header.put("Cookie", cookie);
        String htmlText = HttpUtil.doPost(consumptionUrl, header, null).getContent();
        Map<String, String> keys = YktParser.parseYktViewstateAndEventValidation(htmlText);
        Map<String, String> params = getYktPagePostData(keys.get("viewstate"), keys.get("eventvalidation"), start, end);
        htmlText = HttpUtil.doPost(consumptionUrl, header, params).getContent();
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
        //header.put("Cookie", cookie);
        Map<String, String> dataBody = inputConsumptionForm(page, viewstate, eventvalidation, start, end);
        String htmlText = HttpUtil.doPost(consumptionUrl, header, dataBody).getContent();
        return htmlText;
    }

    //请求信息填充
    private Map<String, String> inputConsumptionForm(int page, String viewstate, String eventvalidation, String start, String end) {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("__EVENTTARGET", "ctl00$ContentPlaceHolder1$AspNetPager1");
        pairs.put("__EVENTARGUMENT", String.valueOf(page));
        pairs.put("__VIEWSTATE", viewstate);
        pairs.put("__EVENTVALIDATION", eventvalidation);
        pairs.put("ctl00$ContentPlaceHolder1$rbtnType", "0");
        pairs.put("ctl00$ContentPlaceHolder1$txtStartDate", start);
        pairs.put("ctl00$ContentPlaceHolder1$txtEndDate", end);
        return pairs;
    }

    //请求信息填充
    private Map<String, String> getYktPagePostData(String viewstate, String eventvalidation, String start, String end) {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("__EVENTTARGET", "");
        pairs.put("__EVENTARGUMENT", "");
        pairs.put("__VIEWSTATE", viewstate);
        pairs.put("__EVENTVALIDATION", eventvalidation);
        pairs.put("ctl00$ContentPlaceHolder1$rbtnType", "0");
        pairs.put("ctl00$ContentPlaceHolder1$txtStartDate", start);
        pairs.put("ctl00$ContentPlaceHolder1$txtEndDate", end);
        pairs.put("ctl00$ContentPlaceHolder1$btnSearch", "查  询");
        return pairs;
    }
}
