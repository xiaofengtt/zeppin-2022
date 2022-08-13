package com.zixueku.activity.user;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.zixueku.R;
import com.zixueku.activity.AbstractAsyncActivity;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.CommonTools;

public class UserAboutActivity extends AbstractAsyncActivity {
	private TextView mTitle_center_text;
	private ImageButton goBackButton;
	private Button uploadButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_user_about);
	}

	@Override
	protected void findViewById() {
		mTitle_center_text = (TextView) findViewById(R.id.action_bar_center_text);
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		uploadButton = (Button) findViewById(R.id.btn_upload);
	}

	@Override
	protected void setListener() {
		goBackButton.setOnClickListener(new FinishActivityListener(this));
	}

	@Override
	protected void processLogic() {
		mTitle_center_text.setText("关于自学酷");
		String s = String.format("当前已是最新版本:%s", CommonTools.getVersionName(mContext));
		uploadButton.setText(s);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				switch (updateStatus) {
				case UpdateStatus.Yes: // has update
					uploadButton(updateInfo);
					break;
				case UpdateStatus.No: // has no update
					// Toast.makeText(mContext, "没有更新",
					// Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.NoneWifi: // none wifi
					// Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新",
					// Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.Timeout: // time out
					// Toast.makeText(mContext, "超时",
					// Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		UmengUpdateAgent.forceUpdate(mContext);
	}

	public void uploadButton(final UpdateResponse updateInfo) {
		uploadButton.setEnabled(true);
		String s = String.format("有新版本%s可更新", updateInfo.version);
		uploadButton.setText(s);
		uploadButton.setTextColor(getResources().getColor(R.color.white));
		uploadButton.setBackgroundResource(R.drawable.green_button_img);
		uploadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
			}
		});
	}

}
