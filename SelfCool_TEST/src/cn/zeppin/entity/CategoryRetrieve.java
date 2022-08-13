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
 * CategoryRetrieve entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "category_retrieve")
public class CategoryRetrieve implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9158200187751939807L;
	private Integer id;
	private RetrieveType retrieveType;
	private Category category;

	// Constructors

	/** default constructor */
	public CategoryRetrieve() {
	}

	/** full constructor */
	public CategoryRetrieve(Integer id, RetrieveType retrieveType, Category category) {
		this.id = id;
		this.retrieveType = retrieveType;
		this.category = category;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORYID", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
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