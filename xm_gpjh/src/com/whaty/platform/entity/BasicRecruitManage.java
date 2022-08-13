package com.whaty.platform.entity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitCourse;
import com.whaty.platform.entity.recruit.RecruitNoExamCondition;
import com.whaty.platform.entity.recruit.RecruitSort;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.recruit.RecruitTestCourse;
import com.whaty.platform.entity.recruit.RecruitTestStudent;
import com.whaty.platform.util.Page;

/**
 * 该类描述了管理员管理招生部分的功能
 * 
 * @author chenjian
 * 
 */

public abstract class BasicRecruitManage {
	/** Creates a new instance of BasicRecruitManage */
	public BasicRecruitManage() {
	}

	/**
	 * 添加专业科类
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitSort(String edutype_id, String name,
			String note) throws PlatformException;

	/**
	 * 
	 * @param edutype_id
	 * @param name
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int setRegDate(String batch_id, String reg_startdate,
			String reg_enddate) throws PlatformException;

	/**
	 * 专业科类列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 专业科类列表
	 * @throws PlatformException
	 */
	public abstract List getRecruitSorts(Page page) throws PlatformException;

	/**
	 * 专业科类数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitSortsNum() throws PlatformException;

	/**
	 * 根据aid得到专业科类对象
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitSort getRecruitSort(String aid)
			throws PlatformException;

	/**
	 * 修改专业科类
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitSort(String id, String edutype_id,
			String name, String note) throws PlatformException,
			PlatformException;

	/**
	 * 删除专业科类
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitSort(String id) throws PlatformException,
			PlatformException;

	/**
	 * 添加专业科类所属专业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitSortMajors(String recruitSortId,
			List majorId, List majorIds) throws PlatformException,
			PlatformException;

	/**
	 * 专业科类所属专业列表
	 * 
	 * @param aid
	 * @return 专业科类所属专业列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortMajors(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitSortMajorsNum(String aid, String status)
			throws PlatformException;

	/**
	 * 添加专业科类所属课程
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitSortCourses(String recruitSortId,
			List courseId, List courseIds) throws PlatformException,
			PlatformException;

	/**
	 * 专业科类所属课程列表
	 * 
	 * @param aid
	 * @return 专业科类所属课程列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortCourses(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitSortCoursesNum(String aid, String status)
			throws PlatformException;

	/**
	 * 专业科类所属考试课程列表
	 * 
	 * @return 专业科类所属考试课程列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortTestCourses(String batchId, String sortId)
			throws PlatformException;

	/**
	 * 添加课程所属专业科类
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitCourseSorts(String recruitCourseId,
			List sortId, List sortIds) throws PlatformException,
			PlatformException;

	/**
	 * 课程所属专业科类列表
	 * 
	 * @param aid
	 * @return 课程所属专业科类列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitCourseSorts(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitCourseSortsNum(String aid, String status)
			throws PlatformException;

	/**
	 * 修改招生批次状态，解锁某一招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitBatchStatus(String id)
			throws PlatformException;

	/**
	 * 修改招生计划状态，审核某一招生计划
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitPlanStatus(String id, String status,
			String num, String note) throws PlatformException;

	/**
	 * 修改招生计划状态，审核某一招生计划
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitPlanStatus(String id, String status)
			throws PlatformException;

	/**
	 * 浏览已上报id为batchId的招生批次的招生计划的教学站
	 * 
	 * @param batchId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitPlanSites(Page page, String batchId)
			throws PlatformException;

	/**
	 * 考试课程数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitCourseNum() throws PlatformException;

	/**
	 * 考试课程列表
	 * 
	 * @return 考试课程列表
	 * @throws PlatformException
	 */
	public abstract List getRecruitCourse(Page page) throws PlatformException;

	public abstract RecruitCourse getRecruitCourse(String aid)
			throws PlatformException;

