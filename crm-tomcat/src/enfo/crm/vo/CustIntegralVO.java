package enfo.crm.vo;

import java.math.BigDecimal;

public class CustIntegralVO {
	private Integer 		cust_id;
	private String 			cust_name;
	private Integer 		startDate;
	private Integer 		endDate;
	private Integer			busi_type;
	private Integer			busi_id;
	private BigDecimal		amount;
	private Integer			ad_integral;
	private Integer			rule_id;
	private Integer			rdtl_id;
	private Integer			ramount_id;
	private Integer			product_id;
	private Integer 		input_man;
	private String 			remark;
	
	public Integer getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(Integer busi_type) {
		this.busi_type = busi_type;
	}
	public Integer getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(Integer busi_id) {
		this.busi_id = busi_id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getAd_integral() {
		return ad_integral;
	}
	public void setAd_integral(Integer ad_integral) {
		this.ad_integral = ad_integral;
	}
	public Integer getRule_id() {
		return rule_id;
	}
	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}
	public Integer getRdtl_id() {
		return rdtl_id;
	}
	public void setRdtl_id(Integer rdtl_id) {
		this.rdtl_id = rdtl_id;
	}
	public Integer getRamount_id() {
		return ramount_id;
	}
	public void setRamount_id(Integer ramount_id) {
		this.ramount_id = ramount_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
