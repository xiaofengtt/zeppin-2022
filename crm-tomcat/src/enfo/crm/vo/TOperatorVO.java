/*
 * 创建日期 2009-10-29
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class TOperatorVO {
	//database	
	private Integer op_code;
	private String op_name;
	private Integer depart_id;
	private String if_manager;
	private Integer role_id;
	private String password;
	private String ip_address;
	private Integer reg_date;
	private String login_user;
	private String login_time;
	private String logout_time;
	private Integer status;
	private Integer is_ip_hold;
	private String address;
	private String telephone;
	private String mobile;
	private String postcode;
	private String email;
	private String depart_name;
	private Integer online;
	private String remark;
	private Integer inputman;
	private String summary;
	private String card_type;
	private String card_id;
	private Integer card_valid_date;
	private Integer iniflag;
	
	private Integer team_id ;
	private String manager_name;
	private Integer query_date;
	private Integer product_id;
	
	
	private Integer flag;
	private Integer intrust_op_code;
	private Integer  intrust_bookcode;
	private Integer etrust_Operator; 
	 

	
	
	/**
	 * @return 返回 etrust_Operator。
	 */
	public Integer getEtrust_Operator() {
		return etrust_Operator;
	}
	/**
	 * @param etrust_Operator 要设置的 etrust_Operator。
	 */
	public void setEtrust_Operator(Integer etrust_Operator) {
		this.etrust_Operator = etrust_Operator;
	}
	/**
	 * @return 返回 intrust_bookcode。
	 */
	public Integer getIntrust_bookcode() {
		return intrust_bookcode;
	}
	/**
	 * @param intrust_bookcode 要设置的 intrust_bookcode。
	 */
	public void setIntrust_bookcode(Integer intrust_bookcode) {
		this.intrust_bookcode = intrust_bookcode;
	}
	/**
	 * @return 返回 intrust_op_code。
	 */
	public Integer getIntrust_op_code() {
		return intrust_op_code;
	}
	/**
	 * @param intrust_op_code 要设置的 intrust_op_code。
	 */
	public void setIntrust_op_code(Integer intrust_op_code) {
		this.intrust_op_code = intrust_op_code;
	}
	/**
	 * @return 返回 flag。
	 */
	public Integer getFlag() { 
		return flag;
	}
	/**
	 * @param flag 要设置的 flag。
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * @return 返回 product_id。
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id 要设置的 product_id。
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public Integer getDepart_id() {
		return depart_id;
	}

	/**
	 * @return
	 */
	public String getDepart_name() {
		return depart_name;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public String getIf_manager() {
		return if_manager;
	}

	/**
	 * @return
	 */
	public Integer getInputman() {
		return inputman;
	}

	/**
	 * @return
	 */
	public String getIp_address() {
		return ip_address;
	}

	/**
	 * @return
	 */
	public Integer getIs_ip_hold() {
		return is_ip_hold;
	}

	/**
	 * @return
	 */
	public String getLogin_time() {
		return login_time;
	}

	/**
	 * @return
	 */
	public String getLogin_user() {
		return login_user;
	}

	/**
	 * @return
	 */
	public String getLogout_time() {
		return logout_time;
	}

	/**
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return
	 */
	public Integer getOnline() {
		return online;
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
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @return
	 */
	public Integer getReg_date() {
		return reg_date;
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
	public Integer getRole_id() {
		return role_id;
	}

	/**
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDepart_name(String string) {
		depart_name = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setIf_manager(String string) {
		if_manager = string;
	}

	/**
	 * @param integer
	 */
	public void setInputman(Integer integer) {
		inputman = integer;
	}

	/**
	 * @param string
	 */
	public void setIp_address(String string) {
		ip_address = string;
	}

	/**
	 * @param integer
	 */
	public void setIs_ip_hold(Integer integer) {
		is_ip_hold = integer;
	}

	/**
	 * @param string
	 */
	public void setLogin_time(String string) {
		login_time = string;
	}

	/**
	 * @param string
	 */
	public void setLogin_user(String string) {
		login_user = string;
	}

	/**
	 * @param string
	 */
	public void setLogout_time(String string) {
		logout_time = string;
	}

	/**
	 * @param string
	 */
	public void setMobile(String string) {
		mobile = string;
	}

	/**
	 * @param integer
	 */
	public void setOnline(Integer integer) {
		online = integer;
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
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setPostcode(String string) {
		postcode = string;
	}

	/**
	 * @param integer
	 */
	public void setReg_date(Integer integer) {
		reg_date = integer;
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
	public void setRole_id(Integer integer) {
		role_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setStatus(Integer integer) {
		status = integer;
	}

	/**
	 * @param string
	 */
	public void setTelephone(String string) {
		telephone = string;
	}

	/**
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @return
	 */
	public String getCard_id() {
		return card_id;
	}

	/**
	 * @return
	 */
	public String getCard_type() {
		return card_type;
	}

	/**
	 * @return
	 */
	public Integer getCard_valid_date() {
		return card_valid_date;
	}

	/**
	 * @param string
	 */
	public void setCard_id(String string) {
		card_id = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type(String string) {
		card_type = string;
	}

	/**
	 * @param integer
	 */
	public void setCard_valid_date(Integer integer) {
		card_valid_date = integer;
	}

	/**
	 * @return
	 */
	public Integer getIniflag() {
		return iniflag;
	}

	/**
	 * @param integer
	 */
	public void setIniflag(Integer integer) {
		iniflag = integer;
	}

	/**
	 * @return 返回 manager_name。
	 */
	public String getManager_name() {
		return manager_name;
	}
	/**
	 * @param manager_name 要设置的 manager_name。
	 */
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	/**
	 * @return 返回 query_date。
	 */
	public Integer getQuery_date() {
		return query_date;
	}
	/**
	 * @param query_date 要设置的 query_date。
	 */
	public void setQuery_date(Integer query_date) {
		this.query_date = query_date;
	}
	/**
	 * @return 返回 team_id。
	 */
	public Integer getTeam_id() {
		return team_id;
	}
	/**
	 * @param team_id 要设置的 team_id。
	 */
	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}
}
