package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.util.Page;

public abstract class BasicGraduateDesignManage {
	/** Creates a new instance of BasicRecruitManage */
	public BasicGraduateDesignManage() {
	}

	public BasicGraduateDesignManage(ManagerPriv managerPriv) {
	}

	/**
	 * 修改毕业设计批次状态，解锁某一招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateDesignBatchStatus(String id)
			throws PlatformException;

	/**
	 * 添加毕业设计批次
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate)
			throws PlatformException;
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate,String semester)
			throws PlatformException;
	
	/**
	 * 删除招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteGraduateDesignBatch(String id)
			throws PlatformException, PlatformException;

	/**
	 * 修改毕业设计批次
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException;
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException;
	/**
	 * 根据aid得到招生批次对象
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateDesignBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * 获取当前活动批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;

	/**
	 * 根据批次号与批次名称查询相关毕业设计批次列表
	 * 
	 * @param page
	 * @param piciNo
	 * @param pciName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateDesignBatches(Page page, String piciNo,
			String pciName) throws PlatformException;

	public abstract int getGraduateDesignBatchesNum(String piciNo,
			String pciName) throws PlatformException;
	
	
//	-----------毕业大做业处理批次start
	/**
	 * 添加毕业大作业批次
	 */
	
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate)
			throws PlatformException;
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate,String semester_id)
			throws PlatformException;
	
	/**
	 * 修改毕业设计批次状态，解锁某一招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateExecDesignBatchStatus(String id)
			throws PlatformException;
	
	/**
	 * 删除招生批次
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteGraduateExecDesignBatch(String id)
			throws PlatformException, PlatformException;

	/**
	 * 修改毕业设计批次
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException;
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException;
	/**
	 * 根据aid得到招生批次对象
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * 获取当前活动批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
			throws PlatformException;

	/**
	 * 根据批次号与批次名称查询相关毕业设计批次列表
	 * 
	 * @param page
	 * @param piciNo
	 * @param pciName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecDesignBatches(Page page, String piciNo,
			String pciName) throws PlatformException;

	public abstract int getGraduateExecDesignBatchesNum(String piciNo,
			String pciName) throws PlatformException;


	//-----------毕业大做业处理次end

	/**
	 * 某一毕业设计批次下符合要求的年级层次专业
	 * 
	 * @param page
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajor(Page page, String piciId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorNum(String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException;
	
	

	/**
	 * 年级层次专业的笛卡尔积
	 * 
	 * @param page
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajorList(Page page, String pici_id,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorListNum(String pici_id,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * 为相应层次添加年级专业层次
	 * 
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGradeEduTypeMajors(String piciId, String gradeId,
			String eduTypeId, String majorId) throws PlatformException;

	public abstract int removeGradeEduTypeMajors(String id)
			throws PlatformException;

	public abstract int IsExitGraduateStudent(String id)
			throws PlatformException;

	/**
	 * 查询是否有毕业设计批次下设置层次和专业
	 * 
	 * @throws PlatformException
	 */
	public abstract int IsExistBatchId(String batch_id)
			throws PlatformException;

	public abstract FreeApply getStudentFreeApply(String studentId)
			throws PlatformException;

	/**
	 * 查询学员的毕业大作业列表信息
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	/**
	 * 查询学员的毕业大作业列表信息
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 *            毕业设计批次ID
	 * @param courseId
	 *            毕业设计课程ID
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId)
			throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId)
			throws PlatformException;

	/**
	 * 查询学员免做申请列表信息
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @param courseId
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	//加入了新的参数，使用其可以按审核结果查询；score;及类型type
	public abstract List getStudentFreeApplyList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	加入了新的参数，使用其可以按审核结果查询；score;　及类型type 
	public abstract int getStudentFreeApplyListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
	
	/**
	 * 获得毕业大作业的免做申请
	 */
