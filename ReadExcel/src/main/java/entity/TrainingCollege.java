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
 * TrainingCollege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "training_college", catalog = "xjtts")
public class TrainingCollege implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Area area;
    private ProjectLevel projectLevel;
    private String name;
    private String shortName;
    private String adress;
    private String contacts;
    private String phone;
    private String fax;
    private Short status;
    private Integer creator;
    private Timestamp creattime;
    private Set<TeacherTrainingRecords> teacherTrainingRecordses = new HashSet<TeacherTrainingRecords>(
	    0);
    private Set<Submit> submits = new HashSet<Submit>(0);
    private Set<ProjectCollegeRange> projectCollegeRanges = new HashSet<ProjectCollegeRange>(
	    0);
    private Set<AssignTeacherTask> assignTeacherTasks = new HashSet<AssignTeacherTask>(
	    0);
    private Set<TrainingAdmin> trainingAdmins = new HashSet<TrainingAdmin>(0);
    private Set<Teachexpert> teachexperts = new HashSet<Teachexpert>(0);
    private Set<ProjectApply> projectApplies = new HashSet<ProjectApply>(0);

    // Constructors

    /** default constructor */
    public TrainingCollege()
    {
    }

    /** minimal constructor */
    public TrainingCollege(Area area, ProjectLevel projectLevel, String name,
	    Short status, Timestamp creattime)
    {
	this.area = area;
	this.projectLevel = projectLevel;
	this.name = name;
	this.status = status;
	this.creattime = creattime;
    }

    /** full constructor */
    public TrainingCollege(Area area, ProjectLevel projectLevel, String name,
	    String shortName, String adress, String contacts, String phone,
	    String fax, Short status, Integer creator, Timestamp creattime,
	    Set<TeacherTrainingRecords> teacherTrainingRecordses,
	    Set<Submit> submits, Set<ProjectCollegeRange> projectCollegeRanges,
	    Set<AssignTeacherTask> assignTeacherTasks,
	    Set<TrainingAdmin> trainingAdmins, Set<Teachexpert> teachexperts,
	    Set<ProjectApply> projectApplies)
    {
	this.area = area;
	this.projectLevel = projectLevel;
	this.name = name;
	this.shortName = shortName;
	this.adress = adress;
	this.contacts = contacts;
	this.phone = phone;
	this.fax = fax;
	this.status = status;
	this.creator = creator;
	this.creattime = creattime;
	this.teacherTrainingRecordses = teacherTrainingRecordses;
	this.submits = submits;
	this.projectCollegeRanges = projectCollegeRanges;
	this.assignTeacherTasks = assignTeacherTasks;
	this.trainingAdmins = trainingAdmins;
	this.teachexperts = teachexperts;
	this.projectApplies = projectApplies;
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
    @JoinColumn(name = "AREA", nullable = false)
    public Area getArea()
    {
	return this.area;
    }

    public void setArea(Area area)
    {
	this.area = area;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_LEVEL", nullable = false)
    public ProjectLevel getProjectLevel()
    {
	return this.projectLevel;
    }

    public void setProjectLevel(ProjectLevel projectLevel)
    {
	this.projectLevel = projectLevel;
    }

    @Column(name = "NAME", nullable = false, length = 100)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "SHORT_NAME", length = 100)
    public String getShortName()
    {
	return this.shortName;
    }

    public void setShortName(String shortName)
    {
	this.shortName = shortName;
    }

    @Column(name = "ADRESS", length = 200)
    public String getAdress()
    {
	return this.adress;
    }

    public void setAdress(String adress)
    {
	this.adress = adress;
    }

    @Column(name = "CONTACTS", length = 50)
    public String getContacts()
    {
	return this.contacts;
    }

    public void setContacts(String contacts)
    {
	this.contacts = contacts;
    }

    @Column(name = "PHONE", length = 20)
    public String getPhone()
    {
	return this.phone;
    }

    public void setPhone(String phone)
    {
	this.phone = phone;
    }

    @Column(name = "FAX", length = 20)
    public String getFax()
    {
	return this.fax;
    }

    public void setFax(String fax)
    {
	this.fax = fax;
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

    @Column(name = "CREATOR")
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
	    mappedBy = "trainingCollege")
    public Set<TeacherTrainingRecords> getTeacherTrainingRecordses()
    {
	return this.teacherTrainingRecordses;
    }

    public void setTeacherTrainingRecordses(
	    Set<TeacherTrainingRecords> teacherTrainingRecordses)
    {
	this.teacherTrainingRecordses = teacherTrainingRecordses;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<Submit> getSubmits()
    {
	return this.submits;
    }

    public void setSubmits(Set<Submit> submits)
    {
	this.submits = submits;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<ProjectCollegeRange> getProjectCollegeRanges()
    {
	return this.projectCollegeRanges;
    }

    public void setProjectCollegeRanges(
	    Set<ProjectCollegeRange> projectCollegeRanges)
    {
	this.projectCollegeRanges = projectCollegeRanges;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<AssignTeacherTask> getAssignTeacherTasks()
    {
	return this.assignTeacherTasks;
    }

    public void setAssignTeacherTasks(Set<AssignTeacherTask> assignTeacherTasks)
    {
	this.assignTeacherTasks = assignTeacherTasks;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<TrainingAdmin> getTrainingAdmins()
    {
	return this.trainingAdmins;
    }

    public void setTrainingAdmins(Set<TrainingAdmin> trainingAdmins)
    {
	this.trainingAdmins = trainingAdmins;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<Teachexpert> getTeachexperts()
    {
	return this.teachexperts;
    }

    public void setTeachexperts(Set<Teachexpert> teachexperts)
    {
	this.teachexperts = teachexperts;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "trainingCollege")
    public Set<ProjectApply> getProjectApplies()
    {
	return this.projectApplies;
    }

    public void setProjectApplies(Set<ProjectApply> projectApplies)
    {
	this.projectApplies = projectApplies;
    }

}