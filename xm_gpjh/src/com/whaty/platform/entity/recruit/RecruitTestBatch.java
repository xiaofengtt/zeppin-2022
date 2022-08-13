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
	 * ���ñ���������Ϊ����
	 * 
	 * @return 0Ϊ���ɹ���1Ϊ�ɹ�
	 */
	public abstract int setActive();

	/**
	 * ���ñ���������Ϊ������
	 * 
	 * @return 0Ϊ���ɹ�;1Ϊ�ɹ�
	 */
	public abstract int cancelActive();

	/**
	 * Ϊ�����Լƻ���ӿ��Գ���
	 * 
	 * @param sequenceList
	 * @throws PlatformException
	 */
	public abstract void addSequences(List sequenceList)
			throws PlatformException;

	/**
	 * ɾ�������Լƻ��Ŀ��Գ���
	 * 
	 * @param sequenceList
	 * @throws PlatformException
	 */
	public abstract void deleteSequences(List sequenceList)
			throws PlatformException;

	/**
	 * �ÿ������������Ƿ��п��Գ���
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasSequences();

}
