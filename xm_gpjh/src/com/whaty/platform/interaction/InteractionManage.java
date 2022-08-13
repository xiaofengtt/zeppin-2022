package com.whaty.platform.interaction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.entity.BasicActivityManage;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.interaction.announce.Announce;
import com.whaty.platform.interaction.answer.Answer;
import com.whaty.platform.interaction.answer.EliteAnswer;
import com.whaty.platform.interaction.answer.EliteQuestion;
import com.whaty.platform.interaction.answer.Question;
import com.whaty.platform.interaction.answer.QuestionEliteDir;
import com.whaty.platform.interaction.forum.Forum;
import com.whaty.platform.interaction.forum.ForumList;
import com.whaty.platform.interaction.homework.Homework;
import com.whaty.platform.interaction.homework.HomeworkCheck;
import com.whaty.platform.interaction.homework.InHomework;
import com.whaty.platform.interaction.homework.InHomeworkCheck;
import com.whaty.platform.test.TestManage;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.util.Page;
import com.whaty.platform.vote.VoteNormalManage;

public abstract class InteractionManage {
	private InteractionUserPriv interactionUserPriv;

	public InteractionUserPriv getInteractionUserPriv() {
		return interactionUserPriv;
	}

	public void setInteractionUserPriv(InteractionUserPriv interactionUserPriv) {
		this.interactionUserPriv = interactionUserPriv;
	}

	public InteractionManage() {
	}

	/** Creates a new instance of InteractionManage */

	/***************************************************************************
	 * 以下部分为Announce管理 *
	 **************************************************************************/
	/**
	 * 添加公告
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addAnnounce(String title, String body,
			String publisherId, String publisherName, String publisherType,
			String courseId) throws NoRightException, PlatformException;

	/**
	 * 根据aid删除公告对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteAnnounce(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新公告
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateAnnounce(String id, String title, String body,
			String publisherId, String publisherName, String publisherType,
			String courseId) throws NoRightException;

	/**
	 * 根据aid得到公告对象
	 * 
	 * @param tid
	 * @return 公告对象
	 * @throws NoRightException
	 */
	public abstract Announce getAnnounce(String tid) throws NoRightException;

	/**
	 * 公告列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 公告列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getAnnounces(Page page, String teachlass_id,
			String title, String publisher_name) throws NoRightException;

	/**
	 * 公告列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 公告列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getAnnounces(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/**
	 * 公告列表
	 * 
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @return 公告列表
	 * @throws NoRightException
	 */
	public abstract int getAnnouncesNum(String teachlass_id, String title,
			String publisher_name) throws NoRightException;

	/**
	 * 公告列表
	 * 
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @return 公告列表
	 * @throws NoRightException
	 */
	public abstract int getAnnouncesNum(List searchproperty)
			throws NoRightException;

	/***************************************************************************
	 * 以下部分为Homework管理 *
	 **************************************************************************/
	/**
	 * 添加作业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addHomework(String title, String body,
			String handinDate, String submituserId, String submituserName,
			String submituserType, String courseId, String checkStatus)
			throws NoRightException, PlatformException;

	/**
	 * 根据aid删除作业对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteHomework(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新作业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateHomework(String id, String title, String body,
			String handinDate, String submituserId, String submituserName,
			String submituserType, String courseId, String checkStatusStr)
			throws NoRightException;

	/**
	 * 根据aid得到作业对象
	 * 
	 * @param tid
	 * @return 作业对象
	 * @throws NoRightException
	 */
	public abstract Homework getHomework(String tid) throws NoRightException;

	/**
	 * 作业列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 作业列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getHomeworks(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为HomeworkCheck管理 *
	 **************************************************************************/
	/**
	 * 添加作业点评
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addHomeworkCheck(String homeworkId, String body,
			String checkuserId, String checkuserName, String checkuserType)
			throws NoRightException, PlatformException;

	/**
	 * 根据aid删除作业点评对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteHomeworkCheck(String tid)
			throws NoRightException, PlatformException;

	/**
	 * 更新作业点评
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateHomeworkCheck(String id, String homeworkId,
			String body, String checkuserId, String checkuserName,
			String checkuserType) throws NoRightException;

	/**
	 * 根据aid得到作业点评对象
	 * 
	 * @param tid
	 * @return 作业点评对象
	 * @throws NoRightException
	 */
	public abstract HomeworkCheck getHomeworkCheck(String tid)
			throws NoRightException;

