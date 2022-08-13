package com.zixueku.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	protected Activity mActivity;
	
	public BaseFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (Activity) activity;
	}
}
