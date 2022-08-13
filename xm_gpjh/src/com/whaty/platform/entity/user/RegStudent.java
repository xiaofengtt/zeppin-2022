package com.whaty.platform.entity.user;

public abstract class RegStudent extends Student{

	protected String reg_id;
	
	protected String semester_id;
	
	protected String semester_name;

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getSemester_id() {
		return semester_id;
	}

	public void setSemester_id(String semester_id) {
		this.semester_id = semester_id;
	}

	public String getSemester_name() {
		return semester_name;
	}

	public void setSemester_name(String semester_name) {
		this.semester_name = semester_name;
	}
	
	
}
