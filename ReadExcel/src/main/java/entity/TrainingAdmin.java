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
 * TrainingAdmin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "training_admin", catalog = "xjtts", uniqueConstraints = {
	@UniqueConstraint(columnNames = "IDCARD"),
	@UniqueConstraint(columnNames = "EMAIL"),
	@UniqueConstraint(columnNames = "MOBILE") })
public class TrainingAdmin implements java.io.Serializable
{

    // Fields

    private Integer id;
    private Politics politics;
    private Area area;
    private TrainingCollege trainingCollege;
    private Ethnic ethnic;
    private String name;
    private String idcard;
    private String mobile;
    private String email;
    private String password;
    private Short sex;
    private String department;
    private Boolean createuser;
    private Boolean restrictRight;
    private String phone;
    private String fax;
    private String jobDuty;
    private String address;
    private String postcode;
    private Short status;
    private String remark;
    private Integer creator;
    private Timestamp creattime;

    // Constructors

    /** default constructor */
    public TrainingAdmin()
    {
    }

    /** minimal constructor */
    public TrainingAdmin(TrainingCollege trainingCollege, Ethnic ethnic,
	    String name, String idcard, String mobile, String email,
	    String password, Short sex, Boolean createuser,
	    Boolean restrictRight, String phone, Short status,
	    Timestamp creattime)
    {
	this.trainingCollege = trainingCollege;
	this.ethnic = ethnic;
	this.name = name;
	this.idcard = idcard;
	this.mobile = mobile;
	this.email = email;
	this.password = password;
	this.sex = sex;
	this.createuser = createuser;
	this.restrictRight = restrictRight;
	this.phone = phone;
	this.status = status;
	this.creattime = creattime;
    }

    /** full constructor */
    public TrainingAdmin(Politics politics, Area area,
	    TrainingCollege trainingCollege, Ethnic ethnic, String name,
	    String idcard, String mobile, String email, String password,
	    Short sex, String department, Boolean createuser,
	    Boolean restrictRight, String phone, String fax, String jobDuty,
	    String address, String postcode, Short status, String remark,
	    Integer creator, Timestamp creattime)
    {
	this.politics = politics;
	this.area = area;
	this.trainingCollege = trainingCollege;
	this.ethnic = ethnic;
	this.name = name;
	this.idcard = idcard;
	this.mobile = mobile;
	this.email = email;
	this.password = password;
	this.sex = sex;
	this.department = department;
	this.createuser = createuser;
	this.restrictRight = restrictRight;
	this.phone = phone;
	this.fax = fax;
	this.jobDuty = jobDuty;
	this.address = address;
	this.postcode = postcode;
	this.status = status;
	this.remark = remark;
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
    @JoinColumn(name = "AREA")
    public Area getArea()
    {
	return this.area;
    }

    public void setArea(Area area)
    {
	this.area = area;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION", nullable = false)
    public TrainingCollege getTrainingCollege()
    {
	return this.trainingCollege;
    }

    public void setTrainingCollege(TrainingCollege trainingCollege)
    {
	this.trainingCollege = trainingCollege;
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

    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    public String getEmail()
    {
	return this.email;
    }

    public void setEmail(String email)
    {
	this.email = email;
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

    @Column(name = "SEX", nullable = false)
    public Short getSex()
    {
	return this.sex;
    }

    public void setSex(Short sex)
    {
	this.sex = sex;
    }

    @Column(name = "DEPARTMENT", length = 100)
    public String getDepartment()
    {
	return this.department;
    }

    public void setDepartment(String department)
    {
	this.department = department;
    }

    @Column(name = "CREATEUSER", nullable = false)
    public Boolean getCreateuser()
    {
	return this.createuser;
    }

    public void setCreateuser(Boolean createuser)
    {
	this.createuser = createuser;
    }

    @Column(name = "RESTRICT_RIGHT", nullable = false)
    public Boolean getRestrictRight()
    {
	return this.restrictRight;
    }

    public void setRestrictRight(Boolean restrictRight)
    {
	this.restrictRight = restrictRight;
    }

    @Column(name = "PHONE", nullable = false, length = 20)
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

    @Column(name = "JOB_DUTY", length = 20)
    public String getJobDuty()
    {
	return this.jobDuty;
    }

    public void setJobDuty(String jobDuty)
    {
	this.jobDuty = jobDuty;
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

    @Column(name = "POSTCODE", length = 10)
    public String getPostcode()
    {
	return this.postcode;
    }

    public void setPostcode(String postcode)
    {
	this.postcode = postcode;
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

    @Column(name = "REMARK", length = 200)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
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

}