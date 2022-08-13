package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.entity.Item;
import com.zixueku.fragment.SuperExerciseCombinationJudgeCardFragment;
import com.zixueku.fragment.SuperExerciseCombinationMultipleCardFragment;
import com.zixueku.fragment.SuperExerciseCombinationSingleCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 组合题模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class ExerciseCombinationPagerAdapter extends FragmentStatePagerAdapter {
	private List<Item> children;
	private ViewPager parentViewPager;
	private ViewPager subViewPager;
	private PagerSlidingTabStrip2 tab;
	private int parentPosition;

	public ExerciseCombinationPagerAdapter(FragmentManager fm, int parentPosition, List<Item> children, ViewPager parentViewPager, ViewPager subViewPager, PagerSlidingTabStrip2 tab) {
		super(fm);
		this.children = children;
		this.parentViewPager = parentViewPager;
		this.subViewPager = subViewPager;
		this.tab = tab;
		this.parentPosition = parentPosition;
	}

	@Override
	public int getCount() {
		return children.size();
	}

	@Override
	public Fragment getItem(int subPosition) {
		Fragment fragment = null;

		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (children.get(subPosition).getModelType()) {
		// 单选题
		case 1:
			fragment = SuperExerciseCombinationSingleCardFragment.newInstance(parentPosition, subPosition, parentViewPager, subViewPager, tab);
			break;
		case 5:
			fragment = SuperExerciseCombinationMultipleCardFragment.newInstance(parentPosition, subPosition, parentViewPager, subViewPager, tab);
			break;
		case 3:
			fragment = SuperExerciseCombinationJudgeCardFragment.newInstance(parentPosition, subPosition, parentViewPager, subViewPager, tab);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(children.get(subPosition));
		}
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

}
