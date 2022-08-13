package com.zixueku.adapter;

import java.util.List;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.Option;
import com.zixueku.util.Constant;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class ExerciseCardMultipleItemAdapter extends ArrayAdapter<Option> {
	private List<Option> list;
	private LayoutInflater layoutInflater;
	private Context context;

	public ExerciseCardMultipleItemAdapter(Activity context, List<Option> list) {
		super(context, R.layout.exercise_item_multiple, list);
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
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
			view = layoutInflater.inflate(R.layout.exercise_item_multiple, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.checkButton = (CheckBox) view.findViewById(R.id.exercise_multiple_item_check_box);
			viewHolder.title = (TextView) view.findViewById(R.id.exercise_multiple_item_title);
			viewHolder.content = (HtmlTextView) view.findViewById(R.id.exercise_multiple_item_content);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(Constant.OPTION_INDEX.get(list.get(position).getInx()));

		//URLImageParser p = new URLImageParser(holder.content);
		//Spanned htmlSpan = Html.fromHtml(list.get(position).getContent(), p, null);
		holder.content.setHtmlFromString(list.get(position).getContent(),new HtmlTextView.RemoteImageGetter());

		holder.checkButton.setChecked(list.get(position).isSelected());

		if (holder.checkButton.isChecked()) {
			holder.title.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			holder.title.setTextColor(context.getResources().getColor(R.color.black));
		}

		return view;
	}

	static class ViewHolder {
		protected HtmlTextView content;
		protected TextView title;
		protected CheckBox checkButton;
	}

}
