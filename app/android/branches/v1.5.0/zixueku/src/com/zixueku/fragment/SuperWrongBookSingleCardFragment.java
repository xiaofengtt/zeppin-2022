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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView2;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardSingleItemAdapter;
import com.zixueku.adapter.ExerciseCardSingleItemAdapter;
import com.zixueku.entity.Item;
import com.zixueku.entity.User;
import com.zixueku.entity.WrongBook;
import com.zixueku.listerner.WrongBookAnalysisOnExpandStateChangeListener;
import com.zixueku.listerner.WrongBookSingleItemListViewOnItemClickListener;
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
public class SuperWrongBookSingleCardFragment extends BaseFragment implements OnClickListener {

	private BaseAdapter singleItemAdapter;
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
	private static final String ARG_POSITION = "position";
	private User user;
	private Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			floatButton.setOnClickListener(SuperWrongBookSingleCardFragment.this);
			deleteFloatButton.setOnClickListener(SuperWrongBookSingleCardFragment.this);

			BusinessCommonMethod.setTextHtmlContent(mActivity, analysis, item.getAnalysis());

			typeName.setText(item.getItemTypeName());
			sourceName.setText(item.getSource());
			currentPosition.setText(String.valueOf(item.getIndex() + 1));
			totalNum.setText(String.valueOf(wrongBookInfo.getTotalNum()));
			if (item.isAnalysisIsRead()) {
				listView.setSelector(R.color.transparent);
				listView.setOnItemClickListener(null);
				singleItemAdapter = new AnalysisCardSingleItemAdapter(mActivity, item);
				analysis.expandText2();
			} else {
				singleItemAdapter = new ExerciseCardSingleItemAdapter(mActivity, item.getData().getOptions());
				listView.setOnItemClickListener(new WrongBookSingleItemListViewOnItemClickListener(mActivity, wrongBookInfo, analysis, position));
				analysis.setOnExpandStateChangeListener(new WrongBookAnalysisOnExpandStateChangeListener(mActivity, listView, wrongBookInfo, position));
			}
			listView.setAdapter(singleItemAdapter);
			CommonTools.setListViewHeightBasedOnChildren(listView);
		}
	};

	public static SuperWrongBookSingleCardFragment newInstance(ViewPager viewPager) {
		SuperWrongBookSingleCardFragment f = new SuperWrongBookSingleCardFragment(viewPager);
		return f;
	}
	
	

	public SuperWrongBookSingleCardFragment() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SuperWrongBookSingleCardFragment(ViewPager viewPager) {
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
		View contactsLayout = inflater.inflate(R.layout.wrong_book_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.single_list_view);
		content = (WebView) contactsLayout.findViewById(R.id.single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);

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

}