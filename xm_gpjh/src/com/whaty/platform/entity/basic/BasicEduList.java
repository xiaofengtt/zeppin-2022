
package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.util.Page;

public  interface BasicEduList {

	/**
	 * ��ÿγ���
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ���
	 */
	public int getCoursesNum(List searchproperty);
	
	/**
	 * ��ÿγ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ��б�
	 */
	public List getCourses(Page page, List searchproperty, List orderproperty);
	
	/**
	 * ��ÿγ�������
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ�������
	 */
	public int getCourseTypesNum(List searchproperty);
	
	/**
	 * ��ÿγ������б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ������б�
	 */
	public List getCourseTypes(Page page, List searchproperty, List orderproperty);
	
	/**
	 * ���ѧ����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧ����
	 */
	public int getSemestersNum(List searchproperty);
	
	/**
	 * ���ѧ���б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧ���б�
	 */
	public List getSemesters(Page page, List searchproperty, List orderproperty);
	
	 /**
     * �õ���ѧ���б�
     * @param page
     * @return ��ѧ���б�
     */
    public List getTeachClasses(Page page, List searchproperty, List orderproperty);

    /**
     * �õ���ѧ����Ŀ
     * @return ��ѧ����Ŀ
     */
    public int getTeachClassesNum(List searchproperty);
}
