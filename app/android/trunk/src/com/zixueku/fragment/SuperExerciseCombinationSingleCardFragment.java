package com.zixueku.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.activity.ExerciseActivity;
import com.zixueku.adapter.ExerciseCardSingleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;

/**
 * 类说明 组合题中的单选题
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月10日 下午1:35:05
 */
public class SuperExerciseCombinationSingleCardFragment extends BaseFragment implements OnItemClickListener {
	private static final String ARG_PARENT_POSITION = "parentPosition";
	private static final String ARG_SUB_POSITION = "subPosition";
	private ExerciseCardSingleItemAdapter singleItemAdapter;
	private WebView content;
	private ListView listView;
	private PagerSlidingTabStrip2 tab;
	private Exercise exercise;
	private Item item; // 试题
	private ViewPager subViewPager;
	private ViewPager parentViewPager;
	private TextView typeName;
	private TextView sourceName;
	private int subPosition;
	private int parentPosition;
	private int subViewPagerCount;
	private TextView currentPosition;
	private TextView totalNum;
	private Handler handler = new Handler();

	public static SuperExerciseCombinationSingleCardFragment newInstance(int parentPosition, int subPosition, ViewPager parentViewPager, ViewPager subViewPager, PagerSlidingTabStrip2 tab) {
		SuperExerciseCombinationSingleCardFragment f = new SuperExerciseCombinationSingleCardFragment(parentViewPager, subViewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_SUB_POSITION, subPosition);
		b.putInt(ARG_PARENT_POSITION, parentPosition);
		f.setArguments(b);
		return f;
	}
	
	

	public SuperExerciseCombinationSingleCardFragment() {
		super();
		// TODO Auto-generated constructor stub
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
		this.exercise = App.getInstance().getExercise();
		this.subViewPagerCount = subViewPager.getAdapter().getCount();
		this.item = exercise.getItems().get(parentPosition).getChildren().get(subPosition);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.exercise_single, container, false);
		content = (WebView) contactsLayout.findViewById(R.id.exercise_single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_single_list_view);
		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);

		BusinessCommonMethod.setWetbViewContent(mActivity, content, item.getData().getStem());

		return contactsLayout;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		for (int i = 0; i < exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().size(); i++) {
			exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().get(i).setSelected(false);
		}
		exercise.getItems().get(parentPosition).getChildren().get(subPosition).getCustomerAnswer().clear();
		CustomerAnswer customerAnswer = new CustomerAnswer(exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().get(pos).getInx());
		exercise.getItems().get(parentPosition).getChildren().get(subPosition).getCustomerAnswer().add(customerAnswer);
		exercise.getItems().get(parentPosition).getChildren().get(subPosition).getData().getOptions().get(pos).setSelected(true);
		singleItemAdapter.notifyDataSetChanged();
		tab.updateTabStyles();
		int nextPosition = subPosition + 1;
		subViewPager = ExerciseActivity.subViewPagers.get(parentPosition);
		if (nextPosition >= subViewPagerCount) {
			parentViewPager.setCurrentItem(parentPosition + 1, true);
		} else {
			subViewPager.setCurrentItem(subViewPager.getCurrentItem() + 1, true);
		}
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			typeName.setText(item.getItemTypeName());
			sourceName.setText(exercise.getItems().get(parentPosition).getSource());
			currentPosition.setText(String.valueOf(item.getIndex() + 1));
			totalNum.setText(String.valueOf(exercise.getTotalNum()));
			singleItemAdapter = new ExerciseCardSingleItemAdapter(mActivity, item.getData().getOptions());
			listView.setAdapter(singleItemAdapter);
			CommonTools.setListViewHeightBasedOnChildren(listView);
			// 填充各控件的数据
			listView.setOnItemClickListener(SuperExerciseCombinationSingleCardFragment.this);
		}
	};
}
