package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zixueku.entity.Item;
import com.zixueku.fragment.SuperMaterialCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 材料题中的组合题模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class MaterialCombinationPagerAdapter extends FragmentPagerAdapter {
	private List<Item> children;
	private int parentPosition;

	public MaterialCombinationPagerAdapter(FragmentManager fm, int parentPosition, List<Item> children) {
		super(fm);
		this.children = children;
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
		// 问答
		case 6:
			fragment = SuperMaterialCardFragment.newInstance(parentPosition, subPosition);
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
