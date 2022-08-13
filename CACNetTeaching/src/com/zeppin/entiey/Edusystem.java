package com.zeppin.entiey;

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
 * Edusystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "edusystem", catalog = "cac")
public class Edusystem implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String name;
    private float year;
    private boolean isValue;
    private Set<Student> students = new HashSet<Student>(0);

    // Constructors

    /** default constructor */
    public Edusystem()
    {
    }

    /** minimal constructor */
    public Edusystem(boolean isValue)
    {
	this.isValue = isValue;
    }

    /** full constructor */
    public Edusystem(String name, float year, boolean isValue,
	    Set<Student> students)
    {
	this.name = name;
	this.year = year;
	this.isValue = isValue;
	this.students = students;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(name = "name", length = 20)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "year", precision = 12, scale = 0)
    public float getYear()
    {
	return this.year;
    }

    public void setYear(float year)
    {
	this.year = year;
    }

    @Column(name = "isValue", nullable = false)
    public boolean getIsValue()
    {
	return this.isValue;
    }

    public void setIsValue(boolean isValue)
    {
	this.isValue = isValue;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "edusystem")
    public Set<Student> getStudents()
    {
	return this.students;
    }

    public void setStudents(Set<Student> students)
    {
	this.students = students;
    }

}