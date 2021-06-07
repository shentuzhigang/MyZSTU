package club.zstuca.myzstu.spyder.sso;

import club.zstuca.myzstu.utils.crypto.DESUtil;
import club.zstuca.myzstu.utils.http.HttpContext;
import club.zstuca.myzstu.utils.http.HttpResponse;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-03-20 18:04
 */
@Component
public class SSOLoginer {
    private final String LOGIN_URL = "https://sso.zstu.edu.cn/login";
    private final String LOGOUT_URL = "https://sso.zstu.edu.cn/logout";
    private final String SUCCESS_MOVE_URL = "https://service.zstu.edu.cn/cas-success/index.html";
    public boolean login(String username, String password) throws Exception {
        return login(username,password,null);
    }
    public boolean login(String username, String password, HttpContext httpContext)
            throws Exception {
        HttpResponse preResponse;
        if(httpContext==null){
            preResponse = HttpUtil.doGet(LOGIN_URL);
        }else{
            preResponse = httpContext.doGet(LOGIN_URL);
            if(preResponse.getStatusCode()==302&&preResponse.getContent().equals(SUCCESS_MOVE_URL)){
                return true;
            }
        }
        Document doc = Jsoup.parse(preResponse.getContent());
        //System.out.println(doc);
        String currentLoginType = doc.select("#current-login-type").html();
        String loginCroypto = doc.select("#login-croypto").html();
        String ssoSecond = doc.select("#sso-second").html();
        String userId = doc.select("#user-id").html();
        String userObjectId = doc.select("#user-object-id").html();
        String lphoneNumber = doc.select("#phone-number").html();
        String loginRuleType = doc.select("#login-rule-type").html();
        String loginPageFlowKey = doc.select("#login-page-flowkey").html();
        String captchaUrl = doc.select("#captcha-url").html();
        String redirectUri = doc.select("#redirect-uri").html();
        String loginBackUri = doc.select("#login-back-uri").html();
        String loginErrorCode = doc.select("#login-error-code").html();
        String recaptchaInvisible = doc.select("#recaptcha-invisible").html();
        if("".equals(loginPageFlowKey)){
            logout();
            return false;
        }
        Map<String,String> params=new HashMap<>();
        params.put("username",username);
        params.put("type",currentLoginType);
        params.put("_eventId","submit");
        params.put("geolocation","");
        params.put("execution",loginPageFlowKey);
        params.put("captcha_code","");
        params.put("croypto",loginCroypto);
        params.put("password", DESUtil.encrypt(loginCroypto,password));
        HttpResponse httpResponse;
        if(httpContext == null){
            httpResponse = HttpUtil.doPost(LOGIN_URL, params);
        }else{
            httpResponse = httpContext.doPost(LOGIN_URL, params);
        }
        System.out.println(httpResponse);
        System.out.println(loginCroypto);
        System.out.println(DESUtil.encrypt(loginCroypto,password));
        System.out.println(httpResponse.getStatusCode());
        if(httpResponse.getStatusCode()==401){
            throw new SSOException("用户名或者密码错误");
        }
        return true;
    }
    public void logout()throws Exception {
        logout(null);
    }
    public void logout(HttpContext httpContext) throws Exception {
        if(httpContext == null){
            HttpUtil.doGet(LOGOUT_URL);
        }else{
            httpContext.doGet(LOGOUT_URL);
        }
    }
}
