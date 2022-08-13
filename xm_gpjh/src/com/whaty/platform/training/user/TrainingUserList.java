/**
 * 
 */
package com.whaty.platform.training.user;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface TrainingUserList {

	/**
	 * 搜索培训学员
	 * 
	 * @param page
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchTrainingStudents(Page page, List serachProperty,
			List orderProperty) throws PlatformException;

	/**
	 * 获取培训学员人数
	 * 
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getTrainingStudentsNum(List serachProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 搜索培训班主管
	 * 
	 * @param page
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchTrainingClassManager(Page page, List serachProperty,
			List orderProperty) throws PlatformException;

	/**获取符合条件培训班主管人数
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getTrainingClassManagerNum(List serachProperty,
			List orderProperty) throws PlatformException;

}
