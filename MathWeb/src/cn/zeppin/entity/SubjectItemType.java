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
 * SubjectItemType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject_item_type", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = {
		"SUBJECT", "ITEM_TYPE" }))
public class SubjectItemType extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3629411197015138823L;
	private Integer id;
	private ItemType itemType;
	private Subject subject;
	private Integer inx;

	// Constructors

	/** default constructor */
	public SubjectItemType() {
	}

	/** full constructor */
	public SubjectItemType(ItemType itemType, Subject subject, Integer inx) {
		this.itemType = itemType;
		this.subject = subject;
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
	@JoinColumn(name = "ITEM_TYPE", nullable = false)
	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "INX", nullable = false)
	public Integer getInx() {
		return inx;
	}

	public void setInx(Integer inx) {
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