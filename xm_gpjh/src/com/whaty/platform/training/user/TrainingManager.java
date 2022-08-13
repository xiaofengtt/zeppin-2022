/**
 * 
 */
package com.whaty.platform.training.user;

import com.whaty.platform.Items;

/**
 * @author chenjian
 *
 */
public abstract class TrainingManager extends TrainingUser implements Items{
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
