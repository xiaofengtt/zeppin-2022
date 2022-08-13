/**
 * 
 */
package com.whaty.platform.entity.user;

/**
 * @author Administrator
 *
 */
public abstract class StudentPriv {
	private String studentId;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
		
	public int getSelectTeachClasses=1;
	
	/**
	 * 是否允许登录
	 */
	public int login=1;

	/**
	 * 是否允许修改密码
	 */
	public int updatePwd=1;
	
	/**
	 * 是否允许修改个人信息
	 */
	public int updateInfo=1;

	
	/***************************************************************************
	 * 新闻管理权限 *
	 **************************************************************************/
	public int getNews = 1; // 是否可以查看新闻

	public int addNews = 1; // 是否可以添加新闻

	public int updateNews = 1; // 是否可以修改新闻

	public int deleteNews = 1; // 是否可以删除新闻

	public int putTopNews = 1; // 是否可以置顶新闻

	public int lockNews = 1; // 是否可以锁定新闻

	public int getNewsType = 1; // 是否可以查看新闻类型

	public int addNewsType = 1; // 是否可以添加新闻类型

	public int updateNewsType = 1; // 是否可以修改新闻类型

	public int deleteNewsType = 1; // 是否可以删除新闻类型

	public int setNewsTypeManagers = 1; // 是否可以设置新闻类型管理员

	public int getNewsTypeManagers = 1; // 是否可以查看新闻类型管理员

	/***************************************************************************
	 * 重要信息管理权限 *
	 **************************************************************************/
	public int getImpnote = 1; // 是否可以查看重要信息

	public int addImpnote = 1; // 是否可以添加重要信息

	public int updateImpnote = 1; // 是否可以修改重要信息

	public int deleteImpnote = 1; // 是否可以删除重要信息

	public int activeImpnote = 1; // 是否可以发布重要信息

	/***************************************************************************
	 * 基本信息管理权限 *
	 **************************************************************************/

	public int creatDep = 1;

	public int creatEduType = 1;

	public int creatGrade = 1;

	public int creatMajor = 1;

	public int creatSite = 1;

	public int createTemplate = 1;

	public int getDep = 1;// 是否可以浏览院系信息

	public int getEduType = 1;// 是否可以浏览层次信息

	public int getGrade = 1;// 是否可以浏览年级信息

	public int getMajor = 1;// 是否可以浏览专业信息

	public int getSite = 1;// 是否可以浏览教学站信息

	public int getSiteStudents = 1;// 是否可以浏览教学站下的学生信息

	public int searchSite = 1;// 是否可以查找教学站信息

	public int getTemplate = 1;

	public int getDeps = 1;// 是否可以浏览院系信息

	public int getRecruitPlans = 1;// 是否可以浏览招生计划

	public int getRecruitBatches = 1;// 是否可以浏览招生批次

	public int getEduTypes = 1;// 是否可以浏览层次信息

	public int getGrades = 1;// 是否可以浏览年级信息

	public int getMajors = 1;// 是否可以浏览专业信息

	public int getSites = 1;// 是否可以浏览教学站信息

	public int getTemplates = 1;

	public int addDep = 1;// 是否可以添加院系信息

	public int addEduType = 1;// 是否可以添加层次信息

	public int addGrade = 1;// 是否可以添加年级信息

	public int addMajor = 1;// 是否可以添加专业信息

	public int addSite = 1;// 是否可以添加教学站信息

	public int addTemplate = 1;// 是否可以添加模板

	public int updateDep = 1;// 是否可以修改院系信息

	public int updateRecruitBatch = 1; // 是否可以修改招生批次信息

	public int updateEduType = 1;// 是否可以修改层次信息

	public int updateGrade = 1;// 是否可以修改年级信息

	public int updateMajor = 1;// 是否可以修改专业信息

	public int updateSite = 1;// 是否可以修改教学站信息

	public int updateTemplate = 1;// 是否可以修改模板信息

	public int deleteDep = 1;// 是否可以删除院系信息

	public int deleteRecruitBatch = 1;// 是否可以删除院系信息

	public int deleteEduType = 1;// 是否可以删除层次信息

	public int deleteGrade = 1;// 是否可以删除年级信息

	public int deleteMajor = 1;// 是否可以删除专业信息

