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
public class AuthorizationVO {
	private Integer ca_id;
	private String ca_name;
	private String ca_description;
	private Integer managerID;
	private Integer input_man;
	
	/**
	 * @return ���� ca_description��
	 */
	public String getCa_description() {
		return ca_description;
	}
	/**
	 * @param ca_description Ҫ���õ� ca_description��
	 */
	public void setCa_description(String ca_description) {
		this.ca_description = ca_description;
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
	 * @return ���� ca_name��
	 */
	public String getCa_name() {
		return ca_name;
	}
	/**
	 * @param ca_name Ҫ���õ� ca_name��
	 */
	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
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
	 * @return ���� managerID��
	 */
	public Integer getManagerID() {
		return managerID;
	}
	/**
	 * @param managerID Ҫ���õ� managerID��
	 */
	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
	}
}
