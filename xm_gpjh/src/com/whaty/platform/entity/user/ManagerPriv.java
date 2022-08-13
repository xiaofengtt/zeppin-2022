/*
 * ManagerPriv.java
 *
 * Created on 2005年4月6日, 下午8:19
 */

package com.whaty.platform.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻、信息权限类
 * 
 * @author Chenjian
 */
public abstract class ManagerPriv {
	/***************************************************************************
	 * SSO_ID *
	 **************************************************************************/
	public String sso_id;

	/***************************************************************************
	 * 所辖范围管理 *
	 **************************************************************************/

	public List site = new ArrayList();

	public List major = new ArrayList();

	public List grade = new ArrayList();

	public List eduType = new ArrayList();

	/***************************************************************************
	 * 新闻管理权限 *
	 **************************************************************************/
	public int getNews = 0; // 是否可以查看新闻

	public int addNews = 0; // 是否可以添加新闻

	public int updateNews = 0; // 是否可以修改新闻

	public int deleteNews = 0; // 是否可以删除新闻

	public int topNews = 0; // 是否可以置顶新闻

	public int popNews = 0; // 是否可以弹出新闻

	public int lockNews = 0; // 是否可以锁定新闻

	public int confirmNews = 0; // 是否可以审核新闻

	public int copyNews = 0; // 是否可以拷贝新闻

	public int getNewsType = 1; // 是否可以查看新闻类型
	
//	public int getNews = 1; // 是否可以查看新闻
//
//	public int addNews = 1; // 是否可以添加新闻
//
//	public int updateNews = 1; // 是否可以修改新闻
//
//	public int deleteNews = 1; // 是否可以删除新闻
//
//	public int topNews = 1; // 是否可以置顶新闻
//
//	public int popNews = 1; // 是否可以弹出新闻
//
//	public int lockNews = 1; // 是否可以锁定新闻
//
//	public int confirmNews = 1; // 是否可以审核新闻
//
//	public int copyNews = 1; // 是否可以拷贝新闻
//
//	public int getNewsType = 1; // 是否可以查看新闻类型

	/***************************************************************************
	 * 投票管理权限 *
	 **************************************************************************/

	public int addVotePaper = 0; // 是否可以添加调查问卷

	public int getVotePaper = 0; // 是否可以查看调查问卷列表
	
	/***************************************************************************
	 * 短信管理权限 *
	 **************************************************************************/
	public int sendSms = 0;

	public int getSms = 0;

	public int updateSms = 0;

	public int deleteSms = 0;

	public int checkSms = 0;

	public int addSms = 0;

	public int batchImportMobiles = 0; // 是否可以批量导入移动号码

	public int manageSmsSystemPoint = 0; // 系统短信点管理
	
	public int getSmsStatistic = 0 ;
	
	public int rejectSms = 0 ; //拒绝短信
	
	

	/***************************************************************************
	 * 留言管理权限 *
	 **************************************************************************/

	public int getLeaveWord = 0; // 是否可以查看留言列表

	public int totalLeaveWord = 0; // 是否可以查看留言统计
//	public int getLeaveWord = 0; // 是否可以查看留言列表
//	
//	public int totalLeaveWord = 0; // 是否可以查看留言统计

	/***************************************************************************
	 * 资源管理权限 *
	 **************************************************************************/

	public int resourceManage = 0; // 是否可以资源管理

	public int resourceTypeManage = 0; // 是否可以资源类型管理

	/***************************************************************************
	 * 基本信息管理权限 *
	 **************************************************************************/

	public int creatDep = 1;

	public int creatEduType = 1;

	public int creatGrade = 1;

	public int creatMajor = 1;

	public int creatSite = 1;

	public int createTemplate = 1;

	// Site
	public int addSite = 1;// 是否可以添加教学站信息

	public int deleteSite = 1;// 是否可以删除教学站信息

	public int getSite = 1;// 是否可以浏览教学站信息

	public int updateSite = 1;// 是否可以修改教学站信息

	// Dep
	public int addDep = 1;// 是否可以添加院系信息

	public int deleteDep = 1;// 是否可以删除院系信息

	public int getDep = 1;// 是否可以浏览院系信息

	public int updateDep = 1;// 是否可以修改院系信息

	// Grade
	public int addGrade = 1;// 是否可以添加年级信息

	public int deleteGrade = 1;// 是否可以删除年级信息

	public int getGrade = 1;// 是否可以浏览年级信息

	public int updateGrade = 1;// 是否可以修改年级信息

