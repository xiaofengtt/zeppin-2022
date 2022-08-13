/**
 * 
 */
package com.whaty.platform.entity;

/**
 * @author wq
 * 
 */

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.ChangeMajorStudent;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.util.Page;

public abstract class BasicSiteActivityManage {

	public abstract TestManage creatTestManage();

	/**
	 * ��ѧ��semester_id�п���
	 * 
	 * @param �γ�course�����б�courses
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void openCourses(List courses, String semesterId)
			throws PlatformException;

	/**
	 * ��IDΪsemester_idѧ���п���
	 * 
	 * @param �γ̱������coursesId
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void openCourses(String[] coursesId, String semesterId)
			throws PlatformException;

	/**
	 * ��ѧ��semester_idȡ���
	 * 
	 * @param courseIds
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void unOpenCourses(List courses, String semesterId)
			throws PlatformException;

	/**
	 * ��IDΪsemester_idѧ���п���
	 * 
	 * @param �γ̱������coursesId
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void unOpenCourses(String[] coursesId, String semesterId)
			throws PlatformException;

	/**
	 * �õ�IDΪsemesterIdѧ���µ�����רҵ�����б�
	 * 
	 * @param page
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getOpenCourses(Page page, String semesterId)
			throws PlatformException;

	/**
	 * �õ�IDΪsemesterIdѧ����רҵIDΪmajor_id��רҵ�����б�
	 * 
	 * @param page
	 * @param major_id
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getOpenCourses(Page page, String major_id,
			String semesterId) throws PlatformException;

	public abstract List getOpenCourses(Page page, String major_id,
			String semesterId, String course_id, String course_name)
			throws PlatformException;

	public abstract List getOpenCourses(Page page, String semesterId,
			String major_id, String site_id, String eduType_Id, String grade_Id)
			throws PlatformException;

	public abstract int getOpenCoursesNum(Page page, String semesterId,
			String major_id, String site_id, String eduType_Id, String grade_Id)
			throws PlatformException;

	/**
	 * @param open_course_id
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getOpenCourses(String open_course_id, String semesterId)
			throws PlatformException;

	/**
	 * �жϱ��Ϊcourse_id�Ŀγ��ڱ��ΪsemesterId��ѧ�����Ƿ��Ѿ�����
	 * 
	 * @param course_id
	 * @param semesterId
	 * @return 1Ϊ�ѿ� 0Ϊδ��
	 * @throws PlatformException
	 */
	public abstract int checkCourseIsOpen(String course_id, String semesterId)
			throws PlatformException;

	public abstract String getOpenCourseId(String semesterId, String courseId)
			throws PlatformException;

	/**
	 * ��ݿ���ID�õ���ѧ���б�
	 * 
	 * @param open_course_id
	 * @return ��ѧ�����List
	 * @throws PlatformException
	 */
	public abstract List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException;

