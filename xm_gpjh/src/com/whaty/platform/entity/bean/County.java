package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeProvince entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class County extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String note;
	private City city;
	private Set peTrainees = new HashSet(0);
	
	private String isPoor;
	private String isCountryPoor;

	// Constructors

	/** default constructor */
	public County() {
	}

	/** full constructor */
	public County(String id, String name, String code, String note,String isPoor,String isCountryPoor,
			Set peTrainees) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.note = note;
		this.isPoor = isPoor;
		this.isCountryPoor = isCountryPoor;
		this.peTrainees = peTrainees;
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

	
	public City getCity() {
		return city;
	}
	

	public void setCity(City city) {
		this.city = city;
	}
	

	public String getIsPoor() {
		return isPoor;
	}
	

	public void setIsPoor(String isPoor) {
		this.isPoor = isPoor;
	}
	

	public String getIsCountryPoor() {
		return isCountryPoor;
	}
	

	public void setIsCountryPoor(String isCountryPoor) {
		this.isCountryPoor = isCountryPoor;
	}

	
	
}