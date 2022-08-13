package com.whaty.platform.entity.bean;

/**
 * PrStuChangeableMajor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuChangeableMajor extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajorByFkNewMajorId;
	private PeMajor peMajorByFkOldMajorId;

	// Constructors

	/** default constructor */
	public PrStuChangeableMajor() {
	}

	/** full constructor */
	public PrStuChangeableMajor(PeMajor peMajorByFkNewMajorId,
			PeMajor peMajorByFkOldMajorId) {
		this.peMajorByFkNewMajorId = peMajorByFkNewMajorId;
		this.peMajorByFkOldMajorId = peMajorByFkOldMajorId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajorByFkNewMajorId() {
		return this.peMajorByFkNewMajorId;
	}

	public void setPeMajorByFkNewMajorId(PeMajor peMajorByFkNewMajorId) {
		this.peMajorByFkNewMajorId = peMajorByFkNewMajorId;
	}

	public PeMajor getPeMajorByFkOldMajorId() {
		return this.peMajorByFkOldMajorId;
	}

	public void setPeMajorByFkOldMajorId(PeMajor peMajorByFkOldMajorId) {
		this.peMajorByFkOldMajorId = peMajorByFkOldMajorId;
	}

}