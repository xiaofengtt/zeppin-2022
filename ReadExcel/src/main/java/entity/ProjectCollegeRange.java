package entity;

import java.sql.Timestamp;
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
 * ProjectCollegeRange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "project_college_range",
	catalog = "xjtts",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT",
		"TRAINING_COLLEGE" }))
public class ProjectCollegeRange implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Project project;
    private TrainingCollege trainingCollege;
    private Integer creator;
    private Timestamp creattime;

    // Constructors

    /** default constructor */
    public ProjectCollegeRange()
    {
    }

    /** full constructor */
    public ProjectCollegeRange(Project project,
	    TrainingCollege trainingCollege, Integer creator,
	    Timestamp creattime)
    {
	this.project = project;
	this.trainingCollege = trainingCollege;
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
    @JoinColumn(name = "PROJECT", nullable = false)
    public Project getProject()
    {
	return this.project;
    }

    public void setProject(Project project)
    {
	this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINING_COLLEGE", nullable = false)
    public TrainingCollege getTrainingCollege()
    {
	return this.trainingCollege;
    }

    public void setTrainingCollege(TrainingCollege trainingCollege)
    {
	this.trainingCollege = trainingCollege;
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