package com.zixueku.fragment;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardMultipleItemAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.util.CommonTools;
import com.zixueku.util.URLImageParser;

/**
 * 单选题选项卡
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperAnalysisMultipleCardFragment extends Fragment {

	private AnalysisCardMultipleItemAdapter multipleItemAdapter;
	private HtmlTextView content;
	private ListView listView;

	private Exercise exercise;

	private int position;

	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;
	private TextView analysis;

	public SuperAnalysisMultipleCardFragment(Exercise exercise, int position, ViewPager viewPager) {
		// TODO Auto-generated constructor stub
		this.exercise = exercise;
		this.position = position;
		this.viewPager = viewPager;
	}

	public static SuperAnalysisMultipleCardFragment newInstance(Exercise exercise, int position, ViewPager viewPager) {
		SuperAnalysisMultipleCardFragment f = new SuperAnalysisMultipleCardFragment(exercise, position, viewPager);
		// Bundle b = new Bundle();
		// b.putString(ARG_EXERCISE, JSONUtil.objectToJson(exercise));
		// b.putInt(ARG_POSITION, position);
		// f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.position = getArguments().getInt(ARG_POSITION);
		// this.item = JSONUtil.jsonToObject(getArguments().getString(ARG_ITEM),
		// Item.class);
		// this.exercise =
		// JSONUtil.jsonToObject(getArguments().getString(ARG_EXERCISE),
		// Exercise.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.analysis_multiple, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_multiple_list_view);
		content = (HtmlTextView) contactsLayout.findViewById(R.id.exercise_multiple_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.multiple_type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.multiple_source_name);
		analysis = (TextView) contactsLayout.findViewById(R.id.multiple_analysis);

		// this.textView = (TextView)this.findViewById(R.id.textview);
		//URLImageParser p = new URLImageParser(content);
		//Spanned htmlSpan = Html.fromHtml(exercise.getItems().get(position).getData().getStem(), p, null);
		content.setHtmlFromString(exercise.getItems().get(position).getData().getStem(),new HtmlTextView.RemoteImageGetter());
		typeName.setText(exercise.getItems().get(position).getItemTypeName());
		sourceName.setText(exercise.getItems().get(position).getSource());

		URLImageParser p = new URLImageParser(analysis,this.getActivity());
		Spanned htmlSpan2 = Html.fromHtml(exercise.getItems().get(position).getAnalysis(), p, null);
		analysis.setText(htmlSpan2);

		multipleItemAdapter = new AnalysisCardMultipleItemAdapter(this.getActivity(), exercise.getItems().get(position));
		listView.setAdapter(multipleItemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);

		return contactsLayout;
	}

}