package com.zixueku.fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.shamanland.fab.FloatingActionButton;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.listerner.MaterialAnalysisOnExpandStateChangeListener;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.widget.CustomWebView;

/**
 * 问答题
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperMaterialCardFragment extends BaseFragment implements OnClickListener {
	private static final String ARG_POSITION = "position";
	private static final String ARG_SUB_POSITION = "subPosition";
	private CustomWebView content;
	private TextView typeName;
	private TextView sourceName;
	private int position;
	private int subPosition;
	private Item item;
	private Material material;
	private ExpandableTextView analysis;
	private FloatingActionButton floatingButton;
	private TextView currentNumTextView;
	private TextView totalNumTextView;

	Handler handler = new Handler();

	public static SuperMaterialCardFragment newInstance(int... position) {
		SuperMaterialCardFragment f = new SuperMaterialCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position[0]);
		if (position.length == 2) {
			b.putInt(ARG_SUB_POSITION, position[1]);
		}
		f.setArguments(b);
		return f;
	}

	public SuperMaterialCardFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.subPosition = getArguments().getInt(ARG_SUB_POSITION, -1);
		this.material = App.getInstance().getMaterial();
		if (subPosition == -1) {
			this.item = material.getMaterialItems().get(position);
		} else {
			this.item = material.getMaterialItems().get(position).getChildren().get(subPosition);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.material, container, false);
		content = (CustomWebView) contactsLayout.findViewById(R.id.material_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentNumTextView = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNumTextView = (TextView) contactsLayout.findViewById(R.id.total_num);
		analysis = (ExpandableTextView) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);
		floatingButton = (FloatingActionButton) contactsLayout.findViewById(R.id.fab);
		BusinessCommonMethod.setWetbViewContent(mActivity, content, item.getData().getStem());

		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);

		return contactsLayout;
	}

	private void setFloatingButtonStatus() {
		if (item.getCompleteType() == 1) {
			floatingButton.setImageResource(R.drawable.master_icon);
		} else {
			floatingButton.setImageResource(R.drawable.un_master_icon);
		}
	}

	@Override
	public void onClick(View v) {
		int flag = item.getCompleteType();
		if (flag == 1) {
			flag = 0;
		} else {
			flag = 1;
		}
		final int f = flag;
		// version,user.id,item.id,flag
		Map<String, Object> requestData = new HashMap<String, Object>();
		requestData.put("item.id", item.getItemId());
		// requestData.put("user.id", user.getUserId());
		requestData.put("flag", flag);
		ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>() {
		};
		Request request = new Request(R.string.UserTestMasterItem, requestData, mActivity, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
			@Override
			public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
				App.getInstance().setTestAbilityChange(paramObject.getRecords());
				item.setCompleteType(f);
				setFloatingButtonStatus();
				if (f == 1) {
					CommonTools.showShortToastDefaultStyle(mActivity, "成功设置为已掌握");
				} else {
					CommonTools.showShortToastDefaultStyle(mActivity, "已取消掌握该题");
				}
			}
		});
		setFloatingButtonStatus();
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			BusinessCommonMethod.setTextHtmlContent(mActivity, analysis, item.getAnalysis());
			typeName.setText(item.getItemTypeName());
			sourceName.setText(material.getMaterialItems().get(position).getSource());

			currentNumTextView.setText(String.valueOf(item.getIndex() + 1));
			totalNumTextView.setText(String.valueOf(material.getTotalNum()));
			floatingButton.setOnClickListener(SuperMaterialCardFragment.this);
			setFloatingButtonStatus();
			if (subPosition != -1) {
				analysis.setOnExpandStateChangeListener(new MaterialAnalysisOnExpandStateChangeListener(mActivity, material, position, subPosition));
			} else {
				analysis.setOnExpandStateChangeListener(new MaterialAnalysisOnExpandStateChangeListener(mActivity, material, position));
			}
		}
	};
}