package cn.zeppin.entity ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RetrieveType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "retrieve_type")
public class RetrieveType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 766300951937983448L;
	// Fields

	private Integer id;
	private String name;
	private Short status;
	
	// Constructors

	/** default constructor */
	public RetrieveType() {
	}

	/** minimal constructor */
	public RetrieveType(String name, Short status) {
		this.name = name;
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

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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