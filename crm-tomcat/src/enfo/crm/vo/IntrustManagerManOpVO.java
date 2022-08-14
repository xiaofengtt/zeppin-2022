package enfo.crm.vo;

public class IntrustManagerManOpVO {
	private Integer serial_no; //序号
	
	private Integer manager_id; //责任人id
	
	private String op_code; //编号
	
	private String op_name; //名称
	
	private Integer input_man; //操作员
	
	
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
	 * @return 返回 manager_id。
	 */
	public Integer getManager_id() {
		return manager_id;
	}
	/**
	 * @param manager_id 要设置的 manager_id。
	 */
	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}
	/**
	 * @return 返回 op_code。
	 */
	public String getOp_code() {
		return op_code;
	}
	/**
	 * @param op_code 要设置的 op_code。
	 */
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	/**
	 * @return 返回 op_name。
	 */
	public String getOp_name() {
		return op_name;
	}
	/**
	 * @param op_name 要设置的 op_name。
	 */
	public void setOp_name(String op_name) {
		this.op_name = op_name;
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

