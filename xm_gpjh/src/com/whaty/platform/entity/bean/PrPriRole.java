package com.whaty.platform.entity.bean;

/**
 * PrPriRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPriRole extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePriRole pePriRole;
	private PePriority pePriority;
	private String flagIsvalid;

	// Constructors

	/** default constructor */
	public PrPriRole() {
	}

	/** full constructor */
	public PrPriRole(PePriRole pePriRole, PePriority pePriority,
			String flagIsvalid) {
		this.pePriRole = pePriRole;
		this.pePriority = pePriority;
		this.flagIsvalid = flagIsvalid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePriRole getPePriRole() {
		return this.pePriRole;
	}

	public void setPePriRole(PePriRole pePriRole) {
		this.pePriRole = pePriRole;
	}

	public PePriority getPePriority() {
		return this.pePriority;
	}

	public void setPePriority(PePriority pePriority) {
		this.pePriority = pePriority;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

}