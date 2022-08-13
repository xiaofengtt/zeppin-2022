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
public abstract class EliteAnswer extends Answer {
	private EliteQuestion eliteQuestion; 

	/** Creates a new instance of EliteQuestion */
	public EliteAnswer() {
	}

	public EliteQuestion getEliteQuestion() {
		return eliteQuestion;
	}

	public void setEliteQuestion(EliteQuestion eliteQuestion) {
		this.eliteQuestion = eliteQuestion;
	}
}
