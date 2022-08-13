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

/**
 * CourseCoursewareMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "course_courseware_map", catalog = "cac")
public class CourseCoursewareMap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacher;
    private Coursedesign coursedesign;
    private Accessory accessory;

    // Constructors

    /** default constructor */
    public CourseCoursewareMap()
    {
    }

    /** full constructor */
    public CourseCoursewareMap(Teacher teacher, Coursedesign coursedesign,
	    Accessory accessory)
    {
	this.teacher = teacher;
	this.coursedesign = coursedesign;
	this.accessory = accessory;
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
    @JoinColumn(name = "fk_courseId", nullable = false)
    public Coursedesign getCoursedesign()
    {
	return this.coursedesign;
    }

    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_accessoryId", nullable = false)
    public Accessory getAccessory()
    {
	return this.accessory;
    }

    public void setAccessory(Accessory accessory)
    {
	this.accessory = accessory;
    }

}