package com.zixueku.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 类说明
 * 解決scrollview中嵌套listview时滚动条冲突问题
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-20 下午4:16:42
 */
public class CustomListView extends ListView {

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}



	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
