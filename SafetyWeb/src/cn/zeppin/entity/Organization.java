package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

/**
 * Organization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "organization", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Organization extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7571963074968778131L;
	// Fields

	private Integer id;
	private String name;
	private Integer type;

	// Constructors

	/** default constructor */
	public Organization() {
	}

	/** minimal constructor */
	public Organization(String name, Integer type) {
		this.name = name;
		this.type = type;
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

	@Column(name = "NAME", unique = true, nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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