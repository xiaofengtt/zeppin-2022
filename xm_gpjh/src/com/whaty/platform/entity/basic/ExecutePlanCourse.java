/**
 * 
 */
package com.whaty.platform.entity.basic;

import com.whaty.platform.test.exam.BasicSequence;

/**
 * @author wangqiang
 * 
 */
public abstract class ExecutePlanCourse {
	private String id ;

	private ExecutePlanCourseGroup group;

	private TeachPlanCourse course;

	private BasicSequence sequence;

	private String examType;

	public TeachPlanCourse getCourse() {
		return course;
	}

	public void setCourse(TeachPlanCourse course) {
		this.course = course;
	}

	public ExecutePlanCourseGroup getGroup() {
		return group;
	}

	public void setGroup(ExecutePlanCourseGroup group) {
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BasicSequence getSequence() {
		return sequence;
	}

	public void setSequence(BasicSequence sequence) {
		this.sequence = sequence;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

}
