package club.zstuca.myzstu.spyder.edu;

import club.zstuca.myzstu.utils.captcha.CaptchaUtil;
import club.zstuca.myzstu.utils.crypto.RSAUtil;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 13:50
 */
@Component
public class EduLoginer {
    //登录URL
    private final String LOGIN_URL = "http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
    //RSA URL
    private final String PUBLICKEY_URL = "http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=";
    //验证码 URL
    private final String CAPTCHA_URL = "http://jwglxt.zstu.edu.cn/jwglxt/kaptcha?time=";
    //退出URL
    private final String LOGOUT_URL = "http://jwglxt.zstu.edu.cn/jwglxt/logout?login_type=&t=";

    /**
     * 密码加密 RSA
     *
     * @param password
     * @return
     */
    private String encryp(String password) throws Exception {
        //一、获取 exponent modulus 生成公钥
        String exponent = null, modulus = null;
        String emJson = HttpUtil.doGet(PUBLICKEY_URL + System.currentTimeMillis()).getContent();
        JSONObject jsonObject = new JSONObject(emJson);
        exponent = jsonObject.getString("exponent");
        modulus = jsonObject.getString("modulus");
        //二、根据公钥进行密码加密
        password = RSAUtil.encryptByRSAPublicKeySpec(password, modulus, exponent);
        return password;
    }

    /**
     * 获取Token
     *
     * @param timestamp
     * @return
     */
    private String crawlCsrfToken(String timestamp) throws Exception {
        String s = HttpUtil
                .doGet("http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_slogin.html").getContent();
        Document csrftokenDoc = Jsoup.parse(s);
        String csrftoken = csrftokenDoc
                .select("#csrftoken")
                .first()
                .attr("value");
        return csrftoken;
    }

    private String crawlCaptcha(String username, String password) throws Exception {
        //访问验证码页面
        String code_url = CAPTCHA_URL + System.currentTimeMillis();
        String imgUrl = ResourceUtils.getURL("classpath:static").getPath() + File.separator + "codeimg" + File.separator;
        imgUrl += username + "-" + System.currentTimeMillis() + ".jpg";
        if (HttpUtil.download(code_url, imgUrl)) {
            return CaptchaUtil.tess(imgUrl);
        } else {
            throw new RuntimeException("下载错误");
        }
    }

    /**
     * 模拟登录
     *
     * @param stu
     * @return
     */
    public boolean login(String username, String password) throws Exception {
        String timestamp = "" + System.currentTimeMillis();
        String csrftoken = null;
        String captcha = null;
        try {
            csrftoken = this.crawlCsrfToken(timestamp);
            password = this.encryp(password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("205|登录教务系统错误");
        }
//        try {
//            captcha=this.crawlCaptcha(stu);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new StudentException("205|登录教务系统错误");
//        }
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("csrftoken", csrftoken);
        loginParams.put("yhm", username);
        loginParams.put("mm", password);
        loginParams.put("mm", password);
        //loginParams.put("yzm",captcha);
        String response = HttpUtil
                .doPost(LOGIN_URL + timestamp,
                        null, loginParams).getContent();
//        String pattern = ".*用户名或密码不正确，请重新输入！.*";
//        String patternCaptcha = ".*验证码输入错误！.*";

//        boolean isLogin = ||;
//
//                //(Pattern.matches(pattern, response) || Pattern.matches(patternCaptcha,response));
//        if(isLogin){
//            //System.out.println(response);
//            throw new StudentException("205|登录教务系统错误");
//        }
        if (response.contains("用户名或密码不正确，请重新输入！")) {
            throw new RuntimeException("203|用户名或密码不正确！");
        } else if (response.contains("验证码输入错误！")) {
            throw new RuntimeException("204|验证码识别错误！");
        }

        return true;
    }

    /**
     * 退出
     *
     * @return
     */
    public boolean logout() throws Exception {
        HttpUtil.doGet(LOGOUT_URL + System.currentTimeMillis());
        return true;
    }
}
