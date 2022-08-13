package com.zixueku.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;
import com.zixueku.entity.User;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.App;
import com.zixueku.widget.CircleImageView;

public class PersonalActivity extends AbstractAsyncActivity {

	private ImageButton goBackButton;
	private Button exitButton;
	private TextView centerTextView;
	private User user;
	private ImageLoader imageLoader;

	private TextView userName;
	private CircleImageView userIcon;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_personal);
		//getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		//getActionBar().setCustomView(R.layout.action_bar);
	}

	@Override
	protected void findViewById() {
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		centerTextView = (TextView) findViewById(R.id.action_bar_center_text);
		exitButton = (Button) findViewById(R.id.personal_exit);

		userName = (TextView) findViewById(R.id.user_name);
		userIcon = (CircleImageView) findViewById(R.id.user_icon);
		imageLoader = ImageLoader.getInstance();
		user = ((App) getApplication()).getUserInfo();
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(PersonalActivity.this, R.anim.right_in, R.anim.left_out));

		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// initial ShareSDK
				ShareSDK.initSDK(PersonalActivity.this);
				Platform[] platforms = ShareSDK.getPlatformList(PersonalActivity.this);
				for (Platform platform : platforms) {
					if (platform.isValid()) {
						// 如果要删除授权信息，重新授权
						// platform.SSOSetting(true);
						platform.removeAccount();
						platform.getDb().removeAccount();
						ShareSDK.removeCookieOnAuthorize(true);
					}
				}
				Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub
		centerTextView.setText(R.string.me);
		if (!"".equals(user.getIcon())) {
			imageLoader.displayImage(user.getIcon(), userIcon);
		}

		userName.setText(user.getUsername());
	}

	@Override
	public void onBackPressed() {
		mOnBackPressed();
		super.onBackPressed();
	}

	private void mOnBackPressed() {
		PersonalActivity.this.finish();
		// 设置界面间的切换动画 下一个界面的进入动画 当前界面的退出动画
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

}
