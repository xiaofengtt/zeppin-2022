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
 * IdentifyClasshours entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "identify_classhours")
public class IdentifyClasshours implements java.io.Serializable
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
	private ProjectType projecttype;
	private Integer creator;
	private Short status;
	private Timestamp createtime;
	
	
	public IdentifyClasshours() {
		super();
		// TODO Auto-generated constructor stub
	}


	public IdentifyClasshours(Integer id, Integer centralize,
			Integer information, Integer regional, Integer school,
			ProjectType projecttype, Integer creator, Short status, Timestamp createtime) {
		super();
		this.id = id;
		this.centralize = centralize;
		this.information = information;
		this.regional = regional;
		this.school = school;
		this.projecttype = projecttype;
		this.creator = creator;
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
	
}