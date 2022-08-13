package com.whaty.platform.test.reexam;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

public abstract class ReExamCourse implements Items {
	private String id;

	private String name;

	private String startDate;

	private String endDate;

	private ReExamBatch examBatch;

	private String course_id;

	private ReExamSequence sequence;

	private String courseType;

	private String examType;
	
	private String course_name;
	
	private String edu_type_id;
	
	private String edu_type_name;
	
	private String major_id;
	
	private String major_name;
	
	private String kaoqu_id;
	
	private String kaoqu_name;
	
	private String shenfen_id;
	
	private String grade_id;
	
	private String grade_name;
	
	private String useres;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ReExamBatch getExamBatch() {
		return examBatch;
	}

	public void setExamBatch(ReExamBatch examBatch) {
		this.examBatch = examBatch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public ReExamSequence getSequence() {
		return sequence;
	}

	public void setSequence(ReExamSequence sequence) {
		this.sequence = sequence;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public abstract int changeCourseType() throws PlatformException;

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getEdu_type_id() {
		return edu_type_id;
	}

	public void setEdu_type_id(String edu_type_id) {
		this.edu_type_id = edu_type_id;
	}

	public String getEdu_type_name() {
		return edu_type_name;
	}

	public void setEdu_type_name(String edu_type_name) {
		this.edu_type_name = edu_type_name;
	}

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getKaoqu_id() {
		return kaoqu_id;
	}

	public void setKaoqu_id(String kaoqu_id) {
		this.kaoqu_id = kaoqu_id;
	}

	public String getKaoqu_name() {
		return kaoqu_name;
	}

	public void setKaoqu_name(String kaoqu_name) {
		this.kaoqu_name = kaoqu_name;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getMajor_name() {
		return major_name;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}

	public String getShenfen_id() {
		return shenfen_id;
	}

	public void setShenfen_id(String shenfen_id) {
		this.shenfen_id = shenfen_id;
	}

	public String getUseres() {
		return useres;
	}

	public void setUseres(String useres) {
		this.useres = useres;
	}

}
