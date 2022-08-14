/*
 * 创建日期 2009-11-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class MeetingVO {
	private Integer serial_no;
	private Integer meeting_date;
	private String start_date;
	private String end_date;
	private String meeting_type;
	private String meeting_type_name;
	private String meeting_address;
	private String attend_man;
	private String attend_man_code;
	private String meeting_theme;
	private String remark;
	private Integer input_man;//操作员
	private Integer q_start_date;
	private Integer q_end_date;
	
	
	/**
	 * @return
	 */
	public String getAttend_man() {
		return attend_man;
	}

	/**
	 * @return
	 */
	public String getMeeting_address() {
		return meeting_address;
	}

	/**
	 * @return
	 */
	public Integer getMeeting_date() {
		return meeting_date;
	}

	/**
	 * @return
	 */
	public String getMeeting_theme() {
		return meeting_theme;
	}

	/**
	 * @return
	 */
	public String getMeeting_type() {
		return meeting_type;
	}

	/**
	 * @return
	 */
	public String getMeeting_type_name() {
		return meeting_type_name;
	}

	/**
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param string
	 */
	public void setAttend_man(String string) {
		attend_man = string;
	}

	/**
	 * @param string
	 */
	public void setMeeting_address(String string) {
		meeting_address = string;
	}

	/**
	 * @param integer
	 */
	public void setMeeting_date(Integer integer) {
		meeting_date = integer;
	}

	/**
	 * @param string
	 */
	public void setMeeting_theme(String string) {
		meeting_theme = string;
	}

	/**
	 * @param string
	 */
	public void setMeeting_type(String string) {
		meeting_type = string;
	}

	/**
	 * @param string
	 */
	public void setMeeting_type_name(String string) {
		meeting_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string) {
		remark = string;
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
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @return
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param string
	 */
	public void setEnd_date(String string) {
		end_date = string;
	}

	/**
	 * @param string
	 */
	public void setStart_date(String string) {
		start_date = string;
	}

	/**
	 * @return
	 */
	public Integer getQ_end_date() {
		return q_end_date;
	}

	/**
	 * @return
	 */
	public Integer getQ_start_date() {
		return q_start_date;
	}

	/**
	 * @param integer
	 */
	public void setQ_end_date(Integer integer) {
		q_end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setQ_start_date(Integer integer) {
		q_start_date = integer;
	}

	/**
	 * @return
	 */
	public String getAttend_man_code() {
		return attend_man_code;
	}

	/**
	 * @param string
	 */
	public void setAttend_man_code(String string) {
		attend_man_code = string;
	}

}
