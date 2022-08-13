package com.zixueku.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zixueku.entity.Item;
import com.zixueku.fragment.SuperMaterialCardFragment;
import com.zixueku.fragment.SuperMaterialCombinationCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明
 * 大题特训试题适配器
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class MaterialPagerAdapter extends FragmentPagerAdapter {
	private List<Item> itemList;
	public MaterialPagerAdapter(FragmentManager fm,List<Item> itemList) {
		super(fm);
		this.itemList = itemList;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (itemList.get(position).getModelType()) {
		// 问答题
		case 6:
			fragment = SuperMaterialCardFragment.newInstance(position);
			break;
		case 4:
			fragment = SuperMaterialCombinationCardFragment.newInstance(position);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(itemList.get(position));
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

}
