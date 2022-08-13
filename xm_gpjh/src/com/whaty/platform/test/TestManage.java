package com.whaty.platform.test;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.test.exam.BasicSequence;
import com.whaty.platform.test.exam.ExamBatch;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.test.exam.ExamRoom;
import com.whaty.platform.test.exam.ExamSequence;
import com.whaty.platform.test.exam.ExamUser;
import com.whaty.platform.test.history.ExamPaperHistory;
import com.whaty.platform.test.history.ExperimentPaperHistory;
import com.whaty.platform.test.history.HomeworkPaperHistory;
import com.whaty.platform.test.history.TestPaperHistory;
import com.whaty.platform.test.lore.Lore;
import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.test.onlinetest.OnlineExamCourse;
import com.whaty.platform.test.onlinetest.OnlineTestCourse;
import com.whaty.platform.test.paper.ExamPaper;
import com.whaty.platform.test.paper.ExperimentPaper;
import com.whaty.platform.test.paper.HomeworkPaper;
import com.whaty.platform.test.paper.PaperPolicy;
import com.whaty.platform.test.paper.PaperPolicyCore;
import com.whaty.platform.test.paper.TestPaper;
import com.whaty.platform.test.question.PaperQuestion;
import com.whaty.platform.test.question.StoreQuestion;
import com.whaty.platform.util.Page;

public abstract class TestManage {
	private TestPriv testPriv;

	public TestPriv getTestPriv() {
		return testPriv;
	}

	public void setTestPriv(TestPriv testPriv) {
		this.testPriv = testPriv;
	}

	public TestManage() {
	}

	/***************************************************************************
	 * 以下部分为Lore管理 *
	 **************************************************************************/

	/**
	 * 添加Lore
	 * 
	 * @param name
	 * @param creatDate
	 * @param content
	 * @param loreDir
	 * @param createrId
	 * @param active
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addLore(String name, String creatDate, String content,
			String loreDir, String createrId, String active)
			throws NoRightException, PlatformException;

	/**
	 * 更新Lore
	 * 
	 * @param id
	 * @param name
	 * @param creatDate
	 * @param content
	 * @param loreDir
	 * @param createrId
	 * @param active
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateLore(String id, String name, String creatDate,
			String content, String loreDir, String createrId, String active)
			throws NoRightException, PlatformException;

	/**
	 * 根据id删除Lore
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteLore(String id) throws NoRightException,
			PlatformException;

	/**
	 * 根据id获得Lore
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract Lore getLore(String id) throws NoRightException,
			PlatformException;

	/**
	 * 获得符合条件的Lore的数目
	 * 
	 * @param id
	 * @param name
	 * @param creatDate
	 * @param content
	 * @param loreDir
	 * @param createrId
	 * @param active
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getLoresNum(String id, String name, String creatDate,
			String content, String loreDir, String createrId, String active)
			throws NoRightException, PlatformException;

	public abstract int getLoresNum(List idList) throws NoRightException,
			PlatformException;

	/**
	 * 根据所提供的参数获取Lore的列表
	 * 
	 * @param page
	 * @param id
	 * @param name
	 * @param creatDate
	 * @param content
	 * @param loreDir
	 * @param createrId
	 * @param active
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getLores(Page page, String id, String name,
			String creatDate, String content, String loreDir, String createrId,
			String active) throws NoRightException, PlatformException;

	public abstract List getLores(Page page, List idList)
			throws NoRightException, PlatformException;

	public abstract List getLores(Page page, List idList1, List idList2)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为LoreDir管理 *
	 **************************************************************************/

	/**
	 * 添加LoreDir
	 * 
	 * @param name
	 * @param note
	 * @param fatherdir
	 * @param creatdate
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addLoreDir(String name, String note, String fatherdir,
			String creatdate, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 修改LoreDir
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @param fatherdir
	 * @param creatdate
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateLoreDir(String id, String name, String note,
			String fatherdir, String creatdate, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 根据id删除LoreDir
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteLoreDir(String id) throws NoRightException,
			PlatformException;

	/**
	 * 根据id获得LoreDir信息
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract LoreDir getLoreDir(String id) throws NoRightException,
			PlatformException;

	/**
	 * 根据groupId获得LoreDir
	 * 
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract LoreDir getLoreDirByGroupId(String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 返回符合条件的LoreDir数目
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @param fatherdir
	 * @param creatdate
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getLoreDirsNum(String id, String name, String note,
			String fatherdir, String creatdate, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 返回符合条件的LoreDir数目
	 * 
	 * @param page
	 * @param id
	 * @param name
	 * @param note
	 * @param fatherdir
	 * @param creatdate
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getLoreDirs(Page page, String id, String name,
			String note, String fatherdir, String creatdate, String groupId)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为StoreQuestion管理 *
	 **************************************************************************/

