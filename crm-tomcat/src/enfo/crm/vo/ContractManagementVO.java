/*
 * �������� 2011-7-8
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� cust_name��
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return ���� contract_bh��
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh Ҫ���õ� contract_bh��
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/**
	 * @return ���� contract_id��
	 */
	public Integer getContract_id() {
		return contract_id;
	}
	/**
	 * @param contract_id Ҫ���õ� contract_id��
	 */
	public void setContract_id(Integer contract_id) {
		this.contract_id = contract_id;
	}
	/**
	 * @return ���� cust_id��
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� end_date��
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date Ҫ���õ� end_date��
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return ���� payment_type��
	 */
	public String getPayment_type() {
		return payment_type;
	}
	/**
	 * @param payment_type Ҫ���õ� payment_type��
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	/**
	 * @return ���� payment_type_name��
	 */
	public String getPayment_type_name() {
		return payment_type_name;
	}
	/**
	 * @param payment_type_name Ҫ���õ� payment_type_name��
	 */
	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}
	/**
	 * @return ���� scribe_money��
	 */
	public BigDecimal getScribe_money() {
		return scribe_money;
	}
	/**
	 * @param scribe_money Ҫ���õ� scribe_money��
	 */
	public void setScribe_money(BigDecimal scribe_money) {
		this.scribe_money = scribe_money;
	}
	/**
	 * @return ���� sign_date��
	 */
	public Integer getSign_date() {
		return sign_date;
	}
	/**
	 * @param sign_date Ҫ���õ� sign_date��
	 */
	public void setSign_date(Integer sign_date) {
		this.sign_date = sign_date;
	}
}
