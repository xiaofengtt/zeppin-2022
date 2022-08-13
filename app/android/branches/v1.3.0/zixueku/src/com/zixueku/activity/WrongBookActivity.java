package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.WrongBookViewPagerAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.entity.WrongBook;
import com.zixueku.fragment.WrongBookCardFragment;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.DialogUtil;

/**
 * 类说明 错题本
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月24日 下午2:10:54
 */
public class WrongBookActivity extends AbstractAsyncActivity implements OnClickListener {
	private int subjectId;
	private User user;
	private WrongBook wrongBookInfo;
	public static ViewPager viewPager;
	private PagerAdapter viewPagerAdapter;
	private ImageButton goBack;
	private TextView title;
	public static SparseArray<ViewPager> subViewPagers = new SparseArray<ViewPager>();
	// 选项卡开关标志
	private boolean showAssert = false;
	// 选项卡
	private Fragment fragment;
	// 选项卡按钮
	private ImageButton cardButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_wrong_book);
	}

	@Override
	protected void findViewById() {
		subjectId = getIntent().getIntExtra("subjectId", -1);
		user = ((App) getApplication()).getUserInfo();
		viewPager = (ViewPager) findViewById(R.id.wrong_book_viewpager);
		this.cardButton = (ImageButton) findViewById(R.id.image_card);
		this.goBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		this.title = (TextView) findViewById(R.id.material_action_bar_title);
	}

	@Override
	protected void setListener() {
		cardButton.setOnClickListener(this);
		goBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (showAssert) {
					hideTheAssert();
				} else {
					CommonTools.finishActivity(WrongBookActivity.this);
				}
			}
		});
	}

	@Override
	protected void processLogic() {
		viewPager.setOffscreenPageLimit(2);
		this.title.setText("错题本");
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("subject.id", subjectId);
		requestData.put("user.id", user.getUserId());
		Request request = new Request(R.string.UserWrongBookItemList, requestData, WrongBookActivity.this, new ActionResult<ArrayList<Item>>() {
		});
		netDataConnation(request, new ServerDataCallback<ActionResult<List<Item>>>() {
			@Override
			public void processData(ActionResult<List<Item>> paramObject, boolean paramBoolean) {
				if (paramObject.getRecords() == null || paramObject.getRecords().size() == 0) {
					DialogUtil.showInfoDialog(WrongBookActivity.this, "该学科还没有错题!", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							WrongBookActivity.this.finish();
						}
					});
					return;
				}
				wrongBookInfo = new WrongBook();
				wrongBookInfo.setWrongItemList(paramObject.getRecords());
				BusinessCommonMethod.buildIndex(wrongBookInfo);
				((App) getApplicationContext()).setWrongBookInfo(wrongBookInfo);
				viewPagerAdapter = new WrongBookViewPagerAdapter(getSupportFragmentManager(), viewPager, wrongBookInfo);
				viewPager.setAdapter(viewPagerAdapter);
				// handler.postDelayed(runnable, Constant.LOADING_TIME);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (showAssert) {
			hideTheAssert();
		} else {
			showCardFragment();
		}
	}

	public void hideTheAssert() {
		if (fragment != null) {
			getSupportFragmentManager().beginTransaction().remove(fragment).commit();
			fragment = null;
		}
		showAssert = false;
	}

	private void showCardFragment() {
		fragment = new WrongBookCardFragment(viewPager);
		getSupportFragmentManager().beginTransaction().add(R.id.root, fragment).commit();
		showAssert = true;
	}

	@Override
	public void onBackPressed() {
		if (showAssert) {
			hideTheAssert();
		} else {
			super.onBackPressed();
		}
	}

}
