package com.zixueku.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.service.LoginService;
import com.zixueku.util.App;

/**
 * 程序启动入口 用于 设置启动画面 检测是否是已注册用户
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SplashActivity extends AbstractAsyncActivity {

	private Intent intent;
	private User user;
	private LoginService loginService;
	private boolean isValid; // 标示该用户知否需要跳转到登录界面

	private void init() {
		// initial ShareSDK
		ShareSDK.initSDK(SplashActivity.this);
		Platform[] platforms = ShareSDK.getPlatformList(this);

		for (Platform platform : platforms) {
			if (platform.isValid()) {
				// 授权成功后,获取用户信息，要自己解析，看看oncomplete里面的注释
				// ShareSDK只保存以下这几个通用值
				PlatformDb db = platform.getDb();
				loginService.setUserInfo(user, db);
				intent = new Intent(SplashActivity.this, MainActivity.class);
				isValid = true;
				return;
			}
		}
		intent = new Intent(SplashActivity.this, LoginActivity.class);
	}

	@Override
	protected void loadViewLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		user = ((App) getApplication()).getUserInfo();
		loginService = new LoginService();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processLogic() {
		
		new Handler().postDelayed(new Runnable() {
			// 为了减少代码使用匿名Handler创建一个延时的调用
			public void run() {
				if (intent == null) {
					init();
				}

				if (isValid) {
					ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
					};

					Request request = new Request(R.string.AuthUser, loginService.setRequestData(user, 2), SplashActivity.this, actionResult);
					netDataConnationNoProgressDialog(request, new ServerDataCallback<ActionResult<Map<String, Object>>>() {
						@Override
						public void processData(ActionResult<Map<String, Object>> paramObject, boolean paramBoolean) {
							loginService.setUserInfo(user, paramObject.getRecords());
							SplashActivity.this.startActivity(intent); // 启动界面
							SplashActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
							SplashActivity.this.finish(); // 关闭自己这个开场屏
						}
					});
				} else {
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					SplashActivity.this.startActivity(intent); // 启动界面
					SplashActivity.this.finish(); // 关闭自己这个开场屏
					SplashActivity.this.overridePendingTransition(0, 0);
				}
			}
		}, 500); // 0.5秒
		init();
	}
}
