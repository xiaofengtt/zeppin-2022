/*
 * �������� 2012-5-7
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TCrmWebCallVO {
	private Integer op_code;
	private Integer cust_id;
	private String uuid;
	private String webcallId;	
	private Integer input_man;
	
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
	 * @return ���� cust_id��
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� op_code��
	 */
	public Integer getOp_code() {
		return op_code;
	}
	/**
	 * @param op_code Ҫ���õ� op_code��
	 */
	public void setOp_code(Integer op_code) {
		this.op_code = op_code;
	}
	/**
	 * @return ���� uuid��
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid Ҫ���õ� uuid��
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return ���� webcallId��
	 */
	public String getWebcallId() {
		return webcallId;
	}
	/**
	 * @param webcallId Ҫ���õ� webcallId��
	 */
	public void setWebcallId(String webcallId) {
		this.webcallId = webcallId;
	}
}
