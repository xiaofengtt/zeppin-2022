package com.zixueku.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-21 下午2:46:44
 */
public class CategoryAdapter extends BaseAdapter {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ArrayList<HashMap<String, Object>> list;
	private LayoutInflater layoutInflater;
	private String serverImagePath;

	public CategoryAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList) {
		super();
		this.list = arrayList;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.serverImagePath = context.getString(R.string.ServerImagePath);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
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
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.activity_category_item, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView) view.findViewById(R.id.category_item_image);
			holder.textView1 = (TextView) view.findViewById(R.id.category_item_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		imageLoader.displayImage(serverImagePath + list.get(position).get("resource.url"), holder.imageView);
		holder.textView1.setText(list.get(position).get("shortname").toString());
		return view;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView textView1;
	}

}
