package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitTestRoom implements Items {
	private String id = "";

	private String title = "";

	private String note = "";

	private String roomNo = "";

	private RecruitTestSite testSite;

	private RecruitBatch batch;

	private RecruitSort sort;

	private int num = 0;

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public RecruitTestSite getTestSite() {
		return testSite;
	}

	public void setTestSite(RecruitTestSite testSite) {
		this.testSite = testSite;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * ������γ��Ƿ��ѱ�ʹ��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasTestDesks();

	public RecruitSort getSort() {
		return sort;
	}

	public void setSort(RecruitSort sort) {
		this.sort = sort;
	}

	public RecruitBatch getBatch() {
		return batch;
	}

	public void setBatch(RecruitBatch batch) {
		this.batch = batch;
	}

	public abstract String getNumByBatchId(String batch_id);
}
