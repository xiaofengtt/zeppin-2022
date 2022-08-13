package com.whaty.platform.test;

import java.util.List;

import com.whaty.platform.test.lore.LoreDir;
import com.whaty.platform.util.Page;

public interface TestList {
	/**
	 * ��÷���������֪ʶ����Ŀ
	 * 
	 * @param searchproperty
	 * @return
	 */
	public int getLoresNum(List searchproperty);

	/**
	 * ��÷���������֪ʶ���б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getLores(Page page, List searchproperty, List orderproperty);

	/**
	 * ��÷���������֪ʶ��Ŀ¼��
	 * 
	 * @param searchproperty
	 * @return
	 */
	public int getLoreDirsNum(List searchproperty);

	/**
	 * ��÷���������֪ʶ��Ŀ¼�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public LoreDir getLoreDirIdByGroupId(String groupId);

	public List getLoreDirs(Page page, List searchproperty, List orderproperty);

	/**
	 * ��÷���������StoreQuestion��
	 * 
	 * @param searchproperty
	 * @return
	 */
	public int getStoreQuestionsNum(List searchproperty);

	public int getStoreQuestions1Num(List searchproperty, String courseId);

	/**
	 * ��÷�������������б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getStoreQuestions(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ���������Ի������
	 * 
	 * @param score
	 * @param courseId
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getQuestionsByPaperPolicy(String score, String courseId,
			Page page, List searchproperty, List orderproperty);

	public List getPaperPolicys(Page page, List searchproperty,
			List orderproperty);

	public int getPaperPolicysNum(List searchproperty);

	// OnlineTestCourse
	public List getOnlineTestCourses(Page page, List searchproperty,
			List orderproperty);

	public int getOnlineTestCoursesNum(List searchproperty);

	// TestPaper
	public int getTestPapersNum(List searchproperty);

	public List getTestPapers(Page page, List searchproperty, List orderproperty);

	// TestPaperHistory
	public List getTestPaperHistorys(Page page, List searchproperty,
			List orderproperty, String type);

	public int getTestPaperHistorysNum(List searchproperty, String type);

	// HomeworkPaper
	public List getHomeworkPapers(Page page, List searchproperty,
			List orderproperty);

	public int getHomeworkPapersNum(List searchproperty);

	public List getActiveHomeworkPapers(Page page, List searchproperty,
			List orderproperty);

	public int getActiveHomeworkPapersNum(List searchproperty);

	// HomeworkPaperHistory
	public List getHomeworkPaperHistorys(Page page, List searchproperty,
			List orderproperty, String type);

	public int getHomeworkPaperHistorysNum(List searchproperty, String type);

	// OnlineExamCourse
	public List getOnlineExamCourses(Page page, List searchproperty,
			List orderproperty);

	public int getOnlineExamCoursesNum(List searchproperty);

	// ExamPaper
	public int getExamPapersNum(List searchproperty);

	public List getExamPapers(Page page, List searchproperty, List orderproperty);

	// ExamPaperHistory
	public List getExamPaperHistorys(Page page, List searchproperty,
			List orderproperty, String type);

	public int getExamPaperHistorysNum(List searchproperty, String type);

	// ExperimentPaper
	public List getExperimentPapers(Page page, List searchproperty,
			List orderproperty);

	public int getExperimentPapersNum(List searchproperty);

	public List getActiveExperimentPapers(Page page, List searchproperty,
			List orderproperty);

	public int getActiveExperimentPapersNum(List searchproperty);

	// ExperimentPaperHistory
	public List getExperimentPaperHistorys(Page page, List searchproperty,
			List orderproperty, String type);

	public int getExperimentPaperHistorysNum(List searchproperty, String type);

}
