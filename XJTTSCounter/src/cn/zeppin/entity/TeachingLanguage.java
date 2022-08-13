package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TeachingLanguage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "teaching_language",
	uniqueConstraints = @UniqueConstraint(columnNames = { "TEACHER",
		"LANGUAGE" }))
public class TeachingLanguage implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Language language;
    private Teacher teacher;
    private Boolean isprime;

    // Constructors

    /** default constructor */
    public TeachingLanguage()
    {
    }

    /** full constructor */
    public TeachingLanguage(Language language, Teacher teacher, Boolean isprime)
    {
	this.language = language;
	this.teacher = teacher;
	this.isprime = isprime;
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
    @JoinColumn(name = "LANGUAGE", nullable = false)
    public Language getLanguage()
    {
	return this.language;
    }

    public void setLanguage(Language language)
    {
	this.language = language;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER", nullable = false)
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    @Column(name = "ISPRIME", nullable = false)
    public Boolean getIsprime()
    {
	return this.isprime;
    }

    public void setIsprime(Boolean isprime)
    {
	this.isprime = isprime;
    }

}