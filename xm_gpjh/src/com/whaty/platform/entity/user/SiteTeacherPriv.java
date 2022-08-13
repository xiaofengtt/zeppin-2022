/*
 * TeacherPriv.java
 *
 * Created on 2005年4月6日, 下午5:20
 */

package com.whaty.platform.entity.user;

/**
 * 
 * @author Administrator
 */
public abstract class SiteTeacherPriv {

	private String teacherId;

	private String siteId;

	public String getTeacherId() {
		return teacherId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeachClasses = 1;

	public int getTeacher = 1;

	public int getTeachers = 1;

	public int updateTeacher = 1;

	public int updatePwd = 1;

	public int getCourses = 1;

	public int getCourseware = 1;

	public int getCoursewareType = 1;

	public int getCoursewareTypes = 1;

	public int addCourseware = 1;

	public int enterclass = 1;

	public int getOpenCourse = 1;

	public int getOpenCourses = 1;

	/***************************************************************************
	 * 公告管理权限 *
	 **************************************************************************/

	public int getAnnounce = 1; // 是否可以查看公告

	public int addAnnounce = 1; // 是否可以添加公告

	public int updateAnnounce = 1; // 是否可以修改公告

	public int deleteAnnounce = 1; // 是否可以删除公告

	public int getAnnounces = 1; // 是否可以查看公告

	public int addAnnounces = 1; // 是否可以批量添加公告

	public int updateAnnounces = 1; // 是否可以批量修改公告

	public int deleteAnnounces = 1; // 是否可以批量删除公告

	/***************************************************************************
	 * 作业管理权限 *
	 **************************************************************************/

	public int getHomework = 1; // 是否可以查看作业

	public int addHomework = 1; // 是否可以添加作业

	public int updateHomework = 1; // 是否可以修改作业

	public int deleteHomework = 1; // 是否可以删除作业

	public int getHomeworks = 1; // 是否可以查看作业

	public int addHomeworks = 1; // 是否可以批量添加作业

	public int updateHomeworks = 1; // 是否可以批量修改作业

	public int deleteHomeworks = 1; // 是否可以批量删除作业

	/***************************************************************************
	 * 作业点评管理权限 *
	 **************************************************************************/

	public int getHomeworkCheck = 1; // 是否可以查看作业点评

	public int addHomeworkCheck = 1; // 是否可以添加作业点评

	public int updateHomeworkCheck = 1; // 是否可以修改作业点评

	public int deleteHomeworkCheck = 1; // 是否可以删除作业点评

	public int getHomeworkChecks = 1; // 是否可以查看作业点评

	public int addHomeworkChecks = 1; // 是否可以批量添加作业点评

	public int updateHomeworkChecks = 1; // 是否可以批量修改作业点评

	public int deleteHomeworkChecks = 1; // 是否可以批量删除作业点评

	/***************************************************************************
	 * 上交的作业管理权限 *
	 **************************************************************************/

	public int getInHomework = 1; // 是否可以查看上交的作业

	public int addInHomework = 1; // 是否可以添加上交的作业

	public int updateInHomework = 1; // 是否可以修改上交的作业

	public int deleteInHomework = 1; // 是否可以删除上交的作业

	public int getInHomeworks = 1; // 是否可以查看上交的作业

	public int addInHomeworks = 1; // 是否可以添加批量上交的作业

	public int updateInHomeworks = 1; // 是否可以批量修改上交的作业

	public int deleteInHomeworks = 1; // 是否可以批量删除上交的作业

	/***************************************************************************
	 * 上交的作业点评管理权限 *
	 **************************************************************************/

	public int getInHomeworkCheck = 1; // 是否可以查看上交的作业点评

	public int addInHomeworkCheck = 1; // 是否可以添加上交的作业点评

	public int updateInHomeworkCheck = 1; // 是否可以修改上交的作业点评

	public int deleteInHomeworkCheck = 1; // 是否可以删除上交的作业点评

	public int getInHomeworkChecks = 1; // 是否可以查看上交的作业点评

	public int addInHomeworkChecks = 1; // 是否可以批量添加上交的作业点评

	public int updateInHomeworkChecks = 1; // 是否可以批量修改上交的作业点评

	public int deleteInHomeworkChecks = 1; // 是否可以批量删除上交的作业点评

	/***************************************************************************
	 * 答疑问题管理权限 *
	 **************************************************************************/

	public int getQuestion = 1; // 是否可以查看答疑问题

	public int addQuestion = 1; // 是否可以添加答疑问题

	public int updateQuestion = 1; // 是否可以修改答疑问题

	public int deleteQuestion = 1; // 是否可以删除答疑问题

	public int getQuestions = 1; // 是否可以查看答疑问题

	public int addQuestions = 1; // 是否可以批量添加答疑问题

	public int updateQuestions = 1; // 是否可以批量修改答疑问题

	public int deleteQuestions = 1; // 是否可以批量删除答疑问题

	/***************************************************************************
	 * 答疑答案管理权限 *
	 **************************************************************************/

	public int getAnswer = 1; // 是否可以查看答疑答案

	public int addAnswer = 1; // 是否可以添加答疑答案

	public int updateAnswer = 1; // 是否可以修改答疑答案

	public int deleteAnswer = 1; // 是否可以删除答疑答案

	public int getAnswers = 1; // 是否可以查看答疑答案

	public int addAnswers = 1; // 是否可以批量添加答疑答案

	public int updateAnswers = 1; // 是否可以批量修改答疑答案

	public int deleteAnswers = 1; // 是否可以批量删除答疑答案

	/***************************************************************************
	 * 常见问题答疑问题管理权限 *
	 **************************************************************************/

	public int getEliteQuestion = 1; // 是否可以查看常见问题答疑问题

	public int addEliteQuestion = 1; // 是否可以添加常见问题答疑问题

	public int updateEliteQuestion = 1; // 是否可以修改常见问题答疑问题

	public int deleteEliteQuestion = 1; // 是否可以删除常见问题答疑问题

	public int getEliteQuestions = 1; // 是否可以查看常见问题答疑问题

	public int addEliteQuestions = 1; // 是否可以批量添加常见问题答疑问题

	public int updateEliteQuestions = 1; // 是否可以批量修改常见问题答疑问题

	public int deleteEliteQuestions = 1; // 是否可以批量删除常见问题答疑问题

	/***************************************************************************
	 * 毕业设计与毕业大作业批次权限 lwx 2008-06-15
	 **************************************************************************/
	public int getGraduateDesignPici = 1; //是否可以浏览毕业设计批次
	
	public int getGraduateDesignPiciExec = 1; //是否可以浏览毕业大作业批次
	
	public int getGradeEdutypeMajor = 1;  //是否可以获得年级层次专业
	
	public int getSubjectQuestionary = 1; //是否可以浏览毕业大作业
	
	public int getStudentFreeApply = 1; //是否可以浏览毕业设计的免做申请;
	
	public int getStudentFreeApplyExec = 1; //是否可以浏览毕业大作业的免做申请;
	
	public int getRegBeginCourse = 1;  //是否可以浏览毕业设计开题报告;
	
	public int getMetaphaseCheck = 1; //是否可以浏览毕业设计中期检查信息;
	
	public int getRejoinRequisition = 1; //是否要以浏览毕业设计答辩评审表;
	
	public int getDiscourse = 1; //是否可以浏览毕业设计终稿;
	
	public int getSiteTutorTeacher = 1; //是否要以浏览分站指导教师;
	
	public int getGradeEduTypeMajorForTeacher = 1; //是否可以浏览总站指导教师指导的年级专业层次;
	
	public int getGraduateSiteTeacherMajors = 1; //是否可以浏览分站指导教师辅导的专业;
	
	public int changeSubjectQuestionary  = 1; //是否可以修改毕业大作业状态;
	
	public int changeRegBeginCourse = 1; // 是否可以修改毕业设计开题报告状态;
	
	public int changeMetaphaseCheck = 1; //是否可以修改毕业设计中期的状态;
	
}
