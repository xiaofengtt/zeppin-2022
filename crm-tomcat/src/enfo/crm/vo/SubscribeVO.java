/*
 * 创建日期 2011-05-17
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SubscribeVO {
	private Integer subscribe_id;
	private Integer cust_id;
	private Integer nonproduct_id;
	private String nonproduct_name;
	private String subscribe_bh;
	private Integer sign_date;
	private BigDecimal subscribe_money;
	private String bank_id;
	private String bank_name;
	private String bank_sub_name;
	private String bank_acct;
	private String acct_name;
	private Integer input_man;
	private java.sql.Timestamp input_time;
	private Integer check_flag;
	private Integer check_man; 
	private java.sql.Timestamp check_time;
	private String cust_name;
	private String status;
	private String status_name;
	private Integer pay_date;
	private String temp_status;
	
	public String getTemp_status() {
		return temp_status;
	}

	public void setTemp_status(String tempStatus) {
		temp_status = tempStatus;
	}

	public Integer getPay_date() {
		return pay_date;
	}

	public void setPay_date(Integer payDate) {
		pay_date = payDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String statusName) {
		status_name = statusName;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String custName) {
		cust_name = custName;
	}

	public Integer getSubscribe_id() {
		return subscribe_id;
	}
	
	public void setSubscribe_id(Integer subscribeId) {
		subscribe_id = subscribeId;
	}
	
	public Integer getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(Integer custId) {
		cust_id = custId;
	}
	
	public Integer getNonproduct_id() {
		return nonproduct_id;
	}
	
	public void setNonproduct_id(Integer nonproductId) {
		nonproduct_id = nonproductId;
	}
	
	public String getNonproduct_name() {
		return nonproduct_name;
	}
	
	public void setNonproduct_name(String nonproductName) {
		nonproduct_name = nonproductName;
	}
	
	public String getSubscribe_bh() {
		return subscribe_bh;
	}
	
	public void setSubscribe_bh(String subscribeBh) {
		subscribe_bh = subscribeBh;
	}
	
	public Integer getSign_date() {
		return sign_date;
	}
	
	public void setSign_date(Integer signDate) {
		sign_date = signDate;
	}
	
	public BigDecimal getSubscribe_money() {
		return subscribe_money;
	}
	
	public void setSubscribe_money(BigDecimal subscribeMoney) {
		subscribe_money = subscribeMoney;
	}
	
	public String getBank_id() {
		return bank_id;
	}
	
	public void setBank_id(String bankId) {
		bank_id = bankId;
	}
	
	public String getBank_name() {
		return bank_name;
	}
	
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	
	public String getBank_sub_name() {
		return bank_sub_name;
	}
	
	public void setBank_sub_name(String bankSubName) {
		bank_sub_name = bankSubName;
	}
	
	public String getBank_acct() {
		return bank_acct;
	}
	
	public void setBank_acct(String bankAcct) {
		bank_acct = bankAcct;
	}
	
	public String getAcct_name() {
		return acct_name;
	}
	
	public void setAcct_name(String acctName) {
		acct_name = acctName;
	}
	
	public Integer getInput_man() {
		return input_man;
	}
	
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	
	public void setInput_time(java.sql.Timestamp inputTime) {
		input_time = inputTime;
	}
	
	public Integer getCheck_flag() {
		return check_flag;
	}
	
	public void setCheck_flag(Integer checkFlag) {
		check_flag = checkFlag;
	}
	
	public Integer getCheck_man() {
		return check_man;
	}
	
	public void setCheck_man(Integer checkMan) {
		check_man = checkMan;
	}
	
	public java.sql.Timestamp getCheck_time() {
		return check_time;
	}
	
	public void setCheck_time(java.sql.Timestamp checkTime) {
		check_time = checkTime;
	}
}