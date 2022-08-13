/**
 * 
 */
package com.whaty.platform.training.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface TrainingBasicList {

	/**
	 * 得到课程类型的目录树列表
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourseTypeTree(List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 获取课程门数
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int searchCourseNum(List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 搜索课程
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourse(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 搜索学生和课程的关系
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchStudentCourse(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException;

	/**
	 * 获取培训班数
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getClassNum(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 搜索培训班
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchClass(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * 搜索某个学生与培训班的关系
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public List searchStudentClass(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException;

	/**
	 * 搜索某个培训班下的课程
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public List searchClassCourses(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程数目
	 * 
	 * @param searchProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int searchClassCoursesNum(List searchProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * 获取某个培训班下的学生人数
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int getClassStudentsNum(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * 搜索某个培训班下的学生
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public List searchClassStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * 得到某个培训班下的学生数目
	 * 
	 * @param searchProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int searchClassStudentsNum(List searchProperty,
			TrainingClass newClass) throws PlatformException;

	/**
	 * 搜索某个课程下的学生
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param course
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingCourse course) throws PlatformException;

	/**
	 * 得到某个课程下学生的人数
	 * 
	 * @param searchProperty
	 * @param course
	 * @return
	 * @throws PlatformException
	 */
	public int searchCourseStudentsNum(List searchProperty,
			TrainingCourse course) throws PlatformException;

	/**
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public int searchStudentCourseNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException;

	/**
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public int getStudentClassNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException;

	public int searchClassCourseStudentsNum(List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException;

	public List searchClassCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException;
}
