/*
 * �������� 2009-11-27
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
public class TcustmanagerchangesVO {
	private Integer serial_no;
	private Integer managerid_before;
	private String managername_before;
	private Integer managerid_now;
	private String managername_now;
	private Integer check_flag;
	private Integer check_man;
	private Integer input_man;
	private Integer flag1;
	private Integer cust_id;

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
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getManagerid_before() {
		return managerid_before;
	}

	/**
	 * @return
	 */
	public Integer getManagerid_now() {
		return managerid_now;
	}

	/**
	 * @return
	 */
	public String getManagername_before() {
		return managername_before;
	}

	/**
	 * @return
	 */
	public String getManagername_now() {
		return managername_now;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerid_before(Integer integer) {
		managerid_before = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerid_now(Integer integer) {
		managerid_now = integer;
	}

	/**
	 * @param string
	 */
	public void setManagername_before(String string) {
		managername_before = string;
	}

	/**
	 * @param string
	 */
	public void setManagername_now(String string) {
		managername_now = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}
	
	/**
	 * @return ���� flag1��
	 */
	public Integer getFlag1() {
		return flag1;
	}
	/**
	 * @param flag1 Ҫ���õ� flag1��
	 */
	public void setFlag1(Integer flag1) {
		this.flag1 = flag1;
	}
}
