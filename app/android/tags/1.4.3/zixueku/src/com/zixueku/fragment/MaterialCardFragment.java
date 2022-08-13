package com.zixueku.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.MaterialCardGridViewAdapter;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.util.App;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月21日 下午1:42:39
 */
public class MaterialCardFragment extends BaseFragment {
	private Material material;
	private ViewPager viewPager;
	private int notSeeNum;
	
	

	public MaterialCardFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MaterialCardFragment(ViewPager viewPager) {
		super();
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		material =App.getInstance().getMaterial();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		notSeeNum = 0;
		View view = inflater.inflate(R.layout.material_card_layout, container, false);
		TextView title = (TextView) view.findViewById(R.id.material_card_title);
		GridView gridView = (GridView) view.findViewById(R.id.material_card_grid_view);
		gridView.setAdapter(new MaterialCardGridViewAdapter(mActivity, material, viewPager));
		calculate();
		int totalNum = material.getTotalNum();
		int readNum = totalNum - notSeeNum;
		title.setText(String.format("已看%d道,未看%d道,共%d道", readNum, notSeeNum, totalNum));
		return view;
	}

	// 计算答题情况
	// completeType -1没看过,0未掌握(已看答案解析但未标注),1掌握
	private void calculate() {
		for (Item item : material.getMaterialItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					choice(child);
				}
			} else {
				choice(item);
			}
		}
	}

	// flag -1没看过,0未掌握(已看答案解析但未标注),1掌握
	private void choice(Item item) {
		int completeType = item.getCompleteType();
		switch (completeType) {
		case -1:
			notSeeNum++;
			break;
		}
	}
}
