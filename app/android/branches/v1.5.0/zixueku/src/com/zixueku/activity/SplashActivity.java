package com.zixueku.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.zixueku.R;
import com.zixueku.activity.user.ActivityUserLogin;
import com.zixueku.service.LoginService;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;

/**
 * 程序启动入口 用于 设置启动画面 检测是否是已注册用户
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SplashActivity extends AbstractAsyncActivity {
	private Intent intent;
	private boolean isValid; // 标示该用户知否需要跳转到登录界面
	private Class<?> to;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void findViewById() {
	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void processLogic() {
		new Handler().postDelayed(new MRun(), 500); // 0.5秒
	}

	private void init() {
		SharedPreferences preferences = mContext.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
		if (preferences.contains("userId")) {
			LoginService.loadUserInfor(preferences);
			to = MainActivity.class;
		} else {
			to = ActivityUserLogin.class;
		}
	}

	class MRun implements Runnable {
		@Override
		public void run() {
			init();
			CommonTools.finishActivity(SplashActivity.this, to,R.anim.right_in, R.anim.left_out);
		}
	}
}
