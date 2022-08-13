package entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SysUserId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class SysUserId implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String idcard;
    private String mobile;
    private String email;
    private String password;
    private Long role;
    private String name;
    private Long organization;
    private Long createuser;
    private Long level;
    private Short status;
    private Integer creator;
    private Timestamp creattime;

    // Constructors

    /** default constructor */
    public SysUserId()
    {
    }

    /** minimal constructor */
    public SysUserId(Integer id, String mobile, String password, Long role,
	    String name, Long organization, Long createuser, Long level,
	    Timestamp creattime)
    {
	this.id = id;
	this.mobile = mobile;
	this.password = password;
	this.role = role;
	this.name = name;
	this.organization = organization;
	this.createuser = createuser;
	this.level = level;
	this.creattime = creattime;
    }

    /** full constructor */
    public SysUserId(Integer id, String idcard, String mobile, String email,
	    String password, Long role, String name, Long organization,
	    Long createuser, Long level, Short status, Integer creator,
	    Timestamp creattime)
    {
	this.id = id;
	this.idcard = idcard;
	this.mobile = mobile;
	this.email = email;
	this.password = password;
	this.role = role;
	this.name = name;
	this.organization = organization;
	this.createuser = createuser;
	this.level = level;
	this.status = status;
	this.creator = creator;
	this.creattime = creattime;
    }

    // Property accessors

    @Column(name = "ID", nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(name = "IDCARD", length = 20)
    public String getIdcard()
    {
	return this.idcard;
    }

    public void setIdcard(String idcard)
    {
	this.idcard = idcard;
    }

    @Column(name = "MOBILE", nullable = false, length = 20)
    public String getMobile()
    {
	return this.mobile;
    }

    public void setMobile(String mobile)
    {
	this.mobile = mobile;
    }

    @Column(name = "EMAIL", length = 50)
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

    @Column(name = "ROLE", nullable = false)
    public Long getRole()
    {
	return this.role;
    }

    public void setRole(Long role)
    {
	this.role = role;
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

    @Column(name = "ORGANIZATION", nullable = false)
    public Long getOrganization()
    {
	return this.organization;
    }

    public void setOrganization(Long organization)
    {
	this.organization = organization;
    }

    @Column(name = "CREATEUSER", nullable = false)
    public Long getCreateuser()
    {
	return this.createuser;
    }

    public void setCreateuser(Long createuser)
    {
	this.createuser = createuser;
    }

    @Column(name = "LEVEL", nullable = false)
    public Long getLevel()
    {
	return this.level;
    }

    public void setLevel(Long level)
    {
	this.level = level;
    }

    @Column(name = "STATUS")
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

    public boolean equals(Object other)
    {
	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof SysUserId))
	    return false;
	SysUserId castOther = (SysUserId) other;

	return ((this.getId() == castOther.getId()) || (this.getId() != null
		&& castOther.getId() != null && this.getId().equals(
		castOther.getId())))
		&& ((this.getIdcard() == castOther.getIdcard()) || (this
			.getIdcard() != null && castOther.getIdcard() != null && this
			.getIdcard().equals(castOther.getIdcard())))
		&& ((this.getMobile() == castOther.getMobile()) || (this
			.getMobile() != null && castOther.getMobile() != null && this
			.getMobile().equals(castOther.getMobile())))
		&& ((this.getEmail() == castOther.getEmail()) || (this
			.getEmail() != null && castOther.getEmail() != null && this
			.getEmail().equals(castOther.getEmail())))
		&& ((this.getPassword() == castOther.getPassword()) || (this
			.getPassword() != null
			&& castOther.getPassword() != null && this
			.getPassword().equals(castOther.getPassword())))
		&& ((this.getRole() == castOther.getRole()) || (this.getRole() != null
			&& castOther.getRole() != null && this.getRole()
			.equals(castOther.getRole())))
		&& ((this.getName() == castOther.getName()) || (this.getName() != null
			&& castOther.getName() != null && this.getName()
			.equals(castOther.getName())))
		&& ((this.getOrganization() == castOther.getOrganization()) || (this
			.getOrganization() != null
			&& castOther.getOrganization() != null && this
			.getOrganization().equals(castOther.getOrganization())))
		&& ((this.getCreateuser() == castOther.getCreateuser()) || (this
			.getCreateuser() != null
			&& castOther.getCreateuser() != null && this
			.getCreateuser().equals(castOther.getCreateuser())))
		&& ((this.getLevel() == castOther.getLevel()) || (this
			.getLevel() != null && castOther.getLevel() != null && this
			.getLevel().equals(castOther.getLevel())))
		&& ((this.getStatus() == castOther.getStatus()) || (this
			.getStatus() != null && castOther.getStatus() != null && this
			.getStatus().equals(castOther.getStatus())))
		&& ((this.getCreator() == castOther.getCreator()) || (this
			.getCreator() != null && castOther.getCreator() != null && this
			.getCreator().equals(castOther.getCreator())))
		&& ((this.getCreattime() == castOther.getCreattime()) || (this
			.getCreattime() != null
			&& castOther.getCreattime() != null && this
			.getCreattime().equals(castOther.getCreattime())));
    }

    public int hashCode()
    {
	int result = 17;

	result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
	result = 37 * result
		+ (getIdcard() == null ? 0 : this.getIdcard().hashCode());
	result = 37 * result
		+ (getMobile() == null ? 0 : this.getMobile().hashCode());
	result = 37 * result
		+ (getEmail() == null ? 0 : this.getEmail().hashCode());
	result = 37 * result
		+ (getPassword() == null ? 0 : this.getPassword().hashCode());
	result = 37 * result
		+ (getRole() == null ? 0 : this.getRole().hashCode());
	result = 37 * result
		+ (getName() == null ? 0 : this.getName().hashCode());
	result = 37
		* result
		+ (getOrganization() == null ? 0 : this.getOrganization()
			.hashCode());
	result = 37
		* result
		+ (getCreateuser() == null ? 0 : this.getCreateuser()
			.hashCode());
	result = 37 * result
		+ (getLevel() == null ? 0 : this.getLevel().hashCode());
	result = 37 * result
		+ (getStatus() == null ? 0 : this.getStatus().hashCode());
	result = 37 * result
		+ (getCreator() == null ? 0 : this.getCreator().hashCode());
	result = 37 * result
		+ (getCreattime() == null ? 0 : this.getCreattime().hashCode());
	return result;
    }

}