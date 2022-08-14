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
public class RoleVO {

	private Integer role_id;
	private String role_name;
	private String summary;
	private Integer flag_access_all;
	private Integer flag_encryption;
	private Integer input_man;
	private String menu_id;
	private String menu_name;
	private Integer flag;
	
	
	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getRole_id() {
		return role_id;
	}

	/**
	 * @return
	 */
	public String getRole_name() {
		return role_name;
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
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setRole_id(Integer integer) {
		role_id = integer;
	}

	/**
	 * @param string
	 */
	public void setRole_name(String string) {
		role_name = string;
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
	public String getMenu_id() {
		return menu_id;
	}

	/**
	 * @return
	 */
	public String getMenu_name() {
		return menu_name;
	}

	/**
	 * @param string
	 */
	public void setMenu_id(String string) {
		menu_id = string;
	}

	/**
	 * @param string
	 */
	public void setMenu_name(String string) {
		menu_name = string;
	}

	/**
	 * @return
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @param integer
	 */
	public void setFlag(Integer integer) {
		flag = integer;
	}

	public Integer getFlag_access_all() {
		return flag_access_all;
	}

	public void setFlag_access_all(Integer flag_access_all) {
		this.flag_access_all = flag_access_all;
	}

	public Integer getFlag_encryption() {
		return flag_encryption;
	}

	public void setFlag_encryption(Integer flag_encryption) {
		this.flag_encryption = flag_encryption;
	}

}
