/*
 * 创建日期 2009-11-18
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PreContractVO {
	private java.lang.Integer book_code;
	private java.lang.Integer serial_no;
	private java.lang.Integer product_id;
    private Integer sub_product_id;
	private java.lang.Integer cust_id;
	private java.lang.String pre_code;
	private java.math.BigDecimal pre_money;
	private java.math.BigDecimal rg_money; //已经认购金额
	private java.lang.Integer rg_date;
	private java.lang.Integer link_man;
	private java.lang.Integer pre_date;
	private java.lang.Integer valid_days;
	private java.lang.Integer end_date;
	private java.lang.String pre_type;
	private java.lang.String pre_type_name;
	private java.lang.String pre_status;
	private java.lang.String pre_status_name;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private java.lang.String summary;
	private java.lang.Integer pre_num;
	private java.lang.Integer rg_num;
	private java.math.BigDecimal uncheck_rg_money;
	private java.lang.String cust_tel;
	private java.lang.String cust_name;
	private java.lang.String cust_source;
	private Integer cust_type;
	private String cust_no;
	private Integer bz_flag;
	private Integer int_flag;
	
	private java.math.BigDecimal reg_money; //登记额度
	private java.math.BigDecimal max_reg_money; //最大登记额度
	private java.math.BigDecimal min_reg_money; //最小登记额度
	private Integer last_rg_date; // 最近认购日期 LAST_PRODUCT_ID
	private Integer last_product_id; // 最近认购产品
	private java.math.BigDecimal last_money; //最近认购金额
	private String post_address;

	private String o_tel;
	private String h_tel;
	private String mobile;
	private String bp;

	//汤双根 2007/03/15
	private Integer reg_date; //登记日期
	private Integer reg_end_date; //到期日期
	private Integer reg_valid_days; //有效天数
	private Integer reg_status; //状态

	private String invest_type;//add by nizh 20090322 项目投向
	private String invest_type_name;
	
	private Integer exp_reg_date;
	private Integer cust_group_id;
	private Integer classdetail_id;
	
	private String per_code;
	private Integer test_code;
	
	private Integer money_status;//缴款状态
	private Integer pre_product_id;
	private String pre_product_name;
	private Integer team_id;
	private String team_name;
	
	
	private Integer date1;
	private Integer date2;
	private BigDecimal money1;
	private BigDecimal money2;
	private Integer bind_serial_no;
	private String customer_id;
	private String email;
	private String address;
	private String sex_name;
	private String product_name;
	private String product_code;
	private Integer start_date;
	private Integer turn_flag;
	private String file_name;
	

	

	private Integer serv_man;
	
	
	
	

	

	
	
	

	

	/*
	 * @return 返回 serv_man。
	 */
	public Integer getServ_man() {
		return serv_man;
	}
	/**
	 * @param serv_man 要设置的 serv_man。
	 */
	public void setServ_man(Integer serv_man) {
		this.serv_man = serv_man;
	}
	
	/**
	 * @return 返回 team_name。
	 */
	public String getTeam_name() {
		return team_name;
	}
	/**
	 * @param team_name 要设置的 team_name。
	 */
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	/**

	 * @return 返回 file_name。
	 */
	public String getFile_name() {
		return file_name;
	}
	/**
	 * @param file_name 要设置的 file_name。
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	/**
	 * @return 返回 turn_flag。
	 */
	public Integer getTurn_flag() {
		return turn_flag;
	}
	/**
	 * @param turn_flag 要设置的 turn_flag。
	 */
	public void setTurn_flag(Integer turn_flag) {
		this.turn_flag = turn_flag;
	}
	/**
	 * @return 返回 start_date。
	 */
	public Integer getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date 要设置的 start_date。
	 */
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return 返回 product_code。
	 */
	public String getProduct_code() {
		return product_code;
	}
	/**
	 * @param product_code 要设置的 product_code。
	 */
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	/**
	 * @return 返回 product_name。
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name 要设置的 product_name。
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return 返回 sex_name。
	 */
	public String getSex_name() {
		return sex_name;
	}
	/**
	 * @param sex_name 要设置的 sex_name。
	 */
	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}
	/**
	 * @return 返回 address。
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address 要设置的 address。
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return 返回 customer_id。
	 */
	public String getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id 要设置的 customer_id。
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return 返回 email。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email 要设置的 email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return 返回 date1。
	 */
	public Integer getDate1() {
		return date1;
	}
	/**
	 * @param date1 要设置的 date1。
	 */
	public void setDate1(Integer date1) {
		this.date1 = date1;
	}
	/**
	 * @return 返回 date2。
	 */
	public Integer getDate2() {
		return date2;
	}
	/**
	 * @param date2 要设置的 date2。
	 */
	public void setDate2(Integer date2) {
		this.date2 = date2;
	}
	/**
	 * @return 返回 money1。
	 */
	public BigDecimal getMoney1() {
		return money1;
	}
	/**
	 * @param money1 要设置的 money1。
	 */
	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}
	/**
	 * @return 返回 money2。
	 */
	public BigDecimal getMoney2() {
		return money2;
	}
	/**
	 * @param money2 要设置的 money2。
	 */
	public void setMoney2(BigDecimal money2) {
		this.money2 = money2;
	}
	public Integer getMoney_status() {
		return money_status;
	}

	public void setMoney_status(Integer money_status) {
		this.money_status = money_status;
	}
	public Integer getTest_code() {
		return test_code;
	}

	public void setTest_code(Integer testCode) {
		test_code = testCode;
	}

	public String getPer_code() {
		return per_code;
	}
 
	public void setPer_code(String perCode) {
		per_code = perCode;
	}

	public Integer getCust_group_id() {
		return cust_group_id;
	}

	public void setCust_group_id(Integer cust_group_id) {
		this.cust_group_id = cust_group_id;
	}

	public Integer getClassdetail_id() {
		return classdetail_id;
	}

	public void setClassdetail_id(Integer classdetail_id) {
		this.classdetail_id = classdetail_id;
	}

	public Integer getExp_reg_date() {
		return exp_reg_date;
	}

	public void setExp_reg_date(Integer exp_reg_date) {
		this.exp_reg_date = exp_reg_date;
	}

	public java.lang.String getCust_source() {
		return cust_source;
	}

	public void setCust_source(java.lang.String cust_source) {
		this.cust_source = cust_source;
	}

	public String getInvest_type_name() {
		return invest_type_name;
	}

	public void setInvest_type_name(String invest_type_name) {
		this.invest_type_name = invest_type_name;
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
	public String getBp() {
		return bp;
	}

	/**
	 * @return
	 */
	public Integer getBz_flag() {
		return bz_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_name() {
		return cust_name;
	}

	/**
	 * @return
	 */
	public String getCust_no() {
		return cust_no;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_tel() {
		return cust_tel;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public String getH_tel() {
		return h_tel;
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
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getInt_flag() {
		return int_flag;
	}

	/**
	 * @return
	 */
	public String getInvest_type() {
		return invest_type;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getLast_money() {
		return last_money;
	}

	/**
	 * @return
	 */
	public Integer getLast_product_id() {
		return last_product_id;
	}

	/**
	 * @return
	 */
	public Integer getLast_rg_date() {
		return last_rg_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getLink_man() {
		return link_man;
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
	public String getO_tel() {
		return o_tel;
	}

	/**
	 * @return
	 */
	public String getPost_address() {
		return post_address;
	}

	/**
	 * @return
	 */
	public java.lang.String getPre_code() {
		return pre_code;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getPre_date() {
		return pre_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getPre_money() {
		return pre_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getPre_num() {
		return pre_num;
	}

	/**
	 * @return
	 */
	public java.lang.String getPre_status() {
		return pre_status;
	}

	/**
	 * @return
	 */
	public java.lang.String getPre_status_name() {
		return pre_status_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getPre_type() {
		return pre_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getPre_type_name() {
		return pre_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getProduct_id() {
		return product_id;
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
	public Integer getReg_end_date() {
		return reg_end_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getReg_money() {
		return reg_money;
	}

	/**
	 * @return
	 */
	public Integer getReg_status() {
		return reg_status;
	}

	/**
	 * @return
	 */
	public Integer getReg_valid_days() {
		return reg_valid_days;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRg_date() {
		return rg_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getRg_money() {
		return rg_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRg_num() {
		return rg_num;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public java.lang.String getSummary() {
		return summary;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getUncheck_rg_money() {
		return uncheck_rg_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getValid_days() {
		return valid_days;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(java.lang.Integer integer) {
		book_code = integer;
	}

	/**
	 * @param string
	 */
	public void setBp(String string) {
		bp = string;
	}

	/**
	 * @param integer
	 */
	public void setBz_flag(Integer integer) {
		bz_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(java.lang.Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setCust_name(java.lang.String string) {
		cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_no(String string) {
		cust_no = string;
	}

	/**
	 * @param string
	 */
	public void setCust_tel(java.lang.String string) {
		cust_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(java.lang.Integer integer) {
		end_date = integer;
	}

	/**
	 * @param string
	 */
	public void setH_tel(String string) {
		h_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(java.lang.Integer integer) {
		input_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInput_time(java.sql.Timestamp timestamp) {
		input_time = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setInt_flag(Integer integer) {
		int_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setInvest_type(String string) {
		invest_type = string;
	}

	/**
	 * @param decimal
	 */
	public void setLast_money(java.math.BigDecimal decimal) {
		last_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setLast_product_id(Integer integer) {
		last_product_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setLast_rg_date(Integer integer) {
		last_rg_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setLink_man(java.lang.Integer integer) {
		link_man = integer;
	}

	/**
	 * @param string
	 */
	public void setMobile(String string) {
		mobile = string;
	}

	/**
	 * @param string
	 */
	public void setO_tel(String string) {
		o_tel = string;
	}

	/**
	 * @param string
	 */
	public void setPost_address(String string) {
		post_address = string;
	}

	/**
	 * @param string
	 */
	public void setPre_code(java.lang.String string) {
		pre_code = string;
	}

	/**
	 * @param integer
	 */
	public void setPre_date(java.lang.Integer integer) {
		pre_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setPre_money(java.math.BigDecimal decimal) {
		pre_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setPre_num(java.lang.Integer integer) {
		pre_num = integer;
	}

	/**
	 * @param string
	 */
	public void setPre_status(java.lang.String string) {
		pre_status = string;
	}

	/**
	 * @param string
	 */
	public void setPre_status_name(java.lang.String string) {
		pre_status_name = string;
	}

	/**
	 * @param string
	 */
	public void setPre_type(java.lang.String string) {
		pre_type = string;
	}

	/**
	 * @param string
	 */
	public void setPre_type_name(java.lang.String string) {
		pre_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(java.lang.Integer integer) {
		product_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setReg_date(Integer integer) {
		reg_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setReg_end_date(Integer integer) {
		reg_end_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setReg_money(java.math.BigDecimal decimal) {
		reg_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setReg_status(Integer integer) {
		reg_status = integer;
	}

	/**
	 * @param integer
	 */
	public void setReg_valid_days(Integer integer) {
		reg_valid_days = integer;
	}

	/**
	 * @param integer
	 */
	public void setRg_date(java.lang.Integer integer) {
		rg_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setRg_money(java.math.BigDecimal decimal) {
		rg_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setRg_num(java.lang.Integer integer) {
		rg_num = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(java.lang.Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(java.lang.String string) {
		summary = string;
	}

	/**
	 * @param decimal
	 */
	public void setUncheck_rg_money(java.math.BigDecimal decimal) {
		uncheck_rg_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setValid_days(java.lang.Integer integer) {
		valid_days = integer;
	}

	public Integer getCust_type() {
		return cust_type;
	}

	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
	}

	public java.math.BigDecimal getMax_reg_money() {
		return max_reg_money;
	}

	public void setMax_reg_money(java.math.BigDecimal max_reg_money) {
		this.max_reg_money = max_reg_money;
	}

	public java.math.BigDecimal getMin_reg_money() {
		return min_reg_money;
	}

	public void setMin_reg_money(java.math.BigDecimal min_reg_money) {
		this.min_reg_money = min_reg_money;
	}

	/**
	 * @return 返回 pre_product_id。
	 */
	public Integer getPre_product_id() {
		return pre_product_id;
	}
	/**
	 * @param pre_product_id 要设置的 pre_product_id。
	 */
	public void setPre_product_id(Integer pre_product_id) {
		this.pre_product_id = pre_product_id;
	}
	/**
	 * @return 返回 pre_product_name。
	 */
	public String getPre_product_name() {
		return pre_product_name;
	}
	/**
	 * @param pre_product_name 要设置的 pre_product_name。
	 */
	public void setPre_product_name(String pre_product_name) {
		this.pre_product_name = pre_product_name;
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
	/**
	 * @return 返回 bind_serial_no。
	 */
	public Integer getBind_serial_no() {
		return bind_serial_no;
	}
	/**
	 * @param bind_serial_no 要设置的 bind_serial_no。
	 */
	public void setBind_serial_no(Integer bind_serial_no) {
		this.bind_serial_no = bind_serial_no;
	}
    
    
	/**
	 * @return 返回 sub_product_id。
	 */
	public Integer getSub_product_id() {
		return sub_product_id;
	}
	/**
	 * @param sub_product_id 要设置的 sub_product_id。
	 */
	public void setSub_product_id(Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
}
