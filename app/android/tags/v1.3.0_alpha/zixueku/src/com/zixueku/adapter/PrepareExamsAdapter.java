package com.zixueku.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.entity.PrepareExam;

/**
 * 类说明
 * 正在备考
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-3 上午11:33:08
 */
public class PrepareExamsAdapter extends BaseAdapter {

	private List<PrepareExam> list;
	private LayoutInflater layoutInflater;
	protected ImageLoader imageLoader;
	protected Context context;

	public PrepareExamsAdapter(Context context, List<PrepareExam> list) {
		this.list = list;
		this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = ImageLoader.getInstance();
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public PrepareExam getItem(int position) {
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
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.prepare_exams_item, parent, false);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();

		PrepareExam prepareExam = list.get(position);

		// imageLoader.displayImage(prepareExam.getImagePath(),
		// viewHolder.imageView);
		holder.name.setText(prepareExam.getSubjectname());
		holder.progress.setProgress((int) prepareExam.getProgress());
		holder.remainDay.setText(prepareExam.getNextTestdayCount() + "");
		holder.progressNum.setText((int) prepareExam.getProgress() + "%");
		return convertView;
	}

	static class ViewHolder {
		protected TextView name;
		protected ImageView imageView;
		protected NumberProgressBar progress;
		protected TextView remainDay;
		protected TextView progressNum;

		ViewHolder(View view) {
			imageView = (ImageView) view.findViewById(R.id.prepare_exams_item_img);
			name = (TextView) view.findViewById(R.id.prepare_exams_item_name);
			progress = (NumberProgressBar) view.findViewById(R.id.prepare_exams_item_progress_bar);
			remainDay = (TextView) view.findViewById(R.id.prepare_exams_remain_days);
			progressNum = (TextView) view.findViewById(R.id.prepare_exams_item_progress_bar_num);
			view.setTag(this);
		}
	}

	public List<PrepareExam> getList() {
		return list;
	}

	public void setList(List<PrepareExam> list) {
		this.list = list;
	}

}
