package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeVotePaper entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeVotePaper extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagViewSuggest;
	private PeProApplyno peProApplyno;
	private EnumConst enumConstByFlagCanSuggest;
	private EnumConst enumConstByFlagIsvalid;
	private EnumConst enumConstByFlagLimitDiffip;
	private EnumConst enumConstByFlagLimitDiffsession;
	private String title;
	private String pictitle;
	private Date foundDate;
	private Date startDate;
	private Date endDate;
	private String keywords;
	private String note;
	private Set prVoteRecords = new HashSet(0);
	private Set prVoteQuestions = new HashSet(0);
	private Set prVoteSuggests = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeVotePaper() {
	}

	/** minimal constructor */
	public PeVotePaper(String title) {
		this.title = title;
	}

	/** full constructor */
	public PeVotePaper(EnumConst enumConstByFlagViewSuggest,
			PeProApplyno peProApplyno, EnumConst enumConstByFlagCanSuggest,
			EnumConst enumConstByFlagIsvalid,
			EnumConst enumConstByFlagLimitDiffip,
			EnumConst enumConstByFlagLimitDiffsession, String title,
			String pictitle, Date foundDate, Date startDate, Date endDate,
			String keywords, String note, Set prVoteRecords,
			Set prVoteQuestions, Set prVoteSuggests) {
		this.enumConstByFlagViewSuggest = enumConstByFlagViewSuggest;
		this.peProApplyno = peProApplyno;
		this.enumConstByFlagCanSuggest = enumConstByFlagCanSuggest;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.enumConstByFlagLimitDiffip = enumConstByFlagLimitDiffip;
		this.enumConstByFlagLimitDiffsession = enumConstByFlagLimitDiffsession;
		this.title = title;
		this.pictitle = pictitle;
		this.foundDate = foundDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.keywords = keywords;
		this.note = note;
		this.prVoteRecords = prVoteRecords;
		this.prVoteQuestions = prVoteQuestions;
		this.prVoteSuggests = prVoteSuggests;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagViewSuggest() {
		return this.enumConstByFlagViewSuggest;
	}

	public void setEnumConstByFlagViewSuggest(
			EnumConst enumConstByFlagViewSuggest) {
		this.enumConstByFlagViewSuggest = enumConstByFlagViewSuggest;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public EnumConst getEnumConstByFlagCanSuggest() {
		return this.enumConstByFlagCanSuggest;
	}

	public void setEnumConstByFlagCanSuggest(EnumConst enumConstByFlagCanSuggest) {
		this.enumConstByFlagCanSuggest = enumConstByFlagCanSuggest;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public EnumConst getEnumConstByFlagLimitDiffip() {
		return this.enumConstByFlagLimitDiffip;
	}

	public void setEnumConstByFlagLimitDiffip(
			EnumConst enumConstByFlagLimitDiffip) {
		this.enumConstByFlagLimitDiffip = enumConstByFlagLimitDiffip;
	}

	public EnumConst getEnumConstByFlagLimitDiffsession() {
		return this.enumConstByFlagLimitDiffsession;
	}

	public void setEnumConstByFlagLimitDiffsession(
			EnumConst enumConstByFlagLimitDiffsession) {
		this.enumConstByFlagLimitDiffsession = enumConstByFlagLimitDiffsession;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictitle() {
		return this.pictitle;
	}

	public void setPictitle(String pictitle) {
		this.pictitle = pictitle;
	}

	public Date getFoundDate() {
		return this.foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrVoteRecords() {
		return this.prVoteRecords;
	}

	public void setPrVoteRecords(Set prVoteRecords) {
		this.prVoteRecords = prVoteRecords;
	}

	public Set getPrVoteQuestions() {
		return this.prVoteQuestions;
	}

	public void setPrVoteQuestions(Set prVoteQuestions) {
		this.prVoteQuestions = prVoteQuestions;
	}

	public Set getPrVoteSuggests() {
		return this.prVoteSuggests;
	}

	public void setPrVoteSuggests(Set prVoteSuggests) {
		this.prVoteSuggests = prVoteSuggests;
	}

}