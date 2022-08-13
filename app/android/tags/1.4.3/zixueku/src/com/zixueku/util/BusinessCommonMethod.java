package com.zixueku.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView2;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.WrongBookActivity;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.entity.WrongBook;
import com.zixueku.listerner.MyWebViewClient;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月27日 上午11:01:41
 */
public class BusinessCommonMethod {
	public static String imagebasePath = "http://api.zixueku.cn";
	private static SoundPool soundPool;

	// 错题本中给每个item按序编号 从零开始
	public static void buildIndex(WrongBook wrongBook) {
		int i = 0;
		for (Item item : wrongBook.getWrongItemList()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					child.setIndex(i);
					i++;
				}
			} else {
				item.setIndex(i);
				i++;
			}
		}
		wrongBook.setTotalNum(i);
	}

	// 从错题本中删除选择题
	public static void deleteChoiseItemFromWrongBook(final Context context, WrongBook wrongBookInfo, int position, ViewPager viewPager, User user) {
		deleteItemFromWrongBook(context, user, wrongBookInfo.getWrongItemList().remove(position));
		BusinessCommonMethod.buildIndex(wrongBookInfo);
		viewPager.getAdapter().notifyDataSetChanged();
	}

	// 从错题本中删除组合题
	public static void deleteCombinationItemFromWrongBook(final Context context, WrongBook wrongBookInfo, final int position, int subPosition,
			ViewPager viewPager, ViewPager subViewPager, User user) {
		subViewPager = WrongBookActivity.subViewPagers.get(position);
		viewPager = WrongBookActivity.viewPager;
		List<Item> child = wrongBookInfo.getWrongItemList().get(position).getChildren();
		if (child.size() == 1) {
			deleteItemFromWrongBook(context, user, child.get(0));
			wrongBookInfo.getWrongItemList().remove(position);
			BusinessCommonMethod.buildIndex(wrongBookInfo);
			WrongBookActivity.subViewPagers.remove(position);
		} else {
			deleteItemFromWrongBook(context, user, wrongBookInfo.getWrongItemList().get(position).getChildren().remove(subPosition));
			BusinessCommonMethod.buildIndex(wrongBookInfo);
			subViewPager.getAdapter().notifyDataSetChanged();
		}
		viewPager.getAdapter().notifyDataSetChanged();

		// handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);

		// viewPager.getAdapter().notifyDataSetChanged();

		/*
		 * deleteItemFromWrongBook(context, user,
		 * wrongBookInfo.getWrongItemList(
		 * ).get(position).getChildren().remove(subPosition));
		 * BusinessCommonMethod.buildIndex(wrongBookInfo); subViewPager =
		 * WrongBookActivity.subViewPagers.get(position);
		 * subViewPager.getAdapter().notifyDataSetChanged();
		 * 
		 * if(child.size() == 1){
		 * wrongBookInfo.getWrongItemList().remove(position); }
		 * viewPager.getAdapter().notifyDataSetChanged();
		 */
	}

	private static void deleteItemFromWrongBook(final Context context, User user, Item item) {
		AnalysisEventAgent.onEvent(context, AnalysisEventAgent.WrongBookDelete);
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("item.id", String.valueOf(item.getItemId()));
		requestData.put("item.blankInx", String.valueOf(item.getBlankInx()));
		ActionResult<HashMap> actionResult = new ActionResult<HashMap>() {
		};
		Request request = new Request(R.string.UserWrongBookDeleteItem, requestData, context, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<HashMap>>() {
			@Override
			public void processData(ActionResult<HashMap> paramObject, boolean paramBoolean) {
				CommonTools.showShortToastDefaultStyle(context, "删除成功!");
			}
		});
	}

	public static void slideview(final float p1, final float p2, final View deleteFloatButton) {
		TranslateAnimation animation = new TranslateAnimation(0, p1, 0, p2);
		animation.setDuration(100);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				int left = deleteFloatButton.getLeft() + (int) (p1);
				int top = deleteFloatButton.getTop();
				int width = deleteFloatButton.getWidth();
				int height = deleteFloatButton.getHeight();
				deleteFloatButton.clearAnimation();
				deleteFloatButton.layout(left, top, left + width, top + height);
			}
		});
		deleteFloatButton.startAnimation(animation);
	}

	private static boolean contains(String[] rightAnswer, String customerAnswer) {
		boolean mark = false;
		for (String result : rightAnswer) {
			if (result.equals(customerAnswer)) {
				mark = true;
				break;
			}
		}
		return mark;
	}

	// userWrongBookSubmitItem 错题本中答题
	// version,user.id,item.id,item.blankInx,
	// isAnswered=1,completeType,reference,content
	public static void wrongBookSubmitItem(final Context context, Item item) {
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("item.id", String.valueOf(item.getItemId()));
		requestData.put("item.blankInx", String.valueOf(item.getBlankInx()));
		StringBuffer customStr = new StringBuffer();
		for (CustomerAnswer custom : item.getCustomerAnswer()) {
			customStr.append(custom.getInx() + ",");
		}
		CharSequence cuStr = "";
		int isAnswered = 0; // 0该题没作答 1该题已作答
		if (customStr.length() > 0) {
			cuStr = customStr.subSequence(0, customStr.length() - 1);
			requestData.put("reference", cuStr.toString()); // 用户答案
			isAnswered = 1;
		}

		String rightAnswer[] = item.getData().getResults().get(0).getInx().split(",");
		Set<CustomerAnswer> customerAnswer = item.getCustomerAnswer();
		item.setRight(true);
		for (CustomerAnswer answer : customerAnswer) {
			if (!contains(rightAnswer, answer.getInx())) {
				item.setRight(false);
				break;
			}
		}
		requestData.put("completeType", String.valueOf((item.isRight()) ? 1 : 0)); // 是否答对
		requestData.put("isAnswered", String.valueOf(isAnswered));
		ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>() {
		};
		Request request = new Request(R.string.UserWrongBookSubmitItem, requestData, context, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
			@Override
			public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
				int previousProgress = 0;
				int newProgress = (int) paramObject.getRecords().getProgress();
				TestAbilityChange previousChange = App.getInstance().getTestAbilityChange();
				if (previousChange == null) {
					previousProgress = (int) App.getInstance().getPrepareExam().getProgress();
				} else {
					previousProgress = (int) previousChange.getProgress();
				}
				BusinessCommonMethod.achievementDialog(context, previousProgress, newProgress);
				App.getInstance().setTestAbilityChange(paramObject.getRecords());
			}
		});
	}

	public static void setWetbViewContent(Context context, WebView webView, String content) {
		webView.setVerticalScrollBarEnabled(false);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setDefaultFontSize(17);
		webSettings.setBlockNetworkImage(true);
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML><HEAD><style type=\"text/css\">p{line-height:160%}</style></HEAD><body>");
		sb.append(content);
		sb.append("</body></HTML>");
		webView.loadDataWithBaseURL(imagebasePath, sb.toString(), "text/html", "utf-8", null);
		webView.setWebViewClient(new MyWebViewClient());
		// 添加js交互接口类，并起别名 imagelistner
		webView.addJavascriptInterface(new JavascriptInterface(context), "imagelistner");
	}

	public static void setTextHtmlContent(Context context, TextView textView, String content) {
		ImageGetter imageGetter = new HtmlRemoteImageGetter(textView, context, null);
		Spanned htmlSpan = Html.fromHtml(content, imageGetter, null);
		textView.setText(htmlSpan);
	}

	public static void setTextHtmlContent(Context context, ExpandableTextView expandableTextView, String content) {
		ImageGetter imageGetter = new HtmlRemoteImageGetter(expandableTextView.getmTv(), context, null);
		Spanned htmlSpan = Html.fromHtml(content, imageGetter, null);
		expandableTextView.setText(htmlSpan);
	}

	public static void setTextHtmlContent(Context context, ExpandableTextView2 expandableTextView, String content) {
		ImageGetter imageGetter = new HtmlRemoteImageGetter(expandableTextView.getmTv(), context, null);
		Spanned htmlSpan = Html.fromHtml(content, imageGetter, null);
		expandableTextView.setText(htmlSpan);
	}

	public static void achievementDialog(Context context, int previousProgress, int newProgress) {
		boolean displaySound = false;
		if (newProgress > previousProgress) {
			if (newProgress >= 100 && previousProgress < 100) {
				displaySound = true;
				DialogUtil.achievementDialog(context, R.drawable.achievement_100);
			}
			if (newProgress >= 60 && previousProgress < 60) {
				displaySound = true;
				DialogUtil.achievementDialog(context, R.drawable.achievement_60);
			}
			if (newProgress >= 30 && previousProgress < 30) {
				displaySound = true;
				DialogUtil.achievementDialog(context, R.drawable.achievement_30);
			}
			if (newProgress >= 10 && previousProgress < 10) {
				displaySound = true;
				DialogUtil.achievementDialog(context, R.drawable.achievement_10);
			}

			if (displaySound) {
				displaySound(context, R.raw.result_succ);
			}
		}
	}

	public static void displaySound(Context context, int resourceId) {
		if (soundPool == null) {
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		}
		soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
			}
		});
		soundPool.load(context, resourceId, 1);
	}
}
