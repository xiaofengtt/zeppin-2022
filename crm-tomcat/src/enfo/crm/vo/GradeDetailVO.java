/*
 * 创建日期 2009-11-25
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 评级得分明细对象对应GradeDetailVO对象
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GradeDetailVO {

	private Integer serial_id;
	private Integer index_id;
	private Integer grade_id;
	private Integer trade_date; //评分时间
	private Integer cust_id;
	private BigDecimal sj_value; //实际得分
	private BigDecimal df_value; //得分
	private Integer input_man;
	private Timestamp input_time;
	private Integer valid_flag;//值来源
	private Integer op_code;//操作员
	
	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public BigDecimal getDf_value() {
		return df_value;
	}

	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @return
	 */
	public Integer getIndex_id() {
		return index_id;
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
	public Timestamp getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getSerial_id() {
		return serial_id;
	}

	/**
	 * @return
	 */
	public BigDecimal getSj_value() {
		return sj_value;
	}

	/**
	 * @return
	 */
	public Integer getTrade_date() {
		return trade_date;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setDf_value(BigDecimal decimal) {
		df_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setIndex_id(Integer integer) {
		index_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInput_time(Timestamp timestamp) {
		input_time = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setSerial_id(Integer integer) {
		serial_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setSj_value(BigDecimal decimal) {
		sj_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setTrade_date(Integer integer) {
		trade_date = integer;
	}

	/**
	 * @return
	 */
	public Integer getOp_code() {
		return op_code;
	}

	/**
	 * @return
	 */
	public Integer getValid_flag() {
		return valid_flag;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer) {
		op_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setValid_flag(Integer integer) {
		valid_flag = integer;
	}

}
