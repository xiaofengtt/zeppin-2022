package cn.zeppin.entity;

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

import cn.zeppin.entity.base.BaseEntity;

/**
 * Textbook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "textbook", catalog = "cetv")
public class Textbook extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1298298744722096907L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private Subject subject;
	private Resource resource;
	private String name;
	private String publisher;
	private String version;
	private Short status;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Textbook() {
	}

	/** minimal constructor */
	public Textbook(SysUser sysUser, Grade grade, Subject subject, String name,
			String publisher, String version, Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.subject = subject;
		this.name = name;
		this.publisher = publisher;
		this.version = version;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Textbook(SysUser sysUser, Grade grade, Subject subject,
			Resource resource, String name, String publisher, String version,
			Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.subject = subject;
		this.resource = resource;
		this.name = name;
		this.publisher = publisher;
		this.version = version;
		this.status = status;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATER", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GRADE", nullable = false)
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COVER")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PUBLISHER", nullable = false, length = 100)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "VERSION", nullable = false, length = 30)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
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