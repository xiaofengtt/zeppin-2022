package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePcTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcTeacher extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String fkSsoUserId;
	private String flagTeacherType;
	private String flagBak;
	private String note;
	private Set pePcNotes = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcTeacher() {
	}

	/** full constructor */
	public PePcTeacher(String name, String fkSsoUserId, String flagTeacherType,
			String flagBak, String note, Set pePcNotes) {
		this.name = name;
		this.fkSsoUserId = fkSsoUserId;
		this.flagTeacherType = flagTeacherType;
		this.flagBak = flagBak;
		this.note = note;
		this.pePcNotes = pePcNotes;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getFlagTeacherType() {
		return this.flagTeacherType;
	}

	public void setFlagTeacherType(String flagTeacherType) {
		this.flagTeacherType = flagTeacherType;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPePcNotes() {
		return this.pePcNotes;
	}

	public void setPePcNotes(Set pePcNotes) {
		this.pePcNotes = pePcNotes;
	}

}