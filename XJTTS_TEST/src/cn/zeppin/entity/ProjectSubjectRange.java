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
import javax.persistence.UniqueConstraint;

/**
 * ProjectSubjectRange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "project_subject_range",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT",
		"SUBJECT" }))
public class ProjectSubjectRange implements java.io.Serializable
{

    // Fields

    private Integer id;
    private TrainingSubject trainingSubject;
    private Project project;
    private Integer creator;
    private Timestamp creattime;

    // Constructors

    /** default constructor */
    public ProjectSubjectRange()
    {
    }

    /** full constructor */
    public ProjectSubjectRange(TrainingSubject trainingSubject,
	    Project project, Integer creator, Timestamp creattime)
    {
	this.trainingSubject = trainingSubject;
	this.project = project;
	this.creator = creator;
	this.creattime = creattime;
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
    @JoinColumn(name = "SUBJECT", nullable = false)
    public TrainingSubject getTrainingSubject()
    {
	return this.trainingSubject;
    }

    public void setTrainingSubject(TrainingSubject trainingSubject)
    {
	this.trainingSubject = trainingSubject;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT", nullable = false)
    public Project getProject()
    {
	return this.project;
    }

    public void setProject(Project project)
    {
	this.project = project;
    }

    @Column(name = "CREATOR", nullable = false)
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

}