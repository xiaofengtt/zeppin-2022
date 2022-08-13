package com.whaty.platform.vote.basic;

import java.util.Date;
import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.vote.util.exception.VoteException;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;

public abstract class VotePaper implements Items {

	private String id;

	private String picTitle;

	private String title;

	private String note;

	private boolean active;

	private Date foundDate;

	private Date startDate;

	private Date endDate;

	private boolean canSuggest;

	private boolean viewSuggest;

	private boolean limitDiffIP;

	private boolean limitDiffSession;

	private String type;

	private String keywords;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getPicTitle() {
		return picTitle;
	}

	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the viewSuggest
	 */
	public boolean isViewSuggest() {
		return viewSuggest;
	}

	/**
	 * @return the canSuggest
	 */
	public boolean isCanSuggest() {
		return canSuggest;
	}

	/**
	 * @param canSuggest
	 *            the canSuggest to set
	 */
	public void setCanSuggest(boolean canSuggest) {
		this.canSuggest = canSuggest;
	}

	/**
	 * @param viewSuggest
	 *            the viewSuggest to set
	 */
	public void setViewSuggest(boolean viewSuggest) {
		this.viewSuggest = viewSuggest;
	}

	/**
	 * @return the limitDiffIP
	 */
	public boolean isLimitDiffIP() {
		return limitDiffIP;
	}

	/**
	 * @param limitDiffIP
	 *            the limitDiffIP to set
	 */
	public void setLimitDiffIP(boolean limitDiffIP) {
		this.limitDiffIP = limitDiffIP;
	}

	/**
	 * @return the limitDiffSession
	 */
	public boolean isLimitDiffSession() {
		return limitDiffSession;
	}

	/**
	 * @param limitDiffSession
	 *            the limitDiffSession to set
	 */
	public void setLimitDiffSession(boolean limitDiffSession) {
		this.limitDiffSession = limitDiffSession;
	}

	/**
	 * �õ�������ʾ��µ�����
	 * 
	 * @return
	 * @throws VoteException
	 */
	public abstract List getVoteQuestions() throws VoteException;

	/**
	 * ��ӵ���ʾ��е�����
	 * 
	 * @param question
	 * @throws VoteException
	 */
	public abstract void addVoteQuestion(VoteQuestion question)
			throws VoteException;

	/**
	 * ɾ�����ʾ��е�����
	 * 
	 * @param questionIds
	 * @throws VoteException
	 */
	public abstract void removeVoteQuestion(List questionIds)
			throws VoteException;

	/**
	 * �õ�ͶƱ�Ĵ���
	 * 
	 * @return
	 * @throws VoteException
	 */
	public abstract int getVoteNum() throws VoteException;

	public String getStartDateStr() throws VoteException {
		if (this.getStartDate() == null)
			return "";

		StrManage strManage = StrManageFactory.creat();
		try {
			return strManage.dateToStr(this.getStartDate(),
					"yyyy-MM-dd hh:mm:ss");
		} catch (WhatyUtilException e) {
			throw new VoteException("startDate error!(" + e.toString() + ")");
		}
	}

	public String getEndDateStr() throws VoteException {
		if (this.getEndDate() == null)
			return "";

		StrManage strManage = StrManageFactory.creat();
		try {
			return strManage
					.dateToStr(this.getEndDate(), "yyyy-MM-dd hh:mm:ss");
		} catch (WhatyUtilException e) {
			throw new VoteException("endDate error!(" + e.toString() + ")");
		}
	}

}