	/**
	 * 作业点评列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 作业点评列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getHomeworkChecks(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为InHomework管理 *
	 **************************************************************************/
	/**
	 * 添加上交的作业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addInHomework(String title, String file, String body,
			String submituserId, String submituserName, String submituserType,
			String homeworkId, String checkStatusStr) throws NoRightException,
			PlatformException;

	/**
	 * 根据aid删除上交的作业对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteInHomework(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新上交的作业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateInHomework(String id, String title, String file,
			String body, String submituserId, String submituserName,
			String submituserType, String homeworkId, String checkStatusStr)
			throws NoRightException;

	/**
	 * 根据aid得到上交的作业对象
	 * 
	 * @param tid
	 * @return 上交的作业对象
	 * @throws NoRightException
	 */
	public abstract InHomework getInHomework(String tid)
			throws NoRightException;

	/**
	 * 上交的作业列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 上交的作业列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getInHomeworks(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为InHomeworkCheck管理 *
	 **************************************************************************/
	/**
	 * 添加上交的作业点评
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addInHomeworkCheck(String inHomeworkId, String body,
			String score, String checkuserId, String checkuserName,
			String checkuserType) throws NoRightException, PlatformException;

	/**
	 * 根据aid删除上交的作业点评对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteInHomeworkCheck(String tid)
			throws NoRightException, PlatformException;

	/**
	 * 更新上交的作业点评
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateInHomeworkCheck(String id, String inHomeworkId,
			String body, String score, String checkuserId,
			String checkuserName, String checkuserType) throws NoRightException;

	/**
	 * 根据aid得到上交的作业点评对象
	 * 
	 * @param tid
	 * @return 上交的作业点评对象
	 * @throws NoRightException
	 */
	public abstract InHomeworkCheck getInHomeworkCheck(String tid)
			throws NoRightException;

	/**
	 * 上交的作业点评列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 上交的作业点评列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getInHomeworkChecks(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为Question管理 *
	 **************************************************************************/
	/**
	 * 添加答疑问题
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addQuestion(String title, String body, String key,
			String submituserId, String submituserName, String submituserType,
			String courseId, String reStatusStr) throws NoRightException,
			PlatformException;

	/**
	 * 根据aid删除答疑问题对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteQuestion(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新答疑问题
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateQuestion(String id, String title, String body,
			String key, String submituserId, String submituserName,
			String submituserType, String courseId, String reStatusStr)
			throws NoRightException;

	/**
	 * 根据aid得到答疑问题对象
	 * 
	 * @param tid
	 * @return 答疑问题对象
	 * @throws NoRightException
	 */
	public abstract Question getQuestion(String tid) throws NoRightException;

	/**
	 * 答疑问题列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 答疑问题列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getQuestions(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	public abstract List getQuestions(Page page, String teachclass_id,
			String title, String name) throws NoRightException;

	public abstract int getQuestionsNum(String teachclass_id, String title,
			String name) throws NoRightException;

	public abstract int getQuestionsNum(List searchProperty)
			throws NoRightException;

	public abstract int getQuestionsNum(String teachclass_id, String title,
			String name, String siteId) throws NoRightException;

	public abstract List getQuestions(Page page, String teachclass_id,
			String title, String name, String siteId) throws NoRightException;

	/***************************************************************************
	 * 以下部分为Answer管理 *
	 **************************************************************************/
	/**
	 * 添加答疑答案
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addAnswer(String questionId, String body,
			String reuserId, String reuserName, String reuserType)
			throws NoRightException, PlatformException;

	/**
	 * 根据aid删除答疑答案对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteAnswer(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新答疑答案
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateAnswer(String id, String questionId, String body,
			String reuserId, String reuserName, String reuserType)
			throws NoRightException;

	/**
	 * 根据aid得到答疑答案对象
	 * 
	 * @param tid
	 * @return 答疑答案对象
	 * @throws NoRightException
	 */
	public abstract Answer getAnswer(String tid) throws NoRightException;

	/**
	 * 答疑答案列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 答疑答案列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getAnswers(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	public abstract int getAnswersNum(List serachProperty)
			throws NoRightException;

	/***************************************************************************
	 * 以下部分为EliteQuestion管理 *
	 **************************************************************************/

