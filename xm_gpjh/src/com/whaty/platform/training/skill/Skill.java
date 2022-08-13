/**
 * 
 */
package com.whaty.platform.training.skill;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * 该类描述了培训中的技能，学员可以获得该技能
 */
public abstract class Skill implements Items {

	/**
	 * 技能的标识
	 */
	private String id;

	/**
	 * 技能的名称
	 */
	private String name;

	/**
	 * 技能通过后可以发的证书列表（可以没有，也可以具有多个证书）
	 */
	private List certificateList;

	/**
	 * 技能相关说明
	 */
	private String note;

	/**
	 * 技能所属技能链
	 */
	private SkillChain chain;

	/**
	 * 技能的状态
	 */
	private boolean isActive;

	/**
	 * 技能的类型
	 */
	private String type;

	/**
	 * Access method for the id property.
	 * 
	 * @return the current value of the id property
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param aId
	 *            the new value of the id property
	 */
	public void setId(String aId) {
		id = aId;
	}

	/**
	 * Access method for the name property.
	 * 
	 * @return the current value of the name property
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param aName
	 *            the new value of the name property
	 */
	public void setName(String aName) {
		name = aName;
	}

	/**
	 * Access method for the certificateList property.
	 * 
	 * @return the current value of the certificateList property
	 */
	public List getCertificateList() {
		return certificateList;
	}

	/**
	 * Sets the value of the certificateList property.
	 * 
	 * @param aCertificateList
	 *            the new value of the certificateList property
	 */
	public void setCertificateList(List aCertificateList) {
		certificateList = aCertificateList;
	}

	/**
	 * Access method for the note property.
	 * 
	 * @return the current value of the note property
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the value of the note property.
	 * 
	 * @param aNote
	 *            the new value of the note property
	 */
	public void setNote(String aNote) {
		note = aNote;
	}

	/**
	 * Access method for the chain property.
	 * 
	 * @return the current value of the chain property
	 */
	public SkillChain getChain() {
		return chain;
	}

	/**
	 * Sets the value of the chain property.
	 * 
	 * @param aChain
	 *            the new value of the chain property
	 */
	public void setChain(SkillChain aChain) {
		chain = aChain;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 审核学员是否有资格获得该技能（判断是否满足上面课程和先决条件的要求）
	 * 
	 * @param userId
	 * @return boolean
	 */
	public abstract boolean checkStudentSkill(String userId)
			throws PlatformException;

	/**
	 * 获得具有该项技能的学员列表
	 * 
	 * @return List
	 */
	public abstract List getRewardStudents() throws PlatformException;

	/**
	 * 判断某个学生是否获得该技能
	 * 
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean isObtained(TrainingStudent student)
			throws PlatformException;

	/**
	 * 增加需要预先通过的技能
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void addPreSkill(List skillList) throws PlatformException;

	/**
	 * 移除需要预先通过的技能
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void removePreSkill(List skillList)
			throws PlatformException;

	/**
	 * 得到申请本技能需要预先通过的技能数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPreSkillNum() throws PlatformException;

	public abstract int getPreSkillNum(String id, String name)
			throws PlatformException;

	/**
	 * 得到申请本技能需要预先通过的技能
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkillList() throws PlatformException;

	/**
	 * 分页得到申请本技能需要预先通过的技能
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkillList(Page page) throws PlatformException;

	public abstract List getPreSkillList(Page page, String id, String name)
			throws PlatformException;

	/**
	 * 增加需要预先通过的课程
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void addCourse(List courseList) throws PlatformException;

	/**
	 * 移除需要预先通过的课程
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void removeCourse(List courseList) throws PlatformException;

	public abstract void removeStudent(List studentList)
			throws PlatformException;

	/**
	 * 得到申请本技能需要预先通过的课程门数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseNum() throws PlatformException;

	public abstract int getCourseNum(String id, String name)
			throws PlatformException;

	/**
	 * 得到申请本技能需要预先通过的课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseList() throws PlatformException;

	/**
	 * 分页得到申请本技能需要预先通过的课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseList(Page page) throws PlatformException;

	public abstract List getCourseList(Page page, String id, String name)
			throws PlatformException;

	/**
	 * 设为studentIds列表中学生的目标技能
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void target(List studentIds) throws PlatformException;

	/**
	 * 解除studentIds列表中学生的目标技能
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void unTarget(List studentIds) throws PlatformException;

	/**
	 * 授予studentIds列表中学生该技能
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void reward(List studentIds) throws PlatformException;

	/**
	 * 解除授予studentIds列表中学生该技能
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void unRward(List studentIds) throws PlatformException;

	/**
	 * 获取技能中待审核学员的人数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUnCheckedStudentNum() throws PlatformException;

	public abstract int getUnCheckedStudentNum(String id, String name)
			throws PlatformException;

	/**
	 * 获取技能中待审核学员列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedStudentList() throws PlatformException;

	/**
	 * 分页获取技能中待审核学员列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedStudentList(Page page)
			throws PlatformException;

	public abstract List getUnCheckedStudentList(Page page, String id,
			String name) throws PlatformException;

	/**
	 * 获取技能中已审核学员人数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedStudentNum() throws PlatformException;

	public abstract int getCheckedStudentNum(String id, String name)
			throws PlatformException;

	/**
	 * 获取技能中已审核学员列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedStudentList() throws PlatformException;

	/**
	 * 分页获取技能中已审核学员列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedStudentList(Page page)
			throws PlatformException;

	public abstract List getCheckedStudentList(Page page, String id, String name)
			throws PlatformException;
}
