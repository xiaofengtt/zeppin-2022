package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ProjectApplyWorkReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "project_apply_work_report",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT_APPLY",
		"DOCUMENT" }))
public class ProjectApplyWorkReport implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private ProjectApply projectApply;
    private Document document;

    // Constructors

    /** default constructor */
    public ProjectApplyWorkReport()
    {
    }

    /** full constructor */
    public ProjectApplyWorkReport(ProjectApply projectApply, Document document)
    {
	this.projectApply = projectApply;
	this.document = document;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_APPLY", nullable = false)
    public ProjectApply getProjectApply()
    {
	return this.projectApply;
    }

    public void setProjectApply(ProjectApply projectApply)
    {
	this.projectApply = projectApply;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCUMENT", nullable = false)
    public Document getDocument()
    {
	return this.document;
    }

    public void setDocument(Document document)
    {
	this.document = document;
    }

}