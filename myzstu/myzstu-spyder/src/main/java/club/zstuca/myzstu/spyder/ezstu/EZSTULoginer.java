package club.zstuca.myzstu.spyder.ezstu;


import club.zstuca.myzstu.utils.http.HttpContext;
import club.zstuca.myzstu.utils.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 11:10
 */
public class EZSTULoginer {
    private final static String URL = "http://stu.zstu.edu.cn/webroot/decision/login/cross/domain";

    public boolean login(String username, String password) throws Exception {
        String url = URL + "?fine_username=" + username + "&fine_password=" + password + "&validity=" + -1;
        String jsonp = HttpUtil.doGet(url, null, null).getContent();
        System.out.println(jsonp);
        JSONPObject parse = new JSONPObject(jsonp);
        //JSONObject jsonObject = null;
        JSONSerializer jsonSerializer = new JSONSerializer();
        parse.write(jsonSerializer, "callback", null, 0);
        System.out.println(parse);
        ObjectSerializer objectWriter = jsonSerializer.getObjectWriter(CallBack.class);
        //System.out.println(((CallBack)objectWriter).getErrorCode());
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex + 1, endIndex);
        CallBack callBack = JSONObject.parseObject(json, CallBack.class);
        System.out.println(callBack);

        return true;
    }

    public boolean login(String username, String password, HttpContext httpContext) throws Exception {
        String url = URL + "?fine_username=" + username + "&fine_password=" + password + "&validity=" + -1;
        String jsonp = httpContext.doGet(url).getContent();
        System.out.println(jsonp);
        JSONPObject parse = new JSONPObject(jsonp);
        //JSONObject jsonObject = null;
        JSONSerializer jsonSerializer = new JSONSerializer();
        parse.write(jsonSerializer, "callback", null, 0);
        System.out.println(parse);
        ObjectSerializer objectWriter = jsonSerializer.getObjectWriter(CallBack.class);
        //System.out.println(((CallBack)objectWriter).getErrorCode());
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex + 1, endIndex);
        CallBack callBack = JSONObject.parseObject(json, CallBack.class);
        System.out.println(callBack);

        return true;
    }


}

