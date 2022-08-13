package com.zixueku.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;
import com.zixueku.util.App;
import com.zixueku.util.AppManager;
import com.zixueku.util.DialogUtil;
import com.zixueku.util.MyJsonRequest;
import com.zixueku.util.VolleyErrorHelper;

public abstract class AbstractAsyncActivity extends FragmentActivity {
	private RequestQueue mRequestQueue;
	protected Context mContext;
	
	private ProgressDialog progressDialog;

	// 把android默认的onCreate方法拆分,使每个方法的功能更明确
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mRequestQueue = App.getInstance().getmRequestQueue();
		this.mContext = this;
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
		AppManager.getAppManager().addActivity(this);
	}

	protected abstract void loadViewLayout();

	protected abstract void findViewById();

	protected abstract void setListener();

	/**
	 * 逻辑
	 */
	protected abstract void processLogic();

	/**
	 * 与服务器端进行数据交互，网络错误统一处理
	 * 
	 * @param req
	 * @param callBack
	 *            成功后客户端回调接口
	 */
	protected void netDataConnation(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack, new NetErrorListener());
	}

	/**
	 * 与服务器端进行数据交互，网络错误统一处理
	 * 
	 * @param req
	 * @param 无回调方法
	 */
	protected void netDataConnation(Request req) {
		serverConnection(req, null, new NetErrorListener());
	}

	/**
	 * 与服务器端进行数据交互
	 * 
	 * @param req
	 * @param callBack
	 *            成功后客户端回调接口
	 */
	protected void netDataConnationIgnoreNetError(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack, null);
	}

	/**
	 * 与服务器端进行数据交互
	 * 
	 * @param req
	 * @param 无回调方法
	 */
	protected void netDataConnationIgnoreNetError(Request req) {
		serverConnection(req, null, null);
	}

	/**
	 * 与服务器端进行数据交互，网络错误统一处理
	 * 
	 * @param req
	 * @param callBack
	 *            成功后客户端回调接口 无loading
	 */
	protected void netDataConnationNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack, new NetErrorListener());
	}

	/**
	 * 与服务器端进行数据交互，网络错误统一处理
	 * 
	 * @param req
	 * @param 无回调方法
	 *            无loading
	 */
	protected void netDataConnationNoProgressDialog(Request req) {
		serverConnectionNoProgressDialog(req, null, new NetErrorListener());
	}

	/**
	 * 与服务器端进行数据交互，
	 * 
	 * @param req
	 * @param callBack
	 *            成功后客户端回调接口 无loading
	 */
	protected void netDataConnationNoProgressDialogIgnoreNetError(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack, null);
	}

	/**
	 * 与服务器端进行数据交互
	 * 
	 * @param req
	 * @param 无回调方法
	 *            无loading
	 */
	protected void netDataConnationNoProgressDialogIgnoreNetError(Request req) {
		serverConnectionNoProgressDialog(req, null, null);
	}

	// 带loading的请求
	protected void serverConnection(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		progressDialog = new ProgressDialog(this);
		DialogUtil.showLoadingProgressDialog(this, progressDialog);
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, progressDialog);
		mRequestQueue.add(myJson);
	}

	// 不带loading的请求
	private void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, null);
		
		mRequestQueue.add(myJson);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	class NetErrorListener implements ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
			// 取消进度条
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			DialogUtil.showInfoDialog(mContext, VolleyErrorHelper.getMessage(error, mContext));
		}
	}

}
