package com.zixueku.util;

import com.ab.util.AbDialogUtil;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Request;
import com.zixueku.listerner.NetErrorListener;

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
		serverConnection(req, callBack, null);
	}

	// 不带loading的请求，自定义错误处理
	// 如需要带loading请求，并自行处理错误信息，则需要在调用该方法前自行添加loading效果，并自行处理后续情况
	public static void sendDataToServerNoProgressDialog(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		serverConnection(req, callBack, errorListener);
	}

	public static void sendDataToServerNoProgressDialogIgnoError(Request req, ServerDataCallback<?> callBack) {
		serverConnection(req, callBack, null);
	}

	public static void sendDataToServerWithProgressDialog(Request req, ServerDataCallback<?> callBack) {
		NetErrorListener errorListerner = new NetErrorListener(req.getContext());
		AbDialogUtil.showProgressDialog(req.getContext(), 0, "数据加载中，请稍候...");
		serverConnection(req, callBack, errorListerner);
	}

	protected static void serverConnection(Request req, ServerDataCallback<?> callBack, ErrorListener errorListener) {
		MyJsonRequest myJson = new MyJsonRequest(req, callBack, errorListener);
		mRequestQueue.add(myJson);
	}

}
