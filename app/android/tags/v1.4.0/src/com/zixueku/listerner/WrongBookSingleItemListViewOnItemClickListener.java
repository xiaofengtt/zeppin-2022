package com.zixueku.listerner;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ms.square.android.expandabletextview.ExpandableTextView2;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.Option;
import com.zixueku.entity.WrongBook;
import com.zixueku.util.BusinessCommonMethod;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月24日 下午8:29:11
 */
public class WrongBookSingleItemListViewOnItemClickListener implements OnItemClickListener {
	private WrongBook wrongBookInfo;
	private int position;
	private int subPosition;
	private ExpandableTextView2 analysis;
	private boolean isCombination;// 是否为组合题
	private Context context;

	public WrongBookSingleItemListViewOnItemClickListener(Context context,WrongBook wrongBookInfo, ExpandableTextView2 analysis, int... position) {
		super();
		this.wrongBookInfo = wrongBookInfo;
		this.position = position[0];
		if (position.length == 2) {
			this.subPosition = position[1];
			isCombination = true;
		}
		this.analysis = analysis;
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
		for (Option option : item.getData().getOptions()) {
			option.setSelected(false);
		}
		item.getCustomerAnswer().clear();
		CustomerAnswer customerAnswer = new CustomerAnswer(item.getData().getOptions().get(pos).getInx());
		item.getCustomerAnswer().add(customerAnswer);
		item.getData().getOptions().get(pos).setSelected(true);
		BusinessCommonMethod.wrongBookSubmitItem(context, item);
		analysis.expandText();
	}
}
