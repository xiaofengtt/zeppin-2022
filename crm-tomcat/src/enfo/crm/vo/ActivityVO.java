/*
 * 创建日期 2009-11-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ActivityVO {
	private Integer serial_no;
	private String active_type;
	private String active_type_name;
	private String active_theme;
	private String active_start_date;
	private String active_end_date;
	private Integer manage_code;
	private String manage_man;
	private String customer_type;
	private String active_plan;
	private String active_trace;
	private String active_result;
	private Integer active_flag;
	private String completeTime;
	private BigDecimal  active_fee;
	private String creator_name;
	private Integer creator;
	private String active_code;	
	private Integer input_man;//操作员
	
	//查询条件
	private Integer StartDate;
	private Integer EndDate;
	private Integer CompleteTimeUp;
	private Integer CompleteTimeDown;
	private BigDecimal active_fee_up;
	private BigDecimal active_fee_down;
	
	private Integer cust_id;

	public Integer getCust_id() {
		return cust_id;
	}

	public void setCust_id(Integer custId) {
		cust_id = custId;
	}

	/**
	 * @return
	 */
	public String getActive_plan() {
		return active_plan;
	}

	/**
	 * @return
	 */
	public String getActive_result() {
		return active_result;
	}

	/**
	 * @return
	 */
	public String getActive_trace() {
		return active_trace;
	}

	/**
	 * @return
	 */
	public String getActive_type() {
		return active_type;
	}

	/**
	 * @return
	 */
	public String getActive_type_name() {
		return active_type_name;
	}

	/**
	 * @return
	 */
	public String getCustomer_type() {
		return customer_type;
	}

	/**
	 * @return
	 */
	public Integer getManage_code() {
		return manage_code;
	}

	/**
	 * @return
	 */
	public String getManage_man() {
		return manage_man;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param string
	 */
	public void setActive_plan(String string) {
		active_plan = string;
	}

	/**
	 * @param string
	 */
	public void setActive_result(String string) {
		active_result = string;
	}

	/**
	 * @param string
	 */
	public void setActive_trace(String string) {
		active_trace = string;
	}

	/**
	 * @param string
	 */
	public void setActive_type(String string) {
		active_type = string;
	}

	/**
	 * @param string
	 */
	public void setActive_type_name(String string) {
		active_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setCustomer_type(String string) {
		customer_type = string;
	}

	/**
	 * @param integer
	 */
	public void setManage_code(Integer integer) {
		manage_code = integer;
	}

	/**
	 * @param string
	 */
	public void setManage_man(String string) {
		manage_man = string;
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
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @return
	 */
	public String getActive_theme() {
		return active_theme;
	}

	/**
	 * @param string
	 */
	public void setActive_theme(String string) {
		active_theme = string;
	}

	/**
	 * @return
	 */
	public String getActive_end_date() {
		return active_end_date;
	}

	/**
	 * @return
	 */
	public String getActive_start_date() {
		return active_start_date;
	}

	/**
	 * @param string
	 */
	public void setActive_end_date(String string) {
		active_end_date = string;
	}

	/**
	 * @param string
	 */
	public void setActive_start_date(String string) {
		active_start_date = string;
	}

	/**
	 * @return
	 */
	public String getActive_code() {
		return active_code;
	}

	/**
	 * @return
	 */
	public BigDecimal getActive_fee() {
		return active_fee;
	}

	/**
	 * @return
	 */
	public Integer getActive_flag() {
		return active_flag;
	}

	/**
	 * @return
	 */
	public String getCompleteTime() {
		return completeTime;
	}

	/**
	 * @return
	 */
	public Integer getCreator() {
		return creator;
	}

	/**
	 * @return
	 */
	public String getCreator_name() {
		return creator_name;
	}

	/**
	 * @param string
	 */
	public void setActive_code(String string) {
		active_code = string;
	}

	/**
	 * @param decimal
	 */
	public void setActive_fee(BigDecimal decimal) {
		active_fee = decimal;
	}

	/**
	 * @param integer
	 */
	public void setActive_flag(Integer integer) {
		active_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setCompleteTime(String string) {
		completeTime = string;
	}

	/**
	 * @param integer
	 */
	public void setCreator(Integer integer) {
		creator = integer;
	}

	/**
	 * @param string
	 */
	public void setCreator_name(String string) {
		creator_name = string;
	}

	/**
	 * @return
	 */
	public BigDecimal getActive_fee_down() {
		return active_fee_down;
	}

	/**
	 * @return
	 */
	public BigDecimal getActive_fee_up() {
		return active_fee_up;
	}

	/**
	 * @return
	 */
	public Integer getCompleteTimeDown() {
		return CompleteTimeDown;
	}

	/**
	 * @return
	 */
	public Integer getCompleteTimeUp() {
		return CompleteTimeUp;
	}

	/**
	 * @return
	 */
	public Integer getEndDate() {
		return EndDate;
	}

	/**
	 * @return
	 */
	public Integer getStartDate() {
		return StartDate;
	}

	/**
	 * @param decimal
	 */
	public void setActive_fee_down(BigDecimal decimal) {
		active_fee_down = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setActive_fee_up(BigDecimal decimal) {
		active_fee_up = decimal;
	}

	/**
	 * @param integer
	 */
	public void setCompleteTimeDown(Integer integer) {
		CompleteTimeDown = integer;
	}

	/**
	 * @param integer
	 */
	public void setCompleteTimeUp(Integer integer) {
		CompleteTimeUp = integer;
	}

	/**
	 * @param integer
	 */
	public void setEndDate(Integer integer) {
		EndDate = integer;
	}

	/**
	 * @param integer
	 */
	public void setStartDate(Integer integer) {
		StartDate = integer;
	}

}
