package com.zixueku.activity.user;

import java.util.HashMap;
import java.util.List;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.base.BaseActivity;
import com.zixueku.base.ConstantValue;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.util.AnalysisEventAgent;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

@ContentView(R.layout.activity_user_settingpsw)
public class ChangePswActivity extends BaseActivity {
	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;
	@ViewInject(R.id.et_psd)
	private com.zixueku.customview.LineEditText et_psw;
	@ViewInject(R.id.et_re_psw)
	private com.zixueku.customview.LineEditText et_re_psw;
	private User user;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.USERINFO_SUCCESS:
			AnalysisEventAgent.onEvent(ChangePswActivity.this, AnalysisEventAgent.ResetPassword);
			finish();
			break;
		case ConstantValue.USERINFO_ERROR:
			String errorMsg = (String) msg.obj;
			showToast(errorMsg);
			break;
		}
	}

	@Override
	protected void initData() {
		mTitle_center_text.setText("修改密码");
	}

	@OnClick(R.id.btn_ok)
	private void btnPswOk(View view) {
		// 密码
		String psw = et_psw.getText().toString().trim();
		// 确认密码
		String rePsw = et_re_psw.getText().toString().trim();

		if (!CommonTools.isPassword(psw)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.pwd_error));
			return;
		}
		
		if (!CommonTools.isPassword(rePsw)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.re_pwd_error));
			return;
		}
		
		if (!psw.equals(rePsw)) {
			showImageDiaglog(getResources().getDrawable(R.drawable.error_warning), getResources().getString(R.string.pwd_not_equals));
			return;
		}
		user = ((App) getApplication()).getUserInfo();
		user.setPassword(rePsw);
		upDataUserInfo(rePsw);
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}

	private void upDataUserInfo(final String user_info) {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		ActionResult<List<User>> actionResult = new ActionResult<List<User>>() {
		};
		requestData.put("phone", user.getPhone());
		requestData.put("password", user.getPassword());
		Request req = new Request(R.string.UserUpdate, requestData, ChangePswActivity.this, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<List<User>>>() {
			private Message message;
			@Override
			public void processData(ActionResult<List<User>> paramObject, boolean paramBoolean) {
				message = Message.obtain();
				String status = paramObject.getStatus();
				if (status.equals("success")) {
					message.obj = ReUserProActivity.class;
					message.what = ConstantValue.USERINFO_SUCCESS;
					mHandler.sendMessage(message);
				} else if (status.equals("error")) {
					String loginMsg = paramObject.getMessage();
					message.obj = loginMsg;
					message.what = ConstantValue.USERINFO_ERROR;
					mHandler.sendMessage(message);
				}
			}
		});
	}
}
