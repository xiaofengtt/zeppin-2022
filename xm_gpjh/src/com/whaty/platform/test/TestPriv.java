/**
 * 
 */
package com.whaty.platform.test;

/**
 * @author wq
 * 
 */
public abstract class TestPriv {
	/***************************************************************************
	 * 知识点管理权限 *
	 **************************************************************************/
	public int getLore = 1; // 是否可以查看知识点

	public int addLore = 1; // 是否可以添加添加知识点

	public int updateLore = 1; // 是否可以修改知识点

	public int deleteLore = 1; // 是否可以删除知识点

	public int getLores = 1; // 是否可以查看知识点

	public int addLores = 1; // 是否可以批量添加知识点

	public int updateLores = 1; // 是否可以批量修改知识点

	public int deleteLores = 1; // 是否可以批量删除知识点

	/***************************************************************************
	 * 知识点目录管理权限 *
	 **************************************************************************/
	public int getLoreDir = 1; // 是否可以查看知识点目录

	public int addLoreDir = 1; // 是否可以添加添加知识点目录

	public int updateLoreDir = 1; // 是否可以修改知识点目录

	public int deleteLoreDir = 1; // 是否可以删除知识点目录

	public int getLoreDirs = 1; // 是否可以查看知识点目录

	public int addLoreDirs = 1; // 是否可以批量添加知识点目录

	public int updateLoreDirs = 1; // 是否可以批量修改知识点目录

	public int deleteLoreDirs = 1; // 是否可以批量删除知识点目录

	/***************************************************************************
	 * 试题管理权限 *
	 **************************************************************************/
	public int getStoreQuestion = 1; // 是否可以查看题库

	public int updateStoreQuestion = 1; // 是否可以修改题库

	public int addStoreQuestion = 1; // 是否可以添加题库

	public int deleteStoreQuestion = 1; // 是否可以删除题库

	public int getStoreQuestions = 1; // 是否可以查看题库列表

	public int updateStoreQuestions = 1; // 是否可以修改题库列表

	public int addStoreQuestions = 1; // 是否可以添加题库列表

	public int deleteStoreQuestions = 1; // 是否可以删除题库列表

	public int getPaperQuestion = 1; // 是否可以查看题库

	public int updatePaperQuestion = 1; // 是否可以修改题库

	public int addPaperQuestion = 1; // 是否可以添加题库

	public int deletePaperQuestion = 1; // 是否可以删除题库

	public int getPaperQuestions = 1; // 是否可以查看题库列表

	public int updatePaperQuestions = 1; // 是否可以修改题库列表

	public int addPaperQuestions = 1; // 是否可以添加题库列表

	public int deletePaperQuestions = 1; // 是否可以删除题库列表

	/***************************************************************************
	 * 组卷管理权限 *
	 **************************************************************************/
	public int addPaperPolicy = 1;// 是否可以添加组卷策略

	public int deletePaperPolicy = 1;// 是否可以删除组卷策略

	public int updatePaperPolicy = 1;// 是否可以修改组卷策略

	public int getPaperPolicy = 1;// 是否可以查看组卷策略

	public int getPaperPolicys = 1;// 是否可以查看组卷策略

	/***************************************************************************
	 * 在线自测课程管理权限 *
	 **************************************************************************/
	public int addOnlineTestCourse = 1;// 是否可以添加自测课程

	public int deleteOnlineTestCourse = 1;// 是否可以删除自测课程

	public int updateOnlineTestCourse = 1;// 是否可以修改自测课程

	public int getOnlineTestCourse = 1;// 是否可以查看自测课程

	public int getOnlineTestCourses = 1;// 是否可以查看自测课程

	public int getTestPapersByOnlineTestCourse = 1;// 是否可以查看自测试卷

	public int addTestPaperByOnlineTestCourse = 1;// 是否可以添加自测试卷

	public int deleteTestPaperByOnlineTestCourse = 1;// 是否可以删除自测试卷

	public int changeOnlineTestCourseStatus = 1;// 是否可以修改在线自测课程状态

	/***************************************************************************
	 * 在线自测试卷管理权限 *
	 **************************************************************************/

	public int getTestPaper = 1;// 是否可以查看试卷

	public int getTestPapers = 1; // 是否可以查看试卷列表

	public int addTestPaper = 1;// 是否可以添加试卷

	public int updateTestPaper = 1;// 是否可以修改试卷

	public int deleteTestPaper = 1;// 是否可以删除试卷

	public int changeTestPaperStatus = 1;// 是否可以修改试卷状态

	/***************************************************************************
	 * 作业管理权限 *
	 **************************************************************************/

	public int getHomeworkPaper = 1;// 是否可以查看作业

	public int getHomeworkPapers = 1; // 是否可以查看作业列表

	public int addHomeworkPaper = 1;// 是否可以添加作业

	public int updateHomeworkPaper = 1;// 是否可以修改作业

	public int deleteHomeworkPaper = 1;// 是否可以删除作业

	public int changeHomeworkPaperStatus = 1;// 是否可以修改作业状态

	/***************************************************************************
	 * 在线自测答卷结果记录管理权限 *
	 **************************************************************************/
	public int addTestPaperHistory = 1;// 是否可以添加答卷结果

	public int updateTestPaperHistory = 1;// 是否可以修改答卷结果

