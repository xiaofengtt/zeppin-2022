package com.whaty.platform.test.exam;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

public abstract class ExamCourse implements Items {
	private String id;

	private String name;

	private String startDate;

	private String endDate;

	private ExamBatch examBatch;

	private String course_id;

	private ExamSequence sequence;

	private String courseType;

	private String examType;
	
	private int  examUserTotalNum =0; //考此门课程的考生总人数
	
	private int examUserTotalSetNum =0;//参加此门课程已经分配的考生人数

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ExamBatch getExamBatch() {
		return examBatch;
	}

	public void setExamBatch(ExamBatch examBatch) {
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

	public ExamSequence getSequence() {
		return sequence;
	}

	public void setSequence(ExamSequence sequence) {
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


	public int getExamUserTotalNum() {
		return examUserTotalNum;
	}

	public void setExamUserTotalNum(int examUserTotalNum) {
		this.examUserTotalNum = examUserTotalNum;
	}

	public int getExamUserTotalSetNum() {
		return examUserTotalSetNum;
	}

	public void setExamUserTotalSetNum(int examUserTotalSetNum) {
		this.examUserTotalSetNum = examUserTotalSetNum;
	}

}
