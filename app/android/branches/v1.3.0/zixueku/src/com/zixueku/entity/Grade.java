package com.zixueku.entity;

import java.util.List;

/**
 * 类说明 学段
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-27 上午9:56:43
 */
public class Grade {
	private String id;
	private String name;
	private boolean isSelectedAll;
	private List<Subject> subjects;

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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public boolean isSelectedAll() {
		return isSelectedAll;
	}

	public void setSelectedAll(boolean isSelectedAll) {
		this.isSelectedAll = isSelectedAll;
	}

	@Override
	public String toString() {
		return "Grade [id=" + id + ", name=" + name + ", isSelectedAll=" + isSelectedAll + ", subjects=" + subjects + "]";
	}

}
