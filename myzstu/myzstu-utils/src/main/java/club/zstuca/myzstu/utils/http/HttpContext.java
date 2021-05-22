package club.zstuca.myzstu.utils.http;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.Date;
import java.util.Map;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-07-19 10:06
 */
public class HttpContext {
    private HttpClientContext httpClientContext;

    public HttpContext() {
        CookieStore cookieStore = new BasicCookieStore();
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
    }

    public HttpContext(Map<String, String> cookies) {
        if (MapUtils.isNotEmpty(cookies)) {
            CookieStore cookieStore = new BasicCookieStore();
            httpClientContext = HttpClientContext.create();
            // Set params
            for (Map.Entry<String, String> cookieMap : cookies.entrySet()) {
                //  发送自定义cookie：（new了一个对象之后可以设置多种属性。）
                BasicClientCookie cookie = new BasicClientCookie(
                        cookieMap.getKey(),
                        cookieMap.getValue());
                // new a cookie
                cookie.setDomain("domain");
                cookie.setExpiryDate(new Date());
                // set the properties of the cookie
                cookieStore.addCookie(cookie);
            }
            httpClientContext.setCookieStore(cookieStore);
        } else {
            new HttpContext();
        }

    }

    /**
     * 发送get请求；核心方法
     */
    public HttpResponse doGet(String url,
                              Map<String, String> headers,
                              Map<String, String> params) throws Exception {
        return HttpUtil.doGetRequest(url, headers, params, httpClientContext);
    }


    /**
     * 发送Post请求；核心方法
     */
    public HttpResponse doPost(String url,
                               Map<String, String> headers,
                               Map<String, String> params,
                               Map<String, String> formData) throws Exception {
        return HttpUtil.doPostRequest(url, headers, params, formData, httpClientContext);
    }

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @throws Exception
     */
    public HttpResponse doGet(String url)
            throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @throws Exception
     */
    public HttpResponse doGet(String url,
                              Map<String, String> params)
            throws Exception {
        return doGet(url, null, params);
    }


    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public HttpResponse doPost(String url)
            throws Exception {
        return doPost(url, null, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url      请求地址
     * @param fromData 参数集合
     * @return
     * @throws Exception
     */
    public HttpResponse doPost(String url,
                               Map<String, String> fromData)
            throws Exception {
        return doPost(url, null, null, fromData);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url      请求地址
     * @param params
     * @param fromData 参数集合
     * @return
     * @throws Exception
     */
    public HttpResponse doPost(String url,
                               Map<String, String> params,
                               Map<String, String> fromData)
            throws Exception {
        return doPost(url, null, params, fromData);
    }
}
