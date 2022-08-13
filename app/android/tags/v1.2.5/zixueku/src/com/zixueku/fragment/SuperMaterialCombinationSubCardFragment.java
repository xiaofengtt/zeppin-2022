package com.zixueku.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.shamanland.fab.FloatingActionButton;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.Request;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.util.URLImageParser;

/**
 * 组合题中的问答题
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperMaterialCombinationSubCardFragment extends Fragment {
	private static final String ARG_PARENT_POSITION = "parent_position";
	private static final String ARG_SUB_POSITION = "sub_position";
	private TextView content;
	private TextView typeName;
	private TextView sourceName;
	private int parentPosition;
	private int subPosition;
	private List<Item> itemList;
	private Material material;
	private TextView currentNumTextView;
	private TextView totalNumTextView;
	private ExpandableTextView analysis;
	private FloatingActionButton floatingButton;
	private User user;

	public static SuperMaterialCombinationSubCardFragment newInstance(int parentPosition, int subPosition) {
		SuperMaterialCombinationSubCardFragment f = new SuperMaterialCombinationSubCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_PARENT_POSITION, parentPosition);
		b.putInt(ARG_SUB_POSITION, subPosition);
		f.setArguments(b);
		return f;
	}

	public SuperMaterialCombinationSubCardFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.parentPosition = getArguments().getInt(ARG_PARENT_POSITION);
		this.subPosition = getArguments().getInt(ARG_SUB_POSITION);
		this.material = ((App) getActivity().getApplication()).getMaterial();
		this.itemList = material.getMaterialItems().get(parentPosition).getChildren();
		this.user = ((App) getActivity().getApplication()).getUserInfo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.material, container, false);
		content = (TextView) contactsLayout.findViewById(R.id.material_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentNumTextView = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNumTextView = (TextView) contactsLayout.findViewById(R.id.total_num);
		floatingButton = (FloatingActionButton) contactsLayout.findViewById(R.id.fab);

		URLImageParser p = new URLImageParser(content, this.getActivity());

		Spanned htmlSpan = Html.fromHtml(itemList.get(subPosition).getData().getStem(), p, null);
		content.setText(htmlSpan);
		typeName.setText(itemList.get(subPosition).getItemTypeName());
		sourceName.setText(itemList.get(subPosition).getSource());
		currentNumTextView.setText(String.valueOf(itemList.get(subPosition).getIndex() + 1));
		totalNumTextView.setText(String.valueOf(material.getTotalNum()));

		// sample code snippet to set the text content on the ExpandableTextView
		analysis = (ExpandableTextView) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);

		// IMPORTANT - call setText on the ExpandableTextView to set the text
		// content to display
		URLImageParser p2 = new URLImageParser(analysis.getmTv(), this.getActivity());
		Spanned htmlSpan2 = Html.fromHtml(itemList.get(subPosition).getAnalysis(), p2, null);
		analysis.setText(htmlSpan2);

		floatingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				short flag = itemList.get(subPosition).getFlag();
				if (flag == 1) {
					flag = 0;
				} else {
					flag = 1;
				}
				final short f = flag;
				// version,user.id,item.id,flag
				Map<String, Object> requestData = new HashMap<String, Object>();
				requestData.put("item.id", itemList.get(subPosition).getItemId());
				requestData.put("user.id", user.getUserId());
				requestData.put("flag", flag);
				Request request = new Request(R.string.UserTestMasterItem, requestData, getActivity(), ParserType.MAP);
				NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<Map>() {
					@Override
					public void processData(Map paramObject, boolean paramBoolean) {
						itemList.get(subPosition).setFlag(f);
						setFloatingButtonStatus();
						if (f == 1) {
							CommonTools.showShortToast(getActivity(), "成功设置为已掌握");
						} else {
							CommonTools.showShortToastDefaultStyle(getActivity(), "已取消掌握该题");
						}
					}
				});
				setFloatingButtonStatus();
			}
		});
		setFloatingButtonStatus();

		return contactsLayout;
	}

	private void setFloatingButtonStatus() {
		if (itemList.get(subPosition).getFlag() == 1) {
			floatingButton.setImageResource(R.drawable.master_icon);
		} else {
			floatingButton.setImageResource(R.drawable.un_master_icon);
		}
	}
}