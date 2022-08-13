package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeCoursePlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeCoursePlan extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeTrainExpert peTrainExpert;
	private EnumConst enumConstByFlagBak;
	private EnumConst enumConstByFlagValuation;
	private PeProApply peProApply;
	private PeProvince peProvince;
	private Date trainingBeginTime;
	private String prelectWay;
	private String expertName;
	private String workPlace;
	private String zhicheng;
	private String note;
	private String comments;
	private String theme;
	private String trainPlace;
	private Date trainingEndTime;
	private Long firstvote;
	private Long secondvote;
	private Long thirdvote;
	private Long fouthvote;
	private Long fifthvote;

	// Constructors

	/** default constructor */
	public PeCoursePlan() {
	}

	/** full constructor */
	public PeCoursePlan(PeTrainExpert peTrainExpert,
			EnumConst enumConstByFlagBak, EnumConst enumConstByFlagValuation,
			PeProApply peProApply, PeProvince peProvince,
			Date trainingBeginTime, String prelectWay, String expertName,
			String workPlace, String zhicheng, String note, String comments,
			String theme, String trainPlace, Date trainingEndTime,
			Long firstvote, Long secondvote, Long thirdvote, Long fouthvote,
			Long fifthvote) {
		this.peTrainExpert = peTrainExpert;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagValuation = enumConstByFlagValuation;
		this.peProApply = peProApply;
		this.peProvince = peProvince;
		this.trainingBeginTime = trainingBeginTime;
		this.prelectWay = prelectWay;
		this.expertName = expertName;
		this.workPlace = workPlace;
		this.zhicheng = zhicheng;
		this.note = note;
		this.comments = comments;
		this.theme = theme;
		this.trainPlace = trainPlace;
		this.trainingEndTime = trainingEndTime;
		this.firstvote = firstvote;
		this.secondvote = secondvote;
		this.thirdvote = thirdvote;
		this.fouthvote = fouthvote;
		this.fifthvote = fifthvote;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTrainExpert getPeTrainExpert() {
		return this.peTrainExpert;
	}

	public void setPeTrainExpert(PeTrainExpert peTrainExpert) {
		this.peTrainExpert = peTrainExpert;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagValuation() {
		return this.enumConstByFlagValuation;
	}

	public void setEnumConstByFlagValuation(EnumConst enumConstByFlagValuation) {
		this.enumConstByFlagValuation = enumConstByFlagValuation;
	}

	public PeProApply getPeProApply() {
		return this.peProApply;
	}

	public void setPeProApply(PeProApply peProApply) {
		this.peProApply = peProApply;
	}

	public PeProvince getPeProvince() {
		return this.peProvince;
	}

	public void setPeProvince(PeProvince peProvince) {
		this.peProvince = peProvince;
	}

	public Date getTrainingBeginTime() {
		return this.trainingBeginTime;
	}

	public void setTrainingBeginTime(Date trainingBeginTime) {
		this.trainingBeginTime = trainingBeginTime;
	}

	public String getPrelectWay() {
		return this.prelectWay;
	}

	public void setPrelectWay(String prelectWay) {
		this.prelectWay = prelectWay;
	}

	public String getExpertName() {
		return this.expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getWorkPlace() {
		return this.workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getZhicheng() {
		return this.zhicheng;
	}

	public void setZhicheng(String zhicheng) {
		this.zhicheng = zhicheng;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTrainPlace() {
		return this.trainPlace;
	}

	public void setTrainPlace(String trainPlace) {
		this.trainPlace = trainPlace;
	}

	public Date getTrainingEndTime() {
		return this.trainingEndTime;
	}

	public void setTrainingEndTime(Date trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public Long getFirstvote() {
		return this.firstvote;
	}

	public void setFirstvote(Long firstvote) {
		this.firstvote = firstvote;
	}

	public Long getSecondvote() {
		return this.secondvote;
	}

	public void setSecondvote(Long secondvote) {
		this.secondvote = secondvote;
	}

	public Long getThirdvote() {
		return this.thirdvote;
	}

	public void setThirdvote(Long thirdvote) {
		this.thirdvote = thirdvote;
	}

	public Long getFouthvote() {
		return this.fouthvote;
	}

	public void setFouthvote(Long fouthvote) {
		this.fouthvote = fouthvote;
	}

	public Long getFifthvote() {
		return this.fifthvote;
	}

	public void setFifthvote(Long fifthvote) {
		this.fifthvote = fifthvote;
	}

}