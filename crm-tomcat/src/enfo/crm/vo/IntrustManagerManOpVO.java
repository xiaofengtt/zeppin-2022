package enfo.crm.vo;

public class IntrustManagerManOpVO {
	private Integer serial_no; //���
	
	private Integer manager_id; //������id
	
	private String op_code; //���
	
	private String op_name; //����
	
	private Integer input_man; //����Ա
	
	
	/**
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� manager_id��
	 */
	public Integer getManager_id() {
		return manager_id;
	}
	/**
	 * @param manager_id Ҫ���õ� manager_id��
	 */
	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}
	/**
	 * @return ���� op_code��
	 */
	public String getOp_code() {
		return op_code;
	}
	/**
	 * @param op_code Ҫ���õ� op_code��
	 */
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	/**
	 * @return ���� op_name��
	 */
	public String getOp_name() {
		return op_name;
	}
	/**
	 * @param op_name Ҫ���õ� op_name��
	 */
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
}

