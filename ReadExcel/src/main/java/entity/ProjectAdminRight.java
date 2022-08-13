package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ProjectAdminRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "project_admin_right",
	catalog = "xjtts",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT_ADMIN",
		"PROJECT_TYPE" }))
public class ProjectAdminRight implements java.io.Serializable
{

    // Fields

    private Integer id;
    private ProjectAdmin projectAdmin;
    private ProjectType projectType;

    // Constructors

    /** default constructor */
    public ProjectAdminRight()
    {
    }

    /** full constructor */
    public ProjectAdminRight(ProjectAdmin projectAdmin, ProjectType projectType)
    {
	this.projectAdmin = projectAdmin;
	this.projectType = projectType;
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
    @JoinColumn(name = "PROJECT_ADMIN", nullable = false)
    public ProjectAdmin getProjectAdmin()
    {
	return this.projectAdmin;
    }

    public void setProjectAdmin(ProjectAdmin projectAdmin)
    {
	this.projectAdmin = projectAdmin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_TYPE", nullable = false)
    public ProjectType getProjectType()
    {
	return this.projectType;
    }

    public void setProjectType(ProjectType projectType)
    {
	this.projectType = projectType;
    }

}