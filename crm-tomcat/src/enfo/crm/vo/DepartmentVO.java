/*
 * 创建日期 2008-5-26
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class DepartmentVO {

	private Integer depart_id;
	private String depart_name;
	private String depart_jc;//部门简称
	private Integer parent_id;//上级部门编号
	private String manager_man;//部门负责人
	private String leader_man;//高管领导
	
	private Integer bottom_flag;//最低部门标志 1是 2否
	private Integer input_man;//操作员
	
	/**
	 * @return
	 */
	public Integer getDepart_id() {
		return depart_id;
	}

	/**
	 * @return
	 */
	public String getDepart_jc() {
		return depart_jc;
	}

	/**
	 * @return
	 */
	public String getDepart_name() {
		return depart_name;
	}
	
	/**
	 * @return
	 */
	public String getLeader_man() {
		return leader_man;
	}

	/**
	 * @return
	 */
	public String getManager_man() {
		return manager_man;
	}

	/**
	 * @return
	 */
	public Integer getParent_id() {
		return parent_id;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDepart_jc(String string) {
		depart_jc = string;
	}

	/**
	 * @param string
	 */
	public void setDepart_name(String string) {
		depart_name = string;
	}

	/**
	 * @param string
	 */
	public void setLeader_man(String string) {
		leader_man = string;
	}

	/**
	 * @param string
	 */
	public void setManager_man(String string) {
		manager_man = string;
	}

	/**
	 * @param integer
	 */
	public void setParent_id(Integer integer) {
		parent_id = integer;
	}

	/**
	 * @return
	 */
	public Integer getBottom_flag() {
		return bottom_flag;
	}

	/**
	 * @param integer
	 */
	public void setBottom_flag(Integer integer) {
		bottom_flag = integer;
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
