package com.zixueku.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.Option;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class ExerciseCardJudgeItemAdapter extends ArrayAdapter<Option> {
	private List<Option> list;
	private LayoutInflater layoutInflater;
	private Context context;
	private int selectedIndex = -1;
	private static Drawable rightIconDrawable;
	private static Drawable errorIconDrawable;
	private static Drawable rightIconDrawable2;
	private static Drawable errorIconDrawable2;

	public ExerciseCardJudgeItemAdapter(Activity context, List<Option> list) {
		super(context, R.layout.exercise_item_judge, list);
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;

		rightIconDrawable = context.getResources().getDrawable(R.drawable.judge_right_icon);
		errorIconDrawable = context.getResources().getDrawable(R.drawable.judge_error_icon);
		rightIconDrawable2 = context.getResources().getDrawable(R.drawable.judge_right_icon2);
		errorIconDrawable2 = context.getResources().getDrawable(R.drawable.judge_error_icon2);

		int imgSize = context.getResources().getDimensionPixelSize(R.dimen.judge_button);
		rightIconDrawable.setBounds(0, 0, imgSize, imgSize);
		errorIconDrawable.setBounds(0, 0, imgSize, imgSize);
		rightIconDrawable2.setBounds(0, 0, imgSize, imgSize);
		errorIconDrawable2.setBounds(0, 0, imgSize, imgSize);
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Option getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.exercise_item_judge, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.radioButton = (RadioButton) view.findViewById(R.id.judge_radio_button);

			viewHolder.content = (TextView) view.findViewById(R.id.exercise_judge_item_content);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ViewHolder holder = (ViewHolder) view.getTag();

		holder.content.setText(list.get(position).getContent());
		holder.radioButton.setChecked(list.get(position).isSelected());
		holder.content.setFocusable(false);
		holder.content.setFocusableInTouchMode(false);

		Drawable drawable = null;
		if (holder.radioButton.isChecked()) {
			if (list.get(position).getInx().equals("1")) {
				drawable = rightIconDrawable2;
			} else {
				drawable = errorIconDrawable2;
			}
		} else {
			if (list.get(position).getInx().equals("1")) {
				drawable = rightIconDrawable;
			} else {
				drawable = errorIconDrawable;
			}
		}
		holder.radioButton.setCompoundDrawables(drawable, null, null, null);
		return view;
	}

	static class ViewHolder {
		protected TextView content;
		protected RadioButton radioButton;
	}

}
