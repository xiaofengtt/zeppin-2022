/*
 * �������� 2010-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * �ʼ�����VO
 * @author dingyj
 * @since 2010-1-11
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class EmailAttachVO {

	private java.lang.Long serial_no = new Long(0); //�ʼ��������
	private java.lang.Long mail_serial_no = new Long(0); //�ʼ����		
	private java.lang.String attach_name = "";
	private java.lang.Integer input_man;

	/**
	 * @return
	 */
	public java.lang.String getAttach_name() {
		return attach_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public java.lang.Long getMail_serial_no() {
		return mail_serial_no;
	}

	/**
	 * @return
	 */
	public java.lang.Long getSerial_no() {
		return serial_no;
	}

	/**
	 * @param string
	 */
	public void setAttach_name(java.lang.String string) {
		attach_name = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(java.lang.Integer integer) {
		input_man = integer;
	}

	/**
	 * @param long1
	 */
	public void setMail_serial_no(java.lang.Long long1) {
		mail_serial_no = long1;
	}

	/**
	 * @param long1
	 */
	public void setSerial_no(java.lang.Long long1) {
		serial_no = long1;
	}

}
