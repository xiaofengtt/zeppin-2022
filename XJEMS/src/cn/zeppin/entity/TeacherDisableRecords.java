package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Ethnic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_disable_records")
public class TeacherDisableRecords implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6915108687348641826L;
	private Short id;
	private InvigilationTeacher teacher;
	private ExamInformation exam;
	private String reason;
	private int creator;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public TeacherDisableRecords() {
	}

	/** minimal constructor */

	public TeacherDisableRecords(Short id, InvigilationTeacher teacher, ExamInformation exam, String reason,
			int creator, Timestamp createtime) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.exam = exam;
		this.reason = reason;
		this.creator = creator;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Short getId() {
		return this.id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "TEACHER", nullable = false)
	public InvigilationTeacher getTeacher() {
		return teacher;
	}

	public void setTeacher(InvigilationTeacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXAM", nullable = false)
	public ExamInformation getExam() {
		return exam;
	}

	public void setExam(ExamInformation exam) {
		this.exam = exam;
	}

	@Column(name = "reason", length = 50)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "creator")
	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	@Column(name = "createtime")
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}