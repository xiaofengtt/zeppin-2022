/*
 * �������� 2011-8-11
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
public class TcoContractPayPlanVO {
	private Integer payPlan_id;
	private Integer cocontract_id;
	private Integer pay_num;
	private String  pay_num_name;
	private Integer exp_date;
	private BigDecimal pay_rate;
	private BigDecimal pay_money;
	private String  pay_summary;
	private String  bank_id;
	private String  bank_name;
	private String  bank_acct;
	private String  acct_name;
	private Integer invoice_time;
	private BigDecimal invoice_money;
	private Integer invoice_man;
	private Integer arriveMoney_flag;
	private BigDecimal arrive_money;
	private Integer problem_id;
	private Integer input_man;
	private Integer input_time;
	private String  coContract_sub_bh;
	private Integer exp_date_begin;
	private Integer exp_date_end;
	private Integer invoice_time_begin;
	private Integer invoice_time_end;
	private String cust_name;
	private String invoice_summary;
	
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
	 * @return ���� exp_date_begin��
	 */
	public Integer getExp_date_begin() {
		return exp_date_begin;
	}
	/**
	 * @param exp_date_begin Ҫ���õ� exp_date_begin��
	 */
	public void setExp_date_begin(Integer exp_date_begin) {
		this.exp_date_begin = exp_date_begin;
	}
	/**
	 * @return ���� exp_date_end��
	 */
	public Integer getExp_date_end() {
		return exp_date_end;
	}
	/**
	 * @param exp_date_end Ҫ���õ� exp_date_end��
	 */
	public void setExp_date_end(Integer exp_date_end) {
		this.exp_date_end = exp_date_end;
	}
	/**
	 * @return ���� invoice_time_begin��
	 */
	public Integer getInvoice_time_begin() {
		return invoice_time_begin;
	}
	/**
	 * @param invoice_time_begin Ҫ���õ� invoice_time_begin��
	 */
	public void setInvoice_time_begin(Integer invoice_time_begin) {
		this.invoice_time_begin = invoice_time_begin;
	}
	/**
	 * @return ���� invoice_time_end��
	 */
	public Integer getInvoice_time_end() {
		return invoice_time_end;
	}
	/**
	 * @param invoice_time_end Ҫ���õ� invoice_time_end��
	 */
	public void setInvoice_time_end(Integer invoice_time_end) {
		this.invoice_time_end = invoice_time_end;
	}
	/**
	 * @return ���� coContract_sub_bh��
	 */
	public String getCoContract_sub_bh() {
		return coContract_sub_bh;
	}
	/**
	 * @param coContract_sub_bh Ҫ���õ� coContract_sub_bh��
	 */
	public void setCoContract_sub_bh(String coContract_sub_bh) {
		this.coContract_sub_bh = coContract_sub_bh;
	}
	/**
	 * @return ���� arrive_money��
	 */
	public BigDecimal getArrive_money() {
		return arrive_money;
	}
	/**
	 * @param arrive_money Ҫ���õ� arrive_money��
	 */
	public void setArrive_money(BigDecimal arrive_money) {
		this.arrive_money = arrive_money;
	}
	/**
	 * @return ���� arriveMoney_flag��
	 */
	public Integer getArriveMoney_flag() {
		return arriveMoney_flag;
	}
	/**
	 * @param arriveMoney_flag Ҫ���õ� arriveMoney_flag��
	 */
	public void setArriveMoney_flag(Integer arriveMoney_flag) {
		this.arriveMoney_flag = arriveMoney_flag;
	}
	/**
	 * @return ���� invoice_man��
	 */
	public Integer getInvoice_man() {
		return invoice_man;
	}
	/**
	 * @param invoice_man Ҫ���õ� invoice_man��
	 */
	public void setInvoice_man(Integer invoice_man) {
		this.invoice_man = invoice_man;
	}
	/**
	 * @return ���� invoice_money��
	 */
	public BigDecimal getInvoice_money() {
		return invoice_money;
	}
	/**
	 * @param invoice_money Ҫ���õ� invoice_money��
	 */
	public void setInvoice_money(BigDecimal invoice_money) {
		this.invoice_money = invoice_money;
	}
	/**
	 * @return ���� pay_money��
	 */
	public BigDecimal getPay_money() {
		return pay_money;
	}
	/**
	 * @param pay_money Ҫ���õ� pay_money��
	 */
	public void setPay_money(BigDecimal pay_money) {
		this.pay_money = pay_money;
	}
	/**
	 * @return ���� pay_rate��
	 */
	public BigDecimal getPay_rate() {
		return pay_rate;
	}
	/**
	 * @param pay_rate Ҫ���õ� pay_rate��
	 */
	public void setPay_rate(BigDecimal pay_rate) {
		this.pay_rate = pay_rate;
	}
	/**
	 * @return ���� acct_name��
	 */
	public String getAcct_name() {
		return acct_name;
	}
	/**
	 * @param acct_name Ҫ���õ� acct_name��
	 */
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	/**
	 * @return ���� bank_acct��
	 */
	public String getBank_acct() {
		return bank_acct;
	}
	/**
	 * @param bank_acct Ҫ���õ� bank_acct��
	 */
	public void setBank_acct(String bank_acct) {
		this.bank_acct = bank_acct;
	}
	/**
	 * @return ���� bank_id��
	 */
	public String getBank_id() {
		return bank_id;
	}
	/**
	 * @param bank_id Ҫ���õ� bank_id��
	 */
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	/**
	 * @return ���� bank_name��
	 */
	public String getBank_name() {
		return bank_name;
	}
	/**
	 * @param bank_name Ҫ���õ� bank_name��
	 */
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	/**
	 * @return ���� cocontract_id��
	 */
	public Integer getCocontract_id() {
		return cocontract_id;
	}
	/**
	 * @param cocontract_id Ҫ���õ� cocontract_id��
	 */
	public void setCocontract_id(Integer cocontract_id) {
		this.cocontract_id = cocontract_id;
	}
	/**
	 * @return ���� exp_date��
	 */
	public Integer getExp_date() {
		return exp_date;
	}
	/**
	 * @param exp_date Ҫ���õ� exp_date��
	 */
	public void setExp_date(Integer exp_date) {
		this.exp_date = exp_date;
	}
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
	 * @return ���� input_time��
	 */
	public Integer getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time Ҫ���õ� input_time��
	 */
	public void setInput_time(Integer input_time) {
		this.input_time = input_time;
	}

	/**
	 * @return ���� invoice_time��
	 */
	public Integer getInvoice_time() {
		return invoice_time;
	}
	/**
	 * @param invoice_time Ҫ���õ� invoice_time��
	 */
	public void setInvoice_time(Integer invoice_time) {
		this.invoice_time = invoice_time;
	}
	
	/**
	 * @return ���� pay_num��
	 */
	public Integer getPay_num() {
		return pay_num;
	}
	/**
	 * @param pay_num Ҫ���õ� pay_num��
	 */
	public void setPay_num(Integer pay_num) {
		this.pay_num = pay_num;
	}
	/**
	 * @return ���� pay_num_name��
	 */
	public String getPay_num_name() {
		return pay_num_name;
	}
	/**
	 * @param pay_num_name Ҫ���õ� pay_num_name��
	 */
	public void setPay_num_name(String pay_num_name) {
		this.pay_num_name = pay_num_name;
	}
	
	/**
	 * @return ���� pay_summary��
	 */
	public String getPay_summary() {
		return pay_summary;
	}
	/**
	 * @param pay_summary Ҫ���õ� pay_summary��
	 */
	public void setPay_summary(String pay_summary) {
		this.pay_summary = pay_summary;
	}
	/**
	 * @return ���� payPlan_id��
	 */
	public Integer getPayPlan_id() {
		return payPlan_id;
	}
	/**
	 * @param payPlan_id Ҫ���õ� payPlan_id��
	 */
	public void setPayPlan_id(Integer payPlan_id) {
		this.payPlan_id = payPlan_id;
	}
	/**
	 * @return ���� problem_id��
	 */
	public Integer getProblem_id() {
		return problem_id;
	}
	/**
	 * @param problem_id Ҫ���õ� problem_id��
	 */
	public void setProblem_id(Integer problem_id) {
		this.problem_id = problem_id;
	}
	/**
	 * @return ���� invoice_summary��
	 */
	public String getInvoice_summary() {
		return invoice_summary;
	}
	/**
	 * @param invoice_summary Ҫ���õ� invoice_summary��
	 */
	public void setInvoice_summary(String invoice_summary) {
		this.invoice_summary = invoice_summary;
	}
}
