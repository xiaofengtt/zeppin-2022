package com.makati.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtil {
	private static CloseableHttpClient httpClient;
	private static RequestConfig requestConfig;

	private static boolean isIni=false;

	private static int timeout=30*1000;

	/**
	 * 初始化
	 */
	private static void ini(){
		httpClient = HttpClients.createDefault();
		requestConfig= RequestConfig.custom().setConnectionRequestTimeout(timeout)
				                            .setConnectTimeout(timeout)
				                            .setSocketTimeout(timeout)
				                            .build();
		isIni=true;
	}
	/**
	 *
	 * @param url：
	 * @param map：封装的参数
	 * @param charset：指定编码
	 * @return
	 */
	public static String doPost(String url,Map<String,String> map,String charset) throws Exception{
		if(!isIni){
			ini();
		}
		if(StringUtils.isBlank(charset)){
			charset="utf-8";
		}
		HttpPost httpPost = null;
		String result = null;
		CloseableHttpResponse response =null;
		try{
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);

			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String,String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
			}
			if(list.size() > 0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			if(response != null&&response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}else {
				httpPost.abort();
			}
		}catch(Exception ex){
			throw ex;
		}finally {
			if(response!=null)
				response.close();
			if(httpPost!=null)
				httpPost.abort();
		}
		return result;
	}

	/**
	 *
	 * @param url：
	 * @param charset：指定编码
	 * @return
	 */
	public static String doGet(String url,String charset) throws Exception{
		if(!isIni){
			ini();
		}
		HttpGet httpGet = null;
		String result = null;
		CloseableHttpResponse resp =null;
		try{
			httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			resp = httpClient.execute(httpGet);
			log.info("执行地址:"+url+"得到的数据"+resp.toString());

			if(resp != null&&resp.getStatusLine().getStatusCode()== 200){
				 HttpEntity respEntity = resp.getEntity();
				if(respEntity != null){
					result = EntityUtils.toString(respEntity,charset);
				}
			}else {
				httpGet.abort();
			}
		}catch(Exception ex){
			log.error("获取地址:"+url+"失败");
			throw ex;
		}finally {
			if(resp!=null)
				resp.close();
			if(httpGet!=null)
				httpGet.abort();
		}
		return result;
	}
}
