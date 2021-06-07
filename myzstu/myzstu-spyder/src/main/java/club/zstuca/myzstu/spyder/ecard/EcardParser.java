package club.zstuca.myzstu.spyder.ecard;


import club.zstuca.myzstu.spyder.ecard.entity.Consumption;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


//一卡通解析
@Component
public class EcardParser {

    public List<Consumption> parse(String htmlText) {
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Elements elements = doc.select("#ContentPlaceHolder1_gridView tr");
        List<Consumption> res = new LinkedList<Consumption>();
        for (int i = 1; i < elements.size(); i++) {
            Elements tds = elements.get(i).select("td");
            try {
                String money = tds.get(2).text();
                money = new String(money.getBytes(), "GBK").replace("?", "").replace(" ", "").replace(" ", "");
                if (!money.equals("") && !money.equals("聽")) {
                    Consumption sump = new Consumption();
                    sump.setTime(tds.get(0).text());
                    sump.setContent(tds.get(1).text());
                    sump.setMoney(money);
                    sump.setBalance(tds.get(3).text());
                    sump.setWorkstation(tds.get(5).text());
                    sump.setPlace(tds.get(6).text());
                    res.add(sump);
                }
            } catch (Exception e) {
            }
        }
        return res;
    }

}
