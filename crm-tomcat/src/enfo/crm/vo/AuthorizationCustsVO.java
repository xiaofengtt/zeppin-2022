/*
 * �������� 2011-05-18
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class AuthorizationCustsVO {
	private Integer serial_no;
	private Integer ca_id;
	private Integer cust_id;
	private Integer input_man;
	private Integer auth_flag;//1�ɱ༭   2����ѯ
	
	
	
	public Integer getAuth_flag() {
		return auth_flag;
	}
	public void setAuth_flag(Integer authFlag) {
		auth_flag = authFlag;
	}
	/**
	 * @return ���� ca_id��
	 */
	public Integer getCa_id() {
		return ca_id;
	}
	/**
	 * @param ca_id Ҫ���õ� ca_id��
	 */
	public void setCa_id(Integer ca_id) {
		this.ca_id = ca_id;
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
