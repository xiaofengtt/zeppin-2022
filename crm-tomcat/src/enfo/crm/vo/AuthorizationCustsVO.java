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
public class AuthorizationCustsVO {
	private Integer serial_no;
	private Integer ca_id;
	private Integer cust_id;
	private Integer input_man;
	private Integer auth_flag;//1可编辑   2仅查询
	
	
	
	public Integer getAuth_flag() {
		return auth_flag;
	}
	public void setAuth_flag(Integer authFlag) {
		auth_flag = authFlag;
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
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
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
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
}