	public int deleteSite = 1;// 是否可以删除教学站信息

	public int deleteTemplate = 1;// 是否可以删除模板信息

	public int getRecruitCourse = 1;// 是否可以浏览考试课程列表

	public int deleteRecruitCourse = 1;// 是否可以删除院系信息

	/***************************************************************************
	 * 人员信息管理权限 *
	 **************************************************************************/

	public int creatTeacher = 1;

	public int getTeacher = 1;// 是否可以浏览教师

	public int getTeachers = 1;// 是否可以浏览教师

	public int addTeacher = 1;// 是否可以添加教师

	public int addTeacherBatch = 1;// 是否可以批量添加教师

	public int updateTeacher = 1;// 是否可以修改教师

	public int deleteTeacher = 1;// 是否可以删除教师

	public int searchTeacher = 1;// 是否可以查找教师

	public int creatStudent = 1;

	public int getStudent = 1;// 是否可以浏览学生

	public int getStudents = 1;// 是否可以浏览学生

	public int addStudent = 1;// 是否可以添加学生

	public int updateStudent = 1;// 是否可以修改学生

	public int deleteStudent = 1;// 是否可以删除学生

	public int addStudentBatch = 1;// 是否可以批量添加学生

	public int deleteStudentBatch = 1;// 是否可以批量删除学生

	public int searchStudent = 1;// 是否可以查找学生

	/***************************************************************************
	 * 教学信息管理权限 *
	 **************************************************************************/

	public int creatCourse = 1;

	public int getCourse = 1;// 是否可以浏览课程

	public int getCourses = 1;// 是否可以浏览课程

	public int addCourse = 1;// 是否可以添加课程

	public int addCourseBatch = 1;// 是否可以批量添加课程

	public int searchCourse = 1;// 是否可以查找课程

	public int updateCourse = 1;// 是否可以修改课程

	public int deleteCourse = 1;// 是否可以删除课程

	public int getCourseType = 1;// 是否可以浏览课程类型

	public int getCourseTypes = 1;// 是否可以浏览课程类型

	public int addCourseType = 1;// 是否可以添加课程类型

	public int updateCourseType = 1;// 是否可以修改课程类型

	public int deleteCourseType = 1;// 是否可以删除课程类型

	public int searchCourseware = 1;// 是否可以查找课件

	public int getCourseware = 1;// 是否可以浏览课件

	public int getCoursewares = 1;// 是否可以浏览课件

	public int addCourseware = 1;// 是否可以添加课件

	public int updateCourseware = 1;// 是否可以修改课件

	public int deleteCourseware = 1;// 是否可以删除课件

	public int getCwareType = 1;// 是否可以浏览课件类型

	public int getCwareTypes = 1;// 是否可以浏览课件类型

	public int addCwareType = 1;// 是否可以添加课件类型

	public int updateCwareType = 1;// 是否可以修改课件类型

	public int deleteCwareType = 1;// 是否可以删除课件类型

	public int getSemester = 1;// 是否可以浏览学期

	public int activeSemester = 1;// 是否可以激活学期

	public int getSemesters = 1;// 是否可以浏览学期

	public int addSemester = 1;// 是否可以添加学期

	public int updateSemester = 1;// 是否可以修改学期

	public int deleteSemester = 1;// 是否可以删除学期

	public int setCourseMajor = 1;

	public int getCourseMajor = 1;

	/***************************************************************************
	 * 招生部分管理权限 *
	 **************************************************************************/
	public int addRecruitBatch = 1;// 是否可以添加批次

	public int addRecruitCourse = 1;// 是否可以添加考试课程

	public int updateRecruitStatus = 1;// 是否解锁该批次

	public int updatePlanStatus = 1;// 是否审核该招生计划

	public int addRecruitSort = 1;// 是否可以添加专业科类

	public int getRecruitSorts = 1;// 是否可以浏览专业科类

	public int getRecruitSortMajors = 1;// 是否可以浏览专业所属科类

	public int getRecruitMajorSorts = 1;// 是否可以浏览科类所属专业

	public int setRecruitSortMajors = 1;// 是否可以设置专业所属科类

	public int getRecruitSortCourses = 1;// 是否可以浏览专业科类所属课程

	public int setRecruitSortCourses = 1;// 是否可以设置专业科类所属课程

