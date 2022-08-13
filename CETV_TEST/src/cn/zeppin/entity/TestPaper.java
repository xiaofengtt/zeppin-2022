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
 * TestPaper entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_paper", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PAPER", "TEST" }))
public class TestPaper extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 733338760438228840L;
	private Integer id;
	private Test test;
	private Paper paper;

	// Constructors

	/** default constructor */
	public TestPaper() {
	}

	/** full constructor */
	public TestPaper(Test test, Paper paper) {
		this.test = test;
		this.paper = paper;
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
	@JoinColumn(name = "TEST")
	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PAPER")
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
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