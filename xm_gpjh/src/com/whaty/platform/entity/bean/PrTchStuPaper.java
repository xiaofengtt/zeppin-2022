package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PrTchStuPaper entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchStuPaper extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PeTchRejoinRoom peTchRejoinRoom;
	private PrTchPaperTitle prTchPaperTitle;
	private PeTchRejoinSection peTchRejoinSection;
	private String flagTitleAdmission;
	private String flagSyllabusLastUpdate;
	private String flagDraftALastUpdate;
	private String flagFinalLastUpdate;
	private String flagPaperRejoin;
	private Date titleDate;
	private Double finalScore;
	private Date finalScoreDate;
	private Double rejoinScore;
	private Double scoreTotal;
	private Set prTchPaperContents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchStuPaper() {
	}

	/** full constructor */
	public PrTchStuPaper(PeStudent peStudent, PeTchRejoinRoom peTchRejoinRoom,
			PrTchPaperTitle prTchPaperTitle,
			PeTchRejoinSection peTchRejoinSection, String flagTitleAdmission,
			String flagSyllabusLastUpdate, String flagDraftALastUpdate,
			String flagFinalLastUpdate, String flagPaperRejoin, Date titleDate,
			Double finalScore, Date finalScoreDate, Double rejoinScore,
			Double scoreTotal, Set prTchPaperContents) {
		this.peStudent = peStudent;
		this.peTchRejoinRoom = peTchRejoinRoom;
		this.prTchPaperTitle = prTchPaperTitle;
		this.peTchRejoinSection = peTchRejoinSection;
		this.flagTitleAdmission = flagTitleAdmission;
		this.flagSyllabusLastUpdate = flagSyllabusLastUpdate;
		this.flagDraftALastUpdate = flagDraftALastUpdate;
		this.flagFinalLastUpdate = flagFinalLastUpdate;
		this.flagPaperRejoin = flagPaperRejoin;
		this.titleDate = titleDate;
		this.finalScore = finalScore;
		this.finalScoreDate = finalScoreDate;
		this.rejoinScore = rejoinScore;
		this.scoreTotal = scoreTotal;
		this.prTchPaperContents = prTchPaperContents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PeTchRejoinRoom getPeTchRejoinRoom() {
		return this.peTchRejoinRoom;
	}

	public void setPeTchRejoinRoom(PeTchRejoinRoom peTchRejoinRoom) {
		this.peTchRejoinRoom = peTchRejoinRoom;
	}

	public PrTchPaperTitle getPrTchPaperTitle() {
		return this.prTchPaperTitle;
	}

	public void setPrTchPaperTitle(PrTchPaperTitle prTchPaperTitle) {
		this.prTchPaperTitle = prTchPaperTitle;
	}

	public PeTchRejoinSection getPeTchRejoinSection() {
		return this.peTchRejoinSection;
	}

	public void setPeTchRejoinSection(PeTchRejoinSection peTchRejoinSection) {
		this.peTchRejoinSection = peTchRejoinSection;
	}

	public String getFlagTitleAdmission() {
		return this.flagTitleAdmission;
	}

	public void setFlagTitleAdmission(String flagTitleAdmission) {
		this.flagTitleAdmission = flagTitleAdmission;
	}

	public String getFlagSyllabusLastUpdate() {
		return this.flagSyllabusLastUpdate;
	}

	public void setFlagSyllabusLastUpdate(String flagSyllabusLastUpdate) {
		this.flagSyllabusLastUpdate = flagSyllabusLastUpdate;
	}

	public String getFlagDraftALastUpdate() {
		return this.flagDraftALastUpdate;
	}

	public void setFlagDraftALastUpdate(String flagDraftALastUpdate) {
		this.flagDraftALastUpdate = flagDraftALastUpdate;
	}

	public String getFlagFinalLastUpdate() {
		return this.flagFinalLastUpdate;
	}

	public void setFlagFinalLastUpdate(String flagFinalLastUpdate) {
		this.flagFinalLastUpdate = flagFinalLastUpdate;
	}

	public String getFlagPaperRejoin() {
		return this.flagPaperRejoin;
	}

	public void setFlagPaperRejoin(String flagPaperRejoin) {
		this.flagPaperRejoin = flagPaperRejoin;
	}

	public Date getTitleDate() {
		return this.titleDate;
	}

	public void setTitleDate(Date titleDate) {
		this.titleDate = titleDate;
	}

	public Double getFinalScore() {
		return this.finalScore;
	}

	public void setFinalScore(Double finalScore) {
		this.finalScore = finalScore;
	}

	public Date getFinalScoreDate() {
		return this.finalScoreDate;
	}

	public void setFinalScoreDate(Date finalScoreDate) {
		this.finalScoreDate = finalScoreDate;
	}

	public Double getRejoinScore() {
		return this.rejoinScore;
	}

	public void setRejoinScore(Double rejoinScore) {
		this.rejoinScore = rejoinScore;
	}

	public Double getScoreTotal() {
		return this.scoreTotal;
	}

	public void setScoreTotal(Double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public Set getPrTchPaperContents() {
		return this.prTchPaperContents;
	}

	public void setPrTchPaperContents(Set prTchPaperContents) {
		this.prTchPaperContents = prTchPaperContents;
	}

}