/*
 * 创建日期 2008-6-26
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Lzhd
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CellVO {
	
	private java.lang.Integer serial_no;
	private java.lang.Integer rpt_id;
	private java.lang.Integer item_id;
	private java.lang.Integer item_flag;
	private java.lang.String item_name;
	private java.lang.String line_no;
	private java.lang.Integer flag; //标志: 1 固定值 2 空值  3 取财务值 4 合计项
	private java.lang.Integer sum_no;//合计填充行0 无关 > 0 加到该行,  < 0 减到该行 
	private java.lang.String sub_code1;
	private java.lang.String sub_code3;
	private java.lang.Integer direction;
	private BigDecimal balance1;
	private BigDecimal balance2;
	private BigDecimal balance3;
	private BigDecimal balance4;
	private Integer insert_flag;
	private java.lang.Integer input_man;
	private Integer is_common;
	private Integer is_positive;
	
	
	
	/**
	 * @return
	 */
	public BigDecimal getBalance1() {
		return balance1;
	}

	/**
	 * @return
	 */
	public BigDecimal getBalance2() {
		return balance2;
	}

	/**
	 * @return
	 */
	public BigDecimal getBalance3() {
		return balance3;
	}

	/**
	 * @return
	 */
	public BigDecimal getBalance4() {
		return balance4;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDirection() {
		return direction;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getFlag() {
		return flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getItem_flag() {
		return item_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getItem_id() {
		return item_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getItem_name() {
		return item_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getLine_no() {
		return line_no;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRpt_id() {
		return rpt_id;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public java.lang.String getSub_code1() {
		return sub_code1;
	}

	/**
	 * @return
	 */
	public java.lang.String getSub_code3() {
		return sub_code3;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getSum_no() {
		return sum_no;
	}

	/**
	 * @param decimal
	 */
	public void setBalance1(BigDecimal decimal) {
		balance1 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBalance2(BigDecimal decimal) {
		balance2 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBalance3(BigDecimal decimal) {
		balance3 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBalance4(BigDecimal decimal) {
		balance4 = decimal;
	}

	/**
	 * @param integer
	 */
	public void setDirection(java.lang.Integer integer) {
		direction = integer;
	}

	/**
	 * @param integer
	 */
	public void setFlag(java.lang.Integer integer) {
		flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(java.lang.Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setItem_flag(java.lang.Integer integer) {
		item_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setItem_id(java.lang.Integer integer) {
		item_id = integer;
	}

	/**
	 * @param string
	 */
	public void setItem_name(java.lang.String string) {
		item_name = string;
	}

	/**
	 * @param string
	 */
	public void setLine_no(java.lang.String string) {
		line_no = string;
	}

	/**
	 * @param integer
	 */
	public void setRpt_id(java.lang.Integer integer) {
		rpt_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(java.lang.Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setSub_code1(java.lang.String string) {
		sub_code1 = string;
	}

	/**
	 * @param string
	 */
	public void setSub_code3(java.lang.String string) {
		sub_code3 = string;
	}

	/**
	 * @param integer
	 */
	public void setSum_no(java.lang.Integer integer) {
		sum_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getInsert_flag() {
		return insert_flag;
	}

	/**
	 * @param integer
	 */
	public void setInsert_flag(Integer integer) {
		insert_flag = integer;
	}

	/**
	 * @return
	 */
	public Integer getIs_common() {
		return is_common;
	}

	/**
	 * @return
	 */
	public Integer getIs_positive() {
		return is_positive;
	}

	/**
	 * @param integer
	 */
	public void setIs_common(Integer integer) {
		is_common = integer;
	}

	/**
	 * @param integer
	 */
	public void setIs_positive(Integer integer) {
		is_positive = integer;
	}

}
