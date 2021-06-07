package club.zstuca.myzstu.spyder.ezstu;

import club.zstuca.myzstu.spyder.Consts;
import club.zstuca.myzstu.spyder.ezstu.entity.Student;
import club.zstuca.myzstu.utils.http.HttpContext;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-08-01 23:26
 */
@Component
public class AutoBackToDormitorySignServiceImpl {
    private static final String EZSTU_DECISION = Consts.PROTOCOL + Consts.EZLHOST + "/webroot/decision";
    private static final String DOMAIN_CROSS_LOGIN_IN_URL = EZSTU_DECISION + "/login/cross/domain";
    private static final String VIEW_REPORT = EZSTU_DECISION + "/view/report";
    private static final String MOBOILE_VIEW = EZSTU_DECISION + "/url/mobile/view";

    public Boolean autoService(String username, String password) throws Exception {
        HttpContext httpContext = new HttpContext();
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("fine_username", username);
        loginParams.put("fine_password", password);
        loginParams.put("validity", "-1");
        String jsonp = httpContext.doGet(DOMAIN_CROSS_LOGIN_IN_URL, loginParams).getContent();
        JSONPObject parse = new JSONPObject(jsonp);
        JSONSerializer jsonSerializer = new JSONSerializer();
        parse.write(jsonSerializer, "callback", null, 0);
        ObjectSerializer objectWriter = jsonSerializer.getObjectWriter(CallBack.class);
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex + 1, endIndex);
        CallBack callBack = JSONObject.parseObject(json, CallBack.class);
        System.out.println(callBack);


        String sessionid = "";
        Time time = new Time(System.currentTimeMillis());
        System.out.println(time.getTime());
        if ((time.getTime() + 28800000) % 86400000L <= 75600000L) {
            time.setTime((new Random().nextInt(1800000)) + 46800000);
        }
        Float jingdu = (float) (120.360 + 0.001 * Math.random());
        Float weidu = (float) (30.316 + 0.001 * Math.random());


        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Consts.AGENT);
        header.put("Host", Consts.EZLHOST);
        header.put("Cookie", "fine_auth_token=" + callBack.getAccessToken());
        String reportUrl = VIEW_REPORT + "?op=h5_write" +
                "&viewlet=2017/baodaocheck_enter.cpt" +
                "&time=" + time +
                "&jingdu=" + jingdu +
                "&weidu=" + weidu;
        String htmlText = httpContext.doGet(reportUrl, header, null).getContent();
        System.out.println(htmlText);
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Element element = doc.select("script").get(3);
        sessionid = element.toString().split("get")[7].split("'")[1];
        header.put("sessionID", sessionid);
        header.put("Referer", reportUrl);


        Map<String, String> firstdataParams = new HashMap<>();
        firstdataParams.put("op", "h5_template");
        firstdataParams.put("time", time.toString());
        firstdataParams.put("jingdu", jingdu.toString());
        firstdataParams.put("weidu", weidu.toString());
        firstdataParams.put("cmd", "firstdata");
        firstdataParams.put("__parameters__", "%257B%257D");
        firstdataParams.put("sessionID", sessionid);
        System.out.println(httpContext.doGet(MOBOILE_VIEW + "/firstdata", header, firstdataParams));


        Map<String, String> writeParams = new HashMap<>();
        writeParams.put("op", "fr_write");
        writeParams.put("cmd", "read_by_json");
        writeParams.put("toVanCharts", "true");
        writeParams.put("dynamicHyperlink", "true");
        writeParams.put("sessionID", sessionid);
        writeParams.put("reportIndex", "0");
        writeParams.put("pn", "1");
        writeParams.put("__cutpage__", "");
        writeParams.put("fine_api_v_json", "1");
        System.out.println(httpContext.doGet(VIEW_REPORT, header, writeParams).getContent());


        Map<String, String> verifyParams = new HashMap<>();
        verifyParams.put("op", "fr_write");
        verifyParams.put("cmd", "write_verify");
        verifyParams.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        verifyParams.put("cutPage", " ");
        verifyParams.put("sessionID", sessionid);
        System.out.println(httpContext.doPost(VIEW_REPORT, header, null, verifyParams));


        Map<String, String> submitParams = new HashMap<>();
        submitParams.put("op", "fr_write");
        submitParams.put("cmd", "submit_w_report");
        submitParams.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        System.out.println(httpContext.doPost(VIEW_REPORT, header, null, submitParams));
        return true;
    }

    public Boolean student(Student student) throws Exception {
        return autoService(student.getUsername(), student.getEcardPw());
    }
}
