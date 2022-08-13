package com.zixueku.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zixueku.activity.LoginActivity;
import com.zixueku.entity.Request;
import com.zixueku.entity.Status;

/**
 * 类说明
 * 用来与服务器连接的子线程
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-5 上午11:18:01
 */
public class RequestTask implements Runnable {
	//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	//StrictMode.setThreadPolicy(policy); 
	private Context context;
	private Request req;
	private Handler handler;

	public RequestTask(Request req, Handler handler) {
		this.context = req.getContext();
		this.req = req;
		this.handler = handler;
	}

	@Override
	public void run() {
		Object obj = null;
		Message msg = Message.obtain();
		try {
			if (NetUtil.hasNetwork(context)) {
				obj = NetUtil.post(req);
				if (obj instanceof Status) {
					Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
					intent.putExtra("notlogin", "notlogin");
					context.startActivity(intent);
				} else {
					msg.what = Constant.SUCCESS;
					msg.obj = obj;
					handler.sendMessage(msg);  //把结果回调给handle对象
				}
			} else {
				msg.what = Constant.NET_FAILED;
				msg.obj = obj;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			Log.e("RequestTask", e.toString(), e);
			throw new RuntimeException(e);
		}
	}
}