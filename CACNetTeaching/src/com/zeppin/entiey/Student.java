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
 * Student entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "student", catalog = "cac")
public class Student implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Edusystem edusystem;
    private DicAddress dicAddress;
    private DicMajor dicMajor;
    private Teacher teacherByFkCreaterId;
    private Teacher teacherByFkModifierId;
    private String studentCode;
    private String name;
    private short sex;
    private String idCode;
    private Date eduStartTime;
    private String classes;
    private String phone;
    private String picPath;
    private String familyName;
    private String familyphone;
    private String remark;
    private Date createDate;
    private Date modifiDate;
    private boolean isValue;
    private boolean isNomal;
    private short state;
    private Set<GroupStudenMap> groupStudenMaps = new HashSet<GroupStudenMap>(0);
    private Set<Classmap> classmaps = new HashSet<Classmap>(0);

    // Constructors

    /** default constructor */
    public Student()
    {
    }

    /** minimal constructor */
    public Student(DicMajor dicMajor, String studentCode, String name,
	    short sex, Date eduStartTime, Date createDate, boolean isValue,
	    boolean isNomal, short state)
    {
	this.dicMajor = dicMajor;
	this.studentCode = studentCode;
	this.name = name;
	this.sex = sex;
	this.eduStartTime = eduStartTime;
	this.createDate = createDate;
	this.isValue = isValue;
	this.isNomal = isNomal;
	this.state = state;
    }

    /** full constructor */
    public Student(Edusystem edusystem, DicAddress dicAddress,
	    DicMajor dicMajor, Teacher teacherByFkCreaterId,
	    Teacher teacherByFkModifierId, String studentCode, String name,
	    short sex, String idCode, Date eduStartTime, String phone, String classes , 
	    String picPath, String familyName, String familyphone,
	    String remark, Date createDate, Date modifiDate, boolean isValue,
	    boolean isNomal, short state, Set<GroupStudenMap> groupStudenMaps,
	    Set<Classmap> classmaps)
    {
	this.edusystem = edusystem;
	this.dicAddress = dicAddress;
	this.dicMajor = dicMajor;
	this.teacherByFkCreaterId = teacherByFkCreaterId;
	this.teacherByFkModifierId = teacherByFkModifierId;
	this.studentCode = studentCode;
	this.name = name;
	this.sex = sex;
	this.idCode = idCode;
	this.eduStartTime = eduStartTime;
	this.phone = phone;
	this.classes = classes;
	this.picPath = picPath;
	this.familyName = familyName;
	this.familyphone = familyphone;
	this.remark = remark;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
	this.isValue = isValue;
	this.isNomal = isNomal;
	this.state = state;
	this.groupStudenMaps = groupStudenMaps;
	this.classmaps = classmaps;
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
    @JoinColumn(name = "fk_eduSystem")
    public Edusystem getEdusystem()
    {
	return this.edusystem;
    }

    public void setEdusystem(Edusystem edusystem)
    {
	this.edusystem = edusystem;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_from")
    public DicAddress getDicAddress()
    {
	return this.dicAddress;
    }

    public void setDicAddress(DicAddress dicAddress)
    {
	this.dicAddress = dicAddress;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_major", nullable = false)
    public DicMajor getDicMajor()
    {
	return this.dicMajor;
    }

    public void setDicMajor(DicMajor dicMajor)
    {
	this.dicMajor = dicMajor;
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

    @Column(name = "studentCode", nullable = false, length = 15)
    public String getStudentCode()
    {
	return this.studentCode;
    }

    public void setStudentCode(String studentCode)
    {
	this.studentCode = studentCode;
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

    @Column(name = "sex", nullable = false)
    public short getSex()
    {
	return this.sex;
    }

    public void setSex(short sex)
    {
	this.sex = sex;
    }

    @Column(name = "idCode", length = 25)
    public String getIdCode()
    {
	return this.idCode;
    }

    public void setIdCode(String idCode)
    {
	this.idCode = idCode;
    }

    @Column(name = "eduStartTime", nullable = false, length = 19)
    public Date getEduStartTime()
    {
	return this.eduStartTime;
    }

    public void setEduStartTime(Date eduStartTime)
    {
	this.eduStartTime = eduStartTime;
    }

    @Column(name = "phone", length = 30)
    public String getPhone()
    {
	return this.phone;
    }

    public void setPhone(String phone)
    {
	this.phone = phone;
    }

    @Column(name = "picPath", length = 100)
    public String getPicPath()
    {
	return this.picPath;
    }

    public void setPicPath(String picPath)
    {
	this.picPath = picPath;
    }

    @Column(name = "familyName", length = 30)
    public String getFamilyName()
    {
	return this.familyName;
    }

    public void setFamilyName(String familyName)
    {
	this.familyName = familyName;
    }

    @Column(name = "classes", length = 30)
    public String getClasses()
    {
	return this.classes;
    }

    public void setClasses(String classes)
    {
	this.classes = classes;
    }
    
    @Column(name = "familyphone", length = 30)
    public String getFamilyphone()
    {
	return this.familyphone;
    }

    public void setFamilyphone(String familyphone)
    {
	this.familyphone = familyphone;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
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

    @Column(name = "isValue", nullable = false)
    public boolean getIsValue()
    {
	return this.isValue;
    }

    public void setIsValue(boolean isValue)
    {
	this.isValue = isValue;
    }

    @Column(name = "isNomal", nullable = false)
    public boolean getIsNomal()
    {
	return this.isNomal;
    }

    public void setIsNomal(boolean isNomal)
    {
	this.isNomal = isNomal;
    }

    @Column(name = "state", nullable = false)
    public short getState()
    {
	return this.state;
    }

    public void setState(short state)
    {
	this.state = state;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "student")
    public Set<GroupStudenMap> getGroupStudenMaps()
    {
	return this.groupStudenMaps;
    }

    public void setGroupStudenMaps(Set<GroupStudenMap> groupStudenMaps)
    {
	this.groupStudenMaps = groupStudenMaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "student")
    public Set<Classmap> getClassmaps()
    {
	return this.classmaps;
    }

    public void setClassmaps(Set<Classmap> classmaps)
    {
	this.classmaps = classmaps;
    }

}