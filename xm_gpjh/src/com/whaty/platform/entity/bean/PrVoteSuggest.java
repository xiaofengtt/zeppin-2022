package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrVoteSuggest entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrVoteSuggest extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private PeVotePaper peVotePaper;
	private EnumConst enumConstByFlagCheck;
	private String ip;
	private Date foundDate;
	private String note;
	private String classIdentifier;

	// Constructors

	/** default constructor */
	public PrVoteSuggest() {
	}

	/** full constructor */
	public PrVoteSuggest(EnumConst enumConstByFlagBak, PeVotePaper peVotePaper,
			EnumConst enumConstByFlagCheck, String ip, Date foundDate,
			String note, String classIdentifier) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peVotePaper = peVotePaper;
		this.enumConstByFlagCheck = enumConstByFlagCheck;
		this.ip = ip;
		this.foundDate = foundDate;
		this.note = note;
		this.classIdentifier = classIdentifier;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public PeVotePaper getPeVotePaper() {
		return this.peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public EnumConst getEnumConstByFlagCheck() {
		return this.enumConstByFlagCheck;
	}

	public void setEnumConstByFlagCheck(EnumConst enumConstByFlagCheck) {
		this.enumConstByFlagCheck = enumConstByFlagCheck;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getFoundDate() {
		return this.foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getClassIdentifier() {
		return this.classIdentifier;
	}

	public void setClassIdentifier(String classIdentifier) {
		this.classIdentifier = classIdentifier;
	}

}