package com.whaty.platform.entity.bean;

/**
 * PrStudentHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStudentHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private String dateSection;
	private String place;
	private String zhiwu;

	// Constructors

	/** default constructor */
	public PrStudentHistory() {
	}

	/** full constructor */
	public PrStudentHistory(PeStudent peStudent, String dateSection,
			String place, String zhiwu) {
		this.peStudent = peStudent;
		this.dateSection = dateSection;
		this.place = place;
		this.zhiwu = zhiwu;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public String getDateSection() {
		return this.dateSection;
	}

	public void setDateSection(String dateSection) {
		this.dateSection = dateSection;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getZhiwu() {
		return this.zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

}