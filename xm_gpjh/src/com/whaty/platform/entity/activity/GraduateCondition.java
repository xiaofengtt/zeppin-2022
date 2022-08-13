/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.HashMap;

import com.whaty.platform.Items;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;

public abstract class GraduateCondition implements Items {

	/*
	 * public abstract List checkByCredit(List studentList,int credit) throws
	 * PlatformException;
	 * 
	 * public abstract List checkByCompulsory(List studentList,String
	 * major_id,String edu_type_id,String grade_id) throws PlatformException;
	 */
	private String id;

	private Major major;

	private Grade grade;

	private EduType eduType;

	private HashMap condition;

	public HashMap getCondition() {
		return condition;
	}

	public void setCondition(HashMap condition) {
		this.condition = condition;
	}

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public abstract int isExist();
}