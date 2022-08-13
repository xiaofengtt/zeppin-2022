/*
 * UserList.java
 *
 * Created on 2005��5��24��, ����10:53
 */

package com.whaty.platform.entity.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * ѧ���ʦ������Ա�б���Ϣ�ӿ�
 * 
 * @author Ligang
 */
public interface BasicUserList {
	/**
	 * ��ý�ʦ����
	 * 
	 * @return ��ʦ����
	 * 
	 */
	public int getTeachersNum(List searchproperty);

	public int getSiteTeachersNum(List searchproperty);

	/**
	 * ��ý�ʦ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ʦ�б�
	 */
	public List getTeachers(Page page, List searchproperty, List orderproperty);

	public List getSiteTeachers(Page page, List searchproperty,
			List orderproperty);
	public List getSiteTeachers2(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ���ѧ�����
	 * 
	 * @return ѧ�����
	 * 
	 */
	public int getStudentsNum(List searchproperty);

	/**
	 * ���ѧ���б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧ���б�
	 */
	public List getStudents(Page page, List searchproperty, List orderproperty);

	/**
	 * ���ѧ���б�
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getStudents(List searchproperty, List orderproperty);
	
	/**
	 * ���ѧ�����
	 * 
	 * @return ѧ�����
	 * 
	 */
	public int getUnRegStudentsNum(List searchproperty);
	/**
	 * ���δע��ѧ���б�
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getUnRegStudents(Page page,List searchproperty, List orderproperty);

	/**
	 * ��ù���Ա����
	 * 
	 * @return ����Ա����
	 * 
	 */
	public int getManagersNum(List searchproperty);

	/**
	 * ��ù���Ա�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����Ա�б�
	 */
	public List getManagers(Page page, List searchproperty, List orderproperty);

	public abstract HashMap getStudentStatByEduType(String siteId,
			String gradeId) throws PlatformException;

	public abstract HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException;

	public abstract void confirmSiteTeachers(String[] selectedIds,String[] notes) throws PlatformException;
	
	public abstract void confirmSiteTeachers(String[] selectedIds) throws PlatformException;
	
	public abstract void rejectSiteTeachers(String[] selectedIds,String[] notes) throws PlatformException;

	public abstract List getRegStudentIds(String semesterId)
			throws PlatformException;

	public abstract List getRegStat() throws PlatformException;
	
	public abstract Student getStudentByRegNo(String regNo);
	
	public boolean isNewStudent(String ssoId);
	
	public abstract String getStudentList(String teacherId);
	
	public abstract Map getTeacherStat(Page page);
	public abstract  List getAppointStudentSiteTeachers(Page page, List searchproperty,
			List orderproperty);
	public abstract  List getAppointStudentSiteTeachers2(Page page, List searchproperty,
			List orderproperty);
	public abstract int getAppointStudentSiteTeachersNum(List searchproperty);
	public abstract int getAppointStudentSiteTeachersNum2(List searchproperty);
}
