package com.zixueku.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.zixueku.customview.LoadingDialog;
import com.zixueku.util.ToastUtil;

/**
 * 
 * @author Crist
 * 
 */
public abstract class BaseFragment extends Fragment {
	protected Context mContext;
	public View rootView;
	protected LoadingDialog ld;
	protected FragmentActivity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (FragmentActivity) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = initView(inflater);
		ViewUtils.inject(this, rootView); // 注入view和事件
		ld = new LoadingDialog(getActivity()); // 初始化进度条
		return rootView;
	}

	public View getRootView() {
		return rootView;
	}

	/**
	 * 初始化handler
	 */
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			initHandler(msg);
		};
	};

	/**
	 * 业务数据处理后的回调, 一般用于异步网络请求完成后执行的界面刷新
	 */
	protected abstract void initHandler(android.os.Message msg);

	/**
	 * 加载进fragment的view
	 * 
	 * @param inflater
	 * @return 返回要显示的view
	 */
	protected abstract View initView(LayoutInflater inflater);

	/**
	 * 初始化界面数据
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initData(Bundle savedInstanceState);

	protected void showToast(String content) {
		ToastUtil.showTextInMiddle(mContext, content, 1);
	}

	protected void showToast(int resId) {
		ToastUtil.showTextInMiddle(mContext, resId, 1);
	}

	public void showLoadingDiaglog(Context context, String tip) {
		if (ld == null) {
			ld = new LoadingDialog(context);
		}
		if (!TextUtils.isEmpty(tip)) {
			ld.setText(tip);
		}
		ld.show();
	}

	/**
	 * 显示正在加载数据的dialog
	 */
	public void showLoadingDiaglog(Context context, int resId) {
		if (ld == null) {
			ld = new LoadingDialog(context);
		}
		ld.setText(resId);
		ld.show();
	}

	/**
	 * 关闭正在显示的dialog
	 */
	public void closeLoadingDialog(Context context) {
		if (ld == null) {
			ld = new LoadingDialog(context);
		}
		if (ld.isShowing()) {
			ld.dismiss();
		}
	}
}