	/**
	 * 删除考试课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitCourse(String id) throws PlatformException;

	/**
	 * 添加招生批次
	 * 
	 * @param id
	 * @param name
	 * @param planBeginTime
	 * @param planEndTime
	 * @param signBeginTime
	 * @param signEndTime
	 * @param examBeginTime
	 * @param examEndTime
	 * @param note
	 * @param simpleNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addRecruitBatch(String id, String name,
			String planBeginTime, String planEndTime, String signBeginTime,
			String signEndTime, String examBeginTime, String examEndTime,
			String note, String simpleNote) throws PlatformException;

	/**
	 * 添加招生考试课程
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitCourse(String id, String name, String note)
			throws PlatformException;

	public abstract int updateRecruitCourse(String id, String name, String note)
			throws PlatformException;

	/**
	 * 删除招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitBatch(String id) throws PlatformException,
			PlatformException;

	/**
	 * 修改招生批次
	 * 
	 * @param id
	 * @param name
	 * @param planBeginTime
	 * @param planEndTime
	 * @param signBeginTime
	 * @param signEndTime
	 * @param examBeginTime
	 * @param examEndTime
	 * @param note
	 * @param simpleNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitBatch(String id, String name,
			String planBeginTime, String planEndTime, String signBeginTime,
			String signEndTime, String examBeginTime, String examEndTime,
			String note, String simpleNote) throws PlatformException;

	/**
	 * 根据aid得到招生批次对象
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitBatch getRecruitBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * 获取当前活动批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitBatch getActiveRecruitBatch()
			throws PlatformException;

	/**
	 * 招生批次列表
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page) throws PlatformException;

	/**
	 * @param page
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page, String name)
			throws PlatformException;
	/**
	 * @param page
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page, String recruitNo,String recruitName)
			throws PlatformException;

	/**
	 * 招生计划列表
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitPlans(Page page, String batchId,
			String siteId, String status) throws PlatformException;

	/**
	 * 批次数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum() throws PlatformException;

	/**
	 * 根据名称搜索招生批次
	 * 
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum(String name)
			throws PlatformException;
	/**
	 * 根据名称搜索招生批次
	 * 
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum(String id,String name)
			throws PlatformException;

	/**
	 * 符合条件分数段学生人数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitScoreStudents(Page page, String siteId,
			String eduTypeId, String majorId, String batchId, String[] sec)
			throws PlatformException;

	/**
	 * 符合条件分数段学生人数
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitScoreStudents(Page page, String courseId,
			String batchId, String[] sec) throws PlatformException;

	/**
	 * 符合条件分数段学生人数数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitScoreStudentsNum(String siteId,
			String eduTypeId, String majorId, String batchId, String[] sec)
			throws PlatformException;

	/**
	 * 招生计划数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitPlansNum(String batchId, String siteId,
			String status) throws PlatformException;

	/**
	 * 根据指定id获取考试课程信息
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract RecruitTestCourse getRecruitTestCourse(String id)
			throws PlatformException;

	/**
	 * 考试课程列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 考试课程列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitTestCourses(Page page, String batchId,
			String courseId) throws PlatformException;

	/**
	 * 考试课程数目
	 * 
	 * @return 考试课程数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitTestCoursesNum(String batchId, String courseId)
			throws PlatformException;

	/**
	 * 非考试课程列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 非考试课程列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRecruitTestCourses(Page page, String batchId)
			throws PlatformException;

	/**
	 * 非考试课程数目
	 * 
	 * @return 非考试课程数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnRecruitTestCoursesNum(String batchId)
			throws PlatformException;

	/**
	 * 学生成绩列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitStudentScores(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate) throws PlatformException,
			PlatformException;

	/**
	 * 手动录取学生时对学生状态的改变
	 * 
	 * @throws PlatformException
	 * @throws PlatformException
	 */

	public abstract int updateHandSingleStudentStatus(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * 学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getFreeRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String considertype, String considertype_status)
			throws PlatformException;

	public abstract int updateFreeRecruitStudentStatus(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String considertype, String considertype_status)
			throws PlatformException;

