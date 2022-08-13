package com.whaty.platform.entity.fee.level;

import com.whaty.platform.entity.user.Student;

public abstract class UserChargeLevelOfType {
	
	private Student student;
	
	private String value;
	
	

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
