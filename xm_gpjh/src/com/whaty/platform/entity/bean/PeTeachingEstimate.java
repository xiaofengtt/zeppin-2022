package com.whaty.platform.entity.bean;

/**
 * PeTeachingEstimate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTeachingEstimate extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private PeTeachingEstimateId id;

	// Constructors

	/** default constructor */
	public PeTeachingEstimate() {
	}

	/** full constructor */
	public PeTeachingEstimate(PeTeachingEstimateId id) {
		this.id = id;
	}

	// Property accessors

	public PeTeachingEstimateId getId() {
		return this.id;
	}

	public void setId(PeTeachingEstimateId id) {
		this.id = id;
	}

}