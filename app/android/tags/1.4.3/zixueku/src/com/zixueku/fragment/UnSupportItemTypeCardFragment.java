package com.zixueku.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.Item;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-9 下午6:08:23
 */
public class UnSupportItemTypeCardFragment extends BaseFragment {

	private Item item;
	private TextView content;

	private TextView typeName;
	private TextView TypeId;

	public UnSupportItemTypeCardFragment() {
		super();
	}

	public UnSupportItemTypeCardFragment(Item item) {
		this.item = item;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("item")) {
				item = (Item) savedInstanceState.getSerializable("item");
			}
		}

		View contact = inflater.inflate(R.layout.un_support_item_type, container, false);
		typeName = (TextView) contact.findViewById(R.id.un_support_item_type_name);
		TypeId = (TextView) contact.findViewById(R.id.un_support_item_type_id);
		typeName.setText(item.getItemTypeName());
		TypeId.setText(item.getItemTypeId() + "");
		return contact;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("item", item);
	}
}
