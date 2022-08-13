package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;

/**
 * 类描述：开课登记信息
 * 
 * @author Administrator
 * 
 */
public abstract class RegBgCourse implements Items {
	private String id;

	private Student Student; //学生信息

	private String link;

	private String discourseName; //论文名称

	private String discourseContent; //论文内容

	private String discoursePlan; //毕业论文进度以及时间安排

	private String remark; //备注

	private String Teacher_note;//总站指导教师批注

	private String Siteteacher_note;//分站指导教师批注

	private String status;//0为初始态；1为分站指导教确认;2为总站确认

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
	
	public abstract  RegBgCourse getRegBgCourse(String id) ;
	
	public abstract int upLoadGraduateDesignWord(String reg_no,String fileLink);
}
