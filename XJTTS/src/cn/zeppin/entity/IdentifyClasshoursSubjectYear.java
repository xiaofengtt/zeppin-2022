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
@Table(name = "identify_classhours_subject_year")
public class IdentifyClasshoursSubjectYear implements java.io.Serializable
{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8005373917709773399L;
	
	private Integer id;
	private Integer centralize;
	private Integer information;
	private Integer regional;
	private Integer school;
	private Integer totalhours;
	private String studyhour;
	private ProjectType projecttype;
	private Integer creator;
	private Short status;
	private Timestamp createtime;
	private TrainingSubject trainingsubject;
	private Integer credit;
	private String year;
	

	public IdentifyClasshoursSubjectYear(Integer id, Integer centralize,
			Integer information, Integer regional, Integer school, Integer totalhours, 
			String studyhour, ProjectType projecttype, Integer creator, Short status,
			Timestamp createtime, TrainingSubject trainingsubject,
			Integer credit, String year) {
		super();
		this.id = id;
		this.centralize = centralize;
		this.information = information;
		this.regional = regional;
		this.school = school;
		this.totalhours = totalhours;
		this.studyhour = studyhour;
		this.projecttype = projecttype;
		this.creator = creator;
		this.status = status;
		this.createtime = createtime;
		this.trainingsubject = trainingsubject;
		this.credit = credit;
		this.year = year;
	}


	public IdentifyClasshoursSubjectYear() {
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

	@Column(name = "CENTRALIZE")
	public Integer getCentralize() {
		return centralize;
	}

	public void setCentralize(Integer centralize) {
		this.centralize = centralize;
	}

	@Column(name = "INFORMATION")
	public Integer getInformation() {
		return information;
	}

	public void setInformation(Integer information) {
		this.information = information;
	}

	@Column(name = "REGIONAL")
	public Integer getRegional() {
		return regional;
	}

	public void setRegional(Integer regional) {
		this.regional = regional;
	}

	@Column(name = "SCHOOL")
	public Integer getSchool() {
		return school;
	}

	public void setSchool(Integer school) {
		this.school = school;
	}
	
	@Column(name = "TOTALHOURS")
	public Integer getTotalhours() {
		return totalhours;
	}
	
	public void setTotalhours(Integer totalhours) {
		this.totalhours = totalhours;
	}
	
	@Column(name = "STUDYHOUR")
	public String getStudyhour() {
		return studyhour;
	}

	public void setStudyhour(String studyhour) {
		this.studyhour = studyhour;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_TYPE", nullable = false)
	public ProjectType getProjecttype() {
		return projecttype;
	}


	public void setProjecttype(ProjectType projecttype) {
		this.projecttype = projecttype;
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
	public TrainingSubject getTrainingsubject() {
		return trainingsubject;
	}


	public void setTrainingsubject(TrainingSubject trainingsubject) {
		this.trainingsubject = trainingsubject;
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