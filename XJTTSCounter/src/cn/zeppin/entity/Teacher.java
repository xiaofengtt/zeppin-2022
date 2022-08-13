package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher", uniqueConstraints = {
	@UniqueConstraint(columnNames = "IDCARD"),
	@UniqueConstraint(columnNames = "EMAIL"),
	@UniqueConstraint(columnNames = "MOBILE") })
public class Teacher implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Politics politics;
    private EductionBackground eductionBackground;
    private Area area;
    private JobTitle jobTitle;
    private JobDuty jobDuty;
    private Ethnic ethnic;
    private Organization organization;
    private ChineseLanguageLevel chineseLanguageLevel;
    private String name;
    private String idcard;
    private String mobile;
    private String password;
    private String email;
    private Short sex;
    private Date birthday;
    private Date teachingAge;
    private Boolean multiLanguage;
    private String headPicPath;
    private String remark1;
    private String remark2;
    private Integer creator;
    private Timestamp creattime;
    private Short status;
    private Short authorized;
    private String updater;
    private Set<TeachingGrade> teachingGrades = new HashSet<TeachingGrade>(0);
    private Set<TeachingSubject> teachingSubjects = new HashSet<TeachingSubject>(
	    0);
    private Set<TeachingLanguage> teachingLanguages = new HashSet<TeachingLanguage>(
	    0);
    private Set<TeacherTrainingRecords> teacherTrainingRecordses = new HashSet<TeacherTrainingRecords>(
	    0);
    
    private Short isFirstLogin;
    
    private String graduation;//毕业院校
    private String major;//所学专业
    
    private Short isTeachingSchool;//是否是教学点教师
    
