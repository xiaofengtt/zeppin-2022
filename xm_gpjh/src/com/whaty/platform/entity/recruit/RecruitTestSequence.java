package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

public abstract class RecruitTestSequence implements Items {
	private String id = "";

	private String title = "";

	private TimeDef time;

	private String note = "";

	private RecruitTestBatch testBatch;

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

	public RecruitTestBatch getTestBatch() {
		return testBatch;
	}

	public void setTestBatch(RecruitTestBatch testBatch) {
		this.testBatch = testBatch;
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
	 * Ϊ�����Գ�����ӿ��Կ�Ŀ
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addCourses(List courseList) throws PlatformException;

	/**
	 * ɾ�������Գ��εĿ��Կ�Ŀ
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void deleteCourses(List courseList)
			throws PlatformException;

	/**
	 * �ÿ��Գ��������Ƿ��п��Կγ�
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasCourses();

}
