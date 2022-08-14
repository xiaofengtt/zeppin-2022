/*
 * 创建日期 2008-5-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * @author guifeng
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class LogListVO {
	private java.math.BigInteger serial_no =null;
	private Integer busi_flag = new Integer(0);//业务类别
	private String busi_name = "";//业务说明
	private Timestamp trade_time;//发生时间
	private Integer op_code = new Integer(0);//操作员
	private String summary = "";//备注
	private Integer start_date;
	private Integer end_date;
	
	private String start_date1;
	private String end_date1;
	
	
	
	/**
	 * @return 返回 end_date1。
	 */
	public String getEnd_date1() {
		return end_date1;
	}
	/**
	 * @param end_date1 要设置的 end_date1。
	 */
	public void setEnd_date1(String end_date1) {
		this.end_date1 = end_date1;
	}
	/**
	 * @return 返回 start_date1。
	 */
	public String getStart_date1() {
		return start_date1;
	}
	/**
	 * @param start_date1 要设置的 start_date1。
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
