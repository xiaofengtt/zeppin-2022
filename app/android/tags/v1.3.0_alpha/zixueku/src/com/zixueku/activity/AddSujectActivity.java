package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.SubjectPagerAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Grade;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.Request;
import com.zixueku.entity.Subject;
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
	private List<Grade> grades;
	private ImageButton goBackButton;
	private TextView actionbarCenterText;
	private ViewPager pager;
	private SubjectPagerAdapter adapter;
	private PagerSlidingTabStrip tabs;
	private Button complete;
	private User user;
	private ActionResult<ArrayList<Grade>> actionResult;

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
		// 获取上一个界面传进来的数据
		categoryId = getIntent().getIntExtra("categoryId", 0);
		complete = (Button) findViewById(R.id.add_subject_complete);
		user = ((App) getApplication()).getUserInfo();
		actionResult = new ActionResult<ArrayList<Grade>>() {
		};
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(AddSujectActivity.this));
		complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddSujectActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
				/*Map<String, Object> requestData = new IdentityHashMap<String, Object>();
				for (Grade grade : grades) {
					for (Subject subject : grade.getSubjects()) {
						if (subject.isSelected()) {
							requestData.put(new String("subject.id"), subject.getId().toString());
						}
					}
				}

				if (requestData.isEmpty()) {
					Intent intent = new Intent(AddSujectActivity.this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.right_out);
					return;
				}

				requestData.put("user.id", user.getUserId());
				Request request = new Request(R.string.UserSubjectAdd, requestData, AddSujectActivity.this, ParserType.PRIMITIVE);
				sendDataToServer(request, new ServerDataCallback<String>() {
					@Override
					public void processData(String paramObject, boolean paramBoolean) {
						Intent intent = new Intent(AddSujectActivity.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
						overridePendingTransition(R.anim.left_in, R.anim.right_out);
					}
				});
			}*/
		});

		/*selectAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int size = grades.get(pager.getCurrentItem()).getSubjects().size();
				boolean isSelectedAll = grades.get(pager.getCurrentItem()).isSelectedAll();
				if (!isSelectedAll) {
					for (int i = 0; i < size; i++) {
						grades.get(pager.getCurrentItem()).getSubjects().get(i).setSelected(true);
					}
					grades.get(pager.getCurrentItem()).setSelectedAll(true);
					selectAll.setText(R.string.cancel_select_all);
				} else {
					for (int i = 0; i < size; i++) {
						grades.get(pager.getCurrentItem()).getSubjects().get(i).setSelected(false);
					}
					grades.get(pager.getCurrentItem()).setSelectedAll(false);
					selectAll.setText(R.string.select_all);
				}
				pager.getAdapter().notifyDataSetChanged();
			}
		});*/
	}

	@Override
	protected void processLogic() {
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("category.id", categoryId);
		requestData.put("user.id", user.getUserId());
		actionbarCenterText.setText(R.string.add_course);
		Request request = new Request(R.string.CategoryLoadSubjects, requestData, AddSujectActivity.this, actionResult);
		getDataFromServer(request, new ServerDataCallback<ActionResult<ArrayList<Grade>>>() {
			@Override
			public void processData(ActionResult<ArrayList<Grade>> paramObject, boolean paramBoolean) {
				grades = paramObject.getRecords();
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
