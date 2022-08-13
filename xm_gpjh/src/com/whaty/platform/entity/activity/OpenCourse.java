/*
 * OpenCourse.java
 *
 * Created on 2004年11月29日, 下午3:33
 */

package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.test.exam.BasicSequence;
import com.whaty.platform.util.Page;
import com.whaty.platform.entity.bean.PeBzzTchCourse;

/**
 * 开课对象信息
 * 
 * @author Ligang
 */
public abstract class OpenCourse {

	private String id;

	private PeTchCourse course;

	private PeSemester semester;

	private BasicSequence examSequence;
	
	private PeBzzTchCourse bzzCourse;

	/**
	 * 属性 id 的获取方法。
	 * 
	 * @return 属性 id 的值。
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * 属性 id 的设置方法。
	 * 
	 * @param id
	 *            属性 id 的新值。
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 属性 semester 的设置方法。
	 * 
	 * @param semester
	 *            属性 semester 的新值。
	 */
//	public void setSemester(Semester semester) {
//		this.semester = semester;
//	}
//
//	/**
//	 * 属性 semester 的获取方法。
//	 * 
//	 * @return 属性 semester 的值。
//	 */
//	public Semester getSemester() {
//		return semester;
//	}
//
//	/**
//	 * 属性 course 的获取方法。
//	 * 
//	 * @return 属性 course 的值。
//	 */
//	public Course getCourse() {
//		return course;
//	}
//
//	/**
//	 * 属性 course 的设置方法。
//	 * 
//	 * @param course
//	 *            属性 course 的新值。
//	 */
//	public void setCourse(Course course) {
//		this.course = course;
//	}

	public BasicSequence getExamSequence() {
		return examSequence;
	}

	public void setExamSequence(BasicSequence examSequence) {
		this.examSequence = examSequence;
	}

	/**
	 * 增加教学班
	 * 
	 * @param teachClass
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addTeachClass(TeachClass teachClass)
			throws PlatformException;

	/**
	 * 删除教学班，注意必须清空该班级学生后再删除教学班
	 * 
	 * @param teachClassList
	 * @throws PlatformException
	 */
	public abstract void removeTeachClass(List teachClassList)
			throws PlatformException;

	/**
	 * 得到本开课下的教学班列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachClasses(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public PeTchCourse getCourse() {
		return course;
	}

	public void setCourse(PeTchCourse course) {
		this.course = course;
	}

	public PeSemester getSemester() {
		return semester;
	}

	public void setSemester(PeSemester semester) {
		this.semester = semester;
	}

	public PeBzzTchCourse getBzzCourse() {
		return bzzCourse;
	}

	public void setBzzCourse(PeBzzTchCourse bzzCourse) {
		this.bzzCourse = bzzCourse;
	}

}
