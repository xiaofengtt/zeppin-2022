package com.zixueku.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.adapter.ExerciseCardSingleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.URLImageParser;

/**
 * 单选题选项卡
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperExerciseSingleCardFragment extends Fragment {

	private ExerciseCardSingleItemAdapter singleItemAdapter;
	private TextView content;
	private ListView listView;

	private PagerSlidingTabStrip2 tab;

	private Exercise exercise;

	// private Item item; // 试题

	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;

	private int position;
	private static final String ARG_POSITION = "position";

	public static SuperExerciseSingleCardFragment newInstance(int position, ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		SuperExerciseSingleCardFragment f = new SuperExerciseSingleCardFragment(viewPager,tab);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}


	public SuperExerciseSingleCardFragment(ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		this.viewPager = viewPager;
		this.tab = tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.exercise = ((App)(getActivity().getApplication())).getExercise();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.exercise_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_single_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.exercise_single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);

		// this.textView = (TextView)this.findViewById(R.id.textview);
		// Log.e(">>>>>>>>>>>",
		// exercise.getItems().get(position).getData().getStem());
		// URLImageParser p = new URLImageParser(content);
		// Spanned htmlSpan =
		// Html.fromHtml(exercise.getItems().get(position).getData().getStem(),
		// p, null);

		// content.setText(htmlSpan);

		String htmlContentStr = exercise.getItems().get(position).getData().getStem();
		StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		String ind = (position + 1) + "、";
		if (htmlContentStr.startsWith("<p>")) {
			htmlContentStrBui.insert(3, ind);
		} else if (htmlContentStr.startsWith("<div>")) {
			htmlContentStrBui.insert(5, ind);
		}

		/*
		 * // 让网页自适应屏幕宽度 WebSettings webSettings = content.getSettings(); //
		 * webView: 类WebView的实例
		 * webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		 * 
		 * webSettings.setUseWideViewPort(true);
		 * webSettings.setLoadWithOverviewMode(true);
		 */
		// content.loadDataWithBaseURL("", htmlContentStrBui.toString(),
		// "text/html", "utf-8", "");

		// content.setHtmlFromString(htmlContentStrBui.toString(),
		// true,content);

		URLImageParser p = new URLImageParser(content, this.getActivity());
		Spanned htmlSpan = Html.fromHtml(htmlContentStrBui.toString(), p, null);

		content.setText(htmlSpan);

		typeName.setText(exercise.getItems().get(position).getItemTypeName());
		sourceName.setText(exercise.getItems().get(position).getSource());

		singleItemAdapter = new ExerciseCardSingleItemAdapter(this.getActivity(), exercise.getItems().get(position).getData().getOptions());
		listView.setAdapter(singleItemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

				for (int i = 0; i < exercise.getItems().get(position).getData().getOptions().size(); i++) {
					exercise.getItems().get(position).getData().getOptions().get(i).setSelected(false);
				}
				//singleItemAdapter.notifyDataSetChanged();

				exercise.getItems().get(position).getCustomerAnswer().clear();

				CustomerAnswer customerAnswer = new CustomerAnswer(exercise.getItems().get(position).getData().getOptions().get(pos).getInx());

				exercise.getItems().get(position).getCustomerAnswer().add(customerAnswer);
				exercise.getItems().get(position).getData().getOptions().get(pos).setSelected(true);

				//((RadioButton)view.findViewById(R.id.exercise_single_item_radio_button)).setChecked(true);

				singleItemAdapter.notifyDataSetChanged();
				tab.updateTabStyles();
				viewPager.setCurrentItem(position + 1);
			}
		});
		return contactsLayout;
	}
}