//	加入了新的参数，使用其可以按审核结果查询；score;及类型type
	public abstract List getStudentFreeApplyExecList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	加入了新的参数，使用其可以按审核结果查询；score;　及类型type
	public abstract int getStudentFreeApplyExecListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId,String score)
			throws PlatformException;

	/**
	 * 开课登记信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * 开课登记信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	//加入学院审核查询条件conResult
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId,String conResult,String semesterId) throws PlatformException;
//	加入学院审核查询条件conResult
	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId,String conResult,String semesterId) throws PlatformException;

	/**
	 * 指导教师列表
	 * 
	 * @param page
	 * @param teacherId
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTutorTeacherList(Page page, String teacherId,
			String name) throws PlatformException;

	public abstract int getSiteTutorTeacherListNum(String reg_no, String name)
			throws PlatformException;

	/**
	 * 中期检查信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException;

	/**
	 * 答辩评审信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException;

	/**
	 * 答辩评审信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param type
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type, String piciId,String semesterId)
			throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String piciId,String semesterId) throws PlatformException;

	/**
	 * 电子论文信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException;

	/**
	 * 根据批次查询,学期 电子论文信息列表
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param type
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type, String piciId,String semesterId)
			throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String piciId,String semesterId) throws PlatformException;

	public abstract SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException;

	public abstract RegBgCourse getRegBeginCourse(String id)
			throws PlatformException;

	public abstract SiteTeacher getSiteTutorTeacher(String id)
			throws PlatformException;

	public abstract MetaphaseCheck getMetaphaseCheck(String id)
			throws PlatformException;

	public abstract RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException;

	public abstract Discourse getDiscourse(String id) throws PlatformException;

	public abstract int updateDiscourseGrade(String id, String grade)
			throws PlatformException;
	
	public abstract int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException;

	// ******************************总站指导教师*********************************************************
	/**
	 * 总站教师需要审核的年级层次专业
	 * 
	 * @param page
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajorForTeacher(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract List getNoneGradeEduTypeMajorForTeacher(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getNoneGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * 为相应总站指导教师添加年级层次专业
	 * 
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGradeEduTypeMajorsForTeacher(String teacherId,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract int removeGradeEduTypeMajorsForTeahcer(String id)
			throws PlatformException;

	/**
	 * 分站指导教师所要指导的学生
	 * 
	 * @param page
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract List getGraduatePici() throws PlatformException;
	
	/**
	 * 获得大作业批次
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecPici() throws PlatformException;

	public abstract Discourse createDiscourse() throws PlatformException;

	public abstract void addDiscourseAssessGradeByRegNO(List discourseList)
			throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNO(List discourseList)
			throws PlatformException;
	
	//------------------------导入毕业设计成绩, 并加入总评中 lwx 2008-06-07
	public abstract void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList ,String semesterId)
	throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	throws PlatformException;

	
	//-------------------------------------

	/**
	 * 毕业设计限制时间段
	 * 
	 * @param type
	 *            TYPE1:毕业论文设计时间 TYPE2:选题时间 TYPE3:开题时间 TYPE4:提交论文时间
	 *            TYPE5:答辩(评审)时间 TYPE6:成绩上报时间
	 * @throws PlatformException
	 */
	public abstract int limitTime(String type) throws PlatformException;
	/**
	 * 对学员的毕业大作业成绩信息进行单个修改
	 * @param id     毕业大作业表中记录编号
	 * @param score  毕业大作业要以百分制记录
	 * @return		 执行成功数
	 * @throws PlatformException
	 */
	public abstract int updateGraduateHomeWork(String id,String score)throws PlatformException;
	
	/**
	 * 添加毕业大作业成绩,并加入总评成绩中
	 */
	public abstract int updateGraduateHomeWorkToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score)throws PlatformException;
	
	
	/**
	 * 批量添加或修改学员的毕业大作业成绩信息
	 * @param scoreList   学员的大作业成绩信息
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWork(List scoreList)throws PlatformException;
	
	/**
	 * 批量添加或修改学员的毕业大作业成绩信息,并加入到总评成绩中
	 * @param scoreList   学员的大作业成绩信息
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWorkTotalGrade(List scoreList,String semesterId)throws PlatformException;
	
	/**
	 * 获得分站指导教师指导的专业
	 * @return List 教师指导专业列表
	 * @param String teacherId 教师编号
	 */
	public abstract List getGraduateSiteTeacherMajors(String teacherId) throws PlatformException;
	
}
