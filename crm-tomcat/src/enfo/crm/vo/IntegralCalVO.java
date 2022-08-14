package enfo.crm.vo;

import java.math.BigDecimal;

public class IntegralCalVO {
	private Integer ruleID;
	private Integer orderNo;
	private BigDecimal amount;
	private Integer cust_id;
	private Integer bookCode;
	private Integer qsDateUpper;
	private Integer qsDateLower;
	private String cust_name;
	private Integer startDate;
	private Integer endDate;
	private Integer log_type;
	
	public Integer getLog_type() {
		return log_type;
	}
	public void setLog_type(Integer log_type) {
		this.log_type = log_type;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getStartDate() {
		return startDate;
	}
	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}
	public Integer getEndDate() {
		return endDate;
	}
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	public Integer getBookCode() {
		return bookCode;
	}
	public void setBookCode(Integer bookCode) {
		this.bookCode = bookCode;
	}
	public Integer getQsDateUpper() {
		return qsDateUpper;
	}
	public void setQsDateUpper(Integer qsDateUpper) {
		this.qsDateUpper = qsDateUpper;
	}
	public Integer getQsDateLower() {
		return qsDateLower;
	}
	public void setQsDateLower(Integer qsDateLower) {
		this.qsDateLower = qsDateLower;
	}
	public Integer getRuleID() {
		return ruleID;
	}
	public void setRuleID(Integer ruleID) {
		this.ruleID = ruleID;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
