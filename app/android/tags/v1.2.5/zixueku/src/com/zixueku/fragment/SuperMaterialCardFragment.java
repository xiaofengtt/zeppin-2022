package com.zixueku.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.activity.AddSujectActivity;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Item;
import com.zixueku.entity.Material;
import com.zixueku.entity.ParserType;
import com.zixueku.entity.Request;
import com.zixueku.entity.TestAbilityChange;
import com.zixueku.entity.User;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.NetThreadUtil;
import com.zixueku.util.URLImageParser;
import com.zixueku.util.URLImageParser2;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

/**
 * 问答题
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperMaterialCardFragment extends Fragment {
	private static final String ARG_POSITION = "position";
	private org.sufficientlysecure.htmltextview.HtmlTextView content;
	private TextView typeName;
	private TextView sourceName;
	private int position;
	private List<Item> itemList;
	private Material material;
	private ExpandableTextView analysis;
	private FloatingActionButton floatingButton;
	private ScrollView scrollView;
	private User user;
	private TextView currentNumTextView;
	private TextView totalNumTextView;

	public static SuperMaterialCardFragment newInstance(int position) {
		SuperMaterialCardFragment f = new SuperMaterialCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public SuperMaterialCardFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.material = ((App) getActivity().getApplication()).getMaterial();
		this.itemList = material.getMaterialItems();
		this.user = ((App) getActivity().getApplication()).getUserInfo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.material, container, false);
		content = (org.sufficientlysecure.htmltextview.HtmlTextView) contactsLayout.findViewById(R.id.material_content);
		
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentNumTextView = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNumTextView = (TextView) contactsLayout.findViewById(R.id.total_num);

		// sample code snippet to set the text content on the ExpandableTextView
		analysis = (ExpandableTextView) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);
		scrollView = (ScrollView) contactsLayout.findViewById(R.id.material_scroll_view);
		floatingButton = (FloatingActionButton) contactsLayout.findViewById(R.id.fab);

		scrollView.setOnTouchListener(new ShowHideOnScroll(floatingButton));

		/*
		 * String htmlContentStr =itemList.get(position).getData().getStem();
		 * StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		 * String ind = (position + 1) + "、"; if
		 * (htmlContentStr.startsWith("<p>")) { htmlContentStrBui.insert(3,
		 * ind); } else if (htmlContentStr.startsWith("<div>")) {
		 * htmlContentStrBui.insert(5, ind); }
		 */

		String htmlContent = itemList.get(position).getData().getStem();

		Log.e("htmlContent", htmlContent);

		//URLImageParser2 p = new URLImageParser2(contactsLayout,content, this.getActivity());
		//Spanned htmlSpan = Html.fromHtml(htmlContent, p, null);

		content.setHtmlFromString(htmlContent, new HtmlTextView.RemoteImageGetter());
		typeName.setText(itemList.get(position).getItemTypeName());
		sourceName.setText(itemList.get(position).getSource());

		// IMPORTANT - call setText on the ExpandableTextView to set the text
		// content to display
		URLImageParser p2 = new URLImageParser(analysis.getmTv(), this.getActivity());
		Spanned htmlSpan2 = Html.fromHtml(itemList.get(position).getAnalysis(), p2, null);
		analysis.setText(htmlSpan2);

		currentNumTextView.setText(String.valueOf(itemList.get(position).getIndex() + 1));
		totalNumTextView.setText(String.valueOf(material.getTotalNum()));

		floatingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				short flag = itemList.get(position).getFlag();
				if (flag == 1) {
					flag = 0;
				} else {
					flag = 1;
				}
				final short f = flag;
				// version,user.id,item.id,flag
				Map<String, Object> requestData = new HashMap<String, Object>();
				requestData.put("item.id", itemList.get(position).getItemId());
				requestData.put("user.id", user.getUserId());
				requestData.put("flag", flag);
				ActionResult<TestAbilityChange> actionResult = new ActionResult<TestAbilityChange>(){};
				Request request = new Request(R.string.UserTestMasterItem, requestData, getActivity(), actionResult);
				NetThreadUtil.sendDataToServerNoProgressDialog(request, new ServerDataCallback<ActionResult<TestAbilityChange>>() {
					@Override
					public void processData(ActionResult<TestAbilityChange> paramObject, boolean paramBoolean) {
						((App)(getActivity().getApplication())).setTestAbilityChange(paramObject.getRecords());
						itemList.get(position).setFlag(f);
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
		if (itemList.get(position).getFlag() == 1) {
			floatingButton.setImageResource(R.drawable.master_icon);
		} else {
			floatingButton.setImageResource(R.drawable.un_master_icon);
		}
	}
}