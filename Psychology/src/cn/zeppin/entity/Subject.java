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
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

/**
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Subject extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4200511060368407668L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private Category category;
	private String name;
	private Short status;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** minimal constructor */
	public Subject(SysUser sysUser, Category category, String name,
			Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.category = category;
		this.name = name;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Subject(SysUser sysUser, Grade grade, Category category,
			String name, Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.category = category;
		this.name = name;
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
	@JoinColumn(name = "GRADE")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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