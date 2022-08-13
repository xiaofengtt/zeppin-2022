package cn.zeppin.entity ;

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

/**
 * SubjectCountdown entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject_countdown")
public class SubjectCountdown implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3816000687615537131L;
	private Integer id;
	private SysUser sysUser;
	private Subject subject;
	private Timestamp examTime;
	private Timestamp createTime;
	private Short status;

	// Constructors

	/** default constructor */
	public SubjectCountdown() {
	}

	/** full constructor */
	public SubjectCountdown(SysUser sysUser, Subject subject, Timestamp examTime, Timestamp createTime, Short status) {
		this.sysUser = sysUser;
		this.subject = subject;
		this.examTime = examTime;
		this.createTime = createTime;
		this.status = status;
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
	@JoinColumn(name = "SUBJECTID", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "EXAM_TIME", nullable = false, length = 19)
	public Timestamp getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Timestamp examTime) {
		this.examTime = examTime;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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