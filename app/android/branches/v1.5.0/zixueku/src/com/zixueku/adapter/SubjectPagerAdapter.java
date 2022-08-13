package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zixueku.entity.Grade;
import com.zixueku.fragment.SuperSubjectCardFragment;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class SubjectPagerAdapter extends FragmentPagerAdapter {
	private List<Grade> grades;

	public SubjectPagerAdapter(FragmentManager fm, List<Grade> grades) {
		super(fm);
		this.grades = grades;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return grades.get(position).getName();
		// return TITLES[position];
	}

	@Override
	public int getCount() {
		return grades.size();
		// return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		// position代表的是在选项卡中的第几个
		return SuperSubjectCardFragment.newInstance(grades, position);
	}

	//http://lovelease.iteye.com/blog/2107296
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
}
