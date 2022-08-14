/*
 * 创建日期 2009-11-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author WANGHF
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ScheDulesVO {
	private Integer serial_no;
	private Integer schedule_type;
	private String schedule_name;
	private String schedule_start_date;
	private String schedule_end_date;
	private Integer op_code;
	private String op_name;
	private String content;
	private Integer check_flag;
	private Integer begin_date;
	private Integer end_date;
	private Integer input_man;
	/**
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public String getContent() {
		return content;
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
	public String getOp_name() {
		return op_name;
	}

	/**
	 * @return
	 */
	public String getSchedule_name() {
		return schedule_name;
	}

	/**
	 * @return
	 */
	public Integer getSchedule_type() {
		return schedule_type;
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
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setContent(String string) {
		content = string;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer) {
		op_code = integer;
	}

	/**
	 * @param string
	 */
	public void setOp_name(String string) {
		op_name = string;
	}


	/**
	 * @param string
	 */
	public void setSchedule_name(String string) {
		schedule_name = string;
	}

	/**
	 * @param integer
	 */
	public void setSchedule_type(Integer integer) {
		schedule_type = integer;
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
	public Integer getBegin_date() {
		return begin_date;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @param integer
	 */
	public void setBegin_date(Integer integer) {
		begin_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}



	/**
	 * @return
	 */
	public String getSchedule_end_date() {
		return schedule_end_date;
	}

	/**
	 * @return
	 */
	public String getSchedule_start_date() {
		return schedule_start_date;
	}

	/**
	 * @param string
	 */
	public void setSchedule_end_date(String string) {
		schedule_end_date = string;
	}

	/**
	 * @param string
	 */
	public void setSchedule_start_date(String string) {
		schedule_start_date = string;
	}

}
