package com.whaty.platform.entity.recruit;

import java.util.List;
import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;

public abstract class RecruitTestStudent implements Items {
	private RecruitStudent student;

	private List testDeskList;

	private String testCardId;

	private List studentScoreList;

	private String appendScore;

	private String score;

	private List scoreList;

	private String status;

	public RecruitStudent getStudent() {
		return student;
	}

	public void setStudent(RecruitStudent student) {
		this.student = student;
	}

	public String getTestCardId() {
		return testCardId;
	}

	public void setTestCardId(String testCardId) {
		this.testCardId = testCardId;
	}

	public List getTestDeskList() {
		return testDeskList;
	}

	public void setTestDeskList(List testDeskList) {
		this.testDeskList = testDeskList;
	}

	public String getAppendScore() {
		return appendScore;
	}

	public void setAppendScore(String appendScore) {
		this.appendScore = appendScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}

	public List getStudentScoreList() {
		return studentScoreList;
	}

	public void setStudentScoreList(List studentScoreList) {
		this.studentScoreList = studentScoreList;
	}

	public List getScoreList() {
		return scoreList;
	}

	public void setScoreList(List scoreList) {
		this.scoreList = scoreList;
	}

	public abstract int updateScore(String type) throws PlatformException;
}