	// EduType
	public int addEduType = 1;// 是否可以添加层次信息

	public int deleteEduType = 1;// 是否可以删除层次信息

	public int getEduType = 1;// 是否可以浏览层次信息

	public int updateEduType = 1;// 是否可以修改层次信息

	// Major
	public int addMajor = 1;// 是否可以添加专业信息

	public int deleteMajor = 1;// 是否可以删除专业信息

	public int getMajor = 1;// 是否可以浏览专业信息

	public int updateMajor = 1;// 是否可以修改专业信息

	// Template

	public int addTemplate = 1;// 是否可以添加模板

	public int deleteTemplate = 1;// 是否可以删除模板信息

	public int getTemplate = 1;

	public int updateTemplate = 1;// 是否可以修改模板信息

	/***************************************************************************
	 * 教师信息管理权限 *
	 **************************************************************************/
	public int creatTeacher = 1; // 是否返回一个teacher对象

	public int addTeacher = 1;// 是否可以添加教师

	public int addTeacherBatch = 1;// 是否可以批量添加教师

	public int deleteTeacher = 1;// 是否可以删除教师

	public int getTeacher = 1;// 是否可以浏览教师

	public int updateTeacher = 1;// 是否可以修改教师

	/***************************************************************************
	 * 辅导教师信息管理权限 *
	 **************************************************************************/
	public int creatSiteTeacher = 1; // 是否返回一个teacher对象

	public int addSiteTeacher = 1;// 是否可以添加教师

	public int addSiteTeacherBatch = 1;// 是否可以批量添加教师

	public int deleteSiteTeacher = 1;// 是否可以删除教师

	public int getSiteTeacher = 1;// 是否可以浏览教师

	public int updateSiteTeacher = 1;// 是否可以修改教师

	/***************************************************************************
	 * 学生信息管理权限 *
	 **************************************************************************/
	public int creatStudent = 1; // 是否返回一个student对象

	public int addStudent = 1;// 是否可以添加学生

	public int updateStudentPwd = 1;

	public int deleteStudent = 1;// 是否可以删除学生

	public int getStudent = 1;// 是否可以浏览学生

	public int updateStudent = 1;// 是否可以修改学生

	public int addStudentBatch = 1;// 是否可以批量添加学生

	public int deleteStudentBatch = 1;// 是否可以批量删除学生

	public int uploadImage = 1; // 是否可上传学生照片

	public int uploadIdCard = 1; // 是否可上传身份证

	public int uploadGraduateCard = 1; // 是否可上传学生毕业证

	// public int uploadBatchImage = 1; // 是否可批量上传学生照片

	/***************************************************************************
	 * 教学信息管理权限 *
	 **************************************************************************/
	// Course
	public int creatCourse = 1;

	public int addCourse = 1;// 是否可以添加课程

	public int addCourseBatch = 1;// 是否可以批量添加课程

	public int deleteCourse = 1;// 是否可以删除课程

	public int getCourse = 1;// 是否可以浏览课程

	public int updateCourse = 1;// 是否可以修改课程

	// CourseType
	public int addCourseType = 1;// 是否可以添加课程类型

	public int deleteCourseType = 1;// 是否可以删除课程类型

	public int getCourseType = 1;// 是否可以浏览课程类型

	public int updateCourseType = 1;// 是否可以修改课程类型

	// Courseware
	public int addCourseware = 1;// 是否可以添加课件

	public int deleteCourseware = 1;// 是否可以删除课件

	public int getCourseware = 1;// 是否可以浏览课件

	public int updateCourseware = 1;// 是否可以修改课件

	// CwareType
	public int addCwareType = 1;// 是否可以添加课件类型

	public int deleteCwareType = 1;// 是否可以删除课件类型

	public int getCwareType = 1;// 是否可以浏览课件类型

	public int updateCwareType = 1;// 是否可以修改课件类型

	// Semester
	public int addSemester = 1;// 是否可以添加学期

	public int activeSemester = 1;// 是否可以激活学期

	public int deleteSemester = 1;// 是否可以删除学期

	public int getSemester = 1;// 是否可以浏览学期

	public int updateSemester = 1;// 是否可以修改学期

	// changeStatus
	public int changeStatus = 1;// 是否可以进行学籍异动

	public int cancelChangeStatus = 1;// 是否可以取消学籍异动

	/***************************************************************************
	 * 招生部分管理权限 *
	 **************************************************************************/
	// RecruitBatch
	public int addRecruitBatch = 1;// 是否可以添加批次