	/**
	 * 添加常见问题答疑问题
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addEliteQuestion(String title, String body, String key,
			String submituserId, String submituserName, String submituserType,
			String courseId, String dirId) throws NoRightException,
			PlatformException;

	/**
	 * 根据aid删除常见问题答疑问题对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteEliteQuestion(String tid)
			throws NoRightException, PlatformException;

	/**
	 * 更新常见问题答疑问题
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateEliteQuestion(String id, String title,
			String body, String key, String submituserId,
			String submituserName, String submituserType, String courseId,
			String dirId) throws NoRightException;

	/**
	 * 根据aid得到常见问题答疑问题对象
	 * 
	 * @param tid
	 * @return 答疑问题对象
	 * @throws NoRightException
	 */
	public abstract EliteQuestion getEliteQuestion(String tid)
			throws NoRightException;

	/**
	 * 常见问题答疑问题列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 常见问题答疑问题列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getEliteQuestions(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为EliteAnswer管理 *
	 **************************************************************************/
	/**
	 * 添加常见问题答疑答案
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addEliteAnswer(String eliteQuestionId, String body,
			String reuserId, String reuserName, String reuserType)
			throws NoRightException, PlatformException;

	/**
	 * 根据aid删除常见问题答疑答案对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteEliteAnswer(String tid) throws NoRightException,
			PlatformException;

	/**
	 * 更新常见问题答疑答案
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateEliteAnswer(String id, String eliteQuestionId,
			String body, String reuserId, String reuserName, String reuserType)
			throws NoRightException;

	/**
	 * 根据aid得到常见问题答疑答案对象
	 * 
	 * @param tid
	 * @return 常见问题答疑答案对象
	 * @throws NoRightException
	 */
	public abstract EliteAnswer getEliteAnswer(String tid)
			throws NoRightException;

	/**
	 * 常见问题答疑答案列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 常见问题答疑答案列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getEliteAnswers(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为QuestionEliteDir管理 *
	 **************************************************************************/
	/**
	 * 添加常见问题目录
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addQuestionEliteDir(String name, String note,
			String courseId, String fatherDirId) throws NoRightException,
			PlatformException;

	/**
	 * 根据aid删除常见问题目录对象
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteQuestionEliteDir(String tid)
			throws NoRightException, PlatformException;

	/**
	 * 更新常见问题目录
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int updateQuestionEliteDir(String id, String name,
			String note, String courseId, String fatherDirId)
			throws NoRightException;

	/**
	 * 根据aid得到常见问题目录对象
	 * 
	 * @param tid
	 * @return 常见问题目录对象
	 * @throws NoRightException
	 */
	public abstract QuestionEliteDir getQuestionEliteDir(String tid)
			throws NoRightException;

	/**
	 * 常见问题目录列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 常见问题目录列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getQuestionEliteDirs(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/***************************************************************************
	 * 以下部分为Forum管理 *
	 **************************************************************************/

	/**
	 * 添加讨论区板块
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addForumList(String name, String content,
			String courseId) throws NoRightException, PlatformException;

	/**
	 * 根据aid得到讨论区板块
	 * 
	 * @param tid
	 * @return 公告对象
	 * @throws NoRightException
	 */
	public abstract ForumList getForumList(String id) throws NoRightException;

	/**
	 * 讨论区板块列表
	 * 
	 * @return 讨论区板块列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getForumLists(String courseId) throws NoRightException;

	/**
	 * 根据aid删除讨论区板块
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteForumList(String id) throws NoRightException,
			PlatformException;

	/**
	 * 修改讨论区板块
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateForumList(String id, String title, String body)
			throws NoRightException, PlatformException;

	/**
	 * 添加讨论区文章
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addForum(String title, String body,
			String submitUserId, String forumListId, String courseId,
			String submitUserName, String relatedId) throws NoRightException,
			PlatformException;

	/**
	 * 根据aid得到讨论区文章
	 * 
	 * @param tid
	 * @return 讨论区文章对象
	 * @throws NoRightException
	 */
	public abstract Forum getForum(String id) throws NoRightException;

