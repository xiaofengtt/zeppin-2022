package com.zixueku.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.activity.AnalysisActivity;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-24 上午10:23:54
 */
public class ResultGridViewAdapter extends BaseAdapter {
	private Activity context;
	private Exercise exercise;
	private LayoutInflater layoutInflater;

	public ResultGridViewAdapter(Activity context, Exercise exercise) {
		this.context = context;
		this.exercise = exercise;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return exercise.getTotalNum();
	}

	@Override
	public Object getItem(int position) {
		for (Item item : exercise.getItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					if (child.getIndex() == position) {
						return child;
					}
				}
			} else {
				if (item.getIndex() == position) {
					return item;
				}
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.result_grid_view_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.button = (ImageView) view.findViewById(R.id.result_grid_view_item_circle_button);
			viewHolder.textView = (TextView) view.findViewById(R.id.result_grid_view_item_text_view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Item item = (Item) getItem(position);
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.textView.setText(position + 1 + "");
		if (!item.isRight()) {
			holder.button.setImageResource(R.drawable.result_grid_view_false);
			// holder.textView.setTextColor(context.getResources().getColor(R.color.red));
		} else {
			holder.button.setImageResource(R.drawable.result_grid_view_true);
			// holder.textView.setTextColor(context.getResources().getColor(R.color.blue));
		}

		holder.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int parentIndex = -1;
				int subIndex = -1;
				boolean mark = false;
				for (Item item : exercise.getItems()) {
					parentIndex++;
					subIndex = -1;
					if (item.getModelType() == 4) {
						for (Item child : item.getChildren()) {
							subIndex++;
							if (child.getIndex() == position) {
								mark = true;
								break;
							}
						}
					} else {
						if (item.getIndex() == position) {
							break;
						}
					}
					if (mark) {
						break;
					}
				}
				
				
				
				Intent intent = new Intent(context, AnalysisActivity.class);
				intent.putExtra("parentIndex", parentIndex);
				intent.putExtra("subIndex", subIndex);
				context.startActivity(intent);
				context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		return view;
	}

	static class ViewHolder {
		protected ImageView button;
		protected TextView textView;
	}

}
