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
 * TestPaperAnswer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_paper_answer", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = {
		"TEST_PAPER_ITEM", "ITEM_ANSWER" }))
public class TestPaperAnswer extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7689544897860259908L;
	private Integer id;
	private TestPaperItem testPaperItem;
	private Paper paper;
	private ItemAnswer itemAnswer;
	private Short score;

	// Constructors

	/** default constructor */
	public TestPaperAnswer() {
	}

	/** full constructor */
	public TestPaperAnswer(TestPaperItem testPaperItem, Paper paper,
			ItemAnswer itemAnswer, Short score) {
		this.testPaperItem = testPaperItem;
		this.paper = paper;
		this.itemAnswer = itemAnswer;
		this.score = score;
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
	@JoinColumn(name = "TEST_PAPER_ITEM", nullable = false)
	public TestPaperItem getTestPaperItem() {
		return this.testPaperItem;
	}

	public void setTestPaperItem(TestPaperItem testPaperItem) {
		this.testPaperItem = testPaperItem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEST_PAPER", nullable = false)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM_ANSWER", nullable = false)
	public ItemAnswer getItemAnswer() {
		return this.itemAnswer;
	}

	public void setItemAnswer(ItemAnswer itemAnswer) {
		this.itemAnswer = itemAnswer;
	}

	@Column(name = "SCORE", nullable = false)
	public Short getScore() {
		return this.score;
	}

	public void setScore(Short score) {
		this.score = score;
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