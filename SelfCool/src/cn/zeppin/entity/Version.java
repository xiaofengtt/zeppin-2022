package cn.zeppin.entity ;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Short forcedUpdate;
	private Short device;
	private Resource resource;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Version() {
	}

	/** minimal constructor */
	public Version(Integer id, String version , Short status , Short forcedUpdate , Short device) {
		this.id = id;
		this.version = version;
		this.status = status;
		this.forcedUpdate = forcedUpdate;
		this.device = device;
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

	@Column(name = "forced_update", nullable = false)
	public Short getForcedUpdate() {
		return this.forcedUpdate;
	}

	public void setForcedUpdate(Short forcedUpdate) {
		this.forcedUpdate = forcedUpdate;
	}
	
	@Column(name = "device", nullable = false)
	public Short getDevice() {
		return this.device;
	}

	public void setDevice(Short device) {
		this.device = device;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "file")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}