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
public class TcoContractPointsVO {
	private Integer subcoContract_id;
	private Integer coContract_id;
	private String  point_summary;
	private Integer coProduct_id;
	private String  coProduct_name;
	private BigDecimal sub_ht_money;
	private Integer input_man;
	private Integer input_time;
	
	
	/**
	 * @return ���� coContract_id��
	 */
	public Integer getCoContract_id() {
		return coContract_id;
	}
	/**
	 * @param coContract_id Ҫ���õ� coContract_id��
	 */
	public void setCoContract_id(Integer coContract_id) {
		this.coContract_id = coContract_id;
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
	 * @return ���� point_summary��
	 */
	public String getPoint_summary() {
		return point_summary;
	}
	/**
	 * @param point_summary Ҫ���õ� point_summary��
	 */
	public void setPoint_summary(String point_summary) {
		this.point_summary = point_summary;
	}
	/**
	 * @return ���� sub_ht_money��
	 */
	public BigDecimal getSub_ht_money() {
		return sub_ht_money;
	}
	/**
	 * @param sub_ht_money Ҫ���õ� sub_ht_money��
	 */
	public void setSub_ht_money(BigDecimal sub_ht_money) {
		this.sub_ht_money = sub_ht_money;
	}
	/**
	 * @return ���� subcoContract_id��
	 */
	public Integer getSubcoContract_id() {
		return subcoContract_id;
	}
	/**
	 * @param subcoContract_id Ҫ���õ� subcoContract_id��
	 */
	public void setSubcoContract_id(Integer subcoContract_id) {
		this.subcoContract_id = subcoContract_id;
	}
}
