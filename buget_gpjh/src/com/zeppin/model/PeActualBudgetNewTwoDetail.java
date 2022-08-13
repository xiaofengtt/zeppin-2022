/**  
 * @Title: PeBudgetmpDetail.java
 * @Package com.zeppin.model
 * @author jiangfei  
 */
package com.zeppin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "PE_FEE_ACTUAL_BUDGET_TWODETAIL")
@Repository("peActualBudgetNewTwoDetail")
public class PeActualBudgetNewTwoDetail {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "fee_survey")
	private Double feeSurvey;

	@Column(name = "fee_research")
	private Double feeResearch;

	@Column(name = "fee_argument")
	private Double feeArgument;

	@Column(name = "fee_meal")
	private Double feeMeal;
	
	@Column(name = "fee_meal_expert")
	private Double feeMealExpert;

	@Column(name = "fee_accommodation")
	private Double feeAccommodation;

	@Column(name = "fee_traffic_stu")
	private Double feeTrafficStu;

	@Column(name = "fee_teach")
	private Double feeTeach;

	@Column(name = "fee_traffic_expert")
	private Double feeTrafficExpert;

	@Column(name = "fee_meal_acc_expert")
	private Double feeMealAccExpert;

	@Column(name = "fee_materials")
	private Double feeMaterials;

	@Column(name = "fee_office_supplies")
	private Double feeOfficeSupplies;
	
	@Column(name = "fee_course")
	private Double feeCourse;

	@Column(name = "fee_electron_course")
	private Double feeElectronCourse;

	@Column(name = "fee_area_rent")
	private Double feeAreaRent;

	@Column(name = "fee_equipment_rent")
	private Double feeEquipmentRent;

	@Column(name = "fee_appraise")
	private Double feeAppraise;

	@Column(name = "fee_summary_appraise")
	private Double feeSummaryAppraise;

	@Column(name = "fee_labour_service")
	private Double feeLabourService;

	@Column(name = "fee_publicity")
	private Double feePublicity;

	@Column(name = "fee_petty")
	private Double feePetty;

	@Column(name = "FEE_OTHER")
	private Double feeOther;

	@Column(name = "note_survey")
	private String noteSurvey;

	@Column(name = "note_research")
	private String noteResearch;

	@Column(name = "note_argument")
	private String noteArgument;

	@Column(name = "note_meal")
	private String noteMeal;

	@Column(name = "note_meal_expert")
	private String noteMealExpert;
	
	@Column(name = "note_accommodation")
	private String noteAccommodation;

	@Column(name = "note_traffic_stu")
	private String noteTrafficStu;

	@Column(name = "note_teach")
	private String noteTeach;

	@Column(name = "note_traffic_expert")
	private String noteTrafficExpert;

	@Column(name = "note_meal_acc_expert")
	private String noteMealAccExpert;

	@Column(name = "note_materials")
	private String noteMaterials;
	
	@Column(name = "note_office_supplies")
	private String noteOfficeSupplies;

	@Column(name = "note_text_course")
	private String noteTextCourse;

	@Column(name = "note_electron_course")
	private String noteElectronCourse;

	@Column(name = "note_area_rent")
	private String noteAreaRent;

	@Column(name = "NOTE_EQUIPMENT_RENT")
	private String noteEquipmentRent;

	@Column(name = "note_appraise")
	private String noteAppraise;

	@Column(name = "note_summary_appraise")
	private String noteSummaryAppraise;

	@Column(name = "note_labour_service")
	private String noteLabourService;

	@Column(name = "note_publicity")
	private String notePublicity;

	@Column(name = "note_petty")
	private String notePetty;

	@Column(name = "NOTE_OTHER")
	private String noteOther;
	
	@Column(name = "fee_staff_accommodation")
	private Double feeStaffAccommodation;
	
	@Column(name = "note_staff_accommodation")
	private String noteStaffAccommodation;
	
	@Column(name = "fee_meal_staff")
	private Double feeMealStaff;
	
	@Column(name = "note_meal_staff")
	private String noteMealStaff;
	
	@Column(name = "fee_others")
	private Double feeOthers;
	
	@Column(name = "note_others")
	private String noteOthers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getFeeSurvey() {
		return feeSurvey;
	}

	public void setFeeSurvey(Double feeSurvey) {
		this.feeSurvey = feeSurvey;
	}

	public Double getFeeResearch() {
		return feeResearch;
	}

	public void setFeeResearch(Double feeResearch) {
		this.feeResearch = feeResearch;
	}

	public Double getFeeArgument() {
		return feeArgument;
	}

	public void setFeeArgument(Double feeArgument) {
		this.feeArgument = feeArgument;
	}

	public Double getFeeMeal() {
		return feeMeal;
	}

	public void setFeeMeal(Double feeMeal) {
		this.feeMeal = feeMeal;
	}

	public Double getFeeAccommodation() {
		return feeAccommodation;
	}

	public void setFeeAccommodation(Double feeAccommodation) {
		this.feeAccommodation = feeAccommodation;
	}

	public Double getFeeTrafficStu() {
		return feeTrafficStu;
	}

	public void setFeeTrafficStu(Double feeTrafficStu) {
		this.feeTrafficStu = feeTrafficStu;
	}

	public Double getFeeTeach() {
		return feeTeach;
	}

	public void setFeeTeach(Double feeTeach) {
		this.feeTeach = feeTeach;
	}

	public Double getFeeTrafficExpert() {
		return feeTrafficExpert;
	}

	public void setFeeTrafficExpert(Double feeTrafficExpert) {
		this.feeTrafficExpert = feeTrafficExpert;
	}

	public Double getFeeMealAccExpert() {
		return feeMealAccExpert;
	}

	public void setFeeMealAccExpert(Double feeMealAccExpert) {
		this.feeMealAccExpert = feeMealAccExpert;
	}

	public Double getFeeMaterials() {
		return feeMaterials;
	}

	public void setFeeMaterials(Double feeMaterials) {
		this.feeMaterials = feeMaterials;
	}

	public Double getFeeCourse() {
		return feeCourse;
	}

	public void setFeeCourse(Double feeCourse) {
		this.feeCourse = feeCourse;
	}

	public Double getFeeElectronCourse() {
		return feeElectronCourse;
	}

	public void setFeeElectronCourse(Double feeElectronCourse) {
		this.feeElectronCourse = feeElectronCourse;
	}

	public Double getFeeAreaRent() {
		return feeAreaRent;
	}

	public void setFeeAreaRent(Double feeAreaRent) {
		this.feeAreaRent = feeAreaRent;
	}

	public Double getFeeEquipmentRent() {
		return feeEquipmentRent;
	}

	public void setFeeEquipmentRent(Double feeEquipmentRent) {
		this.feeEquipmentRent = feeEquipmentRent;
	}

	public Double getFeeAppraise() {
		return feeAppraise;
	}

	public void setFeeAppraise(Double feeAppraise) {
		this.feeAppraise = feeAppraise;
	}

	public Double getFeeSummaryAppraise() {
		return feeSummaryAppraise;
	}

	public void setFeeSummaryAppraise(Double feeSummaryAppraise) {
		this.feeSummaryAppraise = feeSummaryAppraise;
	}

	public Double getFeeLabourService() {
		return feeLabourService;
	}

	public void setFeeLabourService(Double feeLabourService) {
		this.feeLabourService = feeLabourService;
	}

	public Double getFeePublicity() {
		return feePublicity;
	}

	public void setFeePublicity(Double feePublicity) {
		this.feePublicity = feePublicity;
	}

	public Double getFeePetty() {
		return feePetty;
	}

	public void setFeePetty(Double feePetty) {
		this.feePetty = feePetty;
	}

	public Double getFeeOther() {
		return feeOther;
	}

	public void setFeeOther(Double feeOther) {
		this.feeOther = feeOther;
	}

	public String getNoteSurvey() {
		return noteSurvey;
	}

	public void setNoteSurvey(String noteSurvey) {
		this.noteSurvey = noteSurvey;
	}

	public String getNoteResearch() {
		return noteResearch;
	}

	public void setNoteResearch(String noteResearch) {
		this.noteResearch = noteResearch;
	}

	public String getNoteArgument() {
		return noteArgument;
	}

	public void setNoteArgument(String noteArgument) {
		this.noteArgument = noteArgument;
	}

	public String getNoteMeal() {
		return noteMeal;
	}

	public void setNoteMeal(String noteMeal) {
		this.noteMeal = noteMeal;
	}

	public String getNoteAccommodation() {
		return noteAccommodation;
	}

	public void setNoteAccommodation(String noteAccommodation) {
		this.noteAccommodation = noteAccommodation;
	}

	public String getNoteTrafficStu() {
		return noteTrafficStu;
	}

	public void setNoteTrafficStu(String noteTrafficStu) {
		this.noteTrafficStu = noteTrafficStu;
	}

	public String getNoteTeach() {
		return noteTeach;
	}

	public void setNoteTeach(String noteTeach) {
		this.noteTeach = noteTeach;
	}

	public String getNoteTrafficExpert() {
		return noteTrafficExpert;
	}

	public void setNoteTrafficExpert(String noteTrafficExpert) {
		this.noteTrafficExpert = noteTrafficExpert;
	}

	public String getNoteMealAccExpert() {
		return noteMealAccExpert;
	}

	public void setNoteMealAccExpert(String noteMealAccExpert) {
		this.noteMealAccExpert = noteMealAccExpert;
	}

	public String getNoteMaterials() {
		return noteMaterials;
	}

	public void setNoteMaterials(String noteMaterials) {
		this.noteMaterials = noteMaterials;
	}

	public String getNoteTextCourse() {
		return noteTextCourse;
	}

	public void setNoteTextCourse(String noteTextCourse) {
		this.noteTextCourse = noteTextCourse;
	}

	public String getNoteElectronCourse() {
		return noteElectronCourse;
	}

	public void setNoteElectronCourse(String noteElectronCourse) {
		this.noteElectronCourse = noteElectronCourse;
	}

	public String getNoteAreaRent() {
		return noteAreaRent;
	}

	public void setNoteAreaRent(String noteAreaRent) {
		this.noteAreaRent = noteAreaRent;
	}

	public String getNoteEquipmentRent() {
		return noteEquipmentRent;
	}

	public void setNoteEquipmentRent(String noteEquipmentRent) {
		this.noteEquipmentRent = noteEquipmentRent;
	}

	public String getNoteAppraise() {
		return noteAppraise;
	}

	public void setNoteAppraise(String noteAppraise) {
		this.noteAppraise = noteAppraise;
	}

	public String getNoteSummaryAppraise() {
		return noteSummaryAppraise;
	}

	public void setNoteSummaryAppraise(String noteSummaryAppraise) {
		this.noteSummaryAppraise = noteSummaryAppraise;
	}

	public String getNoteLabourService() {
		return noteLabourService;
	}

	public void setNoteLabourService(String noteLabourService) {
		this.noteLabourService = noteLabourService;
	}

	public String getNotePublicity() {
		return notePublicity;
	}

	public void setNotePublicity(String notePublicity) {
		this.notePublicity = notePublicity;
	}

	public String getNotePetty() {
		return notePetty;
	}

	public void setNotePetty(String notePetty) {
		this.notePetty = notePetty;
	}

	public String getNoteOther() {
		return noteOther;
	}

	public void setNoteOther(String noteOther) {
		this.noteOther = noteOther;
	}

	public Double getFeeMealExpert() {
		return feeMealExpert;
	}

	public void setFeeMealExpert(Double feeMealExpert) {
		this.feeMealExpert = feeMealExpert;
	}

	public Double getFeeOfficeSupplies() {
		return feeOfficeSupplies;
	}

	public void setFeeOfficeSupplies(Double feeOfficeSupplies) {
		this.feeOfficeSupplies = feeOfficeSupplies;
	}

	public String getNoteMealExpert() {
		return noteMealExpert;
	}

	public void setNoteMealExpert(String noteMealExpert) {
		this.noteMealExpert = noteMealExpert;
	}

	public String getNoteOfficeSupplies() {
		return noteOfficeSupplies;
	}

	public void setNoteOfficeSupplies(String noteOfficeSupplies) {
		this.noteOfficeSupplies = noteOfficeSupplies;
	}

	public Double getFeeStaffAccommodation() {
		return feeStaffAccommodation;
	}

	public void setFeeStaffAccommodation(Double feeStaffAccommodation) {
		this.feeStaffAccommodation = feeStaffAccommodation;
	}

	public String getNoteStaffAccommodation() {
		return noteStaffAccommodation;
	}

	public void setNoteStaffAccommodation(String noteStaffAccommodation) {
		this.noteStaffAccommodation = noteStaffAccommodation;
	}

	public Double getFeeMealStaff() {
		return feeMealStaff;
	}

	public void setFeeMealStaff(Double feeMealStaff) {
		this.feeMealStaff = feeMealStaff;
	}

	public String getNoteMealStaff() {
		return noteMealStaff;
	}

	public void setNoteMealStaff(String noteMealStaff) {
		this.noteMealStaff = noteMealStaff;
	}

	public Double getFeeOthers() {
		return feeOthers;
	}

	public void setFeeOthers(Double feeOthers) {
		this.feeOthers = feeOthers;
	}

	public String getNoteOthers() {
		return noteOthers;
	}

	public void setNoteOthers(String noteOthers) {
		this.noteOthers = noteOthers;
	}

}
