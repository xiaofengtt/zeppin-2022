/*
 * �������� 2009-11-25
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class TcustmanagertreeVO {
	private Integer serial_no;
	private Integer managerid;
	private String managername;
	private Integer left_id;
	private Integer right_id;
	private Integer level_id;
	private String  level_no;
	private String level_name;
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
	 * @return ���� level_name��
	 */
	public String getLevel_name() {
		return level_name;
	}
	/**
	 * @param level_name Ҫ���õ� level_name��
	 */
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	/**
	 * @return ���� level_no��
	 */
	public String getLevel_no() {
		return level_no;
	}
	/**
	 * @param level_no Ҫ���õ� level_no��
	 */
	public void setLevel_no(String level_no) {
		this.level_no = level_no;
	}
	/**
	 * @return
	 */
	public Integer getLeft_id() {
		return left_id;
	}

	/**
	 * @return
	 */
	public Integer getLevel_id() {
		return level_id;
	}

	/**
	 * @return
	 */
	public Integer getManagerid() {
		return managerid;
	}

	/**
	 * @return
	 */
	public String getManagername() {
		return managername;
	}

	/**
	 * @return
	 */
	public Integer getRight_id() {
		return right_id;
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
	public void setLeft_id(Integer integer) {
		left_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setLevel_id(Integer integer) {
		level_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerid(Integer integer) {
		managerid = integer;
	}

	/**
	 * @param string
	 */
	public void setManagername(String string) {
		managername = string;
	}

	/**
	 * @param integer
	 */
	public void setRight_id(Integer integer) {
		right_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

}
