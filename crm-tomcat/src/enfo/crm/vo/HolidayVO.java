/*
 * 创建日期 2012-1-13
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

/**
 * @author carlos
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class HolidayVO {
	
	Integer holiday_id;
	String holiday_name;
	Integer input_man;
	
	Integer holiday_day;
	Integer cal_flag;
	Integer creat_task;
	String sms_greeting;
	String email_greeting;
	Integer serial_no;
	Integer yearInt;
	Integer mmddInt;

	/**
	 * @return 返回 cal_flag。
	 */
	public Integer getCal_flag() {
		return cal_flag;
	}
	/**
	 * @param cal_flag 要设置的 cal_flag。
	 */
	public void setCal_flag(Integer cal_flag) {
		this.cal_flag = cal_flag;
	}
	/**
	 * @return 返回 creat_task。
	 */
	public Integer getCreat_task() {
		return creat_task;
	}
	/**
	 * @param creat_task 要设置的 creat_task。
	 */
	public void setCreat_task(Integer creat_task) {
		this.creat_task = creat_task;
	}
	/**
	 * @return 返回 email_greeting。
	 */
	public String getEmail_greeting() {
		return email_greeting;
	}
	/**
	 * @param email_greeting 要设置的 email_greeting。
	 */
	public void setEmail_greeting(String email_greeting) {
		this.email_greeting = email_greeting;
	}
	/**
	 * @return 返回 holiday_day。
	 */
	public Integer getHoliday_day() {
		return holiday_day;
	}
	/**
	 * @param holiday_day 要设置的 holiday_day。
	 */
	public void setHoliday_day(Integer holiday_day) {
		this.holiday_day = holiday_day;
	}
	/**
	 * @return 返回 holiday_id。
	 */
	public Integer getHoliday_id() {
		return holiday_id;
	}
	/**
	 * @param holiday_id 要设置的 holiday_id。
	 */
	public void setHoliday_id(Integer holiday_id) {
		this.holiday_id = holiday_id;
	}
	/**
	 * @return 返回 holiday_name。
	 */
	public String getHoliday_name() {
		return holiday_name;
	}
	/**
	 * @param holiday_name 要设置的 holiday_name。
	 */
	public void setHoliday_name(String holiday_name) {
		this.holiday_name = holiday_name;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 mmddInt。
	 */
	public Integer getMmddInt() {
		return mmddInt;
	}
	/**
	 * @param mmddInt 要设置的 mmddInt。
	 */
	public void setMmddInt(Integer mmddInt) {
		this.mmddInt = mmddInt;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 sms_greeting。
	 */
	public String getSms_greeting() {
		return sms_greeting;
	}
	/**
	 * @param sms_greeting 要设置的 sms_greeting。
	 */
	public void setSms_greeting(String sms_greeting) {
		this.sms_greeting = sms_greeting;
	}
	/**
	 * @return 返回 yearInt。
	 */
	public Integer getYearInt() {
		return yearInt;
	}
	/**
	 * @param yearInt 要设置的 yearInt。
	 */
	public void setYearInt(Integer yearInt) {
		this.yearInt = yearInt;
	}
}
