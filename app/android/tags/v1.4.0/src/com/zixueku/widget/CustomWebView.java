package com.zixueku.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年8月4日 下午2:56:31
 */
public class CustomWebView extends WebView {

	private PlayFinish pf;

	public CustomWebView(Context context) {
		super(context);
		this.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (pf != null) {
			pf.After();
		}
	}

	public void SetPf(PlayFinish pf) {
		this.pf = pf;
	}

	public interface PlayFinish {
		public void After();
	}

}