	public int deleteRecruitBatch = 1;// 是否可以删除批次信息

	public int getRecruitBatch = 1;// 是否可以浏览批次信息

	public int updateRecruitBatch = 1; // 是否可以修改招生批次信息

	public int updateRecruitBatchStatus = 1;// 是否解锁该批次

	public int getRecruitMajors = 1;// 是否可以获取招生专业

	public int deleteRecruitBatchMajor = 1;// 是否可以删除招生批次专业

	public int addRecruitBatchMajor = 1;// 是否可以添加招生批次专业

	// RecruitPlan
	public int getRecruitPlan = 1;// 是否可以浏览招生计划

	public int updatePlanStatus = 1;// 是否审核该招生计划

	public int getBatchEdutype = 1;//是否可以查看招生批次的层次
	
	public int addRecruitPlan = 1;//是否可以添加招生计划
	// RecruitCourse
	public int addRecruitCourse = 1;// 是否可以添加考试课程

	public int deleteRecruitCourse = 1;// 是否可以删除院系信息

	public int getRecruitCourse = 1;// 是否可以浏览考试课程列表

	public int updateRecruitCourse = 1;// 是否可以修改考试课程

	public int getRecruitCourseSort = 1;// 是否可以浏览课程所属专业科类

	public int setRecruitCourseSort = 1;// 是否可以设置课程所属专业科类

	// RecruitSort
	public int addRecruitSort = 1;// 是否可以添加专业科类

	public int deleteRecruitSort = 1;// 是否可以删除专业科类

	public int getRecruitSort = 1;// 是否可以浏览专业科类

	public int updateRecruitSort = 1;// 是否可以修改专业科类

	public int getRecruitSortMajor = 1;// 是否可以浏览专业所属科类

	public int getRecruitMajorSort = 1;// 是否可以浏览科类所属专业

	public int setRecruitSortMajor = 1;// 是否可以设置专业所属科类

	public int getRecruitSortCourse = 1;// 是否可以浏览专业科类所属课程

	public int setRecruitSortCourse = 1;// 是否可以设置专业科类所属课程

	// RecruitTestCourses
	public int addRecruitTestCourse = 1;// 是否可以添加考试课程

	public int deleteRecruitTestCourse = 1;// 是否可以删除考试课程

	public int getRecruitTestCourse = 1;// 是否可以浏览考试课程

	public int getUnRecruitTestCourse = 1;// 是否可以浏览未设定考试时间的考试课程

	public int updateRecruitTestCourse = 1; // 是否可以修改考试课程

	// RecruitStudent
	public int addRecruitStudent = 1;// 是否可以添加报名学生信息

	public int getRecruitStudent = 1;// 是否可以查看报名学生信息

	public int updateRecruitStudent = 1;// 是否可以修改学生报名信息

	public int getSignStatistic = 1;// 是否可以查看报名信息统计

	// RecruitTestStudent
	public int getRecruitTestStudent = 1;// 是否可以浏览学生

	// Score
	public int releaseStudentScore = 1;// 是否可以发布学生成绩

	public int updateRecruitStudentScore = 1;// 是否可以上报学生成绩

	public int updateRecruitStudentScoreBatch = 1;// 是否可以批量上报学生成绩

	public int getRecruitScoreStudent = 1;// 是否获得符合分数段学生人数

	// Pass
	public int releaseStudentPass = 1;// 是否可以自动录取学生

	public int updateRecruitStudentPassstatus = 1;// 是否可以修改学生录取状态

	public int setStudentCon = 1;// 是否可以自动录取学生

	public int getPassRecruitStudent = 1;// 是否获得录取学生人数

	public int getRegStudent = 1;// 是否可以获得已注册学生信息

	public int getPassstastic = 1;// 是否可以浏览录取学生人数

	public int getTotalStu = 1;// 是否可以浏览录取学生统计人数

	// NoExam
	public int addRecruitNoExamCondition = 1;// 是否可以添加专业免试条件

	public int getRecruitNoExamCondition = 1;// 是否可以浏览免试条件

	public int updateRecruitNoExamCondition = 1;// 是否可以修改免试条件

	public int deleteRecruitNoExamCondition = 1;// 是否可以删除免试条件

	public int getFreeRecruitStudent = 1; // 是否可浏览免试学生信息

	public int getConfirmFreeStudent = 1; // 是否可确认免试学生信息

	public int getUnConfirmFreeStudent = 1; // 是否可取消确认免试学生信息

	// report
	public int getRecruitStat = 1;// 是否可以打印报表

