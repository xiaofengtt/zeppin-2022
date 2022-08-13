package cn.zeppin.entity ;

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
 * Retrieve entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "retrieve")
public class Retrieve implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826104800582002878L;
	private Integer id;
	private RetrieveType retrieveType;
	private String name;
	private Short status;
	
	// Constructors

	/** default constructor */
	public Retrieve() {
	}

	/** minimal constructor */
	public Retrieve(Integer id, RetrieveType retrieveType, String name, Short status) {
		this.id = id;
		this.retrieveType = retrieveType;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RETRIEVE_TYPEID", nullable = false)
	public RetrieveType getRetrieveType() {
		return this.retrieveType;
	}

	public void setRetrieveType(RetrieveType retrieveType) {
		this.retrieveType = retrieveType;
	}

	@Column(name = "NAME", nullable = false, length = 20)
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