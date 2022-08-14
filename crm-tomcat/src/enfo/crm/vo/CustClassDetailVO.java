/*
 * 创建日期 2009-12-21
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 客户分级定义之明细VO
 * @author dingyj
 * @since 2009-12-21
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustClassDetailVO {

	private Integer class_define_id; //分级ID
	
	private String class_define_name; //分级名称
	
	private Integer class_detail_id; //分级明细ID
	
	private String class_detail_name; //分级明细名称
	
	private Integer class_minvalue; //级别定义最小值
	
	private Integer class_maxvalue; //级别定义最大值
	
	private Integer canmodi; //是否可修改 1是 2否
	
	private Integer input_man; //操作员
	
	/**
	 * @return
	 */
	public Integer getCanmodi() {
		return canmodi;
	}

	/**
	 * @return
	 */
	public Integer getClass_define_id() {
		return class_define_id;
	}

	/**
	 * @return
	 */
	public String getClass_define_name() {
		return class_define_name;
	}

	/**
	 * @return
	 */
	public Integer getClass_detail_id() {
		return class_detail_id;
	}

	/**
	 * @return
	 */
	public String getClass_detail_name() {
		return class_detail_name;
	}

	/**
	 * @param integer
	 */
	public void setCanmodi(Integer integer) {
		canmodi = integer;
	}

	/**
	 * @param integer
	 */
	public void setClass_define_id(Integer integer) {
		class_define_id = integer;
	}

	/**
	 * @param string
	 */
	public void setClass_define_name(String string) {
		class_define_name = string;
	}

	/**
	 * @param integer
	 */
	public void setClass_detail_id(Integer integer) {
		class_detail_id = integer;
	}

	/**
	 * @param string
	 */
	public void setClass_detail_name(String string) {
		class_detail_name = string;
	}

	/**
	 * @return
	 */
	public Integer getClass_maxvalue() {
		return class_maxvalue;
	}

	/**
	 * @return
	 */
	public Integer getClass_minvalue() {
		return class_minvalue;
	}

	/**
	 * @param integer
	 */
	public void setClass_maxvalue(Integer integer) {
		class_maxvalue = integer;
	}

	/**
	 * @param integer
	 */
	public void setClass_minvalue(Integer integer) {
		class_minvalue = integer;
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

}
