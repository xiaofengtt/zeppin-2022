package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeProvince entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProvince extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String note;
	private Set peTrainees = new HashSet(0);
	private Set peUnits = new HashSet(0);
	private Set peTrainExperts = new HashSet(0);
	
	private Set citys = new HashSet(0);
	
//	private PeProvince peProvince;
//	private String level;
//	private String isPoor;
//	private String isCountryPoor;
//	
//	private Set peProvinces = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeProvince() {
	}

	/** full constructor */
	public PeProvince(String id, String name, String code, String note,
			Set peTrainees, Set peUnits, Set peTrainExperts) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.note = note;
		this.peTrainees = peTrainees;
		this.peUnits = peUnits;
		this.peTrainExperts = peTrainExperts;
//		this.peProvince = peProvince;
//		this.level = level;
//		this.isPoor = isPoor;
//		this.isCountryPoor = isCountryPoor;
//		this.peProvinces = peProvinces;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeTrainees() {
		return this.peTrainees;
	}

	public void setPeTrainees(Set peTrainees) {
		this.peTrainees = peTrainees;
	}

	public Set getPeUnits() {
		return this.peUnits;
	}

	public void setPeUnits(Set peUnits) {
		this.peUnits = peUnits;
	}

	public Set getPeTrainExperts() {
		return this.peTrainExperts;
	}

	public void setPeTrainExperts(Set peTrainExperts) {
		this.peTrainExperts = peTrainExperts;
	}

	
	public Set getCitys() {
		return citys;
	}
	

	public void setCitys(Set citys) {
		this.citys = citys;
	}

	

	
}