	/**
	 * 根据aid删除讨论区文章
	 * 
	 * @param tid
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteForum(String id) throws NoRightException,
			PlatformException;

	/**
	 * 修改讨论区文章
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateForum(String id, String title, String body)
			throws NoRightException, PlatformException;

	/**
	 * 讨论区主题文章列表数目
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getTopicForumsNum(String forumlistId)
			throws NoRightException;

	/**
	 * 讨论区主题文章列表数目
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getTopicForumsNum(String forumlistId,
			String searchTitle, String searchUserName) throws NoRightException;

	/**
	 * 讨论区主题文章列表
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getTopicForums(Page page, String forumlistId)
			throws NoRightException;

	/**
	 * 讨论区主题文章列表
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getTopicForums(Page page, String forumlistId,
			String searchTitle, String searchUserName) throws NoRightException;

	/**
	 * 讨论区回复文章列表数目
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getReplyForumsNum(String forumId)
			throws NoRightException;

	/**
	 * 讨论区恢复文章列表
	 * 
	 * @return 讨论区主题文章列表
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getReplyForums(Page page, String forumId)
			throws NoRightException;

	/**
	 * 讨论区主题文章数
	 * 
	 * @return 讨论区主题文章数
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getTotalForumsNum(String forumlistId)
			throws NoRightException;

	public abstract int deleteTopicForum(String id) throws PlatformException;

	public abstract int deleteReplyForum(String id) throws PlatformException;

	public abstract TestManage creatTestManage();

	public abstract VoteNormalManage creatVoteNormalManage();

	public abstract BasicScoreManage createBasicScoreManage();

	public abstract BasicActivityManage createBasicActivityManage();

	public abstract CoursewareManage createCoursewareManage();

	public abstract TestPriv getTestPriv();

	/**
	 * 根据教学班获取课件列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewares(String teachclassId)
			throws PlatformException;

	/**
	 * 获取课件信息
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Courseware getCourseware(String coursewareId)
			throws PlatformException;

	/**
	 * 根据教学班获取课件列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveCoursewares(String teachclassId)
			throws PlatformException;

	public abstract int deleteCoursewares(String cwareId)
			throws PlatformException;

	/**
	 * 根据教学班获取课程信息
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Course getCourse(String teachclassId)
			throws PlatformException;

	/**
	 * 根据教学班获取授课教师信息
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachers(String teachclassId)
			throws PlatformException;

	public abstract InteractionTeachClass getTeachClass(String teachclassId,
			String type) throws PlatformException;

	public abstract InteractionTeachClass getTeachClass(String id)
			throws PlatformException;

	public abstract List getTeachClassList(String teachclassId, String type)
			throws PlatformException;
	
	public abstract List getTeachClassList(Page page, String teachclassId, 
			String type, String title) throws PlatformException;

	public abstract List getActiveTeachClassList(Page page, String teachclassId,
			String type,String title) throws PlatformException;
	
	public abstract List getActiveTeachClassList(String teachclassId,
			String type) throws PlatformException;

	public abstract int getTeachClassListNum(String teachclassId, String type)
			throws PlatformException;
	
	public abstract int getTeachClassListNum(String teachclassId, String type, String title)
			throws PlatformException;

	public abstract int getActiveTeachClassListNum(String teachclassId,
			String type) throws PlatformException;
	
	public abstract int getActiveTeachClassListNum(String teachclassId,
			String type, String title) throws PlatformException;
	
	public abstract int changeTeachClassStatus(String paperId,
			String status) throws PlatformException;

	public abstract int addTeachClass(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateTeachClass(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteTeachClass(String id) throws PlatformException;

	public abstract String getTeachRule(String teachclassId)
			throws PlatformException;

	public abstract int updateTeachRule(String teachclassId, String rule)
			throws PlatformException;

	/***************************************************************************
	 * 以下部分为交互统计数据管理 *
	 **************************************************************************/

	public abstract int getHomeworkTimes(String teachclassId)
			throws PlatformException;

	public abstract int getForumTimes(String teachclassId)
			throws PlatformException;

	public abstract int getQuestionTimes(String teachclassId)
			throws PlatformException;

	public abstract int getAnswerTimes(String teachclassId)
			throws PlatformException;

	public abstract List getTheachItem(String id);

	/**
	 * 获得interaction管理中需要用的WhatyEditor的配置参数
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	public abstract CoursewareManagerPriv getCoursewareManagerPriv();

}
