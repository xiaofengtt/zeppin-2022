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
	 * 激活或者锁定培训班
	 * 
	 * @param classIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定培训课程
	 * 
	 * @param courseIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定培训课程类别
	 * 
	 * @param typeIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定培训技能
	 * 
	 * @param skillIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkill(List skillIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定技能链
	 * @param skillChainIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定培训学员
	 * 
	 * @param studentIdList
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingStudent(List studentIdList, boolean flag)
			throws PlatformException;

}
