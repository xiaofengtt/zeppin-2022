/*
 * 创建日期 2011-05-18
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class AuthorizationVO {
	private Integer ca_id;
	private String ca_name;
	private String ca_description;
	private Integer managerID;
	private Integer input_man;
	
	/**
	 * @return 返回 ca_description。
	 */
	public String getCa_description() {
		return ca_description;
	}
	/**
	 * @param ca_description 要设置的 ca_description。
	 */
	public void setCa_description(String ca_description) {
		this.ca_description = ca_description;
	}
	/**
	 * @return 返回 ca_id。
	 */
	public Integer getCa_id() {
		return ca_id;
	}
	/**
	 * @param ca_id 要设置的 ca_id。
	 */
	public void setCa_id(Integer ca_id) {
		this.ca_id = ca_id;
	}
	/**
	 * @return 返回 ca_name。
	 */
	public String getCa_name() {
		return ca_name;
	}
	/**
	 * @param ca_name 要设置的 ca_name。
	 */
	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 managerID。
	 */
	public Integer getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID 要设置的 managerID。
	 */
	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}
}
