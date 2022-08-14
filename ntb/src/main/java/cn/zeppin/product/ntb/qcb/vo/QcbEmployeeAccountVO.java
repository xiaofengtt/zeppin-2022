package cn.zeppin.product.ntb.qcb.vo;

import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbEmployeeAccountVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -262543638820707990L;
	
	private String uuid;
	private String name;
	private String sex;
	private String accountBalance;
	private String totalAmount;
	private String totalInvest;
	private String totalReturn;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String totalReturnBuyDay;
	private String totalReturnBuyMonth;
	private String totalReturnBuyYear;
	
 	public QcbEmployeeAccountVO(){
		
	}
	
	public QcbEmployeeAccountVO(QcbEmployee qe){
		this.uuid = qe.getUuid();
		this.name = qe.getRealname();
		this.sex = qe.getSex();
		this.flagCurrent = qe.getFlagCurrent();
		if(qe.getAccountBalance() != null){
			this.accountBalance = Utlity.numFormat4UnitDetail(qe.getAccountBalance());
		}else{
			this.accountBalance = "0.00";
		}
		if(qe.getTotalInvest() != null){
			this.totalInvest = Utlity.numFormat4UnitDetail(qe.getTotalInvest());
		}else{
			this.totalInvest = "0.00";
		}
		
		if(qe.getTotalInvest() != null && qe.getAccountBalance() != null){
			this.totalAmount = Utlity.numFormat4UnitDetail(qe.getTotalInvest().add(qe.getAccountBalance()));
		} else {
			this.totalAmount = "0.00";
		}
		
		if(qe.getTotalReturn() != null ){
			this.totalReturn = Utlity.numFormat4UnitDetail(qe.getTotalReturn());
		} else {
			this.totalReturn = "0.00";
		}
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}

	public Boolean getFlagCurrent() {
		return flagCurrent;
	}

	public void setFlagCurrent(Boolean flagCurrent) {
		this.flagCurrent = flagCurrent;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalReturnBuyDay() {
		return totalReturnBuyDay;
	}

	public void setTotalReturnBuyDay(String totalReturnBuyDay) {
		this.totalReturnBuyDay = totalReturnBuyDay;
	}

	public String getTotalReturnBuyMonth() {
		return totalReturnBuyMonth;
	}

	public void setTotalReturnBuyMonth(String totalReturnBuyMonth) {
		this.totalReturnBuyMonth = totalReturnBuyMonth;
	}

	public String getTotalReturnBuyYear() {
		return totalReturnBuyYear;
	}

	public void setTotalReturnBuyYear(String totalReturnBuyYear) {
		this.totalReturnBuyYear = totalReturnBuyYear;
	}
	
	public String getTotalInvest() {
		return totalInvest;
	}
	
	public void setTotalInvest(String totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(String totalReturn) {
		this.totalReturn = totalReturn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
