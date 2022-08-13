package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

/**
 * Grade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "grade", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Grade extends BaseEntity
{
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7905885378369574409L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private String name;
	private Short level;
	private Short status;
	private Timestamp createtime;
	// private Integer sortNum;
	private String scode;
	
	// Constructors
	
	/** default constructor */
	public Grade()
	{
	}
	
	/** minimal constructor */
	public Grade(SysUser sysUser, String name, Short level, Short status, Timestamp createtime, String scode)
	{
		this.sysUser = sysUser;
		this.name = name;
		this.level = level;
		this.status = status;
		this.createtime = createtime;
		this.scode = scode;
		// this.sortNum = sortNum;
	}
	
	/** full constructor */
	public Grade(SysUser sysUser, Grade grade, String name, Short level, Short status, Timestamp createtime, String scode)
	{
		this.sysUser = sysUser;
		this.grade = grade;
		this.name = name;
		this.level = level;
		this.status = status;
		this.createtime = createtime;
		this.scode = scode;
		// this.sortNum = sortNum;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATER", nullable = false)
	public SysUser getSysUser()
	{
		return this.sysUser;
	}
	
	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "PARENT")
	public Grade getGrade()
	{
		return this.grade;
	}
	
	public void setGrade(Grade grade)
	{
		this.grade = grade;
	}
	
	@Column(name = "NAME", unique = true, nullable = false, length = 50)
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the scode
	 */
	@Column(name = "SCODE", unique = true, nullable = false, length = 100)
	public String getScode()
	{
		return scode;
	}
	
	/**
	 * @param scode
	 *            the scode to set
	 */
	public void setScode(String scode)
	{
		this.scode = scode;
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
	
	@Column(name = "STATUS", nullable = false)
	public Short getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Short status)
	{
		this.status = status;
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
	
	/**
	 * cacheKey
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}