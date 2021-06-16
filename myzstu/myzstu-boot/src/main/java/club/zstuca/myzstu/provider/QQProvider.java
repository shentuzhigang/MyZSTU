package club.zstuca.myzstu.provider;

import club.zstuca.myzstu.dto.qq.QQSession;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-18 17:58
 */
@Component
public class QQProvider {

    private static String QQQUN_JOIN_URL = "https://qun.qq.com/join.html";
    private static String QQQUN_GET_WPA_URL = "https://qun.qq.com/proxy/domain/shang.qq.com/wpa/g_wpa_get";
    private static String QQQUN_SET_WPA_URL = "https://qun.qq.com/proxy/domain/shang.qq.com/wpa/g_wpa_set";
    private static String ADDQUN_URL = "//shang.qq.com/wpa/qunwpa?idkey=";
    private final String QQ_URL = "https://api.q.qq.com/sns/jscode2session";
    @Value("${qq.login.appid}")
    private String APPID;
    @Value("${qq.login.secret}")
    private String SECRET;
    @Value("${qq.login.grant-type}")
    private String grantType;

    public static Map<String, String> getQQqunWPAs(List<String> guins) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", QQQUN_JOIN_URL);
        Map<String, String> params = new HashMap<>();
        params.put("t", "" + System.currentTimeMillis());
        String guin_str = "";
        if (guins != null) {
            for (String guin : guins) {
                if (!guin_str.equals("")) {
                    guin_str = guin_str + ",";
                }
                guin_str = guin_str + guin;
            }
        }
        params.put("guin", guin_str);
        Map<String, String> guinANDkey = new HashMap<>();
        String response;
        try {
            response = HttpUtil.doPost(QQQUN_GET_WPA_URL, headers, params).getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return guinANDkey;
        }
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("retcode").equals("0")) {
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray data = result.getJSONArray("data");
                    for (int i = 0; i < data.length(); ++i) {
                        JSONObject item = (JSONObject) data.get(i);
                        guinANDkey.put(item.getString("guin"),
                                item.getString("key"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return guinANDkey;
            }
        }

        return guinANDkey;
    }

    public static String getQQqunWPA(String qunId) {
        List<String> list = new ArrayList<>();
        list.add(qunId);
        return getQQqunWPAs(list).get(qunId);
    }

    public static String idkeyToUrl(String idkey) {
        return ADDQUN_URL + idkey;
    }

    public static boolean setQQqunWPA(String qunId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", QQQUN_JOIN_URL);
        Map<String, String> params = new HashMap<>();
        params.put("flag", "1");
        params.put("guin", qunId);
        String Response;
        try {
            Response = HttpUtil.doPost(QQQUN_SET_WPA_URL, headers, params).getContent();
            System.out.println(Response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getOpenId(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("secret", SECRET);
        params.put("js_code", code);
        params.put("grant_type", grantType);
        String response = null;
        try {
            response = HttpUtil.doGet(QQ_URL, params).getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        QQSession qqSession = com.alibaba.fastjson.JSONObject.parseObject(response, QQSession.class);
        if (qqSession.getErrcode() == null || qqSession.getErrcode() == 0) {
            return qqSession.getOpenid();
        }
        return null;
    }
}
