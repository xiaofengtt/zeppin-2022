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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Studentgrou entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "studentgrou", catalog = "cac")
public class Studentgrou implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Coursedesign coursedesign;
    private Teacher teacher;
    private String groupName;
    private Set<GroupStudenMap> groupStudenMaps = new HashSet<GroupStudenMap>(0);

    // Constructors

    /** default constructor */
    public Studentgrou()
    {
    }

    /** minimal constructor */
    public Studentgrou(Coursedesign coursedesign, String groupName)
    {
	this.coursedesign = coursedesign;
	this.groupName = groupName;
    }

    /** full constructor */
    public Studentgrou(Coursedesign coursedesign, Teacher teacher,
	    String groupName, Set<GroupStudenMap> groupStudenMaps)
    {
	this.coursedesign = coursedesign;
	this.teacher = teacher;
	this.groupName = groupName;
	this.groupStudenMaps = groupStudenMaps;
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
    @JoinColumn(name = "fk_crousePlan", nullable = false)
    public Coursedesign getCoursedesign()
    {
	return this.coursedesign;
    }

    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_teacher")
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    @Column(name = "groupName", nullable = false, length = 50)
    public String getGroupName()
    {
	return this.groupName;
    }

    public void setGroupName(String groupName)
    {
	this.groupName = groupName;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "studentgrou")
    public Set<GroupStudenMap> getGroupStudenMaps()
    {
	return this.groupStudenMaps;
    }

    public void setGroupStudenMaps(Set<GroupStudenMap> groupStudenMaps)
    {
	this.groupStudenMaps = groupStudenMaps;
    }

}