	// 学习中心异动
	public int changeSite = 1;// 是否可以学习中心异动

	/***************************************************************************
	 * 教务流程管理权限 *
	 **************************************************************************/
	// register
	public int generateRegNo = 1;// 是否可以生成学号

	public int publishRegNo = 1;// 是否可以发布学号

	public int cancelRegisterSingle = 1;// 单个取消注册学生

	public int getRegisterStudent = 1;// 是否可以获得已注册学生信息

	public int registerSingle = 1;// 单个注册学生

	public int registerBatch = 1;// 批量注册学生

	public int cancelRegisterBatch = 1;// 批量取消注册学生

	public int setRegisterDateTime = 1;// 设定注册时间

	// TeachPlan
	public int addTeachPlan = 1;// 是否可以添加教学计划

	public int deleteTeachPlan = 1;// 是否可以删除教学计划

	public int getTeachPlan = 1;// 是否可以浏览教学计划

	public int updateTeachPlan = 1;// 是否可以编辑教学计划

	// TeachPlanCourse
	public int addTeachPlanCourse = 1;// 是否可以添加教学计划课程

	public int deleteTeachPlanCourse = 1;// 是否可以删除教学计划课程

	public int getTeachPlanCourse = 1;// 是否可以浏览教学计划课程

	public int updateTeachPlanCourse = 1;// 是否可以编辑教学计划课程

	// SingleTeachPlanCourse
	public int addSingleTeachPlanCourse = 1;// 是否可以添加个性教学计划课程

	public int deleteSingleTeachPlanCourse = 1;// 是否可以删除个性教学计划课程

	public int getSingleTeachPlanCourse = 1;// 是否可以浏览个性教学计划课程

	// ExecutePlan
	public int addExecutePlan = 1;// 是否可以添加执行计划

	public int deleteExecutePlan = 1;// 是否可以删除执行计划

	public int getExecutePlan = 1;// 是否可以浏览执行计划

	public int updateExecutePlan = 1;// 是否可以修改执行计划

	// ExecutePlanCourse
	public int addExecutePlanCourse = 1;// 是否可以添加执行计划课程

	public int deleteExecutePlanCourse = 1;// 是否可以删除执行计划课程

	public int getExecutePlanCourse = 1;// 是否可以浏览执行计划课程

	public int addExecutePlanCourseGroup = 1;// 是否可以添加执行计划课程组

	public int getExecutePlanCourseGroup = 1;// 是否可以查看执行计划课程组

	// openCourse
	public int openCourseBySemester = 1;// 是否可以学期开课
	

	public int cancelOpenCourseBySemester = 1;// 是否可以取消学期开课

	public int getOpenCoursesBySemester = 1;// 是否可以浏览学期开设课程

	// elective
	public int electiveStudent = 1;// 是否可以浏览选课学生

	public int electiveBatchBySite = 1;// 是否可以教学站批量选课

	public int electiveBatchByStudent = 1; // 是否可以学生批量选课

	public int electiveSingle = 1;// 是否可以单独选课

	public int downloadElectiveInfo = 1;// 是否可以下载选课信息表

	public int uploadElectiveInfo = 1;// 是否可以上载选课信息表

	public int confirmElectiveInfo = 1;// 是否可以确认选课

	// Assistance
	public int addAssistance = 1;// 是否可以添加辅导信息

	public int deleteAssistance = 1;// 是否可以删除辅导信息

	public int updateAssistance = 1;// 是否可以修改辅导信息

	public int releaseAssistance = 1;// 是否可以发布辅导信息到课程交流园地中的公告

	// Course TeachClass
	public int appointTeacherForCourse = 1;// 是否可以为课程制定教师

	public int addTeachClass = 1;// 是否可以添加教学班

	public int deleteTeachClass = 1;// 是否可以删除教学班

	public int getTeachClass = 1;// 是否可以查看教学班

	// Graduate
	public int addGraduateCondition = 1;// 是否可以添加毕业条件

	public int deleteGraduateCondition = 1;// 是否可以删除毕业条件

	public int getGraduateCondition = 1;// 是否可以浏览毕业条件

	public int getGraduatedStudent = 1;// 是否可以浏览毕业学生

	public int checkGraduated = 1;// 是否可以审核毕业

	public int setGraduated = 1;// 是否可以执行毕业操作

	public int cancleGraduated = 1;// 是否可以执行取消毕业操作

	public int setGraduateNo = 1;// 是否可以上报毕业证书

