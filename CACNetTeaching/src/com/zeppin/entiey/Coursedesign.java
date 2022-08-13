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
 * Coursedesign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "coursedesign", catalog = "cac")
public class Coursedesign implements java.io.Serializable
{

    // Fields

    private Integer id;
    private DicAcademy dicAcademy;
    private Subject subject;
    private Date startTime;
    private Integer weeks;
    private Integer classHour;
    private float credit;
    private short studyMode;
    private String studentDescirpt;
    private short scoreState;
    private String remark;
    private String courseName;
    private short status ;
    private Set<CoursedesignTeacherMap> coursedesignTeacherMaps = new HashSet<CoursedesignTeacherMap>(
	    0);
    private Set<Courseschedule> courseschedules = new HashSet<Courseschedule>(0);
    private Set<Classmap> classmaps = new HashSet<Classmap>(0);
    private Set<Studentgrou> studentgrous = new HashSet<Studentgrou>(0);
    private Set<Homework> homeworks = new HashSet<Homework>(0);
    private Set<CourseCoursewareMap> courseCoursewareMaps = new HashSet<CourseCoursewareMap>(
	    0);

    // Constructors

    /** default constructor */
    public Coursedesign()
    {
    }

    /** minimal constructor */
    public Coursedesign(DicAcademy dicAcademy, Subject subject, Date startTime,
	    Integer weeks, Integer classHour, float credit, short studyMode,
	    String studentDescirpt, String courseName)
    {
	this.dicAcademy = dicAcademy;
	this.subject = subject;
	this.startTime = startTime;
	this.weeks = weeks;
	this.classHour = classHour;
	this.credit = credit;
	this.studyMode = studyMode;
	this.studentDescirpt = studentDescirpt;
	this.courseName = courseName;
    }

    /** full constructor */
    public Coursedesign(DicAcademy dicAcademy, Subject subject, Date startTime,
	    Integer weeks, Integer classHour, float credit, short studyMode,
	    String studentDescirpt, short scoreState, String remark,
	    String courseName,short status ,
	    Set<CoursedesignTeacherMap> coursedesignTeacherMaps,
	    Set<Courseschedule> courseschedules, Set<Classmap> classmaps,
	    Set<Studentgrou> studentgrous, Set<Homework> homeworks,
	    Set<CourseCoursewareMap> courseCoursewareMaps)
    {
	this.dicAcademy = dicAcademy;
	this.subject = subject;
	this.startTime = startTime;
	this.weeks = weeks;
	this.classHour = classHour;
	this.credit = credit;
	this.studyMode = studyMode;
	this.studentDescirpt = studentDescirpt;
	this.scoreState = scoreState;
	this.remark = remark;
	this.courseName = courseName;
	this.coursedesignTeacherMaps = coursedesignTeacherMaps;
	this.courseschedules = courseschedules;
	this.classmaps = classmaps;
	this.studentgrous = studentgrous;
	this.homeworks = homeworks;
	this.courseCoursewareMaps = courseCoursewareMaps;
	this.status = status;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_course", nullable = false)
    public Subject getSubject()
    {
	return this.subject;
    }

    public void setSubject(Subject subject)
    {
	this.subject = subject;
    }

    @Column(name = "startTime", nullable = false, length = 19)
    public Date getStartTime()
    {
	return this.startTime;
    }

    public void setStartTime(Date startTime)
    {
	this.startTime = startTime;
    }

    @Column(name = "weeks", nullable = false)
    public Integer getWeeks()
    {
	return this.weeks;
    }

    public void setWeeks(Integer weeks)
    {
	this.weeks = weeks;
    }

    @Column(name = "classHour", nullable = false)
    public Integer getClassHour()
    {
	return this.classHour;
    }

    public void setClassHour(Integer classHour)
    {
	this.classHour = classHour;
    }

    @Column(name = "credit", nullable = false, precision = 12, scale = 0)
    public float getCredit()
    {
	return this.credit;
    }

    public void setCredit(float credit)
    {
	this.credit = credit;
    }

    @Column(name = "studyMode", nullable = false)
    public short getStudyMode()
    {
	return this.studyMode;
    }

    public void setStudyMode(short studyMode)
    {
	this.studyMode = studyMode;
    }

    @Column(name = "studentDescirpt", nullable = false, length = 50)
    public String getStudentDescirpt()
    {
	return this.studentDescirpt;
    }

    public void setStudentDescirpt(String studentDescirpt)
    {
	this.studentDescirpt = studentDescirpt;
    }

    @Column(name = "scoreState")
    public short getScoreState()
    {
	return this.scoreState;
    }

    public void setScoreState(short scoreState)
    {
	this.scoreState = scoreState;
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

    @Column(name = "courseName", nullable = false, length = 45)
    public String getCourseName()
    {
	return this.courseName;
    }

    public void setCourseName(String courseName)
    {
	this.courseName = courseName;
    }

    @Column(name = "status" , length = 4)
    public short getStatus()
    {
	return this.status;
    }

    public void setStatus(short status)
    {
	this.status = status;
    }
    
    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "coursedesign")
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
	    mappedBy = "coursedesign")
    public Set<Courseschedule> getCourseschedules()
    {
	return this.courseschedules;
    }

    public void setCourseschedules(Set<Courseschedule> courseschedules)
    {
	this.courseschedules = courseschedules;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "coursedesign")
    public Set<Classmap> getClassmaps()
    {
	return this.classmaps;
    }

    public void setClassmaps(Set<Classmap> classmaps)
    {
	this.classmaps = classmaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "coursedesign")
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
	    mappedBy = "coursedesign")
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
	    mappedBy = "coursedesign")
    public Set<CourseCoursewareMap> getCourseCoursewareMaps()
    {
	return this.courseCoursewareMaps;
    }

    public void setCourseCoursewareMaps(
	    Set<CourseCoursewareMap> courseCoursewareMaps)
    {
	this.courseCoursewareMaps = courseCoursewareMaps;
    }

}