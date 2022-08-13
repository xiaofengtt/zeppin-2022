package com.zixueku.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip2;
import com.astuetz.PagerSlidingTabStrip2.IconTabProvider;
import com.zixueku.entity.Exercise;
import com.zixueku.fragment.HandPaperFragment;
import com.zixueku.fragment.SuperCombinationCardFragment;
import com.zixueku.fragment.SuperExerciseJudgeCardFragment;
import com.zixueku.fragment.SuperExerciseMultipleCardFragment;
import com.zixueku.fragment.SuperExerciseSingleCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 答题模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class ExercisePagerAdapter extends FragmentPagerAdapter implements IconTabProvider {
	private Exercise exercise;
	private ViewPager viewPager;
	private PagerSlidingTabStrip2 tab;

	public ExercisePagerAdapter(FragmentManager fm, ViewPager viewPager, PagerSlidingTabStrip2 tab, Exercise exercise) {
		super(fm);
		this.viewPager = viewPager;
		this.tab = tab;
		this.exercise = exercise;
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
		return exercise.getItems().size() + 1;
	}

	@Override
	public Fragment getItem(int position) {
		// position代表的是在选项卡中的第几个
		// return SuperExerciseSingleCardFragment.newInstance(exercise,
		// position,viewPager);
		Fragment fragment = null;

		if (position == exercise.getItems().size()) {
			return new HandPaperFragment(viewPager);
		}
		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (exercise.getItems().get(position).getModelType()) {
		// 单选题
		case 1:
			fragment = SuperExerciseSingleCardFragment.newInstance(position, viewPager, tab);
			break;
		// 多选题
		case 5:
			fragment = SuperExerciseMultipleCardFragment.newInstance(position, viewPager, tab);
			break;
		case 3:
			fragment = SuperExerciseJudgeCardFragment.newInstance(position, viewPager, tab);
			break;
		//  组合题
		case 4:
			fragment = SuperCombinationCardFragment.newInstance(position, viewPager, tab);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(exercise.getItems().get(position));
		}
		return fragment;
	}

}
