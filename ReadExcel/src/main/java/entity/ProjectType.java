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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ProjectType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_type", catalog = "xjtts")
public class ProjectType implements java.io.Serializable
{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6211131762269449093L;
	private Integer id;
	private String scode;
	private Area area;
	private ProjectType projectType;
	private ProjectLevel projectLevel;
	private String name;
	private String shortname;
	private Short level;
	private Short status;
	private Integer creator;
	private Timestamp creattime;
	private Set<ProjectType> projectTypes = new HashSet<ProjectType>(0);
	private Set<ProjectAdminRight> projectAdminRights = new HashSet<ProjectAdminRight>(
			0);
	private Set<Project> projects = new HashSet<Project>(0);

	// Constructors

	/** default constructor */
	public ProjectType()
	{
	}

	/** minimal constructor */
	public ProjectType(String scode, Area area, ProjectLevel projectLevel, String name,
			String shortname, Short level, Short status, Timestamp creattime)
	{
		this.scode = scode;
		this.area = area;
		this.projectLevel = projectLevel;
		this.name = name;
		this.shortname = shortname;
		this.level = level;
		this.status = status;
		this.creattime = creattime;
	}

	/** full constructor */
	public ProjectType(String scode, Area area, ProjectType projectType,
			ProjectLevel projectLevel, String name, String shortname,
			Short level, Short status, Integer creator, Timestamp creattime,
			Set<ProjectType> projectTypes,
			Set<ProjectAdminRight> projectAdminRights, Set<Project> projects)
	{
		this.scode = scode;
		this.area = area;
		this.projectType = projectType;
		this.projectLevel = projectLevel;
		this.name = name;
		this.shortname = shortname;
		this.level = level;
		this.status = status;
		this.creator = creator;
		this.creattime = creattime;
		this.projectTypes = projectTypes;
		this.projectAdminRights = projectAdminRights;
		this.projects = projects;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	@Column(name = "SCODE", length = 100)
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA", nullable = false)
	public Area getArea()
	{
		return this.area;
	}

	public void setArea(Area area)
	{
		this.area = area;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public ProjectType getProjectType()
	{
		return this.projectType;
	}

	public void setProjectType(ProjectType projectType)
	{
		this.projectType = projectType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_LEVEL", nullable = false)
	public ProjectLevel getProjectLevel()
	{
		return this.projectLevel;
	}

	public void setProjectLevel(ProjectLevel projectLevel)
	{
		this.projectLevel = projectLevel;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 200)
	public String getShortname()
	{
		return this.shortname;
	}

	public void setShortname(String shortname)
	{
		this.shortname = shortname;
	}

	@Column(name = "LEVEL", nullable = false)
	public Short getLevel()
	{
		return this.level;
	}

	public void setLevel(Short level)
	{
		this.level = level;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus()
	{
		return this.status;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}

	@Column(name = "CREATOR")
	public Integer getCreator()
	{
		return this.creator;
	}

	public void setCreator(Integer creator)
	{
		this.creator = creator;
	}

	@Column(name = "CREATTIME", nullable = false, length = 19)
	public Timestamp getCreattime()
	{
		return this.creattime;
	}

	public void setCreattime(Timestamp creattime)
	{
		this.creattime = creattime;
	}

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "projectType")
	public Set<ProjectType> getProjectTypes()
	{
		return this.projectTypes;
	}

	public void setProjectTypes(Set<ProjectType> projectTypes)
	{
		this.projectTypes = projectTypes;
	}

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "projectType")
	public Set<ProjectAdminRight> getProjectAdminRights()
	{
		return this.projectAdminRights;
	}

	public void setProjectAdminRights(Set<ProjectAdminRight> projectAdminRights)
	{
		this.projectAdminRights = projectAdminRights;
	}

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "projectType")
	public Set<Project> getProjects()
	{
		return this.projects;
	}

	public void setProjects(Set<Project> projects)
	{
		this.projects = projects;
	}

}