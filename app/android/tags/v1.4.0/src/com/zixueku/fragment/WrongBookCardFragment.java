package com.zixueku.fragment;

import java.util.Set;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.zixueku.R;
import com.zixueku.adapter.WrongBookCardGridViewAdapter;
import com.zixueku.entity.CustomerAnswer;
import com.zixueku.entity.Item;
import com.zixueku.entity.WrongBook;
import com.zixueku.util.App;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年7月21日 下午1:42:39
 */
public class WrongBookCardFragment extends BaseFragment {
	private WrongBook wrongBook;
	private ViewPager viewPager;
	private int rightNum = 0;
	private int wrongNum = 0;

	public WrongBookCardFragment(ViewPager viewPager) {
		super();
		this.viewPager = viewPager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wrongBook = App.getInstance().getWrongBookInfo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wrong_book_card_layout, container, false);
		TextView title = (TextView) view.findViewById(R.id.wrong_book_card_title);
		GridView gridView = (GridView) view.findViewById(R.id.wrong_book_card_grid_view);
		judge();
		gridView.setAdapter(new WrongBookCardGridViewAdapter(mActivity, wrongBook, viewPager));
		title.setText(String.format("正确%d道,错误%d道,共%d道", wrongBook.getRightNum(), wrongBook.getWrongNum(), wrongBook.getTotalNum()));
		return view;
	}

	public void judge() {
		for (Item item : wrongBook.getWrongItemList()) {
			if (item.getModelType() == 4) {
				for (Item child : item.getChildren()) {
					judgeItem(child);
				}
			} else {
				judgeItem(item);
			}
		}
		wrongBook.setRightNum(rightNum);
		wrongBook.setWrongNum(wrongNum);
	}

	private void judgeItem(Item item) {
		Set<CustomerAnswer> customerSet = item.getCustomerAnswer();
		if (customerSet.size() == 0) {
			if (item.getIsWrongbookItemTested() == 1) {
				int completeType = item.getLastTestCompleteType();
				if (completeType == 1) {
					rightNum++;
				} else {
					wrongNum++;
				}
			}
		} else {
			boolean isRight = item.isRight();
			if (isRight) {
				rightNum++;
			} else {
				wrongNum++;
			}
		}
	}
}
