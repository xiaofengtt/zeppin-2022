package com.zeppin.entiey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Sso entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sso", catalog = "cac", uniqueConstraints = @UniqueConstraint(
	columnNames = "userCode"))
public class Sso implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String userCode;
    private short userType;
    private String pwd;
    private Date createDate;
    private Date modifiDate;
    private Set<SsoMessageMap> ssoMessageMaps = new HashSet<SsoMessageMap>(0);

    // Constructors

    /** default constructor */
    public Sso()
    {
    }

    /** minimal constructor */
    public Sso(String userCode, short userType, String pwd, Date createDate)
    {
	this.userCode = userCode;
	this.userType = userType;
	this.pwd = pwd;
	this.createDate = createDate;
    }

    /** full constructor */
    public Sso(String userCode, short userType, String pwd, Date createDate,
	    Date modifiDate, Set<Message> messages,
	    Set<HomeworkAccessoryMap> homeworkAccessoryMaps,
	    Set<SsoMessageMap> ssoMessageMaps)
    {
	this.userCode = userCode;
	this.userType = userType;
	this.pwd = pwd;
	this.createDate = createDate;
	this.modifiDate = modifiDate;
	this.ssoMessageMaps = ssoMessageMaps;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(name = "userCode", unique = true, nullable = false, length = 32)
    public String getUserCode()
    {
	return this.userCode;
    }

    public void setUserCode(String userCode)
    {
	this.userCode = userCode;
    }

    @Column(name = "userType", nullable = false)
    public short getUserType()
    {
	return this.userType;
    }

    public void setUserType(short userType)
    {
	this.userType = userType;
    }

    @Column(name = "pwd", nullable = false, length = 64)
    public String getPwd()
    {
	return this.pwd;
    }

    public void setPwd(String pwd)
    {
	this.pwd = pwd;
    }

    @Column(name = "createDate", nullable = false, length = 19)
    public Date getCreateDate()
    {
	return this.createDate;
    }

    public void setCreateDate(Date createDate)
    {
	this.createDate = createDate;
    }

    @Column(name = "modifiDate", length = 19)
    public Date getModifiDate()
    {
	return this.modifiDate;
    }

    public void setModifiDate(Date modifiDate)
    {
	this.modifiDate = modifiDate;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "sso")
    public Set<SsoMessageMap> getSsoMessageMaps()
    {
	return this.ssoMessageMaps;
    }

    public void setSsoMessageMaps(Set<SsoMessageMap> ssoMessageMaps)
    {
	this.ssoMessageMaps = ssoMessageMaps;
    }

}