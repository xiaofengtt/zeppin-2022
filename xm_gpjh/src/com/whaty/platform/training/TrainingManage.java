/**
 * 
 */
package com.whaty.platform.training;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.courseware.AiccTrainingCourseware;
import com.whaty.platform.training.basic.courseware.Scorm12TrainingCourseware;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.user.TrainingClassManager;
import com.whaty.platform.training.user.TrainingManagerPriv;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingManage {

	private TrainingManagerPriv managerPriv;

	public TrainingManagerPriv getManagefPriv() {
		return managerPriv;
	}

	public void setManagePriv(TrainingManagerPriv managerpriv) {
		this.managerPriv = managerPriv;
	}

	/**
	 * 添加课程类型
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @param parentId
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourseType(String name, String status,
			String note, String parentId) throws PlatformException;

	/**
	 * 修改课程类型
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @param status
	 * @throws PlatformException
	 */
	public abstract void updateTrainingCourseType(String id, String name,
			String note, String status) throws PlatformException;

	/**
	 * 激活课程类型
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活学员
	 * 
	 * @param studentIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingStudent(List studentIds, boolean flag)
			throws PlatformException;

	/**
	 * 将某课程类型移到另一类型下
	 * 
	 * @param id
	 * @param parentId
	 * @throws PlatformException
	 */
	public abstract void moveTrainingCourseType(String id, String parentId)
			throws PlatformException;

	/**
	 * 删除一批课程类型
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourseType(List ids)
			throws PlatformException;

	/**
	 * 得到某个课程类型下的子类型
	 * 
	 * @param parentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypes(String parentId)
			throws PlatformException;

	/**
	 * @param parentId
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypes(String parentId, String field,
			String order) throws PlatformException;

	/**
	 * 得到某个课程类型
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourseType getTrainingCourseType(String id)
			throws PlatformException;

	/**
	 * 得到全部课程门数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllCoursesNum() throws PlatformException;

	/**
	 * 得到全部课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllCourses() throws PlatformException;

	/**
	 * 搜索课程
	 * 
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchCourses(Page page, String courseId,
			String courseName, String courseTypeId, String active)
			throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchCourses(Page page, String courseId,
			String courseName, String courseTypeId, String active,
			String field, String order) throws PlatformException;

	/**
	 * 获取课程门数
	 * 
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCoursesNum(String courseId, String courseName,
			String courseTypeId, String active) throws PlatformException;

	/**
	 * 得到某个课程类型下的课程
	 * 
	 * @param typeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllCourses(String typeId) throws PlatformException;

	/**
	 * 得到某个课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourse getTrainingCourse(String id)
			throws PlatformException;

	/**
	 * 得到某个课程下的Aicc课件
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccTrainingCourseware getAiccCourseware(String courseId)
			throws PlatformException;

	/**
	 * 得到Aicc课件管理类
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccCourseManage getAiccCourseManage()
			throws PlatformException;

	/**
	 * 得到某个课程下的Scorm12课件
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Scorm12TrainingCourseware getScorm12Courseware(
			String courseId) throws PlatformException;

	/**
	 * 得到Scorm12课件管理类
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormManage getScormManage() throws PlatformException;

	/**
	 * 得到某门课程的正式选课学员人数
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseCheckedStudentsNum(String courseId)
			throws PlatformException;

	public abstract int getCourseCheckedStudentsNum(String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * 得到某个课程的正式选课学员
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(String courseId)
			throws PlatformException;

	/**
	 * 分页得到某门课程的正式选课学员
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(Page page, String courseId)
			throws PlatformException;

	public abstract List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * 得到某课程的申请选课学员人数
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseApplyededStudentsNum(String courseId)
			throws PlatformException;

	public abstract int getCourseApplyededStudentsNum(String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * 得到某个课程的申请选课学员
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(String courseId)
			throws PlatformException;

	/**
	 * 分页得到某课程的申请选课学员
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(Page page, String courseId)
			throws PlatformException;

	public abstract List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * 添加课程
	 * 
	 * @param id
	 * @param name
	 * @param credit
	 * @param studyTime
	 * @param status
	 * @param typeId
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourse(String id, String name,
			String credit, String studyTime, String status, String typeId,
			String note) throws PlatformException;

	/**
	 * 修改课程
	 * 
	 * @param id
	 * @param name
	 * @param credit
	 * @param studyTime
	 * @param status
	 * @param typeId
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateTrainingCourse(String id, String name,
			String credit, String studyTime, String status, String typeId,
			String note) throws PlatformException;

	/**
	 * 删除课程
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourse(String id)
			throws PlatformException;

	/**
	 * 根据flag激活或者锁定课程
	 * 
	 * @param classId
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException;

	/**
	 * 删除多个课程
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourses(List ids)
			throws PlatformException;

	/**
	 * 将课程移至某个课程类型下
	 * 
	 * @param courseId
	 * @param typeId
	 * @throws PlatformException
	 */
	public abstract void moveToOtherType(String courseId, String typeId)
			throws PlatformException;

	/**
	 * 为课程添加课件
	 * 
	 * @param courseId
	 * @param courewareId
	 * @param coursewareTitle
	 * @param coursewareType
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourseware(String courseId,
			String coursewareId, String coursewareType)
			throws PlatformException;

	/**
	 * 为课程删除课件
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @throws PlatformException
	 */
	public abstract void removeTrainingCourseware(String courseId,
			String coursewareId) throws PlatformException;

	/**
	 * 得到某个学生的信息
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingStudent getTrainingStudent(String studentId)
			throws PlatformException;

	/**
	 * 得到所有学生
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingStudent() throws PlatformException;

	/**
	 * 搜索学生
	 * 
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked)
			throws PlatformException;

	/**
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked,
			String field, String order) throws PlatformException;

	/**
	 * 获取符合条件的学生人数
	 * 
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingStudentsNum(String studentId,
			String studentName, String studentNickName, String checked)
			throws PlatformException;

	/**
	 * 添加学生
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(String id, String name,
			String nickName, String email) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @param active
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, String active,
			String note) throws PlatformException;

	public abstract void addTrainingManager(String id, String name,
			String nickName, String email, String note)
			throws PlatformException;

	/**
	 * 修改学生信息
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @throws PlatformException
	 */
	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @throws PlatformException
	 */
	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone)
			throws PlatformException;

	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, int status)
			throws PlatformException;

	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, int status,
			String note) throws PlatformException;

	/**
	 * 删除学生
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingStudent(String id)
			throws PlatformException;

	public abstract void deleteTrainingManager(List managerIdList)
			throws PlatformException;

	/**
	 * 得到全部的培训班数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllTrainingClassNum() throws PlatformException;

	/**
	 * 得到全部的培训班
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass() throws PlatformException;

	/**
	 * 分页得到全部的培训班
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass(Page page)
			throws PlatformException;

	/**
	 * @param page
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass(Page page, String field,
			String order) throws PlatformException;

	/**
	 * 添加培训班
	 * 
	 * @param name
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param startSelectDate
	 * @param startEndDate
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingClass(String name, String status,
			String startDate, String endDate, String startSelectDate,
			String endSelectDate, String note) throws PlatformException;

	/**
	 * 修改培训班
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param startSelectDate
	 * @param startEndDate
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateTrainingClass(String id, String name,
			String status, String startDate, String endDate,
			String startSelectDate, String endSelectDate, String note)
			throws PlatformException;

	/**
	 * 删除培训班
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingClass(List ids) throws PlatformException;

	/**
	 * 根据标志激活或者锁定培训班
	 * 
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException;

	/**
	 * 为某个培训班增加培训班主管
	 * 
	 * @param chiefIds
	 * @throws PlatformException
	 */
	public abstract void addClassManagers(String classId, List chiefIds)
			throws PlatformException;

	/**
	 * 为某个培训班删除培训班主管
	 * 
	 * @param chiefIds
	 * @throws PlatformException
	 */
	public abstract void removeClassManagers(String classId, List chiefIds)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassCoursesNum(String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassCoursesNum(String classId, String course_id,
			String course_name) throws PlatformException;

	/**
	 * 得到某个培训班下的课程列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId,
			String course_id, String course_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param course_id
	 * @param course_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId,
			String course_id, String course_name, String field, String order)
			throws PlatformException;

	/**
	 * 为某个培训班添加课程
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void addClassCourses(String classId, List courseIds)
			throws PlatformException;

	/**
	 * 为某个培训班删除课程
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void removeClassCourses(String classId, List courseIds)
			throws PlatformException;

	/**
	 * 得到某个培训班下学生人数
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedClassStudentsNum(String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班下学生人数
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedClassStudentsNum(String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * 得到某个培训班下学生列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(String classId)
			throws PlatformException;

	/**
	 * 分页得到某个培训班下学生列表
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId)
			throws PlatformException;

	/**
	 * 分页得到某个培训班下符合条件的学生列表
	 * 
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * 得到某个培训班下未审核通过的学生数
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUnCheckedClassStudentsNum(String classId)
			throws PlatformException;

	public abstract int getUnCheckedClassStudentsNum(String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * 得到某个培训班下未审核通过的学生
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(String classId)
			throws PlatformException;

	/**
	 * 分页得到某个培训班下未审核通过的学生
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String classId)
			throws PlatformException;

	public abstract List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * 为某个培训班添加学员
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void addClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * 为某个培训班确认学员选择培训班
	 * 
	 * @param classId
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void checkClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * 为某个培训班删除学员
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void removeClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * 得到某个技能链
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract SkillChain getSkillChain(String chainId)
			throws PlatformException;

	/**
	 * 得到全部技能链
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkillChain() throws PlatformException;

	/**
	 * @param chainName
	 * @param chainNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getSkillChainNum(String chainName, String chainNote)
			throws PlatformException;

	/**
	 * @param page
	 * @param chainName
	 * @param chainNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkillChain(Page page, String chainName,
			String chainNote, String field, String order)
			throws PlatformException;

	/**
	 * 增加技能链
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addSkillChain(String name, String status, String note)
			throws PlatformException;

	/**
	 * 修改技能链
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateSkillChain(String id, String name,
			String status, String note) throws PlatformException;

	/**
	 * 删除技能链
	 * 
	 * @param idList
	 * @throws PlatformException
	 */
	public abstract void deleteSkillChain(List idList) throws PlatformException;

	/**
	 * 得到某个技能链下的技能
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChainSkills(String chainId)
			throws PlatformException;

	/**
	 * 得到所有技能数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllSkillsNum() throws PlatformException;

	public abstract int getSkillsNum(String skill_id, String skill_name)
			throws PlatformException;

	/**
	 * 得到所有技能
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkills() throws PlatformException;

	/**
	 * 分页得到所有技能
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkills(Page page) throws PlatformException;

	public abstract List getSkills(Page page, String skill_id, String skill_name)
			throws PlatformException;

	/**
	 * @param page
	 * @param skill_id
	 * @param skill_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkills(Page page, String skill_id,
			String skill_name, String field, String order)
			throws PlatformException;

	/**
	 * 增加技能
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @param chainId
	 * @throws PlatformException
	 */
	public abstract void addSkill(String name, String status, String note,
			String chainId) throws PlatformException;

	/**
	 * 修改技能
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param note
	 * @param chainId
	 * @throws PlatformException
	 */
	public abstract void updateSkill(String id, String name, String status,
			String note, String chainId) throws PlatformException;

	/**
	 * 删除技能
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteSkill(String id) throws PlatformException;

	/**
	 * 删除一批技能
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteSkills(List ids) throws PlatformException;

	/**
	 * 激活或者锁定某些skill
	 * 
	 * @param skillIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkill(List skillIds, boolean flag)
			throws PlatformException;

	/**
	 * 激活或者锁定某些skillChain
	 * 
	 * @param skillChainIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException;

	/**
	 * 为某个技能链添加技能
	 * 
	 * @param skills
	 * @param skillChainId
	 * @throws PlatformException
	 */
	public abstract void addChainSkills(List skills, String skillChainId)
			throws PlatformException;

	/**
	 * 从某个技能链删除技能
	 * 
	 * @param skills
	 * @param skillChainId
	 * @throws PlatformException
	 */
	public abstract void removeChainSkills(List skills, String skillChainId)
			throws PlatformException;

	/**
	 * 为某个技能添加课程
	 * 
	 * @param courses
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void addSkillCourses(List courses, String skillId)
			throws PlatformException;

	/**
	 * 为某个技能删除课程
	 * 
	 * @param courses
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removeSkillCourses(List courses, String skillId)
			throws PlatformException;

	/**
	 * 得到某个课程的先决技能
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkills(String skillId) throws PlatformException;

	/**
	 * 为某个技能添加先决技能
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void addPreSkill(List skills, String skillId)
			throws PlatformException;

	/**
	 * 为某个技能删除先决技能
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removePreSkill(List skills, String skillId)
			throws PlatformException;

	/**
	 * 添加选课信息
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void addCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * 审核选课信息
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void checkCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * 删除选课信息
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void removeCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * 设置目标技能
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void targetSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * 取消目标技能
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unTargetSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * 授予技能
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void rewardSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * 解除授予技能
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unRewardSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * 得到符合条件的培训班数目
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingClassNum(String classId, String className)
			throws PlatformException;

	/**
	 * 得到符合条件的培训班
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClass(Page page, String classId,
			String className) throws PlatformException;

	/**
	 * 得到某个培训班
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClass getTrainingClass(String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班主管
	 * 
	 * @param classManagerId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClassManager getTrainingClassManager(
			String classManagerId) throws PlatformException;

	/**
	 * 查找培训班主管
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingClassManagers(Page page, String id,
			String name, String classId) throws PlatformException;

	/**
	 * 得到符合条件的培训班主管人数
	 * 
	 * @param managerId
	 * @param managerName
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingClassManagersNum(String managerId,
			String managerName) throws PlatformException;

	/**
	 * 得到全部培训班主管
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page)
			throws PlatformException;

	/**
	 * 得到符合条件的培训班主管
	 * 
	 * @param page
	 * @param managerId
	 * @param managerName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page,
			String managerId, String managerName) throws PlatformException;

	/**
	 * @param page
	 * @param managerId
	 * @param managerName
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page,
			String managerId, String managerName, String field, String order)
			throws PlatformException;

	/**
	 * 得到课程类别树
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypeTree() throws PlatformException;

	/**
	 * 得到某个类型能够移动到的课程目录树
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMoveTrainingCourseTypeTree(String courseTypeId)
			throws PlatformException;

	/**
	 * 得到某个技能
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Skill getSkill(String skillId) throws PlatformException;

	/**
	 * 搜索技能
	 * 
	 * @param page
	 * @param skillId
	 * @param skillName
	 * @param flag
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchSkill(Page page, String skillId,
			String skillName, String flag) throws PlatformException;

	/**
	 * 批量退选课程
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unApplyCourseStudent(String courseId, List studentList)
			throws PlatformException;

	/**
	 * 取消确认选课
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckCourseStudent(String courseId, List studentList)
			throws PlatformException;

	/**
	 * 取消确认加入培训班
	 * 
	 * @param classId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckClassStudent(String classId, List studentList)
			throws PlatformException;

	/**
	 * 批量删除加入培训班的申请
	 * 
	 * @param classId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unApplyClassStudent(String classId, List studentList)
			throws PlatformException;

	/**
	 * @param skillId
	 * @param courseId
	 * @return 判断某技能课程是否已经存在
	 * @throws PlatformException
	 */
	public abstract boolean isSkillCourseExisted(String skillId, String courseId)
			throws PlatformException;

	/**
	 * 删除某个技能下的学生
	 * 
	 * @param students
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removeSkillStudents(List students, String skillId)
			throws PlatformException;

	/**
	 * 审核某技能的学生的申请
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void checkSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * 取消审核某技能的学生的申请
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckSkillStudent(String skillId, List studentList)
			throws PlatformException;

	/**
	 * 更新培训班管理员的信息
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @throws PlatformException
	 */
	public abstract void updateTrainingClassManager(String id, String name,
			String nickName, String email, String note, String mobilePhone)
			throws PlatformException;

	public abstract LeaveWordManagerPriv getLeaveWordManagerPriv(String id)
			throws PlatformException;

	public abstract LeaveWordManage getLeaveWordManage(LeaveWordManagerPriv priv)
			throws PlatformException;
}
