package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.CourseType;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.util.Page;
import com.whaty.platform.vote.VoteManage;
import com.whaty.platform.vote.user.VoteManagerPriv;

/**
 * ���������˹���Ա�������Ĺ���
 * 
 * @author chenjian
 * 
 */
public abstract class BasicEduManage {
	/** Creates a new instance of BasicEntityManage */
	public BasicEduManage() {
	}

	/***************************************************************************
	 * ���²���ΪCourse���� *
	 **************************************************************************/
	/**
	 * ���aid�õ��γ̶���
	 * 
	 * @param tid
	 * @return �γ̶���
	 * @throws PlatformException
	 */
	public abstract Course getCourse(String tid) throws PlatformException;

	/**
	 * ���aidɾ��γ̶���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteCourse(String tid) throws PlatformException,
			PlatformException;

	/**
	 * ��ӿγ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws PlatformException,
			PlatformException;

	/**
	 * ���¿γ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updateCourse(String id, String name, String credit,
			String course_time, String major_id, String exam_type,
			String course_type, String teaching_type, String course_status,
			String ref_book, String note, String standard_fee,
			String drift_fee, String text_book, String text_book_price,
			String redundance0, String redundance1, String redundance2,
			String redundance3, String redundance4) throws PlatformException;

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
	 * @throws PlatformException
	 */
	// public abstract List getCourses(Page page,List searchproperty,List
	// orderproperty) throws PlatformException;
	public abstract List getCourses(Page page, String search_type,
			String search_value, String major_id) throws PlatformException;

	public abstract List getCourses(Page page, String courseId,
			String courseName, String majorId, String credit, String courseTime)
			throws PlatformException;

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
	 * @throws PlatformException
	 */

	public abstract List getListCourses(Page page, String coursename,
			String courseno, String major_id) throws PlatformException;

	/**
	 * �γ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return �γ��б�
	 * @throws PlatformException
	 */
	public abstract List getCourses(Page page) throws PlatformException;

	/**
	 * �γ��б�
	 * 
	 * @return �γ��б�
	 * @throws PlatformException
	 */
	public abstract List getCourses() throws PlatformException;

	/**
	 * �γ���
	 * 
	 * @return �γ���
	 * @throws PlatformException
	 */
	public abstract int getCoursesNum() throws PlatformException;

	/**
	 * �γ���
	 * 
	 * @return �γ���
	 * @throws PlatformException
	 */
	public abstract int getListCoursesNum(String coursename, String courseno,
			String major_id) throws PlatformException;

	/**
	 * ���ڵ�ǰ�̲�û�б�ѡ��Ŀγ���
	 * 
	 * @return û�б�ѡ��Ŀγ���
	 * @throws PlatformException
	 */

	public abstract int getNoSelCourseNum(String selCourseId,
			String coursename, String courseno, String major_id)
			throws PlatformException;

	/**
	 * ���ڵ�ǰ�̲�û�б�ѡ��Ŀγ�
	 * 
	 * @return û�б�ѡ��Ŀγ�
	 * @throws PlatformException
	 */
	public abstract List getNoSelCourse(Page page, String selCourseId,
			String coursename, String courseno, String major_id)
			throws PlatformException;

	/**
	 * �γ���
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return �γ���
	 * @throws PlatformException
	 */
	// public abstract int getCoursesNum(List searchproperty) throws
	// PlatformException;
	public abstract int getCoursesNum(String search_type, String search_value,
			String major_id) throws PlatformException;

	public abstract int getCoursesNum(String courseId, String courseName,
			String majorId, String credit, String courseTime)
			throws PlatformException;

	/***************************************************************************
	 * ���²���ΪCourseType���� *
	 **************************************************************************/
	/**
	 * ���aid�õ��γ����Ͷ���
	 * 
	 * @param tid
	 * @return �γ����Ͷ���
	 * @throws PlatformException
	 */
	public abstract CourseType getCourseType(String tid)
			throws PlatformException;

