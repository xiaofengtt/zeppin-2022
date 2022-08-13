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
 * Auth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth")
public class Auth implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2979496194287487831L;
	private Integer id;
	private SsoUser ssoUser;
	private String uid;
	private String nickname;
	private String imageUrl;
	private String accessToken;
	private Short gender;
	private Short isbound;
	private Short authType;
	private Short isdefault;
	private Short status;

	// Constructors

	/** default constructor */
	public Auth() {
	}

	/** minimal constructor */
	public Auth(Short status) {
		this.status = status;
	}

	/** full constructor */
	public Auth(SsoUser ssoUser, String uid, String nickname, String imageUrl, String accessToken, Short gender, Short isbound, Short authType, Short isdefault, Short status) {
		this.ssoUser = ssoUser;
		this.uid = uid;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.accessToken = accessToken;
		this.gender = gender;
		this.isbound = isbound;
		this.authType = authType;
		this.isdefault = isdefault;
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
	@JoinColumn(name = "SSO_USER")
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	@Column(name = "UID", length = 200)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "NICKNAME", length = 200)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "IMAGE_URL", length = 500)
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "ACCESS_TOKEN", length = 500)
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Column(name = "GENDER")
	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	@Column(name = "ISBOUND")
	public Short getIsbound() {
		return this.isbound;
	}

	public void setIsbound(Short isbound) {
		this.isbound = isbound;
	}

	@Column(name = "AUTH_TYPE")
	public Short getAuthType() {
		return this.authType;
	}

	public void setAuthType(Short authType) {
		this.authType = authType;
	}

	@Column(name = "ISDEFAULT")
	public Short getIsdefault() {
		return this.isdefault;
	}

	public void setIsdefault(Short isdefault) {
		this.isdefault = isdefault;
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