package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TeacherTrainingRecords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_training_records",uniqueConstraints = { @UniqueConstraint(columnNames = "UUID"), @UniqueConstraint(columnNames = { "PROJECT", "SUBJECT", "TRAINING_COLLEGE", "TEACHER" }) })
public class TeacherTrainingRecords implements java.io.Serializable {

	/**
	 * 
	 */
	// Fields

	private Integer id;
	private Organization organization;
	private TrainingSubject trainingSubject;
	private Project project;
	private Teacher teacher;
	private TrainingCollege trainingCollege;
	private Integer creator;
	private Timestamp creattime;
	private Short checkStatus;
	private String uuid;
	private Boolean isPrepare;
	private Short finalStatus;
	private Timestamp trainingRegistertime;
	private Integer trainingClasshour;
	private Integer trainingOnlineHour;
	private Short trainingStatus;
	private String trainingReason;
	private Float trainingScore;
	private String certificate;
	private Integer registrant;
	private Timestamp registtime;
	private String remark1;
	private String remark2;
	private Set<AssignTeacherCheck> assignTeacherChecks = new HashSet<AssignTeacherCheck>(0);
	private Integer classes;
	private Short isConfirm;
	private Short replaceStatus;
	private Integer replaceTeacher;

	// Constructors

	/** default constructor */
	public TeacherTrainingRecords() {
	}

	/** minimal constructor */
	public TeacherTrainingRecords(Organization organization, TrainingSubject trainingSubject, Project project, Teacher teacher, TrainingCollege trainingCollege, Integer creator, Timestamp creattime, Short checkStatus, String uuid, Boolean isPrepare, Short finalStatus, Short trainingStatus,
			Timestamp registtime) {
		this.organization = organization;
		this.trainingSubject = trainingSubject;
		this.project = project;
		this.teacher = teacher;
		this.trainingCollege = trainingCollege;
		this.creator = creator;
		this.creattime = creattime;
		this.checkStatus = checkStatus;
		this.uuid = uuid;
		this.isPrepare = isPrepare;
		this.finalStatus = finalStatus;
		this.trainingStatus = trainingStatus;
		this.registtime = registtime;
	}

	/** full constructor */
	public TeacherTrainingRecords(Organization organization, TrainingSubject trainingSubject, Project project, Teacher teacher, TrainingCollege trainingCollege, Integer creator, Timestamp creattime, Short checkStatus, String uuid, Boolean isPrepare, Short finalStatus,
			Timestamp trainingRegistertime, Integer trainingClasshour,Integer trainingOnlineHour, Short trainingStatus, String trainingReason, Float trainingScore, String certificate, Integer registrant, Timestamp registtime, String remark1, String remark2, Set<AssignTeacherCheck> assignTeacherChecks,Integer classes) {
		this.organization = organization;
		this.trainingSubject = trainingSubject;
		this.project = project;
		this.teacher = teacher;
		this.trainingCollege = trainingCollege;
		this.creator = creator;
		this.creattime = creattime;
		this.checkStatus = checkStatus;
		this.uuid = uuid;
		this.isPrepare = isPrepare;
		this.finalStatus = finalStatus;
		this.trainingRegistertime = trainingRegistertime;
		this.trainingClasshour = trainingClasshour;
		this.trainingOnlineHour=trainingOnlineHour;
		this.trainingStatus = trainingStatus;
		this.trainingReason = trainingReason;
		this.trainingScore = trainingScore;
		this.certificate = certificate;
		this.registrant = registrant;
		this.registtime = registtime;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.assignTeacherChecks = assignTeacherChecks;
		this.classes = classes;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION", nullable = false)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBJECT", nullable = false)
	public TrainingSubject getTrainingSubject() {
		return this.trainingSubject;
	}

