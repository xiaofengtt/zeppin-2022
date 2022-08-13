package com.zixueku.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zixueku.R;

/**
 * 类说明 选择备考科目(教师资格证等)
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-6 下午2:03:19
 */
public class CategoryActivity extends AbstractAsyncActivity {

	private ImageButton goBackButton;

	private LinearLayout teacherLayout;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_category);
	}

	@Override
	protected void findViewById() {
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		
		teacherLayout =  (LinearLayout) findViewById(R.id.teacher_layout);
		

	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
		// categoryGridView.setOnItemClickListener(new
		// CategoryListener(CategoryActivity.this));

		OnClickListener addSubjectClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int categoryId = 50; //
				Intent intent = new Intent(CategoryActivity.this, AddSujectActivity.class);
				intent.putExtra("categoryId", categoryId);
				CategoryActivity.this.startActivity(intent);
				CategoryActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		};
		teacherLayout.setOnClickListener(addSubjectClick);
	}

	@Override
	protected void processLogic() {
		/*
		 * Request request = new Request(R.string.CategoryList, this,
		 * actionResult); getDataFromServer(request, new
		 * ServerDataCallback<ActionResult<ArrayList<HashMap<String,
		 * Object>>>>() {
		 * 
		 * @Override public void
		 * processData(ActionResult<ArrayList<HashMap<String, Object>>>
		 * paramObject, boolean paramBoolean) { categoryGridView.setAdapter(new
		 * CategoryAdapter(CategoryActivity.this, paramObject.getRecords())); }
		 * });
		 */
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	private void goBack() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

}
