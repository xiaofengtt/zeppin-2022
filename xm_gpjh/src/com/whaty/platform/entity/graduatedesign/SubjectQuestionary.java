package com.whaty.platform.entity.graduatedesign;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * ������ѧԱ��������Ŀ�����Ϣ
 * 
 * @author zhangliang
 * 
 */
public abstract class SubjectQuestionary implements Items {
	private String id;

	private Student student; // ѧԱ��Ϣ

	private String Main_job; // ѧԱ���幤��

	private String link;

	private String subjectName;// �ⶨ��������Ŀ

	private String subjectContent; // �ⶨ��������Ŀ����

	private String remark; // ��ע

	private String Teacher_note; // ��վ��ʦ��ע

	private String siteTeacher_note;

	private String status;

	private SiteTeacher siteTeacher;

	private String score;// ��ҵ����ҵ����

	private String score_note;// ��վ�Է���ķ�!��Ϣ

	private String score_status;// �����ȷ��״̬

	public SiteTeacher getSiteTeacher() {
		return siteTeacher;
	}

	public void setSiteTeacher(SiteTeacher siteTeacher) {
		this.siteTeacher = siteTeacher;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSiteTeacher_note() {
		return siteTeacher_note;
	}

	public void setSiteTeacher_note(String siteTeacher_note) {
		this.siteTeacher_note = siteTeacher_note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMain_job() {
		return Main_job;
	}

	public void setMain_job(String main_job) {
		Main_job = main_job;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacher_note() {
		return Teacher_note;
	}

	public void setTeacher_note(String teacher_note) {
		Teacher_note = teacher_note;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScore_note() {
		return score_note;
	}

	public void setScore_note(String score_note) {
		this.score_note = score_note;
	}

	public String getScore_status() {
		return score_status;
	}

	public void setScore_status(String score_status) {
		this.score_status = score_status;
	}

// *****************************************************
	/**
	 * ��վָ����ʦȷ�� ״̬Ϊ1
	 * 
	 */
	public abstract int siteTeacherConfirm();

	/**
	 * ��վָ����ʦ���� ״̬Ϊ2
	 */
	public abstract int siteTeacherReject();

	/**
	 * ��վָ����ʦȷ�� ״̬Ϊ3
	 */
	public abstract int teacherConfirm();

	/**
	 * ��վָ����ʦ���� ״̬Ϊ4
	 */
	public abstract int teacherReject();

	public abstract SubjectQuestionary getSubjectQuestionary(String studentId);

	public abstract int upLoadGraduateDesignWord(String reg_no, String fileLink);
	public abstract int upLoadGraduateDesignWord(String reg_no, String fileLink,String status);

	public abstract int updateScore();

	public abstract int teacherScoreConfirm();

	public abstract int teacherScoreReject();
	
	public abstract void updateScore_BatchExe(List scoreList) throws PlatformException;
	
	//---------------------���������ҵ�ɼ�,���������3ɼ��� lwx 2008-06-07
	public abstract void updateScore_BatchExeTotalGrade(List scoreList,String semesterId) throws PlatformException;
	
	public abstract int updateScoreToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score);
}
