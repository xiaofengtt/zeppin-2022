package com.zixueku.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip3;
import com.astuetz.PagerSlidingTabStrip4;
import com.makeramen.segmented.SegmentedRadioGroup;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisPagerAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 试题解析
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 下午2:32:01
 */
public class AnalysisActivity extends AbstractAsyncActivity implements OnCheckedChangeListener {
	public static SparseArray<ViewPager> subViewPagers = new SparseArray<ViewPager>();
	private Handler handler = new Handler();
	private SegmentedRadioGroup segmentText;
	private ViewPager pager;
	private AnalysisPagerAdapter adapter;
	private PagerSlidingTabStrip3 tabs;
	private ViewPager pager2;
	private AnalysisPagerAdapter adapter2;
	private PagerSlidingTabStrip4 tabs2;
	private ImageButton goBackButton;
	private Exercise errorExercise;
	private Exercise exercise;
	private int position;
	private int subPosition;
	private TextView noErrorText;
	
	

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_analysis);
	}

	@Override
	protected void findViewById() {
		tabs = (PagerSlidingTabStrip3) findViewById(R.id.exercise_tabs);
		pager = (ViewPager) findViewById(R.id.exercise_pager);

		tabs2 = (PagerSlidingTabStrip4) findViewById(R.id.exercise_tabs2);
		pager2 = (ViewPager) findViewById(R.id.exercise_pager2);
		noErrorText = (TextView) findViewById(R.id.no_error_item);

		exercise = ((App) getApplication()).getExercise();
		// 获取上一个界面传进来的数据
		position = getIntent().getIntExtra("parentIndex", 0);
		subPosition = getIntent().getIntExtra("subIndex", -1);
		segmentText = (SegmentedRadioGroup) findViewById(R.id.segment_text);
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new AccelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(pager.getContext(), sInterpolator);
			mScroller.set(pager, scroller);
		} catch (Exception e) {
		}
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(AnalysisActivity.this));
		segmentText.setOnCheckedChangeListener(this);
	}

	@Override
	protected void processLogic() {
		errorItems();
		tabs2.setErrorExercise(errorExercise);
		adapter = new AnalysisPagerAdapter(getSupportFragmentManager(), exercise, pager, tabs);
		pager.setAdapter(adapter);
		tabs.setExercise(exercise);
		tabs.setViewPager(pager);
		adapter2 = new AnalysisPagerAdapter(getSupportFragmentManager(), errorExercise, pager2, tabs2);
		pager2.setAdapter(adapter2);
		tabs2.setExercise(exercise);
		tabs2.setViewPager(pager2);
		pager.setCurrentItem(position);
		if (subPosition != -1) {
			handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);
		}
	}

	@Override
	public void onBackPressed() {
		CommonTools.finishActivity(AnalysisActivity.this);
	}

	private void errorItems() {
		errorExercise = (Exercise) exercise.deepCopy();
		List<Item> errorLists = new ArrayList<Item>();
		errorExercise.setTotalNum(exercise.getTotalNum());

		for (Item item : exercise.getItems()) {
			Item it = (Item) item.deepCopy();
			if (item.getModelType() == 4) {
				List<Item> subList = new ArrayList<Item>();
				for (Item child : item.getChildren()) {
					if (!child.isRight()) {
						subList.add(child);
					}
				}
				if (subList.size() > 0) {
					it.setChildren(subList);
					errorLists.add(it);
				}
			} else {
				if (!item.isRight()) {
					errorLists.add(item);
				}
			}
		}
		errorExercise.setItems(errorLists);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		errorItems();
		noErrorText.setVisibility(View.GONE);
		if (group == segmentText) {
			if (checkedId == R.id.button_one) {
				pager2.setVisibility(View.GONE);
				tabs2.setVisibility(View.GONE);
				pager.setVisibility(View.VISIBLE);
				tabs.setVisibility(View.VISIBLE);
			} else if (checkedId == R.id.button_three) {
				pager.setVisibility(View.GONE);
				tabs.setVisibility(View.GONE);
				
				if(errorExercise.getItems().size()==0){
					noErrorText.setVisibility(View.VISIBLE);
					return;
				}
				
				pager2.setVisibility(View.VISIBLE);
				tabs2.setVisibility(View.VISIBLE);
			}
		}
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			subViewPagers.get(position).setCurrentItem(subPosition);
		}
	};
}
