package cn.product.payment.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class CompanyBankcard implements Serializable{
	
	/**
	 * 商户银行卡
	 */
	private static final long serialVersionUID = -7592842919137258674L;
	
	@Id
	private String uuid;
	private String company;
	private String holder;
	private String bank;
	private String accountNum;
	private String area;
	private String branchBank;
	private String status;
	private Timestamp createtime;
    
	public class CompanyBankcardStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
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