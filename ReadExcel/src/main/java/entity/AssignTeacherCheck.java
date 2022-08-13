package entity;

import java.sql.Timestamp;
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
 * AssignTeacherCheck entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "assign_teacher_check", catalog = "xjtts")
public class AssignTeacherCheck implements java.io.Serializable
{

    // Fields

    private Integer id;
    private TeacherTrainingRecords teacherTrainingRecords;
    private Short type;
    private Integer checker;
    private Timestamp checktime;
    private String reason;

    // Constructors

    /** default constructor */
    public AssignTeacherCheck()
    {
    }

    /** full constructor */
    public AssignTeacherCheck(TeacherTrainingRecords teacherTrainingRecords,
	    Short type, Integer checker, Timestamp checktime)
    {
	this.teacherTrainingRecords = teacherTrainingRecords;
	this.type = type;
	this.checker = checker;
	this.checktime = checktime;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_TRAINING_RECORDS", nullable = false)
    public TeacherTrainingRecords getTeacherTrainingRecords()
    {
	return this.teacherTrainingRecords;
    }

    public void setTeacherTrainingRecords(
	    TeacherTrainingRecords teacherTrainingRecords)
    {
	this.teacherTrainingRecords = teacherTrainingRecords;
    }

    @Column(name = "TYPE", nullable = false)
    public Short getType()
    {
	return this.type;
    }

    public void setType(Short type)
    {
	this.type = type;
    }

    @Column(name = "CHECKER", nullable = false)
    public Integer getChecker()
    {
	return this.checker;
    }

    public void setChecker(Integer checker)
    {
	this.checker = checker;
    }

    @Column(name = "CHECKTIME", nullable = false, length = 19)
    public Timestamp getChecktime()
    {
	return this.checktime;
    }

    public void setChecktime(Timestamp checktime)
    {
	this.checktime = checktime;
    }

    @Column(name = "REASON", length = 100)
    public String getReason()
    {
	return this.reason;
    }

    public void setReason(String reason)
    {
	this.reason = reason;
    }
}