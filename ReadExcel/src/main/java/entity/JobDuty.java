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
 * JobDuty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "job_duty", catalog = "xjtts")
public class JobDuty implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    // Constructors

    /** default constructor */
    public JobDuty()
    {
    }

    /** minimal constructor */
    public JobDuty(String name)
    {
	this.name = name;
    }

    /** full constructor */
    public JobDuty(String name, Set<Teacher> teachers)
    {
	this.name = name;
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

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "jobDuty")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

}