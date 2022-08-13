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


@Entity
@Table(name = "teacher_training_reversal")
public class TeacherTrainingReversal implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private TeacherTrainingRecords teacherTrainingRecords;
	private TrainingSubject trainingSubject;
	private int classes;
	private Integer creator;
	private Timestamp creattime;
	private Short status;
	private Integer project;
	private Integer teacher;
	private Short subject;
	private Integer trainingCollege;
	private int oldClasses;
	
	

	public TeacherTrainingReversal() {
		super();
		// TODO Auto-generated constructor stub
	}



	public TeacherTrainingReversal(Integer id,
			TeacherTrainingRecords teacherTrainingRecords,
			TrainingSubject trainingSubject, int classes, Integer creator,
			Timestamp creattime, Short status, Integer project,
			Integer teacher, Short subject, Integer trainingCollege, int oldClasses) {
		super();
		this.id = id;
		this.teacherTrainingRecords = teacherTrainingRecords;
		this.trainingSubject = trainingSubject;
		this.classes = classes;
		this.creator = creator;
		this.creattime = creattime;
		this.status = status;
		this.project = project;
		this.teacher = teacher;
		this.subject = subject;
		this.trainingCollege = trainingCollege;
		this.oldClasses = oldClasses;
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
	@JoinColumn(name = "TEACHER_TRAINING_RECORD", nullable = false)
	public TeacherTrainingRecords getTeacherTrainingRecords() {
		return teacherTrainingRecords;
	}



	public void setTeacherTrainingRecords(
			TeacherTrainingRecords teacherTrainingRecords) {
		this.teacherTrainingRecords = teacherTrainingRecords;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REP_TRAINING_SUBJECT", nullable = false)
	public TrainingSubject getTrainingSubject() {
		return trainingSubject;
	}



	public void setTrainingSubject(TrainingSubject trainingSubject) {
		this.trainingSubject = trainingSubject;
	}


	@Column(name = "REP_CLASSES", length = 11)
	public int getClasses() {
		return classes;
	}



	public void setClasses(int classes) {
		this.classes = classes;
	}


	@Column(name = "CREATOR", length = 11, nullable=false)
	public Integer getCreator() {
		return creator;
	}



	public void setCreator(Integer creator) {
		this.creator = creator;
	}


	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime() {
		return creattime;
	}



	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}



	@Column(name = "STATUS", length = 11, nullable=false)
	public Short getStatus() {
		return status;
	}



	public void setStatus(Short status) {
		this.status = status;
	}


	@Column(name = "PROJECT", length = 11, nullable=false)
	public Integer getProject() {
		return project;
	}



	public void setProject(Integer project) {
		this.project = project;
	}


	@Column(name = "TEACHER", length = 11, nullable=false)
	public Integer getTeacher() {
		return teacher;
	}



	public void setTeacher(Integer teacher) {
		this.teacher = teacher;
	}

	

	@Column(name = "SUBJECT", nullable=false)
	public Short getSubject() {
		return subject;
	}



	public void setSubject(Short subject) {
		this.subject = subject;
	}


	@Column(name = "TRAINING_COLLEGE", nullable=false)
	public Integer getTrainingCollege() {
		return trainingCollege;
	}



	public void setTrainingCollege(Integer trainingCollege) {
		this.trainingCollege = trainingCollege;
	}



	
	
	@Column(name = "CLASSES", length = 11)
	public int getOldClasses() {
		return oldClasses;
	}



	public void setOldClasses(int oldClasses) {
		this.oldClasses = oldClasses;
	}



	
	
	

}