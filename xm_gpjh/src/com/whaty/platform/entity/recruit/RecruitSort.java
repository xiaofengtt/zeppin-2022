package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.util.Page;

public abstract class RecruitSort implements Items {

	private String id;

	private EduType edutype;

	private String name;

	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EduType getEdutype() {
		return edutype;
	}

	public void setEdutype(EduType edutype) {
		this.edutype = edutype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public abstract int getMajorsNum(List searchProperty)
			throws PlatformException;

	public abstract List getMajors(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public abstract int setMajors(String recruitSortId, List majorId,
			List majorIds) throws PlatformException;

	public abstract int getCoursesNum(List searchProperty)
			throws PlatformException;

	public abstract List getCourses(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public abstract int setCourses(String recruitSortId, List courseId,
			List courseIds) throws PlatformException;

}
