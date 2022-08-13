package com.zixueku.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.zixueku.entity.WrongBook;
import com.zixueku.fragment.SuperWrongBookCombinationJudgeCardFragment;
import com.zixueku.fragment.SuperWrongBookCombinationMultipleCardFragment;
import com.zixueku.fragment.SuperWrongBookCombinationSingleCardFragment;
import com.zixueku.fragment.UnSupportItemTypeCardFragment;

/**
 * 类说明 组合题模板分发
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-13 下午5:45:11
 */
public class WrongBookCombinationViewPagerAdapter extends FragmentStatePagerAdapter {
	private ViewPager parentViewPager;
	private ViewPager subViewPager;
	private int parentPosition;
	private WrongBook wrongBook;
	private static final String ARG_POSITION = "position";
	private static final String ARG_SUB_POSITION = "sub_position";

	public WrongBookCombinationViewPagerAdapter(FragmentManager fm, int parentPosition, WrongBook wrongBook, ViewPager parentViewPager, ViewPager subViewPager) {
		super(fm);
		this.wrongBook = wrongBook;
		this.parentViewPager = parentViewPager;
		this.subViewPager = subViewPager;
		this.parentPosition = parentPosition;
	}

	@Override
	public int getCount() {
		int size = 0;
		try{
			size = wrongBook.getWrongItemList().get(parentPosition).getChildren().size();
		}catch(Exception e){
			
		}
		return size;
	}

	@Override
	public Fragment getItem(int subPosition) {

		Fragment fragment = null;
		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		switch (wrongBook.getWrongItemList().get(parentPosition).getChildren().get(subPosition).getModelType()) {
		// 单选题
		case 1:
			fragment = SuperWrongBookCombinationSingleCardFragment.newInstance(parentViewPager, subViewPager);
			break;
		case 3:
			fragment =  SuperWrongBookCombinationJudgeCardFragment.newInstance(parentViewPager, subViewPager);
			break;
		case 5:
			fragment =  SuperWrongBookCombinationMultipleCardFragment.newInstance(parentViewPager, subViewPager);
			break;
		default:
			fragment = new UnSupportItemTypeCardFragment(wrongBook.getWrongItemList().get(parentPosition).getChildren().get(subPosition));
		}
		return fragment;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment f = (Fragment) super.instantiateItem(container, position);
		Bundle b = f.getArguments();
		if (b == null) {
			b = new Bundle();
			f.setArguments(b);
		}
		b.putInt(ARG_POSITION, parentPosition);
		b.putInt(ARG_SUB_POSITION, position);
		f.getArguments().putAll(b);
		//Fragment f = (Fragment) super.instantiateItem(container, position);//getItem(position);//(Fragment) super.instantiateItem(container, position);
	/*	Bundle b = new Bundle();
		b.putInt(ARG_POSITION, parentPosition);
		b.putInt(ARG_SUB_POSITION, position);
		f.getArguments().putAll(b);*/
		//f.setArguments(b);
		
		if(f instanceof SuperWrongBookCombinationSingleCardFragment){
			((SuperWrongBookCombinationSingleCardFragment) f).setPosition(parentPosition);
			((SuperWrongBookCombinationSingleCardFragment) f).setSubPosition(position);
		}else if(f instanceof SuperWrongBookCombinationMultipleCardFragment){
			((SuperWrongBookCombinationMultipleCardFragment) f).setPosition(parentPosition);
			((SuperWrongBookCombinationMultipleCardFragment) f).setSubPosition(position);
		}else if(f instanceof SuperWrongBookCombinationJudgeCardFragment){
			((SuperWrongBookCombinationJudgeCardFragment) f).setPosition(parentPosition);
			((SuperWrongBookCombinationJudgeCardFragment) f).setSubPosition(position);
		}
		return f;
	}

	public int getParentPosition() {
		return parentPosition;
	}

	public void setParentPosition(int parentPosition) {
		this.parentPosition = parentPosition;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}
}
