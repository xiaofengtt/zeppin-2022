package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Submit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(
	name = "submit",
	uniqueConstraints = @UniqueConstraint(columnNames = { "PROJECT",
		"SUBJECT", "TRAINING_COLLEGE", "VALUATOR", "PSQ" }))
public class Submit implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Psq psq;
    private TrainingSubject trainingSubject;
    private Project project;
    private TrainingCollege trainingCollege;
    private Integer valuator;
    private Integer creater;
    private Timestamp createtime;
    private String ip;
    private String uuid;
    private Set<Result> results = new HashSet<Result>(0);

    // Constructors

    /** default constructor */
    public Submit()
    {
    }

    /** minimal constructor */
    public Submit(Psq psq, TrainingSubject trainingSubject, Project project,
	    TrainingCollege trainingCollege, Integer valuator, Integer creater,
	    Timestamp createtime)
    {
	this.psq = psq;
	this.trainingSubject = trainingSubject;
	this.project = project;
	this.trainingCollege = trainingCollege;
	this.valuator = valuator;
	this.creater = creater;
	this.createtime = createtime;
    }

    /** full constructor */
    public Submit(Psq psq, TrainingSubject trainingSubject, Project project,
	    TrainingCollege trainingCollege, Integer valuator, Integer creater,
	    Timestamp createtime, String ip, String uuid, Set<Result> results)
    {
	this.psq = psq;
	this.trainingSubject = trainingSubject;
	this.project = project;
	this.trainingCollege = trainingCollege;
	this.valuator = valuator;
	this.creater = creater;
	this.createtime = createtime;
	this.ip = ip;
	this.uuid = uuid;
	this.results = results;
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
    @JoinColumn(name = "PSQ", nullable = false)
    public Psq getPsq()
    {
	return this.psq;
    }

    public void setPsq(Psq psq)
    {
	this.psq = psq;
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
    @JoinColumn(name = "TRAINING_COLLEGE", nullable = false)
    public TrainingCollege getTrainingCollege()
    {
	return this.trainingCollege;
    }

    public void setTrainingCollege(TrainingCollege trainingCollege)
    {
	this.trainingCollege = trainingCollege;
    }

    @Column(name = "VALUATOR", nullable = false)
    public Integer getValuator()
    {
	return this.valuator;
    }

    public void setValuator(Integer valuator)
    {
	this.valuator = valuator;
    }

    @Column(name = "CREATER", nullable = false)
    public Integer getCreater()
    {
	return this.creater;
    }

    public void setCreater(Integer creater)
    {
	this.creater = creater;
    }

    @Column(name = "CREATETIME", nullable = false, length = 19)
    public Timestamp getCreatetime()
    {
	return this.createtime;
    }

    public void setCreatetime(Timestamp createtime)
    {
	this.createtime = createtime;
    }

    @Column(name = "IP", length = 60)
    public String getIp()
    {
	return this.ip;
    }

    public void setIp(String ip)
    {
	this.ip = ip;
    }

    @Column(name = "UUID", length = 50)
    public String getUuid()
    {
	return this.uuid;
    }

    public void setUuid(String uuid)
    {
	this.uuid = uuid;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "submit")
    @OrderBy("id ASC")
    public Set<Result> getResults()
    {
	return this.results;
    }

    public void setResults(Set<Result> results)
    {
	this.results = results;
    }

}