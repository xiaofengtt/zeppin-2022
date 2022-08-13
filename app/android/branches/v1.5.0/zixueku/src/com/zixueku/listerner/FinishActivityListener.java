package com.zixueku.listerner;

import com.zixueku.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 类说明
 * 单击按钮时关闭指定的activity
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-26 上午11:38:51
 */
public class FinishActivityListener implements OnClickListener {
	private Activity activity;
	private int enterAnim;
	private int exitAnim;

	public FinishActivityListener(Activity activity) {
		super();
		this.activity = activity;
		this.enterAnim = R.anim.left_in;
		this.exitAnim = R.anim.right_out;
	}
	
	public FinishActivityListener(Activity activity, int enterAnim, int exitAnim) {
		super();
		this.activity = activity;
		this.enterAnim = enterAnim;
		this.exitAnim = exitAnim;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		activity.finish();
		activity.overridePendingTransition(enterAnim, exitAnim);
	}
}
