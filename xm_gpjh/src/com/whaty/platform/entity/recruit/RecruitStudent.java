package com.whaty.platform.entity.recruit;

import java.util.HashMap;

import com.whaty.platform.Items;
import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.HumanNormalInfo;

public abstract class RecruitStudent extends User implements Items {

	private HumanNormalInfo normalInfo = null;

	private RecruitEduInfo eduInfo;
	
	private String studyStatus;

	public RecruitEduInfo getEduInfo() {
		return eduInfo;
	}

	public void setEduInfo(RecruitEduInfo eduInfo) {
		this.eduInfo = eduInfo;
	}

	public HumanNormalInfo getNormalInfo() {
		return normalInfo;
	}

	public void setNormalInfo(HumanNormalInfo normalInfo) {
		this.normalInfo = normalInfo;
	}

	private String study_no;// 报名号

	private String premajor_status;// 若为专升本学生 '1'表示升本专业和专科专业相同

	private String reg_no;// 学号
	
	private String pubRegNo_status;

	/**
	 * 该报名学生是否有考试课程
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasTestCourses();

	public abstract int confirm() throws PlatformException;

	public abstract int unConfirm() throws PlatformException;

	public abstract int confirmFree() throws PlatformException;

	public abstract int unConfirmFree() throws PlatformException;

	public abstract RecruitStudent getRecruitInfo(String card_no,
			String password, String batchId) throws PlatformException;

	public abstract RecruitStudent getRecruitInfo(String cardNo)
			throws PlatformException;

	public abstract RecruitStudent getRecruitInfo1(String regNo)
			throws PlatformException;

	public abstract RecruitStudent getRecruitInfo2(String name, String cardNo,
			String batchId) throws PlatformException;

	public String getStudy_no() {
		return study_no;
	}

	public void setStudy_no(String study_no) {
		this.study_no = study_no;
	}

	public String getPremajor_status() {
		return premajor_status;
	}

	public void setPremajor_status(String premajor_status) {
		this.premajor_status = premajor_status;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public abstract int updateRegNo();
	
	public abstract int updatePubRegNoStatus();

	public abstract int updateRegNo2();

	public abstract int updateLink();

	public abstract String updateSiteMajor();

	public abstract int updateScore(HashMap scores);

	public abstract int updateStudentSite();

	public String getPubRegNo_status() {
		return pubRegNo_status;
	}

	public void setPubRegNo_status(String pubRegNo_status) {
		this.pubRegNo_status = pubRegNo_status;
	}

	public String getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(String studyStatus) {
		this.studyStatus = studyStatus;
	}

	 
}
