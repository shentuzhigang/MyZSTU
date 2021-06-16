package club.zstuca.myzstu.provider;

import club.zstuca.myzstu.dto.wechat.WeChatSession;
import club.zstuca.myzstu.utils.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 10:59
 * <p>
 * 微信服务端接口
 */
@Component
public class WeChatProvider {
    private final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";
    @Value("${wx.login.appid}")
    private String APPID;
    @Value("${wx.login.secret}")
    private String SECRET;
    @Value("${wx.login.grant-type}")
    private String grantType;

    public String getOpenId(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("secret", SECRET);
        params.put("js_code", code);
        params.put("grant_type", grantType);
        String response = null;
        try {
            response = HttpUtil.doGet(WX_URL,params).getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WeChatSession weChatSession = JSONObject.parseObject(response, WeChatSession.class);
        if (weChatSession.getErrcode() == null || weChatSession.getErrcode() == 0) {
            return weChatSession.getOpenid();
        }
        return null;
    }
}
