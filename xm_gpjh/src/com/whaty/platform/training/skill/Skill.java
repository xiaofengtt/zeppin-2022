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
 * ������������ѵ�еļ��ܣ�ѧԱ���Ի�øü���
 */
public abstract class Skill implements Items {

	/**
	 * ���ܵı�ʶ
	 */
	private String id;

	/**
	 * ���ܵ�����
	 */
	private String name;

	/**
	 * ����ͨ������Է���֤���б�����û�У�Ҳ���Ծ��ж��֤�飩
	 */
	private List certificateList;

	/**
	 * �������˵��
	 */
	private String note;

	/**
	 * ��������������
	 */
	private SkillChain chain;

	/**
	 * ���ܵ�״̬
	 */
	private boolean isActive;

	/**
	 * ���ܵ�����
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
	 * ���ѧԱ�Ƿ����ʸ��øü��ܣ��ж��Ƿ���������γ̺��Ⱦ�������Ҫ��
	 * 
	 * @param userId
	 * @return boolean
	 */
	public abstract boolean checkStudentSkill(String userId)
			throws PlatformException;

	/**
	 * ��þ��и���ܵ�ѧԱ�б�
	 * 
	 * @return List
	 */
	public abstract List getRewardStudents() throws PlatformException;

	/**
	 * �ж�ĳ��ѧ���Ƿ��øü���
	 * 
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean isObtained(TrainingStudent student)
			throws PlatformException;

	/**
	 * ������ҪԤ��ͨ���ļ���
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void addPreSkill(List skillList) throws PlatformException;

	/**
	 * �Ƴ���ҪԤ��ͨ���ļ���
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void removePreSkill(List skillList)
			throws PlatformException;

	/**
	 * �õ����뱾������ҪԤ��ͨ���ļ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPreSkillNum() throws PlatformException;

	public abstract int getPreSkillNum(String id, String name)
			throws PlatformException;

	/**
	 * �õ����뱾������ҪԤ��ͨ���ļ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkillList() throws PlatformException;

	/**
	 * ��ҳ�õ����뱾������ҪԤ��ͨ���ļ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkillList(Page page) throws PlatformException;

	public abstract List getPreSkillList(Page page, String id, String name)
			throws PlatformException;

	/**
	 * ������ҪԤ��ͨ���Ŀγ�
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void addCourse(List courseList) throws PlatformException;

	/**
	 * �Ƴ���ҪԤ��ͨ���Ŀγ�
	 * 
	 * @param skillList
	 * @throws PlatformException
	 */
	public abstract void removeCourse(List courseList) throws PlatformException;

	public abstract void removeStudent(List studentList)
			throws PlatformException;

	/**
	 * �õ����뱾������ҪԤ��ͨ���Ŀγ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseNum() throws PlatformException;

	public abstract int getCourseNum(String id, String name)
			throws PlatformException;

	/**
	 * �õ����뱾������ҪԤ��ͨ���Ŀγ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseList() throws PlatformException;

	/**
	 * ��ҳ�õ����뱾������ҪԤ��ͨ���Ŀγ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseList(Page page) throws PlatformException;

	public abstract List getCourseList(Page page, String id, String name)
			throws PlatformException;

	/**
	 * ��ΪstudentIds�б���ѧ����Ŀ�꼼��
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void target(List studentIds) throws PlatformException;

	/**
	 * ���studentIds�б���ѧ����Ŀ�꼼��
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void unTarget(List studentIds) throws PlatformException;

	/**
	 * ����studentIds�б���ѧ���ü���
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void reward(List studentIds) throws PlatformException;

	/**
	 * �������studentIds�б���ѧ���ü���
	 * 
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void unRward(List studentIds) throws PlatformException;

	/**
	 * ��ȡ�����д����ѧԱ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUnCheckedStudentNum() throws PlatformException;

	public abstract int getUnCheckedStudentNum(String id, String name)
			throws PlatformException;

	/**
	 * ��ȡ�����д����ѧԱ�б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedStudentList() throws PlatformException;

	/**
	 * ��ҳ��ȡ�����д����ѧԱ�б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedStudentList(Page page)
			throws PlatformException;

	public abstract List getUnCheckedStudentList(Page page, String id,
			String name) throws PlatformException;

	/**
	 * ��ȡ�����������ѧԱ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedStudentNum() throws PlatformException;

	public abstract int getCheckedStudentNum(String id, String name)
			throws PlatformException;

	/**
	 * ��ȡ�����������ѧԱ�б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedStudentList() throws PlatformException;

	/**
	 * ��ҳ��ȡ�����������ѧԱ�б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedStudentList(Page page)
			throws PlatformException;

	public abstract List getCheckedStudentList(Page page, String id, String name)
			throws PlatformException;
}
