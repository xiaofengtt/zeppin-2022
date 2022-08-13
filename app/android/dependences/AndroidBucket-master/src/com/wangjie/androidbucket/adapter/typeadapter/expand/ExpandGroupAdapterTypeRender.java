package com.wangjie.androidbucket.adapter.typeadapter.expand;

import android.view.View;

/**
 * 用于对不同类型item的group数据到UI的渲染
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/14/14.
 */
public interface ExpandGroupAdapterTypeRender {

    /**
     * 返回一个item的convertView，也就是BaseAdapter中getView方法中返回的convertView
     *
     * @return
     */
    View getConvertView();

    /**
     * 填充item中各个控件的事件，比如按钮点击事件等
     */
    void fitEvents();

    /**
     * 对指定position的item进行数据的适配
     *
     * @param groupPosition
     */
    void fitDatas(int groupPosition);


}
