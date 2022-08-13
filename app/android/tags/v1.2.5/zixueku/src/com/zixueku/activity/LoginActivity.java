package com.zixueku.activity;

import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.service.LoginService;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

public class LoginActivity extends AbstractAsyncActivity implements PlatformActionListener, Callback, OnClickListener {

	private Platform pf = null;

	private ImageButton loginToQQButton;
	private ImageButton loginToWEIXINButton;
	private ImageButton loginToWEIBOButton;
	
	private LinearLayout loginLayout;

	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;

	public static String TEST_IMAGE;

	@Override
	protected void loadViewLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void findViewById() {
		loginToQQButton = (ImageButton) findViewById(R.id.login_item_image_QQ);
		loginToWEIXINButton = (ImageButton) findViewById(R.id.login_item_image_WEIXIN);
		loginToWEIBOButton = (ImageButton) findViewById(R.id.login_item_image_WEIBO);
		loginLayout = (LinearLayout) findViewById(R.id.login_layout);
	}

	@Override
	protected void setListener() {
		loginToQQButton.setOnClickListener(this);
		loginToWEIXINButton.setOnClickListener(this);
		loginToWEIBOButton.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {

		// initial ShareSDK
		ShareSDK.initSDK(LoginActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_item_image_QQ:
			pf = ShareSDK.getPlatform(this, QQ.NAME);
			break;
		case R.id.login_item_image_WEIXIN:
			pf = ShareSDK.getPlatform(this, Wechat.NAME);
			break;
		case R.id.login_item_image_WEIBO:
			pf = ShareSDK.getPlatform(this, SinaWeibo.NAME);
			break;
		}
		authorize(pf);
	}

	// 执行授权,获取用户信息
	// 文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
	private void authorize(Platform plat) {
		// 设置监听
		plat.setPlatformActionListener(this);
		// 关闭SSO授权
		plat.SSOSetting(false);
		// 获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
		// Perform showUser action,in order to get user info;
		plat.showUser(null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	// 设置监听http://sharesdk.cn/androidDoc/cn/sharesdk/framework/PlatformActionListener.html
	// 监听是子线程，不能Toast，要用handler处理，不要犯这么二的错误
	// Setting listener,
	// http://sharesdk.cn/androidDoc/cn/sharesdk/framework/PlatformActionListener.html
	// The listener is the child-thread that can not handle ui
	@Override
	public void onCancel(Platform platform, int action) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		/*
		 * // 各平台返回的原始数据 for (Entry<String, Object> entry : res.entrySet()) {
		 * Log.e(entry.getKey(), entry.getValue().toString()); }
		 */
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		t.printStackTrace();
		t.getMessage();

		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_TOAST: {
			String text = String.valueOf(msg.obj);
			Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_ACTION_CCALLBACK: {
			switch (msg.arg1) {
			case 1: { // 成功, successful notification
				showNotification(2000, "授权成功");
				// 授权成功后,获取用户信息，要自己解析，看看oncomplete里面的注释
				// ShareSDK只保存以下这几个通用值
				final User user = ((App) getApplication()).getUserInfo();
				PlatformDb db = pf.getDb();
				final LoginService loginService = new LoginService();
				loginService.setUserInfo(user, db);
				ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
				};

				Request request = new Request(R.string.AuthUser, loginService.setRequestData(user, 2), LoginActivity.this, actionResult);
				sendDataToServer(request, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
					@Override
					public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
						loginService.setUserInfo(user, paramObject.getRecords());
						Class<?> target = null;
						if (user.getIsFirstLogin()) {
							target = CategoryActivity.class;
						} else {
							target = MainActivity.class;
						}
						CommonTools.finishActivity(LoginActivity.this, target, R.anim.right_in, R.anim.left_out);
					}
				});

			}
				break;
			case 2: { // 失败, fail notification
				String expName = msg.obj.getClass().getSimpleName();
				if ("WechatClientNotExistException".equals(expName) || "WechatTimelineNotSupportedException".equals(expName)) {
					showNotification(2000, getString(R.string.wechat_client_inavailable));
				} else if ("GooglePlusClientNotExistException".equals(expName)) {
					showNotification(2000, getString(R.string.google_plus_client_inavailable));
				} else if ("QQClientNotExistException".equals(expName)) {
					showNotification(2000, getString(R.string.qq_client_inavailable));
				} else {
					showNotification(2000, "授权失败");
				}
			}
				break;
			case 3: { // 取消, cancel notification
				showNotification(2000, "取消授权");
			}
				break;
			}
		}
			break;
		case MSG_CANCEL_NOTIFY: {
			NotificationManager nm = (NotificationManager) msg.obj;
			if (nm != null) {
				nm.cancel(msg.arg1);
			}
		}
			break;
		}
		return false;
	}

	// 在状态栏提示分享操作,the notification on the status bar
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getApplicationContext();
			NotificationManager nm = (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);

			long when = System.currentTimeMillis();
			Notification notification = new Notification(R.drawable.ic_launcher, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
			notification.setLatestEventInfo(app, "自学酷", text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);

			if (cancelTime > 0) {
				Message msg = new Message();
				msg.what = MSG_CANCEL_NOTIFY;
				msg.obj = nm;
				msg.arg1 = id;
				UIHandler.sendMessageDelayed(msg, cancelTime, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
