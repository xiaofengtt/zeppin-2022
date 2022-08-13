package com.zixueku.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.ResultGridViewAdapter;
import com.zixueku.adapter.ResultListViewAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.listerner.IntentActivityListener;
import com.zixueku.util.AnalysisEventAgent;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.DialogUtil;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-26 下午5:41:31
 */
public class ResultActivity extends AbstractAsyncActivity {
	private Exercise exercise;
	private TestAbilityChange testAbilityChange;
	private Button analysis;
	private Button nextTest;
	private GridView resultGridView;
	private ListView knowledgeListView;
	private ImageButton goBackButton;
	private TextView title;
	private TextView rightNum;
	private TextView totalNum;
	private TextView progress;
	private ImageView progressChangeIcon;
	private PrepareExam prepareExam;
	private int totalItems;// 题目总数
	private int rightItems;// 答对数量
	
	private Handler handler = new Handler();
	private Runnable run = new Runnable() {
		@Override
		public void run() {
			testCountDialog();
		}
	};

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_result);
	}

	@Override
	protected void findViewById() {
		exercise = ((App) (getApplication())).getExercise();
		testAbilityChange = ((App) (getApplication())).getTestAbilityChange();
		prepareExam = ((App) (getApplication())).getPrepareExam();
		analysis = (Button) findViewById(R.id.analysis);
		nextTest = (Button) findViewById(R.id.next_test);
		resultGridView = (GridView) findViewById(R.id.activity_result_gridView);
		goBackButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		title = (TextView) findViewById(R.id.action_bar_center_text);
		rightNum = (TextView) findViewById(R.id.right_num);
		totalNum = (TextView) findViewById(R.id.total_num);
		knowledgeListView = (ListView) findViewById(R.id.knowledge_list);
		progress = (TextView) findViewById(R.id.progress);
		progressChangeIcon = (ImageView) findViewById(R.id.progress_change_icon);

	}

	@Override
	protected void setListener() {
		analysis.setOnClickListener(analysisOnClickListener(0));
		goBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		nextTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int knowledgeId = exercise.getKnowledgeId();
				int subjectId = exercise.getSubjectId();
				AnalysisEventAgent.onEvent(mContext, AnalysisEventAgent.AnswerAgain);// 埋点
				// 下面是你的其他事务逻辑
				Intent intent = new Intent(ResultActivity.this, ExerciseActivity.class);
				intent.putExtra("knowledgeId", knowledgeId);
				intent.putExtra("subjectId", subjectId);
				ResultActivity.this.startActivity(intent);
				ResultActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
				ResultActivity.this.finish();
			}
		});
	}

	@Override
	protected void processLogic() {
		int previousProgress = (int) prepareExam.getProgress();
		int newProgress = (int) testAbilityChange.getProgress();
		totalItems = exercise.getTotalNum();
		rightItems = exercise.getRightNum();
		title.setText("答题报告");
		totalNum.setText(String.valueOf(totalItems));
		rightNum.setText(String.valueOf(rightItems));
		resultGridView.setAdapter(new ResultGridViewAdapter(ResultActivity.this, exercise));

		knowledgeListView.setAdapter(new ResultListViewAdapter(ResultActivity.this, testAbilityChange));
		progress.setText((int) testAbilityChange.getProgress() + "%");

		if (previousProgress > newProgress) {
			progressChangeIcon.setImageResource(R.drawable.down);
		} else if (previousProgress < newProgress) {
			progressChangeIcon.setImageResource(R.drawable.up);
		} else {
			progressChangeIcon.setImageResource(R.drawable.no_change);
		}
		BusinessCommonMethod.achievementDialog(mContext, previousProgress, newProgress);
		handler.postDelayed(run, 150);
	}

	protected IntentActivityListener analysisOnClickListener(int position) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("position", position);
		return new IntentActivityListener(ResultActivity.this, AnalysisActivity.class, param);
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	private void goBack() {
		this.finish();
		this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	public void testCountDialog() {
		int imageResourceId = 0;
		int soundResourceId = 0;
		if (totalItems == rightItems) {
			imageResourceId = R.drawable.achievement_all_right;
			soundResourceId = R.raw.result_right;
		} else if (rightItems == 0) {
			imageResourceId = R.drawable.achievement_all_error;
			soundResourceId = R.raw.result_wrong;
		}
		if (imageResourceId != 0) {
			DialogUtil.achievementDialog(mContext, imageResourceId);
			BusinessCommonMethod.displaySound(mContext, soundResourceId);
		}
	}
}
