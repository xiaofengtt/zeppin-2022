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
 * 评级得分结果对象对应GradeTotalVO对象
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GradeTotalVO {

	private Integer serial_no;
	private Integer grade_id;
	private Integer trade_date;
	private Integer cust_id;
	private BigDecimal total_value; //总得分
	private String grade_level;	
	private String old_grade_level; //原级别
	private Integer check_flag; //审批标志
	private Integer problem_id; //事物ID
	private Integer input_man;
	private Timestamp input_time;
	
	private Integer pre_trade_date;//评分日期
	private Integer end_trade_date;	
	private String cust_name;//客户名称
	private Integer product_id;//产品ID
	/**
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
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
	public String getGrade_level() {
		return grade_level;
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
	public String getOld_grade_level() {
		return old_grade_level;
	}

	/**
	 * @return
	 */
	public Integer getProblem_id() {
		return problem_id;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotal_value() {
		return total_value;
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
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @param string
	 */
	public void setGrade_level(String string) {
		grade_level = string;
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
	 * @param string
	 */
	public void setOld_grade_level(String string) {
		old_grade_level = string;
	}

	/**
	 * @param integer
	 */
	public void setProblem_id(Integer integer) {
		problem_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTotal_value(BigDecimal decimal) {
		total_value = decimal;
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
	public String getCust_name() {
		return cust_name;
	}

	/**
	 * @return
	 */
	public Integer getEnd_trade_date() {
		return end_trade_date;
	}

	/**
	 * @return
	 */
	public Integer getPre_trade_date() {
		return pre_trade_date;
	}

	/**
	 * @param string
	 */
	public void setCust_name(String string) {
		cust_name = string;
	}

	/**
	 * @param integer
	 */
	public void setEnd_trade_date(Integer integer) {
		end_trade_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setPre_trade_date(Integer integer) {
		pre_trade_date = integer;
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
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

}
