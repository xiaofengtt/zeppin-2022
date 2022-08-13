package com.zixueku.activity;

import java.lang.reflect.Field;
import java.util.HashMap;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.ExercisePagerAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.DialogUtil;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 练习
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 下午2:32:01
 */
public class ExerciseActivity extends AbstractAsyncActivity implements OnClickListener {
	public static ExerciseActivity instance = null;
	public static SparseArray<ViewPager> subViewPagers = new SparseArray<ViewPager>();
	private int knowledgeId;
	private int subjectId;
	private ViewPager pager;
	private ExercisePagerAdapter adapter;
	private PagerSlidingTabStrip2 tabs;
	private ImageButton off;
	private ImageButton handPaper;
	private TextView actionBarTitle;
	private User user;
	private ActionResult<Exercise> actionResult;
	private Exercise exercise;
	private PrepareExam prepareExam;
	private View exerciseActionBar;
	private View handPaperActionBar;
	private TextView handPagerTitle;
	private ImageButton handPagerBackButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_exercise);
	}

	@Override
	protected void findViewById() {
		instance = this;
		tabs = (PagerSlidingTabStrip2) findViewById(R.id.exercise_tabs);
		pager = (ViewPager) findViewById(R.id.exercise_pager);
		// 获取上一个界面传进来的数据
		knowledgeId = getIntent().getIntExtra("knowledgeId", -1);
		prepareExam = ((App) getApplication()).getPrepareExam();
		subjectId = prepareExam.getSubjectid();
		exerciseActionBar = findViewById(R.id.exercise_action_bar);
		handPaperActionBar = findViewById(R.id.hand_paper_action_bar);
		off = (ImageButton) findViewById(R.id.off);
		// setData();
		handPaper = (ImageButton) findViewById(R.id.hand_paper);
		// pager.setPageTransformer(true, new ZoomOutPageTransformer());
		actionBarTitle = (TextView) findViewById(R.id.exercise_action_bar_title);
		user = ((App) getApplication()).getUserInfo();
		actionResult = new ActionResult<Exercise>() {
		};

		tabs.setHandPaperActionBar(handPaperActionBar);
		tabs.setExerciseActionBar(exerciseActionBar);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new AccelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
			mScroller.set(pager, scroller);
		} catch (Exception e) {
		}
		
		pager.setOffscreenPageLimit(2);

		handPagerBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		handPagerTitle = (TextView) findViewById(R.id.action_bar_center_text);
		//progressBarLayout = (RelativeLayout) findViewById(R.id.progress_layout);
	}

	@Override
	protected void setListener() {
		off.setOnClickListener(this);
		handPaper.setOnClickListener(this);
		handPagerBackButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.off:
			dialog();
			break;
		case R.id.action_bar_left_go_back_button:
			pager.setCurrentItem(exercise.getItems().size() - 1);
			break;
		case R.id.hand_paper:
			pager.setCurrentItem(exercise.getItems().size());
			break;
		}
	}


	@Override
	protected void processLogic() {

		handPagerTitle.setText("答题卡");
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("user.id", user.getUserId());
		requestData.put("subject.id", subjectId);
		if (knowledgeId != -1) {
			requestData.put("knowledge.id", knowledgeId);
		}

		Request request = new Request(R.string.UserTestSelectItems, requestData, this, actionResult);
		netDataConnation(request, new ServerDataCallback<ActionResult<Exercise>>() {
			@Override
			public void processData(ActionResult<Exercise> paramObject, boolean paramBoolean) {
				exercise = paramObject.getRecords();
				if (exercise == null || exercise.getItems() == null || exercise.getItems().size() == 0) {
					DialogUtil.showInfoDialog(ExerciseActivity.this, paramObject.getMessage(), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ExerciseActivity.this.finish();
						}
					});
					return;
				}
				exercise.setTitle("快速智能练习");
				exercise.setSubjectId(subjectId);
				exercise.setKnowledgeId(knowledgeId);
				setItemIndex();
				((App) getApplication()).setExercise(exercise);
				actionBarTitle.setText(exercise.getTitle());
				adapter = new ExercisePagerAdapter(getSupportFragmentManager(), pager, tabs, exercise);
				pager.setAdapter(adapter);
				tabs.setExercise(exercise);
				tabs.setViewPager(pager);
			}
		});
	}

	@Override
	public void onBackPressed() {
		dialog();
	}

	protected void dialog() {
		// 此处直接new一个Dialog对象出来，在实例化的时候传入主题
		final Dialog dialog = new Dialog(this, R.style.CustomDialogStyle);
		// 设置它的ContentView
		dialog.setContentView(R.layout.exit_test_dialog);
		dialog.setCanceledOnTouchOutside(true);

		dialog.findViewById(R.id.dialog_button_continue).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.findViewById(R.id.dialog_button_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				CommonTools.finishActivity(ExerciseActivity.this);
			}
		});

		dialog.findViewById(R.id.dialog_title_image_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void setItemIndex() {
		int i = 0;
		for (Item item : exercise.getItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					child.setIndex(i);
					i++;
				}
			} else {
				item.setIndex(i);
				i++;
			}
		}
		exercise.setTotalNum(i);
	}

}
