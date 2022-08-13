/**
 * 
 */
package com.whaty.platform.entity.basic;

import com.whaty.platform.entity.user.Student;

/**
 * @author chenjian
 * 
 */
public abstract class SingleTeachPlanCourse implements com.whaty.platform.Items {

	private String id;

	private Course course;

	private Student student;

	/**
	 * ѧ��
	 */
	private float credit;

	private float courseTime;

	/**
	 * ����ΪTeachPlanCourseType�е�һ��
	 */
	private String type;

	private String isdegree;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public float getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(float courseTime) {
		this.courseTime = courseTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getIsdegree() {
		return isdegree;
	}

	public void setIsdegree(String isdegree) {
		this.isdegree = isdegree;
	}

	public abstract int isDeletedCourse();

	public abstract int isAddedCourse();
}
