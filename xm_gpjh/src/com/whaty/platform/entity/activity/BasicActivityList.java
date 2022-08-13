/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface BasicActivityList {

	/**
	 * 按照条件搜索选课信息，返回Elective列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchElective(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException;

	public int searchElectiveNum(List searchproperty, Student student)
			throws PlatformException;

	public List searchElectiveStat(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchElectiveStatNum(List searchproperty)
			throws PlatformException;

	public List searchElective(Page page, List searchproperty,
			List orderproperty, Student student, boolean confirmStatus)
			throws PlatformException;

	public int searchElectiveNum(List searchproperty, Student student,
			boolean confirmStatus) throws PlatformException;

	public List searchElectiveStudents(Page page, List searchproperty,
			List orderproperty, List searchproperty1, boolean confirmStatus)
			throws PlatformException;

	public int searchElectiveStudentsNum(List searchproperty,
			List searchproperty1, boolean confirmStatus)
			throws PlatformException;

	/**
	 * 按照条件搜索某些教学班下的Elective列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param teachClass
	 * @return
	 * @throws PlatformException
	 */
	public List searchElective(Page page, List searchproperty,
			List orderproperty, List teachClasses) throws PlatformException;

	public int searchElectiveNum(List searchproperty, List teachClasses)
			throws PlatformException;

	/**
	 * 按照条件搜索某些教学班下的选课成绩信息，返回ElectiveScore列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchElectiveScore(Page page, List searchproperty,
			List orderproperty, List teachClasses) throws PlatformException;

	public int searchElectiveScoreNum(List searchproperty, List teachClasses)
			throws PlatformException;

	/**
	 * 按照条件搜索某个学生的选课成绩信息，返回ElectiveScore列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public List searchElectiveScore(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException;

	public int searchElectiveScoreNum(List searchproperty, Student student)
			throws PlatformException;

	/**
	 * 搜索某个学生未确认的选课列表
	 * 
	 * @param page
	 * @param studentId
	 * @param semesterId
	 * @param course_id
	 * @param course_name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnConfirmEleCourses(Page page, String studentId,
			String semesterId, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 按照条件搜索某些教学班下的选课其他成绩信息，返回ElectiveScore列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchElectiveOtherScore(Page page, List searchproperty,
			List orderproperty, List teachClasses) throws PlatformException;

	public int searchElectiveOtherScoreNum(List searchproperty,
			List teachClasses) throws PlatformException;

	/**
	 * 按照条件搜索某个学生的选课的其他成绩信息，返回ElectiveScore列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public List searchElectiveOtherScore(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException;

	public int searchElectiveOtherScoreNum(List searchproperty, Student student)
			throws PlatformException;

	/**
	 * 按照条件搜索开课信息，返回OpenCourse列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchOpenCourseNum(List searchproperty)
			throws PlatformException;

	public int searchOpenCourseNum(List searchproperty, String site_id)
			throws PlatformException;

	/**
	 * 按照条件搜索开课信息以及所开课程的选课人数
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchOpenCourseWithEleNum(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public List searchOpenCourseWithEleNum(Page page, List searchproperty,
			List orderproperty, String site_id) throws PlatformException;

	public List searchOpenCourses(Page page, List searchproperty,
			List orderproperty, String semester_id) throws PlatformException;
 
	public int searchOpenCoursesNum(Page page, List searchproperty,
			List orderproperty, String semester_id) throws PlatformException;
	/**
	 * 搜索某个学期下的开课列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param semester
	 * @return
	 * @throws PlatformException
	 */
	public List searchOpenCourse(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException;

	public int searchOpenCourseNum(List searchproperty, Semester semester)
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
	public List searchOpenCourseMajor(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchOpenCourseMajorNum(List searchproperty, List orderproperty)
			throws PlatformException;

	public Map getElectiveStudentsByExecutePlanCourses(String semesterId,
			String majorId, String eduTypeId, String gradeId)
			throws PlatformException;

	public List getOpenCoursesInExecutePlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException;

	public List getOpenCoursesNotInExecutePlan(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId, String studentId,
			List searchProperty, List orderProperty) throws PlatformException;

	/**
	 * 按照条件搜索openCourse课程下的教学班信息，返回教学班列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param openCourse
	 * @return
	 * @throws PlatformException
	 */
	public List searchTeachClass(Page page, List searchproperty,
			List orderproperty, OpenCourse openCourse) throws PlatformException;

	public int searchTeachClassNum(List searchproperty, OpenCourse openCourse)
			throws PlatformException;

	/**
	 * 按照条件搜索teacher教授的教学班信息，返回教学班列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param teacher
	 * @return
	 * @throws PlatformException
	 */
	public List searchTeachClass(Page page, List searchproperty,
			List orderproperty, Teacher teacher) throws PlatformException;

	public int searchTeachClassNum(List searchproperty, Teacher teacher)
			throws PlatformException;

	/**
	 * 按照条件搜索semester学期下未注册的学生
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param semester
	 * @return
	 * @throws PlatformException
	 */
	public List searchUnRegStudent(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException;

	public int searchUnRegStudentNum(List searchproperty, Semester semester)
			throws PlatformException;

	/**
	 * 按照条件搜索semester学期下已注册的学生
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @param semester
	 * @return
	 * @throws PlatformException
	 */
	public List searchRegStudent(Page page, List searchproperty,
			List orderproperty, Semester semester) throws PlatformException;

	public int searchRegStudentNum(List searchproperty, Semester semester)
			throws PlatformException;

	public List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	public List getTeachers(String opencourse_id, String teachclass_id)
			throws PlatformException;

	public List getAllTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	public List searchRegSemester(Page page, Student student)
			throws PlatformException;

	public List searchGraduateConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchGraduateConditionsNum(List searchproperty)
			throws PlatformException;

	public List searchAllGraduateConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchAllGraduateConditionsNum(List searchproperty)
			throws PlatformException;

	public List searchDegreeConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchDegreeConditionsNum(List searchproperty)
			throws PlatformException;

	public List searchAllDegreeConditions(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public int searchAllDegreeConditionsNum(List searchproperty)
			throws PlatformException;

	public int searchRegSemesterNum(Student student) throws PlatformException;

	public int addElectives(String[] opencourse_ids, String semester_id,
			Student student);

	public List getGraduateStat(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public List getDegreeStat(Page page, List searchproperty, List orderproperty)
			throws PlatformException;

	// 获得课程的选课学生数
	public int getElectiveStudentNumByOpenCourseId(List searchProperty)
			throws PlatformException;

	// 获得统考课程
	public List getUniteExamCoursesByName(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public int getUniteExamCourseNumByName(List searchProperty)
			throws PlatformException;

	// 获得统考成绩信息
	public List getUniteExamScores(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public int getUniteExamScoreNum(List searchProperty)
			throws PlatformException;

	// 添加教学班对应的课件
	public int AddTeachClassCware(TeachClass teachClass, Courseware courseware)
			throws PlatformException;

	// 批量录入统考成绩
	public List addBatchUniteExamScore(String src, String filename,
			boolean isUpdate) throws PlatformException;

	/**
	 * 获得在某学期、某课程下的选课学生列表
	 */
	public List getElectiveStudentsByCourseAndSemester(Page page,
			String siteId, String semesterId, String courseId)
			throws PlatformException;

	/**
	 * 获得在某学期、某课程下的选课学生数
	 */
	public int getElectiveStudentsByCourseAndSemesterNum(String siteId,
			String semesterId, String courseId) throws PlatformException;

	/**
	 * 查询学生选课信息List（根据学期，学生ID）
	 */
	public List getElectiveByUserIdAndSemester(Page page, List searchproperty,
			List orderproperty, Student student) throws PlatformException;

	/**
	 * 查询学生选课信息数（根据学期，学生ID）
	 */
	public int getElectiveByUserIdAndSemesterNum(List searchproperty,
			List orderproperty, Student student) throws PlatformException;

	/**
	 * 得到老师的交互统计信息
	 * 
	 * @param page
	 * @param semesterId
	 * @param orderBy
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order) throws PlatformException;

	public List getTeacherInteractionStatistic(Page page, String semesterId,
			String orderBy, String order,String dep_id) throws PlatformException;

	public int getTeacherInteractionStatisticNum(String semesterId)
			throws PlatformException;
	public int getTeacherInteractionStatisticNum(String semesterId,String dep_id)
	throws PlatformException;

	/**
	 * 得到课程所属教材
	 * 
	 * @param courseId
	 * @return List
	 * @throws PlatformException
	 */
	public List getTeachBookByCourse(String courseId) throws PlatformException;

	public int getTeachBookNumByCourse(List searchProperties)
			throws PlatformException;

	public List getCourseByTeachBook(String bookId);

	public List getActiveTeachplanCourseList(Page page, String semesterId,
			String gradeId, String eduTypeId, String majorId);

	public int getActiveTeachplanCoursesNum(String semesterId, String gradeId,
			String eduTypeId, String majorId);

	/**
	 * 返回符合条件的执行计划数
	 * 
	 * @param searchProperty
	 * @return
	 */
	public int getExecutePlansNum(List searchProperty);

	/**
	 * 获取符合条件的执行计划列表
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getExecutePlans(Page page, List searchProperty,
			List orderProperty);

	/**
	 * 获取符合条件的执行计划课程组
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getExecutePlanCourseGroups(Page page, List searchProperty,
			List orderProperty);

	/**
	 * 获取符合条件的执行计划课程数
	 * 
	 * @param searchProperty
	 * @return
	 */
	public int getExecutePlanCoursesNum(List searchProperty);

	/**
	 * 获取符合条件的执行计划课程列表
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getExecutePlanCourses(Page page, List searchProperty,
			List orderProperty);

	/**
	 * 获取某个执行计划下的执行计划课程数
	 * 
	 * @param executePlanId
	 * @return
	 */
	public int getExecutePlanCoursesNumByExecutePlanId(String executePlanId);

	/**
	 * 获取某个执行计划下的执行计划课程列表
	 * 
	 * @param page
	 * @param executePlanId
	 * @return
	 */
	public List getExecutePlanCoursesByExecutePlanId(Page page,
			String executePlanId);

	/**
	 * 获取指定条件下执行计划课程列表以及选课人数信息
	 * 
	 * @param page
	 * @param searchproperty1
	 * @param searchproperty2
	 * @param orderproperty
	 * @return
	 */
	public List getExecutePlanWithConditionEleNum(Page page, String semesterId,
			String siteId, String majorId, String eduTypeId, String gradeId,
			String reg_no, List orderProperty) throws PlatformException;

	/**
	 * 获取指定执行计划下可供选择添加的教学计划课程数
	 * 
	 * @param executePlanId
	 * @return
	 */
	public int getToBeSelectedTeachPlanCoursesNumOfExecutePlan(
			String executePlanId);

	/**
	 * 获取指定执行计划下可供选择添加的教学计划课程列表
	 * 
	 * @param page
	 * @param executePlanId
	 * @return
	 */
	public List getToBeSelectedTeachPlanCoursesOfExecutePlan(Page page,
			String executePlanId);

	/**
	 * 批量删除执行计划课程
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteExecutePlanCourses(String[] ids);

	public int getUserFeeReturnApplyNum(List searchProperty);

	public List getUserFeeReturnApplyList(Page page, List searchProperty,
			List orderProperty);

	public int confirmElective(HttpServletRequest request);

	public int rejectElective(HttpServletRequest request);

	public List getTeachClass(String teacher_id);
}
