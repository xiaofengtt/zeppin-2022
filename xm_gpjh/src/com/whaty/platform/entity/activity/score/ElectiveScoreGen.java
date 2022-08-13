/**
 * 
 */
package com.whaty.platform.entity.activity.score;

/**
 * @author chenjian
 *
 */
public class ElectiveScoreGen {
	
	private float normalPercent=0.1f;
	private float examPercent=0.9f;
	
	public float getExamPercent() {
		return examPercent;
	}
	public void setExamPercent(float examPercent) {
		this.examPercent = examPercent;
	}
	public float getNormalPercent() {
		return normalPercent;
	}
	public void setNormalPercent(float normalPercent) {
		this.normalPercent = normalPercent;
	}
	
}
