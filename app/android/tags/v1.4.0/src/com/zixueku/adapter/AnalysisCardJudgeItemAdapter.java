package com.zixueku.adapter;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class AnalysisCardJudgeItemAdapter extends ArrayAdapter<Option> {
	private Item item; // 试题
	private LayoutInflater layoutInflater;
	private Context context;
	private List<Option> list; // 选项
	private int selectedIndex = -1;
	private static Drawable rightIconDrawable;
	private static Drawable errorIconDrawable;
	private static Drawable rightIconDrawable2;
	private static Drawable rightIconDrawable3;
	private static Drawable errorIconDrawable2;
	private static Drawable errorIconDrawable3;
	private String rightIndex; // 正确答案
	private Set<CustomerAnswer> customerAnswer; // 用户答案 存储option的id

	public AnalysisCardJudgeItemAdapter(Context context, Item item) {
		super(context, R.layout.exercise_item_judge, item.getData().getOptions());
		this.item = item;
		list = item.getData().getOptions(); // 选项
		/*if (list == null || list.size() == 0) {
			Option option1 = new Option("1", "正确", false);
			Option option2 = new Option("2", "错误", false);
			list.add(option1);
			list.add(option2);
		}*/
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.customerAnswer = item.getCustomerAnswer();
		rightIndex = item.getData().getResults().get(0).getInx();

		rightIconDrawable = context.getResources().getDrawable(R.drawable.judge_right_icon);
		errorIconDrawable = context.getResources().getDrawable(R.drawable.judge_error_icon);
		rightIconDrawable2 = context.getResources().getDrawable(R.drawable.judge_right_icon2);
		rightIconDrawable3 = context.getResources().getDrawable(R.drawable.judge_right_icon3);
		errorIconDrawable2 = context.getResources().getDrawable(R.drawable.judge_error_icon2);
		errorIconDrawable3 = context.getResources().getDrawable(R.drawable.judge_error_icon3);

		int imgSize = context.getResources().getDimensionPixelSize(R.dimen.judge_button);
		rightIconDrawable.setBounds(0, 0, imgSize, imgSize);
		errorIconDrawable.setBounds(0, 0, imgSize, imgSize);
		rightIconDrawable2.setBounds(0, 0, imgSize, imgSize);
		rightIconDrawable3.setBounds(0, 0, imgSize, imgSize);
		errorIconDrawable2.setBounds(0, 0, imgSize, imgSize);
		errorIconDrawable3.setBounds(0, 0, imgSize, imgSize);

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
		holder.radioButton.setChecked(false);
		
		if (list.get(position).getInx().equals("1")) {
			holder.radioButton.setCompoundDrawables(rightIconDrawable, null, null, null);
		} else {
			holder.radioButton.setCompoundDrawables(errorIconDrawable, null, null, null);
		}

		// 用户答案

		String inde = list.get(position).getInx(); // 当前选项
		for (CustomerAnswer custo : customerAnswer) {
			if (custo.getInx().equals(inde)) {
				holder.radioButton.setChecked(true);
				// / 这一步必须要做,否则不会显示.
				if (list.get(position).getInx().equals("1")) {
					holder.radioButton.setCompoundDrawables(rightIconDrawable3, null, null, null);
				} else {
					holder.radioButton.setCompoundDrawables(errorIconDrawable3, null, null, null);
				}
			} else {
				if (list.get(position).getInx().equals("1")) {
					holder.radioButton.setCompoundDrawables(rightIconDrawable, null, null, null);
				} else {
					holder.radioButton.setCompoundDrawables(errorIconDrawable, null, null, null);
				}
			}
		}

		// 正确答案

		if (list.get(position).getInx().equals(rightIndex)) {
			holder.radioButton.setChecked(true);
			holder.content.getPaint().setFakeBoldText(true);

			if (list.get(position).getInx().equals("1")) {
				holder.radioButton.setCompoundDrawables(rightIconDrawable2, null, null, null);
			} else {
				holder.radioButton.setCompoundDrawables(errorIconDrawable2, null, null, null);
			}

		} else {
			holder.content.getPaint().setFakeBoldText(false);
		}

		return view;
	}

	static class ViewHolder {
		protected TextView content;
		protected RadioButton radioButton;
	}

}
