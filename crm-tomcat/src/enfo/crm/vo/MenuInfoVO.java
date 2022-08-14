/*
 * 创建日期 2008-5-26
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author guifeng
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class MenuInfoVO {
	private String menu_id;//菜单编号
	private String menu_name;//菜单名称
	private String menu_info;//菜单说明
	private String parent_id;//父菜单号
	private Integer bottom_flag;//最低级菜单标志1是0否
	private String url;
	private Integer tree_order;//排序依据字段
	private Integer user_id;//用户ID，为0表示该菜单所有用户都可用，某个用户不需要时，将该字段设置为不等于用户ID的非0值即可
	private Integer inputman;
	/**
	 * @return
	 */
	public Integer getBottom_flag() {
		return bottom_flag;
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
	public Integer getTree_order() {
		return tree_order;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * @param integer
	 */
	public void setBottom_flag(Integer integer) {
		bottom_flag = integer;
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
	public void setTree_order(Integer integer) {
		tree_order = integer;
	}

	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
	}

	/**
	 * @param integer
	 */
	public void setUser_id(Integer integer) {
		user_id = integer;
	}

}
