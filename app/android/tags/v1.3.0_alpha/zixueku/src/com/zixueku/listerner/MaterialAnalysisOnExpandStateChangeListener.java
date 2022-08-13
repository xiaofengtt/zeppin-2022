package com.zixueku.listerner;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView.OnExpandStateChangeListener;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.util.App;
import com.zixueku.util.NetThreadUtil;

/**
 * 类说明 答题中 当答案解析的按钮展开时触发的事件
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月27日 下午3:56:36
 */
public class MaterialAnalysisOnExpandStateChangeListener implements OnExpandStateChangeListener {
	private Context context;
	private Material material;
	private int position;
	private int subPosition;
	private boolean isCombination = false; // 是否为组合题

	public MaterialAnalysisOnExpandStateChangeListener(Context context, Material material, int... position) {
		super();
		this.context = context;
		this.material = material;
		this.position = position[0];
		if (position.length == 2) {
			this.subPosition = position[1];
			this.isCombination = true;
		}
	}

	@Override
	public void onExpandStateStart(TextView textView, boolean isExpanded) {
		Item item;
		if (isCombination) {
			item = material.getMaterialItems().get(position).getChildren().get(subPosition);
		} else {
			item = material.getMaterialItems().get(position);
		}

		int completeType = item.getCompleteType();

		if (completeType == -1) {
			// version,user.id,item.id,flag
			Map<String, Object> requestData = new HashMap<String, Object>();
			requestData.put("item.id", item.getItemId());
			requestData.put("flag", 0);
			ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>() {
			};
			Request request = new Request(R.string.UserTestMasterItem, requestData, context, actionResult);
			NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
				@Override
				public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
					App.getInstance().setTestAbilityChange(paramObject.getRecords());
				}
			});

		}

	}

	@Override
	public void onExpandStateChanged(TextView textView, boolean isExpanded) {
		if (isCombination) {
			material.getMaterialItems().get(position).getChildren().get(subPosition).setAnalysisIsRead(true);
			int completeType = material.getMaterialItems().get(position).getChildren().get(subPosition).getCompleteType();
			if (completeType != 1) {
				// 如果该题未标注为已掌握,则标注该题为已读
				material.getMaterialItems().get(position).getChildren().get(subPosition).setCompleteType(0);
			}
		} else {
			material.getMaterialItems().get(position).setAnalysisIsRead(true);
			int completeType = material.getMaterialItems().get(position).getCompleteType();
			if (completeType != 1) {
				// 如果该题未标注为已掌握,则标注该题为已读
				material.getMaterialItems().get(position).setCompleteType(0);
			}
		}

	}

}
