/**
 * 
 */
package com.whaty.platform.entity.basic;

/**
 * @author wangqiang
 * 
 */
public abstract class ExecutePlanCourseGroup {
	private String id;

	private String title;

	private ExecutePlan executePlan;

	private String type;

	private int max;

	private int min;

	public ExecutePlan getExecutePlan() {
		return executePlan;
	}

	public void setExecutePlan(ExecutePlan executePlan) {
		this.executePlan = executePlan;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
