package com.zixueku.activity.user;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.zixueku.customview.LineEditText;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.Constant;

@ContentView(R.layout.activity_user_re_nikename)
public class ReNikeNameActivity extends BaseActivity {

	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;

	@ViewInject(R.id.et_nikename)
	private LineEditText et_nikename;

	private User user;

	@Override
	protected void initHandler(Message msg) {
		switch (msg.what) {
		case ConstantValue.USERINFO_SUCCESS:
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
		mTitle_center_text.setText("修改昵称");
		user = ((App) getApplication()).getUserInfo();
		et_nikename.setText(user.getScreen_name());
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}

	@OnClick(R.id.btn_ok)
	private void btnOkClick(View view) {
		String user_nikeName = et_nikename.getText().toString().trim();
		upDataUserInfo(user_nikeName);
	}
	
	@OnClick(R.id.text_delete_icon)
	private void imgTextDeleteClick(View view){
		et_nikename.setText("");
	}

	private void upDataUserInfo(final String user_nikeName) {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		ActionResult<List<User>> actionResult = new ActionResult<List<User>>() {
		};
		requestData.put("nickname", user_nikeName);
		Request req = new Request(R.string.UserUpdate, requestData, ReNikeNameActivity.this, actionResult);
		netDataConnation(req, new ServerDataCallback<ActionResult<List<User>>>() {
			private Message message;

			@Override
			public void processData(ActionResult<List<User>> paramObject, boolean paramBoolean) {
				message = Message.obtain();
				String status = paramObject.getStatus();
				if (status.equals("success")) {
					message.obj = ReNikeNameActivity.class;
					message.what = ConstantValue.USERINFO_SUCCESS;
					mHandler.sendMessage(message);
					updateLocalUserInfor("nickname", user_nikeName);
					user.setScreen_name(user_nikeName);
				} else if (status.equals("error")) {
					String loginMsg = paramObject.getMessage();
					message.obj = loginMsg;
					message.what = ConstantValue.USERINFO_ERROR;
					mHandler.sendMessage(message);
				}
			}
		});
	}

	public void updateLocalUserInfor(String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
