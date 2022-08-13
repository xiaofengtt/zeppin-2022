package com.zixueku.util;

import java.io.UnsupportedEncodingException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;
import com.zixueku.net.URL;

public class MyJsonRequest extends com.android.volley.Request<Object> {
	/** Default charset for JSON request. */
	protected static final String PROTOCOL_CHARSET = "utf-8";
	private static final int socketTimeout = 15000;// net connection timeout 15
													// seconds
	private com.zixueku.entity.Request request;
	private Map<String, Object> params;
	private Context context;
	private ServerDataCallback callBack;
	private ProgressDialog progressDialog;

	public MyJsonRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
	}

	public MyJsonRequest(Request request, ServerDataCallback<?> listener, ErrorListener errorListener, ProgressDialog progressDialog) {
		super(Method.POST, URL.ServerPath.concat(request.getContext().getString(request.getRequestUrl())), errorListener);
		this.request = request;
		this.params = request.getRequestData();
		this.context = request.getContext();
		this.callBack = listener;
		this.progressDialog = progressDialog;
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		this.setRetryPolicy(policy);
	}

	@Override
	protected Response<Object> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
			LogUtils.e("server original data", jsonString);
			return Response.success(parse(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (Exception ex) {
			return Response.error(new ParseError(ex));
		} finally {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

	protected Object parse(String records) {
		Object obj = null;
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
		return obj;
	}

	public com.zixueku.entity.Request getRequest() {
		return request;
	}

	public void setRequest(com.zixueku.entity.Request request) {
		this.request = request;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return MapObjectToString(this.params);
	}

	private Map<String, String> MapObjectToString(Map<String, Object> map) {
		Map<String, String> target = new IdentityHashMap<String, String>();
		if (map != null) {
			Iterator<?> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				target.put(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		// 每次发送请求时带上版本信息
		target.put("version", CommonTools.getVersionName(context));
		target.put("user.id", String.valueOf(App.getInstance().getUserInfo().getUserId()));
		LogUtils.e("请求参数", target.toString());
		return target;
	}

	@Override
	protected void deliverResponse(Object response) {
		if (callBack != null) {
			callBack.processData(response, true);
		}
	}

}
