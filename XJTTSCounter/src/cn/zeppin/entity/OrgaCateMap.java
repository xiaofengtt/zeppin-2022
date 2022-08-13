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
 * OrgaCateMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "orga_cate_map")
public class OrgaCateMap implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private OrganizationLevel organizationLevel;
	private FunCategory funCategory;
	private Short roleid;
	private String name;
	private Short status;

	// Constructors

	/** default constructor */
	public OrgaCateMap() {
	}

	/** full constructor */
	public OrgaCateMap(OrganizationLevel organizationLevel,
			FunCategory funCategory, Short roleid, String name, Short status) {
		this.organizationLevel = organizationLevel;
		this.funCategory = funCategory;
		this.roleid = roleid;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION")
	public OrganizationLevel getOrganizationLevel() {
		return this.organizationLevel;
	}

	public void setOrganizationLevel(OrganizationLevel organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY")
	public FunCategory getFunCategory() {
		return this.funCategory;
	}

	public void setFunCategory(FunCategory funCategory) {
		this.funCategory = funCategory;
	}

	@Column(name = "ROLEID")
	public Short getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Short roleid) {
		this.roleid = roleid;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	

}