/*
 * 创建日期 2010-1-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 产品自定义要素VO
 * @author dingyj
 * @since 2010-1-8
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ProductAddVO {

	private Integer bookcode;
	private Integer input_man;
	private Integer serial_no;
	private String field_caption;
	private String field_value;
	private Integer product_id;
	private String product_info;
	private Integer tb_flag; //对应表：1 TPRODUCT表 2 TCUSTOMERINFO表
	private String tb_name; //tb_flag对应的表的名字
	private String summary; //字段说明
	private Integer is_chiose; //单选标志
	private Integer df_serial_no; //对应要素表ID
	/**
	 * @return
	 */
	public Integer getBookcode() {
		return bookcode;
	}

	/**
	 * @return
	 */
	public Integer getDf_serial_no() {
		return df_serial_no;
	}

	/**
	 * @return
	 */
	public String getField_caption() {
		return field_caption;
	}

	/**
	 * @return
	 */
	public String getField_value() {
		return field_value;
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
	public Integer getIs_chiose() {
		return is_chiose;
	}

	/**
	 * @return
	 */
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @return
	 */
	public String getProduct_info() {
		return product_info;
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
	public String getSummary() {
		return summary;
	}

	/**
	 * @return
	 */
	public Integer getTb_flag() {
		return tb_flag;
	}

	/**
	 * @return
	 */
	public String getTb_name() {
		return tb_name;
	}

	/**
	 * @param integer
	 */
	public void setBookcode(Integer integer) {
		bookcode = integer;
	}

	/**
	 * @param integer
	 */
	public void setDf_serial_no(Integer integer) {
		df_serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setField_caption(String string) {
		field_caption = string;
	}

	/**
	 * @param string
	 */
	public void setField_value(String string) {
		field_value = string;
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
	public void setIs_chiose(Integer integer) {
		is_chiose = integer;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setProduct_info(String string) {
		product_info = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @param integer
	 */
	public void setTb_flag(Integer integer) {
		tb_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setTb_name(String string) {
		tb_name = string;
	}

}
