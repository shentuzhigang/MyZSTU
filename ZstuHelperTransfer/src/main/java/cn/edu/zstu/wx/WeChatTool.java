package cn.edu.zstu.wx;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.ssl.SSLContextBuilder;  
import org.apache.http.conn.ssl.TrustStrategy;   
import javax.net.ssl.SSLContext;  

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
public class WeChatTool {
	private static final String SECRET="c1b5e4e3962f179554fade47ca755dce";
	private static final String APPID="wx3a3adfcb8b523073";
	public static CloseableHttpClient createSSLClientDefault(){
        try {
            SSLContext sslContext=new SSLContextBuilder().loadTrustMaterial(
                    null,new TrustStrategy() {
                        //信任所有
                        public boolean isTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf=new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
    public static String getOpenId(String code) {
    	String openId="";
		CloseableHttpClient httpClient=createSSLClientDefault();
		String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+code+"&grant_type=authorization_code";  
		HttpGet httpGet=new HttpGet(WX_URL);
		try {
			CloseableHttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity,"UTF-8");
			System.out.println("res="+responseString);
			JSONObject jsonObject = JSON.parseObject(responseString);
		    openId=jsonObject.getString("openid");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return openId;    	
    }
}
