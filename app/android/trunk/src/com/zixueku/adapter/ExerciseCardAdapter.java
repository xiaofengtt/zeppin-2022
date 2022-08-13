package com.zixueku.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.Subject;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class ExerciseCardAdapter extends ArrayAdapter<Subject> {
	private List<Subject> list;
	private LayoutInflater layoutInflater;

	public ExerciseCardAdapter(Activity context, List<Subject> list) {
		super(context, R.layout.subject_item, list);
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Subject getItem(int position) {
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
			view = layoutInflater.inflate(R.layout.subject_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.subject_check_box_item);
			
			/*
			 * viewHolder.checkbox.setOnCheckedChangeListener(new
			 * CompoundButton.OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged(CompoundButton buttonView,
			 * boolean isChecked) { int pos = (Integer)
			 * viewHolder.checkbox.getTag(); //
			 * element.setSelected(buttonView.isChecked());
			 * list.get(pos).setSelected(buttonView.isChecked()); } });
			 */

			viewHolder.text = (TextView) view.findViewById(R.id.subject_text_item);
			view.setTag(viewHolder);
			// viewHolder.checkbox.setTag(position);
		} else {
			viewHolder = (ViewHolder) view.getTag();
			// viewHolder.checkbox.setTag(position);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.checkbox.setChecked(list.get(position).isSelected());
		// holder.checkbox.setText(list.get(position).getName());
		holder.text.setText(list.get(position).getName());
		return view;
	}

	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}

}
