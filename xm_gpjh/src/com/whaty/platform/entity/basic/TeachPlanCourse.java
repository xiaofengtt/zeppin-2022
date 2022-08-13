/**
 * 
 */
package com.whaty.platform.entity.basic;


/**
 * @author chenjian
 * 
 */
public abstract class TeachPlanCourse implements com.whaty.platform.Items {

	private String id;

	private Course course;

	/**
	 * ѧ��
	 */
	private float credit;

	private float coursetime;

	/**
	 * ����ΪTeachPlanCourseType�е�һ��
	 */
	private String type;

	private String isdegree;

	private TeachPlan teachPlan;

	private String semester;

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

	public float getCoursetime() {
		return coursetime;
	}

	public void setCoursetime(float coursetime) {
		this.coursetime = coursetime;
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

	public void setTeachPlan(TeachPlan teachPlan) {
		this.teachPlan = teachPlan;
	}

	public TeachPlan getTeachPlan() {
		return teachPlan;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

}
