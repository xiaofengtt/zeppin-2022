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

/**
 * Hsdtest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "hsdtest")
public class Hsdtest implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7735192049239591483L;
	private Integer id;
	private Hsdtestscore hsdtestscoreByChange;
	private Hsdtestscore hsdtestscoreByOptimize;
	private Teacher teacher;
	private String st;
	private String asw;
	private String reason;
	private String recommend;
	private String suggest;
	private Timestamp createtime;
	private int year;

	// Constructors

	/** default constructor */
	public Hsdtest() {
	}

	/** minimal constructor */
	public Hsdtest(Hsdtestscore hsdtestscoreByChange, Hsdtestscore hsdtestscoreByOptimize, Teacher teacher, String st, String asw, String reason, String recommend, Timestamp createtime) {
		this.hsdtestscoreByChange = hsdtestscoreByChange;
		this.hsdtestscoreByOptimize = hsdtestscoreByOptimize;
		this.teacher = teacher;
		this.st = st;
		this.asw = asw;
		this.reason = reason;
		this.recommend = recommend;
		this.createtime = createtime;
	}

	/** full constructor */
	public Hsdtest(Hsdtestscore hsdtestscoreByChange, Hsdtestscore hsdtestscoreByOptimize, Teacher teacher, String st, String asw, String reason, String recommend, String suggest, Timestamp createtime) {
		this.hsdtestscoreByChange = hsdtestscoreByChange;
		this.hsdtestscoreByOptimize = hsdtestscoreByOptimize;
		this.teacher = teacher;
		this.st = st;
		this.asw = asw;
		this.reason = reason;
		this.recommend = recommend;
		this.suggest = suggest;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "year", nullable = false)
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch")
	public Hsdtestscore getHsdtestscoreByChange() {
		return this.hsdtestscoreByChange;
	}

	public void setHsdtestscoreByChange(Hsdtestscore hsdtestscoreByChange) {
		this.hsdtestscoreByChange = hsdtestscoreByChange;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "op")
	public Hsdtestscore getHsdtestscoreByOptimize() {
		return this.hsdtestscoreByOptimize;
	}

	public void setHsdtestscoreByOptimize(Hsdtestscore hsdtestscoreByOptimize) {
		this.hsdtestscoreByOptimize = hsdtestscoreByOptimize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "st", nullable = false, length = 1000)
	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Column(name = "asw", nullable = false, length = 1000)
	public String getAsw() {
		return this.asw;
	}

	public void setAsw(String asw) {
		this.asw = asw;
	}

	@Column(name = "reason", nullable = false, length = 1000)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "recommend", nullable = false, length = 1000)
	public String getRecommend() {
		return this.recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	@Column(name = "suggest", length = 1000)
	public String getSuggest() {
		return this.suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}