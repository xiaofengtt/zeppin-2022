/*
 * 创建日期 2010-1-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author LZHD
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustLevelAuthVO {
	private Integer op_code;
	private Integer product_id;
	private Integer level_value_id;
	private String level_value_name;
	private Integer input_man;
	private Integer level_id;
	/**
	 * @return
	 */
	public Integer getLevel_value_id() {
		return level_value_id;
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
	public Integer getOp_code() {
		return op_code;
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
	public void setLevel_value_id(Integer integer) {
		level_value_id = integer;
	}

	/**
	 * @param string
	 */
	public void setLevel_value_name(String string) {
		level_value_name = string;
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
	public void setProduct_id(Integer integer) {
		product_id = integer;
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
	public Integer getLevel_id() {
		return level_id;
	}

	/**
	 * @param integer
	 */
	public void setLevel_id(Integer integer) {
		level_id = integer;
	}

}
