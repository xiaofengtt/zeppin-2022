package cn.zeppin.product.ntb.web.vo;

import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class InvestorAccountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String accountBalance;
	private String totalAmount;
	private String totalInvest;
	private String totalReturn;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String totalReturnBuyDay;
	private String totalReturnBuyMonth;
	private String totalReturnBuyYear;
	
 	public InvestorAccountVO(){
		
	}
	
	public InvestorAccountVO(Investor investor){
		this.uuid = investor.getUuid();
		this.flagCurrent = investor.getFlagCurrent();
		if(investor.getAccountBalance() != null){
			this.accountBalance = Utlity.numFormat4UnitDetail(investor.getAccountBalance());
		}else{
			this.accountBalance = "0.00";
		}
		if(investor.getTotalInvest() != null){
			this.totalInvest = Utlity.numFormat4UnitDetail(investor.getTotalInvest());
		}else{
			this.totalInvest = "0.00";
		}
		
		if(investor.getTotalInvest() != null && investor.getAccountBalance() != null){
			this.totalAmount = Utlity.numFormat4UnitDetail(investor.getTotalInvest().add(investor.getAccountBalance()));
		} else {
			this.totalAmount = "0.00";
		}
		
		if(investor.getTotalReturn() != null ){
			this.totalReturn = Utlity.numFormat4UnitDetail(investor.getTotalReturn());
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
}
