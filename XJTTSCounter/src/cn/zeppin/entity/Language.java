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
 * Language entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "language")
public class Language implements java.io.Serializable
{

    // Fields

    private Short id;
    private String name;
    private Short weight;
    private Set<TeachingLanguage> teachingLanguages = new HashSet<TeachingLanguage>(
	    0);

    // Constructors

    /** default constructor */
    public Language()
    {
    }

    /** minimal constructor */
    public Language(String name, Short weight)
    {
	this.name = name;
	this.weight = weight;
    }

    /** full constructor */
    public Language(String name, Short weight,
	    Set<TeachingLanguage> teachingLanguages)
    {
	this.name = name;
	this.weight = weight;
	this.teachingLanguages = teachingLanguages;
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
	    mappedBy = "language")
    public Set<TeachingLanguage> getTeachingLanguages()
    {
	return this.teachingLanguages;
    }

    public void setTeachingLanguages(Set<TeachingLanguage> teachingLanguages)
    {
	this.teachingLanguages = teachingLanguages;
    }

}