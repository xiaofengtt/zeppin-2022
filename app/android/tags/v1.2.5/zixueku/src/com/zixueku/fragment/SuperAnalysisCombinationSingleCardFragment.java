package com.zixueku.fragment;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardSingleItemAdapter;
import com.zixueku.adapter.ExerciseCardSingleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.entity.Result;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;
import com.zixueku.util.URLImageParser;
import com.zixueku.widget.LazyFragment;

/**
 * 类说明 组合题中的单选题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 下午1:35:05
 */
public class SuperAnalysisCombinationSingleCardFragment extends Fragment {

	private AnalysisCardSingleItemAdapter singleItemAdapter;
	private TextView content;
	private ListView listView;

	private Exercise exercise;

	private Item item; // 试题
	private int parentPosition;
	private int subPosition;

	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;
	private HtmlTextView analysis;
	// private LinearLayout linearLayout;

	// private LinearLayout customerAnswerLayout;
	// private TextView customerAnswer;
	private TextView answer;

	public SuperAnalysisCombinationSingleCardFragment(int parentPosition, int subPosition, ViewPager viewPager) {
		this.parentPosition = parentPosition;
		this.subPosition = subPosition;
		this.viewPager = viewPager;
	}

	public static Fragment newInstance(int parentPosition, int subPosition, ViewPager viewPager) {
		Fragment f = new SuperAnalysisCombinationSingleCardFragment(parentPosition, subPosition, viewPager);
		// Bundle b = new Bundle();
		// b.putString(ARG_EXERCISE, JSONUtil.objectToJson(exercise));
		// b.putInt(ARG_POSITION, position);
		// f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated constructor stub
		this.exercise = ((App) (getActivity().getApplication())).getExercise();
		this.item = exercise.getItems().get(parentPosition).getChildren().get(subPosition);
		// this.position = getArguments().getInt(ARG_POSITION);
		// this.item = JSONUtil.jsonToObject(getArguments().getString(ARG_ITEM),
		// Item.class);
		// this.exercise =
		// JSONUtil.jsonToObject(getArguments().getString(ARG_EXERCISE),
		// Exercise.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.analysis_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_single_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.exercise_single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.single_type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.single_source_name);
		analysis = (HtmlTextView) contactsLayout.findViewById(R.id.single_analysis);
		// linearLayout = (LinearLayout)
		// contactsLayout.findViewById(R.id.analysis_bar);

		// customerAnswerLayout = (LinearLayout)
		// contactsLayout.findViewById(R.id.customer_answer_layout);
		// customerAnswer = (TextView)
		// contactsLayout.findViewById(R.id.customer_answer);
		answer = (TextView) contactsLayout.findViewById(R.id.right_answer);

		// this.textView = (TextView)this.findViewById(R.id.textview);
		/*
		 * URLImageParser p2 = new URLImageParser(content); Spanned htmlSpan =
		 * Html.fromHtml(item.getData().getStem(), p2, null);
		 * content.setText(htmlSpan);
		 */

		String htmlContentStr = item.getData().getStem();
		StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		String ind = item.getIndex() + "、";
		if (htmlContentStr.startsWith("<p>")) {
			htmlContentStrBui.insert(3, ind);
		} else if (htmlContentStr.startsWith("<div>")) {
			htmlContentStrBui.insert(5, ind);
		}

		URLImageParser p = new URLImageParser(content, this.getActivity());
		Spanned htmlSpan = Html.fromHtml(htmlContentStrBui.toString(), p, null);

		content.setText(htmlSpan);

		// content.loadDataWithBaseURL("", htmlContentStrBui.toString(),
		// "text/html", "utf-8", "");
		// content.setHtmlFromString("<p>" + (position + 1) + "、" +
		// item.getData().getStem().substring(3), false);

		typeName.setText(item.getItemTypeName());
		sourceName.setText(item.getSource());

		/*
		 * URLImageParser p = new URLImageParser(analysis); Spanned htmlSpan2 =
		 * Html.fromHtml(item.getAnalysis(), p, null);
		 */
		analysis.setHtmlFromString(item.getAnalysis(),new HtmlTextView.RemoteImageGetter());

		singleItemAdapter = new AnalysisCardSingleItemAdapter(this.getActivity(), item);
		listView.setAdapter(singleItemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);

		/*
		 * // 答错的情况 if (!exercise.getItems().get(position).isRight()) {
		 * //linearLayout
		 * .setBackgroundColor(getResources().getColor(R.color.anlysisfalsebar
		 * ));
		 * 
		 * if (item.getCustomerAnswer().size() == 0) {
		 * customerAnswer.setText("未选择"); } else { StringBuffer customerBuf =
		 * new StringBuffer(); for (CustomerAnswer cust :
		 * item.getCustomerAnswer()) {
		 * customerBuf.append(Constant.OPTION_INDEX.get(cust.getInx())); }
		 * customerAnswer.setText(customerBuf.toString()); }
		 * 
		 * } else { // 答对的情况 customerAnswerLayout.setVisibility(View.GONE); }
		 */

		StringBuffer resultBuf = new StringBuffer();
		for (Result result : item.getData().getResults()) {
			resultBuf.append(Constant.OPTION_INDEX.get(result.getInx()));
		}
		answer.setText(resultBuf.toString());
		return contactsLayout;
	}
}
