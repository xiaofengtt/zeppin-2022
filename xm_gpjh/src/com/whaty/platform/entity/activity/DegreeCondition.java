/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.HashMap;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;

/**
 * @author chenjian
 * 
 */
public abstract class DegreeCondition {
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
