package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeJob entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeJob extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private PeManager peManager;
	private EnumConst enumConstByFkJobPriority;
	private String name;
	private Date finishDate;
	private String note;
	private Date startDate;
	private String scopeString;
	private Set prJobUnits = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeJob() {
	}

	/** full constructor */
	public PeJob(EnumConst enumConstByFlagBak, PeManager peManager,
			EnumConst enumConstByFkJobPriority, String name, Date finishDate,
			String note, Date startDate, String scopeString, Set prJobUnits) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.peManager = peManager;
		this.enumConstByFkJobPriority = enumConstByFkJobPriority;
		this.name = name;
		this.finishDate = finishDate;
		this.note = note;
		this.startDate = startDate;
		this.scopeString = scopeString;
		this.prJobUnits = prJobUnits;
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

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public EnumConst getEnumConstByFkJobPriority() {
		return this.enumConstByFkJobPriority;
	}

	public void setEnumConstByFkJobPriority(EnumConst enumConstByFkJobPriority) {
		this.enumConstByFkJobPriority = enumConstByFkJobPriority;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getScopeString() {
		return this.scopeString;
	}

	public void setScopeString(String scopeString) {
		this.scopeString = scopeString;
	}

	public Set getPrJobUnits() {
		return this.prJobUnits;
	}

	public void setPrJobUnits(Set prJobUnits) {
		this.prJobUnits = prJobUnits;
	}

}