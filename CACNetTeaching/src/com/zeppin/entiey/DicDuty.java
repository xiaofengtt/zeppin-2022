package com.zeppin.entiey;

import java.util.Date;
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
 * DicDuty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dic_duty", catalog = "cac")
public class DicDuty implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacherByFkModifierId;
    private Teacher teacherByFkCreatorId;
    private String name;
    private boolean isValue;
    private Date createDate;
    private Date modifiDate;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    // Constructors

    /** default constructor */
    public DicDuty()
    {
    }

    /** minimal constructor */
    public DicDuty(String name, boolean isValue, Date createDate)
    {
	this.name = name;
	this.isValue = isValue;
	this.createDate = createDate;
    }

    /** full constructor */
    public DicDuty(Teacher teacherByFkModifierId, Teacher teacherByFkCreatorId,
	    String name, boolean isValue, Date createDate, Date modifiDate,
	    Set<Teacher> teachers)
    {
	this.teacherByFkModifierId = teacherByFkModifierId;
	this.teacherByFkCreatorId = teacherByFkCreatorId;
	this.name = name;
	this.isValue = isValue;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
	this.teachers = teachers;
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
    @JoinColumn(name = "fk_modifierId")
    public Teacher getTeacherByFkModifierId()
    {
	return this.teacherByFkModifierId;
    }

    public void setTeacherByFkModifierId(Teacher teacherByFkModifierId)
    {
	this.teacherByFkModifierId = teacherByFkModifierId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_creatorId")
    public Teacher getTeacherByFkCreatorId()
    {
	return this.teacherByFkCreatorId;
    }

    public void setTeacherByFkCreatorId(Teacher teacherByFkCreatorId)
    {
	this.teacherByFkCreatorId = teacherByFkCreatorId;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
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

    @Column(name = "createDate", nullable = false, length = 19)
    public Date getCreateDate()
    {
	return this.createDate;
    }

    public void setCreateDate(Date createDate)
    {
	this.createDate = createDate;
    }

    @Column(name = "modifiDate", length = 19)
    public Date getModifiDate()
    {
	return this.modifiDate;
    }

    public void setModifiDate(Date modifiDate)
    {
	this.modifiDate = modifiDate;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "dicDuty")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

}