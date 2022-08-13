package com.zeppin.entiey;

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
 * Classmap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "classmap",
	catalog = "cac",
	uniqueConstraints = @UniqueConstraint(columnNames = { "fk_studenteId",
		"fk_courseDesignId" }))
public class Classmap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Student student;
    private Coursedesign coursedesign;
    private boolean isComplete;
    private float score;

    // Constructors

    /** default constructor */
    public Classmap()
    {
    }

    /** minimal constructor */
    public Classmap(Student student, Coursedesign coursedesign,
	    boolean isComplete)
    {
	this.student = student;
	this.coursedesign = coursedesign;
	this.isComplete = isComplete;
    }

    /** full constructor */
    public Classmap(Student student, Coursedesign coursedesign,
	    boolean isComplete, float score)
    {
	this.student = student;
	this.coursedesign = coursedesign;
	this.isComplete = isComplete;
	this.score = score;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_studenteId", nullable = false)
    public Student getStudent()
    {
	return this.student;
    }

    public void setStudent(Student student)
    {
	this.student = student;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_courseDesignId", nullable = false)
    public Coursedesign getCoursedesign()
    {
	return this.coursedesign;
    }

    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    @Column(name = "isComplete", nullable = false)
    public boolean getIsComplete()
    {
	return this.isComplete;
    }

    public void setIsComplete(boolean isComplete)
    {
	this.isComplete = isComplete;
    }

    @Column(name = "score", precision = 12, scale = 0)
    public float getScore()
    {
	return this.score;
    }

    public void setScore(float score)
    {
	this.score = score;
    }

}