package com.whaty.platform.entity.right;

import java.util.Hashtable;

public abstract class RightGroup implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String is_subsite;
	
	private String office_id;

	private String site;

	private String major;

	private String grade;

	private String eduType;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_subsite() {
		return is_subsite;
	}

	public void setIs_subsite(String is_subsite) {
		this.is_subsite = is_subsite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEduType() {
		return eduType;
	}

	public void setEduType(String eduType) {
		this.eduType = eduType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public abstract int changeGroupRights(String office_id, String[] checkgroup);

	public abstract Hashtable getRightsHash();
	
	public abstract int updateRangeGroupRights(String id,String site,String grade,String major,String edutype);

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	
	
}
