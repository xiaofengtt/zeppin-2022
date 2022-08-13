package com.whaty.platform.entity.graduatedesign;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * 类描述：标准毕业论文信息
 * 
 * @author zhangliang
 * 
 */
public abstract class Discourse implements Items {
	
	private String id;
	private Student student; //学生信息

	private String link;

	private String discourseName; //毕业论文名称

	private String discourseContent; //毕业论文内容

	private String remark; //备注

	private String requisitionType; //申请类型：1为答辩；2为评审

	private String requisitionGrade; //答辩或评审成绩

	private String teacher_note;  //总站指导批注
	
	private String siteTeacher_note; //分站指导教师批注
	
	private String status;         //审核状态位
	
	private String score_status; //毕业设计分数审核状态
	
	private String score_note; //毕业设计分数审核反馈信息
	
	
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
   /**
    * 更新答辩或评审成绩信息
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
	
	
	//--------------------批量导入成绩,并把成绩加入到总评成绩中  lwx 2008-06-07
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
