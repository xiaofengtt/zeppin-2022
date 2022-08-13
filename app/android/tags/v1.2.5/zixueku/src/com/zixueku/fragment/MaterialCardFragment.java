package com.zixueku.fragment;

import com.zixueku.R;
import com.zixueku.adapter.MaterialCardGridViewAdapter;
import com.zixueku.entity.Material;
import com.zixueku.util.App;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/** 
 * 类说明 
 * @author  Email: huangweidong@zeppin.cn
 * @version V1.0  创建时间：2015年7月21日 下午1:42:39 
 */
public class MaterialCardFragment extends Fragment {
	private Material material;
	private ViewPager viewPager;
	
	public MaterialCardFragment(ViewPager viewPager) {
		super();
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		material = ((App)(getActivity().getApplication())).getMaterial();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.material_card_layout, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.material_card_grid_view);
		gridView.setAdapter(new MaterialCardGridViewAdapter(getActivity(), material, viewPager));
		return view;
	}
	

}
