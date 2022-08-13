package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class TestPaper extends Paper {
	private String time = "";

	/**
	 * 属性 time 的获取方法。
	 * 
	 * @return 属性 time 的值。
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 属性 time 的设置方法。
	 * 
	 * @param time
	 *            属性 time 的新值。
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 作业试卷的题目的获取方法。
	 * 
	 * @return List。
	 */
	public abstract List getPaperQuestion() throws PlatformException;

	public abstract int removePaperQuestions() throws PlatformException;

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

}
