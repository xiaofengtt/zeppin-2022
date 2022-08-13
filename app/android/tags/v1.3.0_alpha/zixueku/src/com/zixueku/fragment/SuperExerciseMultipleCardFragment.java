package com.zixueku.fragment;

import org.sufficientlysecure.htmltextview.HtmlRemoteImageGetter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip2;
import com.zixueku.R;
import com.zixueku.adapter.ExerciseCardMultipleItemAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.util.App;
import com.zixueku.util.CommonTools;
import com.zixueku.util.URLImageParser;

/**
 * 单选题选项卡
 * 
 * @author huangweidong@zeppin.cn
 * 
 */
public class SuperExerciseMultipleCardFragment extends Fragment {

	private final static String ARG_POSITION = "position";
	private ExerciseCardMultipleItemAdapter multipleItemAdapter;
	private TextView content;
	private ListView listView;
	private Exercise exercise;
	private int position;
	private ViewPager viewPager;
	private TextView typeName;
	private TextView sourceName;
	private Button multipleNextItemButton;
	private PagerSlidingTabStrip2 tab;
	private Item item;
	private TextView currentPosition;
	private TextView totalNum;

	public SuperExerciseMultipleCardFragment(ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		this.viewPager = viewPager;
		this.tab = tab;
	}

	public static SuperExerciseMultipleCardFragment newInstance(int position, ViewPager viewPager, PagerSlidingTabStrip2 tab) {
		SuperExerciseMultipleCardFragment f = new SuperExerciseMultipleCardFragment(viewPager, tab);
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
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
		View contactsLayout = inflater.inflate(R.layout.exercise_multiple, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.exercise_multiple_list_view);
		content = (TextView) contactsLayout.findViewById(R.id.exercise_multiple_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		multipleNextItemButton = (Button) contactsLayout.findViewById(R.id.multiple_next_item_button);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);

		String htmlContentStr = item.getData().getStem();
		/*StringBuilder htmlContentStrBui = new StringBuilder(htmlContentStr);
		String ind = (position + 1) + "、";
		if (htmlContentStr.startsWith("<p>")) {
			htmlContentStrBui.insert(3, ind);
		} else if (htmlContentStr.startsWith("<div>")) {
			htmlContentStrBui.insert(5, ind);
		}*/

		// this.textView = (TextView)this.findViewById(R.id.textview);
		// URLImageParser p = new URLImageParser(content);
		// Spanned htmlSpan =
		// Html.fromHtml(exercise.getItems().get(position).getData().getStem(),
		// p, null);

		ImageGetter imageGetter = new HtmlRemoteImageGetter(content, this.getActivity(), null);
		Spanned htmlSpan = Html.fromHtml(htmlContentStr, imageGetter, null);
		content.setText(htmlSpan);

		typeName.setText(exercise.getItems().get(position).getItemTypeName());
		sourceName.setText(exercise.getItems().get(position).getSource());
		currentPosition.setText(String.valueOf(item.getIndex() + 1));
		totalNum.setText(String.valueOf(exercise.getTotalNum()));

		multipleItemAdapter = new ExerciseCardMultipleItemAdapter(this.getActivity(), exercise.getItems().get(position).getData().getOptions());
		listView.setAdapter(multipleItemAdapter);

		CommonTools.setListViewHeightBasedOnChildren(listView);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Boolean isChecked = exercise.getItems().get(position).getData().getOptions().get(pos).isSelected();
				exercise.getItems().get(position).getData().getOptions().get(pos).setSelected(!isChecked);
				String optionItemId = exercise.getItems().get(position).getData().getOptions().get(pos).getInx();
				CustomerAnswer customerAnswer = new CustomerAnswer(optionItemId);
				if (isChecked) {
					exercise.getItems().get(position).getCustomerAnswer().remove(customerAnswer);
				} else {
					exercise.getItems().get(position).getCustomerAnswer().add(customerAnswer);
				}
				multipleItemAdapter.notifyDataSetChanged();
				tab.updateTabStyles();

				controlNextButtonStyle();
			}
		});

		multipleNextItemButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(position + 1, true);
			}
		});

		controlNextButtonStyle();

		return contactsLayout;
	}

	private void controlNextButtonStyle() {
		if (exercise.getItems().get(position).getCustomerAnswer().isEmpty()) {
			multipleNextItemButton.setClickable(false);
			multipleNextItemButton.setBackgroundColor(getResources().getColor(R.color.grey));
			multipleNextItemButton.setTextColor(getResources().getColor(R.color.black));
		} else {
			multipleNextItemButton.setClickable(true);
			multipleNextItemButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button_background));
			multipleNextItemButton.setTextColor(getResources().getColor(R.color.white));
		}
	}

}