	public int deleteTestPaperHistory = 1;// 是否可以删除答卷结果

	public int getTestPaperHistory = 1;// 是否可以查看答卷结果

	public int getTestPaperHistorys = 1;// 是否可以查看答卷结果

	/***************************************************************************
	 * 作业结果记录管理权限 *
	 **************************************************************************/
	public int addHomeworkPaperHistory = 1;// 是否可以添加作业结果

	public int updateHomeworkPaperHistory = 1;// 是否可以修改作业结果

	public int deleteHomeworkPaperHistory = 1;// 是否可以删除作业结果

	public int getHomeworkPaperHistory = 1;// 是否可以查看作业结果

	public int getHomeworkPaperHistorys = 1;// 是否可以查看作业结果

	/***************************************************************************
	 * 在线考试课程管理权限 *
	 **************************************************************************/
	public int addOnlineExamCourse = 1;// 是否可以添加考试课程

	public int deleteOnlineExamCourse = 1;// 是否可以删除考试课程

	public int updateOnlineExamCourse = 1;// 是否可以修改考试课程

	public int getOnlineExamCourse = 1;// 是否可以查看考试课程

	public int getOnlineExamCourses = 1;// 是否可以查看考试课程

	public int getExamPapersByOnlineExamCourse = 1;// 是否可以查看考试试卷

	public int addExamPaperByOnlineExamCourse = 1;// 是否可以添加考试试卷

	public int deleteExamPaperByOnlineExamCourse = 1;// 是否可以删除考试试卷

	public int changeOnlineExamCourseStatus = 1;// 是否可以修改在线考试课程状态

	/***************************************************************************
	 * 在线考试试卷管理权限 *
	 **************************************************************************/

	public int getExamPaper = 1;// 是否可以查看试卷

	public int getExamPapers = 1; // 是否可以查看试卷列表

	public int addExamPaper = 1;// 是否可以添加试卷

	public int updateExamPaper = 1;// 是否可以修改试卷

	public int deleteExamPaper = 1;// 是否可以删除试卷

	public int changeExamPaperStatus = 1;// 是否可以修改试卷状态

	/***************************************************************************
	 * 实验管理权限 *
	 **************************************************************************/

	public int getExperimentPaper = 1;// 是否可以查看实验

	public int getExperimentPapers = 1; // 是否可以查看实验列表

	public int addExperimentPaper = 1;// 是否可以添加实验

	public int updateExperimentPaper = 1;// 是否可以修改实验

	public int deleteExperimentPaper = 1;// 是否可以删除实验

	public int changeExperimentPaperStatus = 1;// 是否可以修改实验状态

	/***************************************************************************
	 * 在线考试答卷结果记录管理权限 *
	 **************************************************************************/
	public int addExamPaperHistory = 1;// 是否可以添加答卷结果

	public int updateExamPaperHistory = 1;// 是否可以修改答卷结果

	public int deleteExamPaperHistory = 1;// 是否可以删除答卷结果

	public int getExamPaperHistory = 1;// 是否可以查看答卷结果

	public int getExamPaperHistorys = 1;// 是否可以查看答卷结果

	/***************************************************************************
	 * 实验结果记录管理权限 *
	 **************************************************************************/
	public int addExperimentPaperHistory = 1;// 是否可以添加实验结果

	public int updateExperimentPaperHistory = 1;// 是否可以修改实验结果

	public int deleteExperimentPaperHistory = 1;// 是否可以删除实验结果

	public int getExperimentPaperHistory = 1;// 是否可以查看实验结果

	public int getExperimentPaperHistorys = 1;// 是否可以查看实验结果

	/***************************************************************************
	 * 考试管理权限 *
	 **************************************************************************/
	public int getExamBatch = 1;// 是否可以查看考试批次

	public int addExamBatch = 1;// 是否可以添加考试批次

	public int updateExamBatch = 1;// 是否可以更新考试批次

	public int deleteExamBatch = 1;// 是否可以删除考试批次

	public int getExamCourse = 1;// 是否可以查看考试课程

	public int addExamCourse = 1;// 是否可以添加考试课程

	public int updateExamCourse = 1;// 是否可以更新考试课程

	public int deleteExamCourse = 1;// 是否可以删除考试课程

	public int getExamRoom = 1;// 是否可以查看考试教室

	public int addExamRoom = 1;// 是否可以添加考试教室

	public int updateExamRoom = 1;// 是否可以更新考试教室

	public int deleteExamRoom = 1;// 是否可以删除考试教室

	public int getExamUser = 1;// 是否可以查看考生

	public int addExamUser = 1;// 是否可以添加考生

	public int updateExamUser = 1;// 是否可以添加考生

	public int deleteExamUser = 1;// 是否可以删除考生

	public int updateExamScore = 1;// 是否可以更新考生成绩

	public int getExamSequence = 1;// 是否可以查看考试场次

	public int addExamSequence = 1;// 是否可以添加考试场次

	public int updateExamSequence = 1;// 是否可以更新考试场次

	public int deleteExamSequence = 1;// 是否可以删除考试场次

	// 场次

	public int addBasicSequence = 1;// 是否可以添加场次

	public int deleteBasicSequence = 1;// 是否可以删除场次

	public int getBasicSequence = 1;// 是否可以查看场次

	public int updateBasicSequence = 1;// 是否可以更新场次

}
