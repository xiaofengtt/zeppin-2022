package com.zixueku.fragment;

import java.lang.reflect.Field;

import android.content.ClipData;
import android.content.ClipDescription;
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

import com.zixueku.R;
import com.zixueku.activity.MaterialActivity;
import com.zixueku.adapter.MaterialCombinationPagerAdapter;
import com.zixueku.entity.Material;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.widget.FixedSpeedScroller;

/**
 * 类说明 材料题中的组合题模板
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 上午9:54:17
 */
public class SuperMaterialCombinationCardFragment extends BaseFragment implements OnTouchListener, OnDragListener {
	private static final String IMAGEVIEW_TAG = "已经拖到目标区域了";
	private static final String ARG_PARENT_POSITION = "parent_position";
	private MaterialCombinationPagerAdapter combinationPagerAdapter;
	private WebView content; // 组合题的总的题干
	private ViewPager subViewPager; // 组合题的子viewpager
	private int parentPosition; // 该组合题整体的位置(在exercise对象中的位置)
	private LinearLayout topContainer;
	private ScrollView topView;
	private LinearLayout dragLayout;
	private Material material;
	Handler handler = new Handler();

	public static SuperMaterialCombinationCardFragment newInstance(int position) {
		SuperMaterialCombinationCardFragment f = new SuperMaterialCombinationCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_PARENT_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public SuperMaterialCombinationCardFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.parentPosition = getArguments().getInt(ARG_PARENT_POSITION);
		this.material = App.getInstance().getMaterial();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.material_combination, container, false);
		subViewPager = (ViewPager) contactsLayout.findViewById(R.id.material_combination_view_pager);
		MaterialActivity.subViewPagers.put(parentPosition, subViewPager);
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

		BusinessCommonMethod.setWetbViewContent(mActivity, content, material.getMaterialItems().get(parentPosition).getData().getStem());

		// -----------------------------------------------方法三
		ViewTreeObserver vto2 = topContainer.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				topContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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
			dragLayout.setOnTouchListener(SuperMaterialCombinationCardFragment.this);
			topContainer.setOnDragListener(SuperMaterialCombinationCardFragment.this);
			combinationPagerAdapter = new MaterialCombinationPagerAdapter(getChildFragmentManager(), parentPosition, material.getMaterialItems().get(parentPosition).getChildren());
			subViewPager.setAdapter(combinationPagerAdapter);
		}
	};

}
