package com.zixueku.fragment;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.adapter.ExerciseCardSingleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.URLImageParser;
import com.zixueku.widget.LazyFragment;

/**
 * 类说明 组合题中的单选题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 下午1:35:05
 */
public class SuperExerciseCombinationSingleCardFragment extends LazyFragment {

	private ExerciseCardSingleItemAdapter singleItemAdapter;
	private TextView content;
	private ListView listView;

	private PagerSlidingTabStrip2 tab;

	private Exercise exercise;
	// 标志位，标志是否已经初始化完成。
	private boolean isPrepared;

	// private Item item; // 试题

	private ViewPager subViewPager;
	private ViewPager parentViewPager;
	private TextView typeName;
	private TextView sourceName;

	private int subPosition;
	private int parentPosition;
	private int subViewPagerCount;

	private static final String ARG_SUB_POSITION = "subPosition";
	private static final String ARG_PARENT_POSITION = "parentPosition";

	public static SuperExerciseCombinationSingleCardFragment newInstance(int parentPosition, int subPosition, ViewPager parentViewPager,
			ViewPager subViewPager, PagerSlidingTabStrip2 tab) {
		SuperExerciseCombinationSingleCardFragment f = new SuperExerciseCombinationSingleCardFragment(parentViewPager, subViewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_SUB_POSITION, subPosition);
		b.putInt(ARG_PARENT_POSITION, parentPosition);

		f.setArguments(b);
		return f;
	}

	public SuperExerciseCombinationSingleCardFragment(ViewPager parentViewPager, ViewPager subViewPager, PagerSlidingTabStrip2 tab) {
		this.parentViewPager = parentViewPager;
		this.subViewPager = subViewPager;
		this.tab = tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.subPosition = getArguments().getInt(ARG_SUB_POSITION);
		this.parentPosition = getArguments().getInt(ARG_PARENT_POSITION);
		this.exercise = ((App) (getActivity().getApplication())).getExercise();
		// this.subViewPager =
		this.subViewPagerCount = subViewPager.getAdapter().getCount();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.exercise_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_single_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.exercise_single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		String htmlContentStr = exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getStem();
		StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		String ind = exercise.getItems().get(parentPosition).getChildren().get(subPosition).getIndex() + "、";
		if (htmlContentStr.startsWith("<p>")) {
			htmlContentStrBui.insert(3, ind);
		} else if (htmlContentStr.startsWith("<div>")) {
			htmlContentStrBui.insert(5, ind);
		}

		URLImageParser p = new URLImageParser(content, this.getActivity());
		Spanned htmlSpan = Html.fromHtml(htmlContentStrBui.toString(), p, null);

		content.setText(htmlSpan);

		typeName.setText(exercise.getItems().get(parentPosition).getChildren().get(subPosition).getItemTypeName());
		sourceName.setText(exercise.getItems().get(parentPosition).getChildren().get(subPosition).getSource());

		singleItemAdapter = new ExerciseCardSingleItemAdapter(this.getActivity(), exercise.getItems().get(parentPosition).getChildren().get(subPosition)
				.getData().getOptions());
		listView.setAdapter(singleItemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);
		// 填充各控件的数据
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

				for (int i = 0; i < exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().size(); i++) {
					exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().get(i).setSelected(false);
				}
				// singleItemAdapter.notifyDataSetChanged();

				exercise.getItems().get(parentPosition).getChildren().get(subPosition).getCustomerAnswer().clear();

				CustomerAnswer customerAnswer = new CustomerAnswer(exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData()
						.getOptions().get(pos).getInx());

				exercise.getItems().get(parentPosition).getChildren().get(subPosition).getCustomerAnswer().add(customerAnswer);
				exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().get(pos).setSelected(true);
				// ((RadioButton)view.findViewById(R.id.exercise_single_item_radio_button)).setChecked(true);
				singleItemAdapter.notifyDataSetChanged();
				tab.updateTabStyles();
				int nextPosition = subPosition + 1;
				subViewPager = tab.getSubViewPager();
				if (nextPosition >= subViewPagerCount) {
					parentViewPager.setCurrentItem(parentPosition + 1, true);
				} else {
					Log.e("subViewPager", subViewPager.getCurrentItem() + "   " + subViewPager.getAdapter().getCount() + "   " + nextPosition);
					subViewPager.setCurrentItem(subViewPager.getCurrentItem() + 1, true);
				}
			}
		});

		isPrepared = true;
		lazyLoad();
		return contactsLayout;
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
	}
}
