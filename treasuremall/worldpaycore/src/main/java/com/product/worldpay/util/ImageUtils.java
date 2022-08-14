package com.product.worldpay.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.product.worldpay.entity.Resource;

@Component
public class ImageUtils {

	private static String filePath;
	@Value("${app.filePath}")  
	public void setFilePath(String path) {
		ImageUtils.filePath = path;
	}
	
	
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
	
	// 请求配置，设置链接超时和读取超时
	private static final RequestConfig config = RequestConfig.custom()
			.setConnectTimeout(300000).setSocketTimeout(300000).build();

	// https策略，绕过安全检查
	private static CloseableHttpClient getSingleSSLConnection()
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
					.setDefaultRequestConfig(config).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	// 链接url下载图片
	public static Map<String, Object> downloadPicture(String urlStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		String success = "ok";
		String uuid = UUID.randomUUID().toString();
		
		Resource res = null;
//		URL url = null;
		byte[] buffer = new byte[1024];
		int length = 0;
		DataInputStream dataInputStream = null;
		FileOutputStream fileOutputStream = null;
		CloseableHttpResponse response = null;
		String[] dir = uuid.split("-");
		try {
//			url = new URL(urlStr);
			CloseableHttpClient httpClient = getSingleSSLConnection();
			
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(URI.create(urlStr));

			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {// 出现链接异常，抛出
				httpGet.abort();
				throw new Exception("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity entity = response.getEntity();
			dataInputStream = new DataInputStream(entity.getContent());

			String basePath = "/upload/";
			for (String sPath : dir) {
				basePath += sPath + "/";
				File tfFile = new File(filePath + "/" + basePath);
				if (!tfFile.exists()) {
					tfFile.mkdir();
				}
			}

			res = new Resource();
			res.setUuid(uuid);
			String name = dir[dir.length - 1];
			res.setName(name);
			String typevalue = urlStr.substring(urlStr.lastIndexOf(".") + 1);
			res.setType(typevalue);

			res.setUrl(basePath + name + "." + res.getType());
			res.setType("2");
			res.setCreatetime(new Timestamp(System.currentTimeMillis()));
			String imageName = filePath + res.getUrl();
			File resourcefile = new File(imageName);
			fileOutputStream = new FileOutputStream(resourcefile);

			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			
			fileOutputStream.flush();
			
			httpGet.abort();
			
			res.setSize(BigInteger.valueOf(resourcefile.length()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			success = "File Create Error！";

		} catch (IOException e) {
			e.printStackTrace();
			success = "File Write Error！";
		} catch (Exception e) {
			e.printStackTrace();
			success = "File Write Error！";
		} finally {
			try {
				if(dataInputStream != null){
					dataInputStream.close();
				}
				if(fileOutputStream != null){
					fileOutputStream.close();
				}
				if(response != null){
					response.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result.put("success", success);
		result.put("result", res);
		return result;
	}
}
