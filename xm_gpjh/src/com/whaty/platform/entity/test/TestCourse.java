/**
 * 
 */
package com.whaty.platform.entity.test;

import com.whaty.platform.entity.activity.OpenCourse;

/**该类描述了考试课程,该类使用了装饰器的设计模式，包装了OpenCourse对象
 * @author chenjian
 *
 */
public abstract class TestCourse {
	
	private String id;
	
	private OpenCourse openCourse;
	
	private TestTimeDef testTime;
	
	private TestSequence testSequence;
	
	private String courseId;
	
	private String courseName;
	
	private String note;
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public OpenCourse getOpenCourse() {
		return openCourse;
	}
	public void setOpenCourse(OpenCourse openCourse) {
		this.openCourse = openCourse;
		this.setId(openCourse.getId());
		this.setCourseId(openCourse.getCourse().getId());
		this.setCourseName(openCourse.getCourse().getName());
	}
	public TestSequence getTestSequence() {
		return testSequence;
	}
	public void setTestSequence(TestSequence testSequence) {
		this.testSequence = testSequence;
	}
	public TestTimeDef getTestTime() {
		return testTime;
	}
	public void setTestTime(TestTimeDef testTime) {
		this.testTime = testTime;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
