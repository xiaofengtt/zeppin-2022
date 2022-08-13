/**
 * 
 */
package com.whaty.platform.training.basic;

/**
 * @author chenjian
 *
 */
public class StudyProgress {
	
	public static String UNCOMPLETE="UNCOMPLETE";
	
	public static String COMPLETED="COMPLETED";
	
	public static String PASSED="PASSED";
	
	public static String FAILED="FAILED";
	
	public static String BROWSERED="BROWSERED";
	
	public static String INCOMPLETE="INCOMPLETE";
	
	private float percent;
	
	private String learnStatus;
	
	private float score;
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public String getLearnStatus() {
		return learnStatus;
	}

	public void setLearnStatus(String learnStatus) {
		this.learnStatus = learnStatus;
	}
		
}