	/**
	 * ��ݽ�ѧ��IDteachclass_id��øý�ѧ���ڿν�ʦ�б�
	 * 
	 * @param teachclass_id
	 * @return ��ʦ����List
	 * @throws PlatformException
	 */
	public abstract List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	/**
	 * ��ݽ�ѧ��IDteachclass_id��øý�ѧ�ศ����ʦ�б�
	 * 
	 * @param teachclass_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	/**
	 * ȷ�ϱ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�µ�ѧ����IDΪsemester_idѧ���е�ѡ��
	 * 
	 * @param semester_id
	 * @param site_id
	 *            ������Ϊ�գ���ʾ���н�ѧվ��
	 * @param edu_type_id
	 *            ������Ϊ�գ���ʾ���в�Σ�
	 * @param major_id
	 *            ������Ϊ�գ���ʾ����רҵ��
	 * @param grade_id
	 *            ������Ϊ�գ���ʾ�����꼶��
	 * @throws PlatformException
	 */
	public abstract void confirmElectives(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�µ�ѧ����IDΪsemester_idѧ���е�δȷ�ϵ�ѡ��
	 * 
	 * @param page
	 *            ������Ϊ�գ���ʾ����ҳ��
	 * @param semester_id
	 * @param site_id
	 *            ������Ϊ�գ���ʾ���н�ѧվ��
	 * @param edu_type_id
	 *            ������Ϊ�գ���ʾ���в�Σ�
	 * @param major_id
	 *            ������Ϊ�գ���ʾ����רҵ��
	 * @param grade_id
	 *            ������Ϊ�գ���ʾ�����꼶��
	 * @return elective����List
	 * @throws PlatformException
	 */
	public abstract List getUnConfirmElectives(Page page, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�µ�ѧ����IDΪsemester_idѧ���е�δȷ�ϵ�ѡ����Ŀ
	 * 
	 * @param semester_id
	 * @param site_id������Ϊ�գ���ʾ���н�ѧվ��
	 * @param edu_type_id������Ϊ�գ���ʾ���в�Σ�
	 * @param major_id������Ϊ�գ���ʾ����רҵ��
	 * @param grade_id������Ϊ�գ���ʾ�����꼶��
	 * @return δȷ�ϵ�ѡ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int getUnConfirmElectivesNum(String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * �жϱ��Ϊteacher_id�Ľ�ʦ�Ƿ��Ǳ��Ϊteachclass_id��ѧ����ڿν�ʦ
	 * 
	 * @param teacher_id
	 * @param teachclass_id
	 * @return ����1�ǣ�0����
	 * @throws PlatformException
	 */
	public abstract int checkTeacherByTeachClass(String teacher_id,
			String teachclass_id) throws PlatformException;

	/**
	 * Ϊ���Ϊteachclass_id�Ľ�ѧ������ڿν�ʦ
	 * 
	 * @param teacherIdSet
	 *            ��ʦ�������
	 * @param teachclass_id
	 *            ��ѧ����
	 * @throws PlatformException
	 */
	public abstract void addTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException;

	/**
	 * Ϊ���Ϊteachclass_id�Ľ�ѧ����Ӹ�����ʦ
	 * 
	 * @param teacherIdSet
	 *            ��ʦ�������
	 * @param teachclass_id
	 *            ��ѧ����
	 * @throws PlatformException
	 */
	public abstract void addSiteTeachersToTeachClass(String[] siteteacherIdSet,
			String teachclass_id) throws PlatformException;

	/**
	 * ��ñ��ΪsemesterId��ѧ���б��Ϊmajor_id��רҵ�µĿ�����Ŀ
	 * 
	 * @param major_id
	 *            ������Ϊ�գ���ʾ����רҵ��
	 * @param semesterId
	 * @return ������Ŀ
	 * @throws PlatformException
	 */
	public abstract int getOpenCoursesNum(String major_id, String semesterId)
			throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException;

	/**
	 * ��ñ��Ϊedu_type_id��� ���Ϊmajor_id��רҵ ���Ϊgrade_id���꼶�µĽ�ѧ�ƻ��пγ̵���Ŀ
	 * 
	 * @param major_id������Ϊ�գ���ʾ����רҵ��
	 * @param edu_type_id������Ϊ�գ���ʾ���в�Σ�
	 * @param grade_id������Ϊ�գ���ʾ�����꼶��
	 * @return ��ѧ�ƻ��γ���Ŀ
	 * @throws PlatformException
	 */
	public abstract int getTeachPlanCoursesNum(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	/**
	 * ��ñ��Ϊedu_type_id��� ���Ϊmajor_id��רҵ ���Ϊgrade_id���꼶�µĽ�ѧ�ƻ��пγ̵��б�
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param major_id������Ϊ�գ���ʾ����רҵ��
	 * @param edu_type_id������Ϊ�գ���ʾ���в�Σ�
	 * @param grade_id������Ϊ�գ���ʾ�����꼶��
	 * @return TeachPlanCourse����List
	 * @throws PlatformException
	 */
	public abstract List getTeachPlanCourses(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	/**
	 * ��ñ��Ϊedu_type_id��� ���Ϊmajor_id��רҵ ���Ϊgrade_id���꼶�µĽ�ѧ�ƻ���Ϣ�б�
	 * 
	 * @param major_id������Ϊ�գ���ʾ����רҵ��
	 * @param edu_type_id������Ϊ�գ���ʾ���в�Σ�
	 * @param grade_id������Ϊ�գ���ʾ�����꼶��
	 * @return TeachPlan����List
	 * @throws PlatformException
	 */
	public abstract List getTeachPlan(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	/**
	 * ��ӱ��Ϊedu_type_id��� ���Ϊmajor_id��רҵ ���Ϊgrade_id���꼶�µĽ�ѧ�ƻ�
	 * 
	 * @param major_id
	 * @param edu_type_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void addTeachPlan(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	/**
	 * Ϊ���κ�ΪopenCourseId�Ŀ������һ����Ϊid�����Ϊname�Ľ�ѧ��
	 * 
	 * @param id
	 * @param name
	 * @param openCourseId
	 * @throws PlatformException
	 */
	public abstract void addTeachClass(String id, String name,
			String openCourseId) throws PlatformException;

	/**
	 * ɾ����Ϊid�Ľ�ѧ��
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTeachClass(String id) throws PlatformException;

	/**
	 * �õ����κ�ΪopenCourseId�����µĽ�ѧ����б�
	 * 
	 * @param openCourseId
	 * @return TeachClass����List
	 * @throws PlatformException
	 */
	public abstract List getTeachClasses(String openCourseId)
			throws PlatformException;

	/**
	 * Ϊ���ΪteachClassId�Ľ�ѧ�����ѧ��
	 * 
	 * @param studentIdList
	 *            ��ѧ����List��
	 * @param teachClassId
	 * @throws PlatformException
	 */
	public abstract void addTeachClassStudents(List studentIdList,
			String teachClassId) throws PlatformException;

	/**
	 * Ϊ���ΪteachClassId�Ľ�ѧ��ɾ��ѧ��
	 * 
	 * @param studentIdList��ѧ����List��
	 * @param teachClassId
	 * @throws PlatformException
	 */
	public abstract void removeTeachClassStudents(List studentIdList,
			String teachClassId) throws PlatformException;

	/**
	 * �õ����ΪteachClassId�Ľ�ѧ���µ�ѧ��
	 * 
	 * @param teachClassId
	 * @return Student����List
	 * @throws PlatformException
	 */
	public abstract List getTeachClassStudents(String teachClassId)
			throws PlatformException;

	/**
	 * �õ����ΪSemesterId��ѧ���µĿγ�
	 * 
	 * @param teachClassId
	 * @return Student����List
	 * @throws PlatformException
	 */
	public abstract List getCourseIDList(String semester_id)
			throws PlatformException;

	/**
	 * �����ΪoldTeachClassId�Ľ�ѧ���ѧ���Ƶ����ΪnewTeachClassId��ѧ��
	 * 
	 * @param oldTeachClassId
	 * @param newTeachClassId
	 * @throws PlatformException
	 */
	public abstract void moveTeachClassStudents(String oldTeachClassId,
			String newTeachClassId) throws PlatformException;

	/**
	 * ��ñ��Ϊstudent_idѧ���ڱ��Ϊsemester_idѧ���ڵ�ѡ���б�
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param semester_id
	 * @param student_id
	 * @return Elective����List
	 * @throws PlatformException
	 */
	public abstract List getElectiveByUserId(Page page, String semester_id,
			String student_id) throws PlatformException;

	/**
	 * �����һ����Χ�ڵ�ѧ���ѡ���б�
	 */
	public abstract List getElectives(Page page, String major_id,
			String edu_type_id, String grade_id, String semester_id)
			throws PlatformException;

	public abstract int getElectivesNum(String major_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException;

	/**
	 * �����ĳѧ�ڡ�ĳ�γ��µ�ѡ��ѧ���б�
	 */
	public abstract List getElectiveStudentsByCourseAndSemester(Page page,
			String siteId, String semesterId, String courseId)
			throws PlatformException;

	/**
	 * �����ĳѧ�ڡ�ĳ�γ��µ�ѡ��ѧ����
	 */
	public abstract int getElectiveStudentsByCourseAndSemesterNum(
			String siteId, String semesterId, String courseId)
			throws PlatformException;

	/**
	 * ���תרҵ��ѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getChangeMajorStudentsNum() throws PlatformException;

	/**
	 * ���תרҵ��ѧ���б�
	 * 
	 * @return ChangeMajorStudent����List
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents() throws PlatformException;

	/**
	 * ��ҳ���תרҵ��ѧ���б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents(Page page)
			throws PlatformException;

	/**
	 * �����ѧ����ѧ��ѧ���б� statusChangeTypeΪABORTΪ��ѧ��SUBPENDΪ��ѧ
	 * 
	 * @param statusChangeType
	 * @return StatusChangedStudent����List
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudents(String statusChangeType)
			throws PlatformException;

	/**
	 * ��studentList�е�ѧ�����ת����Ϊnewedu_type_id��� ���Ϊnewmajor_id��רҵ
	 * ���Ϊnewgrade_id���꼶�� ���Ϊnewsite_id��ѧվ��
	 * 
	 * @param studentList
	 * @param newmajor_id
	 * @param newedu_type_id
	 * @param newgrade_id
	 * @param newsite_id
	 * @throws PlatformException
	 */
	public abstract void changeMajorStatus(List studentList,
			String newmajor_id, String newedu_type_id, String newgrade_id,
			String newsite_id) throws PlatformException;

	public abstract int changeMajorApplyStatus(String id, String status)
			throws PlatformException;

	/**
	 * ��studentList�е�ѧ�������ѧ����ѧ�Ĳ���
	 * 
	 * @param statusChangeType
	 *            ABORTΪ��ѧ��SUBPENDΪ��ѧ
	 * @throws PlatformException
	 */
	public abstract void changeStatus(List studentList, String statusChangeType)
			throws PlatformException;

	/**
	 * ��studentList�е�ѧ��ȡ����ѧ����ѧ�Ĳ���
	 * 
	 * @param studentList
	 * @param statusChangeType
	 *            ABORTΪ��ѧ��SUBPENDΪ��ѧ
	 * @throws PlatformException
	 */
	public abstract void cancelChangeStatus(List studentList,
			String statusChangeType) throws PlatformException;

	/**
	 * ����ѱ�ҵ��ѧ���б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getGraduateStudents(Page page, List searchproperty,
	// List orderproperty) throws PlatformException;
	/**
	 * ����ѱ�ҵ��ѧ����
	 * 
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract int getGraduateStudentsNum(List searchproperty) throws
	// PlatformException;
	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone�ı�ҵѧ���б�
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return Student�����б�
	 * @throws PlatformException
	 */
	public abstract List getGraduatedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	public abstract List getGraduatedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone�ı�ҵѧ����Ŀ
	 * 
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return �ѱ�ҵѧ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int getGraduatedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	public abstract int getGraduatedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	/**
	 * ����ѧ�����studentList�л��ѧ�ִ��ڵ���credit�ܹ���ҵ��ѧ��
	 * 
	 * @param studentList
	 *            Student����List
	 * @param credit
	 *            ����
	 * @return Student�����б�
	 * @throws PlatformException
	 */
	public abstract List checkGraduateByCredit(List studentList, String credit)
			throws PlatformException;

	/**
	 * ���studentList��ͨ��major_idרҵ edu_type_id��� grade_id�꼶��ѧ�ƻ��б��޿γ̵�ѧ��
	 * 
	 * @param studentList
	 *            Student����List
	 * @param major_id
	 * @param edu_type_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkGraduateByCompulsory(List studentList,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * ��studentList�е�ѧ�����óɱ�ҵ
	 * 
	 * @param studentList
	 *            Student����List
	 * @return ���óɱ�ҵ��ѧ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int setGraduate(List studentList) throws PlatformException;

	/**
	 * ��studentList�е�ѧ��ȡ���ҵ
	 * 
	 * @param studentList
	 *            Student����List
	 * @return ȡ���ҵ��ѧ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int cancelGraduate(List studentList)
			throws PlatformException;

	/**
	 * �ѱ����user_id�е�ѧ��ע�ᵽ���Ϊsemester_idѧ����
	 * 
	 * @param user_id
	 *            ѧ��������
	 * @param semester_id
	 *            ѧ��ID
	 * @return ע��ѧ�����Ŀ
	 * @throws PlatformException
	 */
	public abstract int regStudents(String[] user_id, String semester_id)
			throws PlatformException;

	/**
	 * �ж�һ��ѧ���Ƿ���ĳ��ѧ���Ѿ�ע��
	 */

	public abstract int isRegStudents(String semester_id, String user_id)
			throws PlatformException;

	/**
	 * ȡ������user_id�е�ѧ���ڱ��Ϊsemester_idѧ���е�ע��
	 * 
	 * @param user_id
	 *            ѧ��������
	 * @param semester_id
	 *            ѧ��ID
	 * @return ȡ��ע���ѧ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int unRegStudents(String[] user_id, String semester_id)
			throws PlatformException;

	/**
	 * ɾ��ѧ��ע����Ϣ���е�ID��register_id�е�ע����Ϣ
	 * 
	 * @param register_id
	 *            ѧ��ע����Ϣ���е�ID
	 * @return ɾ�����Ŀ��
	 * @throws PlatformException
	 */
	public abstract int unRegStudents(String[] register_id)
			throws PlatformException;

	/**
	 * �ɿ���ID��øÿ���γ̵�ѧ��ѡ����Ŀ
	 * 
	 * @param open_course_id
	 * @return ѧ��ѡ����Ŀ
	 * @throws PlatformException
	 */
	public abstract int getElectiveNum(String open_course_id)
			throws PlatformException;

	/**
	 * �жϱ��Ϊstudent_id�Ƿ�ѡ�˿��κ�Ϊopen_course_id�Ŀγ̣���ͨ����ȷ��
	 * 
	 * @param open_course_id
	 * @param student_id
	 * @return 1��ʾѡ���ˣ�0��ʾδѡ
	 * @throws PlatformException
	 */
	public abstract int checkElective(String open_course_id, String student_id)
			throws PlatformException;

	/**
	 * �жϱ��Ϊstudent_id�Ƿ�ѡ�˿��κ�Ϊopen_course_id�Ŀγ̣�����δȷ��
	 * 
	 * @param open_course_id
	 * @param student_id
	 * @return 1��ʾѡ���˵���δȷ�ϣ�0��ʾδѡ����ѡ���˸ÿγ̲���ͨ��ȷ��
	 * @throws PlatformException
	 */
	public abstract int checkPreElective(String open_course_id,
			String student_id) throws PlatformException;

	/**
	 * ɾ����Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�µ�ѧ����semester_idѧ���ڵ�����ѡ��
	 * 
	 * @param semester_id
	 * @param site_id������Ϊ�գ���ʾ���н�ѧվ��
	 * @param edu_type_id������Ϊ�գ���ʾ���в�Σ�
	 * @param major_id������Ϊ�գ���ʾ����רҵ��
	 * @param grade_id������Ϊ�գ���ʾ�����꼶��
	 * @return ɾ�����Ŀ��
	 * @throws PlatformException
	 */
	public abstract int deleteAllElective(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * ��ѧվ��ѡ�η��� Ϊ���Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�µ�ѧ����semester_idѧ����ѡ�񿪿κ���idSet�еĿγ�
	 * 
	 * @param idSet
	 *            ���κ�����
	 * @param semester_id
	 * @param site_id������Ϊ�գ���ʾ���н�ѧվ��
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveCourses(String[] idSet, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * @param idSet
	 * @param semester_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveCourses(String[] idSet, String semester_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * ����ѡ�η��� Ϊ���Ϊstudent_id��ѧ����semester_idѧ����ѡ�񿪿κ���idSet�еĿγ�
	 * 
	 * @param idSet���κ�����
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * ����ȷ��ѡ�η��� �Ա��Ϊstudent_id��ѧ����semester_idѧ����ѡ�񿪿κ���idSet�еĿγ̽���ȷ��
	 * 
	 * @param idSet���κ�����
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void confirmElectiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * ��ע��ѧ��
	 * 
	 * @param semester_id
	 * @param id
	 * @param major_id
	 * @param edutype_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException;

	/**
	 * ��ȡ��ע��ѧ��
	 * 
	 * @param semester_id
	 * @param id
	 * @param major_id
	 * @param edutype_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int unRegStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException;

	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone����ע��ѧ���б�
	 * 
	 * @param page������Ϊ��,��ʾ����ҳ��
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return RegStudent����List
	 * @throws PlatformException
	 */
	public abstract List getRegStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * �����semester_idѧ���ڱ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone����ע��ѧ���б�
	 * 
	 * @param semester_id
	 * @param page������Ϊ�գ�
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return RegStudent����List
	 * @throws PlatformException
	 */
	public abstract List getRegStudents(String semester_id, Page page,
			String id, String reg_no, String name, String id_card,
			String phone, String site_id, String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	/**
	 * ��ñ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone����ע��ѧ����Ŀ
	 * 
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return ע��ѧ����
	 * @throws PlatformException
	 */
	public abstract int getRegStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * �����semester_idѧ���ڱ��Ϊsite_id��ѧվ ���Ϊedu_type_id��� ���Ϊmajor_id��רҵ
	 * ���Ϊgrade_id���꼶�£����Ϊid��ѧ��Ϊreg_no������Ϊname,���֤��Ϊid_card,jϵ�绰Ϊphone����ע��ѧ����Ŀ
	 * 
	 * @param semester_id
	 * @param id������Ϊ�գ�
	 * @param reg_no������Ϊ�գ�
	 * @param name������Ϊ�գ�
	 * @param id_card������Ϊ�գ�
	 * @param phone������Ϊ�գ�
	 * @param site_id������Ϊ�գ�
	 * @param major_id������Ϊ�գ�
	 * @param edu_type_id������Ϊ�գ�
	 * @param grade_id������Ϊ�գ�
	 * @return ע��ѧ����
	 * @throws PlatformException
	 */
	public abstract int getRegStudentsNum(String semester_id, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * �����꼶����꼶�µĲ����רҵ������б�
	 * 
	 * @return GradeEduMajorGroup����List
	 * @throws PlatformException
	 */
	public abstract List getAllGradeEduMajorGroups() throws PlatformException;

	/**
	 * �����꼶����꼶�µĲ����רҵ�������
	 * 
	 * @return �꼶���רҵ�����Ŀ
	 * @throws PlatformException
	 */
	public abstract int getAllGradeEduMajorGroupsNum() throws PlatformException;

	/**
	 * �����꼶����꼶�µ���ע��ѧ��Ĳ����רҵ������б�
	 * 
	 * @return GradeEduMajorGroup����List
	 * @throws PlatformException
	 */
	public abstract List getRegGradeEduMajorGroups() throws PlatformException;

	/**
	 * �����꼶����꼶����ע��ѧ��Ĳ����רҵ�������
	 * 
	 * @return �꼶���רҵ�����Ŀ
	 * @throws PlatformException
	 */
	public abstract int getRegGradeEduMajorGroupsNum() throws PlatformException;

	/**
	 * ���semester_idѧ����major_idרҵ��δ����Ŀγ���Ŀ
	 * 
	 * @param semester_id
	 * @param major_id
	 * @return δ���εĿγ���
	 * @throws PlatformException
	 */
	public abstract int getUnOpenCoursesNum(String semester_id, String major_id)
			throws PlatformException;

	/**
	 * ���semester_idѧ����major_idרҵ��δ����Ŀγ��б�
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param semester_id
	 * @param major_id
	 * @return Course�����б�
	 * @throws PlatformException
	 */
	public abstract List getUnOpenCourses(Page page, String semester_id,
			String major_id) throws PlatformException;

	/**
	 * ��ѯѧ��ѡ����ϢList�����ѧ�ڣ�ѧ��ID��
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param semester_id
	 * @return Elective�����б�
	 * @throws PlatformException
	 */
	public abstract List getElectiveByUserIdAndSemester(Page page,
			String userId, String semesterId) throws PlatformException;

	/**
	 * ��ѯѧ��ѡ����Ϣ����ѧ�ڣ�ѧ��ID��
	 * 
	 * @param page������Ϊ�գ���ʾ����ҳ��
	 * @param semester_id
	 * @return ѡ����
	 * @throws PlatformException
	 */
	public abstract int getElectiveByUserIdAndSemesterNum(String userId,
			String semesterId) throws PlatformException;

	/**
	 * Ϊ���Ϊteachclass_id�Ľ�ѧ��ȡ����ʦ
	 * 
	 * @param teacherIdSet
	 *            ��ʦ�������
	 * @param teachclass_id
	 *            ��ѧ����
	 * @throws PlatformException
	 */

	public abstract int deleteSiteTeachersFromTeachClass(
			String[] siteteacherIdSet, String teachclass_id)
			throws PlatformException;

	public abstract List getCondtionEleNumExecutePlanCourses(Page page,
			String semesterId, String site_id, String major_id,
			String grade_id, String edutype_id, String reg_no)
			throws PlatformException;

	/**
	 * �����ѧ����ѧ��ѧ������ statusChangeTypeΪABORTΪ��ѧ��SUSPENDΪ��ѧ
	 * 
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException;

	/**
	 * ��ҳ�����ѧ����ѧ��ѧ���б� statusChangeTypeΪABORTΪ��ѧ��SUSPENDΪ��ѧ
	 * 
	 * @param page
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudents(Page page,
			String statusChangeType) throws PlatformException;

	/**
	 * ��ȡ��ѧ������ѧ��ѧ���¼��
	 * 
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStatusChangeStudentRecordsNum(String site_id,
			String statusChangeType) throws PlatformException;

	/**
	 * ��ҳ�����ѧ������ѧ�ļ�¼
	 * 
	 * @param page
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudentRecords(Page page,
			String site_id, String statusChangeType) throws PlatformException;

	/**
	 * ���תרҵ��ѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getChangeMajorStudentsNum(String site_id)
			throws PlatformException;

	/**
	 * ���תרҵ��ѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getChangeMajorStudentsNum(String site_id, String reg_no)
			throws PlatformException;

	public abstract int getChangeMajorApplysNum(String site_id)
			throws PlatformException;

	/**
	 * ��ҳ���תרҵ��ѧ���б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents(Page page, String site_id,
			String reg_no) throws PlatformException;

	/**
	 * ��ҳ���תרҵ��ѧ���б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents(Page page, String site_id)
			throws PlatformException;

	public abstract List getChangeMajorApplys(Page page, String site_id)
			throws PlatformException;

	/**
	 * ���ID��ȡתרҵ��ϸ��Ϣ
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ChangeMajorStudent getChangeMajorStudent(
			String changeMajorId) throws PlatformException;

	public abstract int setDegreeStatus(List id, List id1)
			throws PlatformException;

	public abstract List getGraduateStat(String major_id, String edu_type_id,
			String grade_id, String site_id) throws PlatformException;

	public abstract List getDegreeStat(String major_id, String edu_type_id,
			String grade_id, String site_id) throws PlatformException;

	public abstract List getDegreedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	public abstract int getDegreedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	public abstract List getUniteExamScores(Page page, String siteId,
			String gradeId, String eduTypeId, String majorId, String regNo,
			String name) throws PlatformException;

	public abstract int getUniteExamScoreNum(String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name)
			throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String site_id) throws PlatformException;

	public abstract List getOpenCourses(Page page, String semesterId,
			String major_id, String site_id) throws PlatformException;

	public abstract int getElectiveStatNum(String teachclass_id, String site_id)
			throws PlatformException;

	public abstract List getElectiveStat(Page page, String teachclass_id,
			String site_id) throws PlatformException;

	public abstract int getElectiveStatNum(String teachclass_id,
			String site_id, String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract List getElectiveStat(Page page, String teachclass_id,
			String site_id, String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract List getExecutePlanCourses(Page page, String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	public abstract Map getElectiveStudentsByExecutePlanCourses(
			String semester_id, String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	public abstract int getElectiveNum(String teachclass_id, String site_id)
			throws PlatformException;

	public abstract List getElectives(Page page, String teachclass_id,
			String site_id) throws PlatformException;

	public abstract int getElectiveNum(String teachclass_id, String site_id,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract List getElectives(Page page, String teachclass_id,
			String site_id, String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract List getCourseIDHasExperimentList(String semester_id)
			throws PlatformException;
	
	/**
	 * ���ս�ѧվ��רҵ���꼶�������ѡ��,ͬʱ����ѡ��ȷ�ϲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * @param idSet
	 * @param semester_id
	 * @param site_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id) throws PlatformException;

	/**
	 * ���ѧ��ѡ��,ͬʱ����ѡ��ȷ�ϲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * ���ѧ�����ѡ��,δ����ѡ��ȷ�Ϲʲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * @param selectIds
	 * @param allIds
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithoutFee(String[] selectIds,
			String[] allIds, String semester_id, String student_id)
			throws PlatformException;

}
