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

import java.util.HashMap;
import java.util.Map;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 20:45
 */
public class AutoHealthDeclarationServiceImpl {
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

        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Consts.AGENT);
        header.put("Host", Consts.EZLHOST);
        header.put("Cookie", "fine_auth_token=" + callBack.getAccessToken());
        String reportUrl = VIEW_REPORT + "?op=h5_write" +
                "&viewlet=/yewubanli/健康申报.cpt";
        String htmlText = httpContext.doGet(reportUrl, header, null).getContent();
        System.out.println(htmlText);
        Document doc = Jsoup.parse(htmlText, "UTF-8");
        Element element = doc.select("script").get(3);
        sessionid = element.toString().split("get")[7].split("'")[1];
        header.put("sessionID", sessionid);
        header.put("Referer", reportUrl);


        Map<String, String> firstdataParams = new HashMap<>();
        firstdataParams.put("op", "h5_write");
        firstdataParams.put("cmd", "firstdata");
        firstdataParams.put("__parameters__", "%7B%7D");
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

        Map<String, String> reportParams1 = new HashMap<>();
        reportParams1.put("op", "fr_write");
        reportParams1.put("cmd", "cal_write_cell");
        reportParams1.put("editcol", "3");
        reportParams1.put("editrow", "11");
        reportParams1.put("editReportIndex", "0");
        reportParams1.put("sessionID", sessionid);
        reportParams1.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3CC%20c%3D%223%22%20r%3D%2211%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        reportParams1.put("loadidxs", "%5B0%5D");
        System.out.println(httpContext.doGet(VIEW_REPORT, header, reportParams1).getContent());

        Map<String, String> reportParams2 = new HashMap<>();
        reportParams2.put("op", "fr_write");
        reportParams2.put("cmd", "cal_write_cell");
        reportParams2.put("editcol", "3");
        reportParams2.put("editrow", "11");
        reportParams2.put("editReportIndex", "0");
        reportParams2.put("sessionID", sessionid);
        reportParams2.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3CC%20c%3D%223%22%20r%3D%2212%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        reportParams2.put("loadidxs", "%5B0%5D");
        System.out.println(httpContext.doGet(VIEW_REPORT, header, reportParams2).getContent());

        Map<String, String> verifyParams = new HashMap<>();
        verifyParams.put("op", "fr_write");
        verifyParams.put("cmd", "write_verify");
        verifyParams.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3CC%20c%3D%223%22%20r%3D%2211%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3CC%20c%3D%223%22%20r%3D%2212%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        verifyParams.put("cutPage", " ");
        verifyParams.put("sessionID", sessionid);
        System.out.println(httpContext.doPost(VIEW_REPORT, header, null, verifyParams));


        Map<String, String> submitParams = new HashMap<>();
        submitParams.put("op", "fr_write");
        submitParams.put("cmd", "submit_w_report");
        submitParams.put("reportXML", "%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20%3F%3E%3CWorkBook%3E%3CVersion%3E6.5%3C%2FVersion%3E%3CReport%20class%3D%22com.fr.report.WorkSheet%22%20name%3D%220%22%3E%3CCellElementList%3E%3CC%20c%3D%223%22%20r%3D%2211%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3CC%20c%3D%223%22%20r%3D%2212%22%3E%3CO%20t%3D%22S%22%3E%3C!%5BCDATA%5B37%E5%BA%A6%E4%BB%A5%E4%B8%8B%5D%5D%3E%3C%2FO%3E%3C%2FC%3E%3C%2FCellElementList%3E%3C%2FReport%3E%3C%2FWorkBook%3E");
        System.out.println(httpContext.doPost(VIEW_REPORT, header, null, submitParams));
        return true;
    }

    public Boolean student(Student student) throws Exception {
        return autoService(student.getUsername(), student.getEcardPw());
    }
}
