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
 * Homework entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "homework", catalog = "cac")
public class Homework implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Coursedesign coursedesign;
    private Teacher teacher;
    private String title;
    private String content;
    private boolean isEndWork;
    private Date endTime;
    private Date startTime;
    private Set<HomeworkAccessoryMap> homeworkAccessoryMaps = new HashSet<HomeworkAccessoryMap>(
	    0);

    // Constructors

    /** default constructor */
    public Homework()
    {
    }

    /** minimal constructor */
    public Homework(Coursedesign coursedesign, Teacher teacher, String content,
	    boolean isEndWork, Date endTime, Date startTime)
    {
	this.coursedesign = coursedesign;
	this.teacher = teacher;
	this.content = content;
	this.isEndWork = isEndWork;
	this.endTime = endTime;
	this.startTime = startTime;
    }

    /** full constructor */
    public Homework(Coursedesign coursedesign, Teacher teacher, String title,
	    String content, boolean isEndWork, Date endTime, Date startTime,
	    Set<HomeworkAccessoryMap> homeworkAccessoryMaps)
    {
	this.coursedesign = coursedesign;
	this.teacher = teacher;
	this.title = title;
	this.content = content;
	this.isEndWork = isEndWork;
	this.endTime = endTime;
	this.startTime = startTime;
	this.homeworkAccessoryMaps = homeworkAccessoryMaps;
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
    @JoinColumn(name = "fk_courese", nullable = false)
    public Coursedesign getCoursedesign()
    {
	return this.coursedesign;
    }

    public void setCoursedesign(Coursedesign coursedesign)
    {
	this.coursedesign = coursedesign;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_teacher", nullable = false)
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    @Column(name = "title", length = 100)
    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    @Column(name = "content", nullable = false, length = 65535)
    public String getContent()
    {
	return this.content;
    }

    public void setContent(String content)
    {
	this.content = content;
    }

    @Column(name = "isEndWork", nullable = false)
    public boolean getIsEndWork()
    {
	return this.isEndWork;
    }

    public void setIsEndWork(boolean isEndWork)
    {
	this.isEndWork = isEndWork;
    }

    @Column(name = "endTime", nullable = false, length = 19)
    public Date getEndTime()
    {
	return this.endTime;
    }

    public void setEndTime(Date endTime)
    {
	this.endTime = endTime;
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

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "homework")
    public Set<HomeworkAccessoryMap> getHomeworkAccessoryMaps()
    {
	return this.homeworkAccessoryMaps;
    }

    public void setHomeworkAccessoryMaps(
	    Set<HomeworkAccessoryMap> homeworkAccessoryMaps)
    {
	this.homeworkAccessoryMaps = homeworkAccessoryMaps;
    }

}