package com.zixueku.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView2;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardMultipleItemAdapter;
import com.zixueku.adapter.ExerciseCardMultipleItemAdapter;
import com.zixueku.entity.Item;
import com.zixueku.entity.User;
import com.zixueku.entity.WrongBook;
import com.zixueku.listerner.WrongBookAnalysisOnExpandStateChangeListener;
import com.zixueku.listerner.WrongBookMultipleItemListViewOnItemClickListener;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;

/**
 * 单选题选项卡
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperWrongBookMultipleCardFragment extends BaseFragment implements OnClickListener {
	private static final String ARG_POSITION = "position";
	private BaseAdapter itemAdapter;
	private WebView content;
	private ListView listView;
	private WrongBook wrongBookInfo;
	private Item item;
	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;
	private TextView currentPosition;
	private TextView totalNum;
	private ExpandableTextView2 analysis;
	private ImageButton floatButton;
	private ImageButton deleteFloatButton;
	private boolean isExpand = false; // 按钮是否展开
	private int position;
	private User user;
	private Button multipleNextItemButton;
	private Handler handler = new Handler();
	

	public static SuperWrongBookMultipleCardFragment newInstance(ViewPager viewPager) {
		SuperWrongBookMultipleCardFragment f = new SuperWrongBookMultipleCardFragment(viewPager);
		return f;
	}
	
	

	public SuperWrongBookMultipleCardFragment() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SuperWrongBookMultipleCardFragment(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.wrongBookInfo = App.getInstance().getWrongBookInfo();
		this.user = App.getInstance().getUserInfo();
		this.item = wrongBookInfo.getWrongItemList().get(position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		isExpand = false;
		View contactsLayout = inflater.inflate(R.layout.wrong_book_multiple, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.list_view);
		content = (WebView) contactsLayout.findViewById(R.id.content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);
		multipleNextItemButton = (Button) contactsLayout.findViewById(R.id.multiple_commit_button);
		analysis = (ExpandableTextView2) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);
		floatButton = (ImageButton) contactsLayout.findViewById(R.id.float_button);
		deleteFloatButton = (ImageButton) contactsLayout.findViewById(R.id.delete_button);
		BusinessCommonMethod.setWetbViewContent(mActivity, content, item.getData().getStem());
		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);
		return contactsLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.float_button:
			changeFloatButtonStyle();
			break;
		case R.id.delete_button:
			deleteItem();
			break;
		case R.id.multiple_commit_button:
			MultipleButtonOnclick();
			break;
		}
	}

	private void changeFloatButtonStyle() {
		int space = 150;
		if (isExpand) {
			BusinessCommonMethod.slideview(space, 0, deleteFloatButton);
		} else {
			BusinessCommonMethod.slideview(-space, 0, deleteFloatButton);
		}
		isExpand = !isExpand;
	}

	private void deleteItem() {
		BusinessCommonMethod.deleteChoiseItemFromWrongBook(mActivity, wrongBookInfo, position, viewPager, user);
	}

	private void MultipleButtonOnclick() {
		analysis.expandText();
		BusinessCommonMethod.wrongBookSubmitItem(mActivity, item);
		multipleNextItemButton.setClickable(false);
		multipleNextItemButton.setBackgroundColor(getResources().getColor(R.color.grey));
		multipleNextItemButton.setTextColor(getResources().getColor(R.color.black));
	}

	private void controlNextButtonStyle() {
		if (wrongBookInfo.getWrongItemList().get(position).getCustomerAnswer().isEmpty()) {
			multipleNextItemButton.setClickable(false);
			multipleNextItemButton.setBackgroundColor(getResources().getColor(R.color.grey));
			multipleNextItemButton.setTextColor(getResources().getColor(R.color.black));
		} else {
			multipleNextItemButton.setClickable(true);
			multipleNextItemButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_background));
			multipleNextItemButton.setTextColor(getResources().getColor(R.color.white));
		}
	}
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			floatButton.setOnClickListener(SuperWrongBookMultipleCardFragment.this);
			deleteFloatButton.setOnClickListener(SuperWrongBookMultipleCardFragment.this);
			BusinessCommonMethod.setTextHtmlContent(mActivity, analysis, item.getAnalysis());
			typeName.setText(item.getItemTypeName());
			sourceName.setText(item.getSource());
			currentPosition.setText(String.valueOf(item.getIndex() + 1));
			totalNum.setText(String.valueOf(wrongBookInfo.getTotalNum()));

			if (item.isAnalysisIsRead()) {
				listView.setSelector(R.color.transparent);
				listView.setOnItemClickListener(null);
				itemAdapter = new AnalysisCardMultipleItemAdapter(mActivity, item);
				analysis.expandText2();
			} else {
				itemAdapter = new ExerciseCardMultipleItemAdapter(mActivity, item.getData().getOptions());
				listView.setOnItemClickListener(new WrongBookMultipleItemListViewOnItemClickListener(mActivity, wrongBookInfo, multipleNextItemButton, listView, position));
				analysis.setOnExpandStateChangeListener(new WrongBookAnalysisOnExpandStateChangeListener(mActivity, listView, wrongBookInfo, position));
			}
			listView.setAdapter(itemAdapter);
			controlNextButtonStyle();
			multipleNextItemButton.setOnClickListener(SuperWrongBookMultipleCardFragment.this);
			CommonTools.setListViewHeightBasedOnChildren(listView);
		}
	};

}