package club.zstuca.myzstu.spyder.edu;

import club.zstuca.myzstu.spyder.sso.SSOLoginer;
import club.zstuca.myzstu.utils.http.HttpContext;
import club.zstuca.myzstu.utils.http.HttpResponse;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-13 23:36
 */
@Component
public class EduSSOLoginer {
    //登录URL
    private final String LOGIN_URL = "http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
    //退出URL
    private final String LOGOUT_URL = "http://jwglxt.zstu.edu.cn/jwglxt/logout?login_type=&t=";
    //成功页
    private final String LOGIN_SUCCESS_URL = "http://jwglxt.zstu.edu.cn/jwglxt/xtgl/index_initMenu.html";
    @Autowired
    private SSOLoginer ssoLoginer;
    private String SSO_EDU_URL = "https://sso.zstu.edu.cn/login?service=http%3A%2F%2Fjwglxt.zstu.edu.cn%2Fsso%2Fjasiglogin";

    public boolean login(String username, String password, HttpContext httpContext) throws Exception {
        if (ssoLoginer.login(username, password, httpContext)) {
            HttpResponse httpResponse;
            if (httpContext == null) {
                httpResponse = HttpUtil.doGet(SSO_EDU_URL);
            } else {
                httpResponse = httpContext.doGet(SSO_EDU_URL);
            }

            for (int i = 0; i < 6; i++) {
                if (httpResponse.getStatusCode() == 302) {
                    String location = httpResponse.getContent();
                    System.out.println(location);
                    if (httpContext == null) {
                        httpResponse = HttpUtil.doGet(location);
                    } else {
                        httpResponse = httpContext.doGet(location);
                    }
                } else {
                    break;
                }
            }
            return true;
        } else {
            throw new ZFSoftException("201|SSO密码错误");
        }
    }

    public boolean login(String username, String password) throws Exception {
        return login(username, password, null);
    }

    public void logout(HttpContext httpContext) throws Exception {
        if (httpContext == null) {
            HttpUtil.doGet(LOGOUT_URL + System.currentTimeMillis());
        } else {
            httpContext.doGet(LOGOUT_URL + System.currentTimeMillis());
        }
    }

    public void logout() throws Exception {
        logout(null);
    }
}
