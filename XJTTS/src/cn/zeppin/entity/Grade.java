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
 * Grade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "grade")
public class Grade implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Set<TeachingGrade> teachingGrades = new HashSet<TeachingGrade>(0);
//    private Set<Organization> organizations = new HashSet<Organization>(0);
    private Boolean isSchool;

    // Constructors

    /** default constructor */
    public Grade()
    {
    }

    /** minimal constructor */
    public Grade(String name)
    {
	this.name = name;
    }

    /** full constructor */
    public Grade(String name, Set<TeachingGrade> teachingGrades)
    {
	this.name = name;
	this.teachingGrades = teachingGrades;
//	this.organizations = organizations;
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

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "grade")
    public Set<TeachingGrade> getTeachingGrades()
    {
	return this.teachingGrades;
    }

    public void setTeachingGrades(Set<TeachingGrade> teachingGrades)
    {
	this.teachingGrades = teachingGrades;
    }

//    @OneToMany(
//	    cascade = CascadeType.ALL,
//	    fetch = FetchType.LAZY,
//	    mappedBy = "grade")
//    public Set<Organization> getOrganizations()
//    {
//	return this.organizations;
//    }
//
//    public void setOrganizations(Set<Organization> organizations)
//    {
//	this.organizations = organizations;
//    }

    @Column(name = "ISSCHOOL")
	public Boolean getIsSchool() {
		return isSchool;
	}

	public void setIsSchool(Boolean isSchool) {
		this.isSchool = isSchool;
	}

}