package com.zixueku.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.SystemClock;

public class INet {

	public final static int CONNECTION_TIMEOUT = 20 * 1000;
	@SuppressWarnings("deprecation")
	private HttpParams params = new BasicHttpParams();

	private static INet instance;
	private String result;

	public static synchronized INet instance() {
		if (instance == null)
			instance = new INet();
		return instance;
	}

	@SuppressWarnings("deprecation")
	class PostResponseHandler implements ResponseHandler<String> {

		public String handleResponse(final HttpResponse response)
				throws HttpResponseException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}

			HttpEntity entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity,
					HTTP.UTF_8);
		}
	}

	private INet() {
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, CONNECTION_TIMEOUT);
		HttpProtocolParams.setContentCharset(params, "UTF_8");
		HttpProtocolParams.setUseExpectContinue(params, false);
	}

	public DefaultHttpClient getHttpClient() throws UnknownHostException {
		DefaultHttpClient httpclient = new DefaultHttpClient(params);
		return httpclient;
	}

	@SuppressWarnings("deprecation")
	public String httpGet(final String httpUrl) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpClient client = null;
				try {
					client = getHttpClient();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				HttpGet httpGet = new HttpGet(replaceSpace(httpUrl));
				HttpResponse response = null;
				try {
					response = client.execute(httpGet);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					try {
						result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				response = null;
				httpGet = null;
				client.getConnectionManager().shutdown();
			}
		}).start();
		SystemClock.sleep(200);
		return result;
	}

	private String replaceSpace(String url) {
		return url.replace(" ", "%20").replace("\n", " ").replace("", "");
	}

}
