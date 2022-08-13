package com.zixueku.fragment;

import java.util.List;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.adapter.ExerciseCardJudgeItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.entity.Option;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;

/**
 * 判断题选项卡 答题界面
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperExerciseJudgeCardFragment extends Fragment {
	private BaseAdapter itemAdapter;
	private TextView content;
	private ListView listView;

	private PagerSlidingTabStrip2 tab;

	private Exercise exercise;

	// private Item item; // 试题

	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;
	private TextView currentPosition;
	private TextView totalNum;
	private Item item;
	private int position;
	private static final String ARG_POSITION = "position";

	public static SuperExerciseJudgeCardFragment newInstance(int position, ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		SuperExerciseJudgeCardFragment f = new SuperExerciseJudgeCardFragment(viewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public SuperExerciseJudgeCardFragment(ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		this.viewPager = viewPager;
		this.tab = tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.exercise = ((App) (getActivity().getApplication())).getExercise();
		this.item = exercise.getItems().get(position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.exercise_single, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_single_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.exercise_single_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);

		String htmlContentStr = item.getData().getStem();

		ImageGetter imageGetter = new HtmlRemoteImageGetter(content, this.getActivity(), null);
		Spanned htmlSpan = Html.fromHtml(htmlContentStr, imageGetter, null);
		content.setText(htmlSpan);

		typeName.setText(item.getItemTypeName());
		sourceName.setText(item.getSource());
		currentPosition.setText(String.valueOf(item.getIndex() + 1));
		totalNum.setText(String.valueOf(exercise.getTotalNum()));

		List<Option> options = exercise.getItems().get(position).getData().getOptions();
		if (options == null || options.size() == 0) {
			Option option1 = new Option("1", "正确", false);
			Option option2 = new Option("2", "错误", false);
			options.add(option1);
			options.add(option2);
			exercise.getItems().get(position).getData().setOptions(options);
		}
		itemAdapter = new ExerciseCardJudgeItemAdapter(this.getActivity(), options);
		listView.setAdapter(itemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

				for (int i = 0; i < exercise.getItems().get(position).getData().getOptions().size(); i++) {
					exercise.getItems().get(position).getData().getOptions().get(i).setSelected(false);
				}
				exercise.getItems().get(position).getCustomerAnswer().clear();

				CustomerAnswer customerAnswer = new CustomerAnswer(exercise.getItems().get(position).getData().getOptions().get(pos).getInx());

				exercise.getItems().get(position).getCustomerAnswer().add(customerAnswer);
				exercise.getItems().get(position).getData().getOptions().get(pos).setSelected(true);

				// ((RadioButton)view.findViewById(R.id.exercise_single_item_radio_button)).setChecked(true);

				itemAdapter.notifyDataSetChanged();
				tab.updateTabStyles();
				viewPager.setCurrentItem(position + 1);
			}
		});
		return contactsLayout;
	}
}