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
 * 评分标准对象对应GradeIndexVO对象
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GradeIndexVO {

	private Integer index_id;
	private Integer grade_id;//评级体系ID
	private Integer xh; //序号
	private String index_type; //类别
	private String index_type_name; //类别名称
	private String index_name; //细则名称
	private Integer value_flag; //值来源：1手工录入2 通过计算得到3 布尔型
	private String value_unit; //单位
	private String value_info; //值计算说明
	private BigDecimal st_value; //标准值
	private BigDecimal zb_right; //权重
	private String df_gs; //得分公式
	private BigDecimal df_max; //最大得分
	private BigDecimal df_min; //最小得分
	private String df_info; //得分计算说明
	private Integer input_man; //细则设置人员
	private Timestamp input_time; //时间
	private Integer valid_flag; //是否有效
	private Integer op_code; //操作员
	/**
	 * @return
	 */
	public String getDf_gs() {
		return df_gs;
	}

	/**
	 * @return
	 */
	public String getDf_info() {
		return df_info;
	}

	/**
	 * @return
	 */
	public BigDecimal getDf_max() {
		return df_max;
	}

	/**
	 * @return
	 */
	public BigDecimal getDf_min() {
		return df_min;
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
	public String getIndex_name() {
		return index_name;
	}

	/**
	 * @return
	 */
	public String getIndex_type() {
		return index_type;
	}

	/**
	 * @return
	 */
	public String getIndex_type_name() {
		return index_type_name;
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
	public BigDecimal getSt_value() {
		return st_value;
	}

	/**
	 * @return
	 */
	public Integer getValid_flag() {
		return valid_flag;
	}

	/**
	 * @return
	 */
	public Integer getValue_flag() {
		return value_flag;
	}

	/**
	 * @return
	 */
	public String getValue_info() {
		return value_info;
	}

	/**
	 * @return
	 */
	public String getValue_unit() {
		return value_unit;
	}

	/**
	 * @return
	 */
	public Integer getXh() {
		return xh;
	}

	/**
	 * @return
	 */
	public BigDecimal getZb_right() {
		return zb_right;
	}

	/**
	 * @param string
	 */
	public void setDf_gs(String string) {
		df_gs = string;
	}

	/**
	 * @param string
	 */
	public void setDf_info(String string) {
		df_info = string;
	}

	/**
	 * @param decimal
	 */
	public void setDf_max(BigDecimal decimal) {
		df_max = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setDf_min(BigDecimal decimal) {
		df_min = decimal;
	}

	/**
	 * @param integer
	 */
	public void setIndex_id(Integer integer) {
		index_id = integer;
	}

	/**
	 * @param string
	 */
	public void setIndex_name(String string) {
		index_name = string;
	}

	/**
	 * @param string
	 */
	public void setIndex_type(String string) {
		index_type = string;
	}

	/**
	 * @param string
	 */
	public void setIndex_type_name(String string) {
		index_type_name = string;
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
	 * @param decimal
	 */
	public void setSt_value(BigDecimal decimal) {
		st_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setValid_flag(Integer integer) {
		valid_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setValue_flag(Integer integer) {
		value_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setValue_info(String string) {
		value_info = string;
	}

	/**
	 * @param string
	 */
	public void setValue_unit(String string) {
		value_unit = string;
	}

	/**
	 * @param integer
	 */
	public void setXh(Integer integer) {
		xh = integer;
	}

	/**
	 * @param decimal
	 */
	public void setZb_right(BigDecimal decimal) {
		zb_right = decimal;
	}

	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @param integer
	 */
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @return
	 */
	public Integer getOp_code() {
		return op_code;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer) {
		op_code = integer;
	}

}
