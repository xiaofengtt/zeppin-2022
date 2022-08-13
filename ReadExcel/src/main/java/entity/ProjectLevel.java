package entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ProjectLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_level", catalog = "xjtts")
public class ProjectLevel implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Short level;
    private Set<ProjectType> projectTypes = new HashSet<ProjectType>(0);
    private Set<TrainingCollege> trainingColleges = new HashSet<TrainingCollege>(
	    0);

    // Constructors

    /** default constructor */
    public ProjectLevel()
    {
    }

    /** minimal constructor */
    public ProjectLevel(String name, Short level)
    {
	this.name = name;
	this.level = level;
    }

    /** full constructor */
    public ProjectLevel(String name, Short level,
	    Set<ProjectType> projectTypes, Set<TrainingCollege> trainingColleges)
    {
	this.name = name;
	this.level = level;
	this.projectTypes = projectTypes;
	this.trainingColleges = trainingColleges;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Short getId()
    {
	return this.id;
    }

    public void setId(Short id)
    {
	this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 80)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
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

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "projectLevel")
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
	    mappedBy = "projectLevel")
    public Set<TrainingCollege> getTrainingColleges()
    {
	return this.trainingColleges;
    }

    public void setTrainingColleges(Set<TrainingCollege> trainingColleges)
    {
	this.trainingColleges = trainingColleges;
    }

}