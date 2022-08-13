package com.whaty.platform.test.onlinetest;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.util.Page;

public abstract class OnlineTestCourse implements Items {
	private String id;

	private String title;

	private String groupId;

	private OnlineTestBatch batch;

	private String note;

	private String creatUser;

	private String creatDate;

	private String startDate;

	private String endDate;

	private String status;

	private String isAutoCheck;

	private String isHiddenAnswer;

	private List paperList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OnlineTestBatch getBatch() {
		return batch;
	}

	public void setBatch(OnlineTestBatch batch) {
		this.batch = batch;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsAutoCheck() {
		return isAutoCheck;
	}

	public void setIsAutoCheck(String isAutoCheck) {
		this.isAutoCheck = isAutoCheck;
	}

	public String getIsHiddenAnswer() {
		return isHiddenAnswer;
	}

	public void setIsHiddenAnswer(String isHiddenAnswer) {
		this.isHiddenAnswer = isHiddenAnswer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List getPaperList() {
		return paperList;
	}

	public void setPaperList(List paperList) {
		this.paperList = paperList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	public abstract int setActive();

	public abstract int cancelActive();

	public abstract int reverseActive();

	public abstract List getTestPapers(Page page);

	public abstract int getTestPapersNum();

	public abstract int addTestPaper(String paperId);

	public abstract int deleteTestPaper(String paperId);

}
