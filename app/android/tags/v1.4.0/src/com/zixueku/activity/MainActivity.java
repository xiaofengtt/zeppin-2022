package com.zixueku.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.user.ActivityUserLogin;
import com.zixueku.activity.user.UserHomeActivity;
import com.zixueku.activity.user.UserInterActionActivity;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.entity.VersionInfo;
import com.zixueku.fragment.HomeFragment;
import com.zixueku.listerner.IntentActivityListener;
import com.zixueku.listerner.StartActivityFromResultActivityListener;
import com.zixueku.util.App;
import com.zixueku.util.AppManager;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.CircleImageView;

public class MainActivity extends AbstractAsyncActivity {

	public static MainActivity instance = null;
	private ImageButton actionBarAddCourse;
	private User userInfo;
	private ImageLoader imageLoader;
	private CircleImageView userIcon;

	private ResideMenu resideMenu;
	private ResideMenuItem user_home;
	private ResideMenuItem user_edit;
	private ResideMenuItem user_setting;
	private HomeFragment homeFragment;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_main);
	}

	/**
	 * 
	 * 初始化抽屉控件
	 */
	private void initResideMenu() {
		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);

		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.5f);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		user_home = new ResideMenuItem(mContext, R.drawable.user_home_icon, "个人信息");
		user_edit = new ResideMenuItem(mContext, R.drawable.user_notice_icon, "用户互动");
		user_setting = new ResideMenuItem(mContext, R.drawable.setting_icon, "设置");

		resideMenu.addMenuItem(user_home, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(user_edit, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(user_setting, ResideMenu.DIRECTION_LEFT);

		resideMenu.setUserName(userInfo.getScreen_name());

		//个人信息
	//	user_home.setOnClickListener(new IntentActivityListener(mContext, UserHomeActivity.class));
		user_home.setOnClickListener(new StartActivityFromResultActivityListener(mContext, UserHomeActivity.class, 1));
		//设置
		user_setting.setOnClickListener(new IntentActivityListener(mContext, PersonalActivity.class));
		//用户互动
		user_edit.setOnClickListener(new IntentActivityListener(mContext, UserInterActionActivity.class));
	}

	@Override
	protected void findViewById() {
		actionBarAddCourse = (ImageButton) findViewById(R.id.main_action_bar_add);
		userInfo = ((App) getApplication()).getUserInfo();
		imageLoader = ImageLoader.getInstance();
		userIcon = (CircleImageView) findViewById(R.id.action_bar_user_icon);
	}

	@Override
	protected void setListener() {
		userIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.d("点击title");
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});

		actionBarAddCourse.setOnClickListener(new StartActivityFromResultActivityListener(mContext, CategoryActivity.class,2));
	}

	@Override
	protected void processLogic() {
		initResideMenu();
		setUserInfo();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		if (ActivityUserLogin.instance != null) {
			ActivityUserLogin.instance.finish();
		}

		if (savedInstanceState == null) {
			homeFragment = new HomeFragment();
			changeFragment(homeFragment);
		}
		checkVersion();
	}

	protected void setUserInfo() {
		if (!"".equals(userInfo.getIcon())) {
			imageLoader.displayImage(userInfo.getIcon(), userIcon);
			imageLoader.displayImage(userInfo.getIcon(), resideMenu.getImageViewUserIcon());
		} else {
			resideMenu.getImageViewUserIcon().setImageResource(R.drawable.default_user_icon2);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0 && resultCode == 0) {
			if (intent != null) {
				Bundle data = intent.getExtras();
				int position = data.getInt("position");
				PrepareExam prepareExam = ((App) getApplication()).getPrepareExam();
				homeFragment.getPrepareExamsListData().set(position, prepareExam);
				homeFragment.getPrepareExamsAdapter().notifyDataSetChanged();
			}
		}else if (requestCode==1){
			setUserInfo();
			resideMenu.setUserName(userInfo.getScreen_name());
		}else if(requestCode==2){
			homeFragment.updateList();
		}
	}

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}

	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onBackPressed() {
		if (resideMenu.isOpened()) {
			resideMenu.closeMenu();
		} else {
			super.onBackPressed();
		}
	}

	public void checkVersion() {
		//UmengUpdateAgent.setUpdateAutoPopup(false);
		ActionResult<VersionInfo> actionResult = new ActionResult<VersionInfo>(){};
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("device", 2);
		Request request = new Request(R.string.VersionVerify, requestData, mContext, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialogIgnoError(request, new ServerDataCallback<ActionResult<VersionInfo>>() {
			@Override
			public void processData(ActionResult<VersionInfo> paramObject, boolean paramBoolean) {
				if(paramObject==null||paramObject.getRecords()==null){
					return;
				}
				final VersionInfo versionInfo = paramObject.getRecords();
				if(versionInfo.isHasNew()){
					UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
						@Override
						public void onClick(int status) {
							switch (status) {
							case UpdateStatus.Update:
								break;
							case UpdateStatus.Ignore:
								break;
							case UpdateStatus.NotNow:
								if(versionInfo.getNewVersion().getForced()){
									AppManager.getAppManager().AppExit(mContext);
								}
								break;
							}
						}
					});
					UmengUpdateAgent.update(mContext);
				}
			}
		});
		
	}

}