	/**
	 * 录取学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status) throws PlatformException;
	/**
	 * 
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param stuName
	 * @param idCard
	 * @param regNo      暂时未用
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String stuName ,String testcard_id,String regNo) throws PlatformException;
	/**
	 * 学号发布状态学生列表     (学生状态：1为发布，2为未发布)
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus,String reg_no) throws PlatformException;
	/**
	 * 
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudentsList(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus) throws PlatformException;

	/**
	 * 录取学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * 已注册学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String cardNo, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * 
	 * @param page
	 * @param siteId
	 * @param batchId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param name
	 * @param cardNo
	 * @param regNo
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String gradeId,String majorId, String eduTypeId,
			String name, String cardNo, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * 未注册学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * 未注册学生列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @return 学生成绩列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo,String gender) throws PlatformException,
			PlatformException;
	
	public abstract List getAllUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * 学生成绩数目
	 * 
	 * @return 学生成绩数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitStudentScoresNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate) throws PlatformException,
			PlatformException;

	/**
	 * 免试学生数目
	 * 
	 * @return 免试学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getFreeRecruitStudentNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo,
			String considertype, String considertype_status)
			throws PlatformException;

	/**
	 * 录取学生数目
	 * 
	 * @return 录取学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status) throws PlatformException;

	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus,String reg_no) throws PlatformException;
	
	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String stuName,String testcard_id,String reg_no) throws PlatformException;
	/**
	 * 学号生成但并未发布的学生总计
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPassRecruitStudentsListNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus) throws PlatformException;
	
	public abstract int updateStudentSiteAndMajor(String[] ids, String siteId,
			String majorId) throws PlatformException;

	public abstract int updateStudentRegNoAndPwd(String[] ids)
			throws PlatformException;

	/**
	 * 录取学生数目
	 * 
	 * @return 录取学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * 录取已注册学生数目
	 * 
	 * @return 录取学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * 
	 * @param siteId
	 * @param batchId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param name
	 * @param cardNo
	 * @param regNo
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterPassRecruitStudentsNum(String siteId,
			String batchId,String gradeId, String majorId, String eduTypeId, String name,
			String cardNo, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * 录取未注册学生数目
	 * 
	 * @return 录取学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException;
	
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo,String gender) throws PlatformException;
	
	public abstract int getAllUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException;

	/**
	 * 添加考试课程
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitTestCourses(String batchId, List courseId,
			String startdate, String enddate) throws PlatformException,
			PlatformException;

	/**
	 * 删除考试课程
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitTestCourse(String id)
			throws PlatformException;

	/**
	 * 上报学生成绩
	 * 
	 * @param score
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitStudentScore(HashMap score)
			throws PlatformException;

	/**
	 * 批量上报学生成绩
	 * 
	 * @param scoreList
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitStudentScoreBatch(List scoreList,
			String type) throws PlatformException;

	/**
	 * 发布学生成绩
	 * 
	 * @param batchId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int releaseScore(String batchId, String eduTypeId,
			String majorId) throws PlatformException;

	/**
	 * 发布学生成绩
	 * 
	 * @param batchId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int releaseScore(String batchId, String siteId)
			throws PlatformException;

	public abstract int unreleaseScore(String batchId, String siteId)
			throws PlatformException;

	public abstract int releaseMatriculate(String batchId, String siteId)
			throws PlatformException;

	public abstract int unreleaseMatriculate(String batchId, String siteId)
			throws PlatformException;

	public abstract List getReleaseSites(String batchId)
			throws PlatformException;

	/**
	 * 录取学生列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNormalStudents(Page page, String batchId,
			String siteId, String eduTypeId, String majorId, String name,
			String cardNo, String pass_status) throws PlatformException;

	/**
	 * 录取学生数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNormalStudentsNum(String batchId, String siteId,
			String eduTypeId, String majorId, String name, String cardNo)
			throws PlatformException;

	/**
	 * 查询考场分布
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTotalTestRoom(Page page, String batchId,
			String edutype_id, String major_id, String site_id)
			throws PlatformException;

	public abstract int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id) throws PlatformException,
			PlatformException;

	public abstract List getTotalTestRoom(Page page, String batchId,
			String site_id) throws PlatformException;

	public abstract int getTotalTestRoomNums(String batchId, String site_id)
			throws PlatformException, PlatformException;

	public abstract List getEdutypeMajorTestDesk(String batchId,
			String edutype_id, String major_id, String card_no, String site_id)
			throws PlatformException;

	public abstract List getTestRooms(Page page, String batchId, String site_id)
			throws PlatformException;

	public abstract List getTestDesks(Page page, String room_id)
			throws PlatformException;

	/**
	 * 专业所属科类列表
	 * 
	 * @return 专业所属科类列表
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitMajorSorts(String majorId)
			throws PlatformException;

	/**
	 * 招生录取情况统计
	 * 
	 * @return 招生录取情况统计
	 * @throws PlatformException
	 * @throws PlatformException
	 * @throws SQLException
	 * @throws PlatformException
	 * @throws SQLException
	 */
	public abstract HashMap getRecruitStat(String batchId)
			throws PlatformException, SQLException;

