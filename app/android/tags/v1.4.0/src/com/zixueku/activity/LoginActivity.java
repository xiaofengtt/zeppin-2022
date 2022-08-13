package com.zixueku.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.user.ForPswActivity;
import com.zixueku.customview.LineEditText;
import com.zixueku.customview.LoadingDialog;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.net.JsonHelper;
import com.zixueku.service.LoginService;
import com.zixueku.util.AnalysisEventAgent;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.CommonUtil;
import com.zixueku.util.Constant;
import com.zixueku.util.MyCountTimer;
import com.zixueku.util.ToastUtil;

public class LoginActivity extends AbstractAsyncActivity implements OnClickListener {
	private UMSocialService mController;
	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	private ImageButton loginToQQButton;
	private ImageButton loginToWEIXINButton;
	private ImageButton loginToWEIBOButton;
	private LinearLayout loginLayout;
	public static String TEST_IMAGE;
	private TextView mTv_forget_psw;
	private TextView mTv_ca_login;
	private ImageButton action_bar_left_go_back_button;
	private TextView mTitle_center_text;
	private RelativeLayout rl_psd;
	private LinearLayout ll_ca_login;
	private Boolean isPsdShow;
	private TextView tv_ca_login;
	private Button btn_login;
	private LineEditText et_phone;
	private LineEditText et_psd;
	private LineEditText et_captcha;
	private String mPhone;
	private String mPassWord;
	private String mCaCode;
	private Button btn_send_captcha;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_login);
		isPsdShow = true;
	}

	@Override
	protected void findViewById() {
		loginToQQButton = (ImageButton) findViewById(R.id.login_item_image_QQ);
		loginToWEIXINButton = (ImageButton) findViewById(R.id.login_item_image_WEIXIN);
		loginToWEIBOButton = (ImageButton) findViewById(R.id.login_item_image_WEIBO);
		loginLayout = (LinearLayout) findViewById(R.id.login_layout);
		mTv_forget_psw = (TextView) findViewById(R.id.tv_forget_psw);
		mTv_ca_login = (TextView) findViewById(R.id.tv_ca_login);
		mTitle_center_text = (TextView) findViewById(R.id.action_bar_center_text);
		action_bar_left_go_back_button = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		rl_psd = (RelativeLayout) findViewById(R.id.rl_psd);
		ll_ca_login = (LinearLayout) findViewById(R.id.ll_ca_login);
		tv_ca_login = (TextView) findViewById(R.id.tv_ca_login);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_phone = (LineEditText) findViewById(R.id.et_phone);
		et_psd = (LineEditText) findViewById(R.id.et_psd);
		et_captcha = (LineEditText) findViewById(R.id.et_captcha);
		btn_send_captcha = (Button) findViewById(R.id.btn_send_captcha);
		mController = UMServiceFactory.getUMSocialService("com.umeng.login");
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mPhone = et_phone.getText().toString().trim();
		mPassWord = et_psd.getText().toString().trim();
		mCaCode = et_captcha.getText().toString().trim();
	}

	@Override
	protected void setListener() {
		loginToQQButton.setOnClickListener(this);
		loginToWEIXINButton.setOnClickListener(this);
		loginToWEIBOButton.setOnClickListener(this);
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hiddenSoftInput();
				initData();
				if (isPsdShow) {
					Login(mPhone, mPassWord);
				} else {
					CaLogin(mPhone, mCaCode);
				}
			}
		});
		mTv_forget_psw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startedActivity(ForPswActivity.class);
			}
		});
		/**
		 * 验证码登录和密码登录
		 */
		mTv_ca_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPsdShow) {
					rl_psd.setVisibility(View.GONE);
					ll_ca_login.setVisibility(View.VISIBLE);
					tv_ca_login.setText("密码登录");
					isPsdShow = false;
				} else {
					rl_psd.setVisibility(View.VISIBLE);
					ll_ca_login.setVisibility(View.GONE);
					tv_ca_login.setText("验证码登录");
					isPsdShow = true;
				}
			}
		});
		action_bar_left_go_back_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btn_send_captcha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				captchaClick();
			}
		});

	}

	protected void CaLogin(String phone, String mCaCode) {
		if (!CommonTools.isMobileNO(phone)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phone_error));
			return;
		}
		if (!CommonTools.isCodeNO(mCaCode)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.code_error));
			return;
		}

		final User user = App.getInstance().getUserInfo();
		user.setPhone(phone);
		user.setCode(mCaCode);
		Map<String, Object> RequestData = LoginService.setRequestData(user, 4);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
		};
		Request req = new Request(R.string.AuthUser, RequestData, mContext, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
			@Override
			public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
				if ("error".equals(paramObject.getStatus())) {
					ToastUtil.showTextInMiddle(mContext, paramObject.getMessage(), Toast.LENGTH_LONG);
				} else {
					AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.CodeLogin);
					LoginService.setInfor2User(user, paramObject.getRecords(), 4);
					LoginService.persistenceUserInfor(mContext, user);
					Class<?> target = null;
					if (user.getIsFirstLogin()) {
						target = CategoryActivity.class;
					} else {
						target = MainActivity.class;
					}
					CommonTools.finishActivity(LoginActivity.this, target, R.anim.right_in, R.anim.left_out);
				}
			}
		});
	}

	protected void Login(String phone, String mPassWord) {
		if (!CommonTools.isMobileNO(phone)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phone_error));
			return;
		}
		if (!CommonTools.isPassword(mPassWord)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.pwd_error));
			return;
		}

		final User user = App.getInstance().getUserInfo();
		user.setPassword(mPassWord);
		user.setPhone(phone);
		Map<String, Object> RequestData = LoginService.setRequestData(user, 1);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
		};
		Request req = new Request(R.string.AuthUser, RequestData, mContext, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
			@Override
			public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
				if ("error".equals(paramObject.getStatus())) {
					ToastUtil.showTextInMiddle(mContext, paramObject.getMessage(), Toast.LENGTH_LONG);
				} else {
					AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.PasswordLogin);
					LoginService.setInfor2User(user, paramObject.getRecords(), 1);
					LoginService.persistenceUserInfor(mContext, user);
					Class<?> target = null;
					if (user.getIsFirstLogin()) {
						target = CategoryActivity.class;
					} else {
						target = MainActivity.class;
					}
					CommonTools.finishActivity(LoginActivity.this, target, R.anim.right_in, R.anim.left_out);
				}
			}
		});
	}

	/**
	 * 需要开启的activity的 字节码文件
	 * 
	 * @param clazz
	 */
	private void startedActivity(Class<? extends android.app.Activity> clazz) {
		Intent intent = new Intent(LoginActivity.this, clazz);
		startActivity(intent);
	}

	@Override
	protected void processLogic() {
		mTitle_center_text.setText("登录");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_item_image_QQ:
			authorize(SHARE_MEDIA.QQ);
			break;
		case R.id.login_item_image_WEIXIN:
			authorize(SHARE_MEDIA.WEIXIN);
			break;
		case R.id.login_item_image_WEIBO:
			authorize(SHARE_MEDIA.SINA);
			break;
		}
	}

	private void authorize(SHARE_MEDIA shareMedia) {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this, "wx451427d0a7a64238", "57cf742e2810c2885e8f9330993a9f94");
		wxHandler.addToSocialSDK();
		// 参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, "1104620628", "bFXB8eqo4icRirod");
		qqSsoHandler.addToSocialSDK();
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.doOauthVerify(mContext, shareMedia, new UMAuthListener() {
			@Override
			public void onStart(SHARE_MEDIA platform) {
				// Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
					AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.QQLogin);
					// 授权成功
					getPlatformInfo(value, platform);
				} else {
					Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getPlatformInfo(final Bundle value, final SHARE_MEDIA paramSHARE_MEDIA) {
		mController.getPlatformInfo(mContext, paramSHARE_MEDIA, new UMDataListener() {
			@Override
			public void onStart() {
				// Toast.makeText(mContext, "获取平台数据开始...",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(int status, Map<String, Object> info) {
				if (status == 200 && info != null) {
					// 公共属性设置
					final User user = App.getInstance().getUserInfo();
					String plateformName = paramSHARE_MEDIA.toString();
					user.setAuth_type(Constant.AUTH_PLATFORMS.get(plateformName));

					if ("qq".equals(plateformName)) {
						user.setUid(value.getString("uid"));
						user.setGender("男".equals(info.get("gender").toString()) ? (short) 0 : (short) 1);
						user.setScreen_name(info.get("screen_name").toString());
						user.setIcon(info.get("profile_image_url").toString());
						AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.QQLogin);
					} else if ("weixin".equals(plateformName)) {
						user.setGender("1".equals(info.get("sex").toString()) ? (short) 0 : (short) 1);
						user.setUid(value.getString("uid"));
						user.setScreen_name(info.get("nickname").toString());
						user.setIcon(info.get("headimgurl").toString());
						AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.WeiXinLogin);
					} else if ("sina".equals(plateformName)) {
						user.setUid(info.get("uid").toString());
						user.setGender("1".equals(info.get("gender").toString()) ? (short) 0 : (short) 1);
						user.setScreen_name(info.get("screen_name").toString());
						user.setIcon(info.get("profile_image_url").toString());
						AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.WeiBoLogin);
					}

					ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
					};
					Request request = new Request(R.string.AuthUser, LoginService.setRequestData(user, 2), mContext, actionResult);
					netDataConnationNoProgressDialog(request, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
						@Override
						public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
							LoginService.setInfor2User(user, paramObject.getRecords(), 2);
							LoginService.persistenceUserInfor(mContext, user);
							Class<?> target = null;
							if (user.getIsFirstLogin()) {
								target = CategoryActivity.class;
							} else {
								target = MainActivity.class;
							}
							CommonTools.finishActivity(LoginActivity.this, target, R.anim.right_in, R.anim.left_out);
						}
					});
				} else {
					Log.d("TestData", "发生错误：" + status);
				}
			}
		});
	}

	private void captchaClick() {
		String phone = et_phone.getText().toString().trim();
		if (CommonUtil.isMobileNO(phone)) {
			hiddenSoftInput();
			MyCountTimer myCountTimer = new MyCountTimer(btn_send_captcha);
			myCountTimer.start();
			JsonHelper.instance().getMessage(LoginActivity.this, phone, 0, new ServerDataCallback<ActionResult>() {
				@Override
				public void processData(ActionResult paramObject, boolean paramBoolean) {
					CommonTools.showShortToastDefaultStyle(mContext, paramObject.getMessage());
				}
			});
		} else {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phone_error));
		}
	}

	public void showImageDiaglog(Drawable drawable, String string) {
		LoadingDialog ld = new LoadingDialog(this);
		ld.setImageResouce(drawable);
		ld.setText(string);
		ld.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	// 收起键盘
	private void hiddenSoftInput() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
		}
	}

}
