/*
 * questionElite.java
 *
 * Created on 2005��1��6��, ����3:15
 */

package com.whaty.platform.interaction.answer;

import com.whaty.platform.entity.basic.Course;

/**
 * ����������е�Ŀ¼����
 * 
 * @author chenjian
 */
public abstract class QuestionEliteDir implements com.whaty.platform.Items,
		com.whaty.platform.DirTree {

	private String id; 

	private String name;

	private String note;

	private String date;

	private Course course;

	private QuestionEliteDir dirFather;

	/** Creates a new instance of questionElite */
	public QuestionEliteDir() {
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public QuestionEliteDir getDirFather() {
		return dirFather;
	}

	public void setDirFather(QuestionEliteDir dirFather) {
		this.dirFather = dirFather;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * �õ��ӽڵ���Ŀ
	 * 
	 * @return �ӽڵ���Ŀ
	 */
	public abstract int isChildDirExist();

	/**
	 * �õ��ӽڵ㳣��������Ŀ
	 * 
	 * @return �ӽڵ㳣��������Ŀ
	 */
	public abstract int isChildEliteQuestionExist();

}
