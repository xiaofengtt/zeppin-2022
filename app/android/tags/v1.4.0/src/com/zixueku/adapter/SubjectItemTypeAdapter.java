package com.zixueku.adapter;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.zixueku.R;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.adapter.PrepareExamsAdapter.ViewHolder;
import com.zixueku.entity.Knowledge;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.SubjectItemType;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.daimajia.numberprogressbar.NumberProgressBar;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:34:42
 */
public class SubjectItemTypeAdapter extends BaseAdapter {

	private List<SubjectItemType> itemTypeList;

	/** LayoutInflater */
	private LayoutInflater layoutInflater;

	private Activity activity;

	public SubjectItemTypeAdapter(Activity activity, List<SubjectItemType> itemTypeList) {
		super();
		this.activity = activity;
		this.itemTypeList = itemTypeList;
		this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return itemTypeList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemTypeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.subject_item_type_item, parent, false);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		SubjectItemType item = itemTypeList.get(position);
		holder.typeName.setText(item.getItemTypeName());
		holder.finishNum.setText(item.getLastRightItemCount()+"");
		holder.totleNum.setText(item.getReleasedItemCount()+"");
		holder.progressBar.setMax(item.getReleasedItemCount());
		holder.progressBar.setProgress(item.getLastRightItemCount());
		return convertView;
	}

	static class ViewHolder {
		protected TextView typeName;
		protected TextView finishNum;
		protected TextView totleNum;
		protected NumberProgressBar progressBar;

		ViewHolder(View view) {
			this.typeName = (TextView) view.findViewById(R.id.subject_item_name);
			this.finishNum = (TextView) view.findViewById(R.id.subject_finish_count);
			this.totleNum = (TextView) view.findViewById(R.id.subject_item_count);
			this.progressBar = (NumberProgressBar) view.findViewById(R.id.subject_item_type_progress_bar);
			view.setTag(this);
		}
	}

}
