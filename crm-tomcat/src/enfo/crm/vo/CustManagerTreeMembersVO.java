/*
 * �������� 2009-11-25
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
public class CustManagerTreeMembersVO {
	private Integer serial_no;
	private Integer tree_id;
	private Integer managerid;
	private String managername;
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
	 * @return ���� managerid��
	 */
	public Integer getManagerid() {
		return managerid;
	}
	/**
	 * @param managerid Ҫ���õ� managerid��
	 */
	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}
	/**
	 * @return ���� managername��
	 */
	public String getManagername() {
		return managername;
	}
	/**
	 * @param managername Ҫ���õ� managername��
	 */
	public void setManagername(String managername) {
		this.managername = managername;
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
	/**
	 * @return ���� tree_id��
	 */
	public Integer getTree_id() {
		return tree_id;
	}
	/**
	 * @param tree_id Ҫ���õ� tree_id��
	 */
	public void setTree_id(Integer tree_id) {
		this.tree_id = tree_id;
	}
}