	public abstract int getStoreQuestionsNum(String id, String title,
			String creatuser, String creatdate, String diff, String keyword,
			String questioncore, String lore, String cognizetype,
			String purpose, String referencescore, String referencetime,
			String studentnote, String teachernote, String type)
			throws NoRightException, PlatformException;

	/**
	 * 根据知识点获得对应的题库列表
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getStoreQuestionByLore(Page page, String id)
			throws NoRightException, PlatformException;

	/**
	 * 获得符合条件的题库列表
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param diff
	 * @param keyword
	 * @param questioncore
	 * @param lore
	 * @param cognizetype
	 * @param purpose
	 * @param referencescore
	 * @param referencetime
	 * @param studentnote
	 * @param teachernote
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getStoreQuestions(Page page, String id, String title,
			String creatuser, String creatdate, String diff, String keyword,
			String questioncore, String lore, String cognizetype,
			String purpose, String referencescore, String referencetime,
			String studentnote, String teachernote, String type)
			throws NoRightException, PlatformException;

	/**
	 * 添加题库
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param diff
	 *            难度系数
	 * @param keyword
	 *            关键字
	 * @param questioncore
	 *            题库内容
	 * @param lore
	 *            所属知识点
	 * @param cognizetype
	 *            认知类型（了解，掌握，熟练）
	 * @param purpose
	 *            用途（自测，考试，作业）
	 * @param referencescore
	 *            建议分数
	 * @param referencetime
	 *            建议时间
	 * @param studentnote
	 *            学生提示
	 * @param teachernote
	 *            教师提示
	 * @param type
	 *            题目类型
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addStoreQuestion(String title, String creatuser,
			String creatdate, String diff, String keyword, String questioncore,
			String lore, String cognizetype, String purpose,
			String referencescore, String referencetime, String studentnote,
			String teachernote, String type) throws NoRightException,
			PlatformException;

	/**
	 * 修改StoreQuestion
	 * 
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param diff
	 * @param keyword
	 * @param questioncore
	 * @param lore
	 * @param cognizetype
	 * @param purpose
	 * @param referencescore
	 * @param referencetime
	 * @param studentnote
	 * @param teachernote
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateStoreQuestion(String id, String title,
			String creatuser, String creatdate, String diff, String keyword,
			String questioncore, String lore, String cognizetype,
			String purpose, String referencescore, String referencetime,
			String studentnote, String teachernote, String type)
			throws NoRightException, PlatformException;

	/**
	 * 删除StoreQuestion
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteStoreQuestion(String id) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract StoreQuestion getStoreQuestion(String id)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为TestPaper管理 *
	 **************************************************************************/
	/**
	 * 获得符合条件的试卷列表
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param time
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getTestPapers(Page page, String id, String title,
			String creatuser, String creatdate, String status, String note,
			String time, String type, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 获得符合条件的试卷数量
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param time
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getTestPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String time, String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract List getTestPapersByOnlineTestCourse(Page page,
			String testCourseId) throws NoRightException, PlatformException;

	public abstract int getTestPapersNumByOnlineTestCourse(String testCourseId)
			throws NoRightException, PlatformException;

	public abstract int addTestPaperByOnlineTestCourse(String paperId,
			String testCourseId) throws NoRightException, PlatformException;

	public abstract int deleteTestPaperByOnlineTestCourse(String paperId,
			String testCourseId) throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param time
	 *            试卷时限
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addTestPaper(String title, String creatuser,
			String status, String note, String time, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 取得试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract TestPaper getTestPaper(String id) throws NoRightException,
			PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param time
	 *            试卷时限
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateTestPaper(String id, String title,
			String creatuser, String status, String note, String time,
			String type, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 删除试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteTestPaper(String id) throws NoRightException,
			PlatformException;

	public abstract int changeTestPaperStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为HomeworkPaper管理 *
	 **************************************************************************/
	/**
	 * 获得符合条件的试卷列表
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getHomeworkPapers(Page page, String id, String title,
			String creatuser, String creatdate, String status, String note,
			String comment, String upDate, String endDate, String type,
			String groupId) throws NoRightException, PlatformException;

	public abstract List getActiveHomeworkPapers(Page page, String id,
			String title, String creatuser, String creatdate, String status,
			String note, String comment, String upDate, String endDate,
			String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract boolean getHomeworkPaperExpired(String paperId)
			throws NoRightException, PlatformException;

	/**
	 * 获得符合条件的试卷数量
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getHomeworkPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String comment, String upDate, String endDate, String type,
			String groupId) throws NoRightException, PlatformException;

	public abstract int getActiveHomeworkPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String comment, String upDate, String endDate, String type,
			String groupId) throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addHomeworkPaper(String title, String creatuser,
			String status, String note, String comment, String upDate,
			String endDate, String type, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 取得试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract HomeworkPaper getHomeworkPaper(String id)
			throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateHomeworkPaper(String id, String title,
			String creatuser, String status, String note, String comment,
			String upDate, String endDate, String type, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 删除试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteHomeworkPaper(String id) throws NoRightException,
			PlatformException;

	public abstract int changeHomeworkPaperStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为PaperPolicy管理 *
	 **************************************************************************/
	/**
	 * 根据paperPolicyId，组织一组question,返回这组question的ID的HashMap
	 * 
	 * @param paperPolicyId
	 * @return HashMap
	 * @throws NoRightException
	 */
	public abstract HashMap getQuestionsByPaperPolicy(String paperPolicyId)
			throws NoRightException;

