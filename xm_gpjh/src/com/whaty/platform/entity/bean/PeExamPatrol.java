package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeExamPatrol entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamPatrol extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private String name;
	private String flagIsJiankao;
	private String flagIsXunkao;
	private String code;
	private String cardNum;
	private Date birthday;
	private String gender;
	private String hometown;
	private String workplace;
	private String occupation;
	private String photoLink;
	private String mobileGuangzhou;
	private String mobileAway;
	private String phoneOffice;
	private String phoneHome;
	private Set prRecPatrolSettings = new HashSet(0);
	private Set prExamPatrolSettings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeExamPatrol() {
	}

	/** minimal constructor */
	public PeExamPatrol(String name, String flagIsJiankao, String flagIsXunkao,
			String code, String cardNum) {
		this.name = name;
		this.flagIsJiankao = flagIsJiankao;
		this.flagIsXunkao = flagIsXunkao;
		this.code = code;
		this.cardNum = cardNum;
	}

	/** full constructor */
	public PeExamPatrol(PeSite peSite, String name, String flagIsJiankao,
			String flagIsXunkao, String code, String cardNum, Date birthday,
			String gender, String hometown, String workplace,
			String occupation, String photoLink, String mobileGuangzhou,
			String mobileAway, String phoneOffice, String phoneHome,
			Set prRecPatrolSettings, Set prExamPatrolSettings) {
		this.peSite = peSite;
		this.name = name;
		this.flagIsJiankao = flagIsJiankao;
		this.flagIsXunkao = flagIsXunkao;
		this.code = code;
		this.cardNum = cardNum;
		this.birthday = birthday;
		this.gender = gender;
		this.hometown = hometown;
		this.workplace = workplace;
		this.occupation = occupation;
		this.photoLink = photoLink;
		this.mobileGuangzhou = mobileGuangzhou;
		this.mobileAway = mobileAway;
		this.phoneOffice = phoneOffice;
		this.phoneHome = phoneHome;
		this.prRecPatrolSettings = prRecPatrolSettings;
		this.prExamPatrolSettings = prExamPatrolSettings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagIsJiankao() {
		return this.flagIsJiankao;
	}

	public void setFlagIsJiankao(String flagIsJiankao) {
		this.flagIsJiankao = flagIsJiankao;
	}

	public String getFlagIsXunkao() {
		return this.flagIsXunkao;
	}

	public void setFlagIsXunkao(String flagIsXunkao) {
		this.flagIsXunkao = flagIsXunkao;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhotoLink() {
		return this.photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public String getMobileGuangzhou() {
		return this.mobileGuangzhou;
	}

	public void setMobileGuangzhou(String mobileGuangzhou) {
		this.mobileGuangzhou = mobileGuangzhou;
	}

	public String getMobileAway() {
		return this.mobileAway;
	}

	public void setMobileAway(String mobileAway) {
		this.mobileAway = mobileAway;
	}

	public String getPhoneOffice() {
		return this.phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneHome() {
		return this.phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public Set getPrRecPatrolSettings() {
		return this.prRecPatrolSettings;
	}

	public void setPrRecPatrolSettings(Set prRecPatrolSettings) {
		this.prRecPatrolSettings = prRecPatrolSettings;
	}

	public Set getPrExamPatrolSettings() {
		return this.prExamPatrolSettings;
	}

	public void setPrExamPatrolSettings(Set prExamPatrolSettings) {
		this.prExamPatrolSettings = prExamPatrolSettings;
	}

}