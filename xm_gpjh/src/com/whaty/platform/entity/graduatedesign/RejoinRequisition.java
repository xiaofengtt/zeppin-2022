package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * 类描述：答辩（评审）申请表
 * 
 * @author zhangliang
 * 
 */
public abstract class RejoinRequisition implements Items {
	private String id;

	private Student student; // 学生信息

	private String link;

	private String discourseName;// 论文名称

	private String completeStatus;// 论文完成状态

	private String RequisitionType = "1";// 申请类型  1为评审；2为答辩

	private String remark;// 备注

	private String Teacher_note;// 总站指导教师批注

	private String Siteteacher_note;// 分站指导教师批注

	private String status;// 0 为初始状态；1为分站指导教师确认；2为总指导教师确认状态

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
	 * 分站指导教师确认 状态为1
	 */
	public abstract int siteTeacherConfirm();
	/**
	 * 分站指导教师驳回 状态为2
	 */
	public abstract int siteTeacherReject();
	/**
	 * 总站指导教师确认 状态为3
	 */
	public abstract int teacherConfirm();
	/**
	 * 总站指导教师驳回 状态为4
	 */
	public abstract int teacherReject();
	
	public abstract RejoinRequisition getRejoinRequisition(String id) ;
	
	public abstract int upLoadGraduateDesignWord(String reg_no,String fileLink);
}
