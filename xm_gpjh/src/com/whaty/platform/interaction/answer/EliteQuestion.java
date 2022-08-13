/*
 * EliteQuestion.java
 *
 * Created on 2005��1��6��, ����5:19
 */

package com.whaty.platform.interaction.answer;

/**
 * ����������
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
