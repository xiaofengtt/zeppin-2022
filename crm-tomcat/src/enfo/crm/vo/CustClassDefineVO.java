/*
 * 创建日期 2009-12-21
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 客户分级定义VO
 * @author dingyj
 * @since 2009-12-21
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustClassDefineVO {

	private Integer class_define_id; //分级ID

	private String class_define_name; //名称

	private String summary; //描述
	
	private Integer default_value; //默认值,必须是TCustClassDetail中的一个明细ID值

	private Integer parent_id; //当前分级的父节点

	private Integer parent_value; //当前分级有父节点时，指定父节点明细值为何值是，当前分级定义有效

	private Integer level_id; //层次编号

	private Integer canmodi; //是否可修改 1是 2否
	
	private Integer input_man; //操作员
	
	private Integer CD_no;//客户评级；2客户分类
	
	public Integer getCD_no() {
		return CD_no;
	}

	public void setCD_no(Integer cDNo) {
		CD_no = cDNo;
	}

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
	public Integer getLevel_id() {
		return level_id;
	}

	/**
	 * @return
	 */
	public Integer getParent_id() {
		return parent_id;
	}

	/**
	 * @return
	 */
	public Integer getParent_value() {
		return parent_value;
	}

	/**
	 * @return
	 */
	public String getSummary() {
		return summary;
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
	public void setLevel_id(Integer integer) {
		level_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setParent_id(Integer integer) {
		parent_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setParent_value(Integer integer) {
		parent_value = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @return
	 */
	public Integer getDefault_value() {
		return default_value;
	}

	/**
	 * @param integer
	 */
	public void setDefault_value(Integer integer) {
		default_value = integer;
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
