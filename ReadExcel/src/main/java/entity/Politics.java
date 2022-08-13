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
 * Politics entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "politics", catalog = "xjtts")
public class Politics implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);
    private Set<ProjectAdmin> projectAdmins = new HashSet<ProjectAdmin>(0);
    private Set<TrainingAdmin> trainingAdmins = new HashSet<TrainingAdmin>(0);
    private Set<ProjectExpert> projectExperts = new HashSet<ProjectExpert>(0);

    // Constructors

    /** default constructor */
    public Politics()
    {
    }

    /** minimal constructor */
    public Politics(String name)
    {
	this.name = name;
    }

    /** full constructor */
    public Politics(String name, Set<Teacher> teachers,
	    Set<ProjectAdmin> projectAdmins, Set<TrainingAdmin> trainingAdmins,
	    Set<ProjectExpert> projectExperts)
    {
	this.name = name;
	this.teachers = teachers;
	this.projectAdmins = projectAdmins;
	this.trainingAdmins = trainingAdmins;
	this.projectExperts = projectExperts;
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

    @Column(name = "NAME", nullable = false, length = 20)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "politics")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "politics")
    public Set<ProjectAdmin> getProjectAdmins()
    {
	return this.projectAdmins;
    }

    public void setProjectAdmins(Set<ProjectAdmin> projectAdmins)
    {
	this.projectAdmins = projectAdmins;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "politics")
    public Set<TrainingAdmin> getTrainingAdmins()
    {
	return this.trainingAdmins;
    }

    public void setTrainingAdmins(Set<TrainingAdmin> trainingAdmins)
    {
	this.trainingAdmins = trainingAdmins;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "politics")
    public Set<ProjectExpert> getProjectExperts()
    {
	return this.projectExperts;
    }

    public void setProjectExperts(Set<ProjectExpert> projectExperts)
    {
	this.projectExperts = projectExperts;
    }

}