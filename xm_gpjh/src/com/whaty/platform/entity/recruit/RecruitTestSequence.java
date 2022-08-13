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
	 * 为本考试场次添加考试科目
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addCourses(List courseList) throws PlatformException;

	/**
	 * 删除本考试场次的考试科目
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void deleteCourses(List courseList)
			throws PlatformException;

	/**
	 * 该考试场次下面是否有考试课程
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasCourses();

}
