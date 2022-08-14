/*
 * 创建日期 2011-7-8
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ContractManagementVO {

	private Integer contract_id;
	private Integer cust_id;
	private String cust_name;
	private String contract_bh;
	private Integer sign_date;
	private Integer end_date;
	private String payment_type;
	private String payment_type_name;
	private BigDecimal scribe_money;
	private Integer input_man;
	
	
	
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 cust_name。
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name 要设置的 cust_name。
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return 返回 contract_bh。
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh 要设置的 contract_bh。
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/**
	 * @return 返回 contract_id。
	 */
	public Integer getContract_id() {
		return contract_id;
	}
	/**
	 * @param contract_id 要设置的 contract_id。
	 */
	public void setContract_id(Integer contract_id) {
		this.contract_id = contract_id;
	}
	/**
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return 返回 end_date。
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date 要设置的 end_date。
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return 返回 payment_type。
	 */
	public String getPayment_type() {
		return payment_type;
	}
	/**
	 * @param payment_type 要设置的 payment_type。
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	/**
	 * @return 返回 payment_type_name。
	 */
	public String getPayment_type_name() {
		return payment_type_name;
	}
	/**
	 * @param payment_type_name 要设置的 payment_type_name。
	 */
	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}
	/**
	 * @return 返回 scribe_money。
	 */
	public BigDecimal getScribe_money() {
		return scribe_money;
	}
	/**
	 * @param scribe_money 要设置的 scribe_money。
	 */
	public void setScribe_money(BigDecimal scribe_money) {
		this.scribe_money = scribe_money;
	}
	/**
	 * @return 返回 sign_date。
	 */
	public Integer getSign_date() {
		return sign_date;
	}
	/**
	 * @param sign_date 要设置的 sign_date。
	 */
	public void setSign_date(Integer sign_date) {
		this.sign_date = sign_date;
	}
}
