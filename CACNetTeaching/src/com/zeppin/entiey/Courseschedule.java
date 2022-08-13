package com.zeppin.entiey;

import java.util.Date;
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
 * Courseschedule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "courseschedule", catalog = "cac")
public class Courseschedule implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Teacher teacherByFkTeacher;
    private Teacher teacherByFkModifierId;
    private Teacher teacherByFkCreatorId;
    private Coursedesign coursedesign;
    private Date startTime;
    private String datetype;
    private Integer weeks;
    private String classroom;
    private String remark;
    private Date createDate;
    private Date modifiDate;

    // Constructors

    /** default constructor */
    public Courseschedule()
    {
    }

    /** minimal constructor */
    public Courseschedule(Teacher teacherByFkTeacher)
    {
	this.teacherByFkTeacher = teacherByFkTeacher;
    }

    /** full constructor */
    public Courseschedule(Teacher teacherByFkTeacher,
	    Teacher teacherByFkModifierId, Teacher teacherByFkCreatorId,
	    Coursedesign coursedesign, Date startTime, String datetype,
	    Integer weeks, String classroom, String remark, Date createDate,
	    Date modifiDate)
    {
	this.teacherByFkTeacher = teacherByFkTeacher;
	this.teacherByFkModifierId = teacherByFkModifierId;
	this.teacherByFkCreatorId = teacherByFkCreatorId;
	this.coursedesign = coursedesign;
	this.startTime = startTime;
	this.datetype = datetype;
	this.weeks = weeks;
	this.classroom = classroom;
	this.remark = remark;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
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
    @JoinColumn(name = "fk_teacher", nullable = false)
    public Teacher getTeacherByFkTeacher()
    {
	return this.teacherByFkTeacher;
    }

    public void setTeacherByFkTeacher(Teacher teacherByFkTeacher)
    {
	this.teacherByFkTeacher = teacherByFkTeacher;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_coursePlan")
    public Coursedesign getCoursedesign()
    {
	return this.coursedesign;
    }

    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    @Column(name = "startTime", length = 19)
    public Date getStartTime()
    {
	return this.startTime;
    }

    public void setStartTime(Date startTime)
    {
	this.startTime = startTime;
    }

    @Column(name = "datetype", length = 10)
    public String getDatetype()
    {
	return this.datetype;
    }

    public void setDatetype(String datetype)
    {
	this.datetype = datetype;
    }

    @Column(name = "weeks")
    public Integer getWeeks()
    {
	return this.weeks;
    }

    public void setWeeks(Integer weeks)
    {
	this.weeks = weeks;
    }

    @Column(name = "classroom", length = 100)
    public String getClassroom()
    {
	return this.classroom;
    }

    public void setClassroom(String classroom)
    {
	this.classroom = classroom;
    }

    @Column(name = "remark", length = 100)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }

    @Column(name = "createDate", length = 19)
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

}