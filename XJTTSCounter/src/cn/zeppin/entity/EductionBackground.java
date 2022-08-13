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
 * EductionBackground entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eduction_background")
public class EductionBackground implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Short level;
    private Set<Teachexpert> teachexperts = new HashSet<Teachexpert>(0);
    private Set<ProjectExpert> projectExperts = new HashSet<ProjectExpert>(0);
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    // Constructors

    /** default constructor */
    public EductionBackground()
    {
    }

    /** minimal constructor */
    public EductionBackground(String name)
    {
	this.name = name;
    }

    /** full constructor */
    public EductionBackground(String name, Short level,
	    Set<Teachexpert> teachexperts, Set<ProjectExpert> projectExperts,
	    Set<Teacher> teachers)
    {
	this.name = name;
	this.level = level;
	this.teachexperts = teachexperts;
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

    @Column(name = "NAME", nullable = false, length = 30)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "LEVEL")
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
	    mappedBy = "eductionBackground")
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
	    mappedBy = "eductionBackground")
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
	    mappedBy = "eductionBackground")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

}