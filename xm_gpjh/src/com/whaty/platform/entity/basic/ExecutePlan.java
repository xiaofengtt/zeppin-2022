/**
 * 
 */
package com.whaty.platform.entity.basic;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public abstract class ExecutePlan implements com.whaty.platform.Items {
	private String id;

	private String title;

	private Semester semester;
	
	private TeachPlan plan;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public abstract int isExist() throws PlatformException;

	public TeachPlan getPlan() {
		return plan;
	}

	public void setPlan(TeachPlan plan) {
		this.plan = plan;
	}

}
