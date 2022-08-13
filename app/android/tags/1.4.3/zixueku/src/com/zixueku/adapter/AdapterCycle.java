package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zixueku.entity.ADInfo;
import com.zixueku.fragment.AdHeadFragment;

public class AdapterCycle extends FragmentPagerAdapter {
	private List<ADInfo> activityList;

	public AdapterCycle(FragmentManager fm,List<ADInfo> activityList) {
		super(fm);
		this.activityList = activityList;
	}

	@Override
	public Fragment getItem(int paramInt) {
		return AdHeadFragment.newInstance(activityList.get(paramInt).getImageUrl(),activityList.get(paramInt).getTitle(),activityList.get(paramInt).getUrl());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return activityList.size();
	}

	
}