	public void setTrainingSubject(TrainingSubject trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAINING_COLLEGE", nullable = false)
	public TrainingCollege getTrainingCollege() {
		return this.trainingCollege;
	}

	public void setTrainingCollege(TrainingCollege trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	@Column(name = "CREATOR", nullable = false)
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

	@Column(name = "CHECK_STATUS", nullable = false)
	public Short getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Column(name = "UUID", unique = true, nullable = false, length = 50)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "IS_PREPARE", nullable = false)
	public Boolean getIsPrepare() {
		return this.isPrepare;
	}

	public void setIsPrepare(Boolean isPrepare) {
		this.isPrepare = isPrepare;
	}

	@Column(name = "FINAL_STATUS", nullable = false)
	public Short getFinalStatus() {
		return this.finalStatus;
	}

	public void setFinalStatus(Short finalStatus) {
		this.finalStatus = finalStatus;
	}

	@Column(name = "TRAINING_REGISTERTIME", length = 19)
	public Timestamp getTrainingRegistertime() {
		return this.trainingRegistertime;
	}

	public void setTrainingRegistertime(Timestamp trainingRegistertime) {
		this.trainingRegistertime = trainingRegistertime;
	}

	@Column(name = "TRAINING_CLASSHOUR")
	public Integer getTrainingClasshour() {
		return this.trainingClasshour;
	}

	public void setTrainingClasshour(Integer trainingClasshour) {
		this.trainingClasshour = trainingClasshour;
	}
	
	@Column(name = "TRAINING_ONLINE_HOUR")
	public Integer getTrainingOnlineHour() {
		return this.trainingOnlineHour;
	}

	public void setTrainingOnlineHour(Integer trainingOnlineHour) {
		this.trainingOnlineHour = trainingOnlineHour;
	}
	
	@Column(name = "TRAINING_STATUS", nullable = false)
	public Short getTrainingStatus() {
		return this.trainingStatus;
	}

	public void setTrainingStatus(Short trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	@Column(name = "TRAINING_REASON", length = 200)
	public String getTrainingReason() {
		return this.trainingReason;
	}

	public void setTrainingReason(String trainingReason) {
		this.trainingReason = trainingReason;
	}

	@Column(name = "TRAINING_SCORE", precision = 12, scale = 0)
	public Float getTrainingScore() {
		return this.trainingScore;
	}

	public void setTrainingScore(Float trainingScore) {
		this.trainingScore = trainingScore;
	}

	@Column(name = "CERTIFICATE", length = 50)
	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Column(name = "REGISTRANT")
	public Integer getRegistrant() {
		return this.registrant;
	}

	public void setRegistrant(Integer registrant) {
		this.registrant = registrant;
	}

	@Column(name = "REGISTTIME", nullable = false, length = 19)
	public Timestamp getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(Timestamp registtime) {
		this.registtime = registtime;
	}

	@Column(name = "REMARK1", length = 200)
	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@Column(name = "REMARK2", length = 200)
	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	@Column(name = "CLASSES", length = 11)
	public Integer getClasses() {
		return this.classes;
	}

	public void setClasses(Integer classes) {
		this.classes = classes;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherTrainingRecords")
	public Set<AssignTeacherCheck> getAssignTeacherChecks() {
		return this.assignTeacherChecks;
	}

	public void setAssignTeacherChecks(Set<AssignTeacherCheck> assignTeacherChecks) {
		this.assignTeacherChecks = assignTeacherChecks;
	}

	@Column(name = "ISCONFIRM")
	public Short getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Short isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	@Column(name = "REPLACE_STATUS")
	public Short getReplaceStatus() {
		return replaceStatus;
	}

	public void setReplaceStatus(Short replaceStatus) {
		this.replaceStatus = replaceStatus;
	}

	@Column(name = "REPLACE_TEACHER")
	public Integer getReplaceTeacher() {
		return replaceTeacher;
	}

	public void setReplaceTeacher(Integer replaceTeacher) {
		this.replaceTeacher = replaceTeacher;
	}
	
	

}