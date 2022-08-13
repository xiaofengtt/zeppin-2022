package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitNoExamCondition implements Items {
	private String id;

	private String name;

	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
