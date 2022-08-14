/*
 * �������� 2010-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * �����ʼ�����VO
 * @author dingyj
 * @since 2010-1-11
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class EmailListVO {
	
	private java.lang.Long mail_serial_no = new Long(0); //�ʼ����		
	private java.lang.String subject = "";
	private java.lang.String body = "";
	private java.sql.Timestamp send_time;
	private java.lang.Integer input_man;
	
	/**
	 * @return
	 */
	public java.lang.String getBody() {
		return body;
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
	public java.sql.Timestamp getSend_time() {
		return send_time;
	}

	/**
	 * @return
	 */
	public java.lang.String getSubject() {
		return subject;
	}

	/**
	 * @param string
	 */
	public void setBody(java.lang.String string) {
		body = string;
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
	 * @param timestamp
	 */
	public void setSend_time(java.sql.Timestamp timestamp) {
		send_time = timestamp;
	}

	/**
	 * @param string
	 */
	public void setSubject(java.lang.String string) {
		subject = string;
	}

}
