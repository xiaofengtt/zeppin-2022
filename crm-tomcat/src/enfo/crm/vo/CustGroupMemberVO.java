/*
 * �������� 2009-12-1
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * �ͻ���ԱȺ������ӦCustGroupMemberVO����
 * @author dingyj
 * @since 2009-12-1
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustGroupMemberVO {

	private Integer serial_no;
	private Integer group_id; //Ⱥ����
	private Integer cust_id; //�ͻ�ID
	private Integer insertMan; //����Ա
	private Timestamp insertTime; //����ʱ��

	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public Integer getInsertMan() {
		return insertMan;
	}

	/**
	 * @return
	 */
	public Timestamp getInsertTime() {
		return insertTime;
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
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setInsertMan(Integer integer) {
		insertMan = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInsertTime(Timestamp timestamp) {
		insertTime = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getGroup_id() {
		return group_id;
	}

	/**
	 * @param integer
	 */
	public void setGroup_id(Integer integer) {
		group_id = integer;
	}

}
