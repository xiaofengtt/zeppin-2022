package com.zixueku.fragment;

import java.util.HashMap;
import java.util.Map;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView.OnExpandStateChangeListener;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.listerner.MaterialAnalysisOnExpandStateChangeListener;
import com.zixueku.listerner.WrongBookAnalysisOnExpandStateChangeListener;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.NetThreadUtil;

/**
 * 问答题
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperMaterialCardFragment extends Fragment {
	private static final String ARG_POSITION = "position";
	private static final String ARG_SUB_POSITION = "subPosition";
	private TextView content;
	private TextView typeName;
	private TextView sourceName;
	private int position;
	private int subPosition;
	private Item item;
	private Material material;
	private ExpandableTextView analysis;
	private FloatingActionButton floatingButton;
	private ScrollView scrollView;
	private User user;
	private TextView currentNumTextView;
	private TextView totalNumTextView;

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
		this.material = ((App) getActivity().getApplication()).getMaterial();
		if (subPosition == -1) {
			this.item = material.getMaterialItems().get(position);
		} else {
			this.item = material.getMaterialItems().get(position).getChildren().get(subPosition);
		}

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
		analysis = (ExpandableTextView) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);
		scrollView = (ScrollView) contactsLayout.findViewById(R.id.material_scroll_view);
		floatingButton = (FloatingActionButton) contactsLayout.findViewById(R.id.fab);
		
		typeName.setText(item.getItemTypeName());
		sourceName.setText(item.getSource());
		
		//scrollView.setOnTouchListener(new ShowHideOnScroll(floatingButton));
		
		ImageGetter p = new  HtmlRemoteImageGetter(content, this.getActivity(),null);
		Spanned htmlSpan = Html.fromHtml(item.getData().getStem(), p, null);
		content.setText(htmlSpan);
		
		
		ImageGetter p2 = new  HtmlRemoteImageGetter(analysis, this.getActivity(),null);
		Spanned htmlSpan2 = Html.fromHtml(item.getAnalysis(), p2, null);
		analysis.setText(htmlSpan2);
		if(subPosition!=-1){
			analysis.setOnExpandStateChangeListener(new MaterialAnalysisOnExpandStateChangeListener(getActivity(), material, position,subPosition));
		}else {
			analysis.setOnExpandStateChangeListener(new MaterialAnalysisOnExpandStateChangeListener(getActivity(), material, position));
		}
		
		currentNumTextView.setText(String.valueOf(item.getIndex() + 1));
		totalNumTextView.setText(String.valueOf(material.getTotalNum()));
		floatingButton.setOnClickListener(new OnClickListener() {
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
				//requestData.put("user.id", user.getUserId());
				requestData.put("flag", flag);
				ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>() {
				};
				Request request = new Request(R.string.UserTestMasterItem, requestData, getActivity(), actionResult);
				NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
					@Override
					public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
						((App) (getActivity().getApplication())).setTestAbilityChange(paramObject.getRecords());
						item.setCompleteType(f);
						setFloatingButtonStatus();
						if (f == 1) {
							CommonTools.showShortToastDefaultStyle(getActivity(), "成功设置为已掌握");
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
		if (item.getCompleteType() == 1) {
			floatingButton.setImageResource(R.drawable.master_icon);
		} else {
			floatingButton.setImageResource(R.drawable.un_master_icon);
		}
	}
}