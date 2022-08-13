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
 * 待分配学员记录表
 */
@Entity
@Table(name = "teacher_training_assigned")
public class TeacherTrainingAssigned implements java.io.Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Teacher teacher;
	private Project project;
	private TrainingSubject subject;
	private Short status;
	private Timestamp createtime;
	
	
	public TeacherTrainingAssigned() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TeacherTrainingAssigned(Integer id, Teacher teacher,
			Project project, TrainingSubject subject, Short status,
			Timestamp createtime) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.project = project;
		this.subject = subject;
		this.status = status;
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
	@JoinColumn(name = "TEACHER", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT", nullable = false)
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAINING_SUBJECT", nullable = false)
	public TrainingSubject getSubject() {
		return subject;
	}


	public void setSubject(TrainingSubject subject) {
		this.subject = subject;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}