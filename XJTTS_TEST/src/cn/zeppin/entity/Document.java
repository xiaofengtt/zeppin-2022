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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Document entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "document")
public class Document implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Short type;
    private String title;
    private String name;
    private Short resourceType;
    private String resourcePath;
    private String resourceUrl;
    private Integer size;
    private Integer creater;
    private Timestamp createtime;
    private Set<Project> projects = new HashSet<Project>(0);
    private Set<ProjectApply> projectAppliesForStartMessage = new HashSet<ProjectApply>(
	    0);
    private Set<ProjectApply> projectAppliesForProformanceReport = new HashSet<ProjectApply>(
	    0);
    private Set<ProjectApply> projectAppliesForWorkReport = new HashSet<ProjectApply>(
	    0);
    private Set<ProjectApply> projectAppliesForProjectApplyDocument = new HashSet<ProjectApply>(
	    0);

    // Constructors

    /** default constructor */
    public Document()
    {
    }

    /** minimal constructor */
    public Document(Short type, String title, String name, Short resourceType,
	    Integer creater, Timestamp createtime)
    {
	this.type = type;
	this.title = title;
	this.name = name;
	this.resourceType = resourceType;
	this.creater = creater;
	this.createtime = createtime;
    }

    /** full constructor */
    public Document(Short type, String title, String name, Short resourceType,
	    String resourcePath, String resourceUrl, Integer size,
	    Integer creater, Timestamp createtime, Set<Project> projects,
	    Set<ProjectApply> projectAppliesForStartMessage,
	    Set<ProjectApply> projectAppliesForProformanceReport,
	    Set<ProjectApply> projectAppliesForWorkReport,
	    Set<ProjectApply> projectAppliesForProjectApplyDocument)
    {
	this.type = type;
	this.title = title;
	this.name = name;
	this.resourceType = resourceType;
	this.resourcePath = resourcePath;
	this.resourceUrl = resourceUrl;
	this.size = size;
	this.creater = creater;
	this.createtime = createtime;
	this.projects = projects;
	this.projectAppliesForStartMessage = projectAppliesForStartMessage;
	this.projectAppliesForProformanceReport = projectAppliesForProformanceReport;
	this.projectAppliesForWorkReport = projectAppliesForWorkReport;
	this.projectAppliesForProjectApplyDocument = projectAppliesForProjectApplyDocument;
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

    @Column(name = "TYPE", nullable = false)
    public Short getType()
    {
	return this.type;
    }

    public void setType(Short type)
    {
	this.type = type;
    }

    @Column(name = "TITLE", nullable = false, length = 200)
    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
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

    @Column(name = "RESOURCE_TYPE", nullable = false)
    public Short getResourceType()
    {
	return this.resourceType;
    }

    public void setResourceType(Short resourceType)
    {
	this.resourceType = resourceType;
    }

    @Column(name = "RESOURCE_PATH", length = 500)
    public String getResourcePath()
    {
	return this.resourcePath;
    }

    public void setResourcePath(String resourcePath)
    {
	this.resourcePath = resourcePath;
    }

    @Column(name = "RESOURCE_URL", length = 500)
    public String getResourceUrl()
    {
	return this.resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
	this.resourceUrl = resourceUrl;
    }

    @Column(name = "SIZE")
    public Integer getSize()
    {
	return this.size;
    }

    public void setSize(Integer size)
    {
	this.size = size;
    }

    @Column(name = "CREATER", nullable = false)
    public Integer getCreater()
    {
	return this.creater;
    }

    public void setCreater(Integer creater)
    {
	this.creater = creater;
    }

    @Column(name = "CREATETIME", nullable = false, length = 19)
    public Timestamp getCreatetime()
    {
	return this.createtime;
    }

    public void setCreatetime(Timestamp createtime)
    {
	this.createtime = createtime;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "document")
    public Set<Project> getProjects()
    {
	return this.projects;
    }

    public void setProjects(Set<Project> projects)
    {
	this.projects = projects;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "documentByStartMessage")
    public Set<ProjectApply> getProjectAppliesForStartMessage()
    {
	return this.projectAppliesForStartMessage;
    }

    public void setProjectAppliesForStartMessage(
	    Set<ProjectApply> projectAppliesForStartMessage)
    {
	this.projectAppliesForStartMessage = projectAppliesForStartMessage;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "documentByProformanceReport")
    public Set<ProjectApply> getProjectAppliesForProformanceReport()
    {
	return this.projectAppliesForProformanceReport;
    }

    public void setProjectAppliesForProformanceReport(
	    Set<ProjectApply> projectAppliesForProformanceReport)
    {
	this.projectAppliesForProformanceReport = projectAppliesForProformanceReport;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "documentByWorkReport")
    public Set<ProjectApply> getProjectAppliesForWorkReport()
    {
	return this.projectAppliesForWorkReport;
    }

    public void setProjectAppliesForWorkReport(
	    Set<ProjectApply> projectAppliesForWorkReport)
    {
	this.projectAppliesForWorkReport = projectAppliesForWorkReport;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "documentByProjectApplyDocument")
    public Set<ProjectApply> getProjectAppliesForProjectApplyDocument()
    {
	return this.projectAppliesForProjectApplyDocument;
    }

    public void setProjectAppliesForProjectApplyDocument(
	    Set<ProjectApply> projectAppliesForProjectApplyDocument)
    {
	this.projectAppliesForProjectApplyDocument = projectAppliesForProjectApplyDocument;
    }

}