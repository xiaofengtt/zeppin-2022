package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.entity.Request;
import com.zixueku.entity.Result;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.JSONUtil;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-23 下午7:56:59
 */
public class HandPaperActivity extends AbstractAsyncActivity {
	private Exercise exercise;
	private GridView gridView;
	private TextView actionBarCenterText;
	private Button submitButton;
	private User userInfo;
	private ImageButton goBack;

	private ActionResult<TestAbilityChange> actionResult;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_hand_paper);
	}

	@Override
	protected void findViewById() {
		exercise =((App) getApplication()).getExercise();
		gridView = (GridView) findViewById(R.id.activity_hand_paper_gridView);
		actionBarCenterText = (TextView) findViewById(R.id.action_bar_center_text);
		submitButton = (Button) findViewById(R.id.hand_paper_submit_button);
		userInfo = ((App) getApplication()).getUserInfo();
		goBack = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		actionResult = new ActionResult<TestAbilityChange>() {
		};
	}

	@Override
	protected void setListener() {
		/*
		 * gridView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { Intent intent = getIntent();
		 * intent.putExtra("position", position);
		 * HandPaperActivity.this.setResult(0, intent);
		 * HandPaperActivity.this.finish();
		 * HandPaperActivity.this.overridePendingTransition(R.anim.left_in,
		 * R.anim.right_out); } });
		 */

		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				judge();
				Map<String, Object> requestData = new HashMap<String, Object>();
				requestData.put("user.id", userInfo.getUserId());
				requestData.put("ssoUserTest.id", exercise.getSsoUserTestId());
				requestData.put("paper.id", exercise.getPaperId());
				requestData.put("subject.id", exercise.getSubjectId());

				List<HashMap<String, Object>> customerAnswers = new ArrayList<HashMap<String, Object>>();

				for (Item item : exercise.getItems()) {
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
					customerAnswers.add(customerAnswer);
				}
				requestData.put("answers", JSONUtil.objectToJson(customerAnswers));
				Request request = new Request(R.string.UserTestSubmitAutoPaper, requestData, HandPaperActivity.this, actionResult);
				getDataFromServer(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
					@Override
					public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
						Intent intent = new Intent();
						((App) getApplication()).setTestAbilityChange(paramObject.getRecords());
						intent.setClass(HandPaperActivity.this, ResultActivity.class);
						HandPaperActivity.this.startActivity(intent);
						HandPaperActivity.this.finish();
						ExerciseActivity.instance.finish();
						HandPaperActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
					}
				});
			}
		});

		goBack.setOnClickListener(new FinishActivityListener(HandPaperActivity.this));

	}

	@Override
	protected void processLogic() {
		//gridView.setAdapter(new HandPaperAdapter(HandPaperActivity.this, exercise));
		actionBarCenterText.setText("答题卡");
	}

	@Override
	protected void onTouchEventRight() {
		CommonTools.finishActivity(HandPaperActivity.this);
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

			if (customerAnswer.size() != resultList.size()) {
				exercise.getItems().get(i).setRight(false);
				rightNum--;
				continue;
			}

			int mark = 0;
			for (Result result : resultList) {
				mark = 0;
				for (CustomerAnswer cust : customerAnswer) {
					if (!(cust.getInx() == result.getInx())) {
						exercise.getItems().get(i).setRight(false);
						mark = 1;
						rightNum--;
						break;
					}
				}
				if (mark == 1) {
					break;
				}
			}
		}
		exercise.setRightNum(rightNum);
	}

}
