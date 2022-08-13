package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * 
 * @author zy
 *
 */
public abstract class CourseItemManage {

	/**
	 * 给选中的课程设置栏目是否显示。
	 * @param item
	 * @param status
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateCourseItemStatus(String item, String status,
			String id) throws PlatformException;
	
	/**
	 * 得到课程栏目显示列表
	 * @param id
	 * @return
	 */
	public abstract List getTheachItem(String id);	
}
