package club.zstuca.myzstu.utils.http;


import club.zstuca.myzstu.utils.core.StringUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-07-19 10:12
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String ENCODING = "UTF-8";
    private static final RequestConfig requestConfig;
    private static final int CONNECTION_REQUEST_TIMEOUT = 1000 * 10;
    private static final int CONNECT_TIMEOUT = 1000 * 10;
    private static final int SOCKET_TIMEOUT = 1000 * 10;
    private static final boolean REDIRECTS_ENABLED = false;

    /**
     * 静态代码块
     */
    static {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setRedirectsEnabled(REDIRECTS_ENABLED)
                .build();
    }

    /**
     * Set URI
     *
     * @param httpRequest
     * @param url
     * @param params
     * @throws URISyntaxException
     * @throws MalformedURLException
     */
    private static void setParams(HttpRequestBase httpRequest,
                                  String url,
                                  Map<String, String> params)
            throws URISyntaxException, MalformedURLException {
        URIBuilder urlbuilder = new URIBuilder(url);
        if (MapUtils.isNotEmpty(params)) {
            // Set params
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                urlbuilder.setParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }
        logger.info(urlbuilder.build().toURL().toString());
        httpRequest.setURI(urlbuilder.build());
    }

    /**
     * Set Header
     *
     * @param httpRequest 请求
     * @param headers     请求头数据
     */
    private static void setHeaders(HttpRequestBase httpRequest,
                                   Map<String, String> headers) {
        // 封装请求头
        if (MapUtils.isNotEmpty(headers)) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpRequest.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    /**
     * Description: 封装请求参数
     *
     * @param httpRequest 请求
     * @param type        参数类型
     * @param params      参数
     * @throws UnsupportedEncodingException
     */
    private static void setEntity(HttpEntityEnclosingRequest httpRequest,
                                  String type,
                                  Map<String, String> params)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 设置到请求的http对象中
            httpRequest.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Request
     *
     * @param url      URL
     * @param header   请求头参数
     * @param params   URL参数
     * @param formData 表单参数
     * @return HTTPResponse
     */
    protected static HttpResponse doRequest(String url,String method,
                                                Map<String, String> header,
                                                Map<String, String> params,
                                                Map<String, String> formData,
                                                HttpClientContext httpClientContext) {

        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;
        try {
            httpClient = HttpClientPool.getHttpClient();
            HttpRequestBase httpRequest = null;
            if(HttpGet.METHOD_NAME.equals(method)){
                httpRequest = new HttpGet();
            }else if(HttpPost.METHOD_NAME.equals(method)){
                httpRequest = new HttpPost();
            }else{
                throw new RuntimeException("不支持的请求类型");
            }
            httpRequest.setConfig(requestConfig);
            setParams(httpRequest, url, params);
            setHeaders(httpRequest, header);
            if(httpRequest instanceof HttpEntityEnclosingRequest){
                setEntity((HttpEntityEnclosingRequest) httpRequest, "form-data", formData);
            }
            //发起请求
            httpResponse = getResponse(httpClient, httpRequest, httpClientContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * Post Request
     *
     * @param url      URL
     * @param header   请求头参数
     * @param params   URL参数
     * @param formData 表单参数
     * @return HTTPResponse
     */
    protected static HttpResponse doPostRequest(String url,
                                                Map<String, String> header,
                                                Map<String, String> params,
                                                Map<String, String> formData,
                                                HttpClientContext httpClientContext) {
        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;
        try {
            httpClient = HttpClientPool.getHttpClient();
            HttpPost httpPost = new HttpPost();
            httpPost.setConfig(requestConfig);
            setParams(httpPost, url, params);
            setHeaders(httpPost, header);
            setEntity(httpPost, "form-data", formData);
            //发起请求
            httpResponse = getResponse(httpClient, httpPost, httpClientContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * Get Request
     *
     * @param url    URL
     * @param header 请求头参数
     * @param params URL参数
     * @return HTTPResponse
     */
    protected static HttpResponse doGetRequest(String url,
                                               Map<String, String> header,
                                               Map<String, String> params,
                                               HttpClientContext httpClientContext) {
        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;
        try {
            httpClient = HttpClientPool.getHttpClient();
            HttpGet httpGet = new HttpGet();
            httpGet.setConfig(requestConfig);
            setParams(httpGet, url, params);
            setHeaders(httpGet, header);
            //发起请求
            httpResponse = getResponse(httpClient, httpGet, httpClientContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * 发送get请求；核心方法
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String url,
                                     Map<String, String> headers,
                                     Map<String, String> params)
            throws Exception {
        return doGetRequest(url, headers, params, null);
    }

    /**
     * 发送Post请求；核心方法
     *
     * @param url
     * @param headers
     * @param params
     * @param formData
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url,
                                      Map<String, String> headers,
                                      Map<String, String> params,
                                      Map<String, String> formData)
            throws Exception {
        return doPostRequest(url, headers, params, formData, null);
    }


    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @throws Exception
     */
    public static HttpResponse doGet(String url)
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
    public static HttpResponse doGet(String url,
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
    public static HttpResponse doPost(String url)
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
    public static HttpResponse doPost(String url,
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
    public static HttpResponse doPost(String url,
                                      Map<String, String> params,
                                      Map<String, String> fromData)
            throws Exception {
        return doPost(url, null, params, fromData);
    }

    public static HttpResponse doGZIPPost(String url,
                                          Map<String, String> headers,
                                          Map<String, String> params,
                                          String data) {
        CloseableHttpClient httpClient = null;
        HttpResponse httpResponse = null;
        try {
            httpClient = HttpClientPool.getHttpClient();
            HttpPost httpPost = new HttpPost();
            httpPost.setConfig(requestConfig);
            setParams(httpPost, url, params);
            setHeaders(httpPost, headers);
            httpPost.setEntity(EntityBuilder.create()
                    .setText(data)
                    .setContentEncoding("UTF-8")
                    .gzipCompress()
                    .build());
            //发起请求
            httpResponse = getResponse(httpClient, httpPost, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * @param url
     * @param saveUrl
     * @return
     * @throws Exception
     */
    public static boolean download(String url, String saveUrl) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClientPool.getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveUrl);
                httpEntity.writeTo(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (fileOut != null) {
                    fileOut.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(httpClient, httpResponse);
        }
        return true;
    }

    /**
     * 发送请求，处理请求数据
     */
    public static HttpResponse getResponse(CloseableHttpClient httpClient,
                                           HttpRequestBase httpMethod,
                                           HttpClientContext httpClientContext) {

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpMethod, httpClientContext);
            if (response == null || response.getStatusLine() == null) {
                return new HttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
            String content = EntityUtils.toString(response.getEntity(), ENCODING);
            HttpResponse httpResponse = new HttpResponse(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                Header[] allHeaders = response.getAllHeaders();
                for (Header headerpair : allHeaders) {
                    if (headerpair.getName().equals("Location")) {
                        httpResponse.setContent(headerpair.getValue());
                        break;
                    }
                }

                if (StringUtil.isEmpty(httpResponse.getContent())) {
                    httpResponse.setContent(content);
                }
            } else {
                httpResponse.setContent(content);
            }
            return httpResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        } finally {
            closeConnection(httpClient, response);
        }
    }

    /**
     * 关掉连接释放资源
     *
     * @param httpClient
     * @param httpResponse
     */
    private static void closeConnection(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭Connection错误！");
            }
        }
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭Response错误！");
            }
        }
    }
}
