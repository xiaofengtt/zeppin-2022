/*
 * BasicEntityManager.java
 *
 * Created on 2004��12��6��, ����2:49
 */

package com.whaty.platform.entity.basic;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.Exception.TypeErrorException;
import com.whaty.platform.util.Page;

/**
 * ƽ̨�������Ϣ����б�ӿ�
 * 
 * @author ligang
 */
public interface BasicEntityList {

	/**
	 * ���ѧԺ��
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧԺ��
	 */
	public int getDepsNum(List searchproperty);

	/**
	 * ���ѧԺ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧԺ�б�
	 */
	public List getDeps(Page page, List searchproperty, List orderproperty);

	/**
	 * ���רҵ��
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return רҵ��
	 */
	public int getMajorsNum(List searchproperty);

	/**
	 * ���רҵ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return רҵ�б�
	 */
	public List getMajors(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ò����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �����
	 */
	public int getEduTypesNum(List searchproperty);

	/**
	 * ��ò���б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����б�
	 */
	public List getEduTypes(Page page, List searchproperty, List orderproperty);

	/**
	 * ���ѧϰ��ʽ��
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧϰ��ʽ��
	 */
	public int getStudyTypesNum(List searchproperty);

	/**
	 * ���ѧϰ��ʽ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧϰ��ʽ�б�
	 */
	public List getStudyTypes(Page page, List searchproperty, List orderproperty);

	/**
	 * ����꼶��
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �꼶��
	 */
	public int getGradesNum(List searchproperty);

	/**
	 * ����꼶�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �꼶�б�
	 */
	public List getGrades(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ý�ѧվ��
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ѧվ��
	 */
	public int getSitesNum(List searchproperty);

	/**
	 * ��ý�ѧվ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ѧվ�б�
	 */
	public List getSites(Page page, List searchproperty, List orderproperty);
	/**
	 * ��ý�ѧվ�б�
	 * 

	 * @return ��ѧվ�б�
	 */
	public Map getSitesMap();
	
	/**
	 * �����ҳ��ѧվ�б�
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getShowSites(Page page, List searchproperty, List orderproperty);
	/**
	 * ��ð༶����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �༶����
	 * 
	 */
	public int getClassesNum(List searchproperty);

	/**
	 * ���ȫ���༶�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �༶�б�
	 */
	public List getClasses(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ý�ѧ�ƻ�����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �༶����
	 * 
	 */
	public int getTeachPlansNum(List searchproperty);

	/**
	 * ��ý�ѧ�ƻ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ѧ�ƻ��б�
	 */
	public List getTeachPlans(Page page, List searchproperty, List orderproperty);

	/**
	 * ���ִ�мƻ�����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ִ�мƻ�����
	 * 
	 */
	public int getExecutePlansNum(List searchproperty);

	/**
	 * ���ִ�мƻ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ִ�мƻ��б�
	 */
	public List getExecutePlans(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ��ø��Խ�ѧ�ƻ��Ŀγ̸���
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ�ѧ�ƻ��Ŀγ̸���
	 * 
	 */
	public int getSingleTeachPlanCoursesNum(List searchproperty);

	/**
	 * ��ø��Խ�ѧ�ƻ��Ŀγ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ�ѧ�ƻ��Ŀγ��б�
	 * @throws PlatformException
	 * @throws TypeErrorException
	 */
	public List getSingleTeachPlanCourses(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	/**
	 * ��÷Ǹ��Խ�ѧ�ƻ��Ŀγ̸���
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �Ǹ��Խ�ѧ�ƻ��Ŀγ̸���
	 * 
	 */
	public int getUnSingleTeachPlanCoursesNum(List searchproperty);

	/**
	 * ��÷Ǹ��Խ�ѧ�ƻ��Ŀγ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �Ǹ��Խ�ѧ�ƻ��Ŀγ��б�
	 */
	public List getUnSingleTeachPlanCourses(Page page, List searchproperty,
			List orderproperty);

	public List getSemesterTeachPlanCourses(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ��ý̲���
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �̲���
	 */
	public int getTeachBookNum(List searchproperty);

	/**
	 * ��ý̲��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �̲��б�
	 */
	public List getTeachBooks(Page page, List searchproperty, List orderproperty);

	/**
	 * @param persons
	 * @param siteIds
	 * @param gradeIds
	 * @param majorIds
	 * @param eduTypeIds
	 * @return
	 */
	public String getMobilePhones(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds);

	/**
	 * ��ȡָ���γ̵Ľ̲���
	 * 
	 * @param courseId
	 * @return
	 */
	public int getTeachBooksNumOfCourse(String courseId);

	/**
	 * ��ȡָ���γ̵Ľ̲��б�
	 * 
	 * @param page
	 * @param courseId
	 * @return
	 */
	public List getTeachBooksOfCourse(Page page, String courseId);
	
	/**
	 * ��ȡָ���γ̿���ӵĽ̲���
	 * @param courseId
	 * @return
	 */
	public int getTeachBooksNumToAddOfCourse(String courseId);

	/**
	 * ��ȡָ���γ̿���ӵĽ̲��б�
	 * @param page
	 * @param courseId
	 * @return
	 */
	public List getTeachBooksToAddOfCourse(Page page, String courseId);
	
	/**
	 * ��ݵ�½�ʺŻ���ֻ����
	 * @param loginId
	 * @return
	 */
	public String getMobilePhoneByLoginId(String loginId);
	
}
