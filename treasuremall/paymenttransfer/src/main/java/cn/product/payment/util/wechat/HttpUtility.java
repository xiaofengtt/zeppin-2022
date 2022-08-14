package cn.product.payment.util.wechat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
  
  
@SuppressWarnings("deprecation")
public class HttpUtility {  
      
    public static String post(String url, Map<String, String> params) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body;  
        HttpPost post = postForm(url, params);
        body = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return body;  
    } 
    
    public static String postXml(String url, String xml) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body;  
        HttpPost post = postForXml(url, xml);
        body = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return body;  
    }  
      
    public static String get(String url) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body;
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);
        httpclient.getConnectionManager().shutdown();
        return body;  
    }  
    
    private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);
        return body;  
    }  
  
    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity(); 
        String body = null;  
        try {  
            body = EntityUtils.toString(entity,HTTP.UTF_8);
        } catch (Exception e) {  
        	e.printStackTrace();
        }
        return body;  
    }  
  
    private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
        try {  
            response = httpclient.execute(httpost);  
        } catch (Exception e) {  
        	e.printStackTrace();  
        }
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }
        try { 
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (Exception e) {  
        	e.printStackTrace();
        }
        return httpost;  
    }  
    
    private static HttpPost postForXml(String url, String xml){  
        HttpPost httpost = new HttpPost(url);  
        try { 
        	StringEntity myEntity = new StringEntity(xml, "UTF-8");
            httpost.setEntity(myEntity);  
        } catch (Exception e) {  
        	e.printStackTrace();
        }
        return httpost;  
    } 
} 