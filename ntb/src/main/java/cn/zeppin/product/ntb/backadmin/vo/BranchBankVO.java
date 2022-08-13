package cn.zeppin.product.ntb.backadmin.vo;

import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class BranchBankVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String bank;
	private String bankName;
	private String name;
	private String address;
	private String status;
	
	public BranchBankVO(){
		
	}
	
	public BranchBankVO(BranchBank branchBank){
		this.uuid = branchBank.getUuid();
		this.bank = branchBank.getBank();
		this.name = branchBank.getName();
		this.address = branchBank.getAddress();
		this.status = branchBank.getStatus();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
