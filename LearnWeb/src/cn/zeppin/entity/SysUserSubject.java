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
 * SysUserSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user_subject", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = {
		"SYS_USER", "SUB_ID" }))
public class SysUserSubject extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6475268306207876541L;
	private Integer id;
	private Subject subject;
	private SysUser sysUser;

	// Constructors

	/** default constructor */
	public SysUserSubject() {
	}

	/** full constructor */
	public SysUserSubject(Subject subject, SysUser sysUser) {
		this.subject = subject;
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
	@JoinColumn(name = "SUB_ID", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
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