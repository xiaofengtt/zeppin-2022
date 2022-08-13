package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.user.Student;

public abstract class FreeApply implements Items {

	private String id;
	private String studentId;
	private String link;
	private String teacher_note;
	private String applyTime;
	private String productionName;
	private String status;
	private Student student; 
	
	private String score_status; //免做申请中审核的成绩
	private String type; //标识类型,1毕业设计,0大做业
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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
	public String getProductionName() {
		return productionName;
	}
	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getTeacher_note() {
		return teacher_note;
	}
	public void setTeacher_note(String teacher_note) {
		this.teacher_note = teacher_note;
	}
	
	/**
	 * @msg 根据学生ＩＤ获得免做申请
	 * @param String studentId 学生ＩＤ
	 * @return FreeApply 免做申请
	 * */
	public abstract FreeApply getFreeApplyByStudentId(String studentId) ;

	/**
	 * @msg 修改免做申请审核状态
	 * @param status
	 * @return int 
	 * */
	public abstract int UpdateFreeApplyStatus(String status) ;
	
	/**
	 * 把成绩加入总评中
	 * @return
	 */
	public abstract int updateFreeApplyToTotalGrade(String studentId,String semesterId,String courseId,String score);
	
	public String getScore_status() {
		return score_status;
	}
	public void setScore_status(String score_status) {
		this.score_status = score_status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
