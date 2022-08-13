package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_cycle")
public class ProjectCycle implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String startyear;
	private String endyear;
	private Integer creator;
	private Timestamp createtime;
	private Short status;
	private Integer centralize;
	private Integer information;
	private Integer regional;
	private Integer school;
	private Integer totalhours;
	private String studyhour;
	
	public ProjectCycle(Integer id, String name, String startyear,
			String endyear, Integer creator, Timestamp createtime,
			Short status, Integer centralize, Integer information,
			Integer regional, Integer school, Integer totalhours, String studyhour) {
		super();
		this.id = id;
		this.name = name;
		this.startyear = startyear;
		this.endyear = endyear;
		this.creator = creator;
		this.createtime = createtime;
		this.status = status;
		this.centralize = centralize;
		this.information = information;
		this.regional = regional;
		this.school = school;
		this.totalhours = totalhours;
		this.studyhour = studyhour;
	}

	public ProjectCycle() {
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

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "START_YEAR", nullable = false, length = 4)
	public String getStartyear() {
		return startyear;
	}

	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}

	@Column(name = "END_YERA", nullable = false, length = 4)
	public String getEndyear() {
		return endyear;
	}

	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}

	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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
}
