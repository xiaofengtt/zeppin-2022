/*
 * �������� 2008-5-27
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * @author guifeng
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class LogListVO {
	private java.math.BigInteger serial_no =null;
	private Integer busi_flag = new Integer(0);//ҵ�����
	private String busi_name = "";//ҵ��˵��
	private Timestamp trade_time;//����ʱ��
	private Integer op_code = new Integer(0);//����Ա
	private String summary = "";//��ע
	private Integer start_date;
	private Integer end_date;
	
	private String start_date1;
	private String end_date1;
	
	
	
	/**
	 * @return ���� end_date1��
	 */
	public String getEnd_date1() {
		return end_date1;
	}
	/**
	 * @param end_date1 Ҫ���õ� end_date1��
	 */
	public void setEnd_date1(String end_date1) {
		this.end_date1 = end_date1;
	}
	/**
	 * @return ���� start_date1��
	 */
	public String getStart_date1() {
		return start_date1;
	}
	/**
	 * @param start_date1 Ҫ���õ� start_date1��
	 */
	public void setStart_date1(String start_date1) {
		this.start_date1 = start_date1;
	}
	/**
	 * @return
	 */
	public Integer getBusi_flag() {
		return busi_flag;
	}

	/**
	 * @return
	 */
	public String getBusi_name() {
		return busi_name;
	}

	/**
	 * @return
	 */
	public Integer getOp_code() {
		return op_code;
	}

	/**
	 * @return
	 */
	public java.math.BigInteger getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @return
	 */
	public Timestamp getTrade_time() {
		return trade_time;
	}

	/**
	 * @param integer
	 */
	public void setBusi_flag(Integer integer) {
		busi_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setBusi_name(String string) {
		busi_name = string;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer) {
		op_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(java.math.BigInteger integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @param timestamp
	 */
	public void setTrade_time(Timestamp timestamp) {
		trade_time = timestamp;
	}
	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public Integer getStart_date() {
		return start_date;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(Integer integer) {
		start_date = integer;
	}

}
