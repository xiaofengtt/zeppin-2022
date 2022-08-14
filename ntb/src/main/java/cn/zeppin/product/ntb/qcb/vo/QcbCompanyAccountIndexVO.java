package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountIndexVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740659141418123237L;
	
	private String name;
	private String accredit;
	
	private String status;
	private String statusCN;
	private Integer countEmployee;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal totalInvest;
	private String totalInvestCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	
	private String authStatus;
	private String authStatusCN;
	
	private Map<String, Object> mapPayInfo;//xInfo--横轴  yInfo--纵轴
	
	public QcbCompanyAccountIndexVO(){
		
	}
	
	public QcbCompanyAccountIndexVO(QcbCompanyAccount company){
		this.name = company.getName();
		this.accredit = company.getAccredit();
		this.status = company.getStatus();
		if(QcbCompanyAccountStatus.UNAUTH.equals(company.getStatus())){
			this.statusCN = "未认证";
		} else if (QcbCompanyAccountStatus.NORMAL.equals(company.getStatus())) {
			this.statusCN = "已认证";
		} else if (QcbCompanyAccountStatus.NOPASS.equals(company.getStatus())) {
			this.statusCN = "认证未通过";
		} else if (QcbCompanyAccountStatus.DELETED.equals(company.getStatus())) {
			this.statusCN = "已删除";
		} else if (QcbCompanyAccountStatus.UNCHECK.equals(company.getStatus())) {
			this.statusCN = "审核中";
		} else {
			this.statusCN = "--";
		}
		
		this.authStatus = company.getAuthStatus();
		if(QcbCompanyAccountAuthStatus.UNAUTH.equals(company.getAuthStatus())){
			this.authStatusCN = "未认证";
		} else if (QcbCompanyAccountAuthStatus.NORMAL.equals(company.getAuthStatus())) {
			this.authStatusCN = "已认证";
		} else if (QcbCompanyAccountAuthStatus.NOPASS.equals(company.getAuthStatus())) {
			this.authStatusCN = "认证未通过";
		} else if (QcbCompanyAccountAuthStatus.UNCHECK.equals(company.getAuthStatus())) {
			this.authStatusCN = "审核中";
		} else {
			this.authStatusCN = "--";
		}
		
		this.accountBalance = company.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(company.getAccountBalance());
		this.totalInvest = company.getTotalInvest();
		this.totalInvestCN = Utlity.numFormat4UnitDetail(company.getTotalInvest());
		this.totalReturn = company.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(company.getTotalReturn());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getAccredit() {
		return accredit;
	}

	public void setAccredit(String accredit) {
		this.accredit = accredit;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}
	
	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getTotalInvestCN() {
		return totalInvestCN;
	}
	
	public void setTotalInvestCN(String totalInvestCN) {
		this.totalInvestCN = totalInvestCN;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}
	
	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public Map<String, Object> getMapPayInfo() {
		return mapPayInfo;
	}

	public void setMapPayInfo(Map<String, Object> mapPayInfo) {
		this.mapPayInfo = mapPayInfo;
	}

	
	public String getStatus() {
		return status;
	}
	

	public void setStatus(String status) {
		this.status = status;
	}
	

	public String getStatusCN() {
		return statusCN;
	}
	

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	

	public Integer getCountEmployee() {
		return countEmployee;
	}
	

	public void setCountEmployee(Integer countEmployee) {
		this.countEmployee = countEmployee;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAuthStatusCN() {
		return authStatusCN;
	}

	public void setAuthStatusCN(String authStatusCN) {
		this.authStatusCN = authStatusCN;
	}
}
