package club.zstuca.myzstu.utils.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Http 连接池
 *
 * @author ShenTuZhiGang
 */
public class HttpClientPool {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslConnectionSocketFactory = null;
    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;
    /**
     * 管理Https连接的上下文类
     */
    private static SSLContextBuilder sslContextBuilder = null;
    private static BasicCookieStore basicCookieStore = null;

    static {
        basicCookieStore = new BasicCookieStore();
        try {
            sslContextBuilder = new SSLContextBuilder().loadTrustMaterial(null,
                    new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s)
                                throws CertificateException {
                            //                    信任所有站点 直接返回true
                            return true;
                        }
                    });
            //"SSLv2Hello", "SSLv3", "TLSv1"
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContextBuilder.build(),
                    new String[]{"TLSv1.2"},
                    null,
                    NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registryBuilder = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslConnectionSocketFactory)
                    .build();
            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder);
            poolingHttpClientConnectionManager.setMaxTotal(200);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取连接
     *
     * @return HttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .setDefaultCookieStore(basicCookieStore)
                .build();
    }

    /**
     * 获取关联上下文的连接
     *
     * @param basicCookieStore Cookie
     * @return HttpClient
     */
    public static CloseableHttpClient getHttpClientWithContext(BasicCookieStore basicCookieStore) {
        return HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .setDefaultCookieStore(basicCookieStore)
                .build();
    }
}