//    private String uuid;

    // Constructors

    /** default constructor */
    public Teacher()
    {
    }

    /** minimal constructor */
    public Teacher(EductionBackground eductionBackground, Area area,
	    JobTitle jobTitle, JobDuty jobDuty, Ethnic ethnic,
	    Organization organization,
	    ChineseLanguageLevel chineseLanguageLevel, String name,
	    String idcard, String mobile, String password, String email,
	    Short sex, Date birthday, Date teachingAge, Boolean multiLanguage,
	    Integer creator, Timestamp creattime, Short status, Short authorized)
    {
	this.eductionBackground = eductionBackground;
	this.area = area;
	this.jobTitle = jobTitle;
	this.jobDuty = jobDuty;
	this.ethnic = ethnic;
	this.organization = organization;
	this.chineseLanguageLevel = chineseLanguageLevel;
	this.name = name;
	this.idcard = idcard;
	this.mobile = mobile;
	this.password = password;
	this.email = email;
	this.sex = sex;
	this.birthday = birthday;
	this.teachingAge = teachingAge;
	this.multiLanguage = multiLanguage;
	this.creator = creator;
	this.creattime = creattime;
	this.status = status;
	this.authorized = authorized;
    }

    /** full constructor */
    public Teacher(Politics politics, EductionBackground eductionBackground,
	    Area area, JobTitle jobTitle, JobDuty jobDuty, Ethnic ethnic,
	    Organization organization,
	    ChineseLanguageLevel chineseLanguageLevel, String name,
	    String idcard, String mobile, String password, String email,
	    Short sex, Date birthday, Date teachingAge, Boolean multiLanguage,
	    String headPicPath, String remark1, String remark2,
	    Integer creator, Timestamp creattime, Short status, Short authorized,
	    Set<TeachingGrade> teachingGrades,
	    Set<TeachingSubject> teachingSubjects,
	    Set<TeachingLanguage> teachingLanguages,
	    Set<TeacherTrainingRecords> teacherTrainingRecordses,
	    Short isFirstLogin,String graduation,String major,Short isTeachingSchool)
    {
	this.politics = politics;
	this.eductionBackground = eductionBackground;
	this.area = area;
	this.jobTitle = jobTitle;
	this.jobDuty = jobDuty;
	this.ethnic = ethnic;
	this.organization = organization;
	this.chineseLanguageLevel = chineseLanguageLevel;
	this.name = name;
	this.idcard = idcard;
	this.mobile = mobile;
	this.password = password;
	this.email = email;
	this.sex = sex;
	this.birthday = birthday;
	this.teachingAge = teachingAge;
	this.multiLanguage = multiLanguage;
	this.headPicPath = headPicPath;
	this.remark1 = remark1;
	this.remark2 = remark2;
	this.creator = creator;
	this.creattime = creattime;
	this.status = status;
	this.authorized = authorized;
	this.teachingGrades = teachingGrades;
	this.teachingSubjects = teachingSubjects;
	this.teachingLanguages = teachingLanguages;
	this.teacherTrainingRecordses = teacherTrainingRecordses;
	this.isFirstLogin = isFirstLogin;
	this.graduation = graduation;
	this.graduation = major;
	this.isTeachingSchool = isTeachingSchool;
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
    @JoinColumn(name = "POLITICS")
    public Politics getPolitics()
    {
	return this.politics;
    }

    public void setPolitics(Politics politics)
    {
	this.politics = politics;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDUCTION_BACKGROUND", nullable = false)
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
    @JoinColumn(name = "JOB_TITLE", nullable = false)
    public JobTitle getJobTitle()
    {
	return this.jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle)
    {
	this.jobTitle = jobTitle;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_DUTY", nullable = false)
    public JobDuty getJobDuty()
    {
	return this.jobDuty;
    }

    public void setJobDuty(JobDuty jobDuty)
    {
	this.jobDuty = jobDuty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ETHNIC", nullable = false)
    public Ethnic getEthnic()
    {
	return this.ethnic;
    }

    public void setEthnic(Ethnic ethnic)
    {
	this.ethnic = ethnic;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION", nullable = false)
    public Organization getOrganization()
    {
	return this.organization;
    }

    public void setOrganization(Organization organization)
    {
	this.organization = organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHINESE_LANGUAGE_LEVEL", nullable = false)
    public ChineseLanguageLevel getChineseLanguageLevel()
    {
	return this.chineseLanguageLevel;
    }

    public void setChineseLanguageLevel(
	    ChineseLanguageLevel chineseLanguageLevel)
    {
	this.chineseLanguageLevel = chineseLanguageLevel;
    }

    @Column(name = "NAME", nullable = false, length = 50)
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

    @Column(name = "MOBILE", unique = true, nullable = false, length = 20)
    public String getMobile()
    {
	return this.mobile;
    }

    public void setMobile(String mobile)
    {
	this.mobile = mobile;
    }

    @Column(name = "PASSWORD", nullable = false, length = 20)
    public String getPassword()
    {
	return this.password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    public String getEmail()
    {
	return this.email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    @Column(name = "SEX", nullable = false)
    public Short getSex()
    {
	return this.sex;
    }

    public void setSex(Short sex)
    {
	this.sex = sex;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY", nullable = false, length = 10)
    public Date getBirthday()
    {
	return this.birthday;
    }

    public void setBirthday(Date birthday)
    {
	this.birthday = birthday;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TEACHING_AGE", nullable = false, length = 10)
    public Date getTeachingAge()
    {
	return this.teachingAge;
    }

    public void setTeachingAge(Date teachingAge)
    {
	this.teachingAge = teachingAge;
    }

    @Column(name = "MULTI_LANGUAGE", nullable = false)
    public Boolean getMultiLanguage()
    {
	return this.multiLanguage;
    }

    public void setMultiLanguage(Boolean multiLanguage)
    {
	this.multiLanguage = multiLanguage;
    }

    @Column(name = "HEAD_PIC_PATH")
    public String getHeadPicPath()
    {
	return this.headPicPath;
    }

    public void setHeadPicPath(String headPicPath)
    {
	this.headPicPath = headPicPath;
    }

    @Column(name = "REMARK1", length = 200)
    public String getRemark1()
    {
	return this.remark1;
    }

    public void setRemark1(String remark1)
    {
	this.remark1 = remark1;
    }

    @Column(name = "REMARK2", length = 200)
    public String getRemark2()
    {
	return this.remark2;
    }

    public void setRemark2(String remark2)
    {
	this.remark2 = remark2;
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

    @Column(name = "STATUS", nullable = false)
    public Short getStatus()
    {
	return this.status;
    }

    public void setStatus(Short status)
    {
	this.status = status;
    }

    @Column(name = "AUTHORIZED", nullable = false)
    public Short getAuthorized()
    {
	return this.authorized;
    }

    public void setAuthorized(Short authorized)
    {
	this.authorized = authorized;
    }
    
    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<TeachingGrade> getTeachingGrades()
    {
	return this.teachingGrades;
    }

    public void setTeachingGrades(Set<TeachingGrade> teachingGrades)
    {
	this.teachingGrades = teachingGrades;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<TeachingSubject> getTeachingSubjects()
    {
	return this.teachingSubjects;
    }

    public void setTeachingSubjects(Set<TeachingSubject> teachingSubjects)
    {
	this.teachingSubjects = teachingSubjects;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<TeachingLanguage> getTeachingLanguages()
    {
	return this.teachingLanguages;
    }

    public void setTeachingLanguages(Set<TeachingLanguage> teachingLanguages)
    {
	this.teachingLanguages = teachingLanguages;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "teacher")
    public Set<TeacherTrainingRecords> getTeacherTrainingRecordses()
    {
	return this.teacherTrainingRecordses;
    }

    public void setTeacherTrainingRecordses(
	    Set<TeacherTrainingRecords> teacherTrainingRecordses)
    {
	this.teacherTrainingRecordses = teacherTrainingRecordses;
    }

	
    @Column(name = "UPDATER")
    public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "IS_FIRST_LOGIN", nullable = false)
	public Short getIsFirstLogin() {
		return isFirstLogin;
	}
	
	public void setIsFirstLogin(Short isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	
	@Column(name = "GRADUATION")
	public String getGraduation() {
		return graduation;
	}

	
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	
	@Column(name = "MAJOR")
	public String getMajor() {
		return major;
	}

	
	public void setMajor(String major) {
		this.major = major;
	}

	
	@Column(name = "IS_TEACHING_SCHOOL", nullable = false)
	public Short getIsTeachingSchool() {
		return isTeachingSchool;
	}
	

	public void setIsTeachingSchool(Short isTeachingSchool) {
		this.isTeachingSchool = isTeachingSchool;
	}


}