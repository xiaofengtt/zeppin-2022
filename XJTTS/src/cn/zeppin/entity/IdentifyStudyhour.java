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
 * IdentifyClasshoursSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "identify_studyhour")
public class IdentifyStudyhour implements java.io.Serializable
{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8005373917709773399L;
	
	private Integer id;
	private ProjectCycle projectCycle;
	private ProjectType projectType;
	private String year;
	private Project project;
	private TrainingSubject trainingSubject;
	private String studyhour;
	private Integer credit;
	private Short status;
	private Integer creator;
	private Timestamp createtime;
	

	public IdentifyStudyhour(Integer id, ProjectCycle projectCycle, ProjectType projectType, String year, Project project,
			TrainingSubject trainingSubject, String studyhour, Integer credit, Short status, Integer creator, Timestamp createtime) {
		super();
		this.id = id;
		this.projectCycle = projectCycle;
		this.projectType = projectType;
		this.year = year;
		this.project = project;
		this.trainingSubject = trainingSubject;
		this.studyhour = studyhour;
		this.credit = credit;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
	}


	public IdentifyStudyhour() {
		super();
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
	
	@Column(name = "STUDYHOUR")
	public String getStudyhour() {
		return studyhour;
	}

	public void setStudyhour(String studyhour) {
		this.studyhour = studyhour;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_CYCLE", nullable = false)
	public ProjectCycle getProjectCycle() {
		return projectCycle;
	}

	public void setProjectCycle(ProjectCycle projectCycle) {
		this.projectCycle = projectCycle;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_TYPE", nullable = false)
	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAINING_SUBJECT", nullable = false)
	public TrainingSubject getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(TrainingSubject trainingSubject) {
		this.trainingSubject = trainingSubject;
	}
	
	@Column(name = "CREDIT")
	public Integer getCredit() {
		return credit;
	}
	
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Column(name = "YEAR", nullable = false, length = 4)
    public String getYear()
    {
		return this.year;
    }

    public void setYear(String year)
    {
		this.year = year;
    }
	
}