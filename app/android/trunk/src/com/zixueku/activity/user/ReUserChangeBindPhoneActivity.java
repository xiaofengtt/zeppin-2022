package com.zixueku.activity.user;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zixueku.R;
import com.zixueku.base.BaseActivity;
import com.zixueku.customview.LineEditText;
import com.zixueku.util.CommonUtil;

@ContentView(R.layout.activity_changephone)
public class ReUserChangeBindPhoneActivity extends BaseActivity {
	public static ReUserChangeBindPhoneActivity instance;

	@ViewInject(R.id.action_bar_center_text)
	private TextView mTitle_center_text;
	
	@ViewInject(R.id.et_phone)
	private LineEditText et_phone;

	private String phone;

	@Override
	protected void initHandler(Message msg) {

	}

	@Override
	protected void initData() {
		mTitle_center_text.setText("更换绑定手机");
		instance =this;
	}

	@OnClick(R.id.action_bar_left_go_back_button)
	private void backToUserHome(View view) {
		finish();
	}

	@OnClick(R.id.btn_next)
	private void btnNextClick(View view) {
		phone = et_phone.getText().toString().trim();
		if(CommonUtil.isMobileNO(phone)){
			startedActivity(ReUserSendCodeActivity.class);
		}else {
			showImageDiaglog(
					getResources().getDrawable(R.drawable.error_warning),
					getResources().getString(R.string.phonerror));
		}
	}

	/**
	 * 需要开启的activity的 字节码文件
	 * 
	 * @param clazz
	 */
	private void startedActivity(Class<? extends android.app.Activity> clazz) {
		Intent intent = new Intent(ReUserChangeBindPhoneActivity.this, clazz);
		intent.putExtra("phone", phone);
		startActivity(intent);
	}
}
