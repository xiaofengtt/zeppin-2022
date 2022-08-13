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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DicAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dic_address", catalog = "cac")
public class DicAddress implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String name;
    private String code;
    private short class_;
    private String parentCode;
    private boolean isvalue;
    private Set<Student> students = new HashSet<Student>(0);

    // Constructors

    /** default constructor */
    public DicAddress()
    {
    }

    /** minimal constructor */
    public DicAddress(short class_, String parentCode, boolean isvalue)
    {
	this.class_ = class_;
	this.parentCode = parentCode;
	this.isvalue = isvalue;
    }

    /** full constructor */
    public DicAddress(String name, String code, short class_,
	    String parentCode, boolean isvalue, Set<Student> students)
    {
	this.name = name;
	this.code = code;
	this.class_ = class_;
	this.parentCode = parentCode;
	this.isvalue = isvalue;
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

    @Column(name = "name", length = 60)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "code", length = 30)
    public String getCode()
    {
	return this.code;
    }

    public void setCode(String code)
    {
	this.code = code;
    }

    @Column(name = "class", nullable = false)
    public short getClass_()
    {
	return this.class_;
    }

    public void setClass_(short class_)
    {
	this.class_ = class_;
    }

    @Column(name = "parentCode", nullable = false, length = 30)
    public String getParentCode()
    {
	return this.parentCode;
    }

    public void setParentCode(String parentCode)
    {
	this.parentCode = parentCode;
    }

    @Column(name = "isvalue", nullable = false)
    public boolean getIsvalue()
    {
	return this.isvalue;
    }

    public void setIsvalue(boolean isvalue)
    {
	this.isvalue = isvalue;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "dicAddress")
    public Set<Student> getStudents()
    {
	return this.students;
    }

    public void setStudents(Set<Student> students)
    {
	this.students = students;
    }

}