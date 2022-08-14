/*
 * 创建日期 2010-1-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 邮件收件人VO
 * @author dingyj
 * @since 2010-1-11
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class EmailRecipientsVO {

	private java.lang.Long serial_no = new Long(0); //邮件附件序号
	private java.lang.Long mail_serial_no = new Long(0); //邮件序号		
	private java.lang.String recipients = "";
	private java.lang.Integer cust_id;
	private java.lang.Integer input_man;
	
	/**
	 * @return
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
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
	public java.lang.String getRecipients() {
		return recipients;
	}

	/**
	 * @return
	 */
	public java.lang.Long getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(java.lang.Integer integer) {
		cust_id = integer;
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
	 * @param string
	 */
	public void setRecipients(java.lang.String string) {
		recipients = string;
	}

	/**
	 * @param long1
	 */
	public void setSerial_no(java.lang.Long long1) {
		serial_no = long1;
	}

}
