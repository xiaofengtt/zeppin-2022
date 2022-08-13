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

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

	// Fields

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Category parent;
	private String name;
	private Integer level;
	private Integer status;
	private String scode;

	// Constructors

	/** default constructor */
	public Category() {
	}

	/** full constructor */
	public Category(Category parent, String name, Integer level, Integer status) {
		this.parent = parent;
		this.name = name;
		this.level = level;
		this.status = status;
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
	public Category getParent() {
		return this.parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "SCODE",  length = 100)
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	@Column(name = "LEVEL", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}