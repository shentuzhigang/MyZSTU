package club.zstuca.myzstu;

import club.zstuca.myzstu.beans.Phone;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Constants {
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    //协议头
    public static final String PROTOCOL = "http://";

    //协议头
    public static final String PROTOCOLS = "https://";

    //浏览器代理
    public static final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

    //编码类型
    public static final String ENCODING = "UTF-8";

    //教务系统Host
    //public static final String EDUHOST = "course.zstu.edu.cn";
    public static final String EDUHOST = "10.11.247.52/jwglxt";

    //E浙理Host
    public static final String EZLHOST = "stu.zstu.edu.cn";

    //图书馆Host
    public static final String LIBHOST = "10.11.35.23:8080";

    //统一身份认证
    public static final String SSOHOST = "sso.zstu.edu.cn";

//	public static final String LIBHOST="10.11.35.23:8080";

    //一卡通Host
    public static final String YKTHOST = "ykt.zstu.edu.cn";

    public static Map<String, String> weekMap = new HashMap<String, String>();

    static {
        weekMap.put("一", "1");
        weekMap.put("二", "2");
        weekMap.put("三", "3");
        weekMap.put("四", "4");
        weekMap.put("五", "5");
        weekMap.put("六", "6");
        weekMap.put("日", "7");
    }

    //获取学期第一天（周一）
    public static String getFirstDay() throws IOException {
        String firstDay = "";
        Properties properties = new Properties();
        InputStream in = Constants.class.getClassLoader().getResourceAsStream("static/public.properties");
        properties.load(in);
        firstDay = properties.getProperty("firstday");
        return firstDay;
    }

    //获取常用电话
    public static List<Phone> getPhones() throws IOException {
        List<Phone> res = new LinkedList<Phone>();
        File phoneXml = new File(Constants.class.getClassLoader().getResource("static/phone.xml").getPath());
        Document doc = Jsoup.parse(phoneXml, "utf-8");
        Element life = doc.getElementsByTag("life").get(0);
        Elements phones = life.getElementsByTag("phone");
        addPhone(res, phones, "life");
        Element work = doc.getElementsByTag("work").get(0);
        phones = work.getElementsByTag("phone");
        addPhone(res, phones, "work");
        Element other = doc.getElementsByTag("other").get(0);
        phones = other.getElementsByTag("phone");
        addPhone(res, phones, "other");
        return res;
    }

    private static void addPhone(List<Phone> res, Elements phones, String type) {
        for (int i = 0; i < phones.size(); i++) {
            Element p = phones.get(i);
            String name = p.getElementsByTag("name").get(0).text();
            String number = p.getElementsByTag("number").get(0).text();
            Phone phone = new Phone();
            phone.setName(name);
            phone.setNumber(number);
            phone.setType(type);
            res.add(phone);
        }
    }

    //自动识别验证码
    public static String tess(String imgurl) throws Exception {
        //System.out.println("开始识别");
        String res = "";
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");
        Properties properties = new Properties();
        InputStream in = Constants.class.getClassLoader().getResourceAsStream("static/public.properties");
        properties.load(in);
        String dataPath = properties.getProperty("tessdatasrc");
        instance.setDatapath(dataPath);
        //System.out.println(dataPath);
        List<String> configs = new ArrayList<>();
        configs.add("digits");
        instance.setConfigs(configs);
        File newFile = new File(imgurl);
        try {
            res = instance.doOCR(newFile);
            if (newFile.exists()) {
                newFile.delete();
            }

        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return res;
    }
}
