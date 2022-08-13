package com.zixueku.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Option;
import com.zixueku.util.App;
import com.zixueku.util.Constant;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 下午2:37:20
 */
public class ExerciseCardSingleItemAdapter2 extends ArrayAdapter<Option> implements OnItemClickListener {
	private List<Option> list;
	private LayoutInflater layoutInflater;
	private Context context;
	private Exercise exercise;
	private int selectedIndex = -1;
	private OnItemCallBack onItemCallBack;
	private int position;

	public ExerciseCardSingleItemAdapter2(Activity context, List<Option> list, int position) {
		super(context, R.layout.exercise_item_single, list);
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		exercise = App.getInstance().getExercise();
		this.position = position;
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
		// Log.e("getView", position + list.get(position).getContent());
		final ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.exercise_item_single, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.radioButton = (RadioButton) view.findViewById(R.id.exercise_single_item_radio_button);
			viewHolder.title = (TextView) view.findViewById(R.id.exercise_single_item_title);
			viewHolder.content = (HtmlTextView) view.findViewById(R.id.exercise_single_item_content);
			view.setTag(viewHolder);
			// viewHolder.checkbox.setTag(position);
		} else {
			viewHolder = (ViewHolder) view.getTag();
			// viewHolder.checkbox.setTag(position);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(Constant.OPTION_INDEX.get(list.get(position).getInx()));

		String htmlContent = list.get(position).getContent();
		holder.content.setHtmlFromString(htmlContent, new HtmlTextView.RemoteImageGetter());
		holder.radioButton.setChecked(list.get(position).isSelected());
		holder.content.setFocusable(false);
		holder.content.setFocusableInTouchMode(false);

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// ViewHolder vh = (ViewHolder)view.getTag();
		for (int i = 0; i < exercise.getItems().get(position).getData().getOptions().size(); i++) {
			exercise.getItems().get(position).getData().getOptions().get(i).setSelected(false);
		}
		// singleItemAdapter.notifyDataSetChanged();

		exercise.getItems().get(position).getCustomerAnswer().clear();

		CustomerAnswer customerAnswer = new CustomerAnswer(exercise.getItems().get(position).getData().getOptions().get(pos).getInx());

		exercise.getItems().get(position).getCustomerAnswer().add(customerAnswer);
		exercise.getItems().get(position).getData().getOptions().get(pos).setSelected(true);
		this.notifyDataSetChanged();

		if (onItemCallBack != null) {
			onItemCallBack.after();
		}
	}
	
	public void setOnItemCallBack(OnItemCallBack onItemCallBack){
		this.onItemCallBack = onItemCallBack;
	}

	static class ViewHolder {
		protected HtmlTextView content;
		protected TextView title;
		protected RadioButton radioButton;
	}
	public interface OnItemCallBack {
		public void after();
	}
}

