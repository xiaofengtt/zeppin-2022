package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.fragment.SuperAnalysisChoiceCardFragment;
import com.zixueku.fragment.SuperAnalysisJudgeCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 组合题模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class AnalysisCombinationPagerAdapter extends FragmentStatePagerAdapter {
	private List<Item> children;
	private int parentPosition;
	private Exercise exercise;

	public AnalysisCombinationPagerAdapter(FragmentManager fm, Exercise exercise,int parentPosition, List<Item> children) {
		super(fm);
		this.children = children;
		this.parentPosition = parentPosition;
		this.exercise = exercise;
	}

	/*
	 * @Override public CharSequence getPageTitle(int position) { return
	 * category.getGrades().get(position).getName(); // return TITLES[position];
	 * }
	 */

	@Override
	public int getCount() {
		return children.size();
	}

	@Override
	public Fragment getItem(int subPosition) {
		// position代表的是在选项卡中的第几个
		// return SuperExerciseSingleCardFragment.newInstance(exercise,
		// position,viewPager);
		Fragment fragment = null;

		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (children.get(subPosition).getModelType()) {
		// 单选题  多选题
		case 1:
		case 5:
			fragment = SuperAnalysisChoiceCardFragment.newInstance(exercise,parentPosition, subPosition);
			break;
		case 3:
			fragment = SuperAnalysisJudgeCardFragment.newInstance(exercise,parentPosition, subPosition);
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
