package com.zixueku.fragment;

import java.lang.reflect.Field;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
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
import com.zixueku.R;
import com.zixueku.activity.WrongBookActivity;
import com.zixueku.adapter.WrongBookCombinationViewPagerAdapter;
import com.zixueku.entity.WrongBook;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 错题本中的组合题模板
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 上午9:54:17
 */
public class SuperWrongBookCombinationCardFragment extends BaseFragment implements OnTouchListener, OnDragListener {
	private static final String ARG_POSITION = "position";
	private static final String IMAGEVIEW_TAG = "已经拖到目标区域了";
	private Handler handler = new Handler();
	private PagerAdapter subViewPagerAdapter;
	private WebView content; // 组合题的总的题干
	private ViewPager parentViewPager;
	private ViewPager subViewPager; // 组合题的子viewpager
	private int parentPosition; // 该组合题整体的位置(在exercise对象中的位置)
	private LinearLayout topContainer;
	private ScrollView topView;
	private LinearLayout dragLayout;
	private WrongBook wrongBook;

	public static SuperWrongBookCombinationCardFragment newInstance(ViewPager veiwPager) {
		SuperWrongBookCombinationCardFragment f = new SuperWrongBookCombinationCardFragment(veiwPager);
		return f;
	}
	
	

	public SuperWrongBookCombinationCardFragment() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SuperWrongBookCombinationCardFragment(ViewPager viewPager) {
		this.parentViewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.parentPosition = getArguments().getInt(ARG_POSITION);
		this.wrongBook = App.getInstance().getWrongBookInfo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contactsLayout = inflater.inflate(R.layout.combination_layout, container, false);
		subViewPager = (ViewPager) contactsLayout.findViewById(R.id.combination_view_pager);
		WrongBookActivity.subViewPagers.put(parentPosition, subViewPager);
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

		// 初始化时获取组件的高度,设置组合面板中分隔符的位置
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
		BusinessCommonMethod.setWetbViewContent(mActivity, content, wrongBook.getWrongItemList().get(parentPosition).getData().getStem());
		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);
		return contactsLayout;
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			handler.post(runnable);
		} catch (Exception e) {

		}

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
			dragLayout.setOnTouchListener(SuperWrongBookCombinationCardFragment.this);
			topContainer.setOnDragListener(SuperWrongBookCombinationCardFragment.this);
			subViewPagerAdapter = new WrongBookCombinationViewPagerAdapter(getChildFragmentManager(), parentPosition, wrongBook, parentViewPager, subViewPager);
			subViewPager.setAdapter(subViewPagerAdapter);
		}
	};

}
