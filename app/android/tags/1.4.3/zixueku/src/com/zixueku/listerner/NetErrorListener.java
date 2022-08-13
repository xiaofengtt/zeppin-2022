package com.zixueku.listerner;

import android.content.Context;

import com.ab.util.AbDialogUtil;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.zixueku.util.DialogUtil;
import com.zixueku.util.VolleyErrorHelper;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年10月9日 下午4:19:25
 */
public class NetErrorListener implements ErrorListener {
	private Context context;

	public NetErrorListener(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		AbDialogUtil.removeDialog(context);
		DialogUtil.showInfoDialog(context, VolleyErrorHelper.getMessage(error, context));
	}

}
