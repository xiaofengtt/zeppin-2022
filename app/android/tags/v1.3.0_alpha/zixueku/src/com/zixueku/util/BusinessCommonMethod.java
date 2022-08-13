package com.zixueku.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月27日 上午11:01:41
 */
public class BusinessCommonMethod {
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
	public static void deleteCombinationItemFromWrongBook(final Context context, WrongBook wrongBookInfo, int position, int subPosition, ViewPager viewPager,
			ViewPager subViewPager, User user) {
		subViewPager = WrongBookActivity.subViewPagers.get(position);
		viewPager = WrongBookActivity.viewPager;
		List<Item> child = wrongBookInfo.getWrongItemList().get(position).getChildren();
		if (child.size() == 1) {
			deleteItemFromWrongBook(context, user, child.get(0));
			wrongBookInfo.getWrongItemList().remove(position);
			BusinessCommonMethod.buildIndex(wrongBookInfo);
		} else {
			deleteItemFromWrongBook(context, user, wrongBookInfo.getWrongItemList().get(position).getChildren().remove(subPosition));
			BusinessCommonMethod.buildIndex(wrongBookInfo);
			subViewPager.getAdapter().notifyDataSetChanged();
		}
		viewPager.getAdapter().notifyDataSetChanged();

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
		requestData.put("item.id", item.getItemId());
		requestData.put("item.blankInx", item.getBlankInx());
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
		requestData.put("item.id", item.getItemId());
		requestData.put("item.blankInx", item.getBlankInx());
		StringBuffer customStr = new StringBuffer();
		for (CustomerAnswer custom : item.getCustomerAnswer()) {
			customStr.append(custom.getInx() + ",");
		}
		CharSequence cuStr = "";
		int isAnswered = 0; // 0该题没作答 1该题已作答
		if (customStr.length() > 0) {
			cuStr = customStr.subSequence(0, customStr.length() - 1);
			requestData.put("reference", cuStr); // 用户答案
			isAnswered = 1;
		}

		String rightAnswer[] = item.getData().getResults().get(0).getInx().split(",");
		Set<CustomerAnswer> customerAnswer = item.getCustomerAnswer();
		
		for (CustomerAnswer answer : customerAnswer) {
			if (!contains(rightAnswer, answer.getInx())) {
				item.setRight(false);
				break;
			}
		}

		requestData.put("completeType", (item.isRight()) ? 1 : 0); // 是否答对
		requestData.put("isAnswered", isAnswered);

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

}
