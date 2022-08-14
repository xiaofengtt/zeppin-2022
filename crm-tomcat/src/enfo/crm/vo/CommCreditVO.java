package enfo.crm.vo;

/*
 * 创建日期 2010-1-21
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */


import java.math.BigDecimal;

/**
 * @author yzj
 * 贷款模块 优化增加的VO
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CommCreditVO {

	private java.lang.Integer contract_id;
	private java.lang.Integer product_id;
	private java.lang.String product_name;
	private java.lang.String contract_bh;
	private java.lang.String contract_sub_bh;

	private java.lang.Integer jkr_cust_id;
	private java.math.BigDecimal ht_money;
	private java.lang.Integer start_date;
	private java.lang.Integer end_date;
	private java.lang.Integer sign_date;
	private java.lang.String purpose_type;
	private java.lang.String purpose_type_name;
	private java.lang.String currency_id;
	private java.math.BigDecimal rmb_rate;
	private java.math.BigDecimal rmb_punish_rate;
	private java.math.BigDecimal rmb_charge_rate;
	private java.lang.Integer rmb_period;
	private java.math.BigDecimal wb_fix_rate;
	private java.math.BigDecimal wb_float_rate;
	private java.lang.String wb_float_type;
	private java.lang.String wb_float_type_name;
	private java.lang.Integer wb_fd_period;
	private java.lang.String wb_float_period;
	private java.lang.String wb_float_period_name;
	private java.lang.String quality_level;
	private java.lang.String quality_level_name;
	private java.math.BigDecimal assure_flag1;
	private java.math.BigDecimal assure_flag2;
	private java.math.BigDecimal assure_flag3;
	private java.math.BigDecimal assure_flag4;
	private java.lang.Integer bzr_cust_id1;
	private java.lang.Integer bzr_cust_id2;
	private java.lang.Integer bzr_cust_id3;
	private java.lang.Integer zyr_cust_id;
	private java.lang.Integer zyr_cust_id2;
	private java.lang.Integer zyr_cust_id3;
	private java.lang.Integer dyr_cust_id;
	private java.lang.Integer dyr_cust_id2;
	private java.lang.Integer dyr_cust_id3;
	private java.lang.Integer susong_flag;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private java.lang.Integer modi_man;
	private java.lang.Integer modi_time;
	private java.lang.Integer check_flag;
	private java.lang.Integer check_man;
	private java.lang.Integer check_time;
	private java.lang.String deal_flag;
	private java.lang.String deal_flag_descript;
	private java.lang.Integer contract_xh;
	private java.lang.Integer cd_flag;
	private java.lang.Integer backup_flag;
	private java.lang.String summary;
	private java.lang.String status;
	private java.lang.String status_name;
	private java.lang.String jkr_sub_code;
	private java.lang.String bank_serial_no;
	private java.lang.Integer gx_flag;
	private java.lang.Integer gx_date;
	private java.lang.Integer jg_date;
	private java.lang.Integer new_flag;
	/////////yingxj改
	private java.math.BigDecimal extend_ht_money;
	
	private java.math.BigDecimal fact_rmb_rate;
	private java.math.BigDecimal hg_money;
	private java.lang.Integer extend_start_date;
	private java.lang.Integer extend_end_date;
	private java.lang.Integer extend_sign_date;
	////////////////////////
	private java.lang.String rate_type;
	private java.lang.String rate_type_name;
	
	private java.lang.String jkr_cust_name;
	
	private java.math.BigDecimal out_balance;
	private java.math.BigDecimal out_current_balance;
	private java.lang.Integer dk_flag;
	private Integer cnf_start_date;
	private Integer interest_type;
	private Integer sub_product_id;
    
	private Integer query_assure_flag1;//查询有"信用"比例 1有2无0全部
	private Integer query_assure_flag2;//是否有"保证"比例 1有2无0全部
	private Integer query_assure_flag3;//是否有"质押"比例 1有2无0全部	
	private Integer query_assure_flag4;//是否有"抵押"比例 1有2无0全部
	private BigDecimal ht_money_min;//合同金额下限
	private BigDecimal ht_money_max;//合同金额上限
	private Integer end_date_min;//--合同结束日期下限
	private Integer end_date_max;//--合同结束日期上限
	
	private String fkr_name;//放款人
	private Integer book_code;
	
	private Integer serial_no;
	private String busi_id;

	private String dbfs_type;
	private java.math.BigDecimal dbzr_rate;
	private Integer cust_id;

	private String invest_type;//
	private String invest_type_name;
	private String estate_developer;
	private String develop_item;
	
	private Integer kl_check_flag;//开立审核标志 1未审核 2 已审核  --add by liug
	
	private Integer org_cust_id;//
	private Integer credit_jkr_cust_id;//
	//20101026添加by zjb
	private String credit_type;//贷款业务种类(5106)
	private String credit_type_name;
	private String credit_type2;//贷款形式(5107)
	private String credit_type2_name;
	private String credit_type4;//贷款种类(5108)
	private String credit_type4_name;
	private Integer fixitem_id; //固有项目id

	private Integer with_security_flag;//是否证信合作
	private Integer security_id;//合作证券公司
	private Integer with_private_flag;//是否私募基金合作
	private Integer private_id;//私募基金公司
	private Integer with_gov_flag;//是否信政合作
	private String gov_prov_regional;//省级行政区域
	private String gov_prov_regional_name;//省级行政区域说明
	private String gov_regional;//行政区域
	private String gov_regional_name;//行政区域说明
	private Integer gov_agency_name;//政府机构名称
	private String gov_agency_type;//政府机构类型
	private String gov_agency_type_name;//政府机构类型说明
	private String gov_item_type;//政府项目类型
	private String gov_item_type_name;//政府项目类型说明
	private String gov_repay_source;//政府还款来源
	private String gov_repay_source_name;//政府还示来源说明
	private String gov_risk_manage;//风险控制措施
	private String gov_risk_manage_name;//风险控制措施说明
	private String product_code;
	
	
	/**
	 * @param fixitem_id 要设置的 fixitem_id。
	 */
	public void setFixitem_id(Integer fixitem_id) {
		this.fixitem_id = fixitem_id;
	}
	/**
	 * @return 返回 credit_jkr_cust_id。
	 */
	public Integer getCredit_jkr_cust_id() {
		return credit_jkr_cust_id;
	}
	/**
	 * @param credit_jkr_cust_id 要设置的 credit_jkr_cust_id。
	 */
	public void setCredit_jkr_cust_id(Integer credit_jkr_cust_id) {
		this.credit_jkr_cust_id = credit_jkr_cust_id;
	}
	/**
	 * @return 返回 org_cust_id。
	 */
	public Integer getOrg_cust_id() {
		return org_cust_id;
	}
	/**
	 * @param org_cust_id 要设置的 org_cust_id。
	 */
	public void setOrg_cust_id(Integer org_cust_id) {
		this.org_cust_id = org_cust_id;
	}
	public java.math.BigDecimal getAssure_flag1() {
		return assure_flag1;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getAssure_flag2() {
		return assure_flag2;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getAssure_flag3() {
		return assure_flag3;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getAssure_flag4() {
		return assure_flag4;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBackup_flag() {
		return backup_flag;
	}

	/**
	 * @return
	 */
	public java.lang.String getBank_serial_no() {
		return bank_serial_no;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBzr_cust_id1() {
		return bzr_cust_id1;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBzr_cust_id2() {
		return bzr_cust_id2;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBzr_cust_id3() {
		return bzr_cust_id3;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCd_flag() {
		return cd_flag;
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
	public java.lang.Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCheck_time() {
		return check_time;
	}

	/**
	 * @return
	 */
	public Integer getCnf_start_date() {
		return cnf_start_date;
	}

	/**
	 * @return
	 */
	public java.lang.String getContract_bh() {
		return contract_bh;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getContract_id() {
		return contract_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getContract_sub_bh() {
		return contract_sub_bh;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getContract_xh() {
		return contract_xh;
	}

	/**
	 * @return
	 */
	public java.lang.String getCurrency_id() {
		return currency_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getDeal_flag() {
		return deal_flag;
	}

	/**
	 * @return
	 */
	public java.lang.String getDeal_flag_descript() {
		return deal_flag_descript;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDk_flag() {
		return dk_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDyr_cust_id() {
		return dyr_cust_id;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDyr_cust_id2() {
		return dyr_cust_id2;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getDyr_cust_id3() {
		return dyr_cust_id3;
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
	public Integer getEnd_date_max() {
		return end_date_max;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date_min() {
		return end_date_min;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getExtend_end_date() {
		return extend_end_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getExtend_ht_money() {
		return extend_ht_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getExtend_sign_date() {
		return extend_sign_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getExtend_start_date() {
		return extend_start_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getFact_rmb_rate() {
		return fact_rmb_rate;
	}

	/**
	 * @return
	 */
	public String getFkr_name() {
		return fkr_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getGx_date() {
		return gx_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getGx_flag() {
		return gx_flag;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getHg_money() {
		return hg_money;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getHt_money() {
		return ht_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getHt_money_max() {
		return ht_money_max;
	}

	/**
	 * @return
	 */
	public BigDecimal getHt_money_min() {
		return ht_money_min;
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
	public Integer getInterest_type() {
		return interest_type;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getJg_date() {
		return jg_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getJkr_cust_id() {
		return jkr_cust_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getJkr_cust_name() {
		return jkr_cust_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getJkr_sub_code() {
		return jkr_sub_code;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getModi_man() {
		return modi_man;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getModi_time() {
		return modi_time;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getNew_flag() {
		return new_flag;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getOut_balance() {
		return out_balance;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getOut_current_balance() {
		return out_current_balance;
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
	public java.lang.String getProduct_name() {
		return product_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getPurpose_type() {
		return purpose_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getPurpose_type_name() {
		return purpose_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getQuality_level() {
		return quality_level;
	}

	/**
	 * @return
	 */
	public java.lang.String getQuality_level_name() {
		return quality_level_name;
	}

	/**
	 * @return
	 */
	public Integer getQuery_assure_flag1() {
		return query_assure_flag1;
	}

	/**
	 * @return
	 */
	public Integer getQuery_assure_flag2() {
		return query_assure_flag2;
	}

	/**
	 * @return
	 */
	public Integer getQuery_assure_flag3() {
		return query_assure_flag3;
	}

	/**
	 * @return
	 */
	public Integer getQuery_assure_flag4() {
		return query_assure_flag4;
	}

	/**
	 * @return
	 */
	public java.lang.String getRate_type() {
		return rate_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getRate_type_name() {
		return rate_type_name;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getRmb_charge_rate() {
		return rmb_charge_rate;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getRmb_period() {
		return rmb_period;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getRmb_punish_rate() {
		return rmb_punish_rate;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getRmb_rate() {
		return rmb_rate;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getSign_date() {
		return sign_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getStart_date() {
		return start_date;
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
	public Integer getSub_product_id() {
		return sub_product_id;
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
	public java.lang.Integer getSusong_flag() {
		return susong_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getWb_fd_period() {
		return wb_fd_period;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getWb_fix_rate() {
		return wb_fix_rate;
	}

	/**
	 * @return
	 */
	public java.lang.String getWb_float_period() {
		return wb_float_period;
	}

	/**
	 * @return
	 */
	public java.lang.String getWb_float_period_name() {
		return wb_float_period_name;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getWb_float_rate() {
		return wb_float_rate;
	}

	/**
	 * @return
	 */
	public java.lang.String getWb_float_type() {
		return wb_float_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getWb_float_type_name() {
		return wb_float_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getZyr_cust_id() {
		return zyr_cust_id;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getZyr_cust_id2() {
		return zyr_cust_id2;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getZyr_cust_id3() {
		return zyr_cust_id3;
	}

	/**
	 * @param decimal
	 */
	public void setAssure_flag1(java.math.BigDecimal decimal) {
		assure_flag1 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setAssure_flag2(java.math.BigDecimal decimal) {
		assure_flag2 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setAssure_flag3(java.math.BigDecimal decimal) {
		assure_flag3 = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setAssure_flag4(java.math.BigDecimal decimal) {
		assure_flag4 = decimal;
	}

	/**
	 * @param integer
	 */
	public void setBackup_flag(java.lang.Integer integer) {
		backup_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setBank_serial_no(java.lang.String string) {
		bank_serial_no = string;
	}

	/**
	 * @param integer
	 */
	public void setBzr_cust_id1(java.lang.Integer integer) {
		bzr_cust_id1 = integer;
	}

	/**
	 * @param integer
	 */
	public void setBzr_cust_id2(java.lang.Integer integer) {
		bzr_cust_id2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setBzr_cust_id3(java.lang.Integer integer) {
		bzr_cust_id3 = integer;
	}

	/**
	 * @param integer
	 */
	public void setCd_flag(java.lang.Integer integer) {
		cd_flag = integer;
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
	public void setCheck_man(java.lang.Integer integer) {
		check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_time(java.lang.Integer integer) {
		check_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setCnf_start_date(Integer integer) {
		cnf_start_date = integer;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(java.lang.String string) {
		contract_bh = string;
	}

	/**
	 * @param integer
	 */
	public void setContract_id(java.lang.Integer integer) {
		contract_id = integer;
	}

	/**
	 * @param string
	 */
	public void setContract_sub_bh(java.lang.String string) {
		contract_sub_bh = string;
	}

	/**
	 * @param integer
	 */
	public void setContract_xh(java.lang.Integer integer) {
		contract_xh = integer;
	}

	/**
	 * @param string
	 */
	public void setCurrency_id(java.lang.String string) {
		currency_id = string;
	}

	/**
	 * @param string
	 */
	public void setDeal_flag(java.lang.String string) {
		deal_flag = string;
	}

	/**
	 * @param string
	 */
	public void setDeal_flag_descript(java.lang.String string) {
		deal_flag_descript = string;
	}

	/**
	 * @param integer
	 */
	public void setDk_flag(java.lang.Integer integer) {
		dk_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setDyr_cust_id(java.lang.Integer integer) {
		dyr_cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setDyr_cust_id2(java.lang.Integer integer) {
		dyr_cust_id2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setDyr_cust_id3(java.lang.Integer integer) {
		dyr_cust_id3 = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(java.lang.Integer integer) {
		end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date_max(Integer integer) {
		end_date_max = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date_min(Integer integer) {
		end_date_min = integer;
	}

	/**
	 * @param integer
	 */
	public void setExtend_end_date(java.lang.Integer integer) {
		extend_end_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setExtend_ht_money(java.math.BigDecimal decimal) {
		extend_ht_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setExtend_sign_date(java.lang.Integer integer) {
		extend_sign_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setExtend_start_date(java.lang.Integer integer) {
		extend_start_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFact_rmb_rate(java.math.BigDecimal decimal) {
		fact_rmb_rate = decimal;
	}

	/**
	 * @param string
	 */
	public void setFkr_name(String string) {
		fkr_name = string;
	}

	/**
	 * @param integer
	 */
	public void setGx_date(java.lang.Integer integer) {
		gx_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setGx_flag(java.lang.Integer integer) {
		gx_flag = integer;
	}

	/**
	 * @param decimal
	 */
	public void setHg_money(java.math.BigDecimal decimal) {
		hg_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setHt_money(java.math.BigDecimal decimal) {
		ht_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setHt_money_max(BigDecimal decimal) {
		ht_money_max = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setHt_money_min(BigDecimal decimal) {
		ht_money_min = decimal;
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
	public void setInterest_type(Integer integer) {
		interest_type = integer;
	}

	/**
	 * @param integer
	 */
	public void setJg_date(java.lang.Integer integer) {
		jg_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setJkr_cust_id(java.lang.Integer integer) {
		jkr_cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setJkr_cust_name(java.lang.String string) {
		jkr_cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setJkr_sub_code(java.lang.String string) {
		jkr_sub_code = string;
	}

	/**
	 * @param integer
	 */
	public void setModi_man(java.lang.Integer integer) {
		modi_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setModi_time(java.lang.Integer integer) {
		modi_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setNew_flag(java.lang.Integer integer) {
		new_flag = integer;
	}

	/**
	 * @param decimal
	 */
	public void setOut_balance(java.math.BigDecimal decimal) {
		out_balance = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setOut_current_balance(java.math.BigDecimal decimal) {
		out_current_balance = decimal;
	}

	/**
	 * @param integer
	 */
	public void setProduct_id(java.lang.Integer integer) {
		product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setProduct_name(java.lang.String string) {
		product_name = string;
	}

	/**
	 * @param string
	 */
	public void setPurpose_type(java.lang.String string) {
		purpose_type = string;
	}

	/**
	 * @param string
	 */
	public void setPurpose_type_name(java.lang.String string) {
		purpose_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setQuality_level(java.lang.String string) {
		quality_level = string;
	}

	/**
	 * @param string
	 */
	public void setQuality_level_name(java.lang.String string) {
		quality_level_name = string;
	}

	/**
	 * @param integer
	 */
	public void setQuery_assure_flag1(Integer integer) {
		query_assure_flag1 = integer;
	}

	/**
	 * @param integer
	 */
	public void setQuery_assure_flag2(Integer integer) {
		query_assure_flag2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setQuery_assure_flag3(Integer integer) {
		query_assure_flag3 = integer;
	}

	/**
	 * @param integer
	 */
	public void setQuery_assure_flag4(Integer integer) {
		query_assure_flag4 = integer;
	}

	/**
	 * @param string
	 */
	public void setRate_type(java.lang.String string) {
		rate_type = string;
	}

	/**
	 * @param string
	 */
	public void setRate_type_name(java.lang.String string) {
		rate_type_name = string;
	}

	/**
	 * @param decimal
	 */
	public void setRmb_charge_rate(java.math.BigDecimal decimal) {
		rmb_charge_rate = decimal;
	}

	/**
	 * @param integer
	 */
	public void setRmb_period(java.lang.Integer integer) {
		rmb_period = integer;
	}

	/**
	 * @param decimal
	 */
	public void setRmb_punish_rate(java.math.BigDecimal decimal) {
		rmb_punish_rate = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setRmb_rate(java.math.BigDecimal decimal) {
		rmb_rate = decimal;
	}

	/**
	 * @param integer
	 */
	public void setSign_date(java.lang.Integer integer) {
		sign_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(java.lang.Integer integer) {
		start_date = integer;
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
	 * @param integer
	 */
	public void setSub_product_id(Integer integer) {
		sub_product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(java.lang.String string) {
		summary = string;
	}

	/**
	 * @param integer
	 */
	public void setSusong_flag(java.lang.Integer integer) {
		susong_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setWb_fd_period(java.lang.Integer integer) {
		wb_fd_period = integer;
	}

	/**
	 * @param decimal
	 */
	public void setWb_fix_rate(java.math.BigDecimal decimal) {
		wb_fix_rate = decimal;
	}

	/**
	 * @param string
	 */
	public void setWb_float_period(java.lang.String string) {
		wb_float_period = string;
	}

	/**
	 * @param string
	 */
	public void setWb_float_period_name(java.lang.String string) {
		wb_float_period_name = string;
	}

	/**
	 * @param decimal
	 */
	public void setWb_float_rate(java.math.BigDecimal decimal) {
		wb_float_rate = decimal;
	}

	/**
	 * @param string
	 */
	public void setWb_float_type(java.lang.String string) {
		wb_float_type = string;
	}

	/**
	 * @param string
	 */
	public void setWb_float_type_name(java.lang.String string) {
		wb_float_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setZyr_cust_id(java.lang.Integer integer) {
		zyr_cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setZyr_cust_id2(java.lang.Integer integer) {
		zyr_cust_id2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setZyr_cust_id3(java.lang.Integer integer) {
		zyr_cust_id3 = integer;
	}

	/**
	 * @return
	 */
	public Integer getBook_code() {
		return book_code;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer) {
		book_code = integer;
	}

	/**
	 * @return
	 */
	public String getBusi_id() {
		return busi_id;
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
	public void setBusi_id(String string) {
		busi_id = string;
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
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public String getDbfs_type() {
		return dbfs_type;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDbfs_type(String string) {
		dbfs_type = string;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getDbzr_rate() {
		return dbzr_rate;
	}

	/**
	 * @param decimal
	 */
	public void setDbzr_rate(java.math.BigDecimal decimal) {
		dbzr_rate = decimal;
	}


	/**
	 * @return
	 */
	public String getInvest_type() {
		return invest_type;
	}

	/**
	 * @param string
	 */
	public void setInvest_type(String string) {
		invest_type = string;
	}

	/**
	 * @return
	 */
	public String getInvest_type_name() {
		return invest_type_name;
	}

	/**
	 * @param string
	 */
	public void setInvest_type_name(String string) {
		invest_type_name = string;
	}
	
	/**
	 * @return
	 */
	public String getDevelop_item() {
		return develop_item;
	}	
	
	/**
	 * @param string
	 */
	public void setDevelop_item(String string) {
	 	 develop_item = string;
	}	
	
	/**
	 * @return
	 */
	public String getEstate_developer() {
		return estate_developer;
	}	
	
	/**
	 * @param string
	 */
	 public void setEstate_developer(String string) {
	 	 estate_developer = string;
	}		

	/**
	 * @return 返回 kl_check_flag。
	 */
	public Integer getKl_check_flag() {
		return kl_check_flag;
	}
	/**
	 * @param kl_check_flag 要设置的 kl_check_flag。
	 */
	public void setKl_check_flag(Integer kl_check_flag) {
		this.kl_check_flag = kl_check_flag;
	}
	
	/**
	 * @return 返回 credit_type。
	 */
	public String getCredit_type() {
		return credit_type;
	}
	/**
	 * @param credit_type 要设置的 credit_type。
	 */
	public void setCredit_type(String credit_type) {
		this.credit_type = credit_type;
	}
	/**
	 * @return 返回 credit_type_name。
	 */
	public String getCredit_type_name() {
		return credit_type_name;
	}
	/**
	 * @param credit_type_name 要设置的 credit_type_name。
	 */
	public void setCredit_type_name(String credit_type_name) {
		this.credit_type_name = credit_type_name;
	}
	/**
	 * @return 返回 credit_type2。
	 */
	public String getCredit_type2() {
		return credit_type2;
	}
	/**
	 * @param credit_type2 要设置的 credit_type2。
	 */
	public void setCredit_type2(String credit_type2) {
		this.credit_type2 = credit_type2;
	}
	/**
	 * @return 返回 credit_type2_name。
	 */
	public String getCredit_type2_name() {
		return credit_type2_name;
	}
	/**
	 * @param credit_type2_name 要设置的 credit_type2_name。
	 */
	public void setCredit_type2_name(String credit_type2_name) {
		this.credit_type2_name = credit_type2_name;
	}
	/**
	 * @return 返回 credit_type4。
	 */
	public String getCredit_type4() {
		return credit_type4;
	}
	/**
	 * @param credit_type4 要设置的 credit_type4。
	 */
	public void setCredit_type4(String credit_type4) {
		this.credit_type4 = credit_type4;
	}
	/**
	 * @return 返回 credit_type4_name。
	 */
	public String getCredit_type4_name() {
		return credit_type4_name;
	}
	/**
	 * @param credit_type4_name 要设置的 credit_type4_name。
	 */
	public void setCredit_type4_name(String credit_type4_name) {
		this.credit_type4_name = credit_type4_name;
	}
	/**
	 * @return 返回 fixitem_id。
	 */
	public Integer getFixitem_id() {
		return fixitem_id;
	}
	/**
	 * @return 返回 gov_agency_name。
	 */
	public Integer getGov_agency_name() {
		return gov_agency_name;
	}
	/**
	 * @param gov_agency_name 要设置的 gov_agency_name。
	 */
	public void setGov_agency_name(Integer gov_agency_name) {
		this.gov_agency_name = gov_agency_name;
	}
	/**
	 * @return 返回 gov_agency_type。
	 */
	public String getGov_agency_type() {
		return gov_agency_type;
	}
	/**
	 * @param gov_agency_type 要设置的 gov_agency_type。
	 */
	public void setGov_agency_type(String gov_agency_type) {
		this.gov_agency_type = gov_agency_type;
	}
	/**
	 * @return 返回 gov_agency_type_name。
	 */
	public String getGov_agency_type_name() {
		return gov_agency_type_name;
	}
	/**
	 * @param gov_agency_type_name 要设置的 gov_agency_type_name。
	 */
	public void setGov_agency_type_name(String gov_agency_type_name) {
		this.gov_agency_type_name = gov_agency_type_name;
	}
	/**
	 * @return 返回 gov_item_type。
	 */
	public String getGov_item_type() {
		return gov_item_type;
	}
	/**
	 * @param gov_item_type 要设置的 gov_item_type。
	 */
	public void setGov_item_type(String gov_item_type) {
		this.gov_item_type = gov_item_type;
	}
	/**
	 * @return 返回 gov_item_type_name。
	 */
	public String getGov_item_type_name() {
		return gov_item_type_name;
	}
	/**
	 * @param gov_item_type_name 要设置的 gov_item_type_name。
	 */
	public void setGov_item_type_name(String gov_item_type_name) {
		this.gov_item_type_name = gov_item_type_name;
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
	 * @return 返回 gov_repay_source。
	 */
	public String getGov_repay_source() {
		return gov_repay_source;
	}
	/**
	 * @param gov_repay_source 要设置的 gov_repay_source。
	 */
	public void setGov_repay_source(String gov_repay_source) {
		this.gov_repay_source = gov_repay_source;
	}
	/**
	 * @return 返回 gov_repay_source_name。
	 */
	public String getGov_repay_source_name() {
		return gov_repay_source_name;
	}
	/**
	 * @param gov_repay_source_name 要设置的 gov_repay_source_name。
	 */
	public void setGov_repay_source_name(String gov_repay_source_name) {
		this.gov_repay_source_name = gov_repay_source_name;
	}
	/**
	 * @return 返回 gov_risk_manage。
	 */
	public String getGov_risk_manage() {
		return gov_risk_manage;
	}
	/**
	 * @param gov_risk_manage 要设置的 gov_risk_manage。
	 */
	public void setGov_risk_manage(String gov_risk_manage) {
		this.gov_risk_manage = gov_risk_manage;
	}
	/**
	 * @return 返回 gov_risk_manage_name。
	 */
	public String getGov_risk_manage_name() {
		return gov_risk_manage_name;
	}
	/**
	 * @param gov_risk_manage_name 要设置的 gov_risk_manage_name。
	 */
	public void setGov_risk_manage_name(String gov_risk_manage_name) {
		this.gov_risk_manage_name = gov_risk_manage_name;
	}
	/**
	 * @return 返回 private_id。
	 */
	public Integer getPrivate_id() {
		return private_id;
	}
	/**
	 * @param private_id 要设置的 private_id。
	 */
	public void setPrivate_id(Integer private_id) {
		this.private_id = private_id;
	}
	/**
	 * @return 返回 security_id。
	 */
	public Integer getSecurity_id() {
		return security_id;
	}
	/**
	 * @param security_id 要设置的 security_id。
	 */
	public void setSecurity_id(Integer security_id) {
		this.security_id = security_id;
	}
	/**
	 * @return 返回 with_gov_flag。
	 */
	public Integer getWith_gov_flag() {
		return with_gov_flag;
	}
	/**
	 * @param with_gov_flag 要设置的 with_gov_flag。
	 */
	public void setWith_gov_flag(Integer with_gov_flag) {
		this.with_gov_flag = with_gov_flag;
	}
	/**
	 * @return 返回 with_private_flag。
	 */
	public Integer getWith_private_flag() {
		return with_private_flag;
	}
	/**
	 * @param with_private_flag 要设置的 with_private_flag。
	 */
	public void setWith_private_flag(Integer with_private_flag) {
		this.with_private_flag = with_private_flag;
	}
	/**
	 * @return 返回 with_security_flag。
	 */
	public Integer getWith_security_flag() {
		return with_security_flag;
	}
	/**
	 * @param with_security_flag 要设置的 with_security_flag。
	 */
	public void setWith_security_flag(Integer with_security_flag) {
		this.with_security_flag = with_security_flag;
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
}
