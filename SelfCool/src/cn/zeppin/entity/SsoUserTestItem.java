package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SsoUserTestItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sso_user_test_item")
public class SsoUserTestItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private SsoUserTest ssoUserTest;
	private Item item;
	private Integer blankInx;
	private String reference;
	private String content;
	private Double score;
	private Short completeType;
	private Integer answertime;
	private Short isAnswered;

	// Constructors

	/** default constructor */
	public SsoUserTestItem() {
	}

	/** minimal constructor */
	public SsoUserTestItem(SsoUserTest ssoUserTest) {
		this.ssoUserTest = ssoUserTest;
	}

//	/** full constructor */
//	public SsoUserTestItem(SsoUserTest ssoUserTest,
//			SsoUserTestItem ssoUserTestItem, Item item,
//			String content, Double score, Short completeType, 
//			Integer inx) {
//		this.ssoUserTest = ssoUserTest;
//		this.ssoUserTestItem = ssoUserTestItem;
//		this.item = item;
//		this.content = content;
//		this.score = score;
//		this.completeType = completeType;
//	}

	// Property accessors
	@Id	
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_TEST", nullable = false)
	public SsoUserTest getSsoUserTest() {
		return this.ssoUserTest;
	}

	public void setSsoUserTest(SsoUserTest ssoUserTest) {
		this.ssoUserTest = ssoUserTest;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "BLANK_INX", nullable = false)
	public Integer getBlankInx() {
		return blankInx;
	}

	public void setBlankInx(Integer blankInx) {
		this.blankInx = blankInx;
	}

	@Column(name = "REFERENCE")
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "CONTENT", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "SCORE", precision = 8)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "COMPLETE_TYPE")
	public Short getCompleteType() {
		return this.completeType;
	}

	public void setCompleteType(Short completeType) {
		this.completeType = completeType;
	}

	@Column(name = "ANSWER_TIME")
	public Integer getAnswertime() {
		return answertime;
	}

	public void setAnswertime(Integer answertime) {
		this.answertime = answertime;
	}
	
	@Column(name = "ISANSWERED", nullable = false)
	public Short getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(Short isAnswered) {
		this.isAnswered = isAnswered;
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