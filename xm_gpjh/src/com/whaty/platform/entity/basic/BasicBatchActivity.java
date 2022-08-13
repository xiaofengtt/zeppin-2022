package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public interface BasicBatchActivity {

	/**
	 * ������ӿγ�
	 * 
	 * @param courseList
	 * @param major
	 * @throws PlatformException
	 */
	public void courseAddBatch(List courseList, List major)
			throws PlatformException;

	/**
	 * ������ӿγ�
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public void courseAddBatch(List courseList) throws PlatformException;
	
	public void bookAddBatch(String srcFile);

}
