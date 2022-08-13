package com.zixueku.listerner;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zixueku.R;
import com.zixueku.activity.SubjectDetailActivity;
import com.zixueku.entity.PrepareExam;
import com.zixueku.util.App;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午10:02:58
 */
public class PrepareExamsItemClickListener implements OnItemClickListener {

	private Activity activity;

	public PrepareExamsItemClickListener(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 获取被点击的item所对应的数据
		PrepareExam prepareExam = (PrepareExam) parent.getItemAtPosition(position);
		((App) (activity.getApplication())).setPrepareExam(prepareExam);

		// 下面是你的其他事务逻辑
		Intent intent = new Intent(activity, SubjectDetailActivity.class);
		// intent.putExtra("prepareExam", prepareExam);
		intent.putExtra("position", position);
		activity.startActivityForResult(intent, 0);
		activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

}
