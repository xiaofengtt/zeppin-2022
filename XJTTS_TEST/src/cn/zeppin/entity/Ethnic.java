package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
 * Ethnic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ethnic")
public class Ethnic implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Short weight;
    private Set<TrainingAdmin> trainingAdmins = new HashSet<TrainingAdmin>(0);
    private Set<Teachexpert> teachexperts = new HashSet<Teachexpert>(0);
    private Set<ProjectAdmin> projectAdmins = new HashSet<ProjectAdmin>(0);
    private Set<ProjectExpert> projectExperts = new HashSet<ProjectExpert>(0);
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    // Constructors

    /** default constructor */
    public Ethnic()
    {
    }

    /** minimal constructor */
    public Ethnic(String name, Short weight)
    {
	this.name = name;
	this.weight = weight;
    }

    /** full constructor */
    public Ethnic(String name, Short weight, Set<TrainingAdmin> trainingAdmins,
	    Set<Teachexpert> teachexperts, Set<ProjectAdmin> projectAdmins,
	    Set<ProjectExpert> projectExperts, Set<Teacher> teachers)
    {
	this.name = name;
	this.weight = weight;
	this.trainingAdmins = trainingAdmins;
	this.teachexperts = teachexperts;
	this.projectAdmins = projectAdmins;
	this.projectExperts = projectExperts;
	this.teachers = teachers;
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

    @Column(name = "NAME", nullable = false, length = 50)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "WEIGHT", nullable = false)
    public Short getWeight()
    {
	return this.weight;
    }

    public void setWeight(Short weight)
    {
	this.weight = weight;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "ethnic")
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
	    mappedBy = "ethnic")
    public Set<Teachexpert> getTeachexperts()
    {
	return this.teachexperts;
    }

    public void setTeachexperts(Set<Teachexpert> teachexperts)
    {
	this.teachexperts = teachexperts;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "ethnic")
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
	    mappedBy = "ethnic")
    public Set<ProjectExpert> getProjectExperts()
    {
	return this.projectExperts;
    }

    public void setProjectExperts(Set<ProjectExpert> projectExperts)
    {
	this.projectExperts = projectExperts;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "ethnic")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

}