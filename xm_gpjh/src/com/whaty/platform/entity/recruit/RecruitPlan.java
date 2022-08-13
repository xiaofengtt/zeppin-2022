package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.basic.Site;

public abstract class RecruitPlan implements Items {

	private String id = "";

	private String title = "";

	private RecruitBatch batch;

	private EduType edutype;

	private Major major;

	private Site site;

	private RecruitLimit limit;

	private String status = "";

	private String reject_note = "";

	public EduType getEdutype() {
		return edutype;
	}

	public void setEdutype(EduType edutype) {
		this.edutype = edutype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RecruitLimit getLimit() {
		return limit;
	}

	public void setLimit(RecruitLimit limit) {
		this.limit = limit;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public RecruitBatch getBatch() {
		return batch;
	}

	public void setBatch(RecruitBatch batch) {
		this.batch = batch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReject_note() {
		return reject_note;
	}

	public void setReject_note(String reject_note) {
		this.reject_note = reject_note;
	}

	public abstract int confirm() throws PlatformException;

	public abstract int unConfirm() throws PlatformException;

	public abstract int rejectConfirm() throws PlatformException;

	public abstract int updateNum() throws PlatformException;

	public abstract int isExist() throws PlatformException;
}
