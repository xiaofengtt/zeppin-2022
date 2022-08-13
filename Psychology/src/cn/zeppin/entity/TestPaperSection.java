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

import cn.zeppin.entity.base.BaseEntity;

/**
 * TestPaperSection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "test_paper_section", catalog = "cetv")
public class TestPaperSection extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2222254052505979466L;
	private Integer id;
	private TestPaperSection testPaperSection;
	private ItemType itemType;
	private Paper paper;
	private Short level;
	private Short inx;
	private String name;
	private String scode;

	// Constructors

	/** default constructor */
	public TestPaperSection() {
	}

	/** minimal constructor */
	public TestPaperSection(Paper paper, Short level, Short inx, String name) {
		this.paper = paper;
		this.level = level;
		this.inx = inx;
		this.name = name;
	}

	/** full constructor */
	public TestPaperSection(TestPaperSection testPaperSection, ItemType itemType, Paper paper, Short level, Short inx, String name) {
		this.testPaperSection = testPaperSection;
		this.itemType = itemType;
		this.paper = paper;
		this.level = level;
		this.inx = inx;
		this.name = name;
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
	@JoinColumn(name = "PARENT")
	public TestPaperSection getTestPaperSection() {
		return this.testPaperSection;
	}

	public void setTestPaperSection(TestPaperSection testPaperSection) {
		this.testPaperSection = testPaperSection;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE")
	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEST_PAPER", nullable = false)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@Column(name = "LEVEL", nullable = false)
	public Short getLevel() {
		return this.level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Column(name = "INX", nullable = false)
	public Short getInx() {
		return this.inx;
	}

	public void setInx(Short inx) {
		this.inx = inx;
	}

	@Column(name = "NAME", nullable = false, length = 1000)
	public String getName() {
		return this.name;
	}
	
	@Column(name = "SCODE", length = 100)
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public void setName(String name) {
		this.name = name;
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