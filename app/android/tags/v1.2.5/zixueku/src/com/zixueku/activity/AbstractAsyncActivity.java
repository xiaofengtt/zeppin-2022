package com.zixueku.activity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;

import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;
import com.zixueku.util.DialogUtil;
import com.zixueku.util.RequestHandler;
import com.zixueku.util.RequestTask;
import com.zixueku.util.ThreadPoolManager;

public abstract class AbstractAsyncActivity extends FragmentActivity {

	protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();
	/** 未登陆 */
	public static final int NOT_LOGIN = 403;

	// 手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;

	private ThreadPoolManager threadPoolManager;

	// 在进行网络通讯时所显示的进度条
	private ProgressDialog progressDialog;

	private boolean destroyed = false;

	public AbstractAsyncActivity() {
		threadPoolManager = ThreadPoolManager.getInstance();
	}

	// 把android默认的onCreate方法拆分,使每个方法的功能更明确
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
	}

	protected abstract void loadViewLayout();

	protected abstract void findViewById();

	protected abstract void setListener();

	/**
	 * 逻辑
	 */
	protected abstract void processLogic();

	protected void onTouchEventLeft() {
	}

	protected void onTouchEventRight() {
	}

	protected void onTouchEventTop() {
	}

	protected void onTouchEventBottom() {
	}

	/**
	 * 请求服务器端数据
	 * 
	 * @param reqVo
	 *            封装请求信息
	 * @param callBack
	 *            回调对象，实现回调方法的具体实现
	 */
	protected void getDataFromServer(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack);
	}

	/**
	 * 向服务器端发送数据 无回调方法的
	 * 
	 * @param reqVo
	 *            封装请求信息
	 */
	protected void sendDataToServer(Request req) {
		serverConnection(req, null);
	}

	/**
	 * 向服务器端发送数据 需要回调方法的
	 * 
	 * @param reqVo
	 *            封装请求信息
	 */
	protected void sendDataToServer(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack);
	}

	/**
	 * 请求服务器端数据
	 * 
	 * @param reqVo
	 *            封装请求信息
	 * @param callBack
	 *            回调对象，实现回调方法的具体实现
	 */
	protected void getDataFromServerNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack);
	}

	/**
	 * 向服务器端发送数据 无回调方法的
	 * 
	 * @param reqVo
	 *            封装请求信息
	 */
	protected void sendDataToServerNoProgressDialog(Request req) {
		serverConnectionNoProgressDialog(req, null);
	}

	/**
	 * 向服务器端发送数据 需要回调方法的
	 * 
	 * @param reqVo
	 *            封装请求信息
	 */
	protected void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack);
	}

	// 带loading的请求
	private void serverConnection(Request req, ServerDataCallback<?> callBack) {
		progressDialog = new ProgressDialog(this);
		DialogUtil.showLoadingProgressDialog(this, progressDialog);
		RequestHandler handler = new RequestHandler(req.getContext(), callBack, progressDialog, destroyed);
		RequestTask taskThread = new RequestTask(req, handler);
		this.threadPoolManager.addTask(taskThread);
	}

	// 不带loading的请求
	private void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		RequestHandler handler = new RequestHandler(req.getContext(), callBack, null, destroyed);
		RequestTask taskThread = new RequestTask(req, handler);
		this.threadPoolManager.addTask(taskThread);
	}

	// ***************************************
	// Activity methods
	// 销毁Activity时被回调,该方法只会被回调一次
	// ***************************************
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 继承了Activity的onTouchEvent方法，直接监听点击事件

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 当手指按下的时候
			x1 = event.getX();
			y1 = event.getY();
			break;

		case MotionEvent.ACTION_UP:

			// 当手指离开的时候
			x2 = event.getX();
			y2 = event.getY();
			if (y1 - y2 > 50) {
				// 向上滑
				onTouchEventTop();
			} else if (y2 - y1 > 50) {
				// 向下滑
				onTouchEventBottom();
			} else if (x1 - x2 > 50) {
				// 向左滑
				onTouchEventLeft();
			} else if (x2 - x1 > 50) {
				// 向右滑
				onTouchEventRight();
			}
			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}
	
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}


}
