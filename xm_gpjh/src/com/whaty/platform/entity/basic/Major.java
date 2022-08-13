/*
 * major.java
 *
 * Created on 2004��9��24��, ����1:36
 */

package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * רҵ����
 * 
 * @author chenjian
 */
public abstract class Major implements com.whaty.platform.Items {

	private String id = "";

	private String name = "";
	
	private String code = ""; //新加入，为了与bjsy2一致;

	private String note = "";

	private String major_alias = "";

	private String major_link = "";

	private boolean status = false;

	private String dep_id = "";

	private String dep_name = "";

	private String recruit_status = "";

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	 * ���� note �Ļ�ȡ������
	 * 
	 * @return ���� note ��ֵ��
	 */
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * ���� note �����÷�����
	 * 
	 * @param note
	 *            ���� note ����ֵ��
	 */
	public void setNote(java.lang.String aNote) {
		note = aNote;
	}

	/**
	 * ���� major_alias �Ļ�ȡ������
	 * 
	 * @return ���� major_alias ��ֵ��
	 */
	public String getMajor_alias() {
		return major_alias;
	}

	/**
	 * ���� major_alias �����÷�����
	 * 
	 * @param major_alias
	 *            ���� major_alias ����ֵ��
	 */
	public void setMajor_alias(String aMajor_alias) {
		major_alias = aMajor_alias;
	}

	/**
	 * ���� major_link �Ļ�ȡ������
	 * 
	 * @return ���� major_link ��ֵ��
	 */
	public String getMajor_link() {
		return major_link;
	}

	/**
	 * ���� major_link �����÷�����
	 * 
	 * @param major_link
	 *            ���� major_link ����ֵ��
	 */
	public void setMajor_link(String aMajor_link) {
		major_link = aMajor_link;
	}

	/**
	 * ���� status �Ļ�ȡ������
	 * 
	 * @return ���� status ��ֵ��
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * ���� status �����÷�����
	 * 
	 * @param status
	 *            ���� status ����ֵ��
	 */
	public void setStatus(boolean aStatus) {
		status = aStatus;
	}

	/**
	 * ���� dep_id �Ļ�ȡ������
	 * 
	 * @return ���� dep_id ��ֵ��
	 */
	public String getDep_id() {
		return dep_id;
	}

	/**
	 * ���� dep_id �����÷�����
	 * 
	 * @param dep_id
	 *            ���� dep_id ����ֵ��
	 */
	public void setDep_id(String aDep_id) {
		dep_id = aDep_id;
	}

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(String aId) {
		id = aId;
	}

	/**
	 * ���� recruit_status �Ļ�ȡ������
	 * 
	 * @return ���� recruit_status ��ֵ��
	 */
	public String getRecruit_status() {
		return recruit_status;
	}

	/**
	 * ���� recruit_status �����÷�����
	 * 
	 * @param recruit_status
	 *            ���� recruit_status ����ֵ��
	 */
	public void setRecruit_status(String aRecruit_status) {
		recruit_status = aRecruit_status;
	}

	/**
	 * ��רҵ����Ƿ����
	 * 
	 * @return 0Ϊ�����ڣ�����0�����
	 */
	protected abstract int isIdExist();

	/**
	 * ��רҵ���Ƿ���ѧ��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasStudents();

	/**
	 * �жϸ�רҵ�����Ƿ��пγ�
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasCourses();

	/**
	 * �жϸ�רҵ���Ƿ��н�ѧ�ƻ�
	 * 
	 * @return 0Ϊû�У�����0����
	 *
	 */
	public abstract int isHasEduplan();
	
	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public abstract List getSorts() throws PlatformException;

}
