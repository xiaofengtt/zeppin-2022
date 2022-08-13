package com.zixueku.listerner;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zixueku.activity.AddSujectActivity;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-26 下午1:42:24
 */
public class CategoryListener implements OnItemClickListener {
	private Activity activity;

	public CategoryListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 获取被点击的item所对应的数据
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		
		int categoryId = ((Double)map.get("id")).intValue();
		// 下面是你的其他事务逻辑
		Intent intent = new Intent(activity, AddSujectActivity.class);
		intent.putExtra("categoryId", categoryId);
		activity.startActivity(intent);
	}
}
