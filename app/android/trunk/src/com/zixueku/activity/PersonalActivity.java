package com.zixueku.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.zixueku.R;
import com.zixueku.activity.user.ActivityUserLogin;
import com.zixueku.activity.user.ChangePswActivity;
import com.zixueku.activity.user.UserAboutActivity;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.listerner.IntentActivityListener;
import com.zixueku.util.Constant;

public class PersonalActivity extends AbstractAsyncActivity {

	private ImageButton goBackButton;
	private Button exitButton;
	private TextView centerTextView;

	private RelativeLayout rl_about;
	private RelativeLayout rl_change_psw;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_personal);
	}

	@Override
	protected void findViewById() {
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		centerTextView = (TextView) findViewById(R.id.action_bar_center_text);
		exitButton = (Button) findViewById(R.id.personal_exit);
		rl_about = (RelativeLayout) findViewById(R.id.rl_about);
		rl_change_psw = (RelativeLayout) findViewById(R.id.rl_change_psw);
	}

	@Override
	protected void setListener() {
		rl_about.setOnClickListener(new IntentActivityListener(mContext, UserAboutActivity.class));
		rl_change_psw.setOnClickListener(new IntentActivityListener(mContext, ChangePswActivity.class));
		goBackButton.setOnClickListener(new FinishActivityListener(PersonalActivity.this));
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
				SocializeClientListener socializeClientListener = new SocializeClientListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onComplete(int arg0, SocializeEntity arg1) {
						// TODO Auto-generated method stub
					}
				};
				mController.deleteOauth(mContext, SHARE_MEDIA.SINA, socializeClientListener);
				mController.deleteOauth(mContext, SHARE_MEDIA.QQ, socializeClientListener);
				mController.deleteOauth(mContext, SHARE_MEDIA.WEIXIN, socializeClientListener);

				SharedPreferences preferences = mContext.getSharedPreferences(Constant.USER_FILE_NAME, Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(PersonalActivity.this, ActivityUserLogin.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub
		centerTextView.setText(R.string.setting);
	}

}
