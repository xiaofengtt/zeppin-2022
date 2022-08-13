package com.zixueku.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
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
import com.zixueku.entity.Result;
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
		/*
		 * for( Map.Entry<Integer,ViewPager>
		 * entry:WrongBookActivity.subViewPagers.entrySet()){
		 * entry.getValue().getAdapter().notifyDataSetChanged(); }
		 */

	}

	// 从错题本中删除组合题
	public static void deleteCombinationItemFromWrongBook(final Context context, WrongBook wrongBookInfo, final int position, int subPosition, ViewPager viewPager, ViewPager subViewPager, User user) {
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

	public static void judge(WrongBook wrongBook) {
		int totalNum = wrongBook.getTotalNum();
		int rightNum = totalNum;
		int wrongNum = 0;
		for (Item item : wrongBook.getWrongItemList()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					Set<CustomerAnswer> customerAnswer = child.getCustomerAnswer();
					List<Result> resultList = child.getData().getResults(); // 正确答案
					String[] rightAnswer = resultList.get(0).getInx().split(",");
					if (customerAnswer.size() != rightAnswer.length) {
						child.setRight(false);
						rightNum--;

						if (customerAnswer.size() != 0) {
							wrongNum++;
						}

						continue;
					}
					child.setRight(true);
					for (CustomerAnswer answer : customerAnswer) {
						if (!contains(rightAnswer, answer.getInx())) {
							child.setRight(false);
							rightNum--;
							wrongNum++;
							break;
						}
					}
				}
			} else {
				Set<CustomerAnswer> customerAnswer = item.getCustomerAnswer();
				List<Result> resultList = item.getData().getResults(); // 正确答案
				String[] rightAnswer = resultList.get(0).getInx().split(",");
				if (customerAnswer.size() != rightAnswer.length) {
					item.setRight(false);
					rightNum--;

					if (customerAnswer.size() != 0) {
						wrongNum++;
					}
					continue;
				}
				item.setRight(true);
				for (CustomerAnswer answer : customerAnswer) {
					if (!contains(rightAnswer, answer.getInx())) {
						item.setRight(false);
						rightNum--;
						wrongNum++;
						break;
					}
				}
			}
		}
		wrongBook.setRightNum(rightNum);
		wrongBook.setWrongNum(wrongNum);
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
		if(!item.isRight()){
			Log.e("item.isRight",item.isRight()+"");
			Log.e("completeType",  String.valueOf((item.isRight()) ? 1 : 0));
		}
		
		Log.e("item.isRight",item.isRight()+"");
		Log.e("completeType",  String.valueOf((item.isRight()) ? 1 : 0));
		ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>() {
		};
		Request request = new Request(R.string.UserWrongBookSubmitItem, requestData, context, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
			@Override
			public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
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

}
