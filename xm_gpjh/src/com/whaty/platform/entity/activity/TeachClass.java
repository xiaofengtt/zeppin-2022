/*
 * TeachClass.java
 *
 * Created on 2004��11��26��, ����2:38
 */

package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * ��ѧ����
 * 
 * @author chenjian
 */
public abstract class TeachClass implements Items {

	private String id;

	private String name;

	private OpenCourse openCourse;

	private String assistanceNote;

	private String assistanceReleaseStatus;

	private String assistanceTitle;

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * ���� name �Ļ�ȡ������
	 * 
	 * @return ���� name ��ֵ��
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * ���� name �����÷�����
	 * 
	 * @param name
	 *            ���� name ����ֵ��
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	public String getAssistanceNote() {
		return assistanceNote;
	}

	public void setAssistanceNote(String assistanceNote) {
		this.assistanceNote = assistanceNote;
	}

	public String getAssistanceReleaseStatus() {
		return assistanceReleaseStatus;
	}

	public void setAssistanceReleaseStatus(String assistanceReleaseStatus) {
		this.assistanceReleaseStatus = assistanceReleaseStatus;
	}

	public String getAssistanceTitle() {
		return assistanceTitle;
	}

	public void setAssistanceTitle(String assistanceTitle) {
		this.assistanceTitle = assistanceTitle;
	}

	/**
	 * ��øý�ѧ���ѧ���б�
	 * 
	 * @param page
	 * @return ѧ���б�
	 */
	public abstract int getStudentsNum();

	public abstract int getStudentsNum(String site_id);

	/**
	 * ��øý�ѧ���ѧ���б�
	 * 
	 * @param page
	 * @return ѧ���б�
	 */
	public abstract List getStudents(Page page);

	public abstract List getStudents(Page page, String site_id);

	/**
	 * ��øý�ѧ��Ľ�ʦ�б�
	 * 
	 * @param page
	 * @return ��ʦ�б�
	 */
	public abstract List getTeachers(Page page);

	/**
	 * Ϊ��ѧ�����ѧ��
	 * 
	 * @param students
	 * @return ���ѧ����
	 */
	public abstract void addStudents(List students) throws PlatformException;

	/**
	 * ɾ���ѧ���ѧ��
	 * 
	 * @param students
	 * @return ɾ���ѧ����
	 */
	public abstract void removeStudents(List students) throws PlatformException;

	/**
	 * Ϊ��ѧ��ѡ���ʦ
	 * 
	 * @param teachers
	 * @return ѡ��Ľ�ʦ��
	 */
	public abstract void addTeachers(List teachers) throws PlatformException;

	/**
	 * ɾ���ѧ��Ľ�ʦ
	 * 
	 * @param teachers
	 * @return ɾ�����
	 */
	public abstract void removeTeachers(List teachers) throws PlatformException;

	public OpenCourse getOpenCourse() {
		return openCourse;
	}

	public void setOpenCourse(OpenCourse openCourse) {
		this.openCourse = openCourse;
	}

	/**
	 * �жϸý�ѧ���Ƿ���ѧ��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract boolean isHasStudents() throws PlatformException;

	/**
	 * �жϸý�ѧ���Ƿ�ѡ���˽�ʦ
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract boolean isHasTeachers() throws PlatformException;

	/**
	 * �жϸý�ѧ�������Ƿ����
	 * 
	 * @return 0Ϊû�У�����0�����
	 */
	public abstract boolean isNameExist() throws PlatformException;

	/**
	 * �õ��ý�ѧ���µĿμ��б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewares() throws PlatformException;

	/**
	 * �õ��ý�ѧ���µĻ�μ��б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveCoursewares() throws PlatformException;

	public abstract int updateAssistance() throws PlatformException;

	public abstract int releaseAssistance(String publisherId,
			String publisherName, String publisherType)
			throws PlatformException;

	public abstract int removeSiteTeachersFromTeachClass(
			String[] siteteacher_ids);

	public abstract void addSiteTeachers(String[] teacherIdSet);

	public abstract List getStudents(Page page , String stuRegNo,String stuName);
	
	public abstract List getStudents(Page page , String stuRegNo,String stuName,String siteId);
	
	public abstract int getStudentsNum(String stuRegNo,String stuName) ;
	
	public abstract int getStudentsNum(String stuRegNo,String stuName,String siteId) ;
}
