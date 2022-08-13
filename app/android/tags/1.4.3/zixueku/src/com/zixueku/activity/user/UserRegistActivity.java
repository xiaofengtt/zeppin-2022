package com.zixueku.activity.user;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.LoginActivity;
import com.zixueku.base.BaseActivity;
import com.zixueku.base.ConstantValue;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.net.JsonHelper;
import com.zixueku.service.LoginService;
import com.zixueku.util.AnalysisEventAgent;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.CommonUtil;
import com.zixueku.util.HandlerUtils;
import com.zixueku.util.MyCountTimer;

@ContentView(R.layout.activity_user_regist)
public class UserRegistActivity extends BaseActivity {

	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;

	@ViewInject(R.id.et_phone)
	private EditText et_phone;

	@ViewInject(R.id.et_psd)
	private EditText et_psd;

	@ViewInject(R.id.et_captcha)
	private EditText et_captcha;

	@ViewInject(R.id.btn_send_captcha)
	private Button btn_send_captcha;
	private LoginService loginService;
	private User user;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.REGIST_SUCCESS:// 注册成功
			ActionResult<HashMap<String, Object>> paramObject = (ActionResult<HashMap<String, Object>>) msg.obj;
			AnalysisEventAgent.onEvent(UserRegistActivity.this, AnalysisEventAgent.UserRegister);
			showToast("注册成功,请登录");
			CommonTools.finishActivity(this, LoginActivity.class, R.anim.right_in, R.anim.left_out);
			break;
		case ConstantValue.REGIST_ERROR:// 注册失败
			String registMsg = (String) msg.obj;
			showToast(registMsg);
			break;
		case ConstantValue.CODE_SUCCESS:// 获取验证码成功
			com.zixueku.entity.Message message = (com.zixueku.entity.Message) msg.obj;
			if (message != null) {
				showToast(message.Message);
			} else {
				showToast("该号码已被注册！");
			}
			break;
		case ConstantValue.CODE_ERROR:// 获取验证码失败
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phonerror));
			break;
		}
	}

	@Override
	protected void initData() {
		mTitle_center_text.setText("注册");
		user = App.getInstance().getUserInfo();
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}

	@OnClick(R.id.btn_send_captcha)
	private void captchaClick(View view) {
		String phone = et_phone.getText().toString().trim();
		MyCountTimer myCountTimer = new MyCountTimer(btn_send_captcha);
		if (CommonUtil.isMobileNO(phone)) {
			myCountTimer.start();
			JsonHelper.instance().getMessage(UserRegistActivity.this, phone, 1, new ServerDataCallback<ActionResult>() {
				@Override
				public void processData(ActionResult paramObject, boolean paramBoolean) {
					CommonTools.showShortToastDefaultStyle(UserRegistActivity.this, paramObject.getMessage());
				}
			});
		} else {
			mHandler.sendEmptyMessage(ConstantValue.CODE_ERROR);
		}
	}

	@OnClick(R.id.btn_regist)
	private void registClick(View view) {
		String phone = et_phone.getText().toString().trim();
		if (!CommonTools.isMobileNO(phone)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phone_error));
			return;
		}
		String code = et_captcha.getText().toString().trim();
		if (!CommonTools.isCodeNO(code)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.code_error));
			return;
		}
		String password = et_psd.getText().toString().trim();
		if (!CommonTools.isPassword(password)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.pwd_error));
			return;
		}
		user.setPhone(phone);
		user.setPassword(password);
		user.setCode(code);

		regist(user);
	}

	private void regist(final User user) {
		loginService = new LoginService();
		Map<String, Object> RequestData = loginService.setRequestData(user, 3);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
		};
		Request req = new Request(R.string.AuthUser, RequestData, UserRegistActivity.this, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
			@Override
			public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
				HashMap<String, Object> records = paramObject.getRecords();
				String status = paramObject.getStatus();
				if (status.equals("success")) {
					HandlerUtils.sendMessage(UserRegistActivity.this, ConstantValue.REGIST_SUCCESS, paramObject);
				} else if (status.equals("error")) {
					String message = paramObject.getMessage();
					HandlerUtils.sendMessage(UserRegistActivity.this, ConstantValue.REGIST_ERROR, message);
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
		Intent intent = new Intent(UserRegistActivity.this, clazz);
		startActivity(intent);
	}
}
