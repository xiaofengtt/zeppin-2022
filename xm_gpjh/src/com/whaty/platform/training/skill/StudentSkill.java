/**
 * 
 */
package com.whaty.platform.training.skill;

import com.whaty.platform.training.user.TrainingStudent;

/**
 * @author chenjian
 *
 */
public class StudentSkill{

	private TrainingStudent student;
	
	private  Skill skill;
	
	private String status;
	
	private String getDate;

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TrainingStudent getStudent() {
		return student;
	}

	public void setStudent(TrainingStudent student) {
		this.student = student;
	}
	
	

}
