package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.SubjectPagerAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Grade;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

/**
 * 类说明 选择某科目的详细信息
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-26 上午11:30:14
 */
public class AddSujectActivity extends AbstractAsyncActivity {
	private int categoryId;
	private String categoryName;
	private List<Grade> grades;
	private ImageButton goBackButton;
	private TextView actionbarCenterText;
	private ViewPager pager;
	private SubjectPagerAdapter adapter;
	private PagerSlidingTabStrip tabs;
	private Button complete;
	private ActionResult<ArrayList<Grade>> actionResult;
	private ImageView imageView;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_add_subject);
	}

	@Override
	protected void findViewById() {
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		actionbarCenterText = (TextView) findViewById(R.id.action_bar_center_text);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.add_subject_tabs);
		pager = (ViewPager) findViewById(R.id.add_subject_pager);
		imageView = (ImageView) findViewById(R.id.subject_hand_image);
		// 获取上一个界面传进来的数据
		categoryId = getIntent().getIntExtra("categoryId", 0);
		categoryName = getIntent().getStringExtra("categoryName");
		complete = (Button) findViewById(R.id.add_subject_complete);
		actionResult = new ActionResult<ArrayList<Grade>>() {
		};
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(AddSujectActivity.this));
		complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Intent intent = new Intent(AddSujectActivity.this,
				 * MainActivity.class);
				 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				 * Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(intent);
				 * overridePendingTransition(R.anim.left_in, R.anim.right_out);
				 */
				if (CategoryActivity.instance != null) {
					CategoryActivity.instance.finish();
				}
				User user = App.getInstance().getUserInfo();
				try {
					if (user.getIsFirstLogin()) {
						user.setIsFirstLogin(false);
						CommonTools.finishActivity(AddSujectActivity.this, MainActivity.class);
					} else {
						CommonTools.finishActivity(AddSujectActivity.this, 2);
					}
				} catch (Exception e) {

				}
			}
		});
	}

	@Override
	protected void processLogic() {
		// 图片来源于
		// String imageUri = "drawable://" + R.drawable.head_teacher;
		// ImageLoader.getInstance().displayImage(imageUri, imageView);
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("category.id", categoryId);
		actionbarCenterText.setText(categoryName);
		Request request = new Request(R.string.CategoryLoadSubjects, requestData, AddSujectActivity.this, actionResult);
		netDataConnation(request, new ServerDataCallback<ActionResult<ArrayList<Grade>>>() {
			@Override
			public void processData(ActionResult<ArrayList<Grade>> paramObject, boolean paramBoolean) {
				grades = paramObject.getRecords();
				if ("fail".equals(paramObject.getStatus())) {
					CommonTools.showShortToastDefaultStyle(mContext, paramObject.getMessage());
					return;
				}
				adapter = new SubjectPagerAdapter(getSupportFragmentManager(), grades);
				pager.setAdapter(adapter);
				tabs.setViewPager(pager);
			}
		});
	}

	@Override
	public void onBackPressed() {
		CommonTools.finishActivity(AddSujectActivity.this);
	}
}
