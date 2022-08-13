package com.whaty.platform.vote.basic;

import com.whaty.platform.Items;

public abstract class VotePaperGroup implements Items{

	private String id;
	
	private String name;
	
	private boolean active;
	
	private String note;
	
	
	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
}
