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
 * Knowladge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "knowledge")
public class Knowledge extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5324728705914984040L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private Knowledge knowledge;
	private Subject subject;
	private String name;
	private Short level;
	private Short status;
	private String scode;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Knowledge() {
	}

	/** minimal constructor */
	public Knowledge(SysUser sysUser, Grade grade, Subject subject,
			String name, Short level, Short status, String scode, Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.subject = subject;
		this.name = name;
		this.level = level;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Knowledge(SysUser sysUser, Grade grade, Knowledge knowledge,
			Subject subject, String name, Short level, Short status, String scode,
			Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.knowledge = knowledge;
		this.subject = subject;
		this.name = name;
		this.level = level;
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
	@JoinColumn(name = "PARENT")
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LEVEL", nullable = false)
	public Short getLevel() {
		return this.level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "SCODE", nullable = true)
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
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