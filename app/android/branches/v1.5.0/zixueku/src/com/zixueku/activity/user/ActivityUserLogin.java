package com.zixueku.activity.user;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zixueku.R;
import com.zixueku.activity.AbstractAsyncActivity;
import com.zixueku.activity.LoginActivity;
import com.zixueku.adapter.LoginPager;

public class ActivityUserLogin extends AbstractAsyncActivity {

	public static Activity instance = null;

	private ViewPager login_view_page;

	private LinearLayout viewPoint;

	private ArrayList<View> pageViews;

	private ImageView mPointView;

	private ImageView[] mPointViews;

	private final int[] imageIds = { R.drawable.login_a, R.drawable.login_b, R.drawable.login_c, R.drawable.login_d };

	public void loginClick(View view) {
		startedActivity(LoginActivity.class);
	}

	public void registClick(View view) {
		startedActivity(UserRegistActivity.class);
	}

	/**
	 * 需要开启的activity的 字节码文件
	 * 
	 * @param clazz
	 */
	private void startedActivity(Class<? extends android.app.Activity> clazz) {
		Intent intent = new Intent(ActivityUserLogin.this, clazz);
		startActivity(intent);
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_user_login);
	}

	@Override
	protected void findViewById() {
		login_view_page = (ViewPager) findViewById(R.id.login_view_page);
		viewPoint = (LinearLayout) findViewById(R.id.viewPoint);
		instance = this;
		pageViews = new ArrayList<View>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(ActivityUserLogin.this);
			ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			imageView.setLayoutParams(viewPagerImageViewParams);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setBackgroundResource(imageIds[i]);
			pageViews.add(imageView);
		}
		initPoint();
	}

	@Override
	protected void setListener() {
		// 给viewpager设置适配器
		login_view_page.setAdapter(new LoginPager(pageViews));
		// 给viewpager设置监听事件
		login_view_page.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				// 遍历数组让当前选中图片下的小圆点设置颜色
				for (int i = 0; i < mPointViews.length; i++) {
					mPointViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);

					if (arg0 != i) {
						mPointViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	private void initPoint() {
		mPointViews = new ImageView[pageViews.size()];
		LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		// 设置每个小圆点距离左边的间距
		margin.setMargins(10, 0, 0, 0);
		for (int i = 0; i < pageViews.size(); i++) {
			mPointView = new ImageView(ActivityUserLogin.this);
			// 设置每个小圆点的宽高
			mPointView.setLayoutParams(new LayoutParams(10, 10));
			mPointViews[i] = mPointView;
			if (i == 0) {
				// 默认选中第一张图片
				mPointViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				// 其他图片都设置未选中状态
				mPointViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			viewPoint.addView(mPointViews[i], margin);
		}
	}
}
