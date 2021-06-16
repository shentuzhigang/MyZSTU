package club.zstuca.myzstu.spyder.ezstu.query;

import club.zstuca.myzstu.spyder.Constants;
import club.zstuca.myzstu.spyder.ezstu.entity.Student;
import club.zstuca.myzstu.spyder.ezstu.CallBack;
import club.zstuca.myzstu.utils.http.HttpContext;
import club.zstuca.myzstu.utils.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.apache.commons.collections.map.HashedMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 15:19
 */
@Component
public class HealthDeclarationQuery {
    private static final String EZSTU_DECISION = Constants.PROTOCOL + Constants.EZLHOST + "/webroot/decision";
    private static final String DOMAIN_CROSS_LOGIN_IN_URL = EZSTU_DECISION + "/login/cross/domain";
    private static final String VIEW_REPORT = EZSTU_DECISION + "/view/report";
    private static final String MOBOILE_VIEW = EZSTU_DECISION + "/url/mobile/view";
    private static final String ezlUrl = Constants.PROTOCOL + Constants.EZLHOST + "/webroot/decision/view/report";
    private static final String paramUrl = Constants.PROTOCOL + Constants.EZLHOST + "/webroot/decision/view/form?op=fr_dialog&cmd=parameters_d";

    public List<Student> getUndeclaredStudentList(String xueyuan, String nianji, String banji, String riqi) throws Exception {
        HttpContext httpContext = new HttpContext();
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("fine_username", "2018329621200");
        loginParams.put("fine_password", "081639");
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


        Map<String, String> params = new HashedMap();
        params.put("__parameters__", "{" +
                "\"LABEL0\":\"[5b66][9662]\"," +
                "\"XUEYUAN\":\"" + xueyuan + "\"," +
                "\"LABEL1\":\"[5e74][7ea7]\"," +
                "\"NIANJI\":\"" + nianji + "\"," +
                "\"LABEL2\":\"[9009][62e9][65e5][671f]\"," +
                "\"RIQI\":[5b]\"" + riqi + "\"[5d]" +
                "}");


        String sessionID = "";

        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Constants.AGENT);
        header.put("Host", Constants.EZLHOST);
        header.put("Cookie", "fine_auth_token=" + callBack.getAccessToken());
        String reportUrl = VIEW_REPORT + "?op=h5_write" +
                "&viewlet=/yewubanli/疫情未上报名单.cpt";
        String htmlText1 = httpContext.doGet(reportUrl, header, null).getContent();
        System.out.println(htmlText1);
        Document doc = Jsoup.parse(htmlText1, "UTF-8");
        Element element = doc.select("script").get(3);
        sessionID = element.toString().split("get")[7].split("'")[1];
        header.put("sessionID", sessionID);
        header.put("Referer", reportUrl);


        header.put("User-Agent", Constants.AGENT);
        header.put("Host", Constants.EZLHOST);
        HttpResponse httpResponse = httpContext.doPost(
                paramUrl, header, params);
        String status = JSONObject.parseObject(httpResponse.getContent()).getString("status");
        if (!status.equals("success")) {
            throw new RuntimeException("E浙理报表参数提交错误");
        }
        String htmlText = httpContext.doPost(
                ezlUrl, header, inputGetForm(sessionID, "1")).getContent();
        JSONObject resjson = JSONObject.parseObject(htmlText);
        int pagenum = resjson.getInteger("reportTotalPage");
        List<Student> students = new ArrayList<>();
        for (Integer i = 1; i <= pagenum; i++) {
            htmlText = httpContext.doPost(
                    ezlUrl, header, inputGetForm(sessionID, i.toString())).getContent();
            resjson = JSONObject.parseObject(htmlText);
            JSONArray detailJson = resjson.getJSONObject("pageContent")
                    .getJSONArray("detail");
            JSONArray rows = detailJson.getJSONObject(0)
                    .getJSONObject("cellData")
                    .getJSONArray("rows");
            JSONArray rowHeight = detailJson.getJSONObject(0)
                    .getJSONObject("pageLayoutInfo")
                    .getJSONArray("rowHeight");
            for (int k = 1; k < rows.size(); k++) {
                JSONArray cells = rows.getJSONObject(k).getJSONArray("cells");
                if (cells.size() <= 2 || rowHeight.getInteger(k) == 0) {
                    continue;
                }
                if (cells.getJSONObject(2).getString("text").equals(banji)) {
                    Student student = new Student();
                    student.setId(cells.getJSONObject(0).getString("text"));
                    student.setName(cells.getJSONObject(1).getString("text"));
                    student.setStuClass(cells.getJSONObject(2).getString("text"));
                    students.add(student);
                }
            }
        }
        return students;
    }

    //填充请求信息
    private Map<String, String> inputGetForm(String sessionid, String pagenum) {
        Map<String, String> formData = new HashMap<>();
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
