package com.zixueku.util;

import android.app.ProgressDialog;

import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年5月14日 下午5:27:51
 */
public class NetThreadUtil {
	private static ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();

	// 在进行网络通讯时所显示的进度条
	private static ProgressDialog progressDialog;

	public static void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionNoProgressDialog(req, callBack);
	}
	
	public static void sendDataToServerWithProgressDialog(Request req, ServerDataCallback<?> callBack) {
		serverConnectionWithProgressDialog(req, callBack);
	}

	// 不带loading的请求
	private static void serverConnectionNoProgressDialog(Request req, ServerDataCallback<?> callBack) {
		RequestHandler handler = new RequestHandler(req.getContext(), callBack, null, false);
		RequestTask taskThread = new RequestTask(req, handler);
		threadPoolManager.addTask(taskThread);
	}

	// 带loading的请求
	private static void serverConnectionWithProgressDialog(Request req, ServerDataCallback<?> callBack) {
		progressDialog = new ProgressDialog(req.getContext());
		DialogUtil.showLoadingProgressDialog(req.getContext(), progressDialog);
		RequestHandler handler = new RequestHandler(req.getContext(), callBack, progressDialog, false);
		RequestTask taskThread = new RequestTask(req, handler);
		threadPoolManager.addTask(taskThread);
	}

}
