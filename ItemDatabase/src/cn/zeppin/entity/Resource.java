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

import cn.zeppin.entity.base.BaseEntity;

/**
 * Resource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "resource")
public class Resource extends BaseEntity
{
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7702116968848124902L;
	private Integer id;
	private SysUser sysUser;
	private Short type;
	private String name;
	private String sourcePath;
	private String path;
	private String url;
	private String title;
	private String suffix;
	private Short status;
	private Timestamp createtime;
	private Integer fileSize;
	
	// Constructors
	
	/** default constructor */
	public Resource()
	{
	}
	
	/** minimal constructor */
	public Resource(SysUser sysUser, Short type, String name, String sourcePath, String title, String suffix, Short status, Timestamp createtime, Integer fileSize)
	{
		this.sysUser = sysUser;
		this.type = type;
		this.name = name;
		this.sourcePath = sourcePath;
		this.title = title;
		this.suffix = suffix;
		this.status = status;
		this.createtime = createtime;
		this.fileSize = fileSize;
	}
	
	/** full constructor */
	public Resource(SysUser sysUser, Short type, String name, String sourcePath, String path, String url, String title, String suffix, Short status, Timestamp createtime,
			Integer fileSize)
	{
		this.sysUser = sysUser;
		this.type = type;
		this.name = name;
		this.sourcePath = sourcePath;
		this.path = path;
		this.url = url;
		this.title = title;
		this.suffix = suffix;
		this.status = status;
		this.createtime = createtime;
		this.fileSize = fileSize;
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
	
	@Column(name = "FILESIZE", nullable = false)
	public Integer getFileSize()
	{
		return this.fileSize;
	}
	
	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(Integer fileSize)
	{
		this.fileSize = fileSize;
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
	
	@Column(name = "TYPE", nullable = false)
	public Short getType()
	{
		return this.type;
	}
	
	public void setType(Short type)
	{
		this.type = type;
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
	
	@Column(name = "SOURCE_PATH", nullable = false, length = 200)
	public String getSourcePath()
	{
		return this.sourcePath;
	}
	
	public void setSourcePath(String sourcePath)
	{
		this.sourcePath = sourcePath;
	}
	
	@Column(name = "PATH", length = 200)
	public String getPath()
	{
		return this.path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	@Column(name = "URL", length = 200)
	public String getUrl()
	{
		return this.url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	@Column(name = "TITLE", nullable = false, length = 200)
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Column(name = "SUFFIX", nullable = false, length = 10)
	public String getSuffix()
	{
		return this.suffix;
	}
	
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Short getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Short i)
	{
		this.status = i;
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