package com.zixueku.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-24 上午10:23:54
 */
public class HandPaperAdapter extends BaseAdapter {
	private Activity context;
	private Exercise exercise;
	private LayoutInflater layoutInflater;
	private ViewPager viewPager;

	public HandPaperAdapter(Activity context, Exercise exercise, ViewPager viewPager) {
		this.context = context;
		this.exercise = exercise;
		this.viewPager = viewPager;
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
			view = layoutInflater.inflate(R.layout.hand_page_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.button = (ImageView) view.findViewById(R.id.hand_paper_item_circle_button);
			viewHolder.textView = (TextView) view.findViewById(R.id.hand_paper_item_text_view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final Item item = (Item) getItem(position);
		final ViewHolder holder = (ViewHolder) view.getTag();
		holder.textView.setText(position + 1 + "");
		if (item.getCustomerAnswer().isEmpty()) {
			holder.button.setImageResource(R.drawable.hand_paper_un_selected);
			holder.textView.setTextColor(context.getResources().getColor(R.color.buttongrey));
		} else {
			holder.button.setImageResource(R.drawable.hand_paper_selected);
			holder.textView.setTextColor(context.getResources().getColor(R.color.white));
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
				viewPager.setCurrentItem(parentIndex);
				if (subIndex != -1) {
					ExerciseActivity.subViewPagers.get(parentIndex).setCurrentItem(subIndex);
				}

			}
		});

		holder.button.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if (action == MotionEvent.ACTION_DOWN && item.getCustomerAnswer().isEmpty()) {
					// 更改为按下时的背景图片
					holder.textView.setTextColor(context.getResources().getColor(R.color.white));
				} else if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) && item.getCustomerAnswer().isEmpty()) {
					// 改为抬起时的图片
					holder.textView.setTextColor(context.getResources().getColor(R.color.buttongrey));
				}
				return false;
			}
		});

		return view;
	}

	static class ViewHolder {
		protected ImageView button;
		protected TextView textView;
	}

}
