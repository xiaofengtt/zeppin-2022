/**
 * 
 */
package com.whaty.platform.training.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingBasicActivity {

	/**
	 * �������������ѵ��
	 * 
	 * @param classIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException;

	/**
	 * �������������ѵ�γ�
	 * 
	 * @param courseIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException;

	/**
	 * �������������ѵ�γ����
	 * 
	 * @param typeIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException;

	/**
	 * �������������ѵ����
	 * 
	 * @param skillIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkill(List skillIds, boolean flag)
			throws PlatformException;

	/**
	 * �����������������
	 * @param skillChainIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException;

	/**
	 * �������������ѵѧԱ
	 * 
	 * @param studentIdList
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingStudent(List studentIdList, boolean flag)
			throws PlatformException;

}
