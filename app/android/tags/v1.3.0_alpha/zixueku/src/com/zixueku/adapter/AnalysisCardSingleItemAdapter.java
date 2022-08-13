package com.zixueku.adapter;

import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.Option;
import com.zixueku.entity.Result;
import com.zixueku.util.Constant;
import com.zixueku.util.URLImageParser;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class AnalysisCardSingleItemAdapter extends ArrayAdapter<Option> {
	private Item item; // 试题
	private LayoutInflater layoutInflater;
	private Context context;
	private List<Option> list; // 选项
	private Set<CustomerAnswer> customerAnswer; // 用户答案 存储option的id
	private List<Result> answer;// 正确答案 存储option的id

	private int selectedIndex = -1;

	public AnalysisCardSingleItemAdapter(Context context, Item item) {
		super(context, R.layout.exercise_item_single, item.getData().getOptions());
		this.item = item;
		list = item.getData().getOptions(); // 选项
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.customerAnswer = item.getCustomerAnswer();
		this.answer = item.getData().getResults();
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
			view = layoutInflater.inflate(R.layout.exercise_item_single, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.radioButton = (RadioButton) view.findViewById(R.id.exercise_single_item_radio_button);
			viewHolder.title = (TextView) view.findViewById(R.id.exercise_single_item_title);
			viewHolder.content = (TextView) view.findViewById(R.id.exercise_single_item_content);
			view.setTag(viewHolder);
			// viewHolder.checkbox.setTag(position);
		} else {
			viewHolder = (ViewHolder) view.getTag();
			// viewHolder.checkbox.setTag(position);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(Constant.OPTION_INDEX.get(list.get(position).getInx()));
		// holder.content.setText(list.get(position).getContext());

		URLImageParser p = new URLImageParser(holder.content,this.getContext());
		Spanned htmlSpan = Html.fromHtml(list.get(position).getContent(), p, null);
		holder.content.setText(htmlSpan);
		holder.radioButton.setChecked(false);

		// 用户答案
		String inde = list.get(position).getInx();
		for (CustomerAnswer custo : customerAnswer) {
			if (custo.getInx().equals(inde)) {
				holder.radioButton.setChecked(true);
				Drawable drawable = context.getResources().getDrawable(R.drawable.anlysis_false_radio_style);
				// / 这一步必须要做,否则不会显示.
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				holder.radioButton.setCompoundDrawables(drawable, null, null, null);
			}
		}

		// 正确答案
		for (Result result : answer) {
			if (list.get(position).getInx().equals(result.getInx())) {
				holder.radioButton.setChecked(true);
				holder.content.getPaint().setFakeBoldText(true);
				Drawable drawable = context.getResources().getDrawable(R.drawable.anlysis_true_radio_style);
				// / 这一步必须要做,否则不会显示.
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				holder.radioButton.setCompoundDrawables(drawable, null, null, null);
			}else{
				//holder.content.setTextColor(context.getResources().getColor(R.color.black));
				holder.content.getPaint().setFakeBoldText(false);
			}
		}

		/*
		 * if (selectedIndex == position) { holder.radioButton.setChecked(true);
		 * } else { holder.radioButton.setChecked(false); }
		 */

		if (holder.radioButton.isChecked()) {
			holder.title.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			holder.title.setTextColor(context.getResources().getColor(R.color.black));
		}

		return view;
	}

	static class ViewHolder {
		protected TextView content;
		protected TextView title;
		protected RadioButton radioButton;
	}

}
