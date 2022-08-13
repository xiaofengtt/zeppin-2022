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
 * DicAcademy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dic_academy", catalog = "cac")
public class DicAcademy implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacherByFkModiferId;
    private Teacher teacherByFkCreaterId;
    private String name;
    private String enName;
    private Integer parentId;
    private boolean isValue;
    private Date createDate;
    private Date modifiDate;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);
    private Set<DicMajor> dicMajors = new HashSet<DicMajor>(0);
    private Set<Coursedesign> coursedesigns = new HashSet<Coursedesign>(0);

    // Constructors

    /** default constructor */
    public DicAcademy()
    {
    }

    /** minimal constructor */
    public DicAcademy(String name, Integer parentId, boolean isValue,
	    Date createDate)
    {
	this.name = name;
	this.parentId = parentId;
	this.isValue = isValue;
	this.createDate = createDate;
    }

    /** full constructor */
    public DicAcademy(Teacher teacherByFkModiferId,
	    Teacher teacherByFkCreaterId, String name, String enName,
	    Integer parentId, boolean isValue, Date createDate,
	    Date modifiDate, Set<Teacher> teachers, Set<DicMajor> dicMajors,
	    Set<Coursedesign> coursedesigns)
    {
	this.teacherByFkModiferId = teacherByFkModiferId;
	this.teacherByFkCreaterId = teacherByFkCreaterId;
	this.name = name;
	this.enName = enName;
	this.parentId = parentId;
	this.isValue = isValue;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
	this.teachers = teachers;
	this.dicMajors = dicMajors;
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
    @JoinColumn(name = "fk_modiferId")
    public Teacher getTeacherByFkModiferId()
    {
	return this.teacherByFkModiferId;
    }

    public void setTeacherByFkModiferId(Teacher teacherByFkModiferId)
    {
	this.teacherByFkModiferId = teacherByFkModiferId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_createrId")
    public Teacher getTeacherByFkCreaterId()
    {
	return this.teacherByFkCreaterId;
    }

    public void setTeacherByFkCreaterId(Teacher teacherByFkCreaterId)
    {
	this.teacherByFkCreaterId = teacherByFkCreaterId;
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

    @Column(name = "en_name", length = 100)
    public String getEnName()
    {
	return this.enName;
    }

    public void setEnName(String enName)
    {
	this.enName = enName;
    }

    @Column(name = "parent_id", nullable = false)
    public Integer getParentId()
    {
	return this.parentId;
    }

    public void setParentId(Integer parentId)
    {
	this.parentId = parentId;
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
	    mappedBy = "dicAcademy")
    public Set<Teacher> getTeachers()
    {
	return this.teachers;
    }

    public void setTeachers(Set<Teacher> teachers)
    {
	this.teachers = teachers;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "dicAcademy")
    public Set<DicMajor> getDicMajors()
    {
	return this.dicMajors;
    }

    public void setDicMajors(Set<DicMajor> dicMajors)
    {
	this.dicMajors = dicMajors;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "dicAcademy")
    public Set<Coursedesign> getCoursedesigns()
    {
	return this.coursedesigns;
    }

    public void setCoursedesigns(Set<Coursedesign> coursedesigns)
    {
	this.coursedesigns = coursedesigns;
    }

}