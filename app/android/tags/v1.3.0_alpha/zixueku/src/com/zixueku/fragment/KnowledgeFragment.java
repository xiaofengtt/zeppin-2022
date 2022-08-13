package com.zixueku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

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
import com.zixueku.util.NetThreadUtil;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月16日 上午10:59:24
 */
public class KnowledgeFragment extends Fragment {

	/** 树中的元素(界面上显示的元素) */
	private List<Knowledge> displayedKnowledgeData;
	private ListView knowledgeListView;
	private KnowledgeAdapter knowledgeAdapter;
	private ScrollView contentScrollView;
	private PrepareExam prepareExam;
	private TestAbilityChange testAbilityChange;
	private User userInfo;
	private ActionResult<ArrayList<Knowledge>> actionResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.subject_detail_knowledge_layout, container, false);
		knowledgeListView = (ListView) view.findViewById(R.id.knowledgeListView);
		contentScrollView = (ScrollView) getActivity().findViewById(R.id.content_scroll);
		prepareExam = ((App) getActivity().getApplication()).getPrepareExam();
		userInfo = ((App) getActivity().getApplication()).getUserInfo();
		processLogic();
		//scrollToUp(contentScrollView);
		return view;
	}

	private void initKnowledgeTree() {
		knowledgeAdapter = new KnowledgeAdapter(getActivity(), displayedKnowledgeData, prepareExam);
		KnowledgeItemClickListener knowledgeItemClickListener = new KnowledgeItemClickListener(knowledgeAdapter);
		knowledgeListView.setAdapter(knowledgeAdapter);
		knowledgeListView.setOnItemClickListener(knowledgeItemClickListener);
		contentScrollView.smoothScrollTo(0, 0);
	}

	protected void processLogic() {
		if (prepareExam.isKnowledgeIsLoad()) {
			displayedKnowledgeData = prepareExam.getKnowledgeData();
			initKnowledgeTree();
		} else {
			HashMap<String, Object> requestData = new HashMap<String, Object>();
			actionResult = new ActionResult<ArrayList<Knowledge>>() {
			};
			requestData.put("user.id", userInfo.getUserId());
			requestData.put("subject.id", prepareExam.getSubjectid());
			Request request = new Request(R.string.KnowledgeSearchAll, requestData, getActivity(), actionResult);
			NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<ArrayList<Knowledge>>>() {
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

	public void updateKnowledge() {
		testAbilityChange = ((App) getActivity().getApplication()).getTestAbilityChange();
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
	
	public static void scrollToUp(final ScrollView scroll) {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				scroll.fullScroll(ScrollView.FOCUS_UP);
			}
		});
	}

}
