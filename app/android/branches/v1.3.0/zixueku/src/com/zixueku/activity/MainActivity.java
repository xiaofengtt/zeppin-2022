package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.PrepareExamsAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.listerner.IntentActivityListener;
import com.zixueku.listerner.PrepareExamsItemClickListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.widget.AbScrollView;
import com.zixueku.widget.CircleImageView;

public class MainActivity extends AbstractAsyncActivity implements  ServerDataCallback<ActionResult<ArrayList<PrepareExam>>> ,ErrorListener  {

	public static MainActivity instance = null;
	private LinearLayout personalInfor;
	private ImageButton actionBarAddCourse;
	private User userInfo;
	private ImageLoader imageLoader;
	private CircleImageView userIcon;
	private TextView userName;
	// private ImageButton actionBarInfor;
	private SwipeMenuListView prepareExamsList;
	private List<PrepareExam> prepareExamsListData;
	private AbScrollView noFouceLayout;
	private ActionResult<ArrayList<PrepareExam>> actionResult;
	private PrepareExamsAdapter prepareExamsAdapter;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void findViewById() {
		personalInfor = (LinearLayout) findViewById(R.id.personal_infor);
		actionBarAddCourse = (ImageButton) findViewById(R.id.main_action_bar_add);
		userInfo = ((App) getApplication()).getUserInfo();
		imageLoader = ImageLoader.getInstance();
		userIcon = (CircleImageView) findViewById(R.id.user_icon);
		userName = (TextView) findViewById(R.id.user_name);
		// actionBarInfor = (ImageButton) findViewById(R.id.action_bar_infor);
		prepareExamsList = (SwipeMenuListView) findViewById(R.id.prepare_exams_list);
		noFouceLayout = (AbScrollView) findViewById(R.id.no_focus_layout);
		actionResult = new ActionResult<ArrayList<PrepareExam>>() {
		};
	}

	@Override
	protected void setListener() {
		personalInfor.setOnClickListener(new IntentActivityListener(MainActivity.this, PersonalActivity.class, R.anim.left_in, R.anim.right_out));
		OnClickListener courseListener = new IntentActivityListener(MainActivity.this, CategoryActivity.class);
		actionBarAddCourse.setOnClickListener(courseListener);
		prepareExamsList.setOnItemClickListener(new PrepareExamsItemClickListener(MainActivity.this));
	}

	@Override
	protected void processLogic() {
		setUserInfo();
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("user.id", userInfo.getUserId());
		Request request = new Request(R.string.UserSubjectList, requestData, this, actionResult);
		serverConnection(request,this,null);
	}

	protected void setUserInfo() {
		if (!"".equals(userInfo.getIcon())) {
			imageLoader.displayImage(userInfo.getIcon(), userIcon);
		}
		userName.setText(userInfo.getUsername());
	}

	protected void createSideMunu() {
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				// deleteItem.setBackground(new
				// StateListDrawable(R.drawable.holder_bg));
				StateListDrawable states = new StateListDrawable();
				states.addState(new int[] { android.R.attr.state_pressed }, getResources().getDrawable(R.color.darkred));
				states.addState(new int[] { android.R.attr.state_focused }, getResources().getDrawable(R.color.darkred));
				states.addState(new int[] {}, getResources().getDrawable(R.color.red));
				deleteItem.setBackground(states);
				// set item width
				deleteItem.setWidth(CommonTools.dip2px(getApplicationContext(), 75));
				// set a icon
				deleteItem.setIcon(R.drawable.del_icon);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		// set creator
		prepareExamsList.setMenuCreator(creator);

		// step 2. listener item click event
		prepareExamsList.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					// delete
					HashMap<String, Object> requestData = new HashMap<String, Object>();
					requestData.put("user.id", userInfo.getUserId());
					requestData.put("subject.id", prepareExamsListData.get(position).getSubjectid());

					Request request = new Request(R.string.UserSubjectDelete, requestData, MainActivity.this, ParserType.USER_DEFINED, ActionResult.class);
					netDataConnationNoProgressDialog(request, new ServerDataCallback<ActionResult<?>>() {
						@Override
						public void processData(ActionResult<?> paramObject, boolean paramBoolean) {
							if ("success".equals(paramObject.getStatus())) {
								prepareExamsListData.remove(position);
								prepareExamsAdapter.notifyDataSetChanged();
								CommonTools.showShortToastDefaultStyle(MainActivity.this, paramObject.getMessage());
								if (prepareExamsListData.size() == 0) {
									noFouceLayout.setVisibility(View.VISIBLE);
								}
								((App) getApplication()).setPrepareExam(null);
							}
						}
					});

					break;
				}
				return false;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0 && resultCode == 0) {
			if (intent != null) {
				Bundle data = intent.getExtras();
				int position = data.getInt("position");
				PrepareExam prepareExam = ((App) getApplication()).getPrepareExam();
				prepareExamsListData.set(position, prepareExam);
				prepareExamsAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
	}

	

	@Override
	public void processData(ActionResult<ArrayList<PrepareExam>> paramObject,
			boolean paramBoolean) {
		prepareExamsListData = paramObject.getRecords();
		if (prepareExamsListData != null && !prepareExamsListData.isEmpty()) {
			noFouceLayout.setVisibility(View.GONE);
			prepareExamsAdapter = new PrepareExamsAdapter(MainActivity.this, prepareExamsListData);
			prepareExamsList.setAdapter(prepareExamsAdapter);
			createSideMunu();
		} else {
			noFouceLayout.setVisibility(View.VISIBLE);
		}
	}
	

}
