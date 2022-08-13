package com.zixueku.activity.user;

import android.widget.ImageButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.activity.AbstractAsyncActivity;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.widget.ProgressWebView;

/**
 * 类说明 调查问卷
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月8日 下午1:08:16
 */
public class QuestionnaireActivity extends AbstractAsyncActivity {
	private final static String FeedbackUrl = "http://form.mikecrm.com/f.php?t=60ervz";
	private TextView tvTitle;
	private ImageButton imgBtnGoBack;
	private ProgressWebView mWebView;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_questionnaire);
	}

	@Override
	protected void findViewById() {
		tvTitle = (TextView) findViewById(R.id.action_bar_center_text);
		imgBtnGoBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		mWebView = (ProgressWebView) findViewById(R.id.webView_questionnaire);
	}

	@Override
	protected void setListener() {
		imgBtnGoBack.setOnClickListener(new FinishActivityListener(QuestionnaireActivity.this));
	}

	@Override
	protected void processLogic() {
		tvTitle.setText("学霸问卷");
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(FeedbackUrl);
	}

}