	/**
	 * ���aidɾ��γ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteCourseType(String tid) throws PlatformException,
			PlatformException;

	/**
	 * ��ӿγ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addCourseType(String id, String name)
			throws PlatformException, PlatformException;

	/**
	 * �޸Ŀγ����Ͷ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updateCourseType(String id, String name)
			throws PlatformException;

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
	 * @throws PlatformException
	 */
	public abstract List getCourseTypes(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	/**
	 * �γ������б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return �γ������б�
	 * @throws PlatformException
	 */
	public abstract List getCourseTypes(Page page) throws PlatformException;

	/**
	 * �γ������б�
	 * 
	 * @return �γ������б�
	 * @throws PlatformException
	 */
	public abstract List getCourseTypes() throws PlatformException;

	/**
	 * �γ�������
	 * 
	 * @return �γ�������
	 * @throws PlatformException
	 */
	public abstract int getCourseTypesNum() throws PlatformException;

	/**
	 * �μ�������
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return �μ�������
	 * @throws PlatformException
	 */
	// public abstract int getCwareTypesNum(List searchproperty) throws
	// PlatformException;
	/***************************************************************************
	 * ���²���ΪSemester���� *
	 **************************************************************************/
	/**
	 * ���aid�õ�ѧ�ڶ���
	 * 
	 * @param tid
	 * @return ѧ�ڶ���
	 * @throws PlatformException
	 */
	public abstract Semester getSemester(String tid) throws PlatformException;

	/**
	 * ���aidɾ��ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteSemester(String tid) throws PlatformException,
			PlatformException;

	/**
	 * ���ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int addSemester(String id, String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws PlatformException;

	/**
	 * �޸�ѧ�ڶ���
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updateSemester(String id, String name,
			String start_date, String end_date, String start_elective,
			String end_elective) throws PlatformException;

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
	 * @throws PlatformException
	 */
	// public abstract List getSemesters(Page page,List searchproperty,List
	// orderproperty) throws PlatformException;
	/**
	 * ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���б�
	 * @throws PlatformException
	 */
	public abstract List getSemesters(Page page) throws PlatformException;

	/**
	 * ѧ���б�
	 * 
	 * @return ѧ���б�
	 * @throws PlatformException
	 */
	public abstract List getSemesters() throws PlatformException;

	/**
	 * ��ǰ�ѧ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveSemesters() throws PlatformException;

	/**
	 * ѧ����
	 * 
	 * @return ѧ����
	 * @throws PlatformException
	 */
	public abstract int getSemestersNum() throws PlatformException;

	/**
	 * ѧ����
	 * 
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @return ѧ����
	 * @throws PlatformException
	 */
	// public abstract int getSemestersNum(List searchproperty) throws
	// PlatformException;
	/**
	 * ���ÿγ�����רҵ���
	 * 
	 * @return 1Ϊ�ɹ�
	 * @throws PlatformException
	 */
	public abstract int setCourseMajor(String course_id, String[] major_id,
			String[] edu_type) throws PlatformException;

	/**
	 * ȡ�ÿγ�����רҵ���
	 * 
	 * @return List��ÿ��Ϊһ�����飬�����е�0��Ϊרҵ����1��Ϊ���
	 * @throws PlatformException
	 */
	public abstract List getCourseMajor(String course_id)
			throws PlatformException;

	/**
	 * Ϊ����ѧ��ѡ��
	 * 
	 * @return int��Ϊѡ�γɹ�����
	 * @throws PlatformException
	 */
	public abstract void elective(String Student_id, String[] OpenCourseId)
			throws PlatformException;

	public abstract Course createCourse() throws PlatformException;

	/**
	 * ��ݽ�ѧ��ID��ȡ��ؿμ�
	 * 
	 * @param teachclass_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewares(String teachclass_id)
			throws PlatformException;

	public abstract CoursewareManage creatCoursewareManage();

	public abstract CoursewareManagerPriv getCoursewareManagerPriv();

	public abstract VoteManage creatVoteManage();

	public abstract VoteManagerPriv getVoteManagerPriv();

}
