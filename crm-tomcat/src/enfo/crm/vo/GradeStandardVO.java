/*
 * 创建日期 2009-11-25
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 评级标准对象对应GradeStandardVO对象
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GradeStandardVO {

	private Integer serial_no; //序号
	private Integer grade_id; //评级体系ID
	private String grade_level; //评级标准
	private String grade_level_name; //评级标准名称
	private String grade_level_sub; //扩展用
	private Integer min_value; //最大值
	private Integer max_value; //最小值
	private String grade_info; //评级内容
	private Integer input_man; //细则设置人员
	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @return
	 */
	public String getGrade_info() {
		return grade_info;
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
	public String getGrade_level_name() {
		return grade_level_name;
	}

	/**
	 * @return
	 */
	public String getGrade_level_sub() {
		return grade_level_sub;
	}

	/**
	 * @return
	 */
	public Integer getMax_value() {
		return max_value;
	}

	/**
	 * @return
	 */
	public Integer getMin_value() {
		return min_value;
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
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @param string
	 */
	public void setGrade_info(String string) {
		grade_info = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level(String string) {
		grade_level = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level_name(String string) {
		grade_level_name = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level_sub(String string) {
		grade_level_sub = string;
	}

	/**
	 * @param integer
	 */
	public void setMax_value(Integer integer) {
		max_value = integer;
	}

	/**
	 * @param integer
	 */
	public void setMin_value(Integer integer) {
		min_value = integer;
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

}
