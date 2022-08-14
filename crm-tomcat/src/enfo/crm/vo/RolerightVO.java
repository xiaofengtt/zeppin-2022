/*
 * 创建日期 2008-5-28
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
public class RolerightVO {

	private String menu_id;
	private String menu_name;
	private String menu_info;
	private String parent_id;
	private Integer bottom_flag;//最低级菜单标志 1是 2否
    
    
    private Integer func_id;//功能编号
    private String func_name;
    
    
    private Integer role_id;//角色编号
    
    
    
	/**
	 * @return
	 */
	public Integer getBottom_flag() {
		return bottom_flag;
	}

	/**
	 * @return
	 */
	public Integer getFunc_id() {
		return func_id;
	}

	/**
	 * @return
	 */
	public String getFunc_name() {
		return func_name;
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
	public String getMenu_info() {
		return menu_info;
	}

	/**
	 * @return
	 */
	public String getMenu_name() {
		return menu_name;
	}

	/**
	 * @return
	 */
	public String getParent_id() {
		return parent_id;
	}

	/**
	 * @return
	 */
	public Integer getRole_id() {
		return role_id;
	}

	/**
	 * @param integer
	 */
	public void setBottom_flag(Integer integer) {
		bottom_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setFunc_id(Integer integer) {
		func_id = integer;
	}

	/**
	 * @param string
	 */
	public void setFunc_name(String string) {
		func_name = string;
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
	public void setMenu_info(String string) {
		menu_info = string;
	}

	/**
	 * @param string
	 */
	public void setMenu_name(String string) {
		menu_name = string;
	}

	/**
	 * @param string
	 */
	public void setParent_id(String string) {
		parent_id = string;
	}

	/**
	 * @param integer
	 */
	public void setRole_id(Integer integer) {
		role_id = integer;
	}

}
