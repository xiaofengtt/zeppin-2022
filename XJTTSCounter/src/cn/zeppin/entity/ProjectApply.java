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
 * ProjectApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_apply", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PROEJCT", "SUBJECT", "TRAINING_COLLEGE" }))
public class ProjectApply implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8005373917709773399L;
	private Integer id;
	private Document documentByProjectApplyDocument;
	private Project project;
	private TrainingSubject trainingSubject;
	private TrainingCollege trainingCollege;
	private Document documentByProformanceReport;
	private Document documentByStartMessage;
	private Short type;
	private Short status;
	private Timestamp trainingStarttime;
	private Timestamp trainingEndtime;
	private Integer trainingClasshour;
	private Integer trainingOnlineHour;
	private String trainingAddress;
	private String contacts;
	private String phone;
	private Integer creator;
	private Timestamp creattime;
	private Integer approver;
	private Timestamp approvetime;
	private Integer approveNumber;
	private Timestamp enrollEndTime;
	private Set<ProjectApplyWorkReport> projectApplyWorkReport = new HashSet<ProjectApplyWorkReport>(
			0);
	private Set<ProjectApplyExpert> projectApplyExpert = new HashSet<ProjectApplyExpert>(
			0);
	private Set<ProjectApplyEvaluate> projectApplyEvaluate = new HashSet<ProjectApplyEvaluate>(
			0);
	private Document documentByCurriculumDocument;// 课程表
	// 9月8号新增冗余存储4种培训学时信息
	private Integer centralize;
	private Integer information;
	private Integer regional;
	private Integer school;
	
	private String remark;

	// Constructors

	/** default constructor */
	public ProjectApply() {
	}

	/** minimal constructor */
	public ProjectApply(Project project, TrainingSubject trainingSubject,
			TrainingCollege trainingCollege, Short type, Short status,
			Integer trainingClasshour, Integer trainingOnlineHour,
			Integer creator, Timestamp creattime, Timestamp approvetime,
			Integer approveNumber) {
		this.project = project;
		this.trainingSubject = trainingSubject;
		this.trainingCollege = trainingCollege;
		this.type = type;
		this.status = status;
		this.trainingClasshour = trainingClasshour;
		this.trainingOnlineHour = trainingOnlineHour;
		this.creator = creator;
		this.creattime = creattime;
		this.approvetime = approvetime;
		this.approveNumber = approveNumber;
	}

	/** full constructor */
	public ProjectApply(Document documentByProjectApplyDocument,
			Document documentByWorkReport, Project project,
			TrainingSubject trainingSubject, TrainingCollege trainingCollege,
			Document documentByCurriculumDocument,
			Document documentByProformanceReport,
			Document documentByStartMessage, Short type, Short status,
			Timestamp trainingStarttime, Timestamp trainingEndtime,
			Integer trainingClasshour, Integer trainingOnlineHour,
			String trainingAddress, String contacts, String phone,
			Integer creator, Timestamp creattime, Integer approver,
			Timestamp approvetime, Integer approveNumber,
			Set<ProjectApplyExpert> projectApplyExpert,
			Set<ProjectApplyEvaluate> projectApplyEvaluate,
			Set<ProjectApplyWorkReport> projectApplyWorkReport) {
		this.documentByProjectApplyDocument = documentByProjectApplyDocument;
		this.project = project;
		this.trainingSubject = trainingSubject;
		this.trainingCollege = trainingCollege;
		this.documentByProformanceReport = documentByProformanceReport;
		this.documentByStartMessage = documentByStartMessage;
		this.documentByCurriculumDocument = documentByCurriculumDocument;
		this.type = type;
		this.status = status;
		this.trainingStarttime = trainingStarttime;
		this.trainingEndtime = trainingEndtime;
		this.trainingClasshour = trainingClasshour;
		this.trainingOnlineHour = trainingOnlineHour;
		this.trainingAddress = trainingAddress;
		this.contacts = contacts;
		this.phone = phone;
		this.creator = creator;
		this.creattime = creattime;
		this.approver = approver;
		this.approvetime = approvetime;
		this.approveNumber = approveNumber;
		this.projectApplyExpert = projectApplyExpert;
		this.projectApplyEvaluate = projectApplyEvaluate;
		this.projectApplyWorkReport = projectApplyWorkReport;
	}

	
	
	public ProjectApply(Integer id, Document documentByProjectApplyDocument,
			Project project, TrainingSubject trainingSubject,
			TrainingCollege trainingCollege,
			Document documentByProformanceReport,
			Document documentByStartMessage, Short type, Short status,
			Timestamp trainingStarttime, Timestamp trainingEndtime,
			Integer trainingClasshour, Integer trainingOnlineHour,
			String trainingAddress, String contacts, String phone,
			Integer creator, Timestamp creattime, Integer approver,
			Timestamp approvetime, Integer approveNumber,
			Timestamp enrollEndTime,
			Set<ProjectApplyWorkReport> projectApplyWorkReport,
			Set<ProjectApplyExpert> projectApplyExpert,
			Set<ProjectApplyEvaluate> projectApplyEvaluate,
			Document documentByCurriculumDocument, Integer centralize,
			Integer information, Integer regional, Integer school) {
		super();
		this.id = id;
		this.documentByProjectApplyDocument = documentByProjectApplyDocument;
		this.project = project;
		this.trainingSubject = trainingSubject;
		this.trainingCollege = trainingCollege;
		this.documentByProformanceReport = documentByProformanceReport;
		this.documentByStartMessage = documentByStartMessage;
		this.type = type;
		this.status = status;
		this.trainingStarttime = trainingStarttime;
		this.trainingEndtime = trainingEndtime;
		this.trainingClasshour = trainingClasshour;
		this.trainingOnlineHour = trainingOnlineHour;
		this.trainingAddress = trainingAddress;
		this.contacts = contacts;
		this.phone = phone;
		this.creator = creator;
		this.creattime = creattime;
		this.approver = approver;
		this.approvetime = approvetime;
		this.approveNumber = approveNumber;
		this.enrollEndTime = enrollEndTime;
		this.projectApplyWorkReport = projectApplyWorkReport;
		this.projectApplyExpert = projectApplyExpert;
		this.projectApplyEvaluate = projectApplyEvaluate;
		this.documentByCurriculumDocument = documentByCurriculumDocument;
		this.centralize = centralize;
		this.information = information;
		this.regional = regional;
		this.school = school;
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
	@JoinColumn(name = "PROJECT_APPLY_DOCUMENT")
	public Document getDocumentByProjectApplyDocument() {
		return this.documentByProjectApplyDocument;
	}

	public void setDocumentByProjectApplyDocument(
			Document documentByProjectApplyDocument) {
		this.documentByProjectApplyDocument = documentByProjectApplyDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROEJCT", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
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
	@JoinColumn(name = "TRAINING_COLLEGE", nullable = false)
	public TrainingCollege getTrainingCollege() {
		return this.trainingCollege;
	}

	public void setTrainingCollege(TrainingCollege trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFORMANCE_REPORT")
	public Document getDocumentByProformanceReport() {
		return this.documentByProformanceReport;
	}

	public void setDocumentByProformanceReport(
			Document documentByProformanceReport) {
		this.documentByProformanceReport = documentByProformanceReport;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "START_MESSAGE")
	public Document getDocumentByStartMessage() {
		return this.documentByStartMessage;
	}

	public void setDocumentByStartMessage(Document documentByStartMessage) {
		this.documentByStartMessage = documentByStartMessage;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "TRAINING_STARTTIME", length = 19)
	public Timestamp getTrainingStarttime() {
		return this.trainingStarttime;
	}

	public void setTrainingStarttime(Timestamp trainingStarttime) {
		this.trainingStarttime = trainingStarttime;
	}

	@Column(name = "TRAINING_ENDTIME", length = 19)
	public Timestamp getTrainingEndtime() {
		return this.trainingEndtime;
	}

	public void setTrainingEndtime(Timestamp trainingEndtime) {
		this.trainingEndtime = trainingEndtime;
	}

	@Column(name = "TRAINING_CLASSHOUR", nullable = false)
	public Integer getTrainingClasshour() {
		return this.trainingClasshour;
	}

	public void setTrainingClasshour(Integer trainingClasshour) {
		this.trainingClasshour = trainingClasshour;
	}

	@Column(name = "TRAINING_ONLINE_HOUR", nullable = false)
	public Integer getTrainingOnlineHour() {
		return this.trainingOnlineHour;
	}

	public void setTrainingOnlineHour(Integer trainingOnlineHour) {
		this.trainingOnlineHour = trainingOnlineHour;
	}

	@Column(name = "TRAINING_ADDRESS", length = 200)
	public String getTrainingAddress() {
		return this.trainingAddress;
	}

	public void setTrainingAddress(String trainingAddress) {
		this.trainingAddress = trainingAddress;
	}

	@Column(name = "CONTACTS", length = 100)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Column(name = "APPROVER")
	public Integer getApprover() {
		return this.approver;
	}

	public void setApprover(Integer approver) {
		this.approver = approver;
	}

	@Column(name = "APPROVETIME", nullable = false, length = 19)
	public Timestamp getApprovetime() {
		return this.approvetime;
	}

	public void setApprovetime(Timestamp approvetime) {
		this.approvetime = approvetime;
	}

	@Column(name = "APPROVE_NUMBER", nullable = false)
	public Integer getApproveNumber() {
		return this.approveNumber;
	}

	public void setApproveNumber(Integer approveNumber) {
		this.approveNumber = approveNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectApply")
	public Set<ProjectApplyExpert> getProjectApplyExpert() {
		return this.projectApplyExpert;
	}

	public void setProjectApplyExpert(Set<ProjectApplyExpert> projectApplyExpert) {
		this.projectApplyExpert = projectApplyExpert;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectApply")
	public Set<ProjectApplyEvaluate> getProjectApplyEvaluate() {
		return this.projectApplyEvaluate;
	}

	public void setProjectApplyEvaluate(
			Set<ProjectApplyEvaluate> projectApplyEvaluate) {
		this.projectApplyEvaluate = projectApplyEvaluate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectApply")
	public Set<ProjectApplyWorkReport> getProjectApplyWorkReport() {
		return this.projectApplyWorkReport;
	}

	public void setProjectApplyWorkReport(
			Set<ProjectApplyWorkReport> projectApplyWorkReport) {
		this.projectApplyWorkReport = projectApplyWorkReport;
	}

	@Column(name = "ENROLL_ENDTIME", length = 19)
	public Timestamp getEnrollEndTime() {
		return enrollEndTime;
	}

	public void setEnrollEndTime(Timestamp enrollEndTime) {
		this.enrollEndTime = enrollEndTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRICULUM")
	public Document getDocumentByCurriculumDocument() {
		return documentByCurriculumDocument;
	}

	public void setDocumentByCurriculumDocument(
			Document documentByCurriculumDocument) {
		this.documentByCurriculumDocument = documentByCurriculumDocument;
	}

	@Column(name = "CENTRALIZE", nullable = false)
	public Integer getCentralize() {
		return centralize;
	}

	public void setCentralize(Integer centralize) {
		this.centralize = centralize;
	}

	@Column(name = "INFORMATION", nullable = false)
	public Integer getInformation() {
		return information;
	}

	public void setInformation(Integer information) {
		this.information = information;
	}

	@Column(name = "REGIONAL", nullable = false)
	public Integer getRegional() {
		return regional;
	}

	public void setRegional(Integer regional) {
		this.regional = regional;
	}

	@Column(name = "SCHOOL", nullable = false)
	public Integer getSchool() {
		return school;
	}

	public void setSchool(Integer school) {
		this.school = school;
	}

	@Column(name = "REMARK", nullable = false)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}