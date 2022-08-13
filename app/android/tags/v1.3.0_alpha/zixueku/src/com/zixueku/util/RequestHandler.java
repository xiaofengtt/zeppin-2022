package com.zixueku.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;

/**
 * 类说明 
 * 用于与子线程通信,子线程发送的消息并配合主线程更新UI。
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-5 上午11:33:42
 */
@SuppressWarnings("rawtypes")
public class RequestHandler extends Handler {
	private Context context;
	private ServerDataCallback callBack;
	private ProgressDialog progressDialog;
	private Boolean destroyed;

	/**
	 * 显示进度条的构造函数
	 * @param context
	 * @param callBack
	 * @param progressDialog
	 * @param destroyed
	 */
	public RequestHandler(Context context, ServerDataCallback callBack, ProgressDialog progressDialog,boolean destroyed) {
		this.context = context;
		this.callBack = callBack;
		//网络加载时的进度条,只是控制进度条,与其他逻辑无关
		this.progressDialog = progressDialog;
		this.destroyed = destroyed;
	}
	/*
	 *无需进度条的构造函数 
	 */
	public RequestHandler(Context context, ServerDataCallback callBack) {
		this.context = context;
		this.callBack = callBack;
		//网络加载时的进度条,只是控制进度条,与其他逻辑无关
		this.progressDialog = null;
		this.destroyed = null;
	}

	// 子类必须重写此方法,接受数据
	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message msg) {
		if(progressDialog != null){
			DialogUtil.dismissProgressDialog(progressDialog, destroyed);
		}
		if (msg.what == Constant.SUCCESS) {
			if (msg.obj == null) {
				DialogUtil.showInfoDialog(context, context.getString(R.string.net_error));
			}else if(msg.obj instanceof ActionResult && "fail".equals(((ActionResult)msg.obj).getStatus())){
				DialogUtil.showInfoDialog(context, ((ActionResult)msg.obj).getMessage());
			} 
			else {
				if(callBack!=null){
					callBack.processData(msg.obj, true);
				}
			}
		} else if (msg.what == Constant.NET_FAILED) {
			DialogUtil.showInfoDialog(context, context.getString(R.string.net_error));
		}
	}
}