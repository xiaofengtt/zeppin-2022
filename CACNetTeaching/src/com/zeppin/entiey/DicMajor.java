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
 * DicMajor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dic_major", catalog = "cac")
public class DicMajor implements java.io.Serializable
{

    // Fields

    private Integer id;
    private DicAcademy dicAcademy;
    private String name;
    private String enName;
    private boolean isValue;
    private Integer fkCreaterId;
    private Date createDate;
    private Integer fkModifierId;
    private Date modifiDate;
    private Set<Student> students = new HashSet<Student>(0);

    // Constructors

    /** default constructor */
    public DicMajor()
    {
    }

    /** minimal constructor */
    public DicMajor(DicAcademy dicAcademy, String name, boolean isValue,
	    Date createDate)
    {
	this.dicAcademy = dicAcademy;
	this.name = name;
	this.isValue = isValue;
	this.createDate = createDate;
    }

    /** full constructor */
    public DicMajor(DicAcademy dicAcademy, String name, String enName,
	    boolean isValue, Integer fkCreaterId, Date createDate,
	    Integer fkModifierId, Date modifiDate, Set<Student> students)
    {
	this.dicAcademy = dicAcademy;
	this.name = name;
	this.enName = enName;
	this.isValue = isValue;
	this.fkCreaterId = fkCreaterId;
	this.createDate = createDate;
	this.fkModifierId = fkModifierId;
	this.modifiDate = modifiDate;
	this.students = students;
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
    @JoinColumn(name = "fk_academy", nullable = false)
    public DicAcademy getDicAcademy()
    {
	return this.dicAcademy;
    }

    public void setDicAcademy(DicAcademy dicAcademy)
    {
	this.dicAcademy = dicAcademy;
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

    @Column(name = "isValue", nullable = false)
    public boolean getIsValue()
    {
	return this.isValue;
    }

    public void setIsValue(boolean isValue)
    {
	this.isValue = isValue;
    }

    @Column(name = "fk_createrId")
    public Integer getFkCreaterId()
    {
	return this.fkCreaterId;
    }

    public void setFkCreaterId(Integer fkCreaterId)
    {
	this.fkCreaterId = fkCreaterId;
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

    @Column(name = "fk_modifierId")
    public Integer getFkModifierId()
    {
	return this.fkModifierId;
    }

    public void setFkModifierId(Integer fkModifierId)
    {
	this.fkModifierId = fkModifierId;
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
	    mappedBy = "dicMajor")
    public Set<Student> getStudents()
    {
	return this.students;
    }

    public void setStudents(Set<Student> students)
    {
	this.students = students;
    }

}