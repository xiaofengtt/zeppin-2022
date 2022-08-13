package com.zixueku.fragment;

import java.lang.reflect.Field;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.adapter.ExerciseCombinationPagerAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 组合题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 上午9:54:17
 */
public class SuperCombinationCardFragment extends BaseFragment implements OnTouchListener, OnDragListener {
	private static final String IMAGEVIEW_TAG = "已经拖到目标区域了";
	private static final String ARG_POSITION = "position";
	private ExerciseCombinationPagerAdapter combinationPagerAdapter;
	private WebView content;
	private ViewPager subViewPager; // 组合题的viewpager
	private PagerSlidingTabStrip2 tab;
	private Exercise exercise;
	private ViewPager parentViewPager; // 最外层总的viewPager
	private int parentPosition; // 该组合题整体的位置(在exercise对象中的位置)
	private LinearLayout topContainer;
	private ScrollView topView;
	private LinearLayout dragLayout;
	private Handler handler = new Handler();
	

	public static SuperCombinationCardFragment newInstance(int position, ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		SuperCombinationCardFragment f = new SuperCombinationCardFragment(viewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public SuperCombinationCardFragment(ViewPager parentViewPager, PagerSlidingTabStrip2 tab) {
		this.parentViewPager = parentViewPager;
		this.tab = tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.parentPosition = getArguments().getInt(ARG_POSITION);
		this.exercise = App.getInstance().getExercise();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View contactsLayout = inflater.inflate(R.layout.exercise_combination, container, false);
		subViewPager = (ViewPager) contactsLayout.findViewById(R.id.exercise_combination_view_pager);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			Interpolator sInterpolator = new AccelerateInterpolator();
			FixedSpeedScroller scroller = new FixedSpeedScroller(subViewPager.getContext(), sInterpolator);
			mScroller.set(subViewPager, scroller);
		} catch (Exception e) {
		}
		content = (WebView) contactsLayout.findViewById(R.id.combination_content);
		topContainer = (LinearLayout) contactsLayout.findViewById(R.id.topContainer);
		topView = (ScrollView) contactsLayout.findViewById(R.id.top_view);
		dragLayout = (LinearLayout) contactsLayout.findViewById(R.id.drag_layout);
		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);

		dragLayout.setOnTouchListener(SuperCombinationCardFragment.this);
		topContainer.setOnDragListener(SuperCombinationCardFragment.this);
		
		combinationPagerAdapter = new ExerciseCombinationPagerAdapter(getChildFragmentManager(), parentPosition, exercise.getItems().get(parentPosition)
				.getChildren(), parentViewPager, subViewPager, tab);
		subViewPager.setAdapter(combinationPagerAdapter);
		tab.setSubViewPager(subViewPager);
		ExerciseActivity.subViewPagers.put(parentPosition, subViewPager);

		// 初始化分隔条的位置
		ViewTreeObserver vto2 = topContainer.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
	                	topContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	                } else {
	                	topContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	                }
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, topContainer.getHeight() / 3 - CommonTools.dip2px(mActivity, 15), 0, 0);
				dragLayout.setLayoutParams(lp);
			}
		});

		return contactsLayout;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ClipData.Item item = new ClipData.Item((String) v.getTag());
		ClipData data = new ClipData(IMAGEVIEW_TAG, new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
		v.startDrag(data, new View.DragShadowBuilder(), null, 0);
		return false;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		final int action = event.getAction();
		switch (action) {
		case DragEvent.ACTION_DRAG_STARTED:
			return true;
		case DragEvent.ACTION_DRAG_ENTERED:
			return true;
		case DragEvent.ACTION_DRAG_LOCATION:
			dragLayout.setY(event.getY() - dragLayout.getHeight() / 2);
			topView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) event.getY() + dragLayout.getHeight() / 2));
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			return true;
		case DragEvent.ACTION_DROP:
			dragLayout.setY(event.getY() - dragLayout.getHeight() / 2);
			return true;
		case DragEvent.ACTION_DRAG_ENDED:
			return true;
		default:
			break;
		}
		return false;
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			BusinessCommonMethod.setWetbViewContent(mActivity, content, exercise.getItems().get(parentPosition).getData().getStem());
		}
	};

}
