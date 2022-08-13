package com.zixueku.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.zixueku.R;
import com.zixueku.entity.PaperType;
import com.zixueku.util.Constant;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:34:42
 */
public class PaperTypeItemAdapter extends BaseAdapter {

	private List<PaperType> itemTypeList;

	/** LayoutInflater */
	private LayoutInflater layoutInflater;

	private Activity activity;

	public PaperTypeItemAdapter(Activity activity, List<PaperType> itemTypeList) {
		super();
		this.activity = activity;
		this.itemTypeList = itemTypeList;
		this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return itemTypeList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemTypeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.paper_type_item, parent, false);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		PaperType item = itemTypeList.get(position);
		holder.typeName.setText(Constant.PAPER_TYPR.get(item.type));
		holder.totleNum.setText(String.format("共%d套", item.count));
		TextDrawable drawable = TextDrawable.builder().beginConfig().endConfig().buildRoundRect("A", Color.YELLOW, 10);
		holder.imageView.setImageDrawable(drawable);
		return convertView;
	}

	static class ViewHolder {
		protected TextView typeName;
		protected TextView totleNum;
		protected ImageView imageView;

		ViewHolder(View view) {
			this.typeName = (TextView) view.findViewById(R.id.prepare_type_item_name);
			this.totleNum = (TextView) view.findViewById(R.id.paper_type_itme_count);
			this.imageView = (ImageView) view.findViewById(R.id.prepare_type_item_img);
			view.setTag(this);
		}
	}

}