	// Degree
	public int addDegreeCondition = 1;// 是否可以添加学位条件

	public int deleteDegreeCondition = 1;// 是否可以删除学位条件

	public int getDegreeCondition = 1;// 是否可以浏览学位条件

	public int getDegreedStudent = 1;// 是否可以浏览学位学生

	public int checkDegreed = 1;// 是否可以审核学位

	public int setDegreed = 1;// 是否可以执行学位操作

	public int cancleDegreed = 1;// 是否可以执行取消学位操作

	public int setDegreeScore = 1;// 是否可以上报学位成绩

	public int setDegreeScoreRelease = 1;// 是否可以上报学位成绩发布

	// 学习管理
	public int mockTeacher = 1;// 是否可以模拟教师登陆

	public int mockStudent = 1;// 是否可以模拟学生登陆

	// 证书管理

	public int addCerificateNO = 1;// 是否可以录入证书号码

	/***************************************************************************
	 * 考试信息管理权限 *
	 **************************************************************************/
	// TestSite
	public int addTestSite = 1; // 是否可以添加考点

	public int deleteTestSite = 1; // 是否可以删除考点

	public int getTestSite = 1; // 是否可以浏览考点

	public int updateTestSite = 1; // 是否可以修改考点

	public int assignTestSite = 1; // 是否可以分配考点

	// TestRoom
	public int addTestRoom = 1;// 是否可以添加考场

	public int deleteTestRoom = 1;// 是否可以删除考场

	public int getTestRoom = 1;// 是否可以浏览考场

	public int updateTestRoom = 1;// 是否可以修改考场

	public int getTestRoomTongji = 1; // 是否可以查看考场分布

	// Student
	public int getNormalStudents = 1; // 是否可以浏览学生信息

	public int getEdutypeMajorTestDesk = 1; // 是否可以浏览层次专业考场信息

	// UniteExamCourse
	public int addUniteExamCourse = 1;// 是否可以添加统考课程

	public int addBatchUniteExamScore = 1;// 是否可以批量导入统考成绩信息

	public int delUniteExamCourse = 1;// 是否可删除统考课程

	public int delBatchUniteExamCourse = 1;// 是否可批量删除统考课程

	public int getUniteExamCourses = 1;// 是否可以查询统考课程

	public int getUniteExamScores = 1; // 是否可以获取统考成绩信息

	public int updateUniteExamCourse = 1;// 是否可以修改统考课程

	// 考试数据

	public int manageBasicSequence = 1;// 是否可以管理场次

	public int manageExamBatch = 1;// 是否可以管理考试批次

	public int manageExamSequence = 1;// 是否可以管理考试场次

	public int importExamData = 1;// 是否可以导入考试数据

	public int getExamCourse = 1;// 是否可以查询考试课程

	public int getExamStudent = 1;// 是否可以查询考试学生

	// 考试安排

	public int allotExamRoom = 1;// 是否可以手动分配考场

	public int examRoomSign = 1;// 是否可以查看考场签到表

	public int arrangeExam = 1;// 是否可以安排考试

	public int examPaper = 1;// 是否可以试卷清单

	public int totalExamStudent = 1;// 是否可以考试人数统计

	public int totalExamRoom = 1;// 是否可以查看考场分布

	/***************************************************************************
	 * 成绩管理 *
	 **************************************************************************/

	// public int getElectiveScores = 1;// 是否可以获得选课成绩信息
	// public int getTotalElectiveScores = 1;// 是否可以获得总选课成绩信息
	// public int makeExpendList = 1;// 是否可以生成补考成绩信息
	// public int getElectiveExpendScores = 1;// 是否可以获得补考成绩信息
	// public int getElectiveScoresStaticsTotalScore = 1;// 是否可以获得总评成绩信息
	// public int getElectiveScoresStaticsExamScore = 1;// 是否可以获得考试成绩信息
	/***************************************************************************
	 * 个人信息管理 *
	 **************************************************************************/

	public int updatePwd = 1;// 是否可以修改密码

	/***************************************************************************
	 * 教材管理 *
	 **************************************************************************/

	public int addTeachBook = 1; // 可以添加教材

	public int deleteTeachBook = 1; // 可以删除教材

	public int getTeachBook = 1; // 可以查看教材

	public int updateTeachBook = 1; // 可以修改教材

	public int appointTeachbookForCourse = 1; // 可以给课程指定教材

	public int appointCourseForTeachbook = 1; // 可以给教材指定课程

	public int viewTeachbookForCourse = 1;// 可以查看已经给课程指定了的教材

