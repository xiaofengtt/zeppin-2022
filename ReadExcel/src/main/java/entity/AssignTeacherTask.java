package entity;

import java.sql.Timestamp;
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
 * AssignTeacherTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "assign_teacher_task", catalog = "xjtts")
public class AssignTeacherTask implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Organization organizationByGOrganization;
    private TrainingSubject trainingSubject;
    private Project project;
    private Organization organizationByPOrganization;
    private AssignTeacherTask assignTeacherTask;
    private TrainingCollege trainingCollege;
    private Short level;
    private Timestamp timeup;
    private Short status;
    private Integer teacherNumber;
    private Short receiveFlag;
    private Integer creator;
    private Timestamp creattime;
    private Set<AssignTeacherTask> assignTeacherTasks = new HashSet<AssignTeacherTask>(
	    0);

    // Constructors

    /** default constructor */
    public AssignTeacherTask()
    {
    }

    /** minimal constructor */
    public AssignTeacherTask(Organization organizationByGOrganization,
	    TrainingSubject trainingSubject, Project project,
	    Organization organizationByPOrganization,
	    TrainingCollege trainingCollege, Short level, Timestamp timeup,
	    Short status, Integer teacherNumber, Short receiveFlag,
	    Integer creator, Timestamp creattime)
    {
	this.organizationByGOrganization = organizationByGOrganization;
	this.trainingSubject = trainingSubject;
	this.project = project;
	this.organizationByPOrganization = organizationByPOrganization;
	this.trainingCollege = trainingCollege;
	this.level = level;
	this.timeup = timeup;
	this.status = status;
	this.teacherNumber = teacherNumber;
	this.receiveFlag = receiveFlag;
	this.creator = creator;
	this.creattime = creattime;
    }

    /** full constructor */
    public AssignTeacherTask(Organization organizationByGOrganization,
	    TrainingSubject trainingSubject, Project project,
	    Organization organizationByPOrganization,
	    AssignTeacherTask assignTeacherTask,
	    TrainingCollege trainingCollege, Short level, Timestamp timeup,
	    Short status, Integer teacherNumber, Short receiveFlag,
	    Integer creator, Timestamp creattime,
	    Set<AssignTeacherTask> assignTeacherTasks)
    {
	this.organizationByGOrganization = organizationByGOrganization;
	this.trainingSubject = trainingSubject;
	this.project = project;
	this.organizationByPOrganization = organizationByPOrganization;
	this.assignTeacherTask = assignTeacherTask;
	this.trainingCollege = trainingCollege;
	this.level = level;
	this.timeup = timeup;
	this.status = status;
	this.teacherNumber = teacherNumber;
	this.receiveFlag = receiveFlag;
	this.creator = creator;
	this.creattime = creattime;
	this.assignTeacherTasks = assignTeacherTasks;
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
    @JoinColumn(name = "G_ORGANIZATION", nullable = false)
    public Organization getOrganizationByGOrganization()
    {
	return this.organizationByGOrganization;
    }

    public void setOrganizationByGOrganization(
	    Organization organizationByGOrganization)
    {
	this.organizationByGOrganization = organizationByGOrganization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT", nullable = false)
    public TrainingSubject getTrainingSubject()
    {
	return this.trainingSubject;
    }

    public void setTrainingSubject(TrainingSubject trainingSubject)
    {
	this.trainingSubject = trainingSubject;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT", nullable = false)
    public Project getProject()
    {
	return this.project;
    }

    public void setProject(Project project)
    {
	this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_ORGANIZATION", nullable = false)
    public Organization getOrganizationByPOrganization()
    {
	return this.organizationByPOrganization;
    }

    public void setOrganizationByPOrganization(
	    Organization organizationByPOrganization)
    {
	this.organizationByPOrganization = organizationByPOrganization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PID")
    public AssignTeacherTask getAssignTeacherTask()
    {
	return this.assignTeacherTask;
    }

    public void setAssignTeacherTask(AssignTeacherTask assignTeacherTask)
    {
	this.assignTeacherTask = assignTeacherTask;
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

    @Column(name = "LEVEL", nullable = false)
    public Short getLevel()
    {
	return this.level;
    }

    public void setLevel(Short level)
    {
	this.level = level;
    }

    @Column(name = "TIMEUP", nullable = false, length = 19)
    public Timestamp getTimeup()
    {
	return this.timeup;
    }

    public void setTimeup(Timestamp timeup)
    {
	this.timeup = timeup;
    }

    @Column(name = "STATUS", nullable = false)
    public Short getStatus()
    {
	return this.status;
    }

    public void setStatus(Short status)
    {
	this.status = status;
    }

    @Column(name = "TEACHER_NUMBER", nullable = false)
    public Integer getTeacherNumber()
    {
	return this.teacherNumber;
    }

    public void setTeacherNumber(Integer teacherNumber)
    {
	this.teacherNumber = teacherNumber;
    }

    @Column(name = "RECEIVE_FLAG", nullable = false)
    public Short getReceiveFlag()
    {
	return this.receiveFlag;
    }

    public void setReceiveFlag(Short receiveFlag)
    {
	this.receiveFlag = receiveFlag;
    }

    @Column(name = "CREATOR", nullable = false)
    public Integer getCreator()
    {
	return this.creator;
    }

    public void setCreator(Integer creator)
    {
	this.creator = creator;
    }

    @Column(name = "CREATTIME", nullable = false, length = 19)
    public Timestamp getCreattime()
    {
	return this.creattime;
    }

    public void setCreattime(Timestamp creattime)
    {
	this.creattime = creattime;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "assignTeacherTask")
    public Set<AssignTeacherTask> getAssignTeacherTasks()
    {
	return this.assignTeacherTasks;
    }

    public void setAssignTeacherTasks(Set<AssignTeacherTask> assignTeacherTasks)
    {
	this.assignTeacherTasks = assignTeacherTasks;
    }

}