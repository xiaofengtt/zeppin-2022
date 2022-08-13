package com.zixueku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.Knowledge;
import com.zixueku.entity.TestAbilityChange;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-23 上午10:32:18
 */
public class ResultListViewAdapter extends BaseAdapter {
	private Context context;
	private TestAbilityChange testAbilityChange;
	private LayoutInflater layoutInflater;
	private int count;

	public ResultListViewAdapter(Context context, TestAbilityChange testAbilityChange) {
		this.context = context;
		this.testAbilityChange = testAbilityChange;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.count = testAbilityChange.getChangeKnowledges().size();
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return testAbilityChange.getChangeKnowledges().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.result_ability_change_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.knowledgeName = (TextView) view.findViewById(R.id.knowledge_name);
			
			viewHolder.userTestRightItemChangeCount = (TextView) view.findViewById(R.id.userTestRightItemChangeCount);
			viewHolder.tip = (TextView) view.findViewById(R.id.tip);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Knowledge knowledge = (Knowledge) getItem(position);
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		holder.knowledgeName.setText(knowledge.getName());
		int userTestRightItemChangeCount = knowledge.getRightChangeCount();
		if(userTestRightItemChangeCount>=0){
			holder.userTestRightItemChangeCount.setText("+"+userTestRightItemChangeCount);
			holder.userTestRightItemChangeCount.setTextColor(context.getResources().getColor(R.color.green));
		}else{
			holder.userTestRightItemChangeCount.setText(userTestRightItemChangeCount+"");
			holder.userTestRightItemChangeCount.setTextColor(context.getResources().getColor(R.color.red));
		}
		return view;
	}
	
	static class ViewHolder {
		protected TextView knowledgeName;
		protected TextView userTestRightItemChangeCount;
		protected TextView tip;
	}
}
