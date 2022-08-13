package com.zixueku.util;

import java.io.UnsupportedEncodingException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;

public class MyJsonRequest extends com.android.volley.Request<Object> {
	/** Default charset for JSON request. */
	protected static final String PROTOCOL_CHARSET = "utf-8";
	private com.zixueku.entity.Request request;
	private Map<String, Object> params;
	private Context context;
	private ServerDataCallback callBack;
	private ProgressDialog progressDialog;

	public MyJsonRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
	}

	public MyJsonRequest(Request request, ServerDataCallback<?> listener, ErrorListener errorListener, ProgressDialog progressDialog) {
		super(Method.POST, request.getContext().getString(R.string.ServerPath).concat(request.getContext().getString(request.getRequestUrl())), errorListener);
		this.request = request;
		this.params = request.getRequestData();
		this.context = request.getContext();
		this.callBack = listener;
		this.progressDialog = progressDialog;
	}

	@Override
	protected Response<Object> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
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
		Iterator<?> iter = map.entrySet().iterator();
		Map<String, String> target = new IdentityHashMap<String, String>();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			target.put(entry.getKey().toString(), entry.getValue().toString());
		}
		// 每次发送请求时带上版本信息
		target.put("version", CommonTools.getVersionName(context));
		target.put("user.id", String.valueOf(App.getInstance().getUserInfo().getUserId()));
		return target;
	}

	@Override
	protected void deliverResponse(Object response) {
		if (callBack != null) {
			callBack.processData(response, true);
		}
	}

}