	public int getRecruitCourseSorts = 1;// 是否可以浏览课程所属专业科类

	public int setRecruitCourseSorts = 1;// 是否可以设置课程所属专业科类

	public int getRecruitSort = 1;// 是否可以浏览专业科类

	public int updateRecruitSort = 1;// 是否可以修改专业科类

	public int deleteRecruitSort = 1;// 是否可以删除专业科类

	public int getRecruitTestCourses = 1;// 是否可以浏览考试课程

	public int addRecruitTestCourses = 1;// 是否可以添加考试课程

	public int deleteRecruitTestCourses = 1;// 是否可以删除考试课程

	public int addRecruitTestCourse = 1;// 是否可以添加考试课程

	public int deleteRecruitTestCourse = 1;// 是否可以删除考试课程

	public int getRecruitTestCourse = 1;// 是否可以浏览考试课程

	public int getRecruitTestStudent = 1;// 是否可以浏览学生

	public int getUnRecruitTestCourses = 1;// 是否可以浏览未设定考试时间的考试课程

	public int getUnRecruitTestCourse = 1;// 是否可以浏览未设定考试时间的考试课程

	public int updateRecruitStudentScore = 1;// 是否可以上报学生成绩

	public int updateRecruitStudentScoreBatch = 1;// 是否可以批量上报学生成绩

	public int getRecruitScoreStudents = 1;// 是否获得符合分数段学生人数

	public int releaseStudentScore = 1;// 是否可以发布学生成绩

	public int getPassstastic = 1;// 是否可以浏览录取学生人数

	public int getTotalStu = 1;// 是否可以浏览录取学生统计人数

	public int releaseStudentPass = 1;// 是否可以自动录取学生

	public int setStudentCon = 1;// 是否可以自动录取学生

	public int updateRecruitStudentPassstatus = 1;// 是否可以上报学生成绩

	public int getRecruitStat = 1;// 是否可以查询学生录取情况

	public int getFreeRecruitStudents = 1;// 是否获得免试学生人数

	public int getPassRecruitStudents = 1;// 是否获得录取学生人数

	/***************************************************************************
	 * 教务流程管理权限 *
	 **************************************************************************/
	public int electiveBatchBySite = 1;// 是否可以教学站批量选课

	public int electiveBatchByStudents = 1; // 是否可以学生批量选课

	public int electiveSingle = 1;// 是否可以单独选课

	public int downloadElectiveInfo = 1;// 是否可以下载选课信息表

	public int uploadElectiveInfo = 1;// 是否可以上载选课信息表

	public int confirmElectiveInfo = 1;// 是否可以确认选课

	public int registerSingle = 1;// 单个注册学生

	public int cancelRegisterSingle = 1;// 单个取消注册学生

	public int registerBatch = 1;// 批量注册学生

	public int cancelRegisterBatch = 1;// 批量取消注册学生

	public int getRegisterStudents = 1;// 是否可以获得已注册学生信息

	public int searchRegisterStudents = 1;// 是否可以查找已注册学生

	public int openCourseBySemester = 1;// 是否可以学期开课
	
	public int getOpenCourse = 1;// 是否可以获取开课信息
	
	public int getOpenCourses = 1;// 是否可以批量获取开课信息

	public int getOpenCoursesBySemester = 1;// 是否可以浏览学期开设课程

	public int addExecutePlan = 1;// 是否可以添加执行计划

	public int getExecutePlan = 1;// 是否可以浏览执行计划

	public int deleteExecutePlan = 1;// 是否可以删除执行计划

	public int addExecutePlanCourses = 1;// 是否可以添加执行计划课程

	public int getExecutePlanCourses = 1;// 是否可以浏览执行计划课程

	public int deleteExecutePlanCourses = 1;// 是否可以删除执行计划课程

	public int addTeachPlan = 1;// 是否可以添加教学计划

	public int getTeachPlan = 1;// 是否可以浏览教学计划

	public int deleteTeachPlan = 1;// 是否可以删除教学计划

	public int updateTeachPlan = 1;// 是否可以编辑教学计划

	public int addTeachPlanCourses = 1;// 是否可以添加教学计划课程

	public int getTeachPlanCourses = 1;// 是否可以浏览教学计划课程

	public int deleteTeachPlanCourses = 1;// 是否可以删除教学计划课程

