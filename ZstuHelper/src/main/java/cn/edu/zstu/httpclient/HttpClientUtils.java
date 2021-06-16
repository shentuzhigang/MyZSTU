package cn.edu.zstu.httpclient;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.edu.zstu.Consts;

import java.io.IOException;
import java.util.Map;

/**
 * Http/Https请求的工具类
 */
public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送post请求
     *
     * @param url:请求地址
     * @param header:请求头参数
     * @param params:表单参数  form提交
     * @param httpEntity   json/xml参数
     * @return
     */
    public static String doPostRequest(String url, Map<String, String> header, HttpEntity httpEntity) {
    	String resultStr = "";
        if (url == null || "".equals(url)) {
            return resultStr;
        }
        
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        
        try {
            httpClient = SSLClientCustom.getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            
            if (!(header == null || header.isEmpty())) {
                for (Map.Entry<String, String> headerEntry : header.entrySet()) {
                    httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            
            if (httpEntity != null) {
                httpPost.setEntity(httpEntity);
            }

            httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                resultStr = EntityUtils.toString(httpResponseEntity);
                //logger.info("POST请求正常,请求地址:{},响应结果:{}", url, resultStr);
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                HeaderIterator headerIterator = httpResponse.headerIterator();
                while (headerIterator.hasNext()) {
                    stringBuffer.append("\t" + headerIterator.next());
                }
                //logger.info("POST请求异常信息:请求地址:{},响应状态:{},请求返回结果:{}", url, httpResponse.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeConnection(httpClient, httpResponse);
        }
        return resultStr;
    }

    public static String doGetRequest(String url, Map<String, String> header, Map<String, String> params) {
    	String resultStr = "";
        if (StringUtils.isEmpty(url)) {
            return resultStr;
        }
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = SSLClientCustom.getHttpClient();

            if (MapUtils.isNotEmpty(params)) {
                url = url + buildUrl(params);
            }
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(false).build();
            httpGet.setConfig(requestConfig);
            
            if (MapUtils.isNotEmpty(header)) {
                for (Map.Entry<String, String> stringStringEntry : header.entrySet()) {
                    httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            //发起请求
            httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                resultStr = EntityUtils.toString(httpResponse.getEntity(), Consts.ENCODING);
                //logger.info("GET请求正常,请求地址:{},响应结果:{}", url);
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                HeaderIterator headerIterator = httpResponse.headerIterator();
                while (headerIterator.hasNext()) {
                    stringBuffer.append("\t" + headerIterator.next());
                }
                //logger.info("GET请求异常信息:请求响应状态:{},请求返回结果:{}", httpResponse.getStatusLine().getStatusCode(), stringBuffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeConnection(httpClient, httpResponse);
        }
        return resultStr;
    }

    /**
     * 关掉连接释放资源
     */
    private static void closeConnection(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
/**
 * 构造get请求的参数
 *
 * @return
 */
	private static String buildUrl(Map<String, String> map) {
	    if (MapUtils.isEmpty(map)) {
	        return "";
	    }
	    StringBuffer stringBuffer = new StringBuffer("?");
	    for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
	        stringBuffer.append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
	    }
	    String result = stringBuffer.toString();
	    if (!StringUtils.isEmpty(result)) {
	        result = result.substring(0, result.length() - 1);//去掉结尾的&连接符
	    }
	    return result;
	}
}

