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
public class TcoProductVO {
	
	private Integer coProduct_id;
	private String coProduct_name;
	private Integer team_id;
	private Integer coProduct_manager;
	private Integer publish_date;
	private String  coProduct_type;
	private String coProduct_type_name;
	private String selfMade_type;
	private String selfMade_type_name;
	private String coProduct_summary;
	private BigDecimal coProduct_price;
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag;
	private Integer check_man;
	private Integer check_date;
	
	
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
	 * @return ���� coProduct_id��
	 */
	public Integer getCoProduct_id() {
		return coProduct_id;
	}
	/**
	 * @param coProduct_id Ҫ���õ� coProduct_id��
	 */
	public void setCoProduct_id(Integer coProduct_id) {
		this.coProduct_id = coProduct_id;
	}
	/**
	 * @return ���� coProduct_manager��
	 */
	public Integer getCoProduct_manager() {
		return coProduct_manager;
	}
	/**
	 * @param coProduct_manager Ҫ���õ� coProduct_manager��
	 */
	public void setCoProduct_manager(Integer coProduct_manager) {
		this.coProduct_manager = coProduct_manager;
	}
	/**
	 * @return ���� coProduct_name��
	 */
	public String getCoProduct_name() {
		return coProduct_name;
	}
	/**
	 * @param coProduct_name Ҫ���õ� coProduct_name��
	 */
	public void setCoProduct_name(String coProduct_name) {
		this.coProduct_name = coProduct_name;
	}
	/**
	 * @return ���� coProduct_price��
	 */
	public BigDecimal getCoProduct_price() {
		return coProduct_price;
	}
	/**
	 * @param coProduct_price Ҫ���õ� coProduct_price��
	 */
	public void setCoProduct_price(BigDecimal coProduct_price) {
		this.coProduct_price = coProduct_price;
	}
	/**
	 * @return ���� coProduct_summary��
	 */
	public String getCoProduct_summary() {
		return coProduct_summary;
	}
	/**
	 * @param coProduct_summary Ҫ���õ� coProduct_summary��
	 */
	public void setCoProduct_summary(String coProduct_summary) {
		this.coProduct_summary = coProduct_summary;
	}
	/**
	 * @return ���� coProduct_type��
	 */
	public String getCoProduct_type() {
		return coProduct_type;
	}
	/**
	 * @param coProduct_type Ҫ���õ� coProduct_type��
	 */
	public void setCoProduct_type(String coProduct_type) {
		this.coProduct_type = coProduct_type;
	}
	/**
	 * @return ���� coProduct_type_name��
	 */
	public String getCoProduct_type_name() {
		return coProduct_type_name;
	}
	/**
	 * @param coProduct_type_name Ҫ���õ� coProduct_type_name��
	 */
	public void setCoProduct_type_name(String coProduct_type_name) {
		this.coProduct_type_name = coProduct_type_name;
	}
	
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer teamId) {
		team_id = teamId;
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
	 * @return ���� publish_date��
	 */
	public Integer getPublish_date() {
		return publish_date;
	}
	/**
	 * @param publish_date Ҫ���õ� publish_date��
	 */
	public void setPublish_date(Integer publish_date) {
		this.publish_date = publish_date;
	}
	/**
	 * @return ���� selfMade_type��
	 */
	public String getSelfMade_type() {
		return selfMade_type;
	}
	/**
	 * @param selfMade_type Ҫ���õ� selfMade_type��
	 */
	public void setSelfMade_type(String selfMade_type) {
		this.selfMade_type = selfMade_type;
	}
	/**
	 * @return ���� selfMade_type_name��
	 */
	public String getSelfMade_type_name() {
		return selfMade_type_name;
	}
	/**
	 * @param selfMade_type_name Ҫ���õ� selfMade_type_name��
	 */
	public void setSelfMade_type_name(String selfMade_type_name) {
		this.selfMade_type_name = selfMade_type_name;
	}
}
