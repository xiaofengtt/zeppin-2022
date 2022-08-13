/**
 * 
 */
package com.whaty.platform.training.user;

import com.whaty.platform.Items;


/**
 * @author chenjian
 *
 */
public abstract class TrainingStudent extends TrainingUser implements Items{

	private boolean  isActive;
	
	private String note;
	

	
	private String regDate;
	
	private String checkDate;
	
	public TrainingStudent() {
		this.setTrainingUserType(TrainingUserType.STUDENT);
	}
	
	




	public boolean getIsActive() {
		return isActive;
	}
	
	public String getStatus() {
		return isActive ? "激活":"未激活";
	}






	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}






	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getNote() {
		if(note == null || note.length() == 0)
			return "目前没有描述";
		else return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	
	
	

}
