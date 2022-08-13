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
import javax.persistence.UniqueConstraint;

/**
 * Teachexpert entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teachexpert", catalog = "xjtts", uniqueConstraints = {
	@UniqueConstraint(columnNames = "IDCARD"),
	@UniqueConstraint(columnNames = "MOBILE") })
public class Teachexpert implements java.io.Serializable
{

    // Fields

    private Integer id;
    private EductionBackground eductionBackground;
    private Area area;
    private Ethnic ethnic;
    private TrainingCollege trainingCollege;
    private String name;
    private String idcard;
    private String jobDuty;
    private String jobTitle;
    private Short educationage;
    private String mobile;
    private String phone;
    private String fax;
    private String address;
    private String politics;
    private String email;
    private String research;
    private String teach;
    private String remark;
    private String resume;
    private String achievement;
    private String experience;
    private String other;
    private Integer creator;
    private Timestamp creattime;

    // Constructors

    /** default constructor */
    public Teachexpert()
    {
    }

    /** minimal constructor */
    public Teachexpert(EductionBackground eductionBackground, Area area,
	    Ethnic ethnic, TrainingCollege trainingCollege, String name,
	    String idcard, Short educationage, String mobile, String politics,
	    String email, Integer creator, Timestamp creattime)
    {
	this.eductionBackground = eductionBackground;
	this.area = area;
	this.ethnic = ethnic;
	this.trainingCollege = trainingCollege;
	this.name = name;
	this.idcard = idcard;
	this.educationage = educationage;
	this.mobile = mobile;
	this.politics = politics;
	this.email = email;
	this.creator = creator;
	this.creattime = creattime;
    }

    /** full constructor */
    public Teachexpert(EductionBackground eductionBackground, Area area,
	    Ethnic ethnic, TrainingCollege trainingCollege, String name,
	    String idcard, String jobDuty, String jobTitle, Short educationage,
	    String mobile, String phone, String fax, String address,
	    String politics, String email, String research, String teach,
	    String remark, String resume, String achievement,
	    String experience, String other, Integer creator,
	    Timestamp creattime)
    {
	this.eductionBackground = eductionBackground;
	this.area = area;
	this.ethnic = ethnic;
	this.trainingCollege = trainingCollege;
	this.name = name;
	this.idcard = idcard;
	this.jobDuty = jobDuty;
	this.jobTitle = jobTitle;
	this.educationage = educationage;
	this.mobile = mobile;
	this.phone = phone;
	this.fax = fax;
	this.address = address;
	this.politics = politics;
	this.email = email;
	this.research = research;
	this.teach = teach;
	this.remark = remark;
	this.resume = resume;
	this.achievement = achievement;
	this.experience = experience;
	this.other = other;
	this.creator = creator;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDUCTION", nullable = false)
    public EductionBackground getEductionBackground()
    {
	return this.eductionBackground;
    }

    public void setEductionBackground(EductionBackground eductionBackground)
    {
	this.eductionBackground = eductionBackground;
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
    @JoinColumn(name = "NATIONALLY", nullable = false)
    public Ethnic getEthnic()
    {
	return this.ethnic;
    }

    public void setEthnic(Ethnic ethnic)
    {
	this.ethnic = ethnic;
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

    @Column(name = "NAME", nullable = false, length = 30)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "IDCARD", unique = true, nullable = false, length = 20)
    public String getIdcard()
    {
	return this.idcard;
    }

    public void setIdcard(String idcard)
    {
	this.idcard = idcard;
    }

    @Column(name = "JOB_DUTY", length = 20)
    public String getJobDuty()
    {
	return this.jobDuty;
    }

    public void setJobDuty(String jobDuty)
    {
	this.jobDuty = jobDuty;
    }

    @Column(name = "JOB_TITLE", length = 20)
    public String getJobTitle()
    {
	return this.jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
	this.jobTitle = jobTitle;
    }

    @Column(name = "EDUCATIONAGE", nullable = false)
    public Short getEducationage()
    {
	return this.educationage;
    }

    public void setEducationage(Short educationage)
    {
	this.educationage = educationage;
    }

    @Column(name = "MOBILE", unique = true, nullable = false, length = 20)
    public String getMobile()
    {
	return this.mobile;
    }

    public void setMobile(String mobile)
    {
	this.mobile = mobile;
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

    @Column(name = "ADDRESS", length = 200)
    public String getAddress()
    {
	return this.address;
    }

    public void setAddress(String address)
    {
	this.address = address;
    }

    @Column(name = "POLITICS", nullable = false, length = 20)
    public String getPolitics()
    {
	return this.politics;
    }

    public void setPolitics(String politics)
    {
	this.politics = politics;
    }

    @Column(name = "EMAIL", nullable = false, length = 50)
    public String getEmail()
    {
	return this.email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    @Column(name = "RESEARCH", length = 200)
    public String getResearch()
    {
	return this.research;
    }

    public void setResearch(String research)
    {
	this.research = research;
    }

    @Column(name = "TEACH", length = 200)
    public String getTeach()
    {
	return this.teach;
    }

    public void setTeach(String teach)
    {
	this.teach = teach;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }

    @Column(name = "RESUME", length = 1000)
    public String getResume()
    {
	return this.resume;
    }

    public void setResume(String resume)
    {
	this.resume = resume;
    }

    @Column(name = "ACHIEVEMENT", length = 1000)
    public String getAchievement()
    {
	return this.achievement;
    }

    public void setAchievement(String achievement)
    {
	this.achievement = achievement;
    }

    @Column(name = "EXPERIENCE", length = 1000)
    public String getExperience()
    {
	return this.experience;
    }

    public void setExperience(String experience)
    {
	this.experience = experience;
    }

    @Column(name = "OTHER", length = 1000)
    public String getOther()
    {
	return this.other;
    }

    public void setOther(String other)
    {
	this.other = other;
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

}