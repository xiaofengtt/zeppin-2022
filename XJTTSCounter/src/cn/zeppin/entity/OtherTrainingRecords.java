package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OtherTrainingRecords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "other_training_records")
public class OtherTrainingRecords implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String year;
	private ProjectType projectType;
	private String projectName;
	private String shortName;
	private Teacher teacher;
    private TrainingSubject trainingSubject;
    private TrainingCollege trainingCollege;
    private Integer trainingHour;
    private Integer trainingOnlineHour;
    private String startTime;
    private String endTime;
    
    // Constructors

    /** default constructor */
    public OtherTrainingRecords()
    {
    }

    /** full constructor */
    public OtherTrainingRecords(String year,ProjectType projectType,String projectName,String shortName,Teacher teacher,
    		TrainingSubject trainingSubject,TrainingCollege trainingCollege,Integer trainingHour,Integer trainingOnlineHour,
    		String startTime,String endTime)
    {
	this.year = year;
	this.projectType = projectType;
	this.projectName = projectName;
	this.shortName = shortName;
	this.teacher = teacher;
	this.trainingSubject = trainingSubject;
	this.trainingCollege = trainingCollege;
	this.trainingHour = trainingHour;
	this.trainingOnlineHour = trainingOnlineHour;
	this.startTime = startTime;
	this.endTime = endTime;
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
    @JoinColumn(name = "PROJECT_TYPE", nullable = false)
    public ProjectType getProjectType()
    {
	return this.projectType;
    }

    public void setProjectType(ProjectType projectType)
    {
	this.projectType = projectType;
    }
    
    @Column(name = "PROJECT_NAME", nullable = false , length = 50)
    public String getProjectName()
    {
	return this.projectName;
    }

    public void setProjectName(String projectName)
    {
	this.projectName = projectName;
    }
    
    @Column(name = "SHORT_NAME", nullable = false , length = 50)
    public String getShortName()
    {
	return this.shortName;
    }

    public void setShortName(String shortName)
    {
	this.shortName = shortName;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINING_SUBJECT", nullable = false)
    public TrainingSubject getTrainingSubject()
    {
	return this.trainingSubject;
    }

    public void setTrainingSubject(TrainingSubject trainingSubject)
    {
	this.trainingSubject = trainingSubject;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINING_COLLEGE", nullable = false)
    public TrainingCollege getTrainingCollege()
    {
	return this.trainingCollege;
    }

    public void setTrainingCollege(TrainingCollege trainingCollege)
    {
	this.trainingCollege = trainingCollege;
    }
    
    @Column(name = "TRAINING_HOUR", nullable = false)
    public Integer getTrainingHour()
    {
	return this.trainingHour;
    }

    public void setTrainingHour(Integer trainingHour)
    {
	this.trainingHour = trainingHour;
    }
    
    @Column(name = "TRAINING_ONLINE_HOUR", nullable = false)
    public Integer getTrainingOnlineHour()
    {
	return this.trainingOnlineHour;
    }

    public void setTrainingOnlineHour(Integer trainingOnlineHour)
    {
	this.trainingOnlineHour = trainingOnlineHour;
    }
    
    @Column(name = "STARTTIME", nullable = false , length = 50)
    public String getStartTime()
    {
	return this.startTime;
    }

    public void setStartTime(String startTime)
    {
	this.startTime = startTime;
    }
    
    @Column(name = "ENDTIME", nullable = false , length = 50)
    public String getEndTime()
    {
	return this.endTime;
    }

    public void setEndTime(String endTime)
    {
	this.endTime = endTime;
    }
}