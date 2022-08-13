package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * ����������磨���������
 * 
 * @author zhangliang
 * 
 */
public abstract class RejoinRequisition implements Items {
	private String id;

	private Student student; // ѧ����Ϣ

	private String link;

	private String discourseName;// ��������

	private String completeStatus;// �������״̬

	private String RequisitionType = "1";// ��������  1Ϊ����2Ϊ���

	private String remark;// ��ע

	private String Teacher_note;// ��վָ����ʦ��ע

	private String Siteteacher_note;// ��վָ����ʦ��ע

	private String status;// 0 Ϊ��ʼ״̬��1Ϊ��վָ����ʦȷ�ϣ�2Ϊ��ָ����ʦȷ��״̬

	private SiteTeacher siteTeacher;
	
	public SiteTeacher getSiteTeacher() {
		return siteTeacher;
	}

	public void setSiteTeacher(SiteTeacher siteTeacher) {
		this.siteTeacher = siteTeacher;
	}

	public String getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
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

	public String getRequisitionType() {
		return RequisitionType;
	}

	public void setRequisitionType(String requisitionType) {
		RequisitionType = requisitionType;
	}

	public String getSiteteacher_note() {
		return Siteteacher_note;
	}

	public void setSiteteacher_note(String siteteacher_note) {
		Siteteacher_note = siteteacher_note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTeacher_note() {
		return Teacher_note;
	}

	public void setTeacher_note(String teacher_note) {
		Teacher_note = teacher_note;
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
	
	public abstract RejoinRequisition getRejoinRequisition(String id) ;
	
	public abstract int upLoadGraduateDesignWord(String reg_no,String fileLink);
}
