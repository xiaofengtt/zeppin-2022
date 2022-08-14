package com.product.worldpay.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.TextUtils;

/**
 * http/https
 */
public class WebUtil {

	private static String[] VERIFY_HOST_NAME_ARRAY = new String[] {};

	public static final HostnameVerifier createInsecureHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				if (TextUtils.isEmpty(hostname)) {
					return false;
				}
				return !Arrays.asList(VERIFY_HOST_NAME_ARRAY)
						.contains(hostname);
			}
		};
	}

	// https策略，绕过安全检查
	private static CloseableHttpClient getSingleSSLConnection(Integer connectTimeout, Integer readTimeout)
			throws Exception {
		// CloseableHttpClient httpClient = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						public boolean isTrusted(
								X509Certificate[] paramArrayOfX509Certificate,
								String paramString) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext, createInsecureHostnameVerifier());
			// return
			// HttpClients.custom().setDefaultRequestConfig(config).build();
			return HttpClients.custom().setSSLSocketFactory(sslsf)
					.setDefaultRequestConfig( RequestConfig.custom()
							.setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build()).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String get_ssl(String url, Integer connectTimeout, Integer readTimeout)
			throws Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(URI.create(url));

			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {// 出现链接异常，抛出
				httpGet.abort();
				throw new Exception("HttpClient,error status code :"
						+ statusCode);
			}

			String httpEntityContent = getHttpEntityContent(response);
			httpGet.abort();

			response.close();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}

	}

	/**
	 * 封装HTTP POST方法
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String post(String url, Map<String, String> paramMap, Integer connectTimeout, Integer readTimeout)
			throws ClientProtocolException, IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> formparams = setHttpParams(paramMap);
			UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(param);
			response = httpClient.execute(httpPost);
			String httpEntityContent = getHttpEntityContent(response);
			httpPost.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}

	}

	/**
	 * 封装HTTP POST方法
	 * 
	 * @param
	 * @param （如JSON串）
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String post(String url, String data, Integer connectTimeout, Integer readTimeout)
			throws ClientProtocolException, IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "text/json; charset=utf-8");
			httpPost.setEntity(new StringEntity(URLEncoder
					.encode(data, "UTF-8")));
			response = httpClient.execute(httpPost);
			String httpEntityContent = getHttpEntityContent(response);
			httpPost.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 封装HTTP GET方法
	 * 
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String get(String url, Integer connectTimeout, Integer readTimeout) throws ClientProtocolException,
			IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(URI.create(url));
			response = httpClient.execute(httpGet);
			String httpEntityContent = getHttpEntityContent(response);
			httpGet.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 封装HTTP GET方法
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String get(String url, Map<String, String> paramMap, Integer connectTimeout, Integer readTimeout)
			throws ClientProtocolException, IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet();
			List<NameValuePair> formparams = setHttpParams(paramMap);
			String param = URLEncodedUtils.format(formparams, "UTF-8");
			httpGet.setURI(URI.create(url + "?" + param));
			response = httpClient.execute(httpGet);
			String httpEntityContent = getHttpEntityContent(response);
			httpGet.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 封装HTTP PUT方法
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String put(String url, Map<String, String> paramMap, Integer connectTimeout, Integer readTimeout)
			throws ClientProtocolException, IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			List<NameValuePair> formparams = setHttpParams(paramMap);
			UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams,
					"UTF-8");
			httpPut.setEntity(param);
			response = httpClient.execute(httpPut);
			String httpEntityContent = getHttpEntityContent(response);
			httpPut.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 封装HTTP DELETE方法
	 * 
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String delete(String url, Integer connectTimeout, Integer readTimeout) throws ClientProtocolException,
			IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpDelete httpDelete = new HttpDelete();
			httpDelete.setURI(URI.create(url));
			response = httpClient.execute(httpDelete);
			String httpEntityContent = getHttpEntityContent(response);
			httpDelete.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 封装HTTP DELETE方法
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static String delete(String url, Map<String, String> paramMap, Integer connectTimeout, Integer readTimeout)
			throws ClientProtocolException, IOException, Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection(connectTimeout, readTimeout);
		CloseableHttpResponse response = null;
		try {
			HttpDelete httpDelete = new HttpDelete();
			List<NameValuePair> formparams = setHttpParams(paramMap);
			String param = URLEncodedUtils.format(formparams, "UTF-8");
			httpDelete.setURI(URI.create(url + "?" + param));
			response = httpClient.execute(httpDelete);
			String httpEntityContent = getHttpEntityContent(response);
			httpDelete.abort();
			return httpEntityContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
	}

	/**
	 * 设置请求参数
	 * 
	 * @param
	 * @return
	 */
	private static List<NameValuePair> setHttpParams(
			Map<String, String> paramMap) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		return formparams;
	}

	/**
	 * 获得响应HTTP实体内容
	 * 
	 * @param response
	 * @return
	 * @throws java.io.IOException
	 * @throws java.io.UnsupportedEncodingException
	 */
	private static String getHttpEntityContent(HttpResponse response)
			throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		return "";
	}

}
