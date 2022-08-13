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
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject", catalog = "xjtts")
public class Subject implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Set<TeachingSubject> teachingSubjects = new HashSet<TeachingSubject>(
	    0);

    // Constructors

    /** default constructor */
    public Subject()
    {
    }

    /** minimal constructor */
    public Subject(String name)
    {
	this.name = name;
    }

    /** full constructor */
    public Subject(String name, Set<TeachingSubject> teachingSubjects)
    {
	this.name = name;
	this.teachingSubjects = teachingSubjects;
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
	    mappedBy = "subject")
    public Set<TeachingSubject> getTeachingSubjects()
    {
	return this.teachingSubjects;
    }

    public void setTeachingSubjects(Set<TeachingSubject> teachingSubjects)
    {
	this.teachingSubjects = teachingSubjects;
    }

}