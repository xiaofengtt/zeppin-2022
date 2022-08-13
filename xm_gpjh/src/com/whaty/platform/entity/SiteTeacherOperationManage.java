package com.whaty.platform.entity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;

public abstract class SiteTeacherOperationManage {

	private SiteTeacherPriv siteTeacherPriv;

	public SiteTeacherOperationManage() {

	}

	public SiteTeacherPriv getSiteTeacherPriv() {
		return siteTeacherPriv;
	}

	public void setSiteTeacherPriv(SiteTeacherPriv siteTeacherPriv) {
		this.siteTeacherPriv = siteTeacherPriv;
	}

	public abstract InteractionUserPriv getInteractionUserPriv(String userId)
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String userId,
			String teachclassId) throws PlatformException;

	public abstract SiteTeacher getSiteTeacher() throws PlatformException;

	public abstract int getTeachClassesNum() throws PlatformException;

	public abstract List getTeachClasses(Page page) throws PlatformException;

	public abstract List getCourses(Page page, String id)
			throws PlatformException;

	/**
	 * 更新教师
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

	/**
	 * 更新教师密码
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updatePwd(String id, String oldPassword,
			String newPassword) throws PlatformException;

	/**
	 * 获取指定的OpenCourse
	 * 
	 * @param openCourseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract OpenCourse getOpenCourse(String openCourseId)
			throws PlatformException;

	public abstract CoursewareManagerPriv getCoursewareManagerPriv();
	
	public abstract BasicActivityManage createBasicActivityManage();

	public abstract ManagerPriv getManagerPriv();
	
	public abstract BasicScoreManage createBasicScoreManage();
	
	
	/**
	 * 获得毕业类型（大作业或论文）
	 * @return
	 */
	public abstract Map getGraduateTypes() throws PlatformException;
	
	/**
	 * 获取当前活动批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
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
	
	//----------------毕业大作业处理 lwx 2008-05-28
	/**
	 * 获取当前活动大作业批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
	throws PlatformException;
	/**
	 * 根据aid得到大作业批次对象
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
	throws PlatformException;
	
	//-----------------------end-------------------
   
	 /**
	  * (分站指导员已经审核确认)学员的题目调查信息表
	  * 
	  * @param page
	  * @param reg_no  学员学号
	  * @param name   姓名
	  * @param site  站点
	  * @param grade  年级
	  * @param eduTypeID  层次
	  * @param majorId  专业
	  * @param status  状态位   0为未确认;1为分站确认；2为分站驳回；3为总站确认；4为总站驳回
	  * @return
	  * @throws PlatformException
	  */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;
	
	public abstract int changeSubjectQuestionary(String id,String status,String message)throws PlatformException;

	/**
	 * (分站指导员已经审核确认)学员的开课登记信息
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
			String majorId,String status) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status)
			throws PlatformException;
	public abstract int changeRegBeginCourse(String id,String status,String message)throws PlatformException;

	/**
	 * 总站指导员需要审核的指导教师列表
	 * @param page
	 * @param teacherId
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTutorTeacherList(Page page, String teacherId,
			String name,String status) throws PlatformException;

	public abstract int getSiteTutorTeacherListNum(String reg_no, String name,String status)
			throws PlatformException;
	public abstract int changeSiteTutorTeacher(String id,String status,String message);
	/**
	 * 分站指导员已经确认的学员中期检查信息列表
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
			String majorId,String status) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status)
			throws PlatformException;
	public abstract int changeMetaphaseCheck(String id,String status,String message)throws PlatformException;
	/**
	 * 分站指导员已经确认的学员答辩评审信息列表
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
			String majorId, String type,String status) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeRejoinRequisition(String id,String status,String message);
	/**
	 * 电子论文信息列表
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
			String majorId, String type,String status) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeDiscourse(String id,String status,String message);
	
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


	/**
	 * 教学站列表
	 * 
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract List getAllSites() throws PlatformException;
	
	/**
	 * 年级列表
	 * 
	 * @return 年级列表
	 * @throws PlatformException
	 */
	public abstract List getAllGrades() throws PlatformException;
	
	
	/**
	 * 层次列表
	 * 
	 * @return 层次列表
	 * @throws PlatformException
	 */
	public abstract List getAllEduTypes() throws PlatformException;
	
	/**
	 * 专业列表
	 * 
	 * @return 专业列表
	 * @throws PlatformException
	 */
	public abstract List getAllMajors() throws PlatformException;


	 
	
	 /**
	  * (分站指导员已经审核确认)学员的题目调查信息表
	  * 
	  * @param page
	  * @param reg_no  学员学号
	  * @param name   姓名
	  * @param site  站点
	  * @param grade  年级
	  * @param eduTypeID  层次
	  * @param majorId  专业
	  * @param status  状态位   0为未确认;1为分站确认；2为分站驳回；3为总站确认；4为总站驳回
	  * @return
	  * @throws PlatformException
	  */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId) throws PlatformException;
	
	public abstract int getSubjectQuestionaryListNum2(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract List getSubjectQuestionaryList2(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	
	/**
	 * (分站指导员已经审核确认)学员的开课登记信息
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
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status,String teacherId,String semesterId)
			throws PlatformException;

 
	/**
	 * 分站指导员已经确认的学员中期检查信息列表
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
			String majorId,String status,String teacherId,String semesterId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status,String teacherId,String semesterId)
			throws PlatformException;
	/**
	 * 分站指导员已经确认的学员答辩评审信息列表
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
			String majorId, String type,String status,String teacherId) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status,String teacherId) throws PlatformException;
	/**
	 * 电子论文信息列表
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
			String majorId, String type,String status,String teacherId) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status,String teacherId) throws PlatformException;

	/**
	*查询出申报题目状态改变成总站指导教师审核通过的
	*/
	public abstract List getSubjectSearchStatusChangedStudent(String[] ids)throws PlatformException ;
	/**
	*查询出申报题目状态改变成总站指导教师审核通过的
	*/
	public abstract List getRegBeginCourseStatusChangedStudent(String[] ids)throws PlatformException ;
	/**
	*查询出申报题目状态改变成总站指导教师审核通过的
	*/
	public abstract List getMetaphaseCheckStatusChangedStudent(String[] ids) throws PlatformException;

	/**
	*查询出大作业分数状态改变成总站指导教师审核通过的
	*/
	public abstract List getExerciseScoreSubmitStatusChangedStudent(String[] ids)throws PlatformException;
	/**
	*查询出毕业设计分数状态改变成总站指导教师审核通过的
	*/
	public abstract List getDiscourseScoreSubmitStatusChangedStudent(String[] ids)throws PlatformException;
	/**
	*查询出申报题目状态改变成总站指导教师审核通过的
	*/
	public abstract List getRejoinRequesStatusChangedStudent(String[] ids) throws PlatformException;
	/**
	*查询出申报题目状态改变成总站指导教师审核通过的
	*/
	public abstract List getDiscourseStatusChangedStudent(String[] ids) throws PlatformException;
	
	/**
	 * 指导学生列表
	 * 
	 * @return 学生列表
	 */
	public abstract List getStudents(Page page,String stu_id,String name,String gradeId,String eduTypeID,String majorId)throws PlatformException ;

	/**
	 * 获得指导学生数量
	 * 
	 * @return 学生列表
	 */
	public abstract int getStudentsNum(String stu_id,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;
	
	/**
	 * 指导学生列表
	 * 
	 * @return 学生列表
	 */
	public abstract List getGraduateStudents(Page page,String regNo,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;

	/**
	 * 获得指导学生数量
	 * 
	 * @return 学生列表
	 */
	public abstract int getGraduateStudentsNum(String regNo,String name,String gradeId,String eduTypeID,String majorId) throws PlatformException;

	
	/**
	 * 获得指导学生专业列表
	 * 
	 * @return 学生列表
	 */
	public abstract List getStudentsMajors() throws PlatformException;
	
	/**
	 * 提交毕业大作业分数
	 * 
	 * @return int
	 */
	public abstract int submitExerciseScore(String subjectId,String score);
	
	/**
	 * 提交毕业大作业分数
	 * 
	 * @return int
	 */
	public abstract int submitDiscourseScore(String subjectId,String score);

	/**
	 * 查询学员免做申请列表
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId	
	 * @param piciId	毕业设计批次ID
	 * @param courseId  毕业设计课程ID
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException;
	 
	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String piciId,String courseId,String semesterId) throws PlatformException;

	
	/**
	 * 获得当前学期
	 */
	public abstract List getActiveSemeser() throws PlatformException;

	/**
	 * 判断是否可操作
	 * */
	public abstract boolean canOperateStduent(String student_id)  throws PlatformException ;
	
	/**
	 * 获得毕业批次列表
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduatePici() throws PlatformException;

	/**
	 * 获得大作业批次
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecPici() throws PlatformException;

}


