package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeEnterprise entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeEnterprise extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeEnterprise peEnterprise;
	private String name;
	private String code;
	private String industry;
	private String address;
	private String zipcode;
	private String fax;
	private String fzrName;
	private String fzrXb;
	private String fzrPosition;
	private String fzrPhone;
	private String fzrMobile;
	private String fzrEmail;
	private String fzrAddress;
	private String lxrName;
	private String lxrXb;
	private String lxrPosition;
	private String lxrPhone;
	private String lxrMobile;
	private String lxrEmail;
	private String lxrAddress;
	private String status;
	private String bz;
	private String lxrDepart;
	private String fzrDepart;
	private String infoSheng;
	private String infoShi;
	private String infoJiedao;
	private Set prBzzPriManagerEnterprises = new HashSet(0);
	private Set peEnterprises = new HashSet(0);
	private Set peEnterpriseManagers = new HashSet(0);
	private Set peBzzStudents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeEnterprise() {
	}

	/** minimal constructor */
	public PeEnterprise(String name, String code) {
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PeEnterprise(PeEnterprise peEnterprise, String name, String code,
			String industry, String address, String zipcode, String fax,
			String fzrName, String fzrXb, String fzrPosition, String fzrPhone,
			String fzrMobile, String fzrEmail, String fzrAddress,
			String lxrName, String lxrXb, String lxrPosition, String lxrPhone,
			String lxrMobile, String lxrEmail, String lxrAddress,
			String status, String bz, String lxrDepart, String fzrDepart,
			String infoSheng, String infoShi, String infoJiedao,
			Set prBzzPriManagerEnterprises, Set peEnterprises,
			Set peEnterpriseManagers, Set peBzzStudents) {
		this.peEnterprise = peEnterprise;
		this.name = name;
		this.code = code;
		this.industry = industry;
		this.address = address;
		this.zipcode = zipcode;
		this.fax = fax;
		this.fzrName = fzrName;
		this.fzrXb = fzrXb;
		this.fzrPosition = fzrPosition;
		this.fzrPhone = fzrPhone;
		this.fzrMobile = fzrMobile;
		this.fzrEmail = fzrEmail;
		this.fzrAddress = fzrAddress;
		this.lxrName = lxrName;
		this.lxrXb = lxrXb;
		this.lxrPosition = lxrPosition;
		this.lxrPhone = lxrPhone;
		this.lxrMobile = lxrMobile;
		this.lxrEmail = lxrEmail;
		this.lxrAddress = lxrAddress;
		this.status = status;
		this.bz = bz;
		this.lxrDepart = lxrDepart;
		this.fzrDepart = fzrDepart;
		this.infoSheng = infoSheng;
		this.infoShi = infoShi;
		this.infoJiedao = infoJiedao;
		this.prBzzPriManagerEnterprises = prBzzPriManagerEnterprises;
		this.peEnterprises = peEnterprises;
		this.peEnterpriseManagers = peEnterpriseManagers;
		this.peBzzStudents = peBzzStudents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeEnterprise getPeEnterprise() {
		return this.peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFzrName() {
		return this.fzrName;
	}

	public void setFzrName(String fzrName) {
		this.fzrName = fzrName;
	}

	public String getFzrXb() {
		return this.fzrXb;
	}

	public void setFzrXb(String fzrXb) {
		this.fzrXb = fzrXb;
	}

	public String getFzrPosition() {
		return this.fzrPosition;
	}

	public void setFzrPosition(String fzrPosition) {
		this.fzrPosition = fzrPosition;
	}

	public String getFzrPhone() {
		return this.fzrPhone;
	}

	public void setFzrPhone(String fzrPhone) {
		this.fzrPhone = fzrPhone;
	}

	public String getFzrMobile() {
		return this.fzrMobile;
	}

	public void setFzrMobile(String fzrMobile) {
		this.fzrMobile = fzrMobile;
	}

	public String getFzrEmail() {
		return this.fzrEmail;
	}

	public void setFzrEmail(String fzrEmail) {
		this.fzrEmail = fzrEmail;
	}

	public String getFzrAddress() {
		return this.fzrAddress;
	}

	public void setFzrAddress(String fzrAddress) {
		this.fzrAddress = fzrAddress;
	}

	public String getLxrName() {
		return this.lxrName;
	}

	public void setLxrName(String lxrName) {
		this.lxrName = lxrName;
	}

	public String getLxrXb() {
		return this.lxrXb;
	}

	public void setLxrXb(String lxrXb) {
		this.lxrXb = lxrXb;
	}

	public String getLxrPosition() {
		return this.lxrPosition;
	}

	public void setLxrPosition(String lxrPosition) {
		this.lxrPosition = lxrPosition;
	}

	public String getLxrPhone() {
		return this.lxrPhone;
	}

	public void setLxrPhone(String lxrPhone) {
		this.lxrPhone = lxrPhone;
	}

	public String getLxrMobile() {
		return this.lxrMobile;
	}

	public void setLxrMobile(String lxrMobile) {
		this.lxrMobile = lxrMobile;
	}

	public String getLxrEmail() {
		return this.lxrEmail;
	}

	public void setLxrEmail(String lxrEmail) {
		this.lxrEmail = lxrEmail;
	}

	public String getLxrAddress() {
		return this.lxrAddress;
	}

	public void setLxrAddress(String lxrAddress) {
		this.lxrAddress = lxrAddress;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getLxrDepart() {
		return this.lxrDepart;
	}

	public void setLxrDepart(String lxrDepart) {
		this.lxrDepart = lxrDepart;
	}

	public String getFzrDepart() {
		return this.fzrDepart;
	}

	public void setFzrDepart(String fzrDepart) {
		this.fzrDepart = fzrDepart;
	}

	public String getInfoSheng() {
		return this.infoSheng;
	}

	public void setInfoSheng(String infoSheng) {
		this.infoSheng = infoSheng;
	}

	public String getInfoShi() {
		return this.infoShi;
	}

	public void setInfoShi(String infoShi) {
		this.infoShi = infoShi;
	}

	public String getInfoJiedao() {
		return this.infoJiedao;
	}

	public void setInfoJiedao(String infoJiedao) {
		this.infoJiedao = infoJiedao;
	}

	public Set getPrBzzPriManagerEnterprises() {
		return this.prBzzPriManagerEnterprises;
	}

	public void setPrBzzPriManagerEnterprises(Set prBzzPriManagerEnterprises) {
		this.prBzzPriManagerEnterprises = prBzzPriManagerEnterprises;
	}

	public Set getPeEnterprises() {
		return this.peEnterprises;
	}

	public void setPeEnterprises(Set peEnterprises) {
		this.peEnterprises = peEnterprises;
	}

	public Set getPeEnterpriseManagers() {
		return this.peEnterpriseManagers;
	}

	public void setPeEnterpriseManagers(Set peEnterpriseManagers) {
		this.peEnterpriseManagers = peEnterpriseManagers;
	}

	public Set getPeBzzStudents() {
		return this.peBzzStudents;
	}

	public void setPeBzzStudents(Set peBzzStudents) {
		this.peBzzStudents = peBzzStudents;
	}

}