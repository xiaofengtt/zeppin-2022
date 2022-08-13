package com.zixueku.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.adapter.PaperListAdapter;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.PaperInfo;
import com.zixueku.entity.Request;
import com.zixueku.listerner.FinishActivityListener;
import com.zixueku.util.Constant;

/**
 * 类说明 试卷列表
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-6 下午2:03:19
 */
public class PaperListActivity extends AbstractAsyncActivity implements OnClickListener {
	private ListView paperListView;
	private ActionResult<ArrayList<PaperInfo>> actionResult;
	private short type;
	private int subjectId;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_paper_list);
	}

	@Override
	protected void findViewById() {
		type = getIntent().getShortExtra("type", (short) -1);
		subjectId = getIntent().getIntExtra("subjectId", -1);
		findViewById(R.id.action_bar_left_go_back_button).setOnClickListener(new FinishActivityListener(this));
		((TextView) findViewById(R.id.action_bar_center_text)).setText(Constant.PAPER_TYPR.get(type));
		paperListView = (ListView) findViewById(R.id.paper_list);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		actionResult = new ActionResult<ArrayList<PaperInfo>>() {
		};
		// subject.id,type(0真题 1模拟试题 3预测题)
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("subject.id", subjectId);
		requestData.put("type", type);
		Request request = new Request(R.string.UserTestPaperList, requestData, mContext, actionResult);

		netDataConnation(request, new ServerDataCallback<ActionResult<ArrayList<PaperInfo>>>() {
			@Override
			public void processData(ActionResult<ArrayList<PaperInfo>> paramObject, boolean paramBoolean) {
				paperListView.setAdapter(new PaperListAdapter(PaperListActivity.this, paramObject.getRecords()));
			}
		});

	}

	@Override
	public void onClick(View v) {

	}

}
