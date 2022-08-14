/*
 * 创建日期 2009-11-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author wanghf
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class TcustmanagerchangesVO {
	private Integer serial_no;
	private Integer managerid_before;
	private String managername_before;
	private Integer managerid_now;
	private String managername_now;
	private Integer check_flag;
	private Integer check_man;
	private Integer input_man;
	private Integer flag1;
	private Integer cust_id;

	/**
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public Integer getManagerid_before() {
		return managerid_before;
	}

	/**
	 * @return
	 */
	public Integer getManagerid_now() {
		return managerid_now;
	}

	/**
	 * @return
	 */
	public String getManagername_before() {
		return managername_before;
	}

	/**
	 * @return
	 */
	public String getManagername_now() {
		return managername_now;
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
	 * @param integer
	 */
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerid_before(Integer integer) {
		managerid_before = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerid_now(Integer integer) {
		managerid_now = integer;
	}

	/**
	 * @param string
	 */
	public void setManagername_before(String string) {
		managername_before = string;
	}

	/**
	 * @param string
	 */
	public void setManagername_now(String string) {
		managername_now = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}
	
	/**
	 * @return 返回 flag1。
	 */
	public Integer getFlag1() {
		return flag1;
	}
	/**
	 * @param flag1 要设置的 flag1。
	 */
	public void setFlag1(Integer flag1) {
		this.flag1 = flag1;
	}
}
