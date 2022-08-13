package com.zixueku.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.IPagerSlidingTabStrip;
import com.astuetz.PagerSlidingTabStrip3;
import com.astuetz.PagerSlidingTabStrip3.IconTabProvider;
import com.zixueku.entity.Exercise;
import com.zixueku.fragment.SuperAnalysisChoiceCardFragment;
import com.zixueku.fragment.SuperAnalysisCombinationCardFragment;
import com.zixueku.fragment.SuperAnalysisJudgeCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 试题解析模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class AnalysisPagerAdapter extends FragmentPagerAdapter implements IconTabProvider {
	private Exercise exercise;
	private ViewPager viewPager;
	private IPagerSlidingTabStrip tab;

	public AnalysisPagerAdapter(FragmentManager fm, Exercise exercise, ViewPager viewPager,IPagerSlidingTabStrip tab) {
		super(fm);
		this.exercise = exercise;
		this.viewPager = viewPager;
		this.tab = tab;
	}

	/*
	 * @Override public CharSequence getPageTitle(int position) { return
	 * category.getGrades().get(position).getName(); // return TITLES[position];
	 * }
	 */

	@Override
	public int getPageIconResId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return exercise.getItems().size();
	}

	@Override
	public Fragment getItem(int position) {
		// position代表的是在选项卡中的第几个
		// return SuperExerciseSingleCardFragment.newInstance(exercise,
		// position,viewPager);
		Fragment fragment = null;

		switch (exercise.getItems().get(position).getModelType()) {
		// 单选题 和多选题公用一个模板
		case 1:
			
		// 多选题
		case 5:
			fragment = SuperAnalysisChoiceCardFragment.newInstance(exercise,position);
			break;
		case 3:
			fragment = SuperAnalysisJudgeCardFragment.newInstance(position);
			break;
		// 组合题
		case 4:
			fragment = SuperAnalysisCombinationCardFragment.newInstance(exercise, position, viewPager,tab);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(exercise.getItems().get(position));
			break;
		}
		return fragment;
	}

}
