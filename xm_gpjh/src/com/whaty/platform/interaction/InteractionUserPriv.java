package com.whaty.platform.interaction;

public abstract class InteractionUserPriv {
	private String userId;

	private String teachclassId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTeachclassId() {
		return teachclassId;
	}

	public void setTeachclassId(String teachclassId) {
		this.teachclassId = teachclassId;
	}	

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
	 * 常见问题答疑答案管理权限 *
	 **************************************************************************/

	public int getEliteAnswer = 1; // 是否可以查看常见问题答疑答案

	public int addEliteAnswer = 1; // 是否可以添加常见问题答疑答案

	public int updateEliteAnswer = 1; // 是否可以修改常见问题答疑答案

	public int deleteEliteAnswer = 1; // 是否可以删除常见问题答疑答案

	public int getEliteAnswers = 1; // 是否可以查看常见问题答疑答案

	public int addEliteAnswers = 1; // 是否可以批量添加常见问题答疑答案

	public int updateEliteAnswers = 1; // 是否可以批量修改常见问题答疑答案

	public int deleteEliteAnswers = 1; // 是否可以批量删除常见问题答疑答案

	/***************************************************************************
	 * 常见问题目录管理权限 *
	 **************************************************************************/

	public int getQuestionEliteDir = 1; // 是否可以查看常见问题目录

	public int addQuestionEliteDir = 1; // 是否可以添加常见问题目录

	public int updateQuestionEliteDir = 1; // 是否可以修改常见问题目录

	public int deleteQuestionEliteDir = 1; // 是否可以删除常见问题目录

	public int getQuestionEliteDirs = 1; // 是否可以查看常见问题目录

	public int addQuestionEliteDirs = 1; // 是否可以批量添加常见问题目录

	public int updateQuestionEliteDirs = 1; // 是否可以批量修改常见问题目录

	public int deleteQuestionEliteDirs = 1; // 是否可以批量删除常见问题目录

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
	 * 讨论区管理权限 *
	 **************************************************************************/
	public int getForumList = 1;

	public int getForumLists = 1;

	public int addForumList = 1; // 是否可以添加讨论区板块

	public int updateForumList = 1; // 是否可以修改讨论区板块

	public int deleteForumList = 1; // 是否可以删除讨论区板块

	public int addForumLists = 1; // 是否可以批量添加讨论区板块

	public int updateForumLists = 1; // 是否可以批量修改讨论区板块

	public int deleteForumLists = 1; // 是否可以批量删除讨论区板块

	public int getForum = 1;

	public int getForums = 1;

	public int addForum = 1; // 是否可以添加讨论区板块

	public int updateForum = 1; // 是否可以修改讨论区板块

	public int deleteForum = 1; // 是否可以删除讨论区板块

	public int addForums = 1; // 是否可以批量添加讨论区板块

	public int updateForums = 1; // 是否可以批量修改讨论区板块

	public int deleteForums = 1; // 是否可以批量删除讨论区板块

}
