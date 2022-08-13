package com.zixueku.activity;

import java.lang.reflect.Field;

import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip3;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisPagerAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 试题解析
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 下午2:32:01
 */
public class AnalysisActivity extends AbstractAsyncActivity {
	private ViewPager pager;
	private AnalysisPagerAdapter adapter;
	private PagerSlidingTabStrip3 tabs;
	private ImageButton goBackButton;
	private TextView actionBarTitle;

	private Exercise exercise;

	private int position;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_analysis);
	}

	@Override
	protected void findViewById() {
		tabs = (PagerSlidingTabStrip3) findViewById(R.id.exercise_tabs);
		pager = (ViewPager) findViewById(R.id.exercise_pager);
		exercise = ((App) getApplication()).getExercise();
		// 获取上一个界面传进来的数据
		position = getIntent().getIntExtra("position", 0);

		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		// pager.setPageTransformer(true, new ZoomOutPageTransformer());
		actionBarTitle = (TextView) findViewById(R.id.action_bar_center_text);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new AccelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
			// scroller.setFixedDuration(5000);
			mScroller.set(pager, scroller);
		} catch (Exception e) {
		}
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(AnalysisActivity.this));
	}

	@Override
	protected void processLogic() {
		actionBarTitle.setText("答案解析");
		adapter = new AnalysisPagerAdapter(getSupportFragmentManager(), exercise, pager,tabs);
		pager.setAdapter(adapter);
		tabs.setExercise(exercise);
		tabs.setViewPager(pager);
		pager.setCurrentItem(position);
	}

	@Override
	public void onBackPressed() {
		CommonTools.finishActivity(AnalysisActivity.this);
	}

}
