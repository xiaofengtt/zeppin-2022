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
 * CoursedesignTeacherMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "coursedesign_teacher_map",
	catalog = "cac",
	uniqueConstraints = @UniqueConstraint(columnNames = {
		"fk_courseDesignId", "fk_teacherId" }))
public class CoursedesignTeacherMap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacher;
    private Coursedesign coursedesign;
    private boolean isLeader;

    // Constructors

    /** default constructor */
    public CoursedesignTeacherMap()
    {
    }

    /** full constructor */
    public CoursedesignTeacherMap(Teacher teacher, Coursedesign coursedesign,
	    boolean isLeader)
    {
	this.teacher = teacher;
	this.coursedesign = coursedesign;
	this.isLeader = isLeader;
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
    @JoinColumn(name = "fk_teacherId", nullable = false)
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
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

    @Column(name = "isLeader", nullable = false)
    public boolean getIsLeader()
    {
	return this.isLeader;
    }

    public void setIsLeader(boolean isLeader)
    {
	this.isLeader = isLeader;
    }

}