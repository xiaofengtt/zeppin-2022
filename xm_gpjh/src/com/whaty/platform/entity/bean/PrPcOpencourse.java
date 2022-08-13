package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrPcOpencourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcOpencourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePcCourse pePcCourse;
	private PeSemester peSemester;
	private Set prPcBookingSeats = new HashSet(0);
	private Set pePcOpencourseCoursewares = new HashSet(0);
	private Set prPcElectives = new HashSet(0);
	private Set pePcNotes = new HashSet(0);
	private Set prPcOpencourseTeachers = new HashSet(0);
	private Set pePcExercises = new HashSet(0);
	private Set pePcOpencourseResources = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrPcOpencourse() {
	}

	/** full constructor */
	public PrPcOpencourse(PePcCourse pePcCourse, PeSemester peSemester,
			Set prPcBookingSeats, Set pePcOpencourseCoursewares,
			Set prPcElectives, Set pePcNotes, Set prPcOpencourseTeachers,
			Set pePcExercises, Set pePcOpencourseResources) {
		this.pePcCourse = pePcCourse;
		this.peSemester = peSemester;
		this.prPcBookingSeats = prPcBookingSeats;
		this.pePcOpencourseCoursewares = pePcOpencourseCoursewares;
		this.prPcElectives = prPcElectives;
		this.pePcNotes = pePcNotes;
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
		this.pePcExercises = pePcExercises;
		this.pePcOpencourseResources = pePcOpencourseResources;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePcCourse getPePcCourse() {
		return this.pePcCourse;
	}

	public void setPePcCourse(PePcCourse pePcCourse) {
		this.pePcCourse = pePcCourse;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public Set getPrPcBookingSeats() {
		return this.prPcBookingSeats;
	}

	public void setPrPcBookingSeats(Set prPcBookingSeats) {
		this.prPcBookingSeats = prPcBookingSeats;
	}

	public Set getPePcOpencourseCoursewares() {
		return this.pePcOpencourseCoursewares;
	}

	public void setPePcOpencourseCoursewares(Set pePcOpencourseCoursewares) {
		this.pePcOpencourseCoursewares = pePcOpencourseCoursewares;
	}

	public Set getPrPcElectives() {
		return this.prPcElectives;
	}

	public void setPrPcElectives(Set prPcElectives) {
		this.prPcElectives = prPcElectives;
	}

	public Set getPePcNotes() {
		return this.pePcNotes;
	}

	public void setPePcNotes(Set pePcNotes) {
		this.pePcNotes = pePcNotes;
	}

	public Set getPrPcOpencourseTeachers() {
		return this.prPcOpencourseTeachers;
	}

	public void setPrPcOpencourseTeachers(Set prPcOpencourseTeachers) {
		this.prPcOpencourseTeachers = prPcOpencourseTeachers;
	}

	public Set getPePcExercises() {
		return this.pePcExercises;
	}

	public void setPePcExercises(Set pePcExercises) {
		this.pePcExercises = pePcExercises;
	}

	public Set getPePcOpencourseResources() {
		return this.pePcOpencourseResources;
	}

	public void setPePcOpencourseResources(Set pePcOpencourseResources) {
		this.pePcOpencourseResources = pePcOpencourseResources;
	}

}