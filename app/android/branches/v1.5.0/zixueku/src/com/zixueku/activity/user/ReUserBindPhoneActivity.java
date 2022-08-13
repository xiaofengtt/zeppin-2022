package com.zixueku.activity.user;

import java.util.HashMap;
import java.util.Map;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
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

@ContentView(R.layout.activity_user_re_phone)
public class ReUserBindPhoneActivity extends BaseActivity {

	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;

	@ViewInject(R.id.et_phone)
	private LineEditText et_phone;

	@ViewInject(R.id.et_code)
	private LineEditText et_code;

	@ViewInject(R.id.btn_sendcode)
	private Button btn_sendcode;

	private String phone;

	private User user;

	private LoginService loginService;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.LOGIN_SUCCESS:
			ActionResult<HashMap<String, Object>> paramObject = (ActionResult<HashMap<String, Object>>) msg.obj;
			// loginService.formUserInfo(user, paramObject);
			Class<?> target = null;
			target = ChangePswActivity.class;
			CommonTools.finishActivity(ReUserBindPhoneActivity.this, target, R.anim.right_in, R.anim.left_out);
			break;
		case ConstantValue.LOGIN_ERROR:
			String loginMsg = (String) msg.obj;
			ToastUtil.showTextInMiddle(ReUserBindPhoneActivity.this, loginMsg, Toast.LENGTH_SHORT);
			break;
		case ConstantValue.CODE_SUCCESS:// 获取验证码成功
			com.zixueku.entity.Message message = (com.zixueku.entity.Message) msg.obj;
			if (message != null) {
				showToast(message.Message);
			}
			break;
		case ConstantValue.CODE_ERROR:// 获取验证码失败
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phonerror));
			break;
		}
	}

	@Override
	protected void initData() {
		mTitle_center_text.setText("绑定手机");
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}

	@OnClick(R.id.btn_bind)
	private void btnNextClick(View view) {
		String phone = et_phone.getText().toString().trim();
		String mCaptcha = et_code.getText().toString().trim();
		if (!phone.isEmpty() && !mCaptcha.isEmpty()) {
			if (CommonUtil.isMobileNO(phone)) {
				CaLogin(phone, mCaptcha);
			}
		} else {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.phonenmty));
		}
	}

	@OnClick(R.id.btn_sendcode)
	private void btnSendMsg(View view) {
		phone = et_phone.getText().toString().trim();
		MyCountTimer myCountTimer = new MyCountTimer(btn_sendcode);
		if (CommonUtil.isMobileNO(phone)) {
			myCountTimer.start();
			JsonHelper.instance().getMessage(ReUserBindPhoneActivity.this, phone, 1, new ServerDataCallback<ActionResult>() {
				@Override
				public void processData(ActionResult paramObject, boolean paramBoolean) {
					CommonTools.showShortToastDefaultStyle(ReUserBindPhoneActivity.this, paramObject.getMessage());
				}
			});
		} else {
			mHandler.sendEmptyMessage(ConstantValue.CODE_ERROR);
		}
	}

	private void CaLogin(String phone, String code) {
		user = ((App) getApplication()).getUserInfo();
		user.setCode(code);
		user.setPhone(phone);
		LoginRequest(5);
	}

	private void LoginRequest(int type) {
		loginService = new LoginService();
		Map<String, Object> RequestData = loginService.setRequestData(user, type);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {
		};
		Request req = new Request(R.string.AuthUser, RequestData, ReUserBindPhoneActivity.this, actionResult);
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
}
