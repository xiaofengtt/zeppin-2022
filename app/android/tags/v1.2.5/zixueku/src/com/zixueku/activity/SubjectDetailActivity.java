package com.zixueku.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;
import com.zixueku.R;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.fragment.KnowledgeFragment;
import com.zixueku.fragment.SubjectItemTypeFragment;
import com.zixueku.util.App;

/**
 * 类说明 每个知识点的知识树，
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:31:29
 */
public class SubjectDetailActivity extends AbstractAsyncActivity implements OnClickListener {

	private ImageButton backButton;
	private TextView actionBarCenterText;
	private HoloCircularProgressBar circleView;
	private TextView correctRate;
	private TextView brushItemCount;
	private TextView nextTestdayCount;
	private TextView progressTextView;
	private LinearLayout headLinearLayout;
	private TextView rankText;

	private TestAbilityChange testAbilityChange;
	private PrepareExam prepareExam;
	private int position;

	// 定义FragmentManager对象
	private FragmentManager fManager;
	// 定义Fragment的对象
	private KnowledgeFragment knowledgeFragment;
	private SubjectItemTypeFragment itemTypeFragment;

	private LinearLayout knowledgeButtonLayout;
	private LinearLayout subjectItemTypeButtonLayout;

	private ImageButton coolTestImageButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_subject_detail);
	}

	@Override
	protected void findViewById() {
		backButton = (ImageButton) findViewById(R.id.knowledge_action_bar_left_go_back_button);
		actionBarCenterText = (TextView) findViewById(R.id.knowledge_action_bar_center_text);
		circleView = (HoloCircularProgressBar) findViewById(R.id.knowledge_circle_imageview);
		correctRate = (TextView) findViewById(R.id.correct_rate);
		brushItemCount = (TextView) findViewById(R.id.brush_item_count);
		nextTestdayCount = (TextView) findViewById(R.id.next_testday_count);
		progressTextView = (TextView) findViewById(R.id.progress);
		headLinearLayout = (LinearLayout) findViewById(R.id.head_background);
		rankText = (TextView) findViewById(R.id.rank_text);
		prepareExam = ((App) getApplication()).getPrepareExam();
		// 获取上一个界面传进来的数据
		position = getIntent().getIntExtra("position", -1);
		fManager = getSupportFragmentManager();

		knowledgeButtonLayout = (LinearLayout) findViewById(R.id.knowledge_test_button_layout);
		subjectItemTypeButtonLayout = (LinearLayout) findViewById(R.id.subject_item_type__button_layout);
		coolTestImageButton = (ImageButton) findViewById(R.id.cool_test);
	}

	@Override
	protected void setListener() {
		knowledgeButtonLayout.setOnClickListener(this);
		subjectItemTypeButtonLayout.setOnClickListener(this);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});

		coolTestImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 下面是你的其他事务逻辑
				Intent intent = new Intent(SubjectDetailActivity.this, ExerciseActivity.class);
				((App) getApplication()).setTestAbilityChange(null);// 设置testAbilityChange为空
				SubjectDetailActivity.this.startActivityForResult(intent, 0);
				SubjectDetailActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}

	@Override
	protected void processLogic() {
		actionBarCenterText.setText(prepareExam.getSubjectname());
		initProgress();
		setChioceItem(0);
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	private void goBack() {
		Intent intent = getIntent();
		((App) getApplication()).setPrepareExam(prepareExam);
		intent.putExtra("position", position);
		this.setResult(0, intent);
		this.finish();
		this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	private void initProgress() {
		double progressRate = prepareExam.getProgress();
		int resid = 0;
		if (progressRate < 25) {
			resid = R.drawable.image1;
		} else if (progressRate < 50) {
			resid = R.drawable.image2;
		} else if (progressRate < 75) {
			resid = R.drawable.image3;
		} else {
			resid = R.drawable.image4;
		}
		int rank = (int) (100 - prepareExam.getRankingRate());
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("进度击败了本届" + rank + "%" + "的考生，");
		if (rank < 25) {
			strBuilder.append("加油啊亲！");
		} else if (rank < 50) {
			strBuilder.append("不要停！");
		} else if (rank < 75) {
			strBuilder.append("就要胜利了！");
		} else {
			strBuilder.append("笑到最后！");
		}
		rankText.setText(strBuilder);
		headLinearLayout.setBackgroundResource(resid);
		correctRate.setText((int) prepareExam.getCorrectRate() + "%");
		brushItemCount.setText(prepareExam.getBrushItemCount() + "");
		nextTestdayCount.setText(prepareExam.getNextTestdayCount() + "");
		circleView.setProgress((float) (progressRate / 100));
		progressTextView.setText((int) progressRate + "");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		testAbilityChange = ((App) getApplication()).getTestAbilityChange();
		if (testAbilityChange == null) {
			return;
		}
		prepareExam.setBrushItemCount(testAbilityChange.getBrushItemCount());
		prepareExam.setCorrectRate(testAbilityChange.getCorrectRate());
		prepareExam.setNextTestdayCount(testAbilityChange.getNextTestdayCount());
		prepareExam.setProgress(testAbilityChange.getProgress());
		prepareExam.setRankingRate(testAbilityChange.getRankingRate());
		initProgress();
		if (requestCode == 1 && knowledgeFragment != null) {
			knowledgeFragment.updateKnowledge();
		} else if (requestCode == 2 && itemTypeFragment != null) {
			itemTypeFragment.updateListView();
		}

	}

	// 隐藏所有的Fragment,避免fragment混乱
	private void hideFragments(FragmentTransaction transaction) {
		if (knowledgeFragment != null) {
			transaction.hide(knowledgeFragment);
		}

		if (itemTypeFragment != null) {
			transaction.hide(itemTypeFragment);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.knowledge_test_button_layout:
			setChioceItem(0);
			break;
		case R.id.subject_item_type__button_layout:
			setChioceItem(1);
			break;
		default:
			break;
		}

	}

	// 定义一个选中一个item后的处理
	public void setChioceItem(int index) {
		// 重置选项+隐藏所有Fragment
		FragmentTransaction transaction = fManager.beginTransaction();
		// clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			if (knowledgeFragment == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				knowledgeFragment = new KnowledgeFragment();
				transaction.add(R.id.subject_detail_container, knowledgeFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(knowledgeFragment);
			}
			break;

		case 1:
			if (itemTypeFragment == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				itemTypeFragment = new SubjectItemTypeFragment();
				transaction.add(R.id.subject_detail_container, itemTypeFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(itemTypeFragment);
			}
			break;
		}
		transaction.commit();
	}

}
