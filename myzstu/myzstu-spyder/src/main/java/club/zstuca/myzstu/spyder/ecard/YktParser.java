package club.zstuca.myzstu.spyder.ecard;


import club.zstuca.myzstu.spyder.ecard.entity.Consumption;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//一卡通解析
public class YktParser {

    // 从一卡通首页解析饭卡基本信息（学号、姓名、余额）
    public static Map<String, Object> parseBalance(String htmlText) {
        Map<String, Object> baseInfo = new HashMap<>();
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements spans = doc.select("span");
        String sid = spans.get(2).text();
        String sname = spans.get(4).text();
        String money = spans.get(10).text().replace("主钱包余额：", "").replace("元", "");
        baseInfo.put("sid", sid);
        baseInfo.put("sname", sname);
        baseInfo.put("balance", Double.valueOf(money));
        return baseInfo;
    }

    // 从一卡通消费查询页解析消费信息总页数
    public static int parsePages(String htmlText) {
        int page = 0;
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements elements = doc.select("#ContentPlaceHolder1_AspNetPager1 a");
        if (elements.size() == 0) {
            //没有数据和只有一页数据时候 都按一页数据处理
            return 1;
        }
        String str = elements.get(elements.size() - 1).toString();
        Matcher m = Pattern.compile(".+__doPostBack\\((.+),(.+)\\).+").matcher(str);
        if (m.find()) {
            String p = m.group(2).replace("'", "").replace("'", "");
            page = Integer.parseInt(p);
        }
        return page;
    }

    // 从一卡通消费查询页解析消费信息总页数
    public static void parseConsumption(List<Consumption> res, String htmlText) {

        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements elements = doc.select("#ContentPlaceHolder1_gridView tr");
        if (elements.toString().contains("抱歉")) {
            return;
        }
        for (int i = 1; i < elements.size(); i++) {
            Elements tds = elements.get(i).select("td");
            String money = tds.get(2).text();
            try {
                money = new String(money.getBytes(), "GBK").replace("?", "").replace(" ", "").replace("聽", "");
                System.out.println("money=" + money);
                if (!money.equals("") && !money.equals("聽")) {
                    Consumption sump = new Consumption();
                    sump.setTime(tds.get(0).text());
                    sump.setContent(tds.get(1).text());
                    sump.setMoney(money);
                    sump.setBalance(tds.get(3).text());
                    sump.setWorkstation(tds.get(5).text());
                    res.add(sump);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 从一卡通消费查询页解析viewstate
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
