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
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject", catalog = "cac")
public class Subject implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacherByFkModifierId;
    private Teacher teacherByFkCreatorId;
    private String code;
    private String name;
    private String enName;
    private boolean isValue;
    private Date createDate;
    private Date modifiDate;
    private Set<Coursedesign> coursedesigns = new HashSet<Coursedesign>(0);

    // Constructors

    /** default constructor */
    public Subject()
    {
    }

    /** minimal constructor */
    public Subject(String code, String name, boolean isValue, Date createDate)
    {
	this.code = code;
	this.name = name;
	this.isValue = isValue;
	this.createDate = createDate;
    }

    /** full constructor */
    public Subject(Teacher teacherByFkModifierId, Teacher teacherByFkCreatorId,
	    String code, String name, String enName, boolean isValue,
	    Date createDate, Date modifiDate, Set<Coursedesign> coursedesigns)
    {
	this.teacherByFkModifierId = teacherByFkModifierId;
	this.teacherByFkCreatorId = teacherByFkCreatorId;
	this.code = code;
	this.name = name;
	this.enName = enName;
	this.isValue = isValue;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
	this.coursedesigns = coursedesigns;
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

    @Column(name = "code", nullable = false, length = 30)
    public String getCode()
    {
	return this.code;
    }

    public void setCode(String code)
    {
	this.code = code;
    }

    @Column(name = "name", nullable = false, length = 30)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "enName", length = 50)
    public String getEnName()
    {
	return this.enName;
    }

    public void setEnName(String enName)
    {
	this.enName = enName;
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
	    mappedBy = "subject")
    public Set<Coursedesign> getCoursedesigns()
    {
	return this.coursedesigns;
    }

    public void setCoursedesigns(Set<Coursedesign> coursedesigns)
    {
	this.coursedesigns = coursedesigns;
    }

}