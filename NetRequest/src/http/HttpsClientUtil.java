package http;

/**
 * Created by Administrator on 2017/10/26.
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpsClientUtil {


    private static  RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 发起post请求，请求参数以Map集合形式传入，封装到List <NameValuePair> 发起post请求
     * @param httpUrl
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String httpUrl, Map<String, Object> params) throws Exception {
        String result = null ;
        CloseableHttpClient httpclient = createSSLClientDefault();
        //httpclient.
        //httpclient.
        BufferedReader in = null ;
        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        StringBuffer paramsBuf = new StringBuffer() ;
        for(Entry<String, Object> e : params.entrySet()) {
            nvps.add(new BasicNameValuePair(e.getKey(), (String) e.getValue()));
            paramsBuf.append("&").append(e.getKey()).append("=").append(e.getValue()) ;
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        try {
//          报文参数27：&id=jn-3-767744&groupPlatProTerminalId=119667&extend=uwJZ8j3CkpGPL4rM5J6KJhjR99O7yAe3BAGLS8ooI8ijNqKHfzTaK6W9wQvjZEVOmWJ3HxFb2O9D
//          wDbe3++UiQ==&xxtCode=370000&terminalType=1&role=3&type=3
            System.out.println("post请求报文地址：" + httpUrl+"?"+paramsBuf.toString()) ;
            CloseableHttpResponse response = httpclient.execute(httpPost);
            InputStream content = response.getEntity().getContent() ;
            in = new BufferedReader(new InputStreamReader(content, "UTF-8"));
//          in = new BufferedReader(new InputStreamReader(content, "GBK"));
//          in = new BufferedReader(new InputStreamReader(content));
            StringBuilder sb = new StringBuilder();
            String line = "" ;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString() ;
            //  响应报文：{"ret":0,"msg":"成功","callbackurl":"https://edu.10086.cn/test-sso/login?service=http%3A%2F%2F112.35.7.169%3A9010%2Feducloud%2Flogin%2Flogin%3Ftype%3D3%26mode%3D1%26groupId%3D4000573%26provincePlatformId%3D54","accesstoken":"2467946a-bee9-4d8c-8cce-d30181073b75"}Í
            return result ;
        } catch (Exception e) {
            e.printStackTrace() ;
        } finally {
            httpclient.close();
        }
        return null ;
    }

    public static CloseableHttpClient createSSLClientDefault(){

        try {
            //SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 在JSSE中，证书信任管理器类就是实现了接口X509TrustManager的类。我们可以自己实现该接口，让它信任我们指定的证书。
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            //信任所有
            X509TrustManager x509mgr = new X509TrustManager() {

                //　　该方法检查客户端的证书，若不信任该证书则抛出异常
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }
                // 　　该方法检查服务端的证书，若不信任该证书则抛出异常
                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }
                // 　返回受信任的X509证书数组。
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            ////创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            //  HttpsURLConnection对象就可以正常连接HTTPS了，无论其证书是否经权威机构的验证，只要实现了接口X509TrustManager的类MyX509TrustManager信任该证书。
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();


        } catch (KeyManagementException e) {

            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // 创建默认的httpClient实例.
        return  HttpClients.createDefault();

    }



}
