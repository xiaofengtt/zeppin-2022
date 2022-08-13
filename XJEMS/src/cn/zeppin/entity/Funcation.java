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

/**
 * Funcation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "funcation")
public class Funcation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -396850707766283829L;
	private Integer id;
	private Funcation funcation;
	private String name;
	private String path;
	private Short level;
	private Short status;
	private String scode;
	
	private String icon;

	// Constructors

	/** default constructor */
	public Funcation() {
	}

	/** minimal constructor */
	public Funcation(String name, Short level, Short status) {
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
	public Funcation getFuncation() {
		return this.funcation;
	}

	public void setFuncation(Funcation funcation) {
		this.funcation = funcation;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "LEVEL", nullable = false)
	public Short getLevel() {
		return this.level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "SCODE", length = 100)
	public String getScode() {
		return this.scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}
	
	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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