package com.zixueku.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.MaterialActivity;
import com.zixueku.adapter.SubjectItemTypeAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.LastRightItemCount;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.entity.SubjectItemType;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.CustomListView;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月16日 下午2:00:35
 */
public class SubjectItemTypeFragment extends BaseFragment {

	private ActionResult<ArrayList<SubjectItemType>> actionResult;
	private User userInfo;
	private PrepareExam prepareExam;
	private ScrollView contentScrollView;
	private CustomListView listView;
	private ArrayList<SubjectItemType> subjectItemList;
	private SubjectItemTypeAdapter subjectItemTypeAdapter;
	private TextView noticNoType;
	
	public SubjectItemTypeFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.subject_detail_special_layout, container, false);
		listView = (CustomListView) view.findViewById(R.id.item_type_list_view);
		contentScrollView = (ScrollView) mActivity.findViewById(R.id.content_scroll);
		noticNoType = (TextView) view.findViewById(R.id.notic_no_type);
		prepareExam = App.getInstance().getPrepareExam();
		userInfo = App.getInstance().getUserInfo();
		processLogic();
		return view;
	}

	// <!--
	// http://localhost:8080/SelfCool/userSubjectItemTypeList?user.id=1&subject.id=28&isStandard=0
	// -->
	protected void processLogic() {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		actionResult = new ActionResult<ArrayList<SubjectItemType>>() {
		};
		requestData.put("user.id", userInfo.getUserId());
		requestData.put("subject.id", prepareExam.getSubjectid());
		requestData.put("isStandard", 0);
		Request request = new Request(R.string.UserSubjectItemTypeList, requestData, mActivity, actionResult);
		NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<ArrayList<SubjectItemType>>>() {
			@Override
			public void processData(ActionResult<ArrayList<SubjectItemType>> paramObject, boolean paramBoolean) {
				AbDialogUtil.removeDialog(mActivity);
				subjectItemList = paramObject.getRecords();
				if (subjectItemList.size() == 0) {
					noticNoType.setVisibility(View.VISIBLE);
					return;
				}
				subjectItemTypeAdapter = new SubjectItemTypeAdapter(mActivity, subjectItemList);
				listView.setAdapter(subjectItemTypeAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						App.getInstance().setTestAbilityChange(null);// 设置testAbilityChange为空
						// 获取被点击的item所对应的数据
						SubjectItemType subjectItemType = (SubjectItemType) parent.getItemAtPosition(position);
						// 下面是你的其他事务逻辑
						Intent intent = new Intent(mActivity, MaterialActivity.class);
						intent.putExtra("subjectId", subjectItemType.getSubjectId());
						intent.putExtra("itemTypeId", subjectItemType.getItemTypeId());
						intent.putExtra("position", position);
						intent.putExtra("subjectName", prepareExam.getSubjectname());
						mActivity.startActivityForResult(intent, 2);
						mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
					}
				});
				contentScrollView.scrollTo(0, 0);
			}
		});
	}

	public void updateListView() {
		LastRightItemCount[] lastRightItemCounts = App.getInstance().getTestAbilityChange().getLastRightItemCount();
		for (LastRightItemCount item : lastRightItemCounts) {
			for (SubjectItemType type : subjectItemList) {
				if (item.getId() == type.getItemTypeId()) {
					type.setLastRightItemCount(item.getCount());
					break;
				}
			}
		}
		subjectItemTypeAdapter.notifyDataSetChanged();
	}
}
