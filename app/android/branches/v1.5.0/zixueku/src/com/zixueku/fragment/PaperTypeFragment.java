package com.zixueku.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.PaperListActivity;
import com.zixueku.adapter.PaperTypeItemAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.PaperType;
import com.zixueku.entity.PrepareExam;
import com.zixueku.entity.Request;
import com.zixueku.util.App;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.CustomListView;

/**
 * 类说明
 * 模拟考试
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月16日 下午2:00:35
 */
public class PaperTypeFragment extends BaseFragment {

	private ActionResult<ArrayList<PaperType>> actionResult;
	private PrepareExam prepareExam;
	private ScrollView contentScrollView;
	private CustomListView listView;
	private ArrayList<PaperType> itemList;
	private PaperTypeItemAdapter adapter;
	private TextView noticNoType;
	
	public PaperTypeFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.paper_type_layout, container, false);
		listView = (CustomListView) view.findViewById(R.id.paper_type_item_list);
		View v = new View(mActivity);
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
		listView.addFooterView(v);
		contentScrollView = (ScrollView) mActivity.findViewById(R.id.content_scroll);
		noticNoType = (TextView) view.findViewById(R.id.notic_no_type);
		prepareExam = App.getInstance().getPrepareExam();
		processLogic();
		return view;
	}

	// <!--
	// http://localhost:8080/SelfCool/userSubjectItemTypeList?user.id=1&subject.id=28&isStandard=0
	// -->
	protected void processLogic() {
		HashMap<String, Object> requestData = new HashMap<String, Object>();
		actionResult = new ActionResult<ArrayList<PaperType>>() {
		};
		requestData.put("subject.id", prepareExam.getSubjectid());
		Request request = new Request(R.string.UserTestSelectPaperType, requestData, mActivity, actionResult);
		NetThreadUtil.sendDataToServerWithProgressDialog(request, new ServerDataCallback<ActionResult<ArrayList<PaperType>>>() {
			@Override
			public void processData(ActionResult<ArrayList<PaperType>> paramObject, boolean paramBoolean) {
				AbDialogUtil.removeDialog(mActivity);
				itemList = paramObject.getRecords();
				if (itemList.size() == 0) {
					noticNoType.setVisibility(View.VISIBLE);
					return;
				}
				adapter = new PaperTypeItemAdapter(mActivity, itemList);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// 获取被点击的item所对应的数据
						PaperType paperType = (PaperType) parent.getItemAtPosition(position);
						// 下面是你的其他事务逻辑
						Intent intent = new Intent(mActivity, PaperListActivity.class);
						intent.putExtra("type", paperType.type);
						intent.putExtra("subjectId", prepareExam.getSubjectid());
						mActivity.startActivity(intent);
						mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
					}
				});
				contentScrollView.scrollTo(0, 0);
			}
		});
	}

}
