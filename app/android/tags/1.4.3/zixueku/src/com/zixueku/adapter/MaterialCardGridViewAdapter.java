package com.zixueku.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.activity.MaterialActivity;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.util.Constant;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-24 上午10:23:54
 */
public class MaterialCardGridViewAdapter extends BaseAdapter {
	private Activity context;
	private Material material;
	private LayoutInflater layoutInflater;
	private ViewPager viewPager;

	public MaterialCardGridViewAdapter(Activity context, Material material, ViewPager viewPager) {
		this.context = context;
		this.material = material;
		this.viewPager = viewPager;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return material.getTotalNum();
	}

	@Override
	public Object getItem(int position) {
		for (Item item : material.getMaterialItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					if (child.getIndex() == position) {
						return child;
					}
				}
			} else {
				if (item.getIndex() == position) {
					return item;
				}
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.result_grid_view_item, parent, false);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		final Item item = (Item) getItem(position);

		holder.textView.setText(position + 1 + "");
		int completeType = item.getCompleteType();
		if (completeType == -1) {
			holder.imageButton.setImageResource(R.drawable.material_card_grid_view_button_un_see);
		} else if (completeType == 0) {
			holder.imageButton.setImageResource(R.drawable.material_card_grid_view_button_had_see);
		} else {
			holder.imageButton.setImageResource(R.drawable.material_card_grid_view_button_master);
		}

		holder.imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int parentIndex = -1;
				int subIndex = -1;
				boolean mark = false;
				for (Item item : material.getMaterialItems()) {
					parentIndex++;
					subIndex = -1;
					if (item.getModelType() == 4) {
						for (Item child : item.getChildren()) {
							subIndex++;
							if (child.getIndex() == position) {
								mark = true;
								break;
							}
						}
					} else {
						if (item.getIndex() == position) {
							break;
						}
					}
					if (mark) {
						break;
					}
				}
				viewPager.setCurrentItem(parentIndex);
				if (subIndex != -1) {
					final int s = subIndex;
					final int p = parentIndex;
					Handler handler = new Handler();
					Runnable run = new Runnable() {
						@Override
						public void run() {
							ViewPager subViewPager = MaterialActivity.subViewPagers.get(p);
							subViewPager.setCurrentItem(s);
						}
					};
					handler.postDelayed(run, Constant.DELAYED_TIME);
				}
				((MaterialActivity) context).hideTheAssert();
			}
		});
		return convertView;
	}

	static class ViewHolder {
		protected ImageView imageButton;
		protected TextView textView;

		ViewHolder(View view) {
			this.imageButton = (ImageView) view.findViewById(R.id.result_grid_view_item_circle_button);
			this.textView = (TextView) view.findViewById(R.id.result_grid_view_item_text_view);
			view.setTag(this);
		}
	}

}
