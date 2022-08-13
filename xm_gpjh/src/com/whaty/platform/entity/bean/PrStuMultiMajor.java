package com.whaty.platform.entity.bean;

/**
 * PrStuMultiMajor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuMultiMajor extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajor;
	private String oldMajor;

	// Constructors

	/** default constructor */
	public PrStuMultiMajor() {
	}

	/** full constructor */
	public PrStuMultiMajor(PeMajor peMajor, String oldMajor) {
		this.peMajor = peMajor;
		this.oldMajor = oldMajor;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public String getOldMajor() {
		return this.oldMajor;
	}

	public void setOldMajor(String oldMajor) {
		this.oldMajor = oldMajor;
	}

}