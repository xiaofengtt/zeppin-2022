/**
 * 
 */
package com.whaty.platform.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.ChangeMajorStudent;
import com.whaty.platform.entity.activity.DegreeCondition;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.ElectiveScore;
import com.whaty.platform.entity.activity.GraduateCondition;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.activity.TeachClass;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.entity.basic.ExecutePlanCourse;
import com.whaty.platform.entity.basic.TeachPlan;
import com.whaty.platform.entity.basic.TeachPlanCourse;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
/**
 * @author Administrator
 * 
 */
public abstract class BasicActivityManage {

	private ManagerPriv managerPriv;

	public ManagerPriv getManagerPriv() {
		return managerPriv;
	}

	public void setManagerPriv(ManagerPriv managerPriv) {
		this.managerPriv = managerPriv;
	}

	/**
	 * 在学期semester_id中开课
	 * 
	 * @param 课程course对象列表courses
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void openCourses(List courses, String semesterId)
			throws PlatformException;

	/**
	 * 在ID为semester_id学期中开课
	 * 
	 * @param 课程编号数组coursesId
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void openCourses(String[] coursesId, String[] sequenceId,
			String semesterId) throws PlatformException;

	public abstract List getActiveExamSequences() throws PlatformException;

	/**
	 * 在学期semester_id取消开课
	 * 
	 * @param courseIds
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void unOpenCourses(List courses, String semesterId)
			throws PlatformException;

	/**
	 * 在ID为semester_id学期中开课
	 * 
	 * @param 课程编号数组coursesId
	 * @param semesterId
	 * @throws PlatformException
	 */
	public abstract void unOpenCourses(String[] coursesId, String getElectiveNum)
			throws PlatformException;

	/**
	 * 得到ID为semesterId学期下的所有专业开课列表
	 * 
	 * @param page
	 * @param semesterId
	 * @return
	 * @throws PlatformExceptionString
	 *             site_id
	 */
	public abstract List getOpenCourses(Page page, String semesterId)
			throws PlatformException;

	public abstract List getOpenCourses(Page page, String semesterId,
			String major_id, String site_id) throws PlatformException;

	public abstract List getOpenCourses(Page page, String semesterId,
			String major_id, String site_id, String eduType_Id, String grade_Id)
			throws PlatformException;

	/**
	 * 得到semesterId学期 eduTypeId层次 majorId专业 gradeId年级下的执行计划课程列表
	 * 
	 * @param page
	 * @param semesterId
	 * @param majorId
	 * @param eduTypeId
	 * @param gradeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getExecutePlanCourses(Page page, String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	/**
	 * 得到ID为semesterId学期下的开课列表以及给定的站点、年级、专业、层次下的学生选课数
	 * 
	 * @param page
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCondtionEleNumOpenCourses(Page page,
			String semesterId, String site_id, String major_id,
			String grade_id, String edutype_id, String reg_no)
			throws PlatformException;

	/**
	 * 得到ID为semesterId学期下的执行计划课程列表以及给定的站点、年级、专业、层次下的学生选课数
	 * 
	 * @param page
	 * @param semesterId
	 * @param site_id
	 * @param major_id
	 * @param grade_id
	 * @param edutype_id
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract List getCondtionEleNumExecutePlanCourses(Page page,
			String semesterId, String site_id, String major_id,
			String grade_id, String edutype_id, String reg_no)
			throws PlatformException;
	/**
	 * 得到ID为semesterId学期下的选修课程的站点、年级、专业、层次下的学生选课数
	 * 
	 * @param page
	 * @param semesterId
	 * @param site_id
	 * @param major_id
	 * @param grade_id
	 * @param edutype_id
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract List getUnCondtionEleNumExecutePlanCourses(Page page,
			String semesterId, String site_id, String major_id,
			String grade_id, String edutype_id, String reg_no)
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
	 * 得到ID为semesterId学期下专业ID为major_id的专业开课列表
	 * 
	 * @param page
	 * @param major_id
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getOpenCourses(Page page, String major_id,
			String semesterId) throws PlatformException;
	/**
	 * @param coursename 课程名
	 * @param course_id课程ID
	 * @param page
	 * @param major_id专业ID
	 * @param semesterId学期ID
	 * @return 该条件下的学期已开课程信息
	 * @throws PlatformException
	 * zlw
	 */
	public abstract List getOpenCourses(Page page, String major_id,
			String semesterId, String course_id, String course_name)
			throws PlatformException;

	public abstract List getOpenCoursesList(Page page, String major_id,
			String semesterId, String course_id, String course_name,
			String status) throws PlatformException;
	/**
	 * @author zlw
	 * @param major_id 专业 semesterId 学期 ,course_id 开课ID,course_name 课程名
	 * 
	 * */
	public abstract List getOpenCoursesList(Page page, String major_id,
			String semesterId, String course_id, String course_name,
			String status,String CWStatus) throws PlatformException;

	/**
	 * 得到ID为semesterId学期下专业ID为major_id的专业 teachclass 列表
	 * 
	 * @param page
	 * @param major_id
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachClasses(Page page, String major_id,
			String semesterId) throws PlatformException;

	public abstract List getTeachClasses(Page page, String major_id,
			String semesterId, String courseId, String courseNamem,
			String appointStatus) throws PlatformException;

	/**
	 * 判断编号为course_id的课程在编号为semesterId的学期中是否已经开课
	 * 
	 * @param course_id
	 * @param semesterId
	 * @return 1为已开 0为未开
	 * @throws PlatformException
	 */
	public abstract int checkCourseIsOpen(String course_id, String semesterId)
			throws PlatformException;

	/**
	 * 根据开课ID得到教学班列表
	 * 
	 * @param open_course_id
	 * @return 教学班对象List
	 * @throws PlatformException
	 */
	public abstract List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException;

	/**
	 * 根据教学班IDteachclass_id获得该教学班授课教师列表
	 * 
	 * @param teachclass_id
	 * @return 教师对象List
	 * @throws PlatformException
	 */
	public abstract List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException;
	public abstract List getCourseWareByTeachClass(String teachclass_id)
	throws PlatformException;
	
	public abstract List getBooksByTeachClass(String teachclass_id)
	throws PlatformException;
	
	public abstract int getBooksByTeachClassNum(String teachclass_id,String bookId,String coursename)
	throws PlatformException;
	/**@author zlw
	 * @param teachclass_id 教学班id
	 * 开课的时候用来显示已经被指定课程的教材
	 * */
	public abstract List getBooksByTeachClass(Page page,String teachclass_id,String bookId,String teachbook_name)
	throws PlatformException;

	public abstract List getTeachers(String opencourse_id, String teachclass_id)
			throws PlatformException;

	public abstract List getOpenCourse(String teacher_id)
	throws PlatformException;
	/**
	 * @author bob
	 * @param teacher_id
	 * @return 得到老师所教课程的信息。
	 * @throws PlatformException
	 */
	public abstract List getOpenCourse2(String teacher_id)
	throws PlatformException;

