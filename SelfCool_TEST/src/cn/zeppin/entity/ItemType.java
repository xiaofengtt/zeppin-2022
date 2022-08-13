package cn.zeppin.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * ItemType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_type")
public class ItemType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6381600280359999969L;

	private Integer id;
	private SysUser sysUser;
	private String name;
	private Boolean isStandard;
//	private Boolean isGroup;
	private Short modelType;
	private Timestamp createtime;
	private Short status;
	
	// Constructors

	/** default constructor */
	public ItemType() {
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
	@JoinColumn(name = "CREATER", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IS_STANDARD", nullable = false)
	public Boolean getIsStandard() {
		return this.isStandard;
	}

	public void setIsStandard(Boolean isStandard) {
		this.isStandard = isStandard;
	}

//	@Column(name = "IS_GROUP", nullable = false)
//	public Boolean getIsGroup() {
//		return this.isGroup;
//	}

//	public void setIsGroup(Boolean isGroup) {
//		this.isGroup = isGroup;
//	}
	
	@Column(name = "MODELTYPE", nullable = false)
	public Short getModelType() {
		return modelType;
	}

	public void setModelType(Short modelType) {
		this.modelType = modelType;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}