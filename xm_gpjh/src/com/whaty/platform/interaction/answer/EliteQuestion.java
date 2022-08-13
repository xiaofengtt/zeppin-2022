/*
 * EliteQuestion.java
 *
 * Created on 2005年1月6日, 下午5:19
 */

package com.whaty.platform.interaction.answer;

/**
 * 常见问题类
 * 
 * @author chenjian
 */
public abstract class EliteQuestion extends Question {

	private QuestionEliteDir dir; 

	/** Creates a new instance of EliteQuestion */
	public EliteQuestion() {
	}

	public QuestionEliteDir getDir() {
		return dir;
	}

	public void setDir(QuestionEliteDir dir) {
		this.dir = dir;
	}

}
