package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Major;

public abstract class RecruitMatricaluteCondition implements Items {

	public RecruitMatricaluteCondition() {
	}

	private String id = "";

	private RecruitBatch batch = null;

	private Major major = null;

	private EduType eduType = null;

	private String score = "";

	private String score1 = "";

	private String photo_status = "";

	private String idcard_status = "";

	private String graduatecard_status = "";

	private List courseList = null;

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}

	public RecruitBatch getBatch() {
		return batch;
	}

	public void setBatch(RecruitBatch batch) {
		this.batch = batch;
	}

	public String getScore1() {
		return score1;
	}

	public void setScore1(String score1) {
		this.score1 = score1;
	}

	public String getGraduatecard_status() {
		return graduatecard_status;
	}

	public void setGraduatecard_status(String graduatecard_status) {
		this.graduatecard_status = graduatecard_status;
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

}
