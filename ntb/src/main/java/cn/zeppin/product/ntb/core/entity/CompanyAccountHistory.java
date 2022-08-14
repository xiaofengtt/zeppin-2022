/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企业账户交易
 */

@Entity
@Table(name = "company_account_history")
public class CompanyAccountHistory extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 66418116224881904L;
	
	private String uuid;
	private String companyAccountOut;
	private String companyAccountIn;
	private String bankFinancialProduct;
	private String fund;
	private String qcbCompanyAccount;
	private String qcbEmployee;
	private String investor;
	private String related;
	private String type;
	private BigDecimal totalAmount;
	private BigDecimal poundage;
	private String purpose;
	private String remark;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	private String investorAccountHistory;
	private String qcbCompanyAccountHistory;
	private String qcbEmployeeHistory;
	
	private BigDecimal accountBalance;
	private BigDecimal accountBalanceIn;
	
	private String shbxUser;
	private String shbxUserHistory;
	
	public class CompanyAccountHistoryStatus{
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}
	
	public class CompanyAccountHistoryType{
		public final static String TRANSFER = "transfer";//转账
		public final static String RECHARGE = "recharge";//企业充值
		public final static String EXPEND = "expend";//费用支出
		public final static String INVEST = "invest";//投资
		public final static String REDEEM = "redeem";//赎回
		public final static String RETURN = "return";//收益
		public final static String CUR_INVEST = "cur_invest";//活期投资
		public final static String CUR_REDEEM = "cur_redeem";//活期赎回
		public final static String TAKEOUT = "takeout";//用户提现
		public final static String FILLIN = "fillin";//用户充值
		public final static String QCB_TAKEOUT = "qcb_takeout";//企财宝企业提现
		public final static String QCB_RECHARGE = "qcb_recharge";//企财宝企业提现
		public final static String QCB_PAYROLL = "qcb_payroll";//企财宝薪资发放
		public final static String EMP_TAKEOUT = "emp_takeout";//企财宝员工提现
		public final static String EMP_FILLIN = "emp_fillin";//企财宝用户充值
		public final static String SHBX_PAY_SHEBAO = "shbx_pay_shebao";//社保熊用户缴费
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "company_account_out", length = 36)
	public String getCompanyAccountOut() {
		return companyAccountOut;
	}
	
	public void setCompanyAccountOut(String companyAccountOut) {
		this.companyAccountOut = companyAccountOut;
	}
	
	@Column(name = "company_account_in", length = 36)
	public String getCompanyAccountIn() {
		return companyAccountIn;
	}
	
	public void setCompanyAccountIn(String companyAccountIn) {
		this.companyAccountIn = companyAccountIn;
	}
	
	@Column(name = "bank_financial_product", length = 36)
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}

	@Column(name = "fund", length = 36)
	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	@Column(name = "qcb_company_account", length = 36)
	public String getQcbCompanyAccount() {
		return qcbCompanyAccount;
	}

	public void setQcbCompanyAccount(String qcbCompanyAccount) {
		this.qcbCompanyAccount = qcbCompanyAccount;
	}

	@Column(name = "qcb_employee", length = 36)
	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	@Column(name = "investor", length = 36)
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}
	
	@Column(name = "related", length = 36)
	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "total_amount", nullable = false, length = 20)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "poundage", nullable = false, length = 20)
	public BigDecimal getPoundage() {
		return poundage;
	}
	
	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}
	
	@Column(name = "purpose", length = 200)
	public String getPurpose() {
		return purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "investor_account_history", length = 36)
	public String getInvestorAccountHistory() {
		return investorAccountHistory;
	}

	public void setInvestorAccountHistory(String investorAccountHistory) {
		this.investorAccountHistory = investorAccountHistory;
	}

	@Column(name = "qcb_company_account_history", length = 36)
	public String getQcbCompanyAccountHistory() {
		return qcbCompanyAccountHistory;
	}

	public void setQcbCompanyAccountHistory(String qcbCompanyAccountHistory) {
		this.qcbCompanyAccountHistory = qcbCompanyAccountHistory;
	}
	
	@Column(name = "qcb_employee_history", length = 36)
	public String getQcbEmployeeHistory() {
		return qcbEmployeeHistory;
	}

	public void setQcbEmployeeHistory(String qcbEmployeeHistory) {
		this.qcbEmployeeHistory = qcbEmployeeHistory;
	}

	@Column(name = "account_balance")
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "account_balance_in")
	public BigDecimal getAccountBalanceIn() {
		return accountBalanceIn;
	}

	public void setAccountBalanceIn(BigDecimal accountBalanceIn) {
		this.accountBalanceIn = accountBalanceIn;
	}

	@Column(name = "shbx_user", length = 36)
	public String getShbxUser() {
		return shbxUser;
	}

	public void setShbxUser(String shbxUser) {
		this.shbxUser = shbxUser;
	}

	@Column(name = "shbx_user_history", length = 36)
	public String getShbxUserHistory() {
		return shbxUserHistory;
	}

	public void setShbxUserHistory(String shbxUserHistory) {
		this.shbxUserHistory = shbxUserHistory;
	}
}
