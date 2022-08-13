package com.zeppin.entiey;

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
 * HomeworkAccessoryMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "homework_accessory_map", catalog = "cac")
public class HomeworkAccessoryMap implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Accessory accessory;
    private Student student;
    private Homework homework;
    private short homeworkState;
    private float score;

    // Constructors

    /** default constructor */
    public HomeworkAccessoryMap()
    {
    }

    /** minimal constructor */
    public HomeworkAccessoryMap(Accessory accessory, Student student, Homework homework)
    {
	this.accessory = accessory;
	this.student = student;
	this.homework = homework;
    }

    /** full constructor */
    public HomeworkAccessoryMap(Accessory accessory, Student student,
	    Homework homework, short homeworkState, float score)
    {
	this.accessory = accessory;
	this.student = student;
	this.homework = homework;
	this.homeworkState = homeworkState;
	this.score = score;
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
    @JoinColumn(name = "accessoryId", nullable = false)
    public Accessory getAccessory()
    {
	return this.accessory;
    }

    public void setAccessory(Accessory accessory)
    {
	this.accessory = accessory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    public Student getStudent()
    {
	return this.student;
    }

    public void setStudent(Student student)
    {
	this.student = student;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homeworkId", nullable = false)
    public Homework getHomework()
    {
	return this.homework;
    }

    public void setHomework(Homework homework)
    {
	this.homework = homework;
    }

    @Column(name = "homeworkState")
    public short getHomeworkState()
    {
	return this.homeworkState;
    }

    public void setHomeworkState(short homeworkState)
    {
	this.homeworkState = homeworkState;
    }

    @Column(name = "score", precision = 12, scale = 0)
    public float getScore()
    {
	return this.score;
    }

    public void setScore(float score)
    {
	this.score = score;
    }

}