	public abstract List getQuestionsByPaperPolicy(Page page,
			String questionType, List questionList) throws NoRightException;

	public abstract int getQuestionsByPaperPolicyNum(String questionType,
			List questionList) throws NoRightException;

	public abstract List getOtherQuestionsByPaperPolicy(Page page,
			String courseId, String questionType, List questionList,
			String purpose) throws NoRightException;

	public abstract int getOtherQuestionsByPaperPolicyNum(String courseId,
			String questionType, List questionList, String purpose)
			throws NoRightException;

	public abstract int addPaperPolicy(HttpServletRequest request,
			HttpSession session) throws NoRightException, PlatformException;

	public abstract int updatePaperPolicy(HttpServletRequest request,
			HttpSession session) throws NoRightException, PlatformException;

	public abstract int updateBasicPaperPolicy(HttpServletRequest request,
			HttpSession session) throws NoRightException, PlatformException;

	public abstract int deletePaperPolicy(String id) throws NoRightException,
			PlatformException;

	public abstract PaperPolicy getPaperPolicy(String id)
			throws NoRightException, PlatformException;

	public abstract List getPaperPolicys(Page page, String id, String title,
			String creatuser, String creatdate, String status, String note,
			String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract int getPaperPolicysNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract PaperPolicyCore getPaperPolicyCore(String xmlPolicyCore)
			throws NoRightException;

	/***************************************************************************
	 * 以下部分为PaperQuestion管理 *
	 **************************************************************************/

	/**
	 * 根据id获得PaperQuestion的信息
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 */
	public abstract PaperQuestion getPaperQuestion(String id)
			throws NoRightException;

	public abstract int addPaperQuestion(PaperQuestion question)
			throws NoRightException, PlatformException;

	public abstract int updatePaperQuestionScore(PaperQuestion question)
			throws NoRightException, PlatformException;

	public abstract int deletePaperQuestions(String paperId)
			throws NoRightException, PlatformException;

	public abstract int deletePaperQuestions(String paperId, String questionIds)
			throws NoRightException, PlatformException;
	
	public abstract int deleteStuPaperQuestions(String paperId,String userId)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为TestPaperHistory管理 *
	 **************************************************************************/
	public abstract int addTestPaperHistory(String userId, String testPaperId,
			HashMap answer) throws NoRightException, PlatformException;
	
	public abstract int deleteTestPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int updateTestPaperHistory(String id, String userId,
			String testPaperId, HashMap answer) throws NoRightException,
			PlatformException;

	public abstract List getTestPaperHistorys(Page page, String userId,
			String testPaperId, String beginDate, String endDate, String groupId)
			throws NoRightException, PlatformException;

	public abstract List getTestPaperHistorys(Page page, String regNo,
			String userName, String testPaperId, String beginDate,
			String endDate, String groupId) throws NoRightException,
			PlatformException;

	public abstract TestPaperHistory getTestPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int getTestPaperHistorysNum(String userId,
			String testPaperId, String beginDate, String endDate, String groupId)
			throws NoRightException, PlatformException;

	public abstract int getTestPaperHistorysNum(String regNo, String userName,
			String testPaperId, String beginDate, String endDate, String groupId)
			throws NoRightException, PlatformException;

	public abstract String getRegNoByUserId(String userId);

	/**
	 * 获取指定教学站下的TestHistory数
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getTestPaperHistorysNum(String siteId)
			throws NoRightException, PlatformException;

	public abstract int getTestPaperHistorysNum(String paperId,
			String teachclassId, String siteId) throws NoRightException,
			PlatformException;

	/**
	 * 获取指定教学站下的TestHistory列表
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getTestPaperHistorys(Page page, String siteId)
			throws NoRightException, PlatformException;

	public abstract List getTestPaperHistorys(Page page, String paperId,
			String teachclassId, String siteId) throws NoRightException,
			PlatformException;

	/***************************************************************************
	 * 以下部分为HomeworkPaperHistory管理 *
	 **************************************************************************/
	public abstract int addHomeworkPaperHistory(String userId,
			String HomeworkPaperId, HashMap answer, String ischeck) throws NoRightException,
			PlatformException;

	public abstract int deleteHomeworkPaperHistory(String id)
			throws NoRightException, PlatformException;
	
	public abstract int updateHomeworkPaperHistory(String id, String userId,
			String HomeworkPaperId, HashMap answer) throws NoRightException,
			PlatformException;

	public abstract int updateHomeworkPaperHistory(String id, String userId,
			String HomeworkPaperId, HashMap answer, String status)
			throws NoRightException, PlatformException;

	public abstract List getHomeworkPaperHistorys(Page page, String userId,
			String HomeworkPaperId, String beginDate, String endDate,
			String groupId) throws NoRightException, PlatformException;

	public abstract List getHomeworkPaperHistorys(Page page,
			String HomeworkPaperId, String siteId, String groupId)
			throws NoRightException, PlatformException;

	public abstract HomeworkPaperHistory getHomeworkPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int getHomeworkPaperHistorysNum(String userId,
			String HomeworkPaperId, String beginDate, String endDate,
			String groupId) throws NoRightException, PlatformException;

	public abstract int getHomeworkPaperHistorysNum(String HomeworkPaperId,
			String site_id, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 获取指定站点的HomewordPaperHistory数
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getHomeworkPaperHistorysNum(String siteId)
			throws NoRightException, PlatformException;

	/**
	 * 获取指定站点的HomewordPaperHistory列表
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getHomeworkPaperHistorys(Page page, String siteId)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为OnlineTestCourse管理 *
	 **************************************************************************/
	public abstract int addOnlineTestCourse(String title, String note,
			String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;

	public abstract int deleteOnlineTestCourse(String id)
			throws NoRightException, PlatformException;

	public abstract int updateOnlineTestCourse(String id, String title,
			String note, String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;

	public abstract List getOnlineTestCourses(Page page, String id,
			String title, String note, String startDate, String endDate,
			String status, String isAutoCheck, String isHiddenAnswer,
			String batchId, String groupId, String creatUser)
			throws NoRightException, PlatformException;

	public abstract OnlineTestCourse getOnlineTestCourse(String id)
			throws NoRightException, PlatformException;

	public abstract int getOnlineTestCoursesNum(String id, String title,
			String note, String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;
	
	public abstract int getOnlineTestCoursesNum(String id, String title,
			String note, String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser,String user) throws NoRightException,
			PlatformException;

	public abstract int changeOnlineTestCourseStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为TestExam管理 *
	 **************************************************************************/
	public abstract ExamBatch getExamBatch(String id) throws PlatformException;

	public abstract List getExamBatchList(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract List getExamActiveBatchList(Page page, HttpServletRequest request)
	throws PlatformException;
	
	
	
	public abstract List getExamBatchList() throws PlatformException;

	public abstract int addExamBatch(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateExamBatch(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteExamBatch(String id) throws PlatformException;

	public abstract int getExamBatchNum(HttpServletRequest request)
			throws PlatformException;
	
//	public abstract int getExamBatchNumStatus(HttpServletRequest request)
//	throws PlatformException;

	public abstract int updateExamBatchStatus(String id, String status)
			throws PlatformException;

	public abstract ExamCourse getExamCourse(String id)
			throws PlatformException;

	public abstract List getExamCourseList(Page page, HttpServletRequest request)
			throws PlatformException;

//	public abstract List getExamCourseList1(String activeBatchId,Page page, HttpServletRequest request)
//	throws PlatformException;
	
//	public abstract List getTableMessage6(Page page,String batch_id,String grade_id,String major_id,String edutype_id,String kaoqu_id,String shenfen_id,String semester_id)
//	throws PlatformException;
//	
//	public abstract List getTableMessage7(Page page,String batch_id,String grade_id,String major_id,String edutype_id,String kaoqu_id,String shenfen_id,String semester_id)
//	throws PlatformException;

	
//	public abstract List getReExamCourseList1(String activeBatchId,Page page, HttpServletRequest request)
//	throws PlatformException;
	
	public abstract List getSiteExamCourseList(String site_id, Page page,
			HttpServletRequest request) throws PlatformException;
	
	
	public abstract List getSiteExamCourseListNewGroup(String site_id, Page page,
			HttpServletRequest request) throws PlatformException;
	
	public abstract List getSiteExamCourseListNewGroup(String activeBatchId,String site_id, Page page,
			HttpServletRequest request) throws PlatformException;
	
	public abstract int addExamCourse(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateExamCourse(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteExamCourse(String id) throws PlatformException;

	public abstract int getExamCourseNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteExamCourseNum(String site_id,
			HttpServletRequest request) throws PlatformException;

	public abstract ExamRoom getExamRoom(String id) throws PlatformException;

	public abstract List getExamRoomList(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int addExamRoom(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateExamRoom(HttpServletRequest request)
			throws PlatformException;
	
//	public abstract int connectExamRoom(String[] ids)throws PlatformException;
	
//	public abstract int connectReExamRoom(String[] ids)throws PlatformException;
	
	public abstract int deleteExamRoom(String id) throws PlatformException;

	public abstract ExamUser getExamUser(String id) throws PlatformException;

	public abstract List getExamUserList(Page page, HttpServletRequest request)
			throws PlatformException;
	
//	public abstract List getExamUserListNewGroup(String course_id,String kaoqu_id,String edu_type_id,String major_id,
//			String grade_id,String shenfen_id )
//	throws PlatformException;
	
	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return 得到该条件下的考试的人员列表。
	 * @throws PlatformException
	 */
//	public abstract List getExamUserListNewGroup(String activeBatchId,String course_id,String kaoqu_id,String edu_type_id,String major_id,
//			String grade_id,String shenfen_id )
//	throws PlatformException;
	
	/**
	 * @author shu
	 * @param activeBatchId
	 * @param course_id
	 * @param kaoqu_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @param shenfen_id
	 * @return 得到该条件下的考试的人员列表。
	 * @throws PlatformException
	 */
//	public abstract List getReExamUserListNewGroup(String activeBatchId,String course_id,String kaoqu_id,String edu_type_id,String major_id,
//			String grade_id,String shenfen_id )
//	throws PlatformException;
	
	public abstract int addExamUser(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateExamUser(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteExamUser(String id) throws PlatformException;

	public abstract int updateExamScore(HttpServletRequest request)
			throws PlatformException;

	public abstract void courseAddUser(List examCourseList, List examUserList)
			throws PlatformException;

	public abstract void courseRemoveUser(List examCourseList, List examUserList)
			throws PlatformException;

	public abstract List getExamSequenceList(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int getExamSequenceNum(HttpServletRequest request)
			throws PlatformException;

//	public abstract int addExamSequence(HttpServletRequest request)
//			throws PlatformException;

	public abstract ExamSequence getExamSequence(String id)
			throws PlatformException;

	public abstract int updateExamSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteExamSequence(String id) throws PlatformException;

	public abstract List getExamCoursesList(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract List getSiteExamCoursesList(String site_id, Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int getExamCoursesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteExamCoursesNum(String site_id,
			HttpServletRequest request) throws PlatformException;

	public abstract int addBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract BasicSequence getBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract List getBasicSequences(Page page, HttpServletRequest request)
			throws PlatformException;
	
//	public abstract List getBasicSequences(Page page, HttpServletRequest request,String batch_id)
//	throws PlatformException;
	
//	public abstract List getBasicSequences2(Page page, HttpServletRequest request,String batch_id)
//	throws PlatformException;
/**
 * @author shu
 * @param page
 * @param request
 * @return 得到没有安排考场时间的场次
 * @throws PlatformException
 */	
//	public abstract List getBasicSequencesNoTime(Page page, HttpServletRequest request)
//	throws PlatformException;
	
	
	/**
	 * @author shu
	 * @param page
	 * @param request
	 * @return 得到没有安排考场时间的场次
	 * @throws PlatformException
	 */	
//		public abstract List getBasicSequencesNoTime(Page page, HttpServletRequest request,String batchId)
//		throws PlatformException;
	/**
	 * @author shu
	 * @param page
	 * @param request
	 * @return 得到没有安排考场时间的场次
	 * @throws PlatformException
	 */	
//		public abstract List getBasicSequencesNoTimeRe(Page page, HttpServletRequest request)
//		throws PlatformException;
//		
//		public abstract List getBasicSequencesNoTimeRe(Page page, HttpServletRequest request,String batchId)
//		throws PlatformException;

	public abstract int getBasicSequencesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int changeCourseType(String id, String courseType)
			throws PlatformException;

	
	/***************************************************************************
	 * 以下部分为TestReExam管理 *
	 **************************************************************************/
	
//	public abstract List getSiteReExamCourseListNewGroup(String activeBatchId,String site_id, Page page,
//			HttpServletRequest request) throws PlatformException;
	
//	public abstract ReExamBatch getReExamBatch(String id) throws PlatformException;

//	public abstract List getReExamBatchList(Page page, HttpServletRequest request)
//			throws PlatformException;
//	
//	public abstract List getReExamActiveBatchList(Page page, HttpServletRequest request)
//	throws PlatformException;
//	
//	public abstract List getReExamBatchList() throws PlatformException;
//
//	public abstract int addReExamBatch(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int updateReExamBatch(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int deleteReExamBatch(String id) throws PlatformException;
//
//	public abstract int getReExamBatchNum(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int getReExamBatchNumStatus(HttpServletRequest request)
//	throws PlatformException;
//	
//	public abstract int updateReExamBatchStatus(String id, String status)
//			throws PlatformException;
//
//	public abstract ReExamCourse getReExamCourse(String id)
//			throws PlatformException;
//
//	public abstract List getReExamCourseList(Page page, HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract List getSiteReExamCourseList(String site_id, Page page,
//			HttpServletRequest request) throws PlatformException;
//
//	public abstract int addReExamCourse(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int updateReExamCourse(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int deleteReExamCourse(String id) throws PlatformException;
//
//	public abstract int getReExamCourseNum(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int getSiteReExamCourseNum(String site_id,
//			HttpServletRequest request) throws PlatformException;
//
//	public abstract ReExamRoom getReExamRoom(String id) throws PlatformException;
//
//	public abstract List getReExamRoomList(Page page, HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int addReExamRoom(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int updateReExamRoom(HttpServletRequest request)
//			throws PlatformException;

//	public abstract int updateExamRoomPlace(String[] ids,String[] places)
//	throws PlatformException;
	/**
	 * @author shu
	 * @param room_id
	 * @return 得到该考场人的座位号。
	 * @throws PlatformException
	 */
//	public abstract List getExamDesks(String room_id)
//	throws PlatformException;
	/**
	 * @author shu
	 * @param room_no
	 * @return 根据room_no来得到考场人员信息。
	 * @throws PlatformException
	 */
//	public abstract List getExamDesks3(String room_no)
//	throws PlatformException;
//	/**
//	 * @author shu
//	 * @param room_id
//	 * @return 得到该考场人的座位号，及学生的相关信息。
//	 * @throws PlatformException
//	 */
//	public abstract List getExamDesks2(String room_no)
//	throws PlatformException;
//
//	/**
//	 * @author bob
//	 * @param room_id
//	 * @return 根据room_no来得到考场人员信息具体信息。
//	 * @throws PlatformException
//	 */
//	public abstract List getExamDesks4(String room_id)
//	throws PlatformException;
	
//	public abstract List getReExamDesks(String room_id)
//	throws PlatformException;
	/**
	 * @author bob 
	 * @param room_id
	 * @return 根据roon_no来查找考生基本信息。
	 * @throws PlatformException
	 */
//	public abstract List getReExamDesks3(String room_no)
//	throws PlatformException;
//	
//	public abstract List getReExamDesks2(String room_id)
//	throws PlatformException;
//	
//	public abstract List getReExamDesks4(String room_no)
//	throws PlatformException;
//	
//	public abstract int updateReExamRoomPlace(String[] ids,String[] places)
//	throws PlatformException;
//	
//	public abstract int deleteReExamRoom(String id) throws PlatformException;
//
//	public abstract ReExamUser getReExamUser(String id) throws PlatformException;
//
//	public abstract List getReExamUserList(Page page, HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int addReExamUser(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int updateReExamUser(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int deleteReExamUser(String id) throws PlatformException;
//
//	public abstract int updateReExamScore(HttpServletRequest request)
//			throws PlatformException;

/*	public abstract void courseAddUser(List ExamCourseList, List ExamUserList)
			throws PlatformException;

	public abstract void courseRemoveUser(List ExamCourseList, List ExamUserList)
			throws PlatformException;
*/
//	public abstract List getReExamSequenceList(Page page,
//			HttpServletRequest request) throws PlatformException;
//
//	public abstract int getReExamSequenceNum(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int addReExamSequence(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract ReExamSequence getReExamSequence(String id)
//			throws PlatformException;
//
//	public abstract int updateReExamSequence(HttpServletRequest request)
//			throws PlatformException;
//
//	public abstract int deleteReExamSequence(String id) throws PlatformException;
//
//	public abstract List getReExamCoursesList(Page page,
//			HttpServletRequest request) throws PlatformException;
//
//	public abstract List getSiteReExamCoursesList(String site_id, Page page,
//			HttpServletRequest request) throws PlatformException;
//
//	public abstract int getReExamCoursesNum(HttpServletRequest request)
//			throws PlatformException;

//	public abstract int getSiteReExamCoursesNum(String site_id,
//			HttpServletRequest request) throws PlatformException;

/*	public abstract int addBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract BasicSequence getBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateBasicSequence(HttpServletRequest request)
			throws PlatformException;

	public abstract List getBasicSequences(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int getBasicSequencesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int changeCourseType(String id, String courseType)
			throws PlatformException;

*/	
	/***************************************************************************
	 * 以下部分为OnlineExamCourse管理 *
	 **************************************************************************/
	public abstract int addOnlineExamCourse(String title, String note,
			String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;

	public abstract int deleteOnlineExamCourse(String id)
			throws NoRightException, PlatformException;

	public abstract int updateOnlineExamCourse(String id, String title,
			String note, String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;

	public abstract List getOnlineExamCourses(Page page, String id,
			String title, String note, String startDate, String endDate,
			String status, String isAutoCheck, String isHiddenAnswer,
			String batchId, String groupId, String creatUser)
			throws NoRightException, PlatformException;

	public abstract OnlineExamCourse getOnlineExamCourse(String id)
			throws NoRightException, PlatformException;

	public abstract int getOnlineExamCoursesNum(String id, String title,
			String note, String startDate, String endDate, String status,
			String isAutoCheck, String isHiddenAnswer, String batchId,
			String groupId, String creatUser) throws NoRightException,
			PlatformException;

	public abstract int changeOnlineExamCourseStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为ExamPaper管理 *
	 **************************************************************************/
	/**
	 * 获得符合条件的试卷列表
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param time
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getExamPapers(Page page, String id, String title,
			String creatuser, String creatdate, String status, String note,
			String time, String type, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 获得符合条件的试卷数量
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param time
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getExamPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String time, String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract List getExamPapersByOnlineExamCourse(Page page,
			String testCourseId) throws NoRightException, PlatformException;

	public abstract int getExamPapersNumByOnlineExamCourse(String testCourseId)
			throws NoRightException, PlatformException;

	public abstract int addExamPaperByOnlineExamCourse(String paperId,
			String testCourseId) throws NoRightException, PlatformException;

	public abstract int deleteExamPaperByOnlineExamCourse(String paperId,
			String testCourseId) throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param time
	 *            试卷时限
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addExamPaper(String title, String creatuser,
			String status, String note, String time, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 取得试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ExamPaper getExamPaper(String id) throws NoRightException,
			PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param time
	 *            试卷时限
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateExamPaper(String id, String title,
			String creatuser, String status, String note, String time,
			String type, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 删除试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteExamPaper(String id) throws NoRightException,
			PlatformException;

	public abstract int changeExamPaperStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为ExperimentPaper管理 *
	 **************************************************************************/
	/**
	 * 获得符合条件的试卷列表
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getExperimentPapers(Page page, String id,
			String title, String creatuser, String creatdate, String status,
			String note, String comment, String upDate, String endDate,
			String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract List getActiveExperimentPapers(Page page, String id,
			String title, String creatuser, String creatdate, String status,
			String note, String comment, String upDate, String endDate,
			String type, String groupId) throws NoRightException,
			PlatformException;

	public abstract boolean getExperimentPaperExpired(String paperId)
			throws NoRightException, PlatformException;

	/**
	 * 获得符合条件的试卷数量
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param creatuser
	 * @param creatdate
	 * @param status
	 * @param note
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 * @param groupId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getExperimentPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String comment, String upDate, String endDate, String type,
			String groupId) throws NoRightException, PlatformException;

	public abstract int getActiveExperimentPapersNum(String id, String title,
			String creatuser, String creatdate, String status, String note,
			String comment, String upDate, String endDate, String type,
			String groupId) throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addExperimentPaper(String title, String creatuser,
			String status, String note, String comment, String upDate,
			String endDate, String type, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 取得试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ExperimentPaper getExperimentPaper(String id)
			throws NoRightException, PlatformException;

	/**
	 * 添加试卷
	 * 
	 * @param title
	 *            名称
	 * @param creatuser
	 *            创建者
	 * @param creatdate
	 *            创建日期
	 * @param status
	 *            状态字段
	 * @param note
	 *            备注
	 * @param comment
	 * @param upDate
	 * @param endDate
	 * @param type
	 *            试卷类型
	 * @param groupId
	 *            试卷分组号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateExperimentPaper(String id, String title,
			String creatuser, String status, String note, String comment,
			String upDate, String endDate, String type, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 删除试卷信息
	 * 
	 * @param id
	 *            编号
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteExperimentPaper(String id)
			throws NoRightException, PlatformException;

	public abstract int changeExperimentPaperStatus(String id, String status)
			throws NoRightException, PlatformException;

	/***************************************************************************
	 * 以下部分为ExamPaperHistory管理 *
	 **************************************************************************/
	public abstract int addExamPaperHistory(String userId, String testPaperId,
			HashMap answer) throws NoRightException, PlatformException;

	public abstract int deleteExamPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int updateExamPaperHistory(String id, String userId,
			String testPaperId, HashMap answer) throws NoRightException,
			PlatformException;

	public abstract List getExamPaperHistorys(Page page, String userId,
			String testPaperId, String beginDate, String endDate, String groupId)
			throws NoRightException, PlatformException;

	public abstract ExamPaperHistory getExamPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int getExamPaperHistorysNum(String userId,
			String testPaperId, String beginDate, String endDate, String groupId)
			throws NoRightException, PlatformException;

	/**
	 * 获取指定教学站下的TestHistory数
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getExamPaperHistorysNum(String siteId)
			throws NoRightException, PlatformException;

	public abstract int getExamPaperHistorysNum(String paperId,
			String teachclassId, String siteId) throws NoRightException,
			PlatformException;

	/**
	 * 获取指定教学站下的TestHistory列表
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getExamPaperHistorys(Page page, String siteId)
			throws NoRightException, PlatformException;

	public abstract List getExamPaperHistorys(Page page, String paperId,
			String teachclassId, String siteId) throws NoRightException,
			PlatformException;

	/***************************************************************************
	 * 以下部分为ExperimentPaperHistory管理 *
	 **************************************************************************/
	public abstract int addExperimentPaperHistory(String userId,
			String HomeworkPaperId, HashMap answer) throws NoRightException,
			PlatformException;

	public abstract int deleteExperimentPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int updateExperimentPaperHistory(String id, String userId,
			String HomeworkPaperId, HashMap answer) throws NoRightException,
			PlatformException;

	public abstract int updateExperimentPaperHistory(String id, String userId,
			String HomeworkPaperId, HashMap answer, String status)
			throws NoRightException, PlatformException;

	public abstract List getExperimentPaperHistorys(Page page, String userId,
			String HomeworkPaperId, String beginDate, String endDate,
			String groupId) throws NoRightException, PlatformException;

	public abstract List getExperimentPaperHistorys(Page page,
			String HomeworkPaperId, String siteId, String groupId)
			throws NoRightException, PlatformException;

	public abstract ExperimentPaperHistory getExperimentPaperHistory(String id)
			throws NoRightException, PlatformException;

	public abstract int getExperimentPaperHistorysNum(String userId,
			String HomeworkPaperId, String beginDate, String endDate,
			String groupId) throws NoRightException, PlatformException;

	public abstract int getExperimentPaperHistorysNum(String HomeworkPaperId,
			String site_id, String groupId) throws NoRightException,
			PlatformException;

	/**
	 * 获取指定站点的HomewordPaperHistory数
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int getExperimentPaperHistorysNum(String siteId)
			throws NoRightException, PlatformException;

	/**
	 * 获取指定站点的HomewordPaperHistory列表
	 * 
	 * @param siteId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getExperimentPaperHistorys(Page page, String siteId)
			throws NoRightException, PlatformException;

	
	public abstract String getPolicyIdByPaperId(String homeworkId) throws Exception;
}