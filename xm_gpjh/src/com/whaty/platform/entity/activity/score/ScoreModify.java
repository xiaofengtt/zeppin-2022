package com.whaty.platform.entity.activity.score;

import com.whaty.platform.entity.activity.Elective;

public class ScoreModify {


	private String id;
	
	private String elective_id;
	
	private String student_id;
	
	private String status;
	
	private String teacher_name;
	
	private String teacher_id;

	//分数类型
	private String type;
	
	//修改前分数
	private float old_score;
	
	//修改后分数
	private float new_score;
	
	//修改前分数等级 例如优秀，良好等
	private String old_scoreLevel;
	
	//修改后分数等级 例如优秀，良好等
	private String new_scoreLevel;
	
	//分数显示采用数字还是等级，如果是数字，isLevelScore=false；如果是等级，isLevelScore=true;
	private boolean isLevelScore;

	//修改成绩的原因
	private String modifyReason;
	
	//修改成绩的时间
	private String modifyDate;
	
	//修改成绩者
	private ScoreModifier scoreModifier;
	
	//成绩所属的选课信息
	private Elective elective;
	
	public boolean isLevelScore() {
		return isLevelScore;
	}

	public void setLevelScore(boolean isLevelScore) {
		this.isLevelScore = isLevelScore;
	}

	public float getNew_score() {
		return new_score;
	}

	public void setNew_score(float new_score) {
		this.new_score = new_score;
	}

	public float getOld_score() {
		return old_score;
	}

	public void setOld_score(float old_score) {
		this.old_score = old_score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNew_scoreLevel() {
		return new_scoreLevel;
	}

	public void setNew_scoreLevel(String new_scoreLevel) {
		this.new_scoreLevel = new_scoreLevel;
	}

	public String getOld_scoreLevel() {
		return old_scoreLevel;
	}

	public void setOld_scoreLevel(String old_scoreLevel) {
		this.old_scoreLevel = old_scoreLevel;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public ScoreModifier getScoreModifier() {
		return scoreModifier;
	}

	public void setScoreModifier(ScoreModifier scoreModifier) {
		this.scoreModifier = scoreModifier;
	}

	public Elective getElective() {
		return elective;
	}

	public void setElective(Elective elective) {
		this.elective = elective;
	}

	public String getElective_id() {
		return elective_id;
	}

	public void setElective_id(String elective_id) {
		this.elective_id = elective_id;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

}
