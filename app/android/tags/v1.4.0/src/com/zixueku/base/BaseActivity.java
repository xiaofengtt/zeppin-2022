package com.zixueku.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.analytics.MobclickAgent;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.customview.LoadingDialog;
import com.zixueku.entity.Request;
import com.zixueku.util.App;
import com.zixueku.util.AppManager;
import com.zixueku.util.DialogUtil;
import com.zixueku.util.MyJsonRequest;
import com.zixueku.util.ToastUtil;
import com.zixueku.util.VolleyErrorHelper;

/**
 * 
 * @author Crist
 * 
 */
public abstract class BaseActivity extends Activity {
	protected LoadingDialog ld;
	private RequestQueue mRequestQueue;
	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		this.mRequestQueue = App.getInstance().getmRequestQueue();
		initView(savedInstanceState);
		ViewUtils.inject(this);
		ld = new LoadingDialog(this); // 初始化进度条
		initData();
		LogUtils.allowD = true; // 测试环境下允许打log
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		onSonDestroy();
		super.onDestroy();
	}

	protected void onSonDestroy() {

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

	/**
	 * 初始化handler
	 */
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			initHandler(msg);
		};
	};

	/**
	 * 业务数据处理后的回调, 一般用于异步网络请求完成后执行的界面刷新
	 */
	protected abstract void initHandler(android.os.Message msg);

	/**
	 * 基层Activity的初始化, 如有必要则重写此方法(如界面crash之后的恢复, 引入外库等)
	 * 
	 * @param savedInstanceState
	 */
	protected void initView(Bundle savedInstanceState) {

	}

	/**
	 * 初始化界面数据
	 */
	protected abstract void initData();

	/**
	 * 显示正在加载数据的dialog
	 */
	public void showLoadingDiaglog(String tip) {
		if (ld == null) {
			ld = new LoadingDialog(this);
		}
		if (!TextUtils.isEmpty(tip)) {
			ld.setText(tip);
		}
		ld.show();
	}

	/**
	 * 显示正在加载数据的dialog
	 */
	public void showLoadingDiaglog(int resId) {
		if (ld == null) {
			ld = new LoadingDialog(this);
		}
		ld.setText(resId);
		ld.show();
	}

	public void showImageDiaglog(Drawable drawable, String string) {
		ld = new LoadingDialog(this);
		ld.setImageResouce(drawable);
		ld.setText(string);
		ld.show();
	}

	/**
	 * 关闭正在显示的dialog
	 */
	public void closeLoadingDialog() {
		if (ld == null) {
			ld = new LoadingDialog(this);
		}
		if (ld.isShowing()) {
			ld.dismiss();
		}
	}

	public void showToast(String content) {
		ToastUtil.showTextInMiddle(this, content, 0);
	}

	public void showToast(int resId) {
		ToastUtil.showTextInMiddle(this, resId, 0);
	}

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
		ProgressDialog progressDialog = new ProgressDialog(this);
		DialogUtil.showLoadingProgressDialog(this, progressDialog);
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, progressDialog);
		mRequestQueue.add(myJson);
	}

	// 不带loading的请求
	private void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener, null);
		mRequestQueue.add(myJson);
	}

	class NetErrorListener implements ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
			DialogUtil.showInfoDialog(context, VolleyErrorHelper.getMessage(error, context));
		}
	}
}
