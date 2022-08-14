package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CompanyAccountHistoryVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916478952518224940L;
	
	private String uuid;
	private String companyAccountIn;
	private String companyAccountOut;
	private String bankFinancialProduct;
	private String fund;
	private String investor;
	private String qcbCompanyAccount;
	private String qcbEmployee;
	private String shbxUser;
	private String related;
	private String abountName;
	private BigDecimal totalAmount;
	private BigDecimal poundage;
	private String totalAmountCN;
	private String poundageCN;
	private String type;
	private String typeCN;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	
	private BigDecimal accountBalanceIn;
	private String accountBalanceInCN;
	
	public CompanyAccountHistoryVO(){
		
	}
	
	public CompanyAccountHistoryVO(CompanyAccountHistory cah){
		this.uuid = cah.getUuid();
		this.companyAccountIn = cah.getCompanyAccountIn();
		this.companyAccountOut = cah.getCompanyAccountOut();
		this.investor = cah.getInvestor();
		this.qcbCompanyAccount = cah.getQcbCompanyAccount();
		this.qcbEmployee = cah.getQcbEmployee();
		this.shbxUser = cah.getShbxUser();
		this.bankFinancialProduct = cah.getBankFinancialProduct();
		this.fund = cah.getFund();
		this.related = cah.getRelated();
		this.type = cah.getType();
		if(CompanyAccountHistoryType.EXPEND.equals(cah.getType())){
			this.typeCN = "手续费扣除";
		}else if(CompanyAccountHistoryType.FILLIN.equals(cah.getType())){
			this.typeCN = "用户充值";
		}else if(CompanyAccountHistoryType.INVEST.equals(cah.getType())){
			this.typeCN = "投资";
		}else if(CompanyAccountHistoryType.RECHARGE.equals(cah.getType())){
			this.typeCN = "充值";
		}else if(CompanyAccountHistoryType.REDEEM.equals(cah.getType())){
			this.typeCN = "赎回";
		}else if(CompanyAccountHistoryType.RETURN.equals(cah.getType())){
			this.typeCN = "投资收益";
		}else if(CompanyAccountHistoryType.TAKEOUT.equals(cah.getType())){
			this.typeCN = "用户提现";
		}else if(CompanyAccountHistoryType.TRANSFER.equals(cah.getType())){
			this.typeCN = "转账";
		}else if(CompanyAccountHistoryType.QCB_TAKEOUT.equals(cah.getType())){
			this.typeCN = "企财宝企业提现";
		}else if(CompanyAccountHistoryType.QCB_RECHARGE.equals(cah.getType())){
			this.typeCN = "企财宝企业充值";
		}else if(CompanyAccountHistoryType.QCB_PAYROLL.equals(cah.getType())){
			this.typeCN = "企财宝薪资发放";
		}else if(CompanyAccountHistoryType.EMP_FILLIN.equals(cah.getType())){
			this.typeCN = "企财宝员工充值";
		}else if(CompanyAccountHistoryType.EMP_TAKEOUT.equals(cah.getType())){
			this.typeCN = "企财宝员工提现";
		}else if(CompanyAccountHistoryType.CUR_INVEST.equals(cah.getType())){
			this.typeCN = "活期理财投资";
		}else if(CompanyAccountHistoryType.CUR_REDEEM.equals(cah.getType())){
			this.typeCN = "活期理财赎回";
		}else if(CompanyAccountHistoryType.SHBX_PAY_SHEBAO.equals(cah.getType())){
			this.typeCN = "社保熊用户缴费";
		}
		
		this.totalAmount = cah.getTotalAmount();
		this.totalAmountCN = Utlity.numFormat4UnitDetail(cah.getTotalAmount());
		this.poundage = cah.getPoundage();
		this.poundageCN = Utlity.numFormat4UnitDetail(cah.getPoundage());
		this.status = cah.getStatus();
		this.creator = cah.getCreator();
		this.createtime = cah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(cah.getCreatetime());
		
		this.accountBalance = cah.getAccountBalance() == null ? BigDecimal.ZERO : cah.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(accountBalance);
		
		this.accountBalanceIn = cah.getAccountBalanceIn() == null ? BigDecimal.ZERO : cah.getAccountBalanceIn();
		this.accountBalanceInCN = Utlity.numFormat4UnitDetail(accountBalanceIn);
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCompanyAccountIn() {
		return companyAccountIn;
	}

	public void setCompanyAccountIn(String companyAccountIn) {
		this.companyAccountIn = companyAccountIn;
	}

	public String getCompanyAccountOut() {
		return companyAccountOut;
	}

	public void setCompanyAccountOut(String companyAccountOut) {
		this.companyAccountOut = companyAccountOut;
	}
	
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	public String getQcbCompanyAccount() {
		return qcbCompanyAccount;
	}

	public void setQcbCompanyAccount(String qcbCompanyAccount) {
		this.qcbCompanyAccount = qcbCompanyAccount;
	}

	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public String getAbountName() {
		return abountName;
	}

	public void setAbountName(String abountName) {
		this.abountName = abountName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getPoundageCN() {
		return poundageCN;
	}

	public void setPoundageCN(String poundageCN) {
		this.poundageCN = poundageCN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToDateString(createtime);
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
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


	public BigDecimal getAccountBalanceIn() {
		return accountBalanceIn;
	}
	

	public void setAccountBalanceIn(BigDecimal accountBalanceIn) {
		this.accountBalanceIn = accountBalanceIn;
	}
	

	public String getAccountBalanceInCN() {
		return accountBalanceInCN;
	}
	

	public void setAccountBalanceInCN(String accountBalanceInCN) {
		this.accountBalanceInCN = accountBalanceInCN;
	}

	public String getShbxUser() {
		return shbxUser;
	}

	public void setShbxUser(String shbxUser) {
		this.shbxUser = shbxUser;
	}
}
