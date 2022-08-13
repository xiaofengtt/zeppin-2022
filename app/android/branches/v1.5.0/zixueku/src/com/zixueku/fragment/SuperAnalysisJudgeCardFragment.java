package com.zixueku.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.AnalysisCardJudgeItemAdapter;
import com.zixueku.entity.Exercise;
import com.zixueku.entity.Item;
import com.zixueku.util.BusinessCommonMethod;
import com.zixueku.util.CommonTools;
import com.zixueku.util.Constant;

/**
 * 类说明 答案解析中 判断题 (和组合题公用一个)
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月23日 下午7:10:55
 */
public class SuperAnalysisJudgeCardFragment extends BaseFragment {
	private static final String ARG_POSITION = "position";
	private static final String ARG_SUB_POSITION = "subPosition";
	private ArrayAdapter<?> listViewAdapter;
	private WebView content;
	private ListView listView;
	private Exercise exercise;
	private int position;
	private int subPosition;
	private TextView typeName;
	private TextView sourceName;
	private TextView analysis;
	private ImageView rightAnswerImg;
	private ImageView customerAnswerImg;
	private TextView correctNum; // 该题答对的次数
	private TextView wrongNum; // 该题答错的次数
	private Item item;
	private TextView currentPosition;
	private TextView totalNum;
	private Handler handler = new Handler();

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			typeName.setText(item.getItemTypeName());
			sourceName.setText(exercise.getItems().get(position).getSource());
			currentPosition.setText(String.valueOf(item.getIndex() + 1));
			totalNum.setText(String.valueOf(exercise.getTotalNum()));
			listView.setAdapter(listViewAdapter);
			String rightIndex = item.getData().getResults().get(0).getInx();
			if (rightIndex.equals("1")) {
				rightAnswerImg.setImageResource(R.drawable.analysis_right_icon);
			} else {
				rightAnswerImg.setImageResource(R.drawable.analysis_error_icon);
			}

			BusinessCommonMethod.setTextHtmlContent(mActivity, analysis, item.getAnalysis());

			int wrongCount = item.getTestItemWrongCount();
			int correctCount = item.getTestItemCorrectCount();
			String customerStr = "";
			if (item.getCustomerAnswer().iterator().hasNext()) {
				customerStr = item.getCustomerAnswer().iterator().next().getInx();
			}

			if (item.isRight()) {
				correctCount++;
				if (customerStr.equals("1")) {
					customerAnswerImg.setImageResource(R.drawable.analysis_right_icon);
				} else {
					customerAnswerImg.setImageResource(R.drawable.analysis_error_icon);
				}
			} else {
				wrongCount++;
				if (customerStr.equals("1")) {
					customerAnswerImg.setImageResource(R.drawable.analysis_right_icon2);
				} else {
					customerAnswerImg.setImageResource(R.drawable.analysis_error_icon2);
				}
			}
			if (correctCount > 0) {
				correctNum.setText("做对" + correctCount + "次");
				correctNum.setVisibility(View.VISIBLE);
			} else {
				correctNum.setVisibility(View.GONE);
			}

			if (wrongCount > 0) {
				wrongNum.setText("做错" + wrongCount + "次");
				wrongNum.setVisibility(View.VISIBLE);
			} else {
				wrongNum.setVisibility(View.GONE);
			}

			CommonTools.setListViewHeightBasedOnChildren(listView);
		}
	};
	
	public SuperAnalysisJudgeCardFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuperAnalysisJudgeCardFragment(Exercise exercise) {
		this.exercise = exercise;
	}

	// 第一个参数为元素在父结构中的位置,如果为组合题则第二位为元素在子结构中的位置
	public static SuperAnalysisJudgeCardFragment newInstance(Exercise exercise,int... position) {
		SuperAnalysisJudgeCardFragment f = new SuperAnalysisJudgeCardFragment(exercise);
		Bundle b = new Bundle();
		if (position.length == 2) {
			b.putInt(ARG_SUB_POSITION, position[1]);
		}
		b.putInt(ARG_POSITION, position[0]);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.position = getArguments().getInt(ARG_POSITION);
		this.subPosition = getArguments().getInt(ARG_SUB_POSITION, -1);

		if (subPosition == -1) {
			item = exercise.getItems().get(position);
		} else {
			item = exercise.getItems().get(position).getChildren().get(subPosition);
		}
		listViewAdapter = new AnalysisCardJudgeItemAdapter(mActivity, item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.analysis_judge, container, false);
		listView = (ListView) contactsLayout.findViewById(R.id.judge_list_view);
		content = (WebView) contactsLayout.findViewById(R.id.judge_content);
		typeName = (TextView) contactsLayout.findViewById(R.id.type_name);
		sourceName = (TextView) contactsLayout.findViewById(R.id.source_from);
		analysis = (TextView) contactsLayout.findViewById(R.id.judge_analysis);
		rightAnswerImg = (ImageView) contactsLayout.findViewById(R.id.right_answer);
		customerAnswerImg = (ImageView) contactsLayout.findViewById(R.id.customer_answer);
		correctNum = (TextView) contactsLayout.findViewById(R.id.testItemCorrectCount);
		wrongNum = (TextView) contactsLayout.findViewById(R.id.testItemWrongCount);
		currentPosition = (TextView) contactsLayout.findViewById(R.id.current_num);
		totalNum = (TextView) contactsLayout.findViewById(R.id.total_num);
		BusinessCommonMethod.setWetbViewContent(mActivity, content, item.getData().getStem());
		handler.postDelayed(runnable, Constant.LOADING_ITEM_TIME);
		return contactsLayout;
	}
}
