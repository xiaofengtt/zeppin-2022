package enfo.crm.vo;

/**
 * Dictparam对应的VO对象
 * @author Felix
 * @since 2008-5-26
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class DictparamVO {

	private Integer serial_no = new Integer(0);
	private Integer type_id = new Integer(0); //参数类型
	private String type_name = ""; //类型名称
	private String type_value = ""; //参数值
	private String type_content = ""; //参数含义
	private String additive_value = "";

	private String flag_type = "";
	private String summary = "";
	private Integer value = null;
	private Integer is_modi = new Integer(1);

	private String sum_code = ""; //IN_SUM_CODE	VARCHAR(6)	摘要代码
	private String sum_name = ""; //IN_SUM_NAME	VARCHAR(30)	摘要内容
	private Integer input_man = new Integer(0);

	private String busi_id;
	private String sub_code_1_1;
	private String sub_code_1_2;
	private String sub_code_1_4;
	private String sub_code_2_1;
	private String sub_code_2_2;
	private String sub_code_2_4;
	private Integer book_code;
	private String aml_value;
	//打印模板设置
	private Integer catalog_id;
	private String catalog_code;
	private String catalog_name;
	private Integer template_id;
	private String template_code; 
	private String template_name;
	private Integer flag;
	private Integer element_id;
	private String element_code;
	private String element_name;
	private String template_content;
	  
	/**
	 * @return 返回 template_content。
	 */
	public String getTemplate_content() {
		return template_content;
	}
	/**
	 * @param template_content 要设置的 template_content。
	 */
	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}
	/**
	 * @return 返回 element_code。
	 */
	public String getElement_code() {
		return element_code;
	}
	/**
	 * @param element_code 要设置的 element_code。
	 */
	public void setElement_code(String element_code) {
		this.element_code = element_code;
	}
	/**
	 * @return 返回 element_id。
	 */
	public Integer getElement_id() {
		return element_id;
	}
	/**
	 * @param element_id 要设置的 element_id。
	 */
	public void setElement_id(Integer element_id) {
		this.element_id = element_id;
	}
	/**
	 * @return 返回 element_name。
	 */
	public String getElement_name() {
		return element_name;
	}
	/**
	 * @param element_name 要设置的 element_name。
	 */
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	/**
	 * @return 返回 flag。
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * @param flag 要设置的 flag。
	 */
	public void setFlag(Integer flag) {
		this.flag = flag; 
	}
	/**
	 * @return
	 */
	public String getAdditive_value() {
		return additive_value;
	}

	/**
	 * @return
	 */
	public Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public String getBusi_id() {
		return busi_id;
	}

	/**
	 * @return
	 */
	public String getFlag_type() {
		return flag_type;
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
	public Integer getIs_modi() {
		return is_modi;
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
	public String getSub_code_1_1() {
		return sub_code_1_1;
	}

	/**
	 * @return
	 */
	public String getSub_code_1_4() {
		return sub_code_1_4;
	}

	/**
	 * @return
	 */
	public String getSub_code_2_1() {
		return sub_code_2_1;
	}

	/**
	 * @return
	 */
	public String getSub_code_2_4() {
		return sub_code_2_4;
	}

	/**
	 * @return
	 */
	public String getSum_code() {
		return sum_code;
	}

	/**
	 * @return
	 */
	public String getSum_name() {
		return sum_name;
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
	public String getType_content() {
		return type_content;
	}

	/**
	 * @return
	 */
	public Integer getType_id() {
		return type_id;
	}

	/**
	 * @return
	 */
	public String getType_name() {
		return type_name;
	}

	/**
	 * @return
	 */
	public String getType_value() {
		return type_value;
	}

	/**
	 * @return
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setAdditive_value(String string) {
		additive_value = string;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer) {
		book_code = integer;
	}

	/**
	 * @param string
	 */
	public void setBusi_id(String string) {
		busi_id = string;
	}

	/**
	 * @param string
	 */
	public void setFlag_type(String string) {
		flag_type = string;
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
	public void setIs_modi(Integer integer) {
		is_modi = integer;
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
	public void setSub_code_1_1(String string) {
		sub_code_1_1 = string;
	}

	/**
	 * @param string
	 */
	public void setSub_code_1_4(String string) {
		sub_code_1_4 = string;
	}

	/**
	 * @param string
	 */
	public void setSub_code_2_1(String string) {
		sub_code_2_1 = string;
	}

	/**
	 * @param string
	 */
	public void setSub_code_2_4(String string) {
		sub_code_2_4 = string;
	}

	/**
	 * @param string
	 */
	public void setSum_code(String string) {
		sum_code = string;
	}

	/**
	 * @param string
	 */
	public void setSum_name(String string) {
		sum_name = string;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @param string
	 */
	public void setType_content(String string) {
		type_content = string;
	}

	/**
	 * @param integer
	 */
	public void setType_id(Integer integer) {
		type_id = integer;
	}

	/**
	 * @param string
	 */
	public void setType_name(String string) {
		type_name = string;
	}

	/**
	 * @param string
	 */
	public void setType_value(String string) {
		type_value = string;
	}

	/**
	 * @param integer
	 */
	public void setValue(Integer integer) {
		value = integer;
	}

	/**
	 * @return
	 */
	public String getSub_code_1_2() {
		return sub_code_1_2;
	}

	/**
	 * @return
	 */
	public String getSub_code_2_2() {
		return sub_code_2_2;
	}

	/**
	 * @param string
	 */
	public void setSub_code_1_2(String string) {
		sub_code_1_2 = string;
	}

	/**
	 * @param string
	 */
	public void setSub_code_2_2(String string) {
		sub_code_2_2 = string;
	}

	/**
	 * @return
	 */
	public String getAml_value() {
		return aml_value;
	}

	/**
	 * @param string
	 */
	public void setAml_value(String string) {
		aml_value = string;
	}

	/**
	 * @return 返回 catalog_code。
	 */
	public String getCatalog_code() {
		return catalog_code;
	}
	/**
	 * @param catalog_code 要设置的 catalog_code。
	 */
	public void setCatalog_code(String catalog_code) {
		this.catalog_code = catalog_code;
	}
	/**
	 * @return 返回 catalog_id。
	 */
	public Integer getCatalog_id() {
		return catalog_id;
	}
	/**
	 * @param catalog_id 要设置的 catalog_id。
	 */
	public void setCatalog_id(Integer catalog_id) {
		this.catalog_id = catalog_id;
	}
	/**
	 * @return 返回 catalog_name。
	 */
	public String getCatalog_name() {
		return catalog_name;
	}
	/**
	 * @param catalog_name 要设置的 catalog_name。
	 */
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	/**
	 * @return 返回 template_code。 
	 */
	public String getTemplate_code() {
		return template_code; 
	}
	/**
	 * @param template_code 要设置的 template_code。 
	 */
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}
	/**
	 * @return 返回 template_id。 
	 */
	public Integer getTemplate_id() {
		return template_id;
	}
	/**
	 * @param template_id 要设置的 template_id。
	 */
	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}
	/**
	 * @return 返回 template_name。
	 */
	public String getTemplate_name() {
		return template_name;
	}
	/**
	 * @param template_name 要设置的 template_name。
	 */
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
}
