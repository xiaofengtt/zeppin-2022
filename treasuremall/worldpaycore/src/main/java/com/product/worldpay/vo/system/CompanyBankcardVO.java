package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.sql.Timestamp;

import com.product.worldpay.entity.CompanyBankcard;

public class CompanyBankcardVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -17379667322241282L;
	
	private String uuid;
	private String company;
	private String companyName;
	private String holder;
	private String bank;
	private String accountNum;
	private String area;
	private String branchBank;
	private String status;
	private Timestamp createtime;
	
	public CompanyBankcardVO() {
		super();
	}
	
	public CompanyBankcardVO (CompanyBankcard cb) {
		this.uuid = cb.getUuid();
		this.company = cb.getCompany();
		this.holder = cb.getHolder();
		this.bank = cb.getBank();
		this.accountNum = cb.getAccountNum();
		this.area = cb.getArea();
		this.branchBank = cb.getBranchBank();
		this.status = cb.getStatus();
		this.createtime = cb.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}