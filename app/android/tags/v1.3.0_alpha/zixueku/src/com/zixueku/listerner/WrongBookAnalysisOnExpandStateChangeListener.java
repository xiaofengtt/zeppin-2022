package com.zixueku.listerner;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView2.OnExpandStateChangeListener;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardJudgeItemAdapter;
import com.zixueku.adapter.AnalysisCardMultipleItemAdapter;
import com.zixueku.adapter.AnalysisCardSingleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.Result;
import com.zixueku.entity.WrongBook;

/**
 * 类说明 错题本中 当答案解析的按钮展开时触发的事件
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月27日 下午3:56:36
 */
public class WrongBookAnalysisOnExpandStateChangeListener implements OnExpandStateChangeListener {
	private Context context;
	private ListView listView;
	private WrongBook wrongBookInfo;
	private int position;
	private int subPosition;
	private boolean isCombination = false; // 是否为组合题

	public WrongBookAnalysisOnExpandStateChangeListener(Context context, ListView listView, WrongBook wrongBookInfo, int... position) {
		super();
		this.context = context;
		this.listView = listView;
		this.wrongBookInfo = wrongBookInfo;
		this.position = position[0];
		if (position.length == 2) {
			this.subPosition = position[1];
			this.isCombination = true;
		}
	}

	@Override
	public void onExpandStateStart(TextView textView, boolean isExpanded) {
		Item item;

		if (isCombination) {
			wrongBookInfo.getWrongItemList().get(position).getChildren().get(subPosition).setAnalysisIsRead(true);
			item = wrongBookInfo.getWrongItemList().get(position).getChildren().get(subPosition);
		} else {
			wrongBookInfo.getWrongItemList().get(position).setAnalysisIsRead(true);
			item = wrongBookInfo.getWrongItemList().get(position);
		}

		BaseAdapter adapter = null;
		/*
		 * 1单选择 2填空 3判断 4组合 5多选 6问答
		 */
		if (item.getModelType() == 1) {
			adapter = new AnalysisCardSingleItemAdapter(context, item);
		} else if (item.getModelType() == 5) {
			judge(item);
			adapter = new AnalysisCardMultipleItemAdapter(context, item);
		} else if (item.getModelType() == 3) {
			adapter = new AnalysisCardJudgeItemAdapter(context, item);
		}

		listView.setAdapter(adapter);
		listView.setSelector(R.color.transparent);
		listView.setOnItemClickListener(null);

	}

	@Override
	public void onExpandStateChanged(TextView textView, boolean isExpanded) {

	}

	// 在多选题中 adapter需要知道item是答对,
	private void judge(Item item) {
		Set<CustomerAnswer> customerAnswer = item.getCustomerAnswer();
		List<Result> resultList = item.getData().getResults(); // 正确答案
		String[] rightAnswer = resultList.get(0).getInx().split(",");
		if (customerAnswer.size() != rightAnswer.length) {
			item.setRight(false);
			return;
		}
		item.setRight(true);
		for (CustomerAnswer answer : customerAnswer) {
			if (!contains(rightAnswer, answer.getInx())) {
				item.setRight(false);
				break;
			}
		}
	}

	private boolean contains(String[] rightAnswer, String customerAnswer) {
		boolean mark = false;
		for (String result : rightAnswer) {
			if (result.equals(customerAnswer)) {
				mark = true;
				break;
			}
		}
		return mark;
	}

}
