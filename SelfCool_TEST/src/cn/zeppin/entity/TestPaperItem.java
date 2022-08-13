package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TestPaperItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_paper_item", uniqueConstraints = { @UniqueConstraint(columnNames = { "TEST_PAPER", "ITEM" }), @UniqueConstraint(columnNames = { "TEST_PAPER_SECTION", "ITEM" }) })
public class TestPaperItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2558836119238911493L;
	private Integer id;
	private Item item;
	private TestPaperSection testPaperSection;
	private Paper paper;
	private Short inx;

	// Constructors

	/** default constructor */
	public TestPaperItem() {
	}

	/** minimal constructor */
	public TestPaperItem(Item item, TestPaperSection testPaperSection) {
		this.item = item;
		this.testPaperSection = testPaperSection;
	}

	/** full constructor */
	public TestPaperItem(Item item, TestPaperSection testPaperSection, Paper paper, Short inx) {
		this.item = item;
		this.testPaperSection = testPaperSection;
		this.paper = paper;
		this.inx = inx;
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
	@JoinColumn(name = "ITEM", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEST_PAPER_SECTION", nullable = false)
	public TestPaperSection getTestPaperSection() {
		return this.testPaperSection;
	}

	public void setTestPaperSection(TestPaperSection testPaperSection) {
		this.testPaperSection = testPaperSection;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEST_PAPER")
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@Column(name = "INX")
	public Short getInx() {
		return this.inx;
	}

	public void setInx(Short inx) {
		this.inx = inx;
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