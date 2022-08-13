package com.zixueku.activity.user;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.utils.Log;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.LoginActivity;
import com.zixueku.base.BaseActivity;
import com.zixueku.base.ConstantValue;
import com.zixueku.customview.LineEditText;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.net.JsonHelper;
import com.zixueku.service.LoginService;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.CommonUtil;
import com.zixueku.util.HandlerUtils;
import com.zixueku.util.MyCountTimer;
import com.zixueku.util.ToastUtil;

/**
 * 忘记密码
 * 
 */
@ContentView(R.layout.activity_user_forpsw)
public class ForPswActivity extends BaseActivity {

	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;

	@ViewInject(R.id.et_phone)
	private LineEditText et_phone;

	@ViewInject(R.id.btn_next)
	private Button btn_next;

	@ViewInject(R.id.btn_send_captcha)
	private Button btn_send_captcha;

	@ViewInject(R.id.et_captcha)
	private LineEditText et_captcha;

	private String phone;

	private User user;

	private LoginService loginService;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.LOGIN_SUCCESS:
			ActionResult<HashMap<String, Object>> paramObject = (ActionResult<HashMap<String, Object>>) msg.obj;
			user.setUserId(((Double) paramObject.getRecords().get("uid")).intValue());
			Class<?> target = null;
			target = ChangePswActivity.class;
			CommonTools.finishActivity(ForPswActivity.this, target, R.anim.right_in, R.anim.left_out);
			break;
		case ConstantValue.LOGIN_ERROR:
			String loginMsg = (String) msg.obj;
			ToastUtil.showTextInMiddle(ForPswActivity.this, loginMsg, Toast.LENGTH_SHORT);
			break;
		case ConstantValue.CODE_SUCCESS:// 获取验证码成功
			com.zixueku.entity.Message message = (com.zixueku.entity.Message) msg.obj;
			if (message != null) {
				showToast(message.Message);
			} else {
				showToast("手机号码不存在！");
			}
			break;
		case ConstantValue.CODE_ERROR:// 获取验证码失败
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phonerror));
			break;
		}
	}

	@Override
	protected void initData() {
		mTitle_center_text.setText("忘记密码");
	}

	@OnClick(R.id.btn_next)
	private void btnNextClick(View view) {
		String phone = et_phone.getText().toString().trim();
		String mCaptcha = et_captcha.getText().toString().trim();
		if (!CommonTools.isMobileNO(phone)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phone_error));
			return;
		}
		if (!CommonTools.isCodeNO(mCaptcha)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.code_error));
			return;
		}

		CaLogin(phone, mCaptcha);

	}

	private void CaLogin(String phone, String code) {
		user = App.getInstance().getUserInfo();
		user.setCode(code);
		user.setPhone(phone);
		LoginRequest(4);
	}

	private void LoginRequest(int type) {
		loginService = new LoginService();
		Map<String, Object> RequestData = loginService.setRequestData(user, type);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
		};
		Request req = new Request(R.string.AuthUser, RequestData, ForPswActivity.this, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<HashMap<String, Object>>>() {
			private Message message;

			@Override
			public void processData(ActionResult<HashMap<String, Object>> paramObject, boolean paramBoolean) {
				message = Message.obtain();
				String status = paramObject.getStatus();
				if (status.equals("success")) {
					message.obj = paramObject;
					message.what = ConstantValue.LOGIN_SUCCESS;
					mHandler.sendMessage(message);
				} else if (status.equals("error")) {
					String loginMsg = paramObject.getMessage();
					message.obj = loginMsg;
					message.what = ConstantValue.LOGIN_ERROR;
					mHandler.sendMessage(message);
				}
			}
		});
	}

	@OnClick(R.id.btn_send_captcha)
	private void btnSendMsg(View view) {
		phone = et_phone.getText().toString().trim();
		MyCountTimer myCountTimer = new MyCountTimer(btn_send_captcha);
		if (CommonUtil.isMobileNO(phone)) {
			myCountTimer.start();
			JsonHelper.instance().getMessage(ForPswActivity.this, phone, 0, new ServerDataCallback<ActionResult>() {
				@Override
				public void processData(ActionResult paramObject, boolean paramBoolean) {
					CommonTools.showShortToastDefaultStyle(ForPswActivity.this,paramObject.getMessage());
				}
			});
		} else {
			mHandler.sendEmptyMessage(ConstantValue.CODE_ERROR);
		}
	}

	/**
	 * 需要开启的activity的 字节码文件
	 * 
	 * @param clazz
	 */
	private void startedActivity(Class<? extends android.app.Activity> clazz) {
		Intent intent = new Intent(ForPswActivity.this, clazz);
		startActivity(intent);
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}
}
