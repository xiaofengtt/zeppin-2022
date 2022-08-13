package com.zixueku.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class BaseFragment extends Fragment {
	protected FragmentActivity mActivity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (FragmentActivity) activity;
	}
}