	public int addSingleTeachPlanCourses = 1;// 是否可以添加个性教学计划课程

	public int getSingleTeachPlanCourses = 1;// 是否可以浏览个性教学计划课程

	public int deleteSingleTeachPlanCourses = 1;// 是否可以删除个性教学计划课程

	public int updateTeachPlanCourse = 1;// 是否可以编辑教学计划课程

	public int cancelOpenCourseBySemester = 1;// 是否可以取消学期开课

	public int appointTeacherForCourse = 1;// 是否可以为课程制定教师

	public int importUsualScore = 1;// 是否可以上报平时成绩

	public int importExamScore = 1;// 是否可以上报考试成绩

	public int importExpendScore = 1;// 是否可以上报补考成绩

	public int importScoreSingle = 1;// 是否可以单独上报成绩

	public int getScoreCard = 1;// 是否可以查看成绩单

	public int getSingleCourseCard = 1;// 是否可以查看学期单科成绩单

	public int modifyUsualScore = 1;// 是否可以修改平时成绩

	public int confirmModifyUsualScore = 1;// 是否可以确认平时成绩的修改

	public int modifyExamScore = 1;// 是否可以修改考试成绩

	public int confirmModifyExamScore = 1;// 是否可以确认考试成绩的修改

	public int importScoreBatch = 1;// 是否可以批量上报成绩

	public int generateTotalScore = 1;// 是否可以生成总评成绩

	public int modifyTotalScore = 1;// 是否可以修改总评成绩

	public int confirmModifyTotalScore = 1;// 是否可以确认总评成绩的修改

	public int modifyExpendScore = 1;// 是否可以修改补考成绩

	public int confirmModifyExpendScore = 1;// 是否可以确认补考成绩的修改

	public int signSingle = 1;// 是否可以单个报名

	public int addSignBatch = 1;// 是否可以添加报名批次

	public int updateSignBatch = 1;// 是否可以修改报名批次

	public int deleteSignBatch = 1;// 是否可以删除报名批次

	public int getSignBatch = 1;// 是否可以浏览报名批次

	public int setRecruitMajor = 1;// 是否可以设定招生专业

	public int setRecruitEdutype = 1;// 是否可以设定招生层次

	public int setRecruitSite = 1;// 是否可以设定招生站点

	public int recruitStudents = 1;// 是否可以录取学生

	public int setRecruitCourse = 1;// 是否可以设置招生考试课程

	public int getGraduatedStudents = 1;// 是否可以浏览毕业学生

	public int checkGraduated = 1;// 是否可以审核毕业

	/***************************************************************************
	 * 考试信息管理权限 *
	 **************************************************************************/
	public int getTestSite = 1; // 是否可以浏览考点

	public int addTestSite = 1; // 是否可以添加考点

	public int updateTestSite = 1; // 是否可以修改考点

	public int deleteTestSite = 1; // 是否可以删除考点

	public int assignTestSite = 1; // 是否可以分配考点

	public int getTestRoom = 1;// 是否可以浏览考场

	public int addTestRoom = 1;// 是否可以添加考场

	public int updateTestRoom = 1;// 是否可以修改考场

	public int deleteTestRoom = 1;// 是否可以删除考场

	public int getNormalStudents = 1; // 是否可以浏览学生信息

	public int getTestRoomTongji = 1; // 是否可以查看考场分布

	public int getEdutypeMajorTestDesk = 1; // 是否可以浏览层次专业考场信息

	
	
	/***************************************************************************
	 * 公告管理权限 *
	 **************************************************************************/

	public int getAnnounce = 1; // 是否可以查看公告

	public int addAnnounce = 0; // 是否可以添加公告

	public int updateAnnounce = 0; // 是否可以修改公告

	public int deleteAnnounce = 0; // 是否可以删除公告

	public int getAnnounces = 1; // 是否可以查看公告

	public int addAnnounces = 0; // 是否可以批量添加公告

	public int updateAnnounces = 0; // 是否可以批量修改公告

	public int deleteAnnounces = 0; // 是否可以批量删除公告

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

	public int addInHomework = 0; // 是否可以添加上交的作业

	public int updateInHomework = 0; // 是否可以修改上交的作业

	public int deleteInHomework = 0; // 是否可以删除上交的作业

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
}
