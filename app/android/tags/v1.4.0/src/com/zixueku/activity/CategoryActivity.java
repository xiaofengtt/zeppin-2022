package com.zixueku.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.zixueku.R;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

/**
 * 类说明 选择备考科目(教师资格证等)
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-6 下午2:03:19
 */
public class CategoryActivity extends AbstractAsyncActivity implements OnClickListener {
	public static Activity instance;
	private ImageButton goBackButton;
	private LinearLayout teacherLayout; // 教师资格证
	private LinearLayout securityLayout;// 证券从业
	private LinearLayout psychologyLayout;// 心理咨询

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_category);
	}

	@Override
	protected void findViewById() {
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		teacherLayout = (LinearLayout) findViewById(R.id.teacher_layout);
		securityLayout = (LinearLayout) findViewById(R.id.security_layout);
		psychologyLayout = (LinearLayout) findViewById(R.id.psychology_layout);
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		teacherLayout.setOnClickListener(this);
		securityLayout.setOnClickListener(this);
		psychologyLayout.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		instance = this;
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	private void goBack() {
		User user = App.getInstance().getUserInfo();
		if (user.getIsFirstLogin()) {
			user.setIsFirstLogin(false);
			CommonTools.finishActivity(this, MainActivity.class);
		} else {
			CommonTools.finishActivity(this, 2);
		}
		
	}

	@Override
	public void onClick(View v) {
		int categoryId = 0;
		String categoryName = "";
		switch (v.getId()) {
		case R.id.teacher_layout:
			categoryId = 50;
			categoryName = "教师资格证考试设置";
			break;
		case R.id.psychology_layout:
			categoryId = 56;
			categoryName = "心理咨询考试设置";
			break;
		case R.id.security_layout:
			categoryId = 58;
			categoryName = "证券从业考试设置";
			break;
		}
		Intent intent = new Intent(CategoryActivity.this, AddSujectActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("categoryName", categoryName);
		CategoryActivity.this.startActivity(intent);
		CategoryActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

}
