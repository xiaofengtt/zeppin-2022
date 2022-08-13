package com.zixueku.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.zixueku.R;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.entity.Knowledge;
import com.zixueku.entity.PrepareExam;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:34:42
 */
public class KnowledgeAdapter extends BaseAdapter {

	/** 树中的元素(界面上显示的元素) */
	private List<Knowledge> displayedKnowledgeData;

	/** LayoutInflater */
	private LayoutInflater layoutInflater;

	private Activity activity;

	/** item的行首缩进基数 */
	private int indentionBase;

	private PrepareExam prepareExam;

	public KnowledgeAdapter(Activity activity, List<Knowledge> displayedKnowledgeData, PrepareExam prepareExam) {
		super();
		this.activity = activity;
		this.displayedKnowledgeData = displayedKnowledgeData;
		this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.indentionBase = 0;
		this.prepareExam = prepareExam;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return displayedKnowledgeData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return displayedKnowledgeData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// "drawable://" + R.drawable.img // from drawables (non-9patch images)
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.knowledge_item, parent, false);

			holder.imageView = (ImageView) convertView.findViewById(R.id.knowledge_item_disclosure_img);
			holder.content = (TextView) convertView.findViewById(R.id.knowledge_item_content);
			holder.finishNum = (TextView) convertView.findViewById(R.id.knowledge_finish_num);
			holder.totleNum = (TextView) convertView.findViewById(R.id.knowledge_total_num);
			holder.progress = (RoundCornerProgressBar) convertView.findViewById(R.id.knowledge_progress_bar);
			holder.imageButton = (ImageButton) convertView.findViewById(R.id.knowledge_item_img_btn);
			holder.line = (View) convertView.findViewById(R.id.knowledge_item_bottom_line);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Knowledge element = displayedKnowledgeData.get(position);

		/*
		 * holder.imageView.setPadding(indentionBase * (level + 1),
		 * holder.imageView.getPaddingTop(), holder.imageView.getPaddingRight(),
		 * holder.imageView.getPaddingBottom());
		 */
		holder.content.setText(element.getName());

		holder.progress.setMax(element.getItemCount());
		holder.progress.setProgress(element.getRightCount());

		holder.finishNum.setText(String.valueOf(element.getRightCount()));
		holder.totleNum.setText(String.valueOf(element.getItemCount()));

		holder.imageButton.setTag(element.getId());

		holder.imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Integer knowledgeId = (Integer) v.getTag();
				// 下面是你的其他事务逻辑
				Intent intent = new Intent(activity, ExerciseActivity.class);
				intent.putExtra("knowledgeId", knowledgeId);
				//intent.putExtra("prepareExam", prepareExam);
				((App) activity.getApplication()).setTestAbilityChange(null);//设置testAbilityChange为空
				activity.startActivityForResult(intent,1);
				activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		/*
		 * if (element.isHaschild() && !element.isExpanded()) {
		 * holder.imageView.setImageResource(R.drawable.knowledge_close); } else
		 * if (element.isHaschild() && element.isExpanded()) {
		 * holder.imageView.setImageResource(R.drawable.knowledge_open); } else
		 * if (!element.isHaschild()) {
		 * holder.imageView.setImageResource(R.drawable.knowledge_point); }
		 */

		int level = element.getLevel();

		holder.imageView.setVisibility(ImageView.VISIBLE);
		holder.line.setVisibility(ImageView.VISIBLE);
		// 第一个参数为宽的设置，第二个参数为高的设置。
		LinearLayout.LayoutParams p;
		// 树形结构全部显示时的逻辑
		/*
		 * if (element.isHaschild() && !element.isExpanded()) { p = new
		 * LinearLayout.LayoutParams(CommonTools.dip2px(context,
		 * R.dimen.knowledge_item_disclosure_img),
		 * LinearLayout.LayoutParams.WRAP_CONTENT);
		 * holder.imageView.setLayoutParams(p);
		 * holder.imageView.setImageResource(R.drawable.knowledge_close); } else
		 * if (element.isHaschild() && element.isExpanded()) { p = new
		 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		 * CommonTools.dip2px(context, R.dimen.knowledge_item_disclosure_img));
		 * holder.imageView.setLayoutParams(p);
		 * holder.imageView.setImageResource(R.drawable.knowledge_open); } else
		 * if (!element.isHaschild()) {
		 * holder.imageView.setVisibility(ImageView.INVISIBLE); }
		 */

		// 只显示指定级数时的逻辑

		if (level == 1) {
			if (element.isExpanded()) {
				p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, CommonTools.dip2px(activity, R.dimen.knowledge_item_disclosure_img));
				holder.imageView.setLayoutParams(p);
				holder.imageView.setImageResource(R.drawable.knowledge_open);
			} else {
				p = new LinearLayout.LayoutParams(CommonTools.dip2px(activity, R.dimen.knowledge_item_disclosure_img), LinearLayout.LayoutParams.WRAP_CONTENT);
				holder.imageView.setLayoutParams(p);
				holder.imageView.setImageResource(R.drawable.knowledge_close);
			}

			convertView.setBackgroundColor(activity.getResources().getColor(R.color.grey));
			holder.content.setTextSize(16);
			holder.progress.setProgressColor(activity.getResources().getColor(R.color.green));
		} else {
			holder.imageView.setVisibility(ImageView.INVISIBLE); // 隐藏图片

			convertView.setBackgroundColor(activity.getResources().getColor(R.color.white));
			holder.content.setTextSize(14);
			holder.progress.setProgressColor(activity.getResources().getColor(R.color.lightblue));
			holder.line.setVisibility(View.INVISIBLE);

		}

		/*
		 * if (!element.isExpanded() && level == 1) { p = new
		 * LinearLayout.LayoutParams(CommonTools.dip2px(context,
		 * R.dimen.knowledge_item_disclosure_img),
		 * LinearLayout.LayoutParams.WRAP_CONTENT);
		 * holder.imageView.setLayoutParams(p);
		 * holder.imageView.setImageResource(R.drawable.knowledge_close); } else
		 * if (element.isExpanded() && level == 1) { p = new
		 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		 * CommonTools.dip2px(context, R.dimen.knowledge_item_disclosure_img));
		 * holder.imageView.setLayoutParams(p);
		 * holder.imageView.setImageResource(R.drawable.knowledge_open); } else
		 * { holder.imageView.setVisibility(ImageView.INVISIBLE); }
		 */

		/*
		 * if (level == 1) {
		 * convertView.setBackgroundColor(context.getResources(
		 * ).getColor(R.color.grey)); holder.content.setTextSize(16);
		 * holder.progress
		 * .setProgressColor(context.getResources().getColor(R.color.green));
		 * holder.line.setVisibility(View.VISIBLE); } else {
		 * convertView.setBackgroundColor
		 * (context.getResources().getColor(R.color.white));
		 * holder.content.setTextSize(14);
		 * holder.progress.setProgressColor(context
		 * .getResources().getColor(R.color.lightblue));
		 * holder.line.setVisibility(View.INVISIBLE); }
		 */

		return convertView;
	}

	public List<Knowledge> getDisplayedKnowledgeData() {
		return displayedKnowledgeData;
	}

	static class ViewHolder {
		protected ImageView imageView;
		protected TextView content;
		protected RoundCornerProgressBar progress;
		protected TextView totleNum;
		protected TextView finishNum;
		protected ImageButton imageButton;
		protected View line;
	}

}
