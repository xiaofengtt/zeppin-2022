package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SsoUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SsoUser extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePriRole pePriRole;
	private EnumConst enumConstByFlagBak;
	private EnumConst enumConstByFlagIsvalid;
	private String loginId;
	private String password;
	private Long loginNum;
	private Date lastLoginDate;
	private String lastLoginIp;
	private String checkedInfo;
	private String checkedPw;
	private Set peTrainExperts = new HashSet(0);
	private Set peValuaExperts = new HashSet(0);
	private Set peManagers = new HashSet(0);
	private Set peTrainees = new HashSet(0);

	// Constructors

	/** default constructor */
	public SsoUser() {
	}

	/** minimal constructor */
	public SsoUser(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	/** full constructor */
	public SsoUser(PePriRole pePriRole, EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagIsvalid, String loginId, String password,
			Long loginNum, Date lastLoginDate, String lastLoginIp,
			String checkedInfo,String checkedPw, Set peTrainExperts, Set peValuaExperts,
			Set peManagers, Set peTrainees) {
		this.pePriRole = pePriRole;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.loginId = loginId;
		this.password = password;
		this.loginNum = loginNum;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginIp = lastLoginIp;
		this.checkedInfo = checkedInfo;
		this.checkedPw=checkedPw;
		this.peTrainExperts = peTrainExperts;
		this.peValuaExperts = peValuaExperts;
		this.peManagers = peManagers;
		this.peTrainees = peTrainees;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePriRole getPePriRole() {
		return this.pePriRole;
	}

	public void setPePriRole(PePriRole pePriRole) {
		this.pePriRole = pePriRole;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getLoginNum() {
		return this.loginNum;
	}

	public void setLoginNum(Long loginNum) {
		this.loginNum = loginNum;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getCheckedInfo() {
		return this.checkedInfo;
	}

	public void setCheckedInfo(String checkedInfo) {
		this.checkedInfo = checkedInfo;
	}

	public Set getPeTrainExperts() {
		return this.peTrainExperts;
	}

	public void setPeTrainExperts(Set peTrainExperts) {
		this.peTrainExperts = peTrainExperts;
	}

	public Set getPeValuaExperts() {
		return this.peValuaExperts;
	}

	public void setPeValuaExperts(Set peValuaExperts) {
		this.peValuaExperts = peValuaExperts;
	}

	public Set getPeManagers() {
		return this.peManagers;
	}

	public void setPeManagers(Set peManagers) {
		this.peManagers = peManagers;
	}

	public Set getPeTrainees() {
		return this.peTrainees;
	}

	public void setPeTrainees(Set peTrainees) {
		this.peTrainees = peTrainees;
	}

	public String getCheckedPw() {
		return checkedPw;
	}

	public void setCheckedPw(String checkedPw) {
		this.checkedPw = checkedPw;
	}

}