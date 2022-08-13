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
 * Paper entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper")
public class Paper extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1299689983501351359L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private Subject subject;
	private Area area;
	private String year;
	private Short type;
	private String name;
	private String source;
	private Integer answerTime;
	private Short totalScore;
	private String cover;
	private Short status;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Paper() {
	}

	/** minimal constructor */
	public Paper(SysUser sysUser, Short type, String name, String source, Integer answerTime, Short totalScore, Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.type = type;
		this.name = name;
		this.source = source;
		this.answerTime = answerTime;
		this.totalScore = totalScore;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Paper(SysUser sysUser, Grade grade, Subject subject, Short type, String name, String source, Integer answerTime, Short totalScore, String cover, Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.subject = subject;
		this.type = type;
		this.name = name;
		this.source = source;
		this.answerTime = answerTime;
		this.totalScore = totalScore;
		this.cover = cover;
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
	@JoinColumn(name = "CREATER")
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
	@JoinColumn(name = "SUBJECT")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SOURCE", nullable = false, length = 200)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "ANSWER_TIME", nullable = false)
	public Integer getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	@Column(name = "TOTAL_SCORE", nullable = false)
	public Short getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Short totalScore) {
		this.totalScore = totalScore;
	}

	@Column(name = "COVER", length = 200)
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AREA")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Column(name = "YEAR", length = 4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}