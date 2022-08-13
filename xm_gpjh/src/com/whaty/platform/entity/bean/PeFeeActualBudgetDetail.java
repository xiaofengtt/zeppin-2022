package com.whaty.platform.entity.bean;

/**
 * PeFeeActualBudgetDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeFeeActualBudgetDetail extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private Double feeSurvey;
	private Double feeResearch;
	private Double feeArgument;
	private Double feeMeal;
	private Double feeAccommodation;
	private Double feeTrafficStu;
	private Double feeTeach;
	private Double feeTrafficExpert;
	private Double feeMealAccExpert;
	private Double feeMaterials;
	private Double feeCourse;
	private Double feeElectronCourse;
	private Double feeAreaRent;
	private Double feeEquipmentRent;
	private Double feeAppraise;
	private Double feeSummaryAppraise;
	private Double feeLabourService;
	private Double feePublicity;
	private Double feePetty;
	private Double feeOther;
	private String noteSurvey;
	private String noteResearch;
	private String noteArgument;
	private String noteMeal;
	private String noteAccommodation;
	private String noteTrafficStu;
	private String noteTeach;
	private String noteTrafficExpert;
	private String noteMealAccExpert;
	private String noteMaterials;
	private String noteTextCourse;
	private String noteElectronCourse;
	private String noteAreaRent;
	private String noteEquipmentRent;
	private String noteAppraise;
	private String noteSummaryAppraise;
	private String noteLabourService;
	private String notePublicity;
	private String notePetty;
	private String noteOther;

	// Constructors

	/** default constructor */
	public PeFeeActualBudgetDetail() {
	}

	/** full constructor */
	public PeFeeActualBudgetDetail(Double feeSurvey, Double feeResearch,
			Double feeArgument, Double feeMeal, Double feeAccommodation,
			Double feeTrafficStu, Double feeTeach, Double feeTrafficExpert,
			Double feeMealAccExpert, Double feeMaterials, Double feeCourse,
			Double feeElectronCourse, Double feeAreaRent,
			Double feeEquipmentRent, Double feeAppraise,
			Double feeSummaryAppraise, Double feeLabourService,
			Double feePublicity, Double feePetty, Double feeOther,
			String noteSurvey, String noteResearch, String noteArgument,
			String noteMeal, String noteAccommodation, String noteTrafficStu,
			String noteTeach, String noteTrafficExpert,
			String noteMealAccExpert, String noteMaterials,
			String noteTextCourse, String noteElectronCourse,
			String noteAreaRent, String noteEquipmentRent, String noteAppraise,
			String noteSummaryAppraise, String noteLabourService,
			String notePublicity, String notePetty, String noteOther) {
		this.feeSurvey = feeSurvey;
		this.feeResearch = feeResearch;
		this.feeArgument = feeArgument;
		this.feeMeal = feeMeal;
		this.feeAccommodation = feeAccommodation;
		this.feeTrafficStu = feeTrafficStu;
		this.feeTeach = feeTeach;
		this.feeTrafficExpert = feeTrafficExpert;
		this.feeMealAccExpert = feeMealAccExpert;
		this.feeMaterials = feeMaterials;
		this.feeCourse = feeCourse;
		this.feeElectronCourse = feeElectronCourse;
		this.feeAreaRent = feeAreaRent;
		this.feeEquipmentRent = feeEquipmentRent;
		this.feeAppraise = feeAppraise;
		this.feeSummaryAppraise = feeSummaryAppraise;
		this.feeLabourService = feeLabourService;
		this.feePublicity = feePublicity;
		this.feePetty = feePetty;
		this.feeOther = feeOther;
		this.noteSurvey = noteSurvey;
		this.noteResearch = noteResearch;
		this.noteArgument = noteArgument;
		this.noteMeal = noteMeal;
		this.noteAccommodation = noteAccommodation;
		this.noteTrafficStu = noteTrafficStu;
		this.noteTeach = noteTeach;
		this.noteTrafficExpert = noteTrafficExpert;
		this.noteMealAccExpert = noteMealAccExpert;
		this.noteMaterials = noteMaterials;
		this.noteTextCourse = noteTextCourse;
		this.noteElectronCourse = noteElectronCourse;
		this.noteAreaRent = noteAreaRent;
		this.noteEquipmentRent = noteEquipmentRent;
		this.noteAppraise = noteAppraise;
		this.noteSummaryAppraise = noteSummaryAppraise;
		this.noteLabourService = noteLabourService;
		this.notePublicity = notePublicity;
		this.notePetty = notePetty;
		this.noteOther = noteOther;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getFeeSurvey() {
		return this.feeSurvey;
	}

	public void setFeeSurvey(Double feeSurvey) {
		this.feeSurvey = feeSurvey;
	}

	public Double getFeeResearch() {
		return this.feeResearch;
	}

	public void setFeeResearch(Double feeResearch) {
		this.feeResearch = feeResearch;
	}

	public Double getFeeArgument() {
		return this.feeArgument;
	}

	public void setFeeArgument(Double feeArgument) {
		this.feeArgument = feeArgument;
	}

	public Double getFeeMeal() {
		return this.feeMeal;
	}

	public void setFeeMeal(Double feeMeal) {
		this.feeMeal = feeMeal;
	}

	public Double getFeeAccommodation() {
		return this.feeAccommodation;
	}

	public void setFeeAccommodation(Double feeAccommodation) {
		this.feeAccommodation = feeAccommodation;
	}

	public Double getFeeTrafficStu() {
		return this.feeTrafficStu;
	}

	public void setFeeTrafficStu(Double feeTrafficStu) {
		this.feeTrafficStu = feeTrafficStu;
	}

	public Double getFeeTeach() {
		return this.feeTeach;
	}

	public void setFeeTeach(Double feeTeach) {
		this.feeTeach = feeTeach;
	}

	public Double getFeeTrafficExpert() {
		return this.feeTrafficExpert;
	}

	public void setFeeTrafficExpert(Double feeTrafficExpert) {
		this.feeTrafficExpert = feeTrafficExpert;
	}

	public Double getFeeMealAccExpert() {
		return this.feeMealAccExpert;
	}

	public void setFeeMealAccExpert(Double feeMealAccExpert) {
		this.feeMealAccExpert = feeMealAccExpert;
	}

	public Double getFeeMaterials() {
		return this.feeMaterials;
	}

	public void setFeeMaterials(Double feeMaterials) {
		this.feeMaterials = feeMaterials;
	}

	public Double getFeeCourse() {
		return this.feeCourse;
	}

	public void setFeeCourse(Double feeCourse) {
		this.feeCourse = feeCourse;
	}

	public Double getFeeElectronCourse() {
		return this.feeElectronCourse;
	}

	public void setFeeElectronCourse(Double feeElectronCourse) {
		this.feeElectronCourse = feeElectronCourse;
	}

	public Double getFeeAreaRent() {
		return this.feeAreaRent;
	}

	public void setFeeAreaRent(Double feeAreaRent) {
		this.feeAreaRent = feeAreaRent;
	}

	public Double getFeeEquipmentRent() {
		return this.feeEquipmentRent;
	}

	public void setFeeEquipmentRent(Double feeEquipmentRent) {
		this.feeEquipmentRent = feeEquipmentRent;
	}

	public Double getFeeAppraise() {
		return this.feeAppraise;
	}

	public void setFeeAppraise(Double feeAppraise) {
		this.feeAppraise = feeAppraise;
	}

	public Double getFeeSummaryAppraise() {
		return this.feeSummaryAppraise;
	}

	public void setFeeSummaryAppraise(Double feeSummaryAppraise) {
		this.feeSummaryAppraise = feeSummaryAppraise;
	}

	public Double getFeeLabourService() {
		return this.feeLabourService;
	}

	public void setFeeLabourService(Double feeLabourService) {
		this.feeLabourService = feeLabourService;
	}

	public Double getFeePublicity() {
		return this.feePublicity;
	}

	public void setFeePublicity(Double feePublicity) {
		this.feePublicity = feePublicity;
	}

	public Double getFeePetty() {
		return this.feePetty;
	}

	public void setFeePetty(Double feePetty) {
		this.feePetty = feePetty;
	}

	public Double getFeeOther() {
		return this.feeOther;
	}

	public void setFeeOther(Double feeOther) {
		this.feeOther = feeOther;
	}

	public String getNoteSurvey() {
		return this.noteSurvey;
	}

	public void setNoteSurvey(String noteSurvey) {
		this.noteSurvey = noteSurvey;
	}

	public String getNoteResearch() {
		return this.noteResearch;
	}

	public void setNoteResearch(String noteResearch) {
		this.noteResearch = noteResearch;
	}

	public String getNoteArgument() {
		return this.noteArgument;
	}

	public void setNoteArgument(String noteArgument) {
		this.noteArgument = noteArgument;
	}

	public String getNoteMeal() {
		return this.noteMeal;
	}

	public void setNoteMeal(String noteMeal) {
		this.noteMeal = noteMeal;
	}

	public String getNoteAccommodation() {
		return this.noteAccommodation;
	}

	public void setNoteAccommodation(String noteAccommodation) {
		this.noteAccommodation = noteAccommodation;
	}

	public String getNoteTrafficStu() {
		return this.noteTrafficStu;
	}

	public void setNoteTrafficStu(String noteTrafficStu) {
		this.noteTrafficStu = noteTrafficStu;
	}

	public String getNoteTeach() {
		return this.noteTeach;
	}

	public void setNoteTeach(String noteTeach) {
		this.noteTeach = noteTeach;
	}

	public String getNoteTrafficExpert() {
		return this.noteTrafficExpert;
	}

	public void setNoteTrafficExpert(String noteTrafficExpert) {
		this.noteTrafficExpert = noteTrafficExpert;
	}

	public String getNoteMealAccExpert() {
		return this.noteMealAccExpert;
	}

	public void setNoteMealAccExpert(String noteMealAccExpert) {
		this.noteMealAccExpert = noteMealAccExpert;
	}

	public String getNoteMaterials() {
		return this.noteMaterials;
	}

	public void setNoteMaterials(String noteMaterials) {
		this.noteMaterials = noteMaterials;
	}

	public String getNoteTextCourse() {
		return this.noteTextCourse;
	}

	public void setNoteTextCourse(String noteTextCourse) {
		this.noteTextCourse = noteTextCourse;
	}

	public String getNoteElectronCourse() {
		return this.noteElectronCourse;
	}

	public void setNoteElectronCourse(String noteElectronCourse) {
		this.noteElectronCourse = noteElectronCourse;
	}

	public String getNoteAreaRent() {
		return this.noteAreaRent;
	}

	public void setNoteAreaRent(String noteAreaRent) {
		this.noteAreaRent = noteAreaRent;
	}

	public String getNoteEquipmentRent() {
		return this.noteEquipmentRent;
	}

	public void setNoteEquipmentRent(String noteEquipmentRent) {
		this.noteEquipmentRent = noteEquipmentRent;
	}

	public String getNoteAppraise() {
		return this.noteAppraise;
	}

	public void setNoteAppraise(String noteAppraise) {
		this.noteAppraise = noteAppraise;
	}

	public String getNoteSummaryAppraise() {
		return this.noteSummaryAppraise;
	}

	public void setNoteSummaryAppraise(String noteSummaryAppraise) {
		this.noteSummaryAppraise = noteSummaryAppraise;
	}

	public String getNoteLabourService() {
		return this.noteLabourService;
	}

	public void setNoteLabourService(String noteLabourService) {
		this.noteLabourService = noteLabourService;
	}

	public String getNotePublicity() {
		return this.notePublicity;
	}

	public void setNotePublicity(String notePublicity) {
		this.notePublicity = notePublicity;
	}

	public String getNotePetty() {
		return this.notePetty;
	}

	public void setNotePetty(String notePetty) {
		this.notePetty = notePetty;
	}

	public String getNoteOther() {
		return this.noteOther;
	}

	public void setNoteOther(String noteOther) {
		this.noteOther = noteOther;
	}

}