package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TeacherTrainingRecords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mobile_code",uniqueConstraints = { @UniqueConstraint(columnNames = "UUID")})
public class MobileCode implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Integer id;
	private Integer ssoUser;
	private String mobile;
	private String code;
	private String uuid;
	private Timestamp createtime;
	private Short status;
	
	public MobileCode(Integer id, Integer ssoUser, String mobile, String code,
			String uuid, Timestamp createtime, Short status) {
		super();
		this.id = id;
		this.ssoUser = ssoUser;
		this.mobile = mobile;
		this.code = code;
		this.uuid = uuid;
		this.createtime = createtime;
		this.status = status;
	}
	
	public MobileCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="USER",nullable=true)
	public Integer getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(Integer ssoUser) {
		this.ssoUser = ssoUser;
	}

	@Column(name = "MOBILE",nullable = false)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CODE", nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "UUID", unique = true, nullable = false, length = 50)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp creattime) {
		this.createtime = creattime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	
		
	

}