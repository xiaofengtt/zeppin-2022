package entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TrainingSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "training_subject", catalog = "xjtts")
public class TrainingSubject implements java.io.Serializable {

	// Fields

	private Short id;
	private String name;
	private String introduce;
	private Short status;
	private Integer creator;
	private Timestamp creattime;
	private Set<ProjectSubjectRange> projectSubjectRanges = new HashSet<ProjectSubjectRange>(0);
	private Set<Submit> submits = new HashSet<Submit>(0);
	private Set<AssignTeacherTask> assignTeacherTasks = new HashSet<AssignTeacherTask>(0);
	private Set<ProjectApply> projectApplies = new HashSet<ProjectApply>(0);
	private Set<TeacherTrainingRecords> teacherTrainingRecordses = new HashSet<TeacherTrainingRecords>(0);

	// Constructors

	/** default constructor */
	public TrainingSubject() {
	}

	/** minimal constructor */
	public TrainingSubject(String name, Short status, Timestamp creattime) {
		this.name = name;
		this.status = status;
		this.creattime = creattime;
	}

	/** full constructor */
	public TrainingSubject(String name, String introduce, Short status, Integer creator, Timestamp creattime, Set<ProjectSubjectRange> projectSubjectRanges,
			Set<Submit> submits, Set<AssignTeacherTask> assignTeacherTasks, Set<ProjectApply> projectApplies,
			Set<TeacherTrainingRecords> teacherTrainingRecordses) {
		this.name = name;
		this.introduce = introduce;
		this.status = status;
		this.creator = creator;
		this.creattime = creattime;
		this.projectSubjectRanges = projectSubjectRanges;
		this.submits = submits;
		this.assignTeacherTasks = assignTeacherTasks;
		this.projectApplies = projectApplies;
		this.teacherTrainingRecordses = teacherTrainingRecordses;
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

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "INTRODUCE", length = 200)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATOR")
	public Integer getCreator() {
		return this.creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime() {
		return this.creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingSubject")
	public Set<ProjectSubjectRange> getProjectSubjectRanges() {
		return this.projectSubjectRanges;
	}

	public void setProjectSubjectRanges(Set<ProjectSubjectRange> projectSubjectRanges) {
		this.projectSubjectRanges = projectSubjectRanges;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingSubject")
	public Set<Submit> getSubmits() {
		return this.submits;
	}

	public void setSubmits(Set<Submit> submits) {
		this.submits = submits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingSubject")
	public Set<AssignTeacherTask> getAssignTeacherTasks() {
		return this.assignTeacherTasks;
	}

	public void setAssignTeacherTasks(Set<AssignTeacherTask> assignTeacherTasks) {
		this.assignTeacherTasks = assignTeacherTasks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingSubject")
	public Set<ProjectApply> getProjectApplies() {
		return this.projectApplies;
	}

	public void setProjectApplies(Set<ProjectApply> projectApplies) {
		this.projectApplies = projectApplies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingSubject")
	public Set<TeacherTrainingRecords> getTeacherTrainingRecordses() {
		return this.teacherTrainingRecordses;
	}

	public void setTeacherTrainingRecordses(Set<TeacherTrainingRecords> teacherTrainingRecordses) {
		this.teacherTrainingRecordses = teacherTrainingRecordses;
	}

}