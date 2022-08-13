package com.zixueku.listerner;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.zixueku.R;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.WrongBook;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月24日 下午8:29:11
 */
public class WrongBookMultipleItemListViewOnItemClickListener implements OnItemClickListener {
	private WrongBook wrongBookInfo;
	private int position;
	private int subPosition;
	private ListView listView;
	private boolean isCombination;
	private Button button;
	private Context context;

	public WrongBookMultipleItemListViewOnItemClickListener(Context context,WrongBook wrongBookInfo,Button button, ListView listView, int ... position) {
		super();
		this.wrongBookInfo = wrongBookInfo;
		this.position = position[0];
		if (position.length == 2) {
			this.subPosition = position[1];
			isCombination = true;
		}
		this.listView = listView;
		this.button = button;
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Item item;
		if (isCombination) {
			item = wrongBookInfo.getWrongItemList().get(position).getChildren().get(subPosition);
		} else {
			item = wrongBookInfo.getWrongItemList().get(position);
		}
		Boolean isChecked =item.getData().getOptions().get(pos).isSelected();
		item.getData().getOptions().get(pos).setSelected(!isChecked);
		String optionItemId = item.getData().getOptions().get(pos).getInx();
		CustomerAnswer customerAnswer = new CustomerAnswer(optionItemId);
		if (isChecked) {
			item.getCustomerAnswer().remove(customerAnswer);
		} else {
			item.getCustomerAnswer().add(customerAnswer);
		}
		((BaseAdapter)(listView.getAdapter())).notifyDataSetChanged();
		controlNextButtonStyle(item);
	}
	
	private void controlNextButtonStyle(Item item) {
		if (item.getCustomerAnswer().isEmpty()) {
			button.setClickable(false);
			button.setBackgroundColor(context.getResources().getColor(R.color.grey));
			button.setTextColor(context.getResources().getColor(R.color.black));
		} else {
			button.setClickable(true);
			button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_button_background));
			button.setTextColor(context.getResources().getColor(R.color.white));
		}
	}
}
