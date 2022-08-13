package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeProvince entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class City extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String note;
	private PeProvince peProvince;
	private Set peTrainees = new HashSet(0);
	private Set countys = new HashSet(0);
	

	// Constructors

	/** default constructor */
	public City() {
	}

	/** full constructor */
	public City(String id, String name, String code, String note,PeProvince peProvince,
			Set peTrainees, Set countys) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.note = note;
		this.peProvince = peProvince;
		this.peTrainees = peTrainees;
		this.countys = countys;
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

	
	public Set getCountys() {
		return countys;
	}

	public void setCountys(Set countys) {
		this.countys = countys;
	}

	public PeProvince getPeProvince() {
		return peProvince;
	}

	public void setPeProvince(PeProvince peProvince) {
		this.peProvince = peProvince;
	}


	

	
}