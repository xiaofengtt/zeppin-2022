/**
 * 
 */
package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class TeachPlan implements com.whaty.platform.Items {
	private String id;

	private String title;

	private Major major;

	private EduType eduType;

	private Grade grade;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract List getCourses(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public abstract int addCourses(List courseList) throws PlatformException;

	public abstract int removeCourses(List courseList) throws PlatformException;

	public abstract void copyCourses(TeachPlan teachplan)
			throws PlatformException;
	
}
