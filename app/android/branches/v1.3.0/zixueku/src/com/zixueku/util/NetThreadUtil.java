package com.zixueku.util;

import android.app.ProgressDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年5月14日 下午5:27:51
 */
public class NetThreadUtil {
	private static RequestQueue mRequestQueue = App.getInstance().getmRequestQueue();

	public static void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack, null);
	}

	public static void sendDataToServerWithProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack, null);
	}

	// 带loading的请求
	protected static void serverConnection(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		ProgressDialog progressDialog = new ProgressDialog(req.getContext());
		DialogUtil.showLoadingProgressDialog(req.getContext(), progressDialog);
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, progressDialog);
		mRequestQueue.add(myJson);
	}

	// 不带loading的请求
	private static void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, null);
		mRequestQueue.add(myJson);
	}

}
