/*
 * 创建日期 2012-5-7
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TCrmWebCallVO {
	private Integer op_code;
	private Integer cust_id;
	private String uuid;
	private String webcallId;	
	private Integer input_man;
	
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
	 * @return 返回 op_code。
	 */
	public Integer getOp_code() {
		return op_code;
	}
	/**
	 * @param op_code 要设置的 op_code。
	 */
	public void setOp_code(Integer op_code) {
		this.op_code = op_code;
	}
	/**
	 * @return 返回 uuid。
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid 要设置的 uuid。
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return 返回 webcallId。
	 */
	public String getWebcallId() {
		return webcallId;
	}
	/**
	 * @param webcallId 要设置的 webcallId。
	 */
	public void setWebcallId(String webcallId) {
		this.webcallId = webcallId;
	}
}
