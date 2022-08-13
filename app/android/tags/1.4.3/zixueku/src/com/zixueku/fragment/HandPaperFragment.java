package com.zixueku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.activity.ResultActivity;
import com.zixueku.adapter.HandPaperAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.entity.Request;
import com.zixueku.entity.Result;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.JSONUtil;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.LazyFragment;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月6日 下午5:20:11
 */
public class HandPaperFragment extends LazyFragment {
	private Exercise exercise;
	private GridView gridView;
	private Button submitButton;
	private User userInfo;
	private ViewPager viewPager;

	// 标志位，标志已经初始化完成。
	private boolean isPrepared;

	private ActionResult<TestAbilityChange> actionResult;

	public HandPaperFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HandPaperFragment(ViewPager viewPager) {
		super();
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_hand_paper, container, false);
		// XXX初始化view的各控件

		gridView = (GridView) view.findViewById(R.id.activity_hand_paper_gridView);
		// actionBarCenterText = (TextView)
		// view.findViewById(R.id.action_bar_center_text);
		submitButton = (Button) view.findViewById(R.id.hand_paper_submit_button);
		// goBack = (ImageButton)
		// view.findViewById(R.id.action_bar_left_go_back_button);
		actionResult = new ActionResult<TestAbilityChange>() {
		};

		// actionBarCenterText.setText("答题卡");
		setListener();
		isPrepared = true;
		lazyLoad();
		return view;
	}

	protected void setListener() {
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				judge2();
				Map<String, Object> requestData = new HashMap<String, Object>();
				requestData.put("user.id", userInfo.getUserId());
				requestData.put("ssoUserTest.id", exercise.getSsoUserTestId());
				requestData.put("paper.id", exercise.getPaperId());
				requestData.put("subject.id", exercise.getSubjectId());
				List<HashMap<String, Object>> customerAnswers = new ArrayList<HashMap<String, Object>>();
				for (Item item : exercise.getItems()) {
					if (item.getModelType() == 4) {
						for (Item child : item.getChildren()) {
							customerAnswers.add(handleCustomerAnswer(child));
						}
					} else {
						customerAnswers.add(handleCustomerAnswer(item));
					}
				}
				requestData.put("answers", JSONUtil.objectToJson(customerAnswers));
				Request request = new Request(R.string.UserTestSubmitAutoPaper, requestData, mActivity, actionResult);
				NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
					@Override
					public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
						Intent intent = new Intent();
						App.getInstance().setTestAbilityChange(paramObject.getRecords());
						intent.setClass(mActivity, ResultActivity.class);
						mActivity.startActivity(intent);
						ExerciseActivity.instance.finish();
						mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
					}
				});
			}
		});
	}

	// 判断用户答题是否正确
	// 默认值为正确的,如果错误,则改变值为false
	private void judge() {
		int totalNum = exercise.getTotalNum();
		int rightNum = totalNum;
		for (int i = 0; i < totalNum; i++) {
			Item item = exercise.getItems().get(i);
			Set<CustomerAnswer> customerAnswer = item.getCustomerAnswer();
			List<Result> resultList = item.getData().getResults(); // 正确答案
			String[] rightAnswer = resultList.get(0).getInx().split(",");
			if (customerAnswer.size() != rightAnswer.length) {
				exercise.getItems().get(i).setRight(false);
				rightNum--;
				continue;
			}
			exercise.getItems().get(i).setRight(true);
			for (CustomerAnswer answer : customerAnswer) {
				if (!contains(rightAnswer, answer.getInx())) {
					exercise.getItems().get(i).setRight(false);
					break;
				}
			}
		}
		exercise.setRightNum(rightNum);
	}

	private void judge2() {
		int totalNum = exercise.getTotalNum();
		int rightNum = totalNum;
		for (Item item : exercise.getItems()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					Set<CustomerAnswer> customerAnswer = child.getCustomerAnswer();
					List<Result> resultList = child.getData().getResults(); // 正确答案
					String[] rightAnswer = resultList.get(0).getInx().split(",");
					if (customerAnswer.size() != rightAnswer.length) {
						child.setRight(false);
						rightNum--;
						continue;
					}
					child.setRight(true);
					for (CustomerAnswer answer : customerAnswer) {
						if (!contains(rightAnswer, answer.getInx())) {
							child.setRight(false);
							rightNum--;
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
					continue;
				}
				item.setRight(true);
				for (CustomerAnswer answer : customerAnswer) {
					if (!contains(rightAnswer, answer.getInx())) {
						item.setRight(false);
						rightNum--;
						break;
					}
				}
			}
		}
		exercise.setRightNum(rightNum);
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
		// 填充各控件的数据
		exercise = App.getInstance().getExercise();
		userInfo = App.getInstance().getUserInfo();
		gridView.setAdapter(new HandPaperAdapter(mActivity, exercise, viewPager));
	}

	private boolean contains(String[] rightAnswer, String customerAnswer) {
		boolean mark = false;
		for (String result : rightAnswer) {
			if (result.equals(customerAnswer)) {
				mark = true;
				break;
			}
		}
		return mark;
	}

	private HashMap<String, Object> handleCustomerAnswer(Item item) {
		HashMap<String, Object> customerAnswer = new HashMap<String, Object>();
		customerAnswer.put("ssoUserTestItem.id", item.getId());
		StringBuffer customStr = new StringBuffer();
		for (CustomerAnswer custom : item.getCustomerAnswer()) {
			customStr.append(custom.getInx() + ",");
		}
		CharSequence cuStr = "";
		int isAnswered = 0; // 0该题没作答 1该题已作答
		if (customStr.length() > 0) {
			cuStr = customStr.subSequence(0, customStr.length() - 1);
			customerAnswer.put("reference", cuStr); // 用户答案
			isAnswered = 1;
		}
		customerAnswer.put("completeType", (item.isRight()) ? 1 : 0); // 是否答对
		customerAnswer.put("isAnswered", isAnswered);
		return customerAnswer;
	}
}
