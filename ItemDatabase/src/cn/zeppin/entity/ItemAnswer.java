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
 * ItemAnswer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_answer",uniqueConstraints = @UniqueConstraint(columnNames = {
		"ITEM", "INX" }))
public class ItemAnswer extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4289628447651154129L;
	private Integer id;
	private Item item;
	private Short inx;
	private Short type;
	private String reference;
	private String content;
	private String contentBackup;
	private Integer score;

	// Constructors

	/** default constructor */
	public ItemAnswer() {
	}

	/** minimal constructor */
	public ItemAnswer(Item item, Short inx, Short type, String content, String contentBackup) {
		this.item = item;
		this.inx = inx;
		this.type = type;
		this.content = content;
		this.contentBackup = contentBackup;
	}

	/** full constructor */
	public ItemAnswer(Item item, Short inx, Short type, String reference,
			String content, String contentBackup, Integer score) {
		this.item = item;
		this.inx = inx;
		this.type = type;
		this.reference = reference;
		this.content = content;
		this.score = score;
		this.contentBackup = contentBackup;
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

	@Column(name = "INX", nullable = false)
	public Short getInx() {
		return this.inx;
	}

	public void setInx(Short inx) {
		this.inx = inx;
	}

	@Column(name = "TYPE", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "REFERENCE", length = 20)
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "CONTENT", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CONTENT_BACKUP", length = 65535)
	public String getContentBackup() {
		return contentBackup;
	}

	public void setContentBackup(String contentBackup) {
		this.contentBackup = contentBackup;
	}

	@Column(name = "SCORE")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
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