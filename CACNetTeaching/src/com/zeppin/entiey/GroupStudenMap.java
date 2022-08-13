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
 * GroupStudenMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "group_studen_map",
	catalog = "cac",
	uniqueConstraints = @UniqueConstraint(columnNames = { "fk_group",
		"fk_student" }))
public class GroupStudenMap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Studentgrou studentgrou;
    private Student student;

    // Constructors

    /** default constructor */
    public GroupStudenMap()
    {
    }

    /** full constructor */
    public GroupStudenMap(Studentgrou studentgrou, Student student)
    {
	this.studentgrou = studentgrou;
	this.student = student;
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
    @JoinColumn(name = "fk_group", nullable = false)
    public Studentgrou getStudentgrou()
    {
	return this.studentgrou;
    }

    public void setStudentgrou(Studentgrou studentgrou)
    {
	this.studentgrou = studentgrou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_student", nullable = false)
    public Student getStudent()
    {
	return this.student;
    }

    public void setStudent(Student student)
    {
	this.student = student;
    }

}