package cn.zeppin.entity;

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
 * SysUserGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user_grade", uniqueConstraints = @UniqueConstraint(columnNames = {
		"SYS_USER", "GRADE" }))
public class SysUserGrade extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5605116971947115887L;
	private Integer id;
	private Grade grade;
	private SysUser sysUser;

	// Constructors

	/** default constructor */
	public SysUserGrade() {
	}

	/** full constructor */
	public SysUserGrade(Grade grade, SysUser sysUser) {
		this.grade = grade;
		this.sysUser = sysUser;
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
	@JoinColumn(name = "GRADE", nullable = false)
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SYS_USER", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
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