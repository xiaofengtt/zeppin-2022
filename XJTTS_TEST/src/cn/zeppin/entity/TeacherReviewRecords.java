package cn.zeppin.entity;
	
import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AssignTeacherCheck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_review_records")
public class TeacherReviewRecords implements java.io.Serializable{

	
	private Integer id; //ID
	private Teacher teacher;//教师
	private Short type; //记录类型 0为未通过审核记录 1为通过审核记录
	private Integer checker; //审核人
	private Timestamp checkTime; //审核时间
	private String reason; //未通过审核原因
	
	public TeacherReviewRecords() {
	}

	public TeacherReviewRecords(Teacher teacher, Short type, Integer checker,
			Timestamp checkTime) {
		this.teacher = teacher;
		this.type = type;
		this.checker = checker;
		this.checkTime = checkTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "CHECKER", nullable = false)
	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	@Column(name = "CHECKTIME", nullable = false, length = 19)
	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name = "REASON", length = 100)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}