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
 * ProjectApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_edu_advance_adu")
public class TeacherEduAdvanceAdu implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TeacherEduAdvance teacherEduAdvance;
	private Integer checker;
	private Short type;
	private String reason;
	private Timestamp createtime;
	
	
	public TeacherEduAdvanceAdu() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TeacherEduAdvanceAdu(Integer id,
			TeacherEduAdvance teacherEduAdvance, Integer checker, Short type,
			String reason, Timestamp createtime) {
		super();
		this.id = id;
		this.teacherEduAdvance = teacherEduAdvance;
		this.checker = checker;
		this.type = type;
		this.reason = reason;
		this.createtime = createtime;
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
	@JoinColumn(name = "TEACHER_EDU_ADVANCE")
	public TeacherEduAdvance getTeacherEduAdvance() {
		return teacherEduAdvance;
	}


	public void setTeacherEduAdvance(TeacherEduAdvance teacherEduAdvance) {
		this.teacherEduAdvance = teacherEduAdvance;
	}


	@Column(name = "CHECKER", nullable = false)
	public Integer getChecker() {
		return checker;
	}


	public void setChecker(Integer checker) {
		this.checker = checker;
	}


	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return type;
	}


	public void setType(Short type) {
		this.type = type;
	}

	
	@Column(name = "REASON")
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	@Column(name = "CREATETIME", length = 19, nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}