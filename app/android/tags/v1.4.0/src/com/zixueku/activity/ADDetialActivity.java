package com.zixueku.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zixueku.R;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.Constant;
import com.zixueku.widget.CustomShareBoard;
import com.zixueku.widget.ProgressWebView;

/**
 * 类说明 用户反馈
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月8日 下午1:08:16
 */
public class ADDetialActivity extends AbstractAsyncActivity {
	private final UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
	private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
	private static final String ARG_URL = "url";
	private static final String ARG_TITLE = "title";
	private String url;
	private String title;
	private TextView tvTitle;
	private ImageButton imgBtnGoBack;
	private ProgressWebView mWebView;
	private ImageButton mShareButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_ad_detail);
	}

	@Override
	protected void findViewById() {
		tvTitle = (TextView) findViewById(R.id.action_bar_center_text);
		imgBtnGoBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		mWebView = (ProgressWebView) findViewById(R.id.webView_ad_detail);
		mShareButton = (ImageButton) findViewById(R.id.img_btn_share);
		title = getIntent().getStringExtra(ARG_TITLE);
		url = getIntent().getStringExtra(ARG_URL);
	}

	@Override
	protected void setListener() {
		imgBtnGoBack.setOnClickListener(new FinishActivityListener(ADDetialActivity.this));
		mShareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomShareBoard shareBoard = new CustomShareBoard(ADDetialActivity.this);
				shareBoard.showAtLocation(ADDetialActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
			}
		});
	}

	@Override
	protected void processLogic() {
		tvTitle.setText(title);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.loadUrl(url);

		// 配置需要分享的相关平台
		configPlatforms();
		// 设置分享的内容
		setShareContent();
	}

	/**
	 * 配置分享平台参数</br>
	 */
	private void configPlatforms() {
		// 添加新浪SSO授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加QQ、QZone平台
		addQQQZonePlatform();
		// 添加微信、微信朋友圈平台
		addWXPlatform();
	}

	/**
	 * 根据不同的平台设置不同的分享内容</br>
	 */
	private void setShareContent() {

		// 配置SSO
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setShareContent(title + " " + url);
		mController.setShareMedia(sinaContent);

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(title + " " + url);
		weixinContent.setTitle(title);
		weixinContent.setTargetUrl(url);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(title + " " + url);
		circleMedia.setTitle(title);
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		// 设置QQ空间分享内容
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(url);
		qzone.setTargetUrl(url);
		qzone.setTitle(title);
		mController.setShareMedia(qzone);
		
		
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx451427d0a7a64238";
		String appSecret = "57cf742e2810c2885e8f9330993a9f94";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(ADDetialActivity.this, appId, appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(ADDetialActivity.this, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String appId = "1104620628";
		String appKey = "bFXB8eqo4icRirod";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(ADDetialActivity.this, appId, appKey);
		qqSsoHandler.setTargetUrl(url);
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(ADDetialActivity.this, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mController.getConfig().cleanListeners();
	}

}
