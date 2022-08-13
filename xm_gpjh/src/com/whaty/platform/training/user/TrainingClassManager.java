/**
 * 
 */
package com.whaty.platform.training.user;


/**
 * @author chenjian
 *
 */
public abstract class TrainingClassManager extends TrainingUser{

	
	private String note;
	
	

	public String getNote() {
		if(note == null || note.length() == 0 )
			return "��ʱû������";
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public TrainingClassManager() {
		this.setTrainingUserType(TrainingUserType.CLASSMANAGER);
	}
	

	
}
