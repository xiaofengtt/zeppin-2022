package cn.zeppin.entity ;

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
 * SysUserSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user_subject", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_ID", "SUB_ID" }))
public class SysUserSubject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3041971087415230999L;
	private Integer id;
	private SysUser sysUser;
	private Subject subject;

	// Constructors

	/** default constructor */
	public SysUserSubject() {
	}

	/** full constructor */
	public SysUserSubject(SysUser sysUser, Subject subject) {
		this.sysUser = sysUser;
		this.subject = subject;
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
	@JoinColumn(name = "USER_ID", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUB_ID", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}