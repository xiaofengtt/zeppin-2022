package com.zixueku.activity.user;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.activity.AbstractAsyncActivity;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.listerner.IntentActivityListener;

public class UserInterActionActivity extends AbstractAsyncActivity {
	private TextView mTitle_center_text;
	private ImageButton imgBtnGoBack;
	private RelativeLayout rlFeedBack;
	private RelativeLayout rlQuestionnaire;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_user_interaction);
	}

	@Override
	protected void findViewById() {
		imgBtnGoBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		mTitle_center_text = (TextView) findViewById(R.id.action_bar_center_text);
		rlFeedBack= (RelativeLayout) findViewById(R.id.rl_feed_back);
		rlQuestionnaire= (RelativeLayout) findViewById(R.id.rl_questionnaire);
	}

	@Override
	protected void setListener() {
		imgBtnGoBack.setOnClickListener(new FinishActivityListener(UserInterActionActivity.this));
		rlFeedBack.setOnClickListener(new IntentActivityListener(mContext, FeedbackActivity.class));
		rlQuestionnaire.setOnClickListener(new IntentActivityListener(mContext, QuestionnaireActivity.class));
	}

	@Override
	protected void processLogic() {
		mTitle_center_text.setText("用户互动");
	}
}
