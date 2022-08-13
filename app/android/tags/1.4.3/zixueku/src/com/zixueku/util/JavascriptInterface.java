package com.zixueku.util;

import com.zixueku.activity.ShowWebImageActivity;

import android.content.Context;
import android.content.Intent;

public class JavascriptInterface {
	private Context context;

	public JavascriptInterface(Context context) {
		this.context = context;
	}

	public void openImage(String img) {
		Intent intent = new Intent();
		intent.putExtra("image", img);
		intent.setClass(context, ShowWebImageActivity.class);
		context.startActivity(intent);
	}
}
