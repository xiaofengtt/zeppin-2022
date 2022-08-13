package com.zixueku.fragment;

import java.lang.reflect.Field;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.IPagerSlidingTabStrip;
import com.astuetz.PagerSlidingTabStrip3;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisCombinationPagerAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.URLImageParser;
import com.zixueku.widget.FixedSpeedScroller;
import com.zixueku.widget.LazyFragment;

/**
 * 类说明 组合题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 上午9:54:17
 */
public class SuperAnalysisCombinationCardFragment extends LazyFragment implements OnTouchListener, OnDragListener {
	private PagerAdapter pagerAdapter;
	private TextView content;
	private ViewPager subViewPager; // 组合题的viewpager
	private IPagerSlidingTabStrip tab;
	private Exercise exercise;
	private ViewPager parentViewPager; // 最外层总的viewPager
	private TextView typeName;
	private TextView sourceName;
	private int parentPosition; // 该组合题整体的位置(在exercise对象中的位置)
	private static final String ARG_POSITION = "position";

	private ImageView dragImg;
	private LinearLayout topContainer;
	private ScrollView topView;
	private static final String IMAGEVIEW_TAG = "已经拖到目标区域了";
	private LinearLayout dragLayout;

	// 标志位，标志已经初始化完成。
	private boolean isPrepared;

	public static SuperAnalysisCombinationCardFragment newInstance(Exercise exercise, int position, ViewPager viewPager, IPagerSlidingTabStrip tab) {
		SuperAnalysisCombinationCardFragment f = new SuperAnalysisCombinationCardFragment(exercise,viewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public SuperAnalysisCombinationCardFragment(Exercise exercise,ViewPager parentViewPager, IPagerSlidingTabStrip tab) {
		this.parentViewPager = parentViewPager;
		this.tab = tab;
		this.exercise = exercise;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.parentPosition = getArguments().getInt(ARG_POSITION);
		//this.exercise = ((App) (getActivity().getApplication())).getExercise();
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

		content = (TextView) contactsLayout.findViewById(R.id.combination_content);

		dragImg = (ImageView) contactsLayout.findViewById(R.id.combination_drag_icon);
		topContainer = (LinearLayout) contactsLayout.findViewById(R.id.topContainer);
		topView = (ScrollView) contactsLayout.findViewById(R.id.top_view);
		dragLayout = (LinearLayout) contactsLayout.findViewById(R.id.drag_layout);

		dragLayout.setOnTouchListener(this);
		topContainer.setOnDragListener(this);

		String htmlContentStr = exercise.getItems().get(parentPosition).getData().getStem();
		StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		String ind = (parentPosition + 1) + "、";
		if (htmlContentStr.startsWith("<p>")) {
			htmlContentStrBui.insert(3, ind);
		} else if (htmlContentStr.startsWith("<div>")) {
			htmlContentStrBui.insert(5, ind);
		}

		URLImageParser p = new URLImageParser(content, this.getActivity());
		Spanned htmlSpan = Html.fromHtml(htmlContentStrBui.toString(), p, null);

		content.setText(htmlSpan);
		pagerAdapter = new AnalysisCombinationPagerAdapter(getChildFragmentManager(),exercise, parentPosition, exercise.getItems().get(parentPosition).getChildren(),
				parentViewPager, subViewPager);
		subViewPager.setAdapter(pagerAdapter);
		tab.setSubViewPager(subViewPager);

		// 初始化分隔条的位置
		ViewTreeObserver vto2 = topContainer.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				topContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, topContainer.getHeight() / 3 - CommonTools.dip2px(getActivity(), 15), 0, 0);
				dragLayout.setLayoutParams(lp);
			}
		});

		isPrepared = true;
		lazyLoad();
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

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
	}

}
