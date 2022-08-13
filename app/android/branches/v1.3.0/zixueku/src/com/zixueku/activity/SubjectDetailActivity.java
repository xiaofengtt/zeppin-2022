package com.zixueku.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
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
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:31:29
 */
public class SubjectDetailActivity extends AbstractAsyncActivity implements OnClickListener {
	private ImageButton backButton;
	private TextView actionBarCenterText;
	private ImageButton wrongBookButton;
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

	// 定义底部导航栏中的ImageView与TextView
	private ImageView knowledgeTestImage;
	private ImageView specialTestImage;
	private TextView knowledgeTestText;
	private TextView specialTestText;

	private LinearLayout knowledgeButtonLayout;
	private LinearLayout subjectItemTypeButtonLayout;

	private ImageButton coolTestImageButton;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_subject_detail);
	}

	@Override
	protected void findViewById() {
		backButton = (ImageButton) findViewById(R.id.action_bar_left_go_back_button);
		actionBarCenterText = (TextView) findViewById(R.id.action_bar_center_text);
		wrongBookButton = (ImageButton) findViewById(R.id.wrong_book_image_button);
		circleView = (HoloCircularProgressBar) findViewById(R.id.knowledge_circle_imageview);
		correctRate = (TextView) findViewById(R.id.correct_rate);
		brushItemCount = (TextView) findViewById(R.id.brush_item_count);
		nextTestdayCount = (TextView) findViewById(R.id.next_testday_count);
		progressTextView = (TextView) findViewById(R.id.progress);
		headLinearLayout = (LinearLayout) findViewById(R.id.head_background);
		rankText = (TextView) findViewById(R.id.rank_text);
		prepareExam = ((App) getApplication()).getPrepareExam();

		knowledgeTestImage = (ImageView) findViewById(R.id.knowledge_test_image);
		specialTestImage = (ImageView) findViewById(R.id.special_test_image);
		knowledgeTestText = (TextView) findViewById(R.id.knowledge_test_text);
		specialTestText = (TextView) findViewById(R.id.special_test_text);

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

		wrongBookButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 下面是你的其他事务逻辑
				Intent intent = new Intent(SubjectDetailActivity.this, WrongBookActivity.class);
				intent.putExtra("subjectId", prepareExam.getSubjectid());
				SubjectDetailActivity.this.startActivityForResult(intent, 1);
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

		prepareExam.setNextTestdayCount(testAbilityChange.getNextTestdayCount());
		prepareExam.setProgress(testAbilityChange.getProgress());
		prepareExam.setRankingRate(testAbilityChange.getRankingRate());

		if ((requestCode == 1 || requestCode == 0) && knowledgeFragment != null) {
			knowledgeFragment.updateKnowledge();
			prepareExam.setBrushItemCount(testAbilityChange.getBrushItemCount());
			prepareExam.setCorrectRate(testAbilityChange.getCorrectRate());
		} else if (requestCode == 2 && itemTypeFragment != null) {
			itemTypeFragment.updateListView();
		}
		initProgress();

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
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			knowledgeTestImage.setImageResource(R.drawable.knowledge_test_icon_2);
			knowledgeTestText.setTextColor(getResources().getColor(R.color.green));
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
			specialTestImage.setImageResource(R.drawable.special_test_icon_2);
			specialTestText.setTextColor(getResources().getColor(R.color.green));

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

	// 定义一个重置所有选项的方法
	public void clearChioce() {
		knowledgeTestImage.setImageResource(R.drawable.knowledge_test_icon);
		knowledgeTestText.setTextColor(getResources().getColor(R.color.dimgrey));
		specialTestImage.setImageResource(R.drawable.special_test_icon);
		specialTestText.setTextColor(getResources().getColor(R.color.dimgrey));
	}

}
