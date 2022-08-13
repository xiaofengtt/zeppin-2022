package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

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
@Table(name = "publisher", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Publisher extends BaseEntity
{
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7905885378369574409L;
	private Integer id;
	private SysUser sysUser;
	private String name;
	private Short status;
	private Timestamp createtime;
	private String fcode;
	
	// Constructors
	
	/** default constructor */
	public Publisher()
	{
	}
	
	/** full constructor */
	public Publisher(SysUser sysUser,String name, Short status, Timestamp createtime, String fcode)
	{
		this.sysUser = sysUser;
		this.name = name;
		this.status = status;
		this.createtime = createtime;
		this.fcode = fcode;
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

	@Column(name = "NAME", nullable = false, length = 50)
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
	@Column(name = "FCODE", nullable = false, length = 50)
	public String getFcode()
	{
		return fcode;
	}
	
	/**
	 * @param scode
	 *            the scode to set
	 */
	public void setFcode(String fcode)
	{
		this.fcode = fcode;
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