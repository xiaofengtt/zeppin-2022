/*
 * 创建日期 2010-1-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.util.Date;


/**
 * 客户修改明细VO
 * @author dingyj
 * @since 2010-1-8
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustomerChangesVO {
	
	private Integer serial_no;
	
	private Integer cust_id;
	
	private String field_name;
	
	private String field_cn_name;
	
	private String old_field_info;
	
	private String new_field_info;
	
	private Integer input_man;
	
	private Date input_time;
	
	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public String getField_cn_name() {
		return field_cn_name;
	}

	/**
	 * @return
	 */
	public String getField_name() {
		return field_name;
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
	public Date getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public String getNew_field_info() {
		return new_field_info;
	}

	/**
	 * @return
	 */
	public String getOld_field_info() {
		return old_field_info;
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
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setField_cn_name(String string) {
		field_cn_name = string;
	}

	/**
	 * @param string
	 */
	public void setField_name(String string) {
		field_name = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param date
	 */
	public void setInput_time(Date date) {
		input_time = date;
	}

	/**
	 * @param string
	 */
	public void setNew_field_info(String string) {
		new_field_info = string;
	}

	/**
	 * @param string
	 */
	public void setOld_field_info(String string) {
		old_field_info = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}
}
