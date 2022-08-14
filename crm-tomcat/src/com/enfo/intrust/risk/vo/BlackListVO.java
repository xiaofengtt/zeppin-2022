package com.enfo.intrust.risk.vo;

public class BlackListVO {
	private String name;
	private String nameType;
	private String commonName;
	private String state;
	private String address;
	private String gender;
	private String dateOfBirth;
	private String residence;
	private String nationality;
	private double ratingscore;
	private String sourcecategory;
	private String subcategory;
	private String matchingsource;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameType() {
		return this.nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public String getCommonName() {
		return this.commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public double getRatingscore() {
		return this.ratingscore;
	}

	public void setRatingscore(double ratingscore) {
		this.ratingscore = ratingscore;
	}

	public String getSourcecategory() {
		return this.sourcecategory;
	}

	public void setSourcecategory(String sourcecategory) {
		this.sourcecategory = sourcecategory;
	}

	public String getSubcategory() {
		return this.subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getMatchingsource() {
		return this.matchingsource;
	}

	public void setMatchingsource(String matchingsource) {
		this.matchingsource = matchingsource;
	}
}
