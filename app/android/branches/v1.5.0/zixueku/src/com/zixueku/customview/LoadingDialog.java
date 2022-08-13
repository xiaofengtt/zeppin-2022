package com.zixueku.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zixueku.R;

/**
 * 
 * @author Crist
 *
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;

	private ImageView tips_loading_img;

	private String message = null;

	private Drawable drawable = null;

	public LoadingDialog(Context context) {
		super(context);
		message = getContext().getResources().getString(R.string.loading);
		init(message);
	}

	public LoadingDialog(Context context, String message) {
		super(context);
		init(message);
	}

	public LoadingDialog(Context context, String message, Drawable drawable) {
		super(context);
		initImage(drawable, message);
	}

	public LoadingDialog(Context context, int theme, String message) {
		super(context, theme);
		init(message);
	}

	private void init(String message) {
		this.message = message;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
	}

	private void initImage(Drawable drawable, String message) {
		this.drawable = drawable;
		this.message = message;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_tips_loading);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_img = (ImageView) findViewById(R.id.tips_loading_img);
		tips_loading_img.setImageDrawable(this.drawable);
		tips_loading_msg.setText(this.message);
	}

	public void setText(String message) {
		this.message = message;
	}

	public void setImageResouce(Drawable drawable) {
		this.drawable = drawable;
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

	public void setImageResouce(int resId) {
		setImageResouce(getContext().getResources().getDrawable(resId));
	}

}
