package com.whaty.platform.entity.bean;

/**
 * PeSmsSystempoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSmsSystempoint extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String code;
	private String name;
	private String flagSmsStatus;
	private String flagIsauto;
	private String sqlNote;
	private String note;

	// Constructors

	/** default constructor */
	public PeSmsSystempoint() {
	}

	/** minimal constructor */
	public PeSmsSystempoint(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/** full constructor */
	public PeSmsSystempoint(String code, String name, String flagSmsStatus,
			String flagIsauto, String sqlNote, String note) {
		this.code = code;
		this.name = name;
		this.flagSmsStatus = flagSmsStatus;
		this.flagIsauto = flagIsauto;
		this.sqlNote = sqlNote;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagSmsStatus() {
		return this.flagSmsStatus;
	}

	public void setFlagSmsStatus(String flagSmsStatus) {
		this.flagSmsStatus = flagSmsStatus;
	}

	public String getFlagIsauto() {
		return this.flagIsauto;
	}

	public void setFlagIsauto(String flagIsauto) {
		this.flagIsauto = flagIsauto;
	}

	public String getSqlNote() {
		return this.sqlNote;
	}

	public void setSqlNote(String sqlNote) {
		this.sqlNote = sqlNote;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}