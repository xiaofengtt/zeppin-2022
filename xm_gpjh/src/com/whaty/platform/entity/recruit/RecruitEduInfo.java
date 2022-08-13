package com.whaty.platform.entity.recruit;

import com.whaty.platform.entity.user.StudentEduInfo;

public class RecruitEduInfo extends StudentEduInfo {
	
	private String considerType;

	private RecruitBatch batch;

	private String status = "0";

	private String passStatus = "0";

	private String considertype_status = "0";

	private String score_status = "0";

	private String considertype_note = "";

	private String photoLink = "";

	private RecruitSort sort;

	private String testCardId;

	private String notice_no;

	private String register_status = "0";

	private String photo_status = "0";

	private String idcard_link = "";

	private String idcard_status = "0";

	private String graduatecard_link = "";

	private String graduatecard_status = "0";

	private String school_name = "";

	private String school_code = "";

	private String graduate_year = "";

	private String graduate_cardno = "";

	public RecruitSort getSort() {
		return sort;
	}

	public void setSort(RecruitSort sort) {
		this.sort = sort;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public RecruitBatch getBatch() {
		return batch;
	}

	public void setBatch(RecruitBatch batch) {
		this.batch = batch;
	}

	public String getConsiderType() {
		return considerType;
	}

	public void setConsiderType(String considerType) {
		this.considerType = considerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestCardId() {
		return testCardId;
	}

	public void setTestCardId(String testCardId) {
		this.testCardId = testCardId;
	}

	public String getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}

	public String getConsidertype_status() {
		return considertype_status;
	}

	public void setConsidertype_status(String considertype_status) {
		this.considertype_status = considertype_status;
	}

	public String getConsidertype_note() {
		return considertype_note;
	}

	public void setConsidertype_note(String considertype_note) {
		this.considertype_note = considertype_note;
	}

	public String getScore_status() {
		return score_status;
	}

	public void setScore_status(String score_status) {
		this.score_status = score_status;
	}

	public String getNotice_no() {
		return notice_no;
	}

	public void setNotice_no(String notice_no) {
		this.notice_no = notice_no;
	}

	public String getRegister_status() {
		return register_status;
	}

	public void setRegister_status(String register_status) {
		this.register_status = register_status;
	}

	public String getGraduatecard_link() {
		return graduatecard_link;
	}

	public void setGraduatecard_link(String graduatecard_link) {
		this.graduatecard_link = graduatecard_link;
	}

	public String getGraduatecard_status() {
		return graduatecard_status;
	}

	public void setGraduatecard_status(String graduatecard_status) {
		this.graduatecard_status = graduatecard_status;
	}

	public String getIdcard_link() {
		return idcard_link;
	}

	public void setIdcard_link(String idcard_link) {
		this.idcard_link = idcard_link;
	}

	public String getIdcard_status() {
		return idcard_status;
	}

	public void setIdcard_status(String idcard_status) {
		this.idcard_status = idcard_status;
	}

	public String getPhoto_status() {
		return photo_status;
	}

	public void setPhoto_status(String photo_status) {
		this.photo_status = photo_status;
	}

	public String getGraduate_cardno() {
		return graduate_cardno;
	}

	public void setGraduate_cardno(String graduate_cardno) {
		this.graduate_cardno = graduate_cardno;
	}

	public String getGraduate_year() {
		return graduate_year;
	}

	public void setGraduate_year(String graduate_year) {
		this.graduate_year = graduate_year;
	}

	public String getSchool_code() {
		return school_code;
	}

	public void setSchool_code(String school_code) {
		this.school_code = school_code;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

}
