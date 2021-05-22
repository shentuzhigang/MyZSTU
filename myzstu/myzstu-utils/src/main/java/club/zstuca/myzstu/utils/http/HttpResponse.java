package club.zstuca.myzstu.utils.http;

import java.io.Serializable;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-07-12 09:52
 */
public class HttpResponse implements Serializable {
    /**
     * 响应状态码
     */
    private int statusCode;

    /**
     * 响应数据
     */
    private String content;

    public HttpResponse() {
        super();
    }

    public HttpResponse(int code) {
        super();
        this.statusCode = code;
    }

    public HttpResponse(String content) {
        super();
        this.content = content;
    }

    public HttpResponse(int code, String content) {
        super();
        this.statusCode = code;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HTTPResponse [code=" + statusCode + ", content=" + content + "]";
    }
}
