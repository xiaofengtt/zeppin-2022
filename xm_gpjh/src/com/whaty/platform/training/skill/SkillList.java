/**
 * 
 */
package com.whaty.platform.training.skill;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface SkillList {

	/**
	 * 搜索技能链
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public List searchSkillChain(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int getSkillChainNum(List searchproperty, List orderproperty)
			throws PlatformException;

	/**
	 * 获取技能数
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public int getSkillNum(Page page, List searchproperty, List orderproperty)
			throws PlatformException;

	/**
	 * 搜索技能
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchSkill(Page page, List searchproperty, List orderproperty)
			throws PlatformException;

	/**
	 * @param searchproperty
	 * @param orderproperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public int getStudentSkillNum(List searchproperty, List orderproperty,
			TrainingStudent student) throws PlatformException;

	/**
	 * 搜索某个学生的技能，包括若干状态：审核中，目标，已获得
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public List searchStudentSkill(Page page, List searchproperty,
			List orderproperty, TrainingStudent student)
			throws PlatformException;

}
