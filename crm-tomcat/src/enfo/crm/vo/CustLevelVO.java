/*
 * 创建日期 2010-1-21
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author LZHD
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */


public class CustLevelVO {
	
	private Integer serial_no;
	private Integer product_id;
	private Integer level_id;
	private Integer level_value_id;
	private String level_value_name;
	private BigDecimal min_value;
	private BigDecimal max_value;
	private Integer input_man;

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getLevel_id() {
		return level_id;
	}

	/**
	 * @return
	 */
	public String getLevel_value_name() {
		return level_value_name;
	}

	/**
	 * @return
	 */
	public BigDecimal getMax_value() {
		return max_value;
	}

	/**
	 * @return
	 */
	public BigDecimal getMin_value() {
		return min_value;
	}

	/**
	 * @return
	 */
	public Integer getProduct_id() {
		return product_id;
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
	public void setLevel_id(Integer integer) {
		level_id = integer;
	}

	/**
	 * @param string
	 */
	public void setLevel_value_name(String string) {
		level_value_name = string;
	}

	/**
	 * @param decimal
	 */
	public void setMax_value(BigDecimal decimal) {
		max_value = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setMin_value(BigDecimal decimal) {
		min_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
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
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getLevel_value_id() {
		return level_value_id;
	}

	/**
	 * @param integer
	 */
	public void setLevel_value_id(Integer integer) {
		level_value_id = integer;
	}

}
