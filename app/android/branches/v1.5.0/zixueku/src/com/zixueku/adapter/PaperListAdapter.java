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
import com.zixueku.entity.PaperInfo;
import com.zixueku.entity.PaperType;
import com.zixueku.util.Constant;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:34:42
 */
public class PaperListAdapter extends BaseAdapter {

	private List<PaperInfo> paperList;

	/** LayoutInflater */
	private LayoutInflater layoutInflater;

	private Activity activity;

	public PaperListAdapter(Activity activity, List<PaperInfo> paperList) {
		super();
		this.activity = activity;
		this.paperList = paperList;
		this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return paperList.size();
	}

	@Override
	public Object getItem(int position) {
		return paperList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.paper_list_item, parent, false);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		PaperInfo item = paperList.get(position);
		holder.paperName.setText(item.name);
		holder.testCount.setText(String.format("%d人测试过", item.testCount));
		TextDrawable drawable = TextDrawable.builder().beginConfig().endConfig().buildRound(item.year, Color.YELLOW);
		holder.imageView.setImageDrawable(drawable);
		if(item.isFree){
			holder.price.setText("免费");
			holder.price.setTextColor(Color.GREEN);
		}else{
			holder.price.setText(String.format("￥%d", item.price));
			holder.price.setTextColor(Color.RED);
		}
		return convertView;
	}

	static class ViewHolder {
		protected TextView paperName;
		protected TextView price;
		protected ImageView imageView;
		protected TextView testCount;

		ViewHolder(View view) {
			this.paperName = (TextView) view.findViewById(R.id.paper_name);
			this.testCount = (TextView) view.findViewById(R.id.paper_test_count);
			this.imageView = (ImageView) view.findViewById(R.id.paper_img);
			this.price = (TextView) view.findViewById(R.id.paper_price);
			view.setTag(this);
		}
	}

}
