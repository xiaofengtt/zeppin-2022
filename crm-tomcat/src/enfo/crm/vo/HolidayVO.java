/*
 * �������� 2012-1-13
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author carlos
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
	 * @return ���� cal_flag��
	 */
	public Integer getCal_flag() {
		return cal_flag;
	}
	/**
	 * @param cal_flag Ҫ���õ� cal_flag��
	 */
	public void setCal_flag(Integer cal_flag) {
		this.cal_flag = cal_flag;
	}
	/**
	 * @return ���� creat_task��
	 */
	public Integer getCreat_task() {
		return creat_task;
	}
	/**
	 * @param creat_task Ҫ���õ� creat_task��
	 */
	public void setCreat_task(Integer creat_task) {
		this.creat_task = creat_task;
	}
	/**
	 * @return ���� email_greeting��
	 */
	public String getEmail_greeting() {
		return email_greeting;
	}
	/**
	 * @param email_greeting Ҫ���õ� email_greeting��
	 */
	public void setEmail_greeting(String email_greeting) {
		this.email_greeting = email_greeting;
	}
	/**
	 * @return ���� holiday_day��
	 */
	public Integer getHoliday_day() {
		return holiday_day;
	}
	/**
	 * @param holiday_day Ҫ���õ� holiday_day��
	 */
	public void setHoliday_day(Integer holiday_day) {
		this.holiday_day = holiday_day;
	}
	/**
	 * @return ���� holiday_id��
	 */
	public Integer getHoliday_id() {
		return holiday_id;
	}
	/**
	 * @param holiday_id Ҫ���õ� holiday_id��
	 */
	public void setHoliday_id(Integer holiday_id) {
		this.holiday_id = holiday_id;
	}
	/**
	 * @return ���� holiday_name��
	 */
	public String getHoliday_name() {
		return holiday_name;
	}
	/**
	 * @param holiday_name Ҫ���õ� holiday_name��
	 */
	public void setHoliday_name(String holiday_name) {
		this.holiday_name = holiday_name;
	}
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
	 * @return ���� mmddInt��
	 */
	public Integer getMmddInt() {
		return mmddInt;
	}
	/**
	 * @param mmddInt Ҫ���õ� mmddInt��
	 */
	public void setMmddInt(Integer mmddInt) {
		this.mmddInt = mmddInt;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� sms_greeting��
	 */
	public String getSms_greeting() {
		return sms_greeting;
	}
	/**
	 * @param sms_greeting Ҫ���õ� sms_greeting��
	 */
	public void setSms_greeting(String sms_greeting) {
		this.sms_greeting = sms_greeting;
	}
	/**
	 * @return ���� yearInt��
	 */
	public Integer getYearInt() {
		return yearInt;
	}
	/**
	 * @param yearInt Ҫ���õ� yearInt��
	 */
	public void setYearInt(Integer yearInt) {
		this.yearInt = yearInt;
	}
}
