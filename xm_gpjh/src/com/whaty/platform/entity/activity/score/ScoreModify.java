package com.whaty.platform.entity.activity.score;

import com.whaty.platform.entity.activity.Elective;

public class ScoreModify {


	private String id;
	
	private String elective_id;
	
	private String student_id;
	
	private String status;
	
	private String teacher_name;
	
	private String teacher_id;

	//��������
	private String type;
	
	//�޸�ǰ����
	private float old_score;
	
	//�޸ĺ����
	private float new_score;
	
	//�޸�ǰ�����ȼ� �������㣬���õ�
	private String old_scoreLevel;
	
	//�޸ĺ�����ȼ� �������㣬���õ�
	private String new_scoreLevel;
	
	//������ʾ�������ֻ��ǵȼ�����������֣�isLevelScore=false������ǵȼ���isLevelScore=true;
	private boolean isLevelScore;

	//�޸ĳɼ���ԭ��
	private String modifyReason;
	
	//�޸ĳɼ���ʱ��
	private String modifyDate;
	
	//�޸ĳɼ���
	private ScoreModifier scoreModifier;
	
	//�ɼ�������ѡ����Ϣ
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
