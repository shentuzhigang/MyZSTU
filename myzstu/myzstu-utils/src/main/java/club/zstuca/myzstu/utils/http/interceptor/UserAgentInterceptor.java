package club.zstuca.myzstu.utils.http.interceptor;

import club.zstuca.myzstu.utils.http.Constants;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-16 15:45
 */
public class UserAgentInterceptor implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        request.addHeader("User-Agent", Constants.AGENT);
    }
}
