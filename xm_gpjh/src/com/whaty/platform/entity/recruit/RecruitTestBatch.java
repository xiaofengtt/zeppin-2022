package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

public abstract class RecruitTestBatch implements Items {
	private String id = "";

	private String title = "";

	private TimeDef time;

	private boolean active = false;

	private String note = "";

	private RecruitBatch batch;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public RecruitBatch getBatch() {
		return batch;
	}

	public void setBatch(RecruitBatch batch) {
		this.batch = batch;
	}

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
	 * 设置本考试批次为激活
	 * 
	 * @return 0为不成功；1为成功
	 */
	public abstract int setActive();

	/**
	 * 设置本考试批次为不激活
	 * 
	 * @return 0为不成功;1为成功
	 */
	public abstract int cancelActive();

	/**
	 * 为本考试计划添加考试场次
	 * 
	 * @param sequenceList
	 * @throws PlatformException
	 */
	public abstract void addSequences(List sequenceList)
			throws PlatformException;

	/**
	 * 删除本考试计划的考试场次
	 * 
	 * @param sequenceList
	 * @throws PlatformException
	 */
	public abstract void deleteSequences(List sequenceList)
			throws PlatformException;

	/**
	 * 该考试批次下面是否有考试场次
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasSequences();

}
