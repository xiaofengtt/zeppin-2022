package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TeacherExamRecords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_exam_records")
public class TeacherExamRecords implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String year;
	private String exam;
	private Teacher teacher;
	private Float score;
    private Timestamp creattime;
    
    // Constructors

    /** default constructor */
    public TeacherExamRecords()
    {
    }

    /** full constructor */
    public TeacherExamRecords(String year,String exam,Teacher teacher,Float score,Timestamp creattime)
    {
	this.year = year;
	this.exam = exam;
	this.teacher = teacher;
	this.score = score;
	this.creattime = creattime;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(name = "YEAR", nullable = false, length = 4)
    public String getYear()
    {
	return this.year;
    }

    public void setYear(String year)
    {
	this.year = year;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER", nullable = false)
    public Teacher getTeacher()
    {
	return this.teacher;
    }

    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }
    
    @Column(name = "EXAM", nullable = false, length = 100)
    public String getExam()
    {
	return this.exam;
    }

    public void setExam(String exam)
    {
	this.exam = exam;
    }    
    
    @Column(name = "SCORE", nullable = false)
    public Float getScore()
    {
	return this.score;
    }

    public void setScore(Float score)
    {
	this.score = score;
    }  
    
    @Column(name = "CREATTIME", nullable = false)
    public Timestamp getCreattime()
    {
	return this.creattime;
    }

    public void setCreattime(Timestamp creattime)
    {
	this.creattime = creattime;
    }  
}