	/**
	 * 录取学生信息发布
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassStatistic(Page page, String batch_id,
			String site_id, String major_id, String edutype_id)
			throws PlatformException;

	public abstract int getPassStatisticNum(String batch_id, String site_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract List getPassStuId(String batch_id, String status,
			String major_id, String edutype_id, String site_id,
			String pass_status) throws PlatformException;

	public abstract int updateRecruitStudentPassstatus(String stuId,
			String passstatus) throws PlatformException;

	public abstract List getTotalStu(Page page, String site_id,
			String batch_id, String major_id, String edutype_id,
			List orderProperty) throws PlatformException;

	public abstract List getStatStudents(String site_id, String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int getTotalStuNum(String site_id, String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int getTotalStuPassNum(String site_id, String batch_id,
			String major_id, String edutype_id, String status)
			throws PlatformException;

	public abstract int updateStudentStatus(String status, String student_id)
			throws PlatformException;

	public abstract int updateStudentStatus(String status, String student_id,
			String grade_id) throws PlatformException;

	public abstract List getMatricaluteCondition(Page page, String batch_id,
			String major_id, String edutype_id, String sort_id)
			throws PlatformException;

	public abstract int getMatricaluteConditionNum(String batch_id,
			String major_id, String edutype_id, String sort_id)
			throws PlatformException;

	public abstract int updateStudentStatusBatch(String batch_id,
			String major_id, String edutype_id, String grade_id)
			throws PlatformException;

	public abstract List getMatricaluteCourse(Page page, String major_id,
			String batch_id, List orderProperty) throws PlatformException,
			PlatformException;

	public abstract String getIdByMajorandBatch(String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int deleteByMajorandBatch(String batch_id, String major_id,
			String edutype_id) throws PlatformException;

	public abstract int deleteByMajor(String mc_id) throws PlatformException,
			PlatformException;

	public abstract int updateMatricaluteCondition(String batch_id,
			String major_id, String edutype_id, String score, String score1,
			String photo_status, String idcard_status,
			String graduatecard_status) throws PlatformException;

	public abstract int setMatricaluteCondition(String mc_id, String course_id,
			String score) throws PlatformException;

	/**
	 * 根据aid得到考试学生信息
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitTestStudent getTestStudent(String aid)
			throws PlatformException;

	/**
	 * 注册情况统计
	 * 
	 * @return 注册情况统计
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterStat(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws PlatformException;

	public abstract List getMatricaluteCourseScore(String mc_id)
			throws PlatformException;

	/**
	 * 注册情况统计
	 * 
	 * @return 注册情况统计
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterStatNum(String batchId, String siteId,
			String majorId, String eduTypeId) throws PlatformException,
			PlatformException;

	public abstract List getMatricaluteConditionTotalScore(String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	/**
	 * 修改考试课程的考试时间
	 * 
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitTestCourse(String id, String startTime,
			String endTime) throws PlatformException;

	/**
	 * 获取报名统计
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSignStatistic(Page page, String batch_id,
			String site_id, String major_id) throws PlatformException;

	public abstract List getSignStatisticBySite(String batch_id)
			throws PlatformException;

	public abstract List getSignStatisticByMajor(String batch_id)
			throws PlatformException;

	/**
	 * 获取学生信息
	 * 
	 * @throws PlatformException
	 */
	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) throws PlatformException;

	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo_status)
			throws PlatformException;

	/**
	 * 获取学生信息
	 * 
	 * @throws PlatformException
	 */
	public abstract List getFreeStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String considertype_status)
			throws PlatformException;

	/**
	 * 获取学生数
	 */
	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo_status);

	/**
	 * 获取学生信息
	 * 
	 * @throws PlatformException
	 */
	public abstract List getTestStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract List getTestStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status,
			String considertype_status) throws PlatformException;

	/**
	 * 获取学生数
	 */
	public abstract int getTestStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * 获取免试学生数
	 */
	public abstract int getFreeStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String considertype_status);

	/**
	 * 获取学生数
	 */
	public abstract RecruitStudent getStudent(String id)
			throws PlatformException;

	/**
	 * 确认学生免试信息
	 */
	public abstract int confirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	/**
	 * 取消确认学生免试信息
	 */
	public abstract int unConfirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	public abstract int rejectFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	/**
	 * 获取报名统计人数
	 * 
	 * @throws PlatformException
	 */
	public abstract int getSignStatisticNum(String batch_id, String site_id,
			String major_id) throws PlatformException;

	public abstract List getStudentsByMajorEdutype(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws PlatformException;

	/**
	 * 由招生批次和层次获取招生专业
	 * 
	 * @throws PlatformException
	 */
	public abstract List getMajors(String batch_id, String edu_type_id)
			throws PlatformException;

	/**
	 * 由招生批次和层次获取招生专业数量
	 * 
	 * @throws PlatformException
	 */
	public abstract int getMajorsNum(String batch_id, String edu_type_id)
			throws PlatformException;

	/**
	 * 由招生批次和层次获取招生专业
	 * 
	 * @throws PlatformException
	 */
	public abstract List getMajors(Page page, String batch_id,
			String edu_type_id) throws PlatformException;

	/**
	 * 删除招生批次和层次获取招生专业
	 * 
	 * @throws PlatformException
	 */
	public abstract int deleteMajors(String batch_id, String edu_type_id)
			throws PlatformException;

	public abstract int deleteMajors(String batch_id, String edu_type_id,
			String major_id) throws PlatformException;

	/**
	 * 删除招生批次和层次获取招生专业
	 * 
	 * @throws PlatformException
	 */
	public abstract int addMajors(String batch_id, String edu_type_id,
			String major_id) throws PlatformException;

	/**
	 * 查询是否有招生批次下设置层次和专业
	 * 
	 * @throws PlatformException
	 */
	public abstract int IsExistBatchId(String batch_id)
			throws PlatformException;

	/**
	 * 查询是否有招生批次下设置层次和专业
	 * 
	 * @throws PlatformException
	 */
	public abstract List getAddBatchMajors(Page page, String batchId,
			String edu_type_id) throws PlatformException;

	public abstract int getAddBatchMajorsNum(String batchId, String edu_type_id)
			throws PlatformException;

	public abstract int addRecruitNoExamCondition(String name, String note)
			throws PlatformException;

	public abstract int deleteRecruitNoExamCondition(String id)
			throws PlatformException;

	public abstract RecruitNoExamCondition getRecruitNoExamCondition(String aid)
			throws PlatformException;

	public abstract List getRecruitNoExamConditions(Page page)
			throws PlatformException;

	public abstract int getRecruitNoExamConditionsNum()
			throws PlatformException;

	public abstract int updateRecruitNoExamCondition(String id, String name,
			String note) throws PlatformException;

	/**
	 * 获取考生特征
	 */
	public abstract int getStudentCharacter(String id, String signDate)
			throws PlatformException;

	/**
	 * 上传照片
	 */
	public abstract int uploadImage(String card_no, String filename)
			throws PlatformException;

	/**
	 * 批量上传照片
	 */
	public abstract int uploadBatchImage(String card_no, String filename)
			throws PlatformException;

	/**
	 * 上传身份证
	 */
	public abstract int uploadIdCard(String card_no, String filename)
			throws PlatformException;

	/**
	 * 上传毕业证
	 */
	public abstract int uploadGraduateCard(String card_no, String filename)
			throws PlatformException;

	public abstract List getStudentPhotos(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract int getStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * 删除照片
	 */
	public abstract int deleteStudentPhoto(String user_id, String note)
			throws PlatformException;

	public abstract int confirmStudentPhoto(String user_id)
			throws PlatformException;

	public abstract int confirmStudentPhoto(String user_id, String status)
			throws PlatformException;

	public abstract List getRejectStudentPhotos(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract int getRejectStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * 更新学生的学习中心
	 * @param status
	 * @param student_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateStudentSite(String site, String student_id)
			throws PlatformException;

	/**
	 * 判断转入的学习中心是否存在学生原来的专业和层次
	 * @param change_siteId  转入的站点ID
	 * @param batchId       招生批次
	 * @param stu_Id       学生ID
	 * @return
	 */
	public abstract int judgeMajorAndEdu(String change_siteId, String batchId,
			String stu_Id);
	 /**
	  * 更新学生的学号发布状态
	  * 1为学号发布　０为学号未发布
	  * @return
	  * @throws PlatformException
	  */
	
	public abstract int releaseRegNoAndPwd(String stuID,String status) throws PlatformException;
	
	/**
	 * 获取某一学籍状态学生数目
	 * 
	 * @return 获取学生学籍状态学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitStudentsStudyStatusNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String studyStatus) throws PlatformException;
	
	/**
	 * 获取某一学籍状态学生
	 * 
	 * @return 获取学生学籍状态学生
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitStudentsStudyStatus(Page page,String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String studyStatus) throws PlatformException;
	
	public abstract int updateStudentStudyStatus(String status, String student_id)
	throws PlatformException;
	
	public abstract int releaseMatriculate(String studentId)
	throws PlatformException;

	public abstract int unreleaseMatriculate(String studentId)
	throws PlatformException;
	
	/**
	 * 获取录取状态发布、未发布学生数目
	 * 
	 * @return 获取录取状态发布、未发布学生数目
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getReleaseStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String releaseStatus,String studyStatus) throws PlatformException;
	
	/**
	 * 获取录取状态发布、未发布学生
	 * 
	 * @return 获取录取状态发布、未发布学生
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getReleaseStudents(Page page,String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String releaseStatus,String studyStatus) throws PlatformException;

	/**
	 * 获取批次下的招生层次
	 */
		public abstract List getBatchEdutypes(String batchID)
			throws PlatformException, NoRightException;
		
	/**
	 * 获取批次下的招生层次
	 */
		
	public abstract List getBatchMajors1(String batchId, String edu_type_id,
			String site_id) throws PlatformException, NoRightException;
	
}


