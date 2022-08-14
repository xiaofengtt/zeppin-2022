package enfo.crm.vo;

import enfo.crm.system.InputMan;

/**
 * 系统登陆操作员对象对应的VO对象
 * @author Felix
 * @since 2008-5-26
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class OperatorVO {
	
	private java.lang.Integer op_code;
	private java.lang.String op_name;
	private java.lang.String password;
	private java.lang.Integer reg_date;
	private java.lang.Integer book_code;
	private java.lang.String ip_address;
	private java.lang.Integer cancel_date;
	private java.sql.Timestamp login_time;
	private java.sql.Timestamp logout_time;
	private java.lang.Integer status;
	private java.lang.Integer depart_id;
	private java.lang.Integer role_id;
	private java.lang.String summary;
	private java.lang.String newPassword;
	private String role_name;
	private java.lang.String login_name;
	private java.lang.String login_info;
	private String menu_id;
	private Integer product_id;
	private Integer serial_no;
	private Integer flag;

	private InputMan inputMan = new InputMan();
	private java.lang.String address;
	private java.lang.String email;
	private java.lang.String o_tel;
	private java.lang.String mobile;
	private java.lang.String login_user;
	private java.lang.String main_menu;
	private java.lang.Integer rep_op_code;
	
	
	//yzj
	private String emp_type;//职工类别编号
	private String emp_type_name;//职工类别名称；
	private String emp_level;//职工级别
	private String emp_level_name;//职工级别说明
	private Integer bank_id;//工资发放银行(2119)
	private String bank_name;//工资发放银行名称；
	private String bank_acct;//工资银行账号
	private Integer emp_flag;//是否具有系统操作权限 0否 1是
	
	private Integer inIt_flag;//是否初始化密码 1是 2否
	private String old_password;//旧密码
	private String new_password;//新密码
	private String new_login_user;//新员工号
	
	private String op_staff;//员工简码
	
	private Integer is_ip_hold;//是否绑定IP地址 1是 2否
	
	private Integer begin_date;//日程起始日期
	private Integer end_date;//日程结束日期
	
	// 与sys message有关的字段
	private Integer is_msg_read;
	private String msg_title;
	private String msg_text;
	private Integer from_op_code;
	private Integer to_op_code;	
	private Integer involved_cust_id;
		
	
	/**
	 * @return 返回 involved_cust_id。
	 */
	public Integer getInvolved_cust_id() {
		return involved_cust_id;
	}
	/**
	 * @param involved_cust_id 要设置的 involved_cust_id。
	 */
	public void setInvolved_cust_id(Integer involved_cust_id) {
		this.involved_cust_id = involved_cust_id;
	}
	/**
	 * @return 返回 msg_text。
	 */
	public String getMsg_text() {
		return msg_text;
	}
	/**
	 * @param msg_text 要设置的 msg_text。
	 */
	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}
	/**
	 * @return 返回 from_op_code。
	 */
	public Integer getFrom_op_code() {
		return from_op_code;
	}
	/**
	 * @param from_op_code 要设置的 from_op_code。
	 */
	public void setFrom_op_code(Integer from_op_code) {
		this.from_op_code = from_op_code;
	}
	/**
	 * @return 返回 is_msg_read。
	 */
	public Integer getIs_msg_read() {
		return is_msg_read;
	}
	/**
	 * @param is_msg_read 要设置的 is_msg_read。
	 */
	public void setIs_msg_read(Integer is_msg_read) {
		this.is_msg_read = is_msg_read;
	}
	/**
	 * @return 返回 msg_title。
	 */
	public String getMsg_title() {
		return msg_title;
	}
	/**
	 * @param msg_title 要设置的 msg_title。
	 */
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	/**
	 * @return 返回 to_op_code。
	 */
	public Integer getTo_op_code() {
		return to_op_code;
	}
	/**
	 * @param to_op_code 要设置的 to_op_code。
	 */
	public void setTo_op_code(Integer to_op_code) {
		this.to_op_code = to_op_code;
	}
	/**
	 * @return
	 */
	public java.lang.Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCancel_date() {
		return cancel_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDepart_id() {
		return depart_id;
	}

	/**
	 * @return
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @return
	 */
	public InputMan getInputMan() {
		return inputMan;
	}

	/**
	 * @return
	 */
	public java.lang.String getIp_address() {
		return ip_address;
	}

	/**
	 * @return
	 */
	public java.lang.String getLogin_info() {
		return login_info;
	}

	/**
	 * @return
	 */
	public java.lang.String getLogin_name() {
		return login_name;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getLogin_time() {
		return login_time;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getLogout_time() {
		return logout_time;
	}

	/**
	 * @return
	 */
	public String getMenu_id() {
		return menu_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getNewPassword() {
		return newPassword;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getOp_code() {
		return op_code;
	}

	/**
	 * @return
	 */
	public java.lang.String getOp_name() {
		return op_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getReg_date() {
		return reg_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRole_id() {
		return role_id;
	}

	/**
	 * @return
	 */
	public String getRole_name() {
		return role_name;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public java.lang.String getSummary() {
		return summary;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(java.lang.Integer integer) {
		book_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setCancel_date(java.lang.Integer integer) {
		cancel_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(java.lang.Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setFlag(Integer integer) {
		flag = integer;
	}

	/**
	 * @param man
	 */
	public void setInputMan(InputMan man) {
		inputMan = man;
	}

	/**
	 * @param string
	 */
	public void setIp_address(java.lang.String string) {
		ip_address = string;
	}

	/**
	 * @param string
	 */
	public void setLogin_info(java.lang.String string) {
		login_info = string;
	}

	/**
	 * @param string
	 */
	public void setLogin_name(java.lang.String string) {
		login_name = string;
	}

	/**
	 * @param timestamp
	 */
	public void setLogin_time(java.sql.Timestamp timestamp) {
		login_time = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setLogout_time(java.sql.Timestamp timestamp) {
		logout_time = timestamp;
	}

	/**
	 * @param string
	 */
	public void setMenu_id(String string) {
		menu_id = string;
	}

	/**
	 * @param string
	 */
	public void setNewPassword(java.lang.String string) {
		newPassword = string;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(java.lang.Integer integer) {
		op_code = integer;
	}

	/**
	 * @param string
	 */
	public void setOp_name(java.lang.String string) {
		op_name = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(java.lang.String string) {
		password = string;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setReg_date(java.lang.Integer integer) {
		reg_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setRole_id(java.lang.Integer integer) {
		role_id = integer;
	}

	/**
	 * @param string
	 */
	public void setRole_name(String string) {
		role_name = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param integer
	 */
	public void setStatus(java.lang.Integer integer) {
		status = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(java.lang.String string) {
		summary = string;
	}

	/**
	 * @return
	 */
	public java.lang.String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public java.lang.String getLogin_user() {
		return login_user;
	}

	/**
	 * @return
	 */
	public java.lang.String getMain_menu() {
		return main_menu;
	}

	/**
	 * @return
	 */
	public java.lang.String getMobile() {
		return mobile;
	}

	/**
	 * @return
	 */
	public java.lang.String getO_tel() {
		return o_tel;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRep_op_code() {
		return rep_op_code;
	}

	/**
	 * @param string
	 */
	public void setAddress(java.lang.String string) {
		address = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(java.lang.String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setLogin_user(java.lang.String string) {
		login_user = string;
	}

	/**
	 * @param string
	 */
	public void setMain_menu(java.lang.String string) {
		main_menu = string;
	}

	/**
	 * @param string
	 */
	public void setMobile(java.lang.String string) {
		mobile = string;
	}

	/**
	 * @param string
	 */
	public void setO_tel(java.lang.String string) {
		o_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setRep_op_code(java.lang.Integer integer) {
		rep_op_code = integer;
	}

	/**
	 * @return
	 */
	public String getBank_acct() {
		return bank_acct;
	}

	/**
	 * @return
	 */
	public Integer getBank_id() {
		return bank_id;
	}

	/**
	 * @return
	 */
	public String getBank_name() {
		return bank_name;
	}

	/**
	 * @return
	 */
	public Integer getEmp_flag() {
		return emp_flag;
	}

	/**
	 * @return
	 */
	public String getEmp_level() {
		return emp_level;
	}

	/**
	 * @return
	 */
	public String getEmp_level_name() {
		return emp_level_name;
	}

	/**
	 * @return
	 */
	public String getEmp_type() {
		return emp_type;
	}

	/**
	 * @return
	 */
	public String getEmp_type_name() {
		return emp_type_name;
	}

	/**
	 * @param string
	 */
	public void setBank_acct(String string) {
		bank_acct = string;
	}

	/**
	 * @param string
	 */
	public void setBank_id(Integer string) {
		bank_id =  string;
	}

	/**
	 * @param string
	 */
	public void setBank_name(String string) {
		bank_name = string;
	}

	/**
	 * @param integer
	 */
	public void setEmp_flag(Integer integer) {
		emp_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setEmp_level(String string) {
		emp_level = string;
	}

	/**
	 * @param string
	 */
	public void setEmp_level_name(String string) {
		emp_level_name = string;
	}

	/**
	 * @param string
	 */
	public void setEmp_type(String string) {
		emp_type = string;
	}

	/**
	 * @param string
	 */
	public void setEmp_type_name(String string) {
		emp_type_name = string;
	}

	/**
	 * @return
	 */
	public Integer getInIt_flag() {
		return inIt_flag;
	}

	/**
	 * @param integer
	 */
	public void setInIt_flag(Integer integer) {
		inIt_flag = integer;
	}

	/**
	 * @return
	 */
	public String getNew_password() {
		return new_password;
	}

	/**
	 * @return
	 */
	public String getOld_password() {
		return old_password;
	}

	/**
	 * @param string
	 */
	public void setNew_password(String string) {
		new_password = string;
	}

	/**
	 * @param string
	 */
	public void setOld_password(String string) {
		old_password = string;
	}

	/**
	 * @return
	 */
	public String getNew_login_user() {
		return new_login_user;
	}

	/**
	 * @param string
	 */
	public void setNew_login_user(String string) {
		new_login_user = string;
	}

	/**
	 * @return
	 */
	public Integer getIs_ip_hold() {
		return is_ip_hold;
	}

	/**
	 * @param integer
	 */
	public void setIs_ip_hold(Integer integer) {
		is_ip_hold = integer;
	}

	/**
	 * @return
	 */
	public String getOp_staff() {
		return op_staff;
	}

	/**
	 * @param string
	 */
	public void setOp_staff(String string) {
		op_staff = string;
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

}
