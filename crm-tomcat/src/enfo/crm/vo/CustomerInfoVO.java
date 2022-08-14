/*
 * 创建日期 2009-11-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.io.InputStream;
import java.math.BigDecimal;

/**
 * 客户基本信息对象对应CustomerVO对象
 * @author dingyj
 * @since 2009-11-18
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustomerInfoVO {

	private java.lang.Integer cust_id;
	private java.lang.Integer book_code;
	private java.lang.String cust_no;
	private java.lang.String cust_name;
	private java.lang.String card_type;
	private java.lang.String card_type_name;
	private java.lang.String card_id;
	private java.lang.Integer cust_type;
	private java.lang.String cust_type_name;
	private java.lang.Integer wt_flag;
	private java.lang.String wt_flag_name;
	private java.lang.String cust_level;
	private java.lang.String cust_level_name;
	private java.lang.String touch_type;
	private java.lang.String touch_type_name;
	private java.lang.String cust_source;
	private java.lang.String cust_source_name;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private java.lang.Integer check_flag;
	private java.lang.String status;
	private java.lang.String status_name;
	private String cust_tel;
	private String post_address;
	private String post_code;
	private Integer age;
	private Integer sex;
	private String sex_name;
	private String o_tel;
	private String h_tel;
	private String mobile;
	private String bp;
	private String fax;
	private String e_mail;
	private Integer rg_times;
	private java.math.BigDecimal total_money;
	private java.math.BigDecimal current_money;
	private java.math.BigDecimal ben_amount;
	private Integer last_rg_date;
	private Integer check_man;
	private Integer modi_man;
	private Integer modi_time;
	private Integer cancel_man;
	private Integer cancel_time;
	private String summary;
	private String contract_bh;
	private Integer product_id;
	private java.math.BigDecimal curr_to_amount;

	private String legal_man; //法人姓名
	private String legal_address; //法人地址
	private String prov_level;
	private Integer birthday;
	private int birthday_temp; //临时变量,因为NULL值没有.intValue()	
	private String post_address2 = "";
	private String post_code2 = "";
	private Integer print_deploy_bill; //--是否打印客户对帐单（收益情况） 
	private Integer print_post_info; // --是否打印披露信息
	private Integer is_link = new Integer(0); //是否关联方
	private String last_product_name; //--最近购买产品名称
	private java.math.BigDecimal exchange_amount; //-转让金额
	private java.math.BigDecimal back_amount; //-兑付金额
	private Integer from_cust_id; //合并客户开始ID
	private Integer to_cust_id; //合并客户到ID
	private Integer service_man;
	private String vip_card_id;
	private Integer vip_date;
	private String hgtzr_bh;
	private Integer tzr_flag;
	private String voc_type;
	private String voc_type_name;
	private int modi_flag;
	private String contact_man; //联系人
	private Integer card_valid_date; //客户身份证 有效期限 8位日期表示
	private String country; //客户国籍（9901）
	private String jg_cust_type; //机构类别（9921），仅在cust_type=2 机构时有效)
	//MONEY_SOURCE 资金来源
	private String money_source;
	private String money_source_name;
	
	private String recommended;
	private Integer wtr_flag;
	private Integer birthday_end;
	private Integer birthday_month_begin;
	private Integer birthday_month_end;
	private Integer complete_flag;	
	private String bank_acct;
	private Integer serial_no;
	private String bank_id;
	private String bank_name;
	private String acct_name;
    
    private String fcName; // 机构客户的实际控制人
    private String fcCardId;
    private String fcCardType;
    private Integer fcCardValidDate;
    
    // 相关企业客户信息表 INTRUST..TENTCUSTINF用到的字段
    private Integer jt_flag;
    private String cust_code;
    private String card_code;
    
	/**
	 * @return 返回 acct_name。
	 */
	public String getAcct_name() {
		return acct_name;
	}
	/**
	 * @param acct_name 要设置的 acct_name。
	 */
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	/**
	 * @return 返回 bank_name。
	 */
	public String getBank_name() {
		return bank_name;
	}
	/**
	 * @param bank_name 要设置的 bank_name。
	 */
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	/**
	 * @return 返回 bank_id。
	 */
	public String getBank_id() {
		return bank_id;
	}
	/**
	 * @param bank_id 要设置的 bank_id。
	 */
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_acct() {
		return bank_acct;
	}
	public void setBank_acct(String bankAcct) {
		bank_acct = bankAcct;
	}
	public Integer getWtr_flag() {
		return wtr_flag;
	}
	public void setWtr_flag(Integer wtrFlag) {
		wtr_flag = wtrFlag;
	}
	public Integer getBirthday_end() {
		return birthday_end;
	}
	public void setBirthday_end(Integer birthdayEnd) {
		birthday_end = birthdayEnd;
	}
	public Integer getBirthday_month_begin() {
		return birthday_month_begin;
	}
	public void setBirthday_month_begin(Integer birthdayMonthBegin) {
		birthday_month_begin = birthdayMonthBegin;
	}
	public Integer getBirthday_month_end() {
		return birthday_month_end;
	}
	public void setBirthday_month_end(Integer birthdayMonthEnd) {
		birthday_month_end = birthdayMonthEnd;
	}
	public Integer getComplete_flag() {
		return complete_flag;
	}
	public void setComplete_flag(Integer completeFlag) {
		complete_flag = completeFlag;
	}
	public String getRecommended() {
		return recommended;
	}
	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}
	/**
	 * @return 返回 money_source_name。
	 */
	public String getMoney_source_name() {
		return money_source_name;
	}
	/**
	 * @param money_source_name 要设置的 money_source_name。
	 */
	public void setMoney_source_name(String money_source_name) {
		this.money_source_name = money_source_name;
	}
	private String grade_level;
	private String fact_controller; //实际控制人
	//购买等次数
	private Integer min_times;
	private Integer max_times;
	private Integer family_id;

	private String family_name; //家属名称
	private Integer onlyemail;
	private BigDecimal min_total_money; //最大金额
	private BigDecimal max_total_money; //最小金额
	private Integer only_thisproduct; // 第一产品
	private String orderby;
	private BigDecimal ben_amount_min;
	private BigDecimal ben_amount_max;
	private Integer form_cust_id;
	private Integer repaeat_flag; //重复类别标志
	private Integer contactID;
	private InputStream inputStream; //上传图片并转换成二进制流
	private String isClient;//是否委托人
	private String service_man_name;//客户经理姓名
	private String area;
	private BigDecimal potenital_money;
	private Integer is_deal;
	private String gov_prov_regional;//省级行政区域(9999)
	private String gov_prov_regional_name;//省级行政区域说明
	private String gov_regional;//行政区域
	private String gov_regional_name;//行政区域说明
	private Integer groupID;
	
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return 返回 gov_prov_regional。
	 */
	public String getGov_prov_regional() {
		return gov_prov_regional;
	}
	/**
	 * @param gov_prov_regional 要设置的 gov_prov_regional。
	 */
	public void setGov_prov_regional(String gov_prov_regional) {
		this.gov_prov_regional = gov_prov_regional;
	}
	/**
	 * @return 返回 gov_prov_regional_name。
	 */
	public String getGov_prov_regional_name() {
		return gov_prov_regional_name;
	}
	/**
	 * @param gov_prov_regional_name 要设置的 gov_prov_regional_name。
	 */
	public void setGov_prov_regional_name(String gov_prov_regional_name) {
		this.gov_prov_regional_name = gov_prov_regional_name;
	}
	/**
	 * @return 返回 gov_regional。
	 */
	public String getGov_regional() {
		return gov_regional;
	}
	/**
	 * @param gov_regional 要设置的 gov_regional。
	 */
	public void setGov_regional(String gov_regional) {
		this.gov_regional = gov_regional;
	}
	/**
	 * @return 返回 gov_regional_name。
	 */
	public String getGov_regional_name() {
		return gov_regional_name;
	}
	/**
	 * @param gov_regional_name 要设置的 gov_regional_name。
	 */
	public void setGov_regional_name(String gov_regional_name) {
		this.gov_regional_name = gov_regional_name;
	}
	/**
	 * @return
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getBack_amount() {
		return back_amount;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getBen_amount() {
		return ben_amount;
	}

	/**
	 * @return
	 */
	public Integer getBirthday() {
		return birthday;
	}

	/**
	 * @return
	 */
	public int getBirthday_temp() {
		return birthday_temp;
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
	public Integer getCancel_man() {
		return cancel_man;
	}

	/**
	 * @return
	 */
	public Integer getCancel_time() {
		return cancel_time;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_id() {
		return card_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_type() {
		return card_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getCard_type_name() {
		return card_type_name;
	}

	/**
	 * @return
	 */
	public Integer getCard_valid_date() {
		return card_valid_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCheck_flag() {
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
	public String getContact_man() {
		return contact_man;
	}

	/**
	 * @return
	 */
	public String getContract_bh() {
		return contract_bh;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getCurr_to_amount() {
		return curr_to_amount;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getCurrent_money() {
		return current_money;
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
	public java.lang.String getCust_level() {
		return cust_level;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_level_name() {
		return cust_level_name;
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
	public java.lang.String getCust_no() {
		return cust_no;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_source() {
		return cust_source;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_source_name() {
		return cust_source_name;
	}

	/**
	 * @return
	 */
	public String getCust_tel() {
		return cust_tel;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCust_type() {
		return cust_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getCust_type_name() {
		return cust_type_name;
	}

	/**
	 * @return
	 */
	public String getE_mail() {
		return e_mail;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getExchange_amount() {
		return exchange_amount;
	}

	/**
	 * @return
	 */
	public String getFact_controller() {
		return fact_controller;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return
	 */
	public Integer getFrom_cust_id() {
		return from_cust_id;
	}

	/**
	 * @return
	 */
	public String getGrade_level() {
		return grade_level;
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
	public String getHgtzr_bh() {
		return hgtzr_bh;
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
	public Integer getIs_link() {
		return is_link;
	}

	/**
	 * @return
	 */
	public String getJg_cust_type() {
		return jg_cust_type;
	}

	/**
	 * @return
	 */
	public String getLast_product_name() {
		return last_product_name;
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
	public String getLegal_address() {
		return legal_address;
	}

	/**
	 * @return
	 */
	public String getLegal_man() {
		return legal_man;
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
	public int getModi_flag() {
		return modi_flag;
	}

	/**
	 * @return
	 */
	public Integer getModi_man() {
		return modi_man;
	}

	/**
	 * @return
	 */
	public Integer getModi_time() {
		return modi_time;
	}

	/**
	 * @return
	 */
	public String getMoney_source() {
		return money_source;
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
	public String getPost_address2() {
		return post_address2;
	}

	/**
	 * @return
	 */
	public String getPost_code() {
		return post_code;
	}

	/**
	 * @return
	 */
	public String getPost_code2() {
		return post_code2;
	}

	/**
	 * @return
	 */
	public Integer getPrint_deploy_bill() {
		return print_deploy_bill;
	}

	/**
	 * @return
	 */
	public Integer getPrint_post_info() {
		return print_post_info;
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
	public String getProv_level() {
		return prov_level;
	}

	/**
	 * @return
	 */
	public Integer getRg_times() {
		return rg_times;
	}

	/**
	 * @return
	 */
	public Integer getService_man() {
		return service_man;
	}

	/**
	 * @return
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @return
	 */
	public String getSex_name() {
		return sex_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public java.lang.String getStatus_name() {
		return status_name;
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
	public Integer getTo_cust_id() {
		return to_cust_id;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getTotal_money() {
		return total_money;
	}

	/**
	 * @return
	 */
	public java.lang.String getTouch_type() {
		return touch_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getTouch_type_name() {
		return touch_type_name;
	}

	/**
	 * @return
	 */
	public Integer getTzr_flag() {
		return tzr_flag;
	}

	/**
	 * @return
	 */
	public String getVip_card_id() {
		return vip_card_id;
	}

	/**
	 * @return
	 */
	public Integer getVip_date() {
		return vip_date;
	}

	/**
	 * @return
	 */
	public String getVoc_type() {
		return voc_type;
	}

	/**
	 * @return
	 */
	public String getVoc_type_name() {
		return voc_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getWt_flag() {
		return wt_flag;
	}

	/**
	 * @return
	 */
	public java.lang.String getWt_flag_name() {
		return wt_flag_name;
	}

	/**
	 * @param integer
	 */
	public void setAge(Integer integer) {
		age = integer;
	}

	/**
	 * @param decimal
	 */
	public void setBack_amount(java.math.BigDecimal decimal) {
		back_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount(java.math.BigDecimal decimal) {
		ben_amount = decimal;
	}

	/**
	 * @param integer
	 */
	public void setBirthday(Integer integer) {
		birthday = integer;
	}

	/**
	 * @param i
	 */
	public void setBirthday_temp(int i) {
		birthday_temp = i;
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
	public void setCancel_man(Integer integer) {
		cancel_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCancel_time(Integer integer) {
		cancel_time = integer;
	}

	/**
	 * @param string
	 */
	public void setCard_id(java.lang.String string) {
		card_id = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type(java.lang.String string) {
		card_type = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type_name(java.lang.String string) {
		card_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setCard_valid_date(Integer integer) {
		card_valid_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_flag(java.lang.Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param string
	 */
	public void setContact_man(String string) {
		contact_man = string;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(String string) {
		contract_bh = string;
	}

	/**
	 * @param string
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param decimal
	 */
	public void setCurr_to_amount(java.math.BigDecimal decimal) {
		curr_to_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setCurrent_money(java.math.BigDecimal decimal) {
		current_money = decimal;
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
	public void setCust_level(java.lang.String string) {
		cust_level = string;
	}

	/**
	 * @param string
	 */
	public void setCust_level_name(java.lang.String string) {
		cust_level_name = string;
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
	public void setCust_no(java.lang.String string) {
		cust_no = string;
	}

	/**
	 * @param string
	 */
	public void setCust_source(java.lang.String string) {
		cust_source = string;
	}

	/**
	 * @param string
	 */
	public void setCust_source_name(java.lang.String string) {
		cust_source_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_tel(String string) {
		cust_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setCust_type(java.lang.Integer integer) {
		cust_type = integer;
	}

	/**
	 * @param string
	 */
	public void setCust_type_name(java.lang.String string) {
		cust_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setE_mail(String string) {
		e_mail = string;
	}

	/**
	 * @param decimal
	 */
	public void setExchange_amount(java.math.BigDecimal decimal) {
		exchange_amount = decimal;
	}

	/**
	 * @param string
	 */
	public void setFact_controller(String string) {
		fact_controller = string;
	}

	/**
	 * @param string
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
	 * @param integer
	 */
	public void setFrom_cust_id(Integer integer) {
		from_cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setGrade_level(String string) {
		grade_level = string;
	}

	/**
	 * @param string
	 */
	public void setH_tel(String string) {
		h_tel = string;
	}

	/**
	 * @param string
	 */
	public void setHgtzr_bh(String string) {
		hgtzr_bh = string;
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
	public void setIs_link(Integer integer) {
		is_link = integer;
	}

	/**
	 * @param string
	 */
	public void setJg_cust_type(String string) {
		jg_cust_type = string;
	}

	/**
	 * @param string
	 */
	public void setLast_product_name(String string) {
		last_product_name = string;
	}

	/**
	 * @param integer
	 */
	public void setLast_rg_date(Integer integer) {
		last_rg_date = integer;
	}

	/**
	 * @param string
	 */
	public void setLegal_address(String string) {
		legal_address = string;
	}

	/**
	 * @param string
	 */
	public void setLegal_man(String string) {
		legal_man = string;
	}

	/**
	 * @param string
	 */
	public void setMobile(String string) {
		mobile = string;
	}

	/**
	 * @param i
	 */
	public void setModi_flag(int i) {
		modi_flag = i;
	}

	/**
	 * @param integer
	 */
	public void setModi_man(Integer integer) {
		modi_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setModi_time(Integer integer) {
		modi_time = integer;
	}

	/**
	 * @param string
	 */
	public void setMoney_source(String string) {
		money_source = string;
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
	public void setPost_address2(String string) {
		post_address2 = string;
	}

	/**
	 * @param string
	 */
	public void setPost_code(String string) {
		post_code = string;
	}

	/**
	 * @param string
	 */
	public void setPost_code2(String string) {
		post_code2 = string;
	}

	/**
	 * @param integer
	 */
	public void setPrint_deploy_bill(Integer integer) {
		print_deploy_bill = integer;
	}

	/**
	 * @param integer
	 */
	public void setPrint_post_info(Integer integer) {
		print_post_info = integer;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setProv_level(String string) {
		prov_level = string;
	}

	/**
	 * @param integer
	 */
	public void setRg_times(Integer integer) {
		rg_times = integer;
	}

	/**
	 * @param integer
	 */
	public void setService_man(Integer integer) {
		service_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setSex(Integer integer) {
		sex = integer;
	}

	/**
	 * @param string
	 */
	public void setSex_name(String string) {
		sex_name = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(java.lang.String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setStatus_name(java.lang.String string) {
		status_name = string;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @param integer
	 */
	public void setTo_cust_id(Integer integer) {
		to_cust_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTotal_money(java.math.BigDecimal decimal) {
		total_money = decimal;
	}

	/**
	 * @param string
	 */
	public void setTouch_type(java.lang.String string) {
		touch_type = string;
	}

	/**
	 * @param string
	 */
	public void setTouch_type_name(java.lang.String string) {
		touch_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setTzr_flag(Integer integer) {
		tzr_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setVip_card_id(String string) {
		vip_card_id = string;
	}

	/**
	 * @param integer
	 */
	public void setVip_date(Integer integer) {
		vip_date = integer;
	}

	/**
	 * @param string
	 */
	public void setVoc_type(String string) {
		voc_type = string;
	}

	/**
	 * @param string
	 */
	public void setVoc_type_name(String string) {
		voc_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setWt_flag(java.lang.Integer integer) {
		wt_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setWt_flag_name(java.lang.String string) {
		wt_flag_name = string;
	}

	/**
	 * @return
	 */
	public Integer getMax_times() {
		return max_times;
	}

	/**
	 * @return
	 */
	public Integer getMin_times() {
		return min_times;
	}

	/**
	 * @param integer
	 */
	public void setMax_times(Integer integer) {
		max_times = integer;
	}

	/**
	 * @param integer
	 */
	public void setMin_times(Integer integer) {
		min_times = integer;
	}

	/**
	 * @return
	 */
	public String getFamily_name() {
		return family_name;
	}

	/**
	 * @param string
	 */
	public void setFamily_name(String string) {
		family_name = string;
	}

	/**
	 * @return
	 */
	public Integer getOnlyemail() {
		return onlyemail;
	}

	/**
	 * @param integer
	 */
	public void setOnlyemail(Integer integer) {
		onlyemail = integer;
	}

	/**
	 * @return
	 */
	public BigDecimal getMax_total_money() {
		return max_total_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getMin_total_money() {
		return min_total_money;
	}

	/**
	 * @param double1
	 */
	public void setMax_total_money(BigDecimal double1) {
		max_total_money = double1;
	}

	/**
	 * @param double1
	 */
	public void setMin_total_money(BigDecimal double1) {
		min_total_money = double1;
	}

	/**
	 * @return
	 */
	public Integer getOnly_thisproduct() {
		return only_thisproduct;
	}

	/**
	 * @param integer
	 */
	public void setOnly_thisproduct(Integer integer) {
		only_thisproduct = integer;
	}

	/**
	 * @return
	 */
	public String getOrderby() {
		return orderby;
	}

	/**
	 * @param string
	 */
	public void setOrderby(String string) {
		orderby = string;
	}

	/**
	 * @return
	 */
	public BigDecimal getBen_amount_max() {
		return ben_amount_max;
	}

	/**
	 * @return
	 */
	public BigDecimal getBen_amount_min() {
		return ben_amount_min;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount_max(BigDecimal decimal) {
		ben_amount_max = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount_min(BigDecimal decimal) {
		ben_amount_min = decimal;
	}
	/**
	 * @return
	 */
	public Integer getForm_cust_id() {
		return form_cust_id;
	}

	/**
	 * @return
	 */
	public Integer getRepaeat_flag() {
		return repaeat_flag;
	}

	/**
	 * @param integer
	 */
	public void setForm_cust_id(Integer integer) {
		form_cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setRepaeat_flag(Integer integer) {
		repaeat_flag = integer;
	}

	/**
	 * @return
	 */
	public Integer getContactID() {
		return contactID;
	}

	/**
	 * @param integer
	 */
	public void setContactID(Integer integer) {
		contactID = integer;
	}

	/**
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param stream
	 */
	public void setInputStream(InputStream stream) {
		inputStream = stream;
	}


	/**
	 * @return
	 */
	public String getIsClient() {
		return isClient;
	}

	/**
	 * @param string
	 */
	public void setIsClient(String string) {
		isClient = string;
	}

	/**
	 * @return
	 */
	public String getService_man_name() {
		return service_man_name;
	}

	/**
	 * @param string
	 */
	public void setService_man_name(String string) {
		service_man_name = string;
	}

	/**
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @return
	 */
	public BigDecimal getPotenital_money() {
		return potenital_money;
	}

	/**
	 * @param string
	 */
	public void setArea(String string) {
		area = string;
	}

	/**
	 * @param decimal
	 */
	public void setPotenital_money(BigDecimal decimal) {
		potenital_money = decimal;
	}

	/**
	 * @return
	 */
	public Integer getIs_deal() {
		return is_deal;
	}

	/**
	 * @param integer
	 */
	public void setIs_deal(Integer integer) {
		is_deal = integer;
	}
	/**
	 * @return 返回 family_id。
	 */
	public Integer getFamily_id() {
		return family_id;
	}
	/**
	 * @param family_id 要设置的 family_id。
	 */
	public void setFamily_id(Integer family_id) {
		this.family_id = family_id;
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
	 * @return 返回 fcCardId。
	 */
	public String getFcCardId() {
		return fcCardId;
	}
	/**
	 * @param fcCardId 要设置的 fcCardId。
	 */
	public void setFcCardId(String fcCardId) {
		this.fcCardId = fcCardId;
	}
	/**
	 * @return 返回 fcCardType。
	 */
	public String getFcCardType() {
		return fcCardType;
	}
	/**
	 * @param fcCardType 要设置的 fcCardType。
	 */
	public void setFcCardType(String fcCardType) {
		this.fcCardType = fcCardType;
	}
	/**
	 * @return 返回 fcCardValidDate。
	 */
	public Integer getFcCardValidDate() {
		return fcCardValidDate;
	}
	/**
	 * @param fcCardValidDate 要设置的 fcCardValidDate。
	 */
	public void setFcCardValidDate(Integer fcCardValidDate) {
		this.fcCardValidDate = fcCardValidDate;
	}
	/**
	 * @return 返回 fcName。
	 */
	public String getFcName() {
		return fcName;
	}
	/**
	 * @param fcName 要设置的 fcName。
	 */
	public void setFcName(String fcName) {
		this.fcName = fcName;
	}
    
    
	/**
	 * @return 返回 card_code。
	 */
	public String getCard_code() {
		return card_code;
	}
	/**
	 * @param card_code 要设置的 card_code。
	 */
	public void setCard_code(String card_code) {
		this.card_code = card_code;
	}
	/**
	 * @return 返回 cust_code。
	 */
	public String getCust_code() {
		return cust_code;
	}
	/**
	 * @param cust_code 要设置的 cust_code。
	 */
	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}
	/**
	 * @return 返回 jt_flag。
	 */
	public Integer getJt_flag() {
		return jt_flag;
	}
	/**
	 * @param jt_flag 要设置的 jt_flag。
	 */
	public void setJt_flag(Integer jt_flag) {
		this.jt_flag = jt_flag;
	}
}	
