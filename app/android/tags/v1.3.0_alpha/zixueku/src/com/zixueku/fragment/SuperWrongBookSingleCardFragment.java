package com.zixueku.fragment;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

/**
 * 单选题选项卡
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperWrongBookSingleCardFragment extends Fragment implements OnClickListener {

	private BaseAdapter singleItemAdapter;
	private TextView content;
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
	private Context context;

	public static SuperWrongBookSingleCardFragment newInstance(ViewPager viewPager) {
		SuperWrongBookSingleCardFragment f = new SuperWrongBookSingleCardFragment(viewPager);
		return f;
	}

	public SuperWrongBookSingleCardFragment(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.wrongBookInfo = ((App) (getActivity().getApplication())).getWrongBookInfo();
		this.user = ((App) (getActivity().getApplication())).getUserInfo();
		this.item = wrongBookInfo.getWrongItemList().get(position);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		isExpand = false;
		View contactsLayout = inflater.inflate(R.layout.wrong_book_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.single_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);

		analysis = (ExpandableTextView2) contactsLayout.findViewById(R.id.expand_text_view);
		analysis.setOutSideView(contactsLayout);

		floatButton = (ImageButton) contactsLayout.findViewById(R.id.float_button);
		deleteFloatButton = (ImageButton) contactsLayout.findViewById(R.id.delete_button);
		floatButton.setOnClickListener(this);
		deleteFloatButton.setOnClickListener(this);

		ImageGetter imageGetter = new HtmlRemoteImageGetter(content, this.getActivity(), null);
		Spanned htmlSpan = Html.fromHtml(item.getData().getStem(), imageGetter, null);
		content.setText(htmlSpan);

		ImageGetter p2 = new HtmlRemoteImageGetter(analysis.getmTv(), this.getActivity(), null);
		Spanned htmlSpan2 = Html.fromHtml(item.getAnalysis(), p2, null);

		SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(1);
		sparseBooleanArray.put(0, false);
		analysis.setText(htmlSpan2);

		typeName.setText(item.getItemTypeName());
		sourceName.setText(item.getSource());
		currentPosition.setText(String.valueOf(item.getIndex() + 1));
		totalNum.setText(String.valueOf(wrongBookInfo.getTotalNum()));
		if (item.isAnalysisIsRead()) {
			listView.setSelector(R.color.transparent);
			listView.setOnItemClickListener(null);
			singleItemAdapter = new AnalysisCardSingleItemAdapter(this.getActivity(), item);
			analysis.expandText2();
		} else {
			singleItemAdapter = new ExerciseCardSingleItemAdapter(this.getActivity(), item.getData().getOptions());
			listView.setOnItemClickListener(new WrongBookSingleItemListViewOnItemClickListener(context, wrongBookInfo, analysis, position));
			analysis.setOnExpandStateChangeListener(new WrongBookAnalysisOnExpandStateChangeListener(context, listView, wrongBookInfo, position));
		}
		listView.setAdapter(singleItemAdapter);
		CommonTools.setListViewHeightBasedOnChildren(listView);
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

		BusinessCommonMethod.deleteChoiseItemFromWrongBook(context, wrongBookInfo, position, viewPager, user);
	}

}