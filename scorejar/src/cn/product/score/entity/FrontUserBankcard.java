/**
 * 
 */
package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @description 【数据对象】用户银行卡
 */

public class FrontUserBankcard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3042211663271924244L;
	
	
	private String uuid;
	private String frontUser;
	private String bank;
	private String branchBank;
	private String type;
	
	private String accountNumber;
	private String accountHolder;
	private String phone;
	private Timestamp createtime;
	private String status;
	
	
	public class FrontUserBankcardType{
		public final static String DEBIT = "debit";
		public final static String CREDIT = "credit";
	}
	
	public class FrontUserBankcardStatus{
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

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