	public abstract List getTeachBookByCourse(String course_id)
			throws PlatformException;

	public abstract List getTeachBookByCourse(HttpServletRequest request)
			throws PlatformException;

	public abstract int getTeachBookNumByCourse(HttpServletRequest request)
			throws PlatformException;

	public abstract List getCourseByTeachBook(String bookId)
			throws PlatformException;

	public abstract List getCourseByTeachBook(Page page, String bookId,
			String coursename, String courseno, String major_id)
			throws PlatformException;

	public abstract List getCourseIdByTeachbook(String bookId)
			throws PlatformException;

	public abstract int getCourseNumByTeachBook(String bookId,
			String coursename, String courseno, String major_id)
			throws PlatformException;
	public abstract int getCourseWaresByTeachClass(String courseWareID,
			String courseWareName, String teachClassId, String major_id)
			throws PlatformException;

	public abstract List getAllTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	public abstract int confirmElective(HttpServletRequest request)
			throws PlatformException;

	public abstract int rejectElective(HttpServletRequest request)
			throws PlatformException;

	/**
	 * 确认编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下的学生在ID为semester_id学期中的选课
	 * 
	 * @param semester_id
	 * @param site_id
	 *            （可以为空，表示所有教学站）
	 * @param edu_type_id
	 *            （可以为空，表示所有层次）
	 * @param major_id
	 *            （可以为空，表示所有专业）
	 * @param grade_id
	 *            （可以为空，表示所有年级）
	 * @throws PlatformException
	 */
	public abstract void confirmElectives(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下的学生在ID为semester_id学期中的未确认的选课
	 * 
	 * @param page
	 *            （可以为空，表示不分页）
	 * @param semester_id
	 * @param site_id
	 *            （可以为空，表示所有教学站）
	 * @param edu_type_id
	 *            （可以为空，表示所有层次）
	 * @param major_id
	 *            （可以为空，表示所有专业）
	 * @param grade_id
	 *            （可以为空，表示所有年级）
	 * @return elective对象List
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract List getUnConfirmElectives(Page page, String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException;

	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下的学生在ID为semester_id学期中的未确认的选课数目
	 * 
	 * @param semester_id
	 * @param site_id（可以为空，表示所有教学站）
	 * @param edu_type_id（可以为空，表示所有层次）
	 * @param major_id（可以为空，表示所有专业）
	 * @param grade_id（可以为空，表示所有年级）
	 * @return 未确认的选课数目
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract int getUnConfirmElectivesNum(String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException;

	public abstract List getUnConfirmElectiveStudents(Page page,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id, String reg_no, String student_name)
			throws PlatformException;

	public abstract int getUnConfirmElectiveStudentsNum(String semester_id,
			String site_id, String edu_type_id, String major_id,
			String grade_id, String reg_no, String student_name)
			throws PlatformException;

	/**
	 * 判断编号为teacher_id的教师是否是编号为teachclass_id教学班的授课教师
	 * 
	 * @param teacher_id
	 * @param teachclass_id
	 * @return 返回1是，0不是
	 * @throws PlatformException
	 */
	public abstract int checkTeacherByTeachClass(String teacher_id,
			String teachclass_id) throws PlatformException;

	/**
	 * 为编号为teachclass_id的教学班添加授课教师
	 * 
	 * @param teacherIdSet
	 *            教师编号数组
	 * @param teachclass_id
	 *            教学班编号
	 * @throws PlatformException
	 */

	public abstract int addTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException;

	public abstract int addTeachBookToCourse(String[] teachbookIdSet,
			String Course_id) throws PlatformException;

	public abstract int addCourseToTeachBook(String[] courseIdSet, String bookId)
			throws PlatformException;
	public abstract int addBookToCourse(String courseIdSet, String[] bookId)
	throws PlatformException;
	public abstract int addCourseWareToCourse(String courseIdSet, String[] bookId)
	throws PlatformException;

	/**
	 * 为编号为teachclass_id的教学班取消授课教师
	 * 
	 * @param teacherIdSet
	 *            教师编号数组
	 * @param teachclass_id
	 *            教学班编号
	 * @throws PlatformException
	 */

	public abstract int deleteTeachersToTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException;

	public abstract int deleteTeachBookToCourse(String[] teachbookIdSet,
			String course_id) throws PlatformException;

	public abstract int deleteCourseToTeachBook(String[] courseIdSet,
			String bookId) throws PlatformException;

	/**
	 * 为编号为teachclass_id的教学班删除授课教师
	 * 
	 * @param teacherIdSet
	 *            教师编号数组
	 * @param teachclass_id
	 *            教学班编号
	 * @throws PlatformException
	 */

	public abstract int removeTeachersFromTeachClass(String[] teacherIdSet,
			String teachclass_id) throws PlatformException;

	/**
	 * 为编号为teachclass_id的教学班更新授课教师
	 * 
	 * @param newSlectedIdSet
	 * @param oldSelectedSet
	 * @param teachclass_id
	 * @throws PlatformException
	 */
	public abstract void updateTeachersForTeachClass(String[] newSlectedIdSet,
			String[] oldSelectedSet, String teachclass_id)
			throws PlatformException;

	/**
	 * 获得编号为semesterId的学期中编号为major_id的专业下的开课数目
	 * 
	 * @param major_id
	 *            （可以为空，表示所有专业）
	 * @param semesterId
	 * @return 开课数目
	 * @throws PlatformException
	 */
	public abstract int getOpenCoursesNum(String major_id, String semesterId)
			throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String site_id) throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name) throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String status)
			throws PlatformException;
	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String status,String CWStatus)
			throws PlatformException;

	public abstract int getTeachClassesNum(String major_id, String semesterId)
			throws PlatformException;

	public abstract int getTeachClassesNum(String major_id, String semesterId,
			String courseno, String coursename, String appointStatus)
			throws PlatformException;

	public abstract int getOpenCoursesNum(Page page, String semesterId,
			String major_id, String site_id, String eduType_Id, String grade_Id)
			throws PlatformException;

	/**
	 * 获得编号为edu_type_id层次 编号为major_id的专业 编号为grade_id的年级下的教学计划中课程的数目
	 * 
	 * @param major_id（可以为空，表示所有专业）
	 * @param edu_type_id（可以为空，表示所有层次）
	 * @param grade_id（可以为空，表示所有年级）
	 * @return 教学计划课程数目
	 * @throws PlatformException
	 */
	// public abstract int getTeachPlanCoursesNum(String major_id,String
	// edu_type_id, String grade_id) throws PlatformException;
	/**
	 * 获得编号为edu_type_id层次 编号为major_id的专业 编号为grade_id的年级下的教学计划中课程的列表
	 * 
	 * @param page（可以为空，表示不分页）
	 * @param major_id（可以为空，表示所有专业）
	 * @param edu_type_id（可以为空，表示所有层次）
	 * @param grade_id（可以为空，表示所有年级）
	 * @return TeachPlanCourse对象List
	 * @throws PlatformException
	 */
	// public abstract List getTeachPlanCourses(Page page, String major_id,
	// String edu_type_id, String grade_id) throws PlatformException;
	/**
	 * 获得编号为edu_type_id层次 编号为major_id的专业 编号为grade_id的年级下的教学计划信息列表
	 * 
	 * @param major_id（可以为空，表示所有专业）
	 * @param edu_type_id（可以为空，表示所有层次）
	 * @param grade_id（可以为空，表示所有年级）
	 * @return TeachPlan对象List
	 * @throws PlatformException
	 */
	public abstract List getTeachPlans(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract List getTeachPlanCourseTypes() throws PlatformException;

	/**
	 * 获取id指定的教学计划
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TeachPlan getTeachPlan(String id) throws PlatformException;

	/**
	 * 由专业、层次、年级获取教学计划
	 * 
	 * @param majorId
	 * @param eduTypeId
	 * @param gradeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TeachPlan getTeachPlan(String majorId, String eduTypeId,
			String gradeId) throws PlatformException;

	public abstract int getTeachPlansNum(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	/**
	 * 添加编号为edu_type_id层次 编号为major_id的专业 编号为grade_id的年级下的教学计划
	 * 
	 * @param major_id
	 * @param edu_type_id
	 * @param grade_id
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract int addTeachPlan(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	public abstract int updateTeachPlan(String id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int updateTeachPlanCourse(String id, String credit,
			String courseTime, String type) throws PlatformException;
	/**
	 * @author zlw
	 * @param id1 教学计划ID
	 * @pramati jhcredit计划学分
	 * @param jhcourse_time计划理路学时 
	 * @param jhsjcuorse_time计划上机学时
	 * @param type课程类型 
	 * @param semester学期
	 * */
	public abstract int updateTeachPlanCourse(String id, String credit,
			String courseTime,String sjcoursetime, String type,String semester) throws PlatformException;

	/**
	 * @author yelina
	 * @param id1 教学计划ID
	 * @pramati jhcredit计划学分
	 * @param jhcourse_time计划理路学时 
	 * @param jhsjcuorse_time计划上机学时
	 * @param type课程类型 
	 * @param semester学期
	 * */
	public abstract int addTeachPlanAndCourse(String major_id, String edu_type_id, String grade_id,String course_id,String credit,
			String courseTime,String sjcoursetime, String type,String semester) throws PlatformException;

	
	/**
	 * @author yelina
	 * @param id1 教学计划ID
	 * @pramati jhcredit计划学分
	 * @param jhcourse_time计划理路学时 
	 * @param jhsjcuorse_time计划上机学时
	 * @param type课程类型 
	 * @param semester学期
	 * */
	public abstract int addTeachPlanCourse(String teachPlanId,String course_id,String credit,
			String courseTime,String sjcoursetime, String type,String semester) throws PlatformException;

	
	
	public abstract int updateTeachPlanCourse(String id, String credit,
			String courseTime, String type, String semester)
			throws PlatformException;
/**@author zlw
 * @param id 教学计划ID
 * 删除教学计划**/
	public abstract int deleteTeachPlan(String id) throws PlatformException;
   /**@param courseList 课程列表
    * @param teachPlanId 教学计划ID
    * @author zlw
    * @author 教学计划的内容供交大参考，审批后进行执行计划
    * 
    * */
	public abstract int addTeachPlanCourses(List courseList, String teachPlanId)
			throws PlatformException;

	/**
	 * 为开课号为openCourseId的开课添加一个编号为id，名称为name的教学班
	 * 
	 * @param id
	 * @param name
	 * @param openCourseId
	 * @throws PlatformException
	 */
	public abstract void addTeachClass(String id, String name,
			String openCourseId) throws PlatformException;

	/**
	 * 删除编号为id的教学班
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTeachClass(String id) throws PlatformException;

	/**
	 * 得到开课号为openCourseId开课下的教学班的列表
	 * 
	 * @param openCourseId
	 * @return TeachClass对象List
	 * @throws PlatformException
	 */
	public abstract List getTeachClasses(String openCourseId)
			throws PlatformException;

	/**
	 * 为编号为teachClassId的教学班添加学生
	 * 
	 * @param studentIdList
	 *            （学生编号List）
	 * @param teachClassId
	 * @throws PlatformException
	 */
	public abstract void addTeachClassStudents(List studentIdList,
			String teachClassId) throws PlatformException;

	/**
	 * 为编号为teachClassId的教学班删除学生
	 * 
	 * @param studentIdList（学生编号List）
	 * @param teachClassId
	 * @throws PlatformException
	 */
	public abstract void removeTeachClassStudents(List studentIdList,
			String teachClassId) throws PlatformException;

	/**
	 * 得到编号为teachClassId的教学班下的学生数量
	 * 
	 * @param teachClassId
	 * @return Student对象List
	 * @throws PlatformException
	 */
	public abstract int getTeachClassStudentsNum(String teachClassId)
			throws PlatformException;

	public abstract int getTeachClassStudentsNum(String teachClassId,
			String siteId) throws PlatformException;

	public abstract int getTeachClassStudentsNum(String teachClassId,
			String stuRegNo, String stuName) throws PlatformException;
	
	public abstract int getTeachClassStudentsNum(String teachClassId,
			String stuRegNo, String stuName,String siteId) throws PlatformException;

	/**
	 * 得到编号为teachClassId的教学班下的学生
	 * 
	 * @param teachClassId
	 * @return Student对象List
	 * @throws PlatformException
	 */
	public abstract List getTeachClassStudents(Page page, String teachClassId)
			throws PlatformException;

	/**
	 * 根据教学ID、学生ID和学生性名查询学生信息
	 * 
	 * @param page
	 * @param teachClassId
	 * @param stuRegNo
	 * @param stuName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachClassStudents(Page page, String teachClassId,
			String stuRegNo, String stuName) throws PlatformException;
	
	public abstract List getTeachClassStudents(Page page, String teachClassId,
			String stuRegNo, String stuName,String siteId) throws PlatformException;

	public abstract List getTeachClassStudents(Page page, String teachClassId,
			String siteId) throws PlatformException;

	/**
	 * 将编号为oldTeachClassId的教学班的学生移到编号为newTeachClassId教学班
	 * 
	 * @param oldTeachClassId
	 * @param newTeachClassId
	 * @throws PlatformException
	 */
	public abstract void moveTeachClassStudents(String oldTeachClassId,
			String newTeachClassId) throws PlatformException;

	/**
	 * 获得编号为student_id学生在编号为semester_id学期内的选课列表
	 * 
	 * @param page（可以为空，表示不分页）
	 * @param semester_id
	 * @param student_id
	 * @return Elective对象List
	 * @throws PlatformException
	 */
	public abstract List getElectiveByUserId(Page page, String semester_id,
			String student_id) throws PlatformException;

	/**
	 * 获得编号为student_id学生在编号为semester_id学期内的已经确认的选课列表
	 * 
	 * @param page
	 * @param semester_id
	 * @param student_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getConfirmedElectiveByUserId(Page page,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * 获得在一定范围内的学生的选课列表
	 */
	public abstract List getElectives(Page page, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String semester_id) throws PlatformException;

	/**
	 * 获得在一定范围内的补考学生的列表
	 */
	public abstract List getElectivesExpend(Page page, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String semester_id, String expend_score_student_status,
			String expend_score_status) throws PlatformException;

	/**
	 * 获得在一定范围内的学生的选课成绩列表
	 */
	public abstract List getElectivesCourse(Page page, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String semester_id) throws PlatformException;

	/**
	 * 获得转专业的学生人数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getChangeMajorStudentsNum() throws PlatformException;

	/**
	 * 获得转专业的学生人数
	 * @author zlw
	 * @param site_id 站段ID,reg_no学生注册号
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getChangeMajorStudentsNum(String site_id, String reg_no)
			throws PlatformException;

	/**
	 * 获得转专业的学生列表
	 * 
	 * @return ChangeMajorStudent对象List
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents() throws PlatformException;

	/**
	 * 根据ID获取转专业详细信息
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ChangeMajorStudent getChangeMajorStudent(
			String changeMajorId) throws PlatformException;

	/**
	 * 
	 */
	/**
	 * 分页获得转专业的学生列表
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents(Page page)
			throws PlatformException;

	/**
	 * 
	 */
	/**
	 * 分页获得转专业的学生列表
	 * @author zlw
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChangeMajorStudents(Page page, String site_id,
			String reg_no) throws PlatformException;

	/**
	 * 获得退学或休学的学生人数 statusChangeType为ABORT为退学，SUSPEND为休学
	 * 
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException;

	/**
	 * 获得退学或休学的学生列表 statusChangeType为ABORT为退学，SUSPEND为休学
	 * 
	 * @param statusChangeType
	 * @return StatusChangedStudent对象List
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudents(String statusChangeType)
			throws PlatformException;

	/**
	 * 分页获得退学或休学的学生列表 statusChangeType为ABORT为退学，SUSPEND为休学
	 * 
	 * @param page
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudents(Page page,
			String statusChangeType) throws PlatformException;

	/**
	 * 获取退学或者休学的学生记录数
	 * 
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStatusChangeStudentRecordsNum(String statusChangeType)
			throws PlatformException;

	/**
	 * 获取退学或者休学的学生记录数
	 * @author zlw
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStatusChangeStudentRecordsNum(
			String statusChangeType, String siteId, String regNo)
			throws PlatformException;

	/**
	 * 分页获得退学或者休学的记录
	 * 
	 * @param page
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudentRecords(Page page,
			String statusChangeType) throws PlatformException;

	/**
	 * 分页获得退学或者休学的记录
	 * @author zlw
	 * @param page
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStatusChangeStudentRecords(Page page,
			String statusChangeType, String siteId, String regNo)
			throws PlatformException;

	/**
	 * 把studentList中的学生进行转入编号为newedu_type_id层次 编号为newmajor_id的专业
	 * 编号为newgrade_id的年级下 编号为newsite_id教学站下
	 * zlw
	 * @param studentList
	 * @param newmajor_id
	 * @param newedu_type_id
	 * @param newgrade_id
	 * @param newsite_id
	 * @throws PlatformException
	 */
	public abstract int changeMajorStatus(List studentList, String newmajor_id,
			String newedu_type_id, String newgrade_id, String newsite_id)
			throws PlatformException;

	/**
	 * 对studentList中的学生进行退学或休学的操作
	 * 
	 * @param statusChangeType
	 *            ABORT为退学，SUBPEND为休学
	 * @throws PlatformException
	 */
	public abstract int changeStatus(List studentList, String statusChangeType)
			throws PlatformException;

	/**
	 * 对studentList中的学生取消退学或休学的操作
	 * 
	 * @param studentList
	 * @param statusChangeType
	 *            ABORT为退学，SUSPEND为休学
	 * @throws PlatformException
	 */
	public abstract int cancelChangeStatus(List studentList,
			String statusChangeType) throws PlatformException;

	/**
	 * 根据休学或退学记录ID复学
	 * 
	 * @param rid
	 * @param statusChangeType
	 * @return
	 * @throws PlatformException
	 */
	public abstract int cancelChangeStatus(String rid, String statusChangeType)
			throws PlatformException;

	/**
	 * 获得已毕业的学生列表
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
	 * 获得已毕业的学生数
	 * 
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract int getGraduateStudentsNum(List searchproperty) throws
	// PlatformException;
	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的毕业学生列表
	 * zlw
	 * @param page（可以为空，表示不分页）
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return Student对象列表
	 * @param  flag "biye"表示查询毕业的学生"xuewei"表示查询获得学位的学生
	 * @throws PlatformException
	 */
	public abstract List getGraduatedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id,String flag)
			throws PlatformException;

	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的毕业学生数目
	 * @author zlw
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return 已毕业学生数目
	 * @throws PlatformException
	 */
	public abstract int getGraduatedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id)
			throws PlatformException;
	
	public abstract int getGraduatedStudentsNum2(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id)
			throws PlatformException;
	
	/**
	 * @author 黄海平6.13
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone学位为xuewei的毕业学生数目
	 * 
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @param xuewei (可为空)
	 * @return 已毕业学生数目	 
	 * @throws PlatformException
	 */
	public abstract int getGraduatedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,String kaoqu_id,String shenfen_id,String xuewei)
			throws PlatformException;
	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的毕业学生信息
	 * @author zlw
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return 学生是否可毕业信息信息
	 * @throws PlatformException
	 */
	public abstract List getUnGraduatedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id,
			String grade_id, String graduate_status,String kaoqu_id,String shenfen_id) throws PlatformException;
	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的毕业学生信息
	 * @author zlw
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return 学生是否可毕业信息信息的条数
	 * @throws PlatformException
	 */
	public abstract int getUnGraduatedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String graduate_status,String kaoqu_id,String shenfen_id) throws PlatformException;

	/**
	 * 按照学分审核studentList中获得学分大于等于credit能够毕业的学生
	 * 
	 * @param studentList
	 *            Student对象List
	 * @param credit
	 *            数字
	 * @return Student对象列表
	 * @throws PlatformException
	 */
	public abstract List checkGraduateByCredit(List studentList, String credit)
			throws PlatformException;

	/**
	 * 审核studentList中通过major_id专业 edu_type_id层次 grade_id年级教学计划中必修课程的学生
	 * 
	 * @param studentList
	 *            Student对象List
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
	 * 把studentList中的学生设置成毕业
	 * @author zlw
	 * @param studentList
	 *            Student对象List
	 * @return 设置成毕业的学生数目
	 * @throws PlatformException
	 */
	public abstract int setGraduated(List studentList, String operatorId,
			String operatorName) throws PlatformException;

	/**
	 * 把studentList中的学生取消毕业
	 * zlw
	 * @param studentList
	 *            Student对象List
	 * @return 取消毕业的学生数目
	 * @throws PlatformException
	 */
	public abstract int cancelGraduated(List studentList)
			throws PlatformException;
   /**
    * @author zlw
    * @param idList 学生的id号
    * @param flag "biye"为输入毕业证号
    *             "xuewei"为输入学位证号
    * **/
	public abstract int setGraduateNo(List idList,String flag) throws PlatformException;

	/**
	 * 把编号在user_id中的学生注册到编号为semester_id学期中
	 * 
	 * @param user_id
	 *            学生编号数组
	 * @param semester_id
	 *            学期ID
	 * @return 注册学生的数目
	 * @throws PlatformException
	 */
	public abstract int regStudents(String[] user_id, String semester_id)
			throws PlatformException;

	/**
	 * 判断一个学生是否在某个学期已经注册
	 */

	public abstract int isRegStudents(String semester_id, String user_id)
			throws PlatformException;
	/**
	 * @author shu
	 * @param batch_id
	 * @return 查看该批次是处于什么状态。发布还是取消发布。
	 * @throws PlatformException
	 */
	public abstract String isRelease(String batch_id) throws PlatformException;

	/**
	 * 取消编号在user_id中的学生在编号为semester_id学期中的注册
	 * 
	 * @param user_id
	 *            学生编号数组
	 * @param semester_id
	 *            学期ID
	 * @return 取消注册的学生数目
	 * @throws PlatformException
	 */
	public abstract int unRegStudents(String[] user_id, String semester_id)
			throws PlatformException;

	/**
	 * 删除学期注册信息表中的ID在register_id中的注册信息
	 * zlw
	 * @param register_id
	 *            学期注册信息表中的ID
	 * @return 删除的条目数
	 * @throws PlatformException
	 */
	public abstract int unRegStudents(String[] register_id)
			throws PlatformException;

	/**
	 * 由开课ID获得该开设课程的学生选课数目
	 * 
	 * @param open_course_id
	 * @return 学生选课数目
	 * @throws PlatformException
	 */
	public abstract int getElectiveNum(String teachclass_id)
			throws PlatformException;

	public abstract List getElectives(Page page, String teachclass_id)
			throws PlatformException;

	public abstract int getElectiveNum(String teachclass_id, String student_id,
			String student_name) throws PlatformException;

	public abstract List getElectives(Page page, String teachclass_id,
			String student_id, String student_name) throws PlatformException;

	public abstract Elective getElective(String student_id, String teachclass_id)
			throws PlatformException;

	public abstract int getElectiveNum(String teachclass_id, String site_id)
			throws PlatformException;

	public abstract List getElectives(Page page, String teachclass_id,
			String site_id) throws PlatformException;

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

	public abstract int getElectiveNum(String teachclass_id, String site_id,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract List getElectives1(Page page, String teachclass_id,
			String site_id, String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	/**
	 * 判断编号为student_id是否选了开课号为open_course_id的课程，并通过了确认
	 * 
	 * @param open_course_id
	 * @param student_id
	 * @return 1表示选择了，0表示未选
	 * @throws PlatformException
	 */
	public abstract int checkElective(String teachclass_id, String student_id)
			throws PlatformException;

	public abstract Map getElectiveStudentsByExecutePlanCourses(
			String semester_id, String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	public abstract String getOpenCourseId(String semesterId, String courseId)
			throws PlatformException;

	/**
	 * 得到编号为SemesterId的学期下的课程
	 * 
	 * @param teachClassId
	 * @return Student对象List
	 * @throws PlatformException
	 */
	public abstract List getCourseIDList(String semester_id)
			throws PlatformException;

	public abstract List getCourseIDHasExperimentList(String semester_id)
			throws PlatformException;

	public abstract List getCourseIDList(Page page, String semester_id)
			throws PlatformException;

	/**
	 * 得到选课表下的id
	 * 
	 * @param teachClassId
	 * @return Student对象List
	 * @throws PlatformException
	 */
	public abstract List getelectList(String teachClassId)
			throws PlatformException;

	public abstract List getelectList1(String teachClassId)
			throws PlatformException;

	/**
	 * 得到编号为SemesterId的学期下的课程
	 * 
	 * @param teachClassId
	 * @return Student对象List
	 * @throws PlatformException
	 */
	public abstract List getCourseList(String semester_id)
			throws PlatformException;

	public abstract List getCourseList(Page page, String semester_id)
			throws PlatformException;

	public abstract List getCourseList(Page page, String semester_id,
			String courseId, String courseName) throws PlatformException;
	
	/**
	 * @author shu
	 * @param semester_id
	 * @param courseId
	 * @param courseName
	 * @return 查找出课程发布信息.
	 * @throws PlatformException
	 */
	public abstract List getPublishCourseList(Page page, String semester_id,
			String courseId, String courseName) throws PlatformException;

	public abstract List getCourseList1(Page page, String semester_id,
			String courseId, String courseName) throws PlatformException;

	public abstract int getCourseListNum(String semester_id)
			throws PlatformException;

	public abstract int getCourseListNum(String semester_id, String courseId,
			String courseName) throws PlatformException;
/**
 * @author shu
 * @param semester_id
 * @param courseId
 * @param courseName
 * @return 查找出课程发布信息的数目。
 * @throws PlatformException
 */
	public abstract int getPublishCourseListNum(String semester_id, String courseId,
			String courseName) throws PlatformException;

	public abstract int getCourseListNum1(String semester_id, String courseId,
			String courseName) throws PlatformException;

	public abstract int getCourseIDListNum(String semester_id)
			throws PlatformException;

	/**
	 * /** 判断编号为student_id是否选了开课号为open_course_id的课程，但还未确认
	 * 
	 * @param open_course_id
	 * @param student_id
	 * @return 1表示选择了但还未确认，0表示未选或者选择了该课程并已通过确认
	 * @throws PlatformException
	 */
	public abstract int checkPreElective(String teachclass_id, String student_id)
			throws PlatformException;

	/**
	 * 删除编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下的学生在semester_id学期内的所有选课
	 * 
	 * @param semester_id
	 * @param site_id（可以为空，表示所有教学站）
	 * @param edu_type_id（可以为空，表示所有层次）
	 * @param major_id（可以为空，表示所有专业）
	 * @param grade_id（可以为空，表示所有年级）
	 * @return 删除的条目数
	 * @throws PlatformException
	 */
	public abstract int deleteAllElective(String semester_id, String site_id,
			String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * 教学站批量选课方法 为编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下的学生在semester_id学期内选择开课号在idSet中的课程
	 * 
	 * @param idSet
	 *            开课号数组
	 * @param semester_id
	 * @param site_id（可以为空，表示所有教学站）
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveCourses(String[] idSet, String semester_id,
			String site_id, String edu_type_id, String major_id, String grade_id)
			throws PlatformException;

	/**
	 * 单独选课方法 为编号为student_id的学生在semester_id学期内选择开课号在idSet中的课程
	 * 
	 * @param idSet开课号数组 teacherclass_id
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 * @author zlw
	 */
	public abstract void electiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException;
	/**
	 * 单独设置免修方法 为编号为student_id的学生在semester_id学期内选择开课号在idSet中的课程
	 * 
	 * @param idSet开课号数组 teacherclass_id
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 * @author  zlw
	 */
	public abstract int unelectiveCoursesByUserId(String[] idSet,String[] str,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * 单独确认选课方法 对编号为student_id的学生在semester_id学期内选择开课号在idSet中的课程进行确认
	 * 
	 * @param idSet开课号数组
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void confirmElectiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * 批量注册学生
	 * 
	 * @param semester_id
	 * @param  s注册上学期
	 * @param id
	 * @param major_id
	 * @param edutype_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id,String s)
			throws PlatformException;

	/**
	 * 批量取消注册学生
	 * @author zlw
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
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的已注册学生列表
	 * zlw
	 * @param page（可以为空,表示不分页）
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return RegStudent对象List
	 * @throws PlatformException
	 */
	public abstract List getRegStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * 获得在semester_id学期内编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的已注册学生列表
	 * zlw
	 * @param semester_id
	 * @param page（可以为空）
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return RegStudent对象List
	 * @throws PlatformException
	 */
	public abstract List getRegStudents(String semester_id, Page page,
			String id, String reg_no, String name, String id_card,
			String phone, String site_id, String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	public abstract List getNoElectiveRegStudents(String semester_id,
			Page page, String id, String reg_no, String name, String id_card,
			String phone, String site_id, String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	/**
	 * 获得编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的已注册学生数目
	 * 
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return 注册学生数
	 * @throws PlatformException
	 */
	public abstract int getRegStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * 获得在semester_id学期内编号为site_id教学站 编号为edu_type_id层次 编号为major_id的专业
	 * 编号为grade_id的年级下，编号为id，学号为reg_no，姓名为name,身份证号为id_card,联系电话为phone的已注册学生数目
	 * zlw
	 * @param semester_id
	 * @param id（可以为空）
	 * @param reg_no（可以为空）
	 * @param name（可以为空）
	 * @param id_card（可以为空）
	 * @param phone（可以为空）
	 * @param site_id（可以为空）
	 * @param major_id（可以为空）
	 * @param edu_type_id（可以为空）
	 * @param grade_id（可以为空）
	 * @return 注册学生数
	 * @throws PlatformException
	 */
	public abstract int getRegStudentsNum(String semester_id, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * 按照年级获得年级下的层次与专业的组合列表
	 * @author zlw
	 * @return GradeEduMajorGroup对象List
	 * @throws PlatformException
	 */
	public abstract List getAllGradeEduMajorGroups(String gradeId) throws PlatformException;

	/**
	 * 按照年级获得年级下的层次与专业的组合数量
	 * @author zlw
	 * @return 年级层次专业组合数目
	 * @throws PlatformException
	 */
	public abstract int getAllGradeEduMajorGroupsNum() throws PlatformException;

	/**
	 * 按照年级获得年级下的有注册学生的层次与专业的组合列表
	 * 
	 * @return GradeEduMajorGroup对象List
	 * @throws PlatformException
	 */
	public abstract List getRegGradeEduMajorGroups() throws PlatformException;

	/**
	 * 按照年级获得年级下有注册学生的层次与专业的组合数量
	 * 
	 * @return 年级层次专业组合数目
	 * @throws PlatformException
	 */
	public abstract int getRegGradeEduMajorGroupsNum() throws PlatformException;

	/**
	 * 获得semester_id学期中major_id专业下未开设的课程数目
	 * 
	 * @param semester_id
	 * @param major_id
	 * @return 未开课的课程数
	 * @throws PlatformException
	 */
	public abstract int getUnOpenCoursesNum(String semester_id, String major_id)
			throws PlatformException;

	public abstract int getUnOpenCoursesNum(String semester_id,
			String major_id, String course_id, String course_name)
			throws PlatformException;

	/**
	 * 获得semester_id学期中major_id专业下未开设的课程列表
	 * 
	 * @param page（可以为空，表示不分页）
	 * @param semester_id
	 * @param major_id
	 * @return Course对象列表
	 * @throws PlatformException
	 */
	public abstract List getUnOpenCourses(Page page, String semester_id,
			String major_id) throws PlatformException;

	public abstract List getUnOpenCourses(Page page, String semester_id,
			String major_id, String course_id, String course_name)
			throws PlatformException;

	/**
	 * 建立一个ElectiveScore对象
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ElectiveScore creatElectiveScore() throws PlatformException;

	/**
	 * 建立一个Elective对象
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Elective creatElective() throws PlatformException;

	public abstract GraduateCondition creatGraduateCondition()
			throws PlatformException;

	/**
	 * 建立一个TeachClass对象
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract TeachClass creatTeachClass() throws PlatformException;

	/**
	 * 建立一个OpenCourse对象
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract OpenCourse creatOpenCourse() throws PlatformException;
   /**
    * @author zlw 
    * @param teachPlanId 教学计划ID
    * @param course_id 课程ID
    * @param course_name 课程名
    * @param credit 学分
    * @param course_time 理论课时
    * @param major_id1 专业ID
    * @return 返回当前条件的记录数 
    * */
	public abstract int getTeachPlanCoursesNum(String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId) throws PlatformException;

	public abstract int getUnTeachPlanCoursesNum(String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId) throws PlatformException;
	
  /**
   * @author zlw
   * @param  teachPlanId 教学计划ID,courseId 课程ID,courseName 课程名,credit 学分,courseTime 默认学时,majorId 专业,order,semester学期,gradeId年级,edutypeId层次
   * 
   * */
	public abstract List getTeachPlanCourses(Page page, String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId, String order,String semester,String gradeId,String edutypeId)
			throws PlatformException;
	  /**
	   * @author zlw
	   * @param  teachPlanId 教学计划ID,courseId 课程ID,courseName 课程名,credit 学分,courseTime 默认学时,majorId 专业,order,semester学期,gradeId年级,edutypeId层次
	   * 
	   * */
		public abstract List getTeachPlanCoursesTycj(Page page, String teachPlanId,
				String courseId, String courseName, String credit,
				String courseTime, String majorId, String order,String semester,String gradeId,String edutypeId)
				throws PlatformException;


	/**
	 * 获取指定的教学计划课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TeachPlanCourse getTeachPlanCourse(String id)
			throws PlatformException;

	public abstract List getUnTeachPlanCourses(Page page, String teachPlanId,
			String courseId, String courseName, String credit,
			String courseTime, String majorId) throws PlatformException;

	public abstract int deleteTeachPlanCourse(String id)
			throws PlatformException;
	public abstract int  delTeachPlanCourseByTeachPlan(String TeachPlanId)throws PlatformException;
	public abstract int deleteTeachPlanCourses(String[] id)
			throws PlatformException;

	public abstract int getSingleTeachPlanCoursesNum(String studentId)
			throws PlatformException;

	public abstract int getUnSingleTeachPlanCoursesNum(String studentId,
			String courseId, String courseName, String majorId)
			throws PlatformException;

	public abstract List getSingleTeachPlanCourses(Page page, String studentId)
			throws PlatformException;

	public abstract List getUnSingleTeachPlanCourses(Page page,
			String studentId, String courseId, String courseName, String majorId)
			throws PlatformException;

	public abstract int deleteSingleTeachPlanCourses(String studentId,
			String[] courseId) throws PlatformException;

	public abstract int addSingleTeachPlanCourses(String studentId,
			List courseList) throws PlatformException;

	public abstract List getSemesterTeachPlanCourses(Page page,
			String semesterId, String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	/**
	 * 更新辅导信息
	 * 
	 * @param id
	 * @param assistanceNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateAssistance(String openCourseId,
			String assistanceNote, String assistanceTitle,
			String assistanceReleaseStatus, String publisherId,
			String publisherName, String publisherType)
			throws PlatformException;

	public abstract int deleteAssistance(String openCourseId,
			String assistanceNote, String assistanceTitle,
			String assistanceReleaseStatus, String publisherId,
			String publisherName, String publisherType)
			throws PlatformException;

	public abstract int releaseAssistance(String openCourseId,
			String publisherId, String publisherName, String publisherType)
			throws PlatformException;

	// Graduate set
	public abstract GraduateCondition getGraduateCondition(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract List getGraduateConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int getGraduateConditionsNum(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
  /**@author zlw
   * @param page分页 major_id 专业id,edu_type_id层次id,grade_id年级id
   * @return list 得到未毕业的符合条件年级专业层次，如果未该专业设定毕业条件则一起返回
   * */
	public abstract List getAllGraduateConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
	 /**@author zlw
	   * @param page分页 major_id 专业id,edu_type_id层次id,grade_id年级id
	   * @return 得到相应的记录条数
	   * */
	public abstract int getAllGraduateConditionsNum(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
	 /**@author zlw
	   * @param list condition
	   * @return 成功插入毕业条件的条数
	   * */
	public abstract int addGraduateConditions(List gemList, HashMap condition)
			throws PlatformException;

	public abstract int deleteGraduateConditions(List gemList)
			throws PlatformException;

	public abstract int setGraduateStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException;
/**@author zlw
 * @return 各个年级专业层次的毕业统计信息
 * */
	public abstract List getGraduateStat(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	// Degree set
	public abstract DegreeCondition getDegreeCondition(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract List getDegreeConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int getDegreeConditionsNum(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
/**@author zlw
 * @return 得到有学生的年级专业层次为设置学位条件做准备
 * @param major_id 专业,edu_type_id层次,grade_id年级
 * 
 * **/
	public abstract List getAllDegreeConditions(Page page, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
	/**@author zlw
	 * @return 得到有学生的年级专业层次的信息条数
	 * @param major_id 专业,edu_type_id层次,grade_id年级
	 * 
	 * **/
	public abstract int getAllDegreeConditionsNum(String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int addDegreeConditions(List gemList, HashMap condition)
			throws PlatformException;

	public abstract int deleteDegreeConditions(List gemList)
			throws PlatformException;

	public abstract int setDegreeStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException;
/**@author zlw
 * @return 学位申请统计的具体信息
 * */
	public abstract List getDegreeStat(String major_id, String edu_type_id,
			String grade_id) throws PlatformException;
/**@author zlw
 * @return 已毕业的可以申请学位的学生
 * */
	public abstract List getUnDegreedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id,
			String grade_id, String degree_status) throws PlatformException;

	public abstract int getUnDegreedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String degree_status) throws PlatformException;

	public abstract int setDegreed(List studentList, String operatorId,
			String operatorName) throws PlatformException;
/**
 * @auhtor shu
 * @param studentList
 * @param operatorId
 * @param operatorName
 * @return 取消学位审核
 * @throws PlatformException
 */	
	public abstract int setDegreedUn(List studentList, String operatorId,
			String operatorName) throws PlatformException;
/**@author zlw 
 * @return 返回通过学位审核的学生列表为其输入毕业英语成绩*/
	public abstract List getDegreedStudents(Page page, String id,
			String reg_no, String name, String id_card, String phone,
			String site_id, String major_id, String edu_type_id, String grade_id)
			throws PlatformException;
	/**@author zlw 
	 * @return 返回通过学位审核的学生的记录条数*/
	public abstract int getDegreedStudentsNum(String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;
/**@author zlw
 * @return 成功设置的条数
 * @see 为通过学位审核的学生录入学位英语成绩
 * **/
	public abstract int setDegreeScore(List idList) throws PlatformException;

	public abstract int setDegreeRelease(List idList, List releaseList)
			throws PlatformException;

	/**
	 * 搜索某个学期下的开课和专业列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param semester
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchOpenCourseMajor(Page page, String semester_id)
			throws PlatformException;

	public abstract int searchOpenCourseMajorNum(String semester_id)
			throws PlatformException;

	// 获取教师已授课程
	public abstract List getTeachClassesByTeacherId(Page page, String teacherId)
			throws PlatformException;

	public abstract int getTeachClassesByTeacherIdNum(String teacherId)
			throws PlatformException;

	public abstract int getElectiveStudentNumByOpenCourseId(
			String openCourseId, String status) throws PlatformException;

	// 统考相关
	/**@author zlw
	 * @return 添加统考课程
	 * 
	 * **/
	public abstract int addUniteExamCourse(String courseName, String note)
			throws PlatformException;
	/**@author zlw
	 * @return 成功编辑条数
	 * @see 更新统考课程信息
	 * 
	 * **/
	public abstract int updateUniteExamCourse(String id, String courseName,
			String note) throws PlatformException;
/**@author zlw
 * @return 得到学生统考成绩信息
 * 
 * **/
	public abstract List getUniteExamCourses(Page page, String id,
			String courseName) throws PlatformException;

	public abstract int getUniteExamCourseNum(String courseName)
			throws PlatformException;

	public abstract int delUniteExamCourse(String id) throws PlatformException;
/**
 * @author Administrator
 * 批量删除统考课程
 * **/
	public abstract int delBatchUniteExamCourse(String[] id)
			throws PlatformException;
/**@author zlw
 * @return 得到学生统考成绩信息*/
	public abstract List getUniteExamScores(Page page, String siteId,
			String gradeId, String eduTypeId, String majorId, String regNo,
			String name,String kaoqu_id,String shenfen_id) throws PlatformException;
/**
 * @author shu
 * @param page
 * @param siteId
 * @param gradeId
 * @param eduTypeId
 * @param majorId
 * @param regNo
 * @param name
 * @param kaoqu_id
 * @param shenfen_id
 * @return 通过学生的id来查询出其相关的课程信息。
 * @throws PlatformException
 */
	public abstract List getUniteExamScores3(Page page, String stu_id,String siteId,
			String gradeId, String eduTypeId, String majorId, String regNo,
			String name,String kaoqu_id,String shenfen_id) throws PlatformException;
/**
 * @author shu
 * @param page
 * @param siteId
 * @param gradeId
 * @param eduTypeId
 * @param majorId
 * @param regNo
 * @param name
 * @param kaoqu_id
 * @param shenfen_id
 * @return 得到符合该条件下的人员
 * @throws PlatformException
 */	
	public abstract List getUniteExamScores2(Page page, String siteId,
			String gradeId, String eduTypeId, String majorId, String regNo,
			String name,String kaoqu_id,String shenfen_id) throws PlatformException;
	/**@author zlw
	 * @return 得到学生统考成绩条目*/
	public abstract int getUniteExamScoreNum(String siteId, String gradeId,
			String eduTypeId, String majorId, String regNo, String name,String kaoqu_id,String shenfen_id)
			throws PlatformException;
	/**@author zlw
	 * @return 批量导入统考成绩*/
	public abstract List addBatchUniteExamScore(String src, String filename,
			boolean isUpdate) throws PlatformException;
	
	

	public abstract TestManage creatTestManage();
	
	public abstract TestManage creatExpendTestManage();

	public abstract int getTeacherInteractionStatisticNum(String semesterId)
			throws PlatformException;
	/**
	 * @author zlw
	 * */
	public abstract int getTeacherInteractionStatisticNum(String semesterId,String dep_id)
	throws PlatformException;

	public abstract List getTeacherInteractionStatistic(Page page,
			String semesterId, String orderBy, String order)
			throws PlatformException;
	/**
	 * @author zlw
	 * @param  semesterId 学期id,orderBy 排序,order,dep_id 站点
	 * 
	 * */
	public abstract List getTeacherInteractionStatistic(Page page,
			String semesterId, String orderBy, String order,String dep_id)
			throws PlatformException;

	
	public abstract List getActiveTeachplanCourseList(Page page,
			String semesterId, String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract int getActiveTeachplanCoursesNum(String semesterId,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	/**
	 * 添加执行计划
	 * 
	 * @param title
	 * @param semesterId
	 * @param teachPlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addExecutePlan(String title, String semesterId,
			String teachPlanId) throws PlatformException;

	/**
	 * 由执行计划ID获取执行计划对象
	 * 
	 * @param planId
	 * @return
	 * @throws PlatformException
	 */
	public abstract ExecutePlan getExecutePlan(String planId)
			throws PlatformException;

	/**
	 * 由学期和教学计划获取执行计划对象
	 * 
	 * @param semesterId
	 * @param teachPlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract ExecutePlan getExecutePlan(String semesterId,
			String teachPlanId) throws PlatformException;

	/**
	 * 获取符合条件的执行计划数
	 * 
	 * @param title
	 * @param semesterId
	 * @param teachPlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getExecutePlansNum(String id, String title,
			String semesterId, String teachPlanId) throws PlatformException;

	/**
	 * 获取符合条件的执行计划列表
	 * 
	 * @param page
	 * @param title
	 * @param semesterId
	 * @param teachPlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getExecutePlans(Page page, String id, String title,
			String semesterId, String teachPlanId) throws PlatformException;

	/**
	 * 删除执行计划
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract int deleteExecutePlan(String id) throws PlatformException;

	/**
	 * 修改执行计划
	 * 
	 * @param id
	 * @param title
	 * @param semesterId
	 * @param teachPlanId
	 * @throws PlatformException
	 */
	public abstract int updateExecutePlan(String id, String title,
			String semesterId, String teachPlanId) throws PlatformException;

	public abstract int updateExecutePlanCourseExamSeq(String id, String esId)
			throws PlatformException;

	public abstract int updateExecutePlanCourseExamSeq(
			HttpServletRequest request) throws PlatformException;

	/**
	 * 添加执行计划课程组
	 * 
	 * @param title
	 * @param executePlanId
	 * @param type
	 * @param max
	 * @param min
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addExecutePlanCourseGroup(String title,
			String executePlanId, String type, int max, int min)
			throws PlatformException;

	/**
	 * 获取某个执行计划的执行计划课程组
	 * 
	 * @param executePlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getExecutePlanCourseGroupsByExecutePlanId(
			String executePlanId) throws PlatformException;

	/**
	 * 添加执行计划课程
	 * 
	 * @param groupId
	 * @param teachPlanCourseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addExecutePlanCourse(String groupId,
			String teachPlanCourseId, String esId) throws PlatformException;

	/**
	 * 获得指定的执行计划课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract ExecutePlanCourse getExecutePlanCourse(String id)
			throws PlatformException;

	/**
	 * 获取符合条件的执行计划课程数
	 * 
	 * @param id
	 * @param groupId
	 * @param teachPlanCourseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getExecutePlanCoursesNum(String id, String groupId,
			String teachPlanCourseId) throws PlatformException;

	/**
	 * 获取符合条件的执行计划课程列表
	 * 
	 * @param page
	 * @param groupId
	 * @param teachPlanCourseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getExecutePlanCourses(Page page, String groupId,
			String teachPlanCourseId) throws PlatformException;

	/**
	 * 删除指定的执行计划课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteExecutePlanCourse(String id)
			throws PlatformException;

	/**
	 * 获取某个执行计划下的执行计划课程数
	 * 
	 * @param executePlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getExecutePlanCoursesNumByExecutePlanId(
			String executePlanId) throws PlatformException;

	/**
	 * 获取某个执行计划下的执行计划课程列表
	 * 
	 * @param executePlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getExecutePlanCoursesByExecutePlanId(Page page,
			String executePlanId) throws PlatformException;

	/**
	 * 获取指定执行计划下可供选择添加的教学计划课程数
	 * 
	 * @param executePlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getToBeSelectedTeachPlanCoursesNumOfExecutePlan(
			String executePlanId) throws PlatformException;

	/**
	 * 获取指定执行计划下可供选择添加的教学计划课程列表
	 * 
	 * @param page
	 * @param executePlanId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getToBeSelectedTeachPlanCoursesOfExecutePlan(
			Page page, String executePlanId) throws PlatformException;

	/**
	 * 批量删除执行计划课程
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteExecutePlanCourses(String[] ids)
			throws PlatformException;

	/**
	 * 检测已经给课程添加了的教材的ID
	 * 
	 * @param teachbook_ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract List selectTeachBookId(String[] teachbook_ids,
			String course_id) throws PlatformException;
	public abstract int addExelTeachPlans(List sqlList) throws PlatformException;

	public abstract int getChangeMajorApplysNum() throws PlatformException;

	public abstract int changeMajorApplyStatus(String id, String status)
			throws PlatformException;

	public abstract int confirmMajorApplyStatus(String[] id)
			throws PlatformException;

	public abstract int rejectMajorApplyStatus(String[] id, String[] notes)
			throws PlatformException;

	public abstract List getChangeMajorApplys(Page page)
			throws PlatformException;
	public abstract int deleteCourseBookById(String courseId,String[] ids)
	throws PlatformException;
	public abstract int deleteCourseWareById(String courseId,String[] ids)
	throws PlatformException;

}