	/** ************************************************************************ */

	/***************************************************************************
	 * 费用管理 *
	 **************************************************************************/

	public int addFeeStandard = 1; // 是否可以添加学费标准

	public int getFeeStandard = 1; // 是否可以查看学费标准

	public int addFee = 1; // 是否可以添加学费标准

	public int getFee = 1; // 是否可以查看学费标准

	public int getFeeByTime = 1; // 是否可以按照时间断查询学费

	public int getStuOtherFee = 1; // 是否可以查看学生杂费

	public int getStuOtherFeeByTime = 1; // 是否可以按照时间段查询学生杂费

	public int getStuFeeReturnApply = 1; // 是否可以查看学生退费申请

	public int getConfirmOrder = 1; // 是否可以确认订单

	public int getReConfirmOrder = 1; // 是否可以重新确认订单

	public int getSiteFeeStat = 1; // 是否可以查看教学站学费统计


	/***************************************************************************
	 * 邮件管理权限 *
	 **************************************************************************/
	public int sendMail = 1;

	public int getMail = 1;

	public int updateMail = 1;

	public int deleteMail = 1;

	public int checkMail = 1;

	public int addMail = 1;

	public int rejectMail = 1;

	public int batchImportMails = 1; // 是否可以批量导入email

	public int getMailStatistic = 1; // 是否可以查看统计信息

	/***************************************************************************
	 * 毕业设计与毕业大作业批次权限 lwx 2118-16-15
	 **************************************************************************/
	public int addGradudateDesignPici = 1; //是否可以添加毕业设计批次
	
	public int updateGraduateDesignPici = 1; //是否可以修改毕业设计批次
	
	public int deleteGraduateDesignPici = 1;//是否可以删除毕业设计批次
	
	public int getGraduateDesignPici = 1; //是否可以浏览毕业设计批次
	
	public int addGradudateDesignPiciExec = 1; //是否可以添加毕业大作业批次
	
	public int updateGraduateDesignPiciExec = 1; //是否可以修改毕业大作业批次
	
	public int deleteGraduateDesignPiciExec = 1;//是否可以删除毕业大作业批次
	
	public int getGraduateDesignPiciExec = 1; //是否可以浏览毕业大作业批次
	
	public int getGradeEdutypeMajor = 1;  //是否可以获得年级层次专业
	
	public int getSubjectQuestionary = 1; //是否可以浏览毕业大作业
	
	public int getStudentFreeApply = 1; //是否可以浏览毕业设计的免做申请;
	
	public int getStudentFreeApplyExec = 1; //是否可以浏览毕业大作业的免做申请;
	
	public int getRegBeginCourse = 1;  //是否可以浏览毕业设计开题报告;
	
	public int getMetaphaseCheck = 1; //是否可以浏览毕业设计中期检查信息;
	
	public int getRejoinRequisition = 1; //是否要以浏览毕业设计答辩评审表;
	
	public int getDiscourse = 1; //是否可以浏览毕业设计终稿;
	
	public int updateDiscourseGrade = 1; //是否可以修改毕业设计的成绩;
	
	public int updateGraduateHomeWork = 1; //是否可以录入成绩;  
	
	public int getSiteTutorTeacher = 1; //是否要以浏览分站指导教师;
	
	public int getGradeEduTypeMajorForTeacher = 1; //是否可以浏览总站指导教师指导的年级专业层次;
	
	public int addGradeEduTypeMajorsForTeacher = 1; //是否可以指定总站指导教师的年级专业层次;
	
	public int removeGradeEduTypeMajorsForTeahcer = 1; //是否可以删除总站教师指定的年级专业层次;
	
	public int getGraduateSiteTeacherMajors = 1; //是否可以浏览分站指导教师辅导的专业;
	

	
	
	// 待加

	public String getSso_id() {
		return sso_id;
	}

	public void setSso_id(String sso_id) {
		this.sso_id = sso_id;
	}

	public void setSite(List site) {

		this.site = site;

	}

	public List getSite() {

		return site;

	}

	public abstract String getSiteList();

	public void setMajor(List major) {

		this.major = major;

	}

	public List getMajor() {

		return major;

	}

	public abstract String getMajorList();

	public void setGrade(List grade) {

		this.grade = grade;

	}

	public List getGrade() {

		return grade;

	}

	public abstract String getGradeList();

	public void setEduType(List eduType) {

		this.eduType = eduType;

	}

	public List getEduType() {

		return eduType;

	}

	public abstract String getEduTypeList();

}
