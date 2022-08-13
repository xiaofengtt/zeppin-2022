package com.zixueku.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
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

	// 不带loading的请求,忽略网络错误
	public static void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack, null);
	}

	// 不带loading的请求，自定义错误处理
	// 如需要带loading请求，并自行处理错误信息，则需要在调用该方法前自行添加loading效果，并自行处理后续情况
	public static void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		serverConnectionNoProgressDialog(req, callBack, errorListener);
	}
	
	public static void sendDataToServerNoProgressDialogIgnoError(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack, null);
	}


	public static void sendDataToServerWithProgressDialog(Request req, ServerDataCallback<?> callBack) {
		ProgressDialog progressDialog = new ProgressDialog(req.getContext());
		NetErrorListener errorListerner = new NetErrorListener(req.getContext(), progressDialog);
		DialogUtil.showLoadingProgressDialog(req.getContext(), progressDialog);
		serverConnection(req, callBack, errorListerner, progressDialog);
	}

	protected static void serverConnection(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener, ProgressDialog progressDialog) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, progressDialog);
		mRequestQueue.add(myJson);
	}

	private static void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, null);
		mRequestQueue.add(myJson);
	}
}

class NetErrorListener implements ErrorListener {
	private Context context;
	private ProgressDialog progressDialog;

	public NetErrorListener(Context context) {
		super();
		this.context = context;
	}

	public NetErrorListener(Context context, ProgressDialog progressDialog) {
		super();
		this.context = context;
		this.progressDialog = progressDialog;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// 取消进度条
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		DialogUtil.showInfoDialog(context, VolleyErrorHelper.getMessage(error, context));
	}
}
