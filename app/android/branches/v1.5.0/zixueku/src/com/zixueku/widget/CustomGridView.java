package com.zixueku.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 类说明 设置不滚动 解决ListView/ScrollView中嵌套 GridView 显示不正常的问题（1行半）
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-1-26 下午2:13:02
 */
public class CustomGridView extends GridView {

	public CustomGridView(Context context) {
		super(context);
	}

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置不滚动 解决ListView/ScrollView中嵌套 GridView 显示不正常的问题（1行半）
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
