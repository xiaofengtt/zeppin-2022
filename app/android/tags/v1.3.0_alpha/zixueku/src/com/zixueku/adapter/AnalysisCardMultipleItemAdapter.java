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
import android.widget.CheckBox;
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
public class AnalysisCardMultipleItemAdapter extends ArrayAdapter<Option> {
	private List<Option> list;
	private LayoutInflater layoutInflater;
	private Context context;
	private Set<CustomerAnswer> customerAnswer; // 用户答案 存储option的id
	// private List<Result> answer;// 正确答案 存储option的id
	private Item item;
	private static Drawable rightDrawable;
	private static Drawable unslectedDrawable;
	private static Drawable halfDrawable;
	private static Drawable errorDrawable;
	private String[] rightAnswer;

	public AnalysisCardMultipleItemAdapter(Context context, Item item) {
		super(context, R.layout.exercise_item_multiple, item.getData().getOptions());
		this.list = item.getData().getOptions(); // 选项列表
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.customerAnswer = item.getCustomerAnswer();
		// this.answer = item.getData().getResults();
		this.rightAnswer = item.getData().getResults().get(0).getInx().split(",");
		this.item = item;
		rightDrawable = context.getResources().getDrawable(R.drawable.multiple_selected);
		unslectedDrawable = context.getResources().getDrawable(R.drawable.multiple_un_selected);
		halfDrawable = context.getResources().getDrawable(R.drawable.half_right_icon);
		errorDrawable = context.getResources().getDrawable(R.drawable.multiple_selected_error);
		
		rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
		unslectedDrawable.setBounds(0, 0, unslectedDrawable.getMinimumWidth(), unslectedDrawable.getMinimumHeight());
		halfDrawable.setBounds(0, 0, context.getResources().getDimensionPixelSize(R.dimen.multiple_button),
				context.getResources().getDimensionPixelSize(R.dimen.multiple_button));
		errorDrawable.setBounds(0, 0, errorDrawable.getMinimumWidth(), errorDrawable.getMinimumHeight());
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
			viewHolder.content = (TextView) view.findViewById(R.id.exercise_multiple_item_content);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(Constant.OPTION_INDEX.get(list.get(position).getInx()));

		URLImageParser p = new URLImageParser(holder.content, this.getContext());
		Spanned htmlSpan = Html.fromHtml(list.get(position).getContent(), p, null);
		holder.content.setText(htmlSpan);

		holder.checkButton.setChecked(false); // 初始化选项状态，否则会因组件重用，状态显示可能会出错

		boolean isSelected = list.get(position).isSelected();

		if (item.isRight() && isSelected) {
			holder.checkButton.setCompoundDrawables(rightDrawable, null, null, null);
		} else if (item.isRight() && !isSelected) {
			holder.checkButton.setCompoundDrawables(unslectedDrawable, null, null, null);
		} else {
			boolean isInRightCollection = false; // 该选项是否在正确答案中
			for (String rightStr : rightAnswer) {
				if (rightStr.equals(list.get(position).getInx())) {
					isInRightCollection = true;
				}
			}
			if (isInRightCollection && isSelected) {
				holder.checkButton.setCompoundDrawables(halfDrawable, null, null, null);
			} else if (isInRightCollection && !isSelected) {
				holder.checkButton.setCompoundDrawables(rightDrawable, null, null, null);
			} else if (isSelected && !isInRightCollection) {
				holder.checkButton.setCompoundDrawables(errorDrawable, null, null, null);
			} else {
				holder.checkButton.setCompoundDrawables(unslectedDrawable, null, null, null);
			}
		}

		/*
		 * // 用户答案
		 * 
		 * if (customerAnswer.contains(list.get(position).getInx())) {
		 * holder.checkButton.setChecked(true); Drawable drawable =
		 * context.getResources
		 * ().getDrawable(R.drawable.anlysis_false_radio_style); // /
		 * 这一步必须要做,否则不会显示. drawable.setBounds(0, 0, drawable.getMinimumWidth(),
		 * drawable.getMinimumHeight());
		 * holder.checkButton.setCompoundDrawables(drawable, null, null, null);
		 * }
		 * 
		 * // 正确答案 for (Result result : answer) { if
		 * (list.get(position).getInx() == result.getInx()) {
		 * holder.checkButton.setChecked(true); Drawable drawable =
		 * context.getResources
		 * ().getDrawable(R.drawable.anlysis_true_radio_style); // /
		 * 这一步必须要做,否则不会显示. drawable.setBounds(0, 0, drawable.getMinimumWidth(),
		 * drawable.getMinimumHeight());
		 * holder.checkButton.setCompoundDrawables(drawable, null, null, null);
		 * } }
		 */

		if (!holder.checkButton.getCompoundDrawables()[0].equals(unslectedDrawable)) {
			holder.title.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			holder.title.setTextColor(context.getResources().getColor(R.color.black));
		}

		return view;
	}

	static class ViewHolder {
		protected TextView content;
		protected TextView title;
		protected CheckBox checkButton;
	}
	
}
