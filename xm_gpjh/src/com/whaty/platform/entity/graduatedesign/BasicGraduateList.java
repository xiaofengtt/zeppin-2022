package com.whaty.platform.entity.graduatedesign;

import java.util.List;
import java.util.Map;

import com.whaty.platform.util.Page;

/**
 * @author Administrator
 *
 */
public interface BasicGraduateList {
	/**
	 *查询出申报题目状态改变成总站指导教师审核通过的
	 */
	public abstract List getSubjectSearchStatusChangedStudent(String[] ids);

	/**
	 *查询出申报题目状态改变成总站指导教师审核通过的
	 */
	public abstract List getRegBeginCourseStatusChangedStudent(String[] ids);

	/**
	 *查询出申报题目状态改变成总站指导教师审核通过的
	 */
	public abstract List getMetaphaseCheckStatusChangedStudent(String[] ids);

	/**
	 *查询出申报题目状态改变成总站指导教师审核通过的
	 */
	public abstract List getRejoinRequesStatusChangedStudent(String[] ids);

	/**
	 *查询出申报题目状态改变成总站指导教师审核通过的
	 */
	public abstract List getDiscourseStatusChangedStudent(String[] ids);

	/**
	 *查询出大作业分数状态改变成总站指导教师审核通过的
	 */
	public abstract List getExerciseScoreSubmitStatusChangedStudent(String[] ids);

	/**
	 *查询出毕业设计分数状态改变成总站指导教师审核通过的
	 */
	public abstract List getDiscourseScoreSubmitStatusChangedStudent(
			String[] ids);

	/**
	 * 根据条件查询毕业设计批次
	 * @param page
	 * @param searchproperty
	 * @param orderProperty
	 * @return
	 */
	public List getGraduateDesignBatch(Page page, List searchProperty,
			List orderProperty);

	public int getGraduateDesignBatchNum(List searchProperty);

	public Pici getGraduateDesignBatch(List searchProperty);

	
	/**
	 * 获得毕业类型（大作业或论文），用于辅导教师
	 * @param teacher_id
	 * @return
	 */
	public Map getGraduateTypes(String teacher_id) ;
	
	/**
	 * 毕业设计批次所未选择的年级层次专业
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getGradeEduTypeMajors(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduTypeMajorsNum(List searchProperty);

	/**
	 * 毕业设计批次已经设置了的年级层次专业
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getGradeEduTypeMajorsForPici(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduTypeMajorsForPiciNum(List searchProperty);

	/**
	 * 题目调查信息列表
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getSubjectQuestionaryList(Page page, List searchProperty,
			List orderProperty);

	
	public List getSubjectQuestionaryList2(Page page, List searchProperty,
			List orderProperty);
	
	/**
	 * 获得可以审核或驳回的学生列表 审题
	 * 
	 * */
	public String getActiveSubjectSearchStudentIdString();

	/**
	 * 获得可以审核或驳回的学生列表  开题
	 * 
	 * */
	public String getActiveRegBeginCourseStudentIdString(List searchProperty);

	/**
	 * 获得可以审核或驳回的学生列表  中期
	 * 
	 * */
	public String getActiveMetaphaseCheckStudentIdString(List searchProperty);

	/**
	 * 获得可以审核或驳回的学生列表  答辩或讨论
	 * 
	 * */
	public String getActiveDiscourseCheckStudentIdString(List searchProperty);

	public int getSubjectQuestionaryListNum(List searchProperty);

	
	public int getSubjectQuestionaryListNum2(List searchProperty);
    /**
     * 开课登记信息列表
     */
	public List getRegBeginCourseList(Page page, List searchProperty,
			List orderProperty);
	
	   /**
     * 开课登记信息列表（用于总站教师）
     */
	public List getRegBeginCourseList1(Page page, List searchProperty,
			List orderProperty,String teacher_id);
	/**
     * 开课登记信息列表（用于分站教师）
     */
	public List getRegBeginCourseList2(Page page, List searchProperty,
			List orderProperty,String teacher_id);

	public int getRegBeginCourseListNum(List searchProperty);
	/**
	 * 查询开题信息数量（用于教师）
	 * @param searchProperty
	 * @return
	 */
	public int getRegBeginCourseListNum1(List searchProperty,String teacher_id);
	/**
	 * 查询开题信息数量（用于辅导教师）
	 * @param searchProperty
	 * @return
	 */
	public int getRegBeginCourseListNum2(List searchProperty,String teacher_id);

	/**
	 * 分站指导教师信息列表
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getSiteTutorTeacherList(Page page, List searchProperty,
			List orderProperty);

	public int getSiteTutorTeacherListNum(List searchProperty);

	/**
	 * 中期检查信息列表
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getMetaphaseCheckList(Page page, List searchProperty,
			List orderProperty);

	public int getMetaphaseCheckListNum(List searchProperty);

	/**
	 * 答辩申请信息列表
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getRejoinRequesList(Page page, List searchProperty,
			List orderProperty);

	public int getRejoinRequesListNum(List searchProperty);

	/**
	 * 电子论文信息列表
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getDisCourseList(Page page, List searchProperty,
			List orderProperty);

	public int getDisCourseListNum(List searchProperty);

	/**
	 * *********************************总站指导教师**************************************************
	 */
	public List getNoneGradeEduMajorsForTeacher(Page page, List searchProperty,
			List orderProperty);

	public int getNoneGradeEduMajorsForTeacherNum(List searchProperty);

	public List getGradeEduMajorsForTeacher(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduMajorsForTeacherNum(List searchProperty);

	//**********************************************************************************

	public List getSelectStudentAtCurrentPici(Page page, List searchProperty,
			List orderProperty);

	public int getSelectStudentAtCurrentPiciNum(List searchProperty);

	public List getSelectGraduateStudentAtCurrentPici(Page page,
			List searchProperty, List orderProperty);

	public int getSelectGraduateStudentAtCurrentPiciNum(List searchProperty);

	public List getNotSelectStudentAtCurrentPici(Page page,
			List searchProperty, List orderProperty);

	public int getNotSelectStudentAtCurrentPiciNum(List searchProperty);

	public List getStudentMajors(String teacherId);

	public int isGraduate(String studentId);

	/**
	 * 获得毕业设计免做申请学生数量
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyNum(List searchproperty, List orderproperty);

	/**
	 * 获得毕业设计免做申请学生列表
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */

	public List getStudentFreeApply(Page page, List searchproperty,
			List orderproperty);
	/**
	 * 获得毕业设计或毕业大做业免做申请学生列表
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	
	//-----------------------------
	public List getStudentFreeApplyExec(Page page, List searchproperty,
			List orderproperty);
	
	/**
	 * 获得毕业大做业免做申请学生数量
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyExecNum(List searchproperty, List orderproperty);
	
	
	//---用于对总站教师
	public int getStudentFreeApplyTutorNum(List searchproperty, List orderproperty);

	/**
	 * 获得毕业设计免做申请学生列表
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */

	public List getStudentFreeApplyTutor(Page page, List searchproperty,
			List orderproperty);
	/**
	 * 获得毕业设计或毕业大做业免做申请学生列表
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	
	//-----------------------------
	public List getStudentFreeApplyExecTutor(Page page, List searchproperty,
			List orderproperty);
	
	/**
	 * 获得毕业大做业免做申请学生数量
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyExecTutorNum(List searchproperty, List orderproperty);

}