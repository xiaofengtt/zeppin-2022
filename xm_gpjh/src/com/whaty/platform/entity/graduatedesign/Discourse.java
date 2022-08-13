package com.whaty.platform.entity.graduatedesign;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * ����������׼��ҵ������Ϣ
 * 
 * @author zhangliang
 * 
 */
public abstract class Discourse implements Items {
	
	private String id;
	private Student student; //ѧ����Ϣ

	private String link;

	private String discourseName; //��ҵ��������

	private String discourseContent; //��ҵ��������

	private String remark; //��ע

	private String requisitionType; //�������ͣ�1Ϊ��磻2Ϊ����

	private String requisitionGrade; //��������ɼ�

	private String teacher_note;  //��վָ����ע
	
	private String siteTeacher_note; //��վָ����ʦ��ע
	
	private String status;         //���״̬λ
	
	private String score_status; //��ҵ��Ʒ������״̬
	
	private String score_note; //��ҵ��Ʒ�����˷�����Ϣ
	
	
	private SiteTeacher siteTeacher;
	 
	public String getSiteTeacher_note() {
		return siteTeacher_note;
	}

	public void setSiteTeacher_note(String siteTeacher_note) {
		this.siteTeacher_note = siteTeacher_note;
	}

	public String getTeacher_note() {
		return teacher_note;
	}

	public void setTeacher_note(String teacher_note) {
		this.teacher_note = teacher_note;
	}

	public String getDiscourseContent() {
		return discourseContent;
	}

	public void setDiscourseContent(String discourseContent) {
		this.discourseContent = discourseContent;
	}

	public String getDiscourseName() {
		return discourseName;
	}

	public void setDiscourseName(String discourseName) {
		this.discourseName = discourseName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRequisitionGrade() {
		return requisitionGrade;
	}

	public void setRequisitionGrade(String requisitionGrade) {
		this.requisitionGrade = requisitionGrade;
	}

	public String getRequisitionType() {
		return requisitionType;
	}

	public void setRequisitionType(String requisitionType) {
		this.requisitionType = requisitionType;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
     //*****************************************************
	/**
	 * ��վָ����ʦȷ�� ״̬Ϊ1
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
   /**
    * ���´�������ɼ���Ϣ
    * @return
    */
	public abstract int updateGrade();
	
	public abstract Discourse  getDiscourse(String studentId);
	
	public SiteTeacher getSiteTeacher() {
		return siteTeacher;
	}

	public void setSiteTeacher(SiteTeacher siteTeacher) {
		this.siteTeacher = siteTeacher;
	}
	
	public abstract int upLoadGraduateDesignWord(String reg_no,String fileLink);
	
	public abstract void graduateAssessGradeAddBatch(List discourseList) throws PlatformException;
	
	public abstract void graduateRejoinGradeAddBatch(List discourseList) throws PlatformException;
	
	
	//--------------------��������ɼ�,���ѳɼ����뵽�����ɼ���  lwx 2008-06-07
	public abstract void graduateAssessGradeAddBatchToTotalGrade(List discourseList,String semesterId) throws PlatformException;
	
	public abstract void graduateRejoinGradeAddBatchToTotalGrade(List discourseList,String semesterId) throws PlatformException;
	
	//-------------------------end 
	

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
	
	public abstract int teacherScoreConfirm();
	
	public abstract int teacherScoreReject();

	
}
