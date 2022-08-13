package cn.zeppin.entity ;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Version entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "version", uniqueConstraints = @UniqueConstraint(columnNames = "version"))
public class Version implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6613911309681581335L;
	private Integer id;
	private String version;
	private Short status;

	// Constructors

	/** default constructor */
	public Version() {
	}

	/** minimal constructor */
	public Version(Integer id, String version , Short status) {
		this.id = id;
		this.version = version;
		this.status = status;
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "version", unique = true, nullable = false)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}