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
public class TcoContractVO {
	private Integer cocontract_id;
	private Integer cust_id;
	private String cocontract_sub_bh;
	private Integer sign_date;
	private Integer start_date;
	private Integer end_date;
	private Integer main_end_date;
	private BigDecimal   ht_money;
	private String payment_type;
	private String payment_type_name;
	private String cocontract_type;
	private String cocontract_type_name;
	private String status;
	private String status_name;
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag;
	private Integer check_man;
	private Integer check_date;
	
	private String cust_name;
	private Integer start_date_begin;
    private Integer start_date_end;
    private Integer end_date_begin;
    private Integer end_date_end;
    private Integer main_end_date_begin;
    private Integer main_end_date_end;
    
    //modi 20111029
    private String comment;
    
	/**
	 * @return ���� end_date_begin��
	 */
	public Integer getEnd_date_begin() {
		return end_date_begin;
	}
	/**
	 * @param end_date_begin Ҫ���õ� end_date_begin��
	 */
	public void setEnd_date_begin(Integer end_date_begin) {
		this.end_date_begin = end_date_begin;
	}
	/**
	 * @return ���� end_date_end��
	 */
	public Integer getEnd_date_end() {
		return end_date_end;
	}
	/**
	 * @param end_date_end Ҫ���õ� end_date_end��
	 */
	public void setEnd_date_end(Integer end_date_end) {
		this.end_date_end = end_date_end;
	}
	/**
	 * @return ���� main_end_date_begin��
	 */
	public Integer getMain_end_date_begin() {
		return main_end_date_begin;
	}
	/**
	 * @param main_end_date_begin Ҫ���õ� main_end_date_begin��
	 */
	public void setMain_end_date_begin(Integer main_end_date_begin) {
		this.main_end_date_begin = main_end_date_begin;
	}
	/**
	 * @return ���� main_end_date_end��
	 */
	public Integer getMain_end_date_end() {
		return main_end_date_end;
	}
	/**
	 * @param main_end_date_end Ҫ���õ� main_end_date_end��
	 */
	public void setMain_end_date_end(Integer main_end_date_end) {
		this.main_end_date_end = main_end_date_end;
	}
	/**
	 * @return ���� start_date_begin��
	 */
	public Integer getStart_date_begin() {
		return start_date_begin;
	}
	/**
	 * @param start_date_begin Ҫ���õ� start_date_begin��
	 */
	public void setStart_date_begin(Integer start_date_begin) {
		this.start_date_begin = start_date_begin;
	}
	/**
	 * @return ���� start_date_end��
	 */
	public Integer getStart_date_end() {
		return start_date_end;
	}
	/**
	 * @param start_date_end Ҫ���õ� start_date_end��
	 */
	public void setStart_date_end(Integer start_date_end) {
		this.start_date_end = start_date_end;
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
	 * @return ���� check_date��
	 */
	public Integer getCheck_date() {
		return check_date;
	}
	/**
	 * @param check_date Ҫ���õ� check_date��
	 */
	public void setCheck_date(Integer check_date) {
		this.check_date = check_date;
	}
	
	/**
	 * @return ���� cocontract_sub_bh��
	 */
	public String getCocontract_sub_bh() {
		return cocontract_sub_bh;
	}
	/**
	 * @param cocontract_sub_bh Ҫ���õ� cocontract_sub_bh��
	 */
	public void setCocontract_sub_bh(String cocontract_sub_bh) {
		this.cocontract_sub_bh = cocontract_sub_bh;
	}
	/**
	 * @return ���� cocontract_type��
	 */
	public String getCocontract_type() {
		return cocontract_type;
	}
	/**
	 * @param cocontract_type Ҫ���õ� cocontract_type��
	 */
	public void setCocontract_type(String cocontract_type) {
		this.cocontract_type = cocontract_type;
	}
	/**
	 * @return ���� cocontract_type_name��
	 */
	public String getCocontract_type_name() {
		return cocontract_type_name;
	}
	/**
	 * @param cocontract_type_name Ҫ���õ� cocontract_type_name��
	 */
	public void setCocontract_type_name(String cocontract_type_name) {
		this.cocontract_type_name = cocontract_type_name;
	}
	/**
	 * @return ���� ht_money��
	 */
	public BigDecimal getHt_money() {
		return ht_money;
	}
	/**
	 * @param ht_money Ҫ���õ� ht_money��
	 */
	public void setHt_money(BigDecimal ht_money) {
		this.ht_money = ht_money;
	}
	/**
	 * @return ���� main_end_date��
	 */
	public Integer getMain_end_date() {
		return main_end_date;
	}
	/**
	 * @param main_end_date Ҫ���õ� main_end_date��
	 */
	public void setMain_end_date(Integer main_end_date) {
		this.main_end_date = main_end_date;
	}
	/**
	 * @return ���� start_date��
	 */
	public Integer getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date Ҫ���õ� start_date��
	 */
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	
	/**
	 * @return ���� check_flag��
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}
	/**
	 * @param check_flag Ҫ���õ� check_flag��
	 */
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/**
	 * @return ���� check_man��
	 */
	public Integer getCheck_man() {
		return check_man;
	}
	/**
	 * @param check_man Ҫ���õ� check_man��
	 */
	public void setCheck_man(Integer check_man) {
		this.check_man = check_man;
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
	/**
	 * @return ���� status��
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status Ҫ���õ� status��
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return ���� status_name��
	 */
	public String getStatus_name() {
		return status_name;
	}
	/**
	 * @param status_name Ҫ���õ� status_name��
	 */
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	/**
	 * @return ���� comment��
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment Ҫ���õ� comment��
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
