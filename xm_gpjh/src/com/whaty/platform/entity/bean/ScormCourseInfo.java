package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * ScormCourseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ScormCourseInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String controlType;
	private String version;
	private String description;
	private String navigate;
	private String address;
	private String courseCode;
	private Set scormStuScos = new HashSet(0);
	private Set scormStuCourses = new HashSet(0);
	private Set scormCourseItems = new HashSet(0);

	// Constructors

	/** default constructor */
	public ScormCourseInfo() {
	}

	/** full constructor */
	public ScormCourseInfo(String title, String controlType, String version,
			String description, String navigate, String address,
			String courseCode, Set scormStuScos, Set scormStuCourses,
			Set scormCourseItems) {
		this.title = title;
		this.controlType = controlType;
		this.version = version;
		this.description = description;
		this.navigate = navigate;
		this.address = address;
		this.courseCode = courseCode;
		this.scormStuScos = scormStuScos;
		this.scormStuCourses = scormStuCourses;
		this.scormCourseItems = scormCourseItems;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getControlType() {
		return this.controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNavigate() {
		return this.navigate;
	}

	public void setNavigate(String navigate) {
		this.navigate = navigate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public Set getScormStuScos() {
		return this.scormStuScos;
	}

	public void setScormStuScos(Set scormStuScos) {
		this.scormStuScos = scormStuScos;
	}

	public Set getScormStuCourses() {
		return this.scormStuCourses;
	}

	public void setScormStuCourses(Set scormStuCourses) {
		this.scormStuCourses = scormStuCourses;
	}

	public Set getScormCourseItems() {
		return this.scormCourseItems;
	}

	public void setScormCourseItems(Set scormCourseItems) {
		this.scormCourseItems = scormCourseItems;
	}

}