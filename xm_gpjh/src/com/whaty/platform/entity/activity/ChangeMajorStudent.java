package com.whaty.platform.entity.activity;

import com.whaty.platform.entity.user.Student;

public abstract class ChangeMajorStudent extends Student{

	private String change_major_id;
	private String old_site_id;
	private String old_major_id;
	private String old_edu_type;
	private String old_grade_id;
	private String old_sitename;
	private String old_majorname;
	private String old_eduname;
	private String old_gradename;
	private String change_date;
	public String getChange_date() {
		return change_date;
	}
	public void setChange_date(String change_date) {
		this.change_date = change_date;
	}
	public String getOld_edu_type() {
		return old_edu_type;
	}
	public void setOld_edu_type(String old_edu_type) {
		this.old_edu_type = old_edu_type;
	}
	public String getOld_grade_id() {
		return old_grade_id;
	}
	public void setOld_grade_id(String old_grade_id) {
		this.old_grade_id = old_grade_id;
	}
	public String getOld_major_id() {
		return old_major_id;
	}
	public void setOld_major_id(String old_major_id) {
		this.old_major_id = old_major_id;
	}
	public String getOld_site_id() {
		return old_site_id;
	}
	public void setOld_site_id(String old_site_id) {
		this.old_site_id = old_site_id;
	}
	public String getOld_eduname() {
		return old_eduname;
	}
	public void setOld_eduname(String old_eduname) {
		this.old_eduname = old_eduname;
	}
	public String getOld_gradename() {
		return old_gradename;
	}
	public void setOld_gradename(String old_gradename) {
		this.old_gradename = old_gradename;
	}
	public String getOld_majorname() {
		return old_majorname;
	}
	public void setOld_majorname(String old_majorname) {
		this.old_majorname = old_majorname;
	}
	public String getOld_sitename() {
		return old_sitename;
	}
	public void setOld_sitename(String old_sitename) {
		this.old_sitename = old_sitename;
	}
	public String getChange_major_id() {
		return change_major_id;
	}
	public void setChange_major_id(String change_major_id) {
		this.change_major_id = change_major_id;
	}
	
}
