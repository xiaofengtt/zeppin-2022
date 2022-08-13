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
	 * ������ѵѧԱ
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
	 * ��ȡ��ѵѧԱ����
	 * 
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getTrainingStudentsNum(List serachProperty, List orderProperty)
			throws PlatformException;

	/**
	 * ������ѵ������
	 * 
	 * @param page
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchTrainingClassManager(Page page, List serachProperty,
			List orderProperty) throws PlatformException;

	/**��ȡ����������ѵ����������
	 * @param serachProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getTrainingClassManagerNum(List serachProperty,
			List orderProperty) throws PlatformException;

}
