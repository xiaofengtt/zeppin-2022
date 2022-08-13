package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchOpencourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchOpencourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String fkCourseId;
	private String adviceExamNo;
	private Long courseTime;
	private String flagExamCourse;
	private String flagBak;
	private Set prTchOpencourseCoursewares = new HashSet(0);
	private Set prTchOpencourseTeachers = new HashSet(0);
	private Set prTchOpencourseBooks = new HashSet(0);
	private Set prCourseArranges = new HashSet(0);
	private Set prTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchOpencourse() {
	}

	/** full constructor */
	public PrTchOpencourse(PeSemester peSemester, String fkCourseId,
			String adviceExamNo, Long courseTime, String flagExamCourse,
			String flagBak, Set prTchOpencourseCoursewares,
			Set prTchOpencourseTeachers, Set prTchOpencourseBooks,
			Set prCourseArranges, Set prTchStuElectives) {
		this.peSemester = peSemester;
		this.fkCourseId = fkCourseId;
		this.adviceExamNo = adviceExamNo;
		this.courseTime = courseTime;
		this.flagExamCourse = flagExamCourse;
		this.flagBak = flagBak;
		this.prTchOpencourseCoursewares = prTchOpencourseCoursewares;
		this.prTchOpencourseTeachers = prTchOpencourseTeachers;
		this.prTchOpencourseBooks = prTchOpencourseBooks;
		this.prCourseArranges = prCourseArranges;
		this.prTchStuElectives = prTchStuElectives;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public String getAdviceExamNo() {
		return this.adviceExamNo;
	}

	public void setAdviceExamNo(String adviceExamNo) {
		this.adviceExamNo = adviceExamNo;
	}

	public Long getCourseTime() {
		return this.courseTime;
	}

	public void setCourseTime(Long courseTime) {
		this.courseTime = courseTime;
	}

	public String getFlagExamCourse() {
		return this.flagExamCourse;
	}

	public void setFlagExamCourse(String flagExamCourse) {
		this.flagExamCourse = flagExamCourse;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Set getPrTchOpencourseCoursewares() {
		return this.prTchOpencourseCoursewares;
	}

	public void setPrTchOpencourseCoursewares(Set prTchOpencourseCoursewares) {
		this.prTchOpencourseCoursewares = prTchOpencourseCoursewares;
	}

	public Set getPrTchOpencourseTeachers() {
		return this.prTchOpencourseTeachers;
	}

	public void setPrTchOpencourseTeachers(Set prTchOpencourseTeachers) {
		this.prTchOpencourseTeachers = prTchOpencourseTeachers;
	}

	public Set getPrTchOpencourseBooks() {
		return this.prTchOpencourseBooks;
	}

	public void setPrTchOpencourseBooks(Set prTchOpencourseBooks) {
		this.prTchOpencourseBooks = prTchOpencourseBooks;
	}

	public Set getPrCourseArranges() {
		return this.prCourseArranges;
	}

	public void setPrCourseArranges(Set prCourseArranges) {
		this.prCourseArranges = prCourseArranges;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

}