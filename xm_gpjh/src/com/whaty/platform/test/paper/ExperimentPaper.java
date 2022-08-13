package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class ExperimentPaper extends Paper {
	private String startDate = "";

	private String endDate = "";

	private String comments = "";

	/**
	 * 属性 comment 的获取方法。
	 * 
	 * @return 属性 comment 的值。
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 属性 comment 的设置方法。
	 * 
	 * @param comment
	 *            属性 comment 的新值。
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * 属性 endDate 的获取方法。
	 * 
	 * @return 属性 endDate 的值。
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 属性 endDate 的设置方法。
	 * 
	 * @param endDate
	 *            属性 endDate 的新值。
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 作业试卷的题目的获取方法。
	 * 
	 * @return List。
	 */
	public List getPaperQuestion() throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 作业试卷的题目的添加方法。
	 * 
	 * @param PaperQuestion
	 */
	public void addPaperQuestion(List PaperQuestion) throws PlatformException {
		// TODO Auto-generated method stub

	}

	/**
	 * 作业试卷的题目的移除方法。
	 * 
	 * @param PaperQuestion
	 */
	public void removePaperQuestion(List PaperQuestion)
			throws PlatformException {
		// TODO Auto-generated method stub

	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
