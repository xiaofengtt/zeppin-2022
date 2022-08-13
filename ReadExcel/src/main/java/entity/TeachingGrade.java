package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TeachingGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "teaching_grade",
	catalog = "xjtts",
	uniqueConstraints = @UniqueConstraint(columnNames = { "TEACHER",
		"GRADE" }))
public class TeachingGrade implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Grade grade;
    private Teacher teacher;
    private Boolean isprime;

    // Constructors

    /** default constructor */
    public TeachingGrade()
    {
    }

    /** full constructor */
    public TeachingGrade(Grade grade, Teacher teacher, Boolean isprime)
    {
	this.grade = grade;
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
    @JoinColumn(name = "GRADE", nullable = false)
    public Grade getGrade()
    {
	return this.grade;
    }

    public void setGrade(Grade grade)
    {
	this.grade = grade;
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