package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.KnowledgeAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Knowledge;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.listerner.KnowledgeItemClickListener;
import com.zixueku.util.App;

/**
 * 类说明 每个知识点的知识树，
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:31:29
 */
public class KnowledgeActivity extends AbstractAsyncActivity {
	/** 树中的元素(界面上显示的元素) */
	private List<Knowledge> displayedKnowledgeData;
	private ListView knowledgeListView;
	private ImageButton backButton;
	private TextView actionBarCenterText;
	private ActionResult<ArrayList<Knowledge>> actionResult;

	private HoloCircularProgressBar circleView;

	private TextView correctRate;
	private TextView brushItemCount;
	private TextView nextTestdayCount;
	private TextView progressTextView;

	private LinearLayout knowledgeLinearLayout;

	private TextView rankText;
	private KnowledgeAdapter knowledgeAdapter;

	private TestAbilityChange testAbilityChange;

	private ScrollView knowledgeScroll;

	private PrepareExam prepareExam;
	private User userInfo;

	private int position;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_knowledge);
	}

	@Override
	protected void findViewById() {
		knowledgeListView = (ListView) findViewById(R.id.knowledgeListView);
		backButton = (ImageButton) findViewById(R.id.knowledge_action_bar_left_go_back_button);
		actionBarCenterText = (TextView) findViewById(R.id.knowledge_action_bar_center_text);
		circleView = (HoloCircularProgressBar) findViewById(R.id.knowledge_circle_imageview);
		correctRate = (TextView) findViewById(R.id.correct_rate);
		brushItemCount = (TextView) findViewById(R.id.brush_item_count);
		nextTestdayCount = (TextView) findViewById(R.id.next_testday_count);
		progressTextView = (TextView) findViewById(R.id.progress);
		knowledgeLinearLayout = (LinearLayout) findViewById(R.id.head_background);
		rankText = (TextView) findViewById(R.id.rank_text);

		knowledgeScroll = (ScrollView) findViewById(R.id.knowledge_scroll);

		prepareExam = ((App) getApplication()).getPrepareExam();
		// 获取上一个界面传进来的数据
		position = getIntent().getIntExtra("position", -1);
		actionResult = new ActionResult<ArrayList<Knowledge>>() {
		};
		userInfo = ((App) getApplication()).getUserInfo();
	}

	@Override
	protected void setListener() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});
	}

	@Override
	protected void processLogic() {
		actionBarCenterText.setText(prepareExam.getSubjectname());
		initProgress();
		if (prepareExam.isKnowledgeIsLoad()) {
			displayedKnowledgeData = prepareExam.getKnowledgeData();
			initKnowledgeTree();
		} else {
			HashMap<String, Object> requestData = new HashMap<String, Object>();
			requestData.put("user.id", userInfo.getUserId());
			requestData.put("subject.id", prepareExam.getSubjectid());
			Request request = new Request(R.string.KnowledgeSearchAll, requestData, this, actionResult);
			getDataFromServer(request, new ServerDataCallback<ActionResult<ArrayList<Knowledge>>>() {
				@Override
				public void processData(ActionResult<ArrayList<Knowledge>> paramObject, boolean paramBoolean) {
					prepareExam.setKnowledgeData(paramObject.getRecords());
					displayedKnowledgeData = paramObject.getRecords();
					prepareExam.setKnowledgeIsLoad(true);
					initKnowledgeTree();
				}
			});
		}
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
		knowledgeLinearLayout.setBackgroundResource(resid);
		correctRate.setText((int) prepareExam.getCorrectRate() + "%");
		brushItemCount.setText(prepareExam.getBrushItemCount() + "");
		nextTestdayCount.setText(prepareExam.getNextTestdayCount() + "");
		circleView.setProgress((float) (progressRate / 100));
		progressTextView.setText((int) progressRate + "");

	}

	private void initKnowledgeTree() {
		knowledgeAdapter = new KnowledgeAdapter(KnowledgeActivity.this, displayedKnowledgeData, prepareExam);
		KnowledgeItemClickListener knowledgeItemClickListener = new KnowledgeItemClickListener(knowledgeAdapter);
		knowledgeListView.setAdapter(knowledgeAdapter);
		knowledgeListView.setOnItemClickListener(knowledgeItemClickListener);
		knowledgeScroll.smoothScrollTo(0, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
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

		// 广度优先搜索遍历
		// 首先将根节点加入节点访问队列
		List<Knowledge> nodeList = new ArrayList<Knowledge>();
		nodeList.addAll(testAbilityChange.getChangeKnowledges());
		// 如果节点访问队列中还有节点，则取出一个进行操作
		while (nodeList.size() > 0) {
			Knowledge currentNode = nodeList.get(0);
			// 下面针对这个节点进行操作，比如说导出该节点的数据，这里用简单的打印id代替
			// Log.e("konwldegeId", currentNode.getId() + "");
			updateKnowledgeNode(currentNode);
			// System.out.println(currentNode.getId());
			// 删除已经访问的头节点
			nodeList.remove(0);
			// 如果该节点有子节点，将该节点的所有子节点加入到访问队列的末尾
			List<Knowledge> currentChildren = currentNode.getData();
			if (currentChildren != null && currentChildren.size() > 0) {
				Iterator<Knowledge> childrenIt = currentChildren.iterator();
				while (childrenIt.hasNext()) {
					Knowledge node = childrenIt.next();
					nodeList.add(node);
				}// while in
			}// if
		}// while out

		knowledgeAdapter.notifyDataSetChanged();
	}

	private void updateKnowledgeNode(Knowledge newKnowledge) {

		int targetId = newKnowledge.getId();
		// 广度优先搜索遍历
		// 首先将根节点加入节点访问队列
		List<Knowledge> nodeList = new ArrayList<Knowledge>();
		nodeList.addAll(prepareExam.getKnowledgeData());
		// 如果节点访问队列中还有节点，则取出一个进行操作
		while (nodeList.size() > 0) {
			Knowledge currentNode = nodeList.get(0);
			// 下面针对这个节点进行操作，比如说导出该节点的数据，这里用简单的打印id代替
			if (targetId == currentNode.getId()) {
				currentNode.setChangeFlag(true);
				currentNode.setRightChangeCount(newKnowledge.getRightCount());
				currentNode.setRightCount(newKnowledge.getRightCount());
				currentNode.setTestTotalCount(newKnowledge.getTestTotalCount());
			}
			// 删除已经访问的头节点
			nodeList.remove(0);
			// 如果该节点有子节点，将该节点的所有子节点加入到访问队列的末尾
			List<Knowledge> currentChildren = currentNode.getData();
			if (currentChildren != null && currentChildren.size() > 0) {
				Iterator<Knowledge> childrenIt = currentChildren.iterator();
				while (childrenIt.hasNext()) {
					Knowledge node = childrenIt.next();
					nodeList.add(node);
				}// while in
			}// if
		}// while out
	}

}
