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
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher", catalog = "cac")
public class Teacher implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacherByModifierId;
    private DicTechnicalTiltle dicTechnicalTiltle;
    private DicDuty dicDuty;
    private DicAcademy dicAcademy;
    private Teacher teacherByCreaterId;
    private String name;
    private String workCode;
    private short sex;
    private String phone;
    private String idCode;
    private short state;
    private short manageType;
    private boolean isValue;
    private String picPath;
    private Date createTime;
    private Date modifyDate;
    private String remark;
    private Set<Subject> subjectsForFkCreatorId = new HashSet<Subject>(0);
    private Set<DicDuty> dicDutiesForFkCreatorId = new HashSet<DicDuty>(0);
    private Set<Student> studentsForFkCreaterId = new HashSet<Student>(0);
    private Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkModifierId = new HashSet<DicTechnicalTiltle>(
	    0);
    private Set<Student> studentsForFkModifierId = new HashSet<Student>(0);
    private Set<DicAcademy> dicAcademiesForFkCreaterId = new HashSet<DicAcademy>(
	    0);
    private Set<Courseschedule> courseschedulesForFkTeacher = new HashSet<Courseschedule>(
	    0);
    private Set<CoursedesignTeacherMap> coursedesignTeacherMaps = new HashSet<CoursedesignTeacherMap>(
	    0);
    private Set<DicDuty> dicDutiesForFkModifierId = new HashSet<DicDuty>(0);
    private Set<Studentgrou> studentgrous = new HashSet<Studentgrou>(0);
    private Set<Homework> homeworks = new HashSet<Homework>(0);
    private Set<DicAcademy> dicAcademiesForFkModiferId = new HashSet<DicAcademy>(
	    0);
    private Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkCreatorId = new HashSet<DicTechnicalTiltle>(
	    0);
    private Set<Teacher> teachersForCreaterId = new HashSet<Teacher>(0);
    private Set<Subject> subjectsForFkModifierId = new HashSet<Subject>(0);
    private Set<CourseCoursewareMap> courseCoursewareMaps = new HashSet<CourseCoursewareMap>(
	    0);
    private Set<Courseschedule> courseschedulesForFkCreatorId = new HashSet<Courseschedule>(
	    0);
    private Set<Teacher> teachersForModifierId = new HashSet<Teacher>(0);
    private Set<Courseschedule> courseschedulesForFkModifierId = new HashSet<Courseschedule>(
	    0);

    // Constructors

    /** default constructor */
    public Teacher()
    {
    }

    /** minimal constructor */
    public Teacher(String name, short sex, short manageType, boolean isValue,
	    Date createTime)
    {
	this.name = name;
	this.sex = sex;
	this.manageType = manageType;
	this.isValue = isValue;
	this.createTime = createTime;
    }

    /** full constructor */
    public Teacher(Teacher teacherByModifierId,
	    DicTechnicalTiltle dicTechnicalTiltle, DicDuty dicDuty,
	    DicAcademy dicAcademy, Teacher teacherByCreaterId, String name,
	    String workCode, short sex, String phone, String idCode,
	    short state, short manageType, boolean isValue, String picPath,
	    Date createTime, Date modifyDate, String remark,
	    Set<Subject> subjectsForFkCreatorId,
	    Set<DicDuty> dicDutiesForFkCreatorId,
	    Set<Student> studentsForFkCreaterId,
	    Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkModifierId,
	    Set<Student> studentsForFkModifierId,
	    Set<DicAcademy> dicAcademiesForFkCreaterId,
	    Set<Courseschedule> courseschedulesForFkTeacher,
	    Set<CoursedesignTeacherMap> coursedesignTeacherMaps,
	    Set<DicDuty> dicDutiesForFkModifierId,
	    Set<Studentgrou> studentgrous, Set<Homework> homeworks,
	    Set<DicAcademy> dicAcademiesForFkModiferId,
	    Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkCreatorId,
	    Set<Teacher> teachersForCreaterId,
	    Set<Subject> subjectsForFkModifierId,
	    Set<CourseCoursewareMap> courseCoursewareMaps,
	    Set<Courseschedule> courseschedulesForFkCreatorId,
	    Set<Teacher> teachersForModifierId,
	    Set<Courseschedule> courseschedulesForFkModifierId)
    {
	this.teacherByModifierId = teacherByModifierId;
	this.dicTechnicalTiltle = dicTechnicalTiltle;
	this.dicDuty = dicDuty;
	this.dicAcademy = dicAcademy;
	this.teacherByCreaterId = teacherByCreaterId;
	this.name = name;
	this.workCode = workCode;
	this.sex = sex;
	this.phone = phone;
	this.idCode = idCode;
	this.state = state;
	this.manageType = manageType;
	this.isValue = isValue;
	this.picPath = picPath;
	this.createTime = createTime;
	this.modifyDate = modifyDate;
	this.remark = remark;
	this.subjectsForFkCreatorId = subjectsForFkCreatorId;
	this.dicDutiesForFkCreatorId = dicDutiesForFkCreatorId;
	this.studentsForFkCreaterId = studentsForFkCreaterId;
	this.dicTechnicalTiltlesForFkModifierId = dicTechnicalTiltlesForFkModifierId;
	this.studentsForFkModifierId = studentsForFkModifierId;
	this.dicAcademiesForFkCreaterId = dicAcademiesForFkCreaterId;
	this.courseschedulesForFkTeacher = courseschedulesForFkTeacher;
	this.coursedesignTeacherMaps = coursedesignTeacherMaps;
	this.dicDutiesForFkModifierId = dicDutiesForFkModifierId;
	this.studentgrous = studentgrous;
	this.homeworks = homeworks;
	this.dicAcademiesForFkModiferId = dicAcademiesForFkModiferId;
	this.dicTechnicalTiltlesForFkCreatorId = dicTechnicalTiltlesForFkCreatorId;
	this.teachersForCreaterId = teachersForCreaterId;
	this.subjectsForFkModifierId = subjectsForFkModifierId;
	this.courseCoursewareMaps = courseCoursewareMaps;
	this.courseschedulesForFkCreatorId = courseschedulesForFkCreatorId;
	this.teachersForModifierId = teachersForModifierId;
	this.courseschedulesForFkModifierId = courseschedulesForFkModifierId;
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
    @JoinColumn(name = "modifierId")
    public Teacher getTeacherByModifierId()
    {
	return this.teacherByModifierId;
    }

    public void setTeacherByModifierId(Teacher teacherByModifierId)
    {
	this.teacherByModifierId = teacherByModifierId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_technical_tiltle")
    public DicTechnicalTiltle getDicTechnicalTiltle()
    {
	return this.dicTechnicalTiltle;
    }

    public void setDicTechnicalTiltle(DicTechnicalTiltle dicTechnicalTiltle)
    {
	this.dicTechnicalTiltle = dicTechnicalTiltle;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_duty")
    public DicDuty getDicDuty()
    {
	return this.dicDuty;
    }

    public void setDicDuty(DicDuty dicDuty)
    {
	this.dicDuty = dicDuty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_department")
    public DicAcademy getDicAcademy()
    {
	return this.dicAcademy;
    }

    public void setDicAcademy(DicAcademy dicAcademy)
    {
	this.dicAcademy = dicAcademy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createrId")
    public Teacher getTeacherByCreaterId()
    {
	return this.teacherByCreaterId;
    }

    public void setTeacherByCreaterId(Teacher teacherByCreaterId)
    {
	this.teacherByCreaterId = teacherByCreaterId;
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

    @Column(name = "work_code", length = 30)
    public String getWorkCode()
    {
	return this.workCode;
    }

    public void setWorkCode(String workCode)
    {
	this.workCode = workCode;
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

    @Column(name = "phone", length = 30)
    public String getPhone()
    {
	return this.phone;
    }

    public void setPhone(String phone)
    {
	this.phone = phone;
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

    @Column(name = "state")
    public short getState()
    {
	return this.state;
    }

    public void setState(short state)
    {
	this.state = state;
    }

    @Column(name = "manageType", nullable = false)
    public short getManageType()
    {
	return this.manageType;
    }

    public void setManageType(short manageType)
    {
	this.manageType = manageType;
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

    @Column(name = "picPath", length = 100)
    public String getPicPath()
    {
	return this.picPath;
    }

    public void setPicPath(String picPath)
    {
	this.picPath = picPath;
    }

    @Column(name = "createTime", nullable = false, length = 19)
    public Date getCreateTime()
    {
	return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
	this.createTime = createTime;
    }

    @Column(name = "modifyDate", length = 19)
    public Date getModifyDate()
    {
	return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate)
    {
	this.modifyDate = modifyDate;
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

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreatorId")
    public Set<Subject> getSubjectsForFkCreatorId()
    {
	return this.subjectsForFkCreatorId;
    }

    public void setSubjectsForFkCreatorId(Set<Subject> subjectsForFkCreatorId)
    {
	this.subjectsForFkCreatorId = subjectsForFkCreatorId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreatorId")
    public Set<DicDuty> getDicDutiesForFkCreatorId()
    {
	return this.dicDutiesForFkCreatorId;
    }

    public void setDicDutiesForFkCreatorId(Set<DicDuty> dicDutiesForFkCreatorId)
    {
	this.dicDutiesForFkCreatorId = dicDutiesForFkCreatorId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreaterId")
    public Set<Student> getStudentsForFkCreaterId()
    {
	return this.studentsForFkCreaterId;
    }

    public void setStudentsForFkCreaterId(Set<Student> studentsForFkCreaterId)
    {
	this.studentsForFkCreaterId = studentsForFkCreaterId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModifierId")
    public Set<DicTechnicalTiltle> getDicTechnicalTiltlesForFkModifierId()
    {
	return this.dicTechnicalTiltlesForFkModifierId;
    }

    public void setDicTechnicalTiltlesForFkModifierId(
	    Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkModifierId)
    {
	this.dicTechnicalTiltlesForFkModifierId = dicTechnicalTiltlesForFkModifierId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModifierId")
    public Set<Student> getStudentsForFkModifierId()
    {
	return this.studentsForFkModifierId;
    }

    public void setStudentsForFkModifierId(Set<Student> studentsForFkModifierId)
    {
	this.studentsForFkModifierId = studentsForFkModifierId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreaterId")
    public Set<DicAcademy> getDicAcademiesForFkCreaterId()
    {
	return this.dicAcademiesForFkCreaterId;
    }

    public void setDicAcademiesForFkCreaterId(
	    Set<DicAcademy> dicAcademiesForFkCreaterId)
    {
	this.dicAcademiesForFkCreaterId = dicAcademiesForFkCreaterId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkTeacher")
    public Set<Courseschedule> getCourseschedulesForFkTeacher()
    {
	return this.courseschedulesForFkTeacher;
    }

    public void setCourseschedulesForFkTeacher(
	    Set<Courseschedule> courseschedulesForFkTeacher)
    {
	this.courseschedulesForFkTeacher = courseschedulesForFkTeacher;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<CoursedesignTeacherMap> getCoursedesignTeacherMaps()
    {
	return this.coursedesignTeacherMaps;
    }

    public void setCoursedesignTeacherMaps(
	    Set<CoursedesignTeacherMap> coursedesignTeacherMaps)
    {
	this.coursedesignTeacherMaps = coursedesignTeacherMaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModifierId")
    public Set<DicDuty> getDicDutiesForFkModifierId()
    {
	return this.dicDutiesForFkModifierId;
    }

    public void setDicDutiesForFkModifierId(
	    Set<DicDuty> dicDutiesForFkModifierId)
    {
	this.dicDutiesForFkModifierId = dicDutiesForFkModifierId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<Studentgrou> getStudentgrous()
    {
	return this.studentgrous;
    }

    public void setStudentgrous(Set<Studentgrou> studentgrous)
    {
	this.studentgrous = studentgrous;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<Homework> getHomeworks()
    {
	return this.homeworks;
    }

    public void setHomeworks(Set<Homework> homeworks)
    {
	this.homeworks = homeworks;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModiferId")
    public Set<DicAcademy> getDicAcademiesForFkModiferId()
    {
	return this.dicAcademiesForFkModiferId;
    }

    public void setDicAcademiesForFkModiferId(
	    Set<DicAcademy> dicAcademiesForFkModiferId)
    {
	this.dicAcademiesForFkModiferId = dicAcademiesForFkModiferId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreatorId")
    public Set<DicTechnicalTiltle> getDicTechnicalTiltlesForFkCreatorId()
    {
	return this.dicTechnicalTiltlesForFkCreatorId;
    }

    public void setDicTechnicalTiltlesForFkCreatorId(
	    Set<DicTechnicalTiltle> dicTechnicalTiltlesForFkCreatorId)
    {
	this.dicTechnicalTiltlesForFkCreatorId = dicTechnicalTiltlesForFkCreatorId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByCreaterId")
    public Set<Teacher> getTeachersForCreaterId()
    {
	return this.teachersForCreaterId;
    }

    public void setTeachersForCreaterId(Set<Teacher> teachersForCreaterId)
    {
	this.teachersForCreaterId = teachersForCreaterId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModifierId")
    public Set<Subject> getSubjectsForFkModifierId()
    {
	return this.subjectsForFkModifierId;
    }

    public void setSubjectsForFkModifierId(Set<Subject> subjectsForFkModifierId)
    {
	this.subjectsForFkModifierId = subjectsForFkModifierId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<CourseCoursewareMap> getCourseCoursewareMaps()
    {
	return this.courseCoursewareMaps;
    }

    public void setCourseCoursewareMaps(
	    Set<CourseCoursewareMap> courseCoursewareMaps)
    {
	this.courseCoursewareMaps = courseCoursewareMaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkCreatorId")
    public Set<Courseschedule> getCourseschedulesForFkCreatorId()
    {
	return this.courseschedulesForFkCreatorId;
    }

    public void setCourseschedulesForFkCreatorId(
	    Set<Courseschedule> courseschedulesForFkCreatorId)
    {
	this.courseschedulesForFkCreatorId = courseschedulesForFkCreatorId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByModifierId")
    public Set<Teacher> getTeachersForModifierId()
    {
	return this.teachersForModifierId;
    }

    public void setTeachersForModifierId(Set<Teacher> teachersForModifierId)
    {
	this.teachersForModifierId = teachersForModifierId;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacherByFkModifierId")
    public Set<Courseschedule> getCourseschedulesForFkModifierId()
    {
	return this.courseschedulesForFkModifierId;
    }

    public void setCourseschedulesForFkModifierId(
	    Set<Courseschedule> courseschedulesForFkModifierId)
    {
	this.courseschedulesForFkModifierId = courseschedulesForFkModifierId;
    }

}