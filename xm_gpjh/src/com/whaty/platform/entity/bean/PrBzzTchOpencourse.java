package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrBzzTchOpencourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrBzzTchOpencourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeBzzBatch peBzzBatch;
	private String fkCourseId;
	private String flagCourseType;
	private String flagBak;
	private Long roomId;
	private Set peBzzAssesses = new HashSet(0);
	private Set stuttimes = new HashSet(0);
	private Set prBzzTchStuElectives = new HashSet(0);
	private Set peBzzSuggestions = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrBzzTchOpencourse() {
	}

	/** full constructor */
	public PrBzzTchOpencourse(PeBzzBatch peBzzBatch, String fkCourseId,
			String flagCourseType, String flagBak, Long roomId,
			Set peBzzAssesses, Set stuttimes, Set prBzzTchStuElectives,
			Set peBzzSuggestions) {
		this.peBzzBatch = peBzzBatch;
		this.fkCourseId = fkCourseId;
		this.flagCourseType = flagCourseType;
		this.flagBak = flagBak;
		this.roomId = roomId;
		this.peBzzAssesses = peBzzAssesses;
		this.stuttimes = stuttimes;
		this.prBzzTchStuElectives = prBzzTchStuElectives;
		this.peBzzSuggestions = peBzzSuggestions;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzBatch getPeBzzBatch() {
		return this.peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public String getFlagCourseType() {
		return this.flagCourseType;
	}

	public void setFlagCourseType(String flagCourseType) {
		this.flagCourseType = flagCourseType;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Set getPeBzzAssesses() {
		return this.peBzzAssesses;
	}

	public void setPeBzzAssesses(Set peBzzAssesses) {
		this.peBzzAssesses = peBzzAssesses;
	}

	public Set getStuttimes() {
		return this.stuttimes;
	}

	public void setStuttimes(Set stuttimes) {
		this.stuttimes = stuttimes;
	}

	public Set getPrBzzTchStuElectives() {
		return this.prBzzTchStuElectives;
	}

	public void setPrBzzTchStuElectives(Set prBzzTchStuElectives) {
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

	public Set getPeBzzSuggestions() {
		return this.peBzzSuggestions;
	}

	public void setPeBzzSuggestions(Set peBzzSuggestions) {
		this.peBzzSuggestions = peBzzSuggestions;
	}

}