/**
 * 
 */
package com.whaty.platform.entity;

/**
 * @author wq
 * 
 */

import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.CourseType;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.util.Page;

public abstract class BasicSiteEduManage {
	/** Creates a new instance of BasicEntityManage */
	public BasicSiteEduManage() {
	}

	/***************************************************************************
	 * ���²���ΪCourse���� *
	 **************************************************************************/
	/**
	 * ���aid�õ��γ̶���
	 * 
	 * @param tid
	 * @return �γ̶���
	 * @throws NoRightException
	 */
	public abstract Course getCourse(String tid) throws NoRightException;

	/**
	 * ���aidɾ��γ̶���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteCourse(String tid) throws NoRightException,
			PlatformException;

	/**
	 * ��ӿγ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws NoRightException,
			PlatformException;

	/**
	 * ���¿γ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws NoRightException;

	/**
	 * �γ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return �γ��б�
	 * @throws NoRightException
	 */
	// public abstract List getCourses(Page page,List searchproperty,List
	// orderproperty) throws NoRightException;
	public abstract List getCourses(Page page, String search_type,
			String search_value, String major_id) throws PlatformException;

	/**
	 * �γ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return �γ��б�
	 * @throws NoRightException
	 */
	public abstract List getCourses(Page page) throws NoRightException;

	/**
	 * �γ��б�
	 * 
	 * @return �γ��б�
	 * @throws NoRightException
	 */
	public abstract List getCourses() throws NoRightException;

	/**
	 * �γ���
	 * 
	 * @return �γ���
	 * @throws NoRightException
	 */
	public abstract int getCoursesNum() throws NoRightException;

	/**
	 * �γ���
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return �γ���
	 * @throws NoRightException
	 */
	// public abstract int getCoursesNum(List searchproperty) throws
	// NoRightException;
	public abstract int getCoursesNum(String search_type, String search_value,
			String major_id) throws PlatformException;

	/***************************************************************************
	 * ���²���ΪCourseType���� *
	 **************************************************************************/
	/**
	 * ���aid�õ��γ����Ͷ���
	 * 
	 * @param tid
	 * @return �γ����Ͷ���
	 * @throws NoRightException
	 */
	public abstract CourseType getCourseType(String tid)
			throws NoRightException;

	/**
	 * ���aidɾ��γ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteCourseType(String tid) throws NoRightException,
			PlatformException;

	/**
	 * ��ӿγ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addCourseType(String id, String name)
			throws NoRightException, PlatformException;

	/**
	 * �޸Ŀγ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateCourseType(String id, String name)
			throws NoRightException;

	/**
	 * �γ������б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return �γ������б�
	 * @throws NoRightException
	 */
	public abstract List getCourseTypes(Page page, List searchproperty,
			List orderproperty) throws NoRightException;

	/**
	 * �γ������б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return �γ������б�
	 * @throws NoRightException
	 */
	public abstract List getCourseTypes(Page page) throws NoRightException;

	/**
	 * �γ������б�
	 * 
	 * @return �γ������б�
	 * @throws NoRightException
	 */
	public abstract List getCourseTypes() throws NoRightException;

	/**
	 * �γ�������
	 * 
	 * @return �γ�������
	 * @throws NoRightException
	 */
	public abstract int getCourseTypesNum() throws NoRightException;

	/**
	 * �μ�������
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return �μ�������
	 * @throws NoRightException
	 */
	// public abstract int getCwareTypesNum(List searchproperty) throws
	// NoRightException;
	/***************************************************************************
	 * ���²���ΪSemester���� *
	 **************************************************************************/
	/**
	 * ���aid�õ�ѧ�ڶ���
	 * 
	 * @param tid
	 * @return ѧ�ڶ���
	 * @throws NoRightException
	 */
	public abstract Semester getSemester(String tid) throws NoRightException;

	/**
	 * ���aidɾ��ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteSemester(String tid) throws NoRightException,
			PlatformException;

	/**
	 * ���ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws NoRightException;

	/**
	 * �޸�ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateSemester(String id, String name,
			String start_date, String end_date, String start_elective,
			String end_elective) throws NoRightException;

	/**
	 * ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	// public abstract List getSemesters(Page page,List searchproperty,List
	// orderproperty) throws NoRightException;
	/**
	 * ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	public abstract List getSemesters(Page page) throws NoRightException;

	/**
	 * ѧ���б�
	 * 
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	public abstract List getSemesters() throws NoRightException;

	/**
	 * ѧ����
	 * 
	 * @return ѧ����
	 * @throws NoRightException
	 */
	public abstract int getSemestersNum() throws NoRightException;

	/**
	 * ѧ����
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return ѧ����
	 * @throws NoRightException
	 */
	// public abstract int getSemestersNum(List searchproperty) throws
	// NoRightException;

	/**
	 * ���ÿγ�����רҵ���
	 * 
	 * @return 1Ϊ�ɹ�
	 * @throws NoRightException
	 */
	public abstract int setCourseMajor(String course_id, String[] major_id,
			String[] edu_type) throws NoRightException;

	/**
	 * ȡ�ÿγ�����רҵ���
	 * 
	 * @return List��ÿ��Ϊһ�����飬�����е�0��Ϊרҵ����1��Ϊ���
	 * @throws NoRightException
	 */
	public abstract List getCourseMajor(String course_id)
			throws NoRightException;

	/**
	 * Ϊ����ѧ��ѡ��
	 * 
	 * @return int��Ϊѡ�γɹ�����
	 * @throws NoRightException
	 */
	public abstract void elective(String Student_id, String[] OpenCourseId)
			throws NoRightException;
}
