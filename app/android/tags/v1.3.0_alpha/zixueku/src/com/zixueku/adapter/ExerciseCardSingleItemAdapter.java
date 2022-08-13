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
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
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
public class ExerciseCardSingleItemAdapter extends ArrayAdapter<Option> {
	private List<Option> list;
	private LayoutInflater layoutInflater;
	private Context context;

	private int selectedIndex = -1;

	public ExerciseCardSingleItemAdapter(Activity context, List<Option> list) {
		super(context, R.layout.exercise_item_single, list);
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
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
		//Log.e("getView", position + list.get(position).getContent());

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
		// holder.content.setText(list.get(position).getContext());

		// URLImageParser p = new URLImageParser(holder.content);
		// Spanned htmlSpan = Html.fromHtml(list.get(position).getContent(), p,
		// null);
		// holder.content.setText(htmlSpan);
		String htmlContent = list.get(position).getContent();
		if (htmlContent != null) {
			//<img src="imgs/1.jpg"
			htmlContent = htmlContent.replace("<img src=","<br><img src=");
			
			htmlContent = htmlContent.replaceAll("<head>([\\s\\S]*)<\\/head>","");
			if (htmlContent.contains("><p>")) {
				String regularExpression1 = "(<[^\\/]\\w><p>)";
				Pattern pat1 = Pattern.compile(regularExpression1);
				Matcher mat1 = pat1.matcher(htmlContent);
				if(mat1.find()){
					for (int i = 0; i < mat1.groupCount(); i++) {
						System.out.println(mat1.group(i));
						String temp = mat1.group(i).replace("<p>", "");
						htmlContent = htmlContent.replace(mat1.group(i), temp);
						String tail = temp.replace("<", "</");
						htmlContent = htmlContent.replace("</p>"+tail, tail);
						System.out.println(htmlContent);
					}
				}	
			
			}

		}

		holder.content.setHtmlFromString(htmlContent,new HtmlTextView.RemoteImageGetter());

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

	static class ViewHolder {
		protected HtmlTextView content;
		protected TextView title;
		protected RadioButton radioButton;
	}

}
