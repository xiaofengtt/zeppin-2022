package com.zixueku.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.WrongBookCardGridViewAdapter;
import com.zixueku.entity.Item;
import com.zixueku.entity.WrongBook;
import com.zixueku.util.App;
import com.zixueku.util.BusinessCommonMethod;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月21日 下午1:42:39
 */
public class WrongBookCardFragment extends Fragment {
	private WrongBook wrongBook;
	private ViewPager viewPager;
	private int errorNum;
	private int rightNum;

	public WrongBookCardFragment(ViewPager viewPager) {
		super();
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wrongBook = ((App) (getActivity().getApplication())).getWrongBookInfo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wrong_book_card_layout, container, false);
		TextView title = (TextView) view.findViewById(R.id.wrong_book_card_title);
		GridView gridView = (GridView) view.findViewById(R.id.wrong_book_card_grid_view);
		BusinessCommonMethod.judge(wrongBook);
		errorNum = wrongBook.getWrongNum();
		rightNum = wrongBook.getRightNum();
		gridView.setAdapter(new WrongBookCardGridViewAdapter(getActivity(), wrongBook, viewPager));
		title.setText(String.format("正确%d道,错误%d道,共%d道", rightNum, errorNum, wrongBook.getTotalNum()));
		return view;
	}

	// 计算答题情况
	// completeType -1未答, 0答错,1答对
	private void calculate() {
		for (Item item : wrongBook.getWrongItemList()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					choice(child);
				}
			} else {
				choice(item);
			}
		}
	}

	private void choice(Item item) {
		int completeType = item.getCompleteType();
		switch (completeType) {
		case -1:
			break;
		case 0:
			errorNum++;
			break;
		case 1:
			rightNum++;
			break;
		}
	}
}
