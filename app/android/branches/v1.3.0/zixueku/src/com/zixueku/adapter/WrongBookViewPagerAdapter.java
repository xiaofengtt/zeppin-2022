package com.zixueku.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zixueku.entity.Item;
import com.zixueku.entity.WrongBook;
import com.zixueku.fragment.SuperWrongBookCombinationCardFragment;
import com.zixueku.fragment.SuperWrongBookJudgeCardFragment;
import com.zixueku.fragment.SuperWrongBookMultipleCardFragment;
import com.zixueku.fragment.SuperWrongBookSingleCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 错题本题型分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class WrongBookViewPagerAdapter extends FragmentStatePagerAdapter {
	private static final String ARG_POSITION = "position";
	private WrongBook wrongBookInfo;
	private ViewPager viewPager;

	public WrongBookViewPagerAdapter(FragmentManager fm, ViewPager viewPager, WrongBook wrongBookInfo) {
		super(fm);
		this.viewPager = viewPager;
		this.wrongBookInfo = wrongBookInfo;
	}

	@Override
	public int getCount() {
		return wrongBookInfo.getWrongItemList().size();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		Item item = wrongBookInfo.getWrongItemList().get(position);
		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (item.getModelType()) {
		// 单选题
		case 1:
			fragment = SuperWrongBookSingleCardFragment.newInstance(viewPager);
			break;
		case 3:
			fragment = SuperWrongBookJudgeCardFragment.newInstance(viewPager);
			break;
		// 多选题
		case 5:
			fragment = SuperWrongBookMultipleCardFragment.newInstance(viewPager);
			break;
		// 组合题
		case 4:
			fragment = SuperWrongBookCombinationCardFragment.newInstance(viewPager);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(wrongBookInfo.getWrongItemList().get(position));
		}
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		/*
		 * Fragment f = (Fragment) super.instantiateItem(container, position);
		 * Bundle b = f.getArguments(); if (b == null) { b = new Bundle();
		 * f.setArguments(b); } b.putInt(ARG_POSITION, position);
		 * f.getArguments().putAll(b);
		 */
		Fragment f = (Fragment) super.instantiateItem(container, position);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

}
