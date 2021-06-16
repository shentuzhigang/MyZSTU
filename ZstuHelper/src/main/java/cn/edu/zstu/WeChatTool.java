package cn.edu.zstu;

import com.alibaba.fastjson.JSONObject;
import cn.edu.zstu.httpclient.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

public class WeChatTool {
	
	private static final String SECRET = "7aef30c38245290be24e28a15cd429f4";
	private static final String APPID = "wx62e4e19b3b761d52";
	private static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
    public static String getOpenId(String code) {
    	String openid = "";
    	Map<String, String> header = new HashMap<>();
		header.put("User-Agent", Consts.AGENT);
		Map<String, String> params = new HashMap<>();
		params.put("appid", APPID);
		params.put("secret", SECRET);
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		String response = HttpClientUtils.doGetRequest(WX_URL, header, params);
		openid = JSONObject.parseObject(response).getString("openid");
		return openid;    	
    }
}
