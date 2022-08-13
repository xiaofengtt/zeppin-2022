package com.zixueku.listerner;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.zixueku.R;

/**
 * 类说明 单击按钮时activity之间的跳转监听类
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-26 上午11:49:42
 */
public class StartActivityFromResultActivityListener implements OnClickListener {

	private Context from;
	private Class<?> to;
	private int enterAnim;
	private int exitAnim;
	private Map<?, ?> params;
	private int requestCode;

	public StartActivityFromResultActivityListener(Context from, Class<?> to, int requestCode) {
		this.from = from;
		this.to = to;
		this.enterAnim = R.anim.right_in;
		this.exitAnim = R.anim.left_out;
		this.requestCode = requestCode;
	}

	public StartActivityFromResultActivityListener(Context from, Class<?> to, Map<?, ?> params, int requestCode) {
		this.from = from;
		this.to = to;
		this.enterAnim = R.anim.right_in;
		this.exitAnim = R.anim.left_out;
		this.params = params;
		this.requestCode = requestCode;
	}

	public StartActivityFromResultActivityListener(Context from, Class<?> to, int enterAnim, int exitAnim, int requestCode) {
		this.from = from;
		this.to = to;
		this.enterAnim = enterAnim;
		this.exitAnim = exitAnim;
		this.requestCode = requestCode;
	}

	public StartActivityFromResultActivityListener(Context from, Class<?> to, int enterAnim, int exitAnim, Map<?, ?> params, int requestCode) {
		this.from = from;
		this.to = to;
		this.enterAnim = enterAnim;
		this.exitAnim = exitAnim;
		this.params = params;
		this.requestCode = requestCode;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(from, to);
		if (params != null) {
			Iterator<?> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
				intent.putExtra(entry.getKey().toString(), (Serializable) entry.getValue());
			}
		}

		((Activity) from).startActivityForResult(intent, requestCode);
		((Activity) from).overridePendingTransition(enterAnim, exitAnim);
	}

}
