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
 * SsoUserTest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sso_user_pay")
public class SsoUserPay implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8911065086396959173L;
	/**
	 * 
	 */
	private Integer id;
	private SsoUser ssoUser;
	private Paper paper;
	private Subject subject;
	private Integer price;
	private Timestamp createtime;
	private Short payType;
	private Short device;

	// Constructors

	/** default constructor */
	public SsoUserPay() {
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
	@JoinColumn(name = "SSOID", nullable = false)
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PAPER")
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@Column(name = "PRICE", nullable = false)
	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Column(name = "PAYTYPE", nullable = false)
	public Short getPayType() {
		return payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}
	
	@Column(name = "DEVICE", nullable = false)
	public Short getDevice() {
		return device;
	}

	public void setDevice(Short device) {
		this.device = device;
	}
	
	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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