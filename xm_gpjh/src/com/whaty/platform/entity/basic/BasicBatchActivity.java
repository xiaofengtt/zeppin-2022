package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public interface BasicBatchActivity {

	/**
	 * 批量添加课程
	 * 
	 * @param courseList
	 * @param major
	 * @throws PlatformException
	 */
	public void courseAddBatch(List courseList, List major)
			throws PlatformException;

	/**
	 * 批量添加课程
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public void courseAddBatch(List courseList) throws PlatformException;
	
	public void bookAddBatch(String srcFile);

}
