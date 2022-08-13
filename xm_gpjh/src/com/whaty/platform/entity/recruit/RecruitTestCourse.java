package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitTestCourse implements Items {
	private RecruitCourse course;

	private String id = "";

	private String title = "";

	private TimeDef time;

	private String note = "";

	private RecruitTestSequence testSequence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public RecruitTestSequence getTestSequence() {
		return testSequence;
	}

	public void setTestSequence(RecruitTestSequence testSequence) {
		this.testSequence = testSequence;
	}

	public TimeDef getTime() {
		return time;
	}

	public void setTime(TimeDef time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ������γ��Ƿ��ѱ�ʹ��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasTestDesks();

	public RecruitCourse getCourse() {
		return course;
	}

	public void setCourse(RecruitCourse course) {
		this.course = course;
	}

}
