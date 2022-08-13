package com.zixueku.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zixueku.R;
import com.zixueku.entity.Request;
import com.zixueku.entity.Status;

/**
 * 
 * @author huangweidong
 * 
 */
public class NetUtil {
	private static final String NOT_LOGIN = "notlogin";
	private static final String TAG = "NetUtil";
	private static Header[] headers = new BasicHeader[12];

	static {
		headers[0] = new BasicHeader("Appkey", "");
		headers[1] = new BasicHeader("Udid", "");
		headers[2] = new BasicHeader("Os", "");
		headers[3] = new BasicHeader("Osversion", "");
		headers[4] = new BasicHeader("Appversion", "");
		headers[5] = new BasicHeader("Sourceid", "");
		headers[6] = new BasicHeader("Ver", "");
		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");
		headers[9] = new BasicHeader("Unique", "");
		headers[10] = new BasicHeader("Cookie", "");
		headers[11] = new BasicHeader("Range", "bytes=" + "");

	}

	/*
	 * 
	 */
	public static Object post(Request request) throws Exception {
		if (!hasNetwork(request.getContext())) {
			return null;
		}
		DefaultHttpClient client = new DefaultHttpClient();
		Context context = request.getContext();
		String url = context.getString(R.string.ServerPath).concat(context.getString(request.getRequestUrl()));

		Log.i("请求地址：", url);

		HttpPost post = new HttpPost(url);
		post.setHeaders(headers);
		Map<String, Object> requestData = request.getRequestData(); // 获取请求参数
		if (requestData == null) {
			requestData = new HashMap<String, Object>();
		}

		//每次发送请求时带上版本信息
		requestData.put("version", CommonTools.getVersionName(context));

		// 把请求参数转化成特定请求的格式
		List<NameValuePair> pairList = conventPair(requestData);
		HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
		post.setEntity(entity);
		// 执行服务器请求
		HttpResponse response = client.execute(post);

		Object obj = null; // 存放服务器返回数据后的转化结果
		// 处理服务器端返回的数据
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			setCookie(response);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			Log.i("服务器端返回原始数据：", result);
			String records = result;// JSONUtil.getRecords(result);
			if (invilidateLogin(records)) {
				return Status.Login;
			}
			switch (request.getParserType()) {
			case LIST:
				obj = JSONUtil.jsonToList(records);
				break;
			case LIST_USER_DEFINED:
				obj = JSONUtil.jsonToList(records, request.getType());
				break;

			case MAP:
				obj = JSONUtil.jsonToMap(records);
				break;
			case USER_DEFINED:
				obj = JSONUtil.jsonToObject(records, request.getType());
				break;
			case USER_DEFINED2:
				obj = JSONUtil.jsonToObject(records, request.getObjType());
				break;
			case PRIMITIVE:
				obj = records;
				break;
			default:
				break;
			}
		}
		return obj;
	}

	/**
	 * 添加Cookie
	 * 
	 * @param response
	 */
	private static void setCookie(HttpResponse response) {
		if (response.getHeaders("Set-Cookie").length > 0) {
			Logger.d(TAG, response.getHeaders("Set-Cookie")[0].getValue());
			headers[10] = new BasicHeader("Cookie", response.getHeaders("Set-Cookie")[0].getValue());
		}
	}

	/**
	 * 验证是否需要登录
	 * 
	 * @param result
	 * @return
	 * @throws JSONException
	 */
	private static boolean invilidateLogin(String result) throws JSONException {
		// JSONObject jsonObject = new JSONObject(result);
		// String responseCode = jsonObject.getString("response");
		if (NOT_LOGIN.equals(result)) {
			return true;
		}
		return false;
	}

	/**
	 * 获得网络连接是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			// Toast.makeText(context, R.string.net_error,
			// Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	protected static List<NameValuePair> conventPair(String params) {
		// 使用NameValuePair来保存要传递的Post参数
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (params != null && (!("".equals(params)))) {
			String[] temp = params.split("&");
			for (String string : temp) {
				String[] temp2 = string.split(":");
				// 添加要传递的参数
				paramsList.add(new BasicNameValuePair(temp2[0], temp2[1]));
			}
		}
		return paramsList;
	}

	@SuppressWarnings("rawtypes")
	protected static List<NameValuePair> conventPair(Map<?, ?> params) {
		// 使用NameValuePair来保存要传递的Post参数
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		Iterator<?> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			paramsList.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
			Log.i("参数名称=参数值", entry.getKey().toString() + "=" + entry.getValue().toString());
		}
		return paramsList;
	}
}
