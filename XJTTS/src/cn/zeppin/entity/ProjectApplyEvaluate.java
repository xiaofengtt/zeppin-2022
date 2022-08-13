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
 * ProjectApplyExpert entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "project_apply_evaluate",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT_APPLY",
			"PROJECT_EXPERT" }))
public class ProjectApplyEvaluate implements java.io.Serializable {
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ProjectApply projectApply;
    private ProjectExpert projectExpert;
    private Short status;
	// Constructors

	/** default constructor */
	public ProjectApplyEvaluate() {
	}

	/** full constructor */
	public ProjectApplyEvaluate(ProjectExpert projectExpert,ProjectApply projectApply,Short status) {
		this.projectApply=projectApply;
		this.projectExpert=projectExpert;
		this.status=status;
	}
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_EXPERT", nullable = false)
    public ProjectExpert getProjectExpert()
    {
	return this.projectExpert;
    }

    public void setProjectExpert(ProjectExpert projectExpert)
    {
	this.projectExpert = projectExpert;
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
}
