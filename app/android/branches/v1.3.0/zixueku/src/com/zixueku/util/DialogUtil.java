package com.zixueku.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

	public static void showLoadingProgressDialog(Context context, ProgressDialog progressDialog) {
		showProgressDialog(context, progressDialog, "正在加载，请稍候......");
	}

	/**
	 * 显示进度条对话框
	 * @param context  上下文
	 * @param progressDialog   进度条
	 * @param message   显示内容
	 */
	public static void showProgressDialog(Context context, ProgressDialog progressDialog, CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setIndeterminate(true);
		}
		progressDialog.setMessage(message);
		progressDialog.show();
	}

	/**
	 * 销毁进度条对话框
	 * @param progressDialog   进度条
	 * @param destroyed     标示调用者activity是否已经销毁，
	 * 若activity已经销毁，那么此时再调用progressDialog.dismiss()将出错
	 */
	public static void dismissProgressDialog(ProgressDialog progressDialog, boolean destroyed) {
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}

	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}
	
	public static void showInfoDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
		showInfoDialog(context, message, "提示", "确定", onClickListener);
	}

	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}

}
