/*
 * �������� 2010-2-24
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author taochen
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class ActivityFeeVO {
	private Integer serial_no;
	private Integer active_serial_no;
	private String fee_items;
	private BigDecimal fee_amount;
	private String remark;
	private Integer input_man;
	private BigDecimal fee_amount_up;
	private BigDecimal fee_amount_down;	
	
	/**
	 * @return
	 */
	public Integer getActive_serial_no() {
		return active_serial_no;
	}

	/**
	 * @return
	 */
	public BigDecimal getFee_amount() {
		return fee_amount;
	}

	/**
	 * @return
	 */
	public BigDecimal getFee_amount_down() {
		return fee_amount_down;
	}

	/**
	 * @return
	 */
	public BigDecimal getFee_amount_up() {
		return fee_amount_up;
	}

	/**
	 * @return
	 */
	public String getFee_items() {
		return fee_items;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setActive_serial_no(Integer integer) {
		active_serial_no = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFee_amount(BigDecimal decimal) {
		fee_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setFee_amount_down(BigDecimal decimal) {
		fee_amount_down = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setFee_amount_up(BigDecimal decimal) {
		fee_amount_up = decimal;
	}

	/**
	 * @param string
	 */
	public void setFee_items(String string) {
		fee_items = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string) {
		remark = string;
	}

}
