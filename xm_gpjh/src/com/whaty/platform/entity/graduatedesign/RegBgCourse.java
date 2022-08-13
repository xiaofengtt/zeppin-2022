package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * �����������εǼ���Ϣ
 * 
 * @author Administrator
 * 
 */
public abstract class RegBgCourse implements Items {
	private String id;

	private Student Student; //ѧ����Ϣ

	private String link;

	private String discourseName; //��������

	private String discourseContent; //��������

	private String discoursePlan; //��ҵ���Ľ����Լ�ʱ�䰲��

	private String remark; //��ע

	private String Teacher_note;//��վָ����ʦ��ע

	private String Siteteacher_note;//��վָ����ʦ��ע

	private String status;//0Ϊ��ʼ̬��1Ϊ��վָ����ȷ��;2Ϊ��վȷ��

	private SiteTeacher siteTeacher;
	
	public SiteTeacher getSiteTeacher() {
		return siteTeacher;
	}

	public void setSiteTeacher(SiteTeacher siteTeacher) {
		this.siteTeacher = siteTeacher;
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

	public String getDiscoursePlan() {
		return discoursePlan;
	}

	public void setDiscoursePlan(String discoursePlan) {
		this.discoursePlan = discoursePlan;
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
		return Student;
	}

	public void setStudent(Student student) {
		Student = student;
	}

	public String getTeacher_note() {
		return Teacher_note;
	}

	public void setTeacher_note(String teacher_note) {
		Teacher_note = teacher_note;
	}
    //*************************************************
	
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
	
	public abstract  RegBgCourse getRegBgCourse(String id) ;
	
	public abstract int upLoadGraduateDesignWord(String reg_no,String fileLink);
}
