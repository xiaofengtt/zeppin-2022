/*
 * 创建日期 2009-12-14
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
public class BenifitorVO {
	private String acct_chg_reason;
	private String acct_user_name;
	private java.math.BigDecimal back_amount;
	private java.lang.String bank_acct;


	//////YZJ////2008-08-21
	private String bank_acct_type;//银行账号类型
	private java.lang.String bank_id;
	private java.lang.String bank_name;
	private java.lang.String bank_sub_name;
	private java.math.BigDecimal ben_amount;
	private java.math.BigDecimal ben_amount2;
	private String  ben_card_no;

	private Integer  ben_count;	//合同数 2006-12-25 caiyuan
	private java.lang.Integer ben_date;
	////////////yingxj20050224
	private java.lang.Integer ben_end_date;
	private Integer ben_lost_date;

	private Integer ben_lost_flag;
	private BigDecimal ben_money;
	private Integer ben_serial_no;
	private String ben_status;
	private String ben_status_name;
	private Integer ben_unfrozen_date;
	
	private java.lang.Integer book_code;
	private Integer card_lost_date;
	private Integer card_lost_flag;
	private java.lang.Integer check_flag;
	private java.lang.Integer check_man;
	private java.lang.String contract_bh;
	private java.lang.String contract_sub_bh;
	///////////////////////

	//添加两个属性 2005-7-29 陶然 begin
	private String cust_acct_name;
	private java.lang.Integer cust_id;
	private String cust_name;
	private String cust_no;
	private Integer prov_flag;
	private Integer sub_product_id;
	private Integer end_date;

	private String card_id;

	/**
	 * add by tsg 2008-03-10
	 * 客户类型
	 * */
	private Integer cust_type;
	//end
	private java.math.BigDecimal exchange_amount;
	private Integer firbid_flag;
	private Integer flag;

	private BigDecimal frozen_money;  //冻结金额
	private java.math.BigDecimal frozen_tmp;
	private java.lang.Integer function_id;
	private String ht_status_name;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private Integer is_transferential;
	private java.lang.String jk_type;
	private java.lang.String jk_type_name;
	private java.lang.Integer list_id;
	private Integer modi_bank_date;
	private Integer modi_check_man;
	private java.sql.Timestamp modi_check_time;
	private Integer modi_man;

	private java.sql.Timestamp modi_time;

	private String new_acct_name;
	private String new_bank_acct;
	private String new_bank_id;
	private String new_bank_sub_name;
	private String new_status;
	private String old_acct_name;
	private String old_bank_acct;

	private String old_bank_id;
	private String old_bank_sub_name;

	private String old_status;
	private Integer  period_unit;
	////////yingxj20041125
	private String product_code;
	private String product_exp_rate;
	private java.lang.Integer product_id;
	private String product_name;
	private Integer product_period;
	private String product_qx;

	private java.lang.String prov_level;
	private java.lang.String prov_level_name;
	/////////////谭鸿20050426添加end

	//////////////////200608016

	private Integer qs_date;
	////////////

	/////////////谭鸿20050426添加en	
	private java.math.BigDecimal remain_amount;
	private java.math.BigDecimal rg_money;
	private java.lang.Integer serial_no;
	private String st_chg_reason;
	private java.lang.Integer start_date;
	private java.lang.String summary;
	private String sy_address;
	private String sy_card_id;
	private String sy_card_type_name;
	private String sy_cust_name;
	private String  sy_cust_no;
	private String temp_acct_name;  
	private java.lang.String temp_bank_acct;
	private java.lang.String temp_bank_id;
	private java.lang.String temp_bank_sub_name;
	private java.lang.String temp_status;
	private java.math.BigDecimal to_amount;
	///////////杨帆20050913添加end
	private String trans_type;//转让类别 捐赠，转让，继承
	private Integer transfer_flag;
	private Integer valid_period;
	private String wt_address;
	private String wt_card_id;
	private String wt_card_type_name;
	private String wt_cust_name;

	private Integer bonus_flag;//收益分配方式
	private Integer temp_bonus_flag;
	
	private String product_status;
	
	private Integer prov_change_date;
	
	private BigDecimal bonus_rate;
	private String bank_province;
	private String bank_city;
	
	private Integer export_flag; // 0非导出查询 1导出受益人信息
	private String export_summary; //导出备注
	private Integer query_flag;
	
	private Integer df_product_id;
	
	private String df_contract_bh;

	private String old_bank_name;
	private String new_bank_name; 
	
	private String modi_man_name;
	
	private String old_bonus_flag_name;
	
	private String new_bonus_flag_name;
	
	private Integer tran_times;
	
	private Integer cxsy_flag;
	
	private Integer bzj_flag;
	private String money_origin;
	private String sub_money_origin;

	 
	private String gov_prov_regional;//省级行政区域(9999)
	private String gov_prov_regional_name;//省级行政区域说明
	private String gov_regional;//行政区域
	private String gov_regional_name;//行政区域说明
	//2018/06/07
    private Integer team_id;
	
	
	
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
	 * @return 返回 bzj_flag。
	 */
	public Integer getBzj_flag() {
		return bzj_flag;
	}
	/**
	 * @param bzj_flag 要设置的 bzj_flag。
	 */
	public void setBzj_flag(Integer bzj_flag) {
		this.bzj_flag = bzj_flag;
	}
	/**
	 * @return 返回 money_origin。
	 */
	public String getMoney_origin() {
		return money_origin;
	}
	/**
	 * @param money_origin 要设置的 money_origin。
	 */
	public void setMoney_origin(String money_origin) {
		this.money_origin = money_origin;
	}
	/**
	 * @return 返回 sub_money_origin。
	 */
	public String getSub_money_origin() {
		return sub_money_origin;
	}
	/**
	 * @param sub_money_origin 要设置的 sub_money_origin。
	 */
	public void setSub_money_origin(String sub_money_origin) {
		this.sub_money_origin = sub_money_origin;
	}
	/**
	 * @return 返回 cxsy_flag。
	 */
	public Integer getCxsy_flag() {
		return cxsy_flag;
	}
	/**
	 * @param cxsy_flag 要设置的 cxsy_flag。
	 */
	public void setCxsy_flag(Integer cxsy_flag) {
		this.cxsy_flag = cxsy_flag;
	}
	/**
	 * @return 返回 export_flag。
	 */
	public Integer getExport_flag() {
		return export_flag;
	}
	/**
	 * @param export_flag 要设置的 export_flag。
	 */
	public void setExport_flag(Integer export_flag) {
		this.export_flag = export_flag;
	}
	/**
	 * @return 返回 bank_city。
	 */
	public String getBank_city() {
		return bank_city;
	}
	/**
	 * @param bank_city 要设置的 bank_city。
	 */
	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}
	/**
	 * @return 返回 bank_province。
	 */
	public String getBank_province() {
		return bank_province;
	}
	/**
	 * @param bank_province 要设置的 bank_province。
	 */
	public void setBank_province(String bank_province) {
		this.bank_province = bank_province;
	}
	/**
	 * @return 返回 bonus_rate。
	 */
	public BigDecimal getBonus_rate() {
		return bonus_rate;
	}
	/**
	 * @param bonus_rate 要设置的 bonus_rate。
	 */
	public void setBonus_rate(BigDecimal bonus_rate) {
		this.bonus_rate = bonus_rate;
	}
	/**
	 * @return 返回 prov_change_date。
	 */
	public Integer getProv_change_date() {
		return prov_change_date;
	}
	/**
	 * @param prov_change_date 要设置的 prov_change_date。
	 */
	public void setProv_change_date(Integer prov_change_date) {
		this.prov_change_date = prov_change_date;
	}
	/**
	 * @return 返回 card_id。
	 */
	public String getCard_id() {
		return card_id;
	}
	/**
	 * @param card_id 要设置的 card_id。
	 */
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	/**
	 * @return
	 */
	public String getAcct_chg_reason() {
		return acct_chg_reason;
	}

	/**
	 * @return
	 */
	public String getAcct_user_name() {
		return acct_user_name;
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
	public java.lang.String getBank_acct() {
		return bank_acct;
	}

	/**
	 * @return
	 */
	public String getBank_acct_type() {
		return bank_acct_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getBank_id() {
		return bank_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getBank_name() {
		return bank_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getBank_sub_name() {
		return bank_sub_name;
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
	public java.math.BigDecimal getBen_amount2() {
		return ben_amount2;
	}

	/**
	 * @return
	 */
	public String getBen_card_no() {
		return ben_card_no;
	}

	/**
	 * @return
	 */
	public Integer getBen_count() {
		return ben_count;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBen_date() {
		return ben_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBen_end_date() {
		return ben_end_date;
	}

	/**
	 * @return
	 */
	public Integer getBen_lost_date() {
		return ben_lost_date;
	}

	/**
	 * @return
	 */
	public Integer getBen_lost_flag() {
		return ben_lost_flag;
	}

	/**
	 * @return
	 */
	public BigDecimal getBen_money() {
		return ben_money;
	}

	/**
	 * @return
	 */
	public Integer getBen_serial_no() {
		return ben_serial_no;
	}

	/**
	 * @return
	 */
	public String getBen_status() {
		return ben_status;
	}

	/**
	 * @return
	 */
	public String getBen_status_name() {
		return ben_status_name;
	}

	/**
	 * @return
	 */
	public Integer getBen_unfrozen_date() {
		return ben_unfrozen_date;
	}

	/**
	 * @return
	 */
	public Integer getBonus_flag() {
		return bonus_flag;
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
	public Integer getCard_lost_date() {
		return card_lost_date;
	}

	/**
	 * @return
	 */
	public Integer getCard_lost_flag() {
		return card_lost_flag;
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
	public java.lang.String getContract_bh() {
		return contract_bh;
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
	public String getCust_acct_name() {
		return cust_acct_name;
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
	public String getCust_name() {
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
	public Integer getCust_type() {
		return cust_type;
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
	public java.math.BigDecimal getExchange_amount() {
		return exchange_amount;
	}

	/**
	 * @return
	 */
	public Integer getFirbid_flag() {
		return firbid_flag;
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
	public BigDecimal getFrozen_money() {
		return frozen_money;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getFrozen_tmp() {
		return frozen_tmp;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getFunction_id() {
		return function_id;
	}

	/**
	 * @return
	 */
	public String getHt_status_name() {
		return ht_status_name;
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
	public Integer getIs_transferential() {
		return is_transferential;
	}

	/**
	 * @return
	 */
	public java.lang.String getJk_type() {
		return jk_type;
	}

	/**
	 * @return
	 */
	public java.lang.String getJk_type_name() {
		return jk_type_name;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getList_id() {
		return list_id;
	}

	/**
	 * @return
	 */
	public Integer getModi_bank_date() {
		return modi_bank_date;
	}

	/**
	 * @return
	 */
	public Integer getModi_check_man() {
		return modi_check_man;
	}

	/**
	 * @return
	 */
	public java.sql.Timestamp getModi_check_time() {
		return modi_check_time;
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
	public java.sql.Timestamp getModi_time() {
		return modi_time;
	}

	/**
	 * @return
	 */
	public String getNew_acct_name() {
		return new_acct_name;
	}

	/**
	 * @return
	 */
	public String getNew_bank_acct() {
		return new_bank_acct;
	}

	/**
	 * @return
	 */
	public String getNew_bank_id() {
		return new_bank_id;
	}

	/**
	 * @return
	 */
	public String getNew_bank_sub_name() {
		return new_bank_sub_name;
	}

	/**
	 * @return
	 */
	public String getNew_status() {
		return new_status;
	}

	/**
	 * @return
	 */
	public String getOld_acct_name() {
		return old_acct_name;
	}

	/**
	 * @return
	 */
	public String getOld_bank_acct() {
		return old_bank_acct;
	}

	/**
	 * @return
	 */
	public String getOld_bank_id() {
		return old_bank_id;
	}

	/**
	 * @return
	 */
	public String getOld_bank_sub_name() {
		return old_bank_sub_name;
	}

	/**
	 * @return
	 */
	public String getOld_status() {
		return old_status;
	}

	/**
	 * @return
	 */
	public Integer getPeriod_unit() {
		return period_unit;
	}

	/**
	 * @return
	 */
	public String getProduct_code() {
		return product_code;
	}

	/**
	 * @return
	 */
	public String getProduct_exp_rate() {
		return product_exp_rate;
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
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @return
	 */
	public Integer getProduct_period() {
		return product_period;
	}

	/**
	 * @return
	 */
	public String getProduct_qx() {
		return product_qx;
	}

	/**
	 * @return
	 */
	public java.lang.String getProv_level() {
		return prov_level;
	}

	/**
	 * @return
	 */
	public java.lang.String getProv_level_name() {
		return prov_level_name;
	}

	/**
	 * @return
	 */
	public Integer getQs_date() {
		return qs_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getRemain_amount() {
		return remain_amount;
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
	public java.lang.Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public String getSt_chg_reason() {
		return st_chg_reason;
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
	public java.lang.String getSummary() {
		return summary;
	}

	/**
	 * @return
	 */
	public String getSy_address() {
		return sy_address;
	}

	/**
	 * @return
	 */
	public String getSy_card_id() {
		return sy_card_id;
	}

	/**
	 * @return
	 */
	public String getSy_card_type_name() {
		return sy_card_type_name;
	}

	/**
	 * @return
	 */
	public String getSy_cust_name() {
		return sy_cust_name;
	}

	/**
	 * @return
	 */
	public String getSy_cust_no() {
		return sy_cust_no;
	}

	/**
	 * @return
	 */
	public String getTemp_acct_name() {
		return temp_acct_name;
	}

	/**
	 * @return
	 */
	public java.lang.String getTemp_bank_acct() {
		return temp_bank_acct;
	}

	/**
	 * @return
	 */
	public java.lang.String getTemp_bank_id() {
		return temp_bank_id;
	}

	/**
	 * @return
	 */
	public java.lang.String getTemp_bank_sub_name() {
		return temp_bank_sub_name;
	}

	/**
	 * @return
	 */
	public Integer getTemp_bonus_flag() {
		return temp_bonus_flag;
	}

	/**
	 * @return
	 */
	public java.lang.String getTemp_status() {
		return temp_status;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getTo_amount() {
		return to_amount;
	}

	/**
	 * @return
	 */
	public String getTrans_type() {
		return trans_type;
	}

	/**
	 * @return
	 */
	public Integer getTransfer_flag() {
		return transfer_flag;
	}

	/**
	 * @return
	 */
	public Integer getValid_period() {
		return valid_period;
	}

	/**
	 * @return
	 */
	public String getWt_address() {
		return wt_address;
	}

	/**
	 * @return
	 */
	public String getWt_card_id() {
		return wt_card_id;
	}

	/**
	 * @return
	 */
	public String getWt_card_type_name() {
		return wt_card_type_name;
	}

	/**
	 * @return
	 */
	public String getWt_cust_name() {
		return wt_cust_name;
	}

	/**
	 * @param string
	 */
	public void setAcct_chg_reason(String string) {
		acct_chg_reason = string;
	}

	/**
	 * @param string
	 */
	public void setAcct_user_name(String string) {
		acct_user_name = string;
	}

	/**
	 * @param decimal
	 */
	public void setBack_amount(java.math.BigDecimal decimal) {
		back_amount = decimal;
	}

	/**
	 * @param string
	 */
	public void setBank_acct(java.lang.String string) {
		bank_acct = string;
	}

	/**
	 * @param string
	 */
	public void setBank_acct_type(String string) {
		bank_acct_type = string;
	}

	/**
	 * @param string
	 */
	public void setBank_id(java.lang.String string) {
		bank_id = string;
	}

	/**
	 * @param string
	 */
	public void setBank_name(java.lang.String string) {
		bank_name = string;
	}

	/**
	 * @param string
	 */
	public void setBank_sub_name(java.lang.String string) {
		bank_sub_name = string;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount(java.math.BigDecimal decimal) {
		ben_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setBen_amount2(java.math.BigDecimal decimal) {
		ben_amount2 = decimal;
	}

	/**
	 * @param string
	 */
	public void setBen_card_no(String string) {
		ben_card_no = string;
	}

	/**
	 * @param integer
	 */
	public void setBen_count(Integer integer) {
		ben_count = integer;
	}

	/**
	 * @param integer
	 */
	public void setBen_date(java.lang.Integer integer) {
		ben_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBen_end_date(java.lang.Integer integer) {
		ben_end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBen_lost_date(Integer integer) {
		ben_lost_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBen_lost_flag(Integer integer) {
		ben_lost_flag = integer;
	}

	/**
	 * @param decimal
	 */
	public void setBen_money(BigDecimal decimal) {
		ben_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setBen_serial_no(Integer integer) {
		ben_serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setBen_status(String string) {
		ben_status = string;
	}

	/**
	 * @param string
	 */
	public void setBen_status_name(String string) {
		ben_status_name = string;
	}

	/**
	 * @param integer
	 */
	public void setBen_unfrozen_date(Integer integer) {
		ben_unfrozen_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBonus_flag(Integer integer) {
		bonus_flag = integer;
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
	public void setCard_lost_date(Integer integer) {
		card_lost_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCard_lost_flag(Integer integer) {
		card_lost_flag = integer;
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
	 * @param string
	 */
	public void setContract_bh(java.lang.String string) {
		contract_bh = string;
	}

	/**
	 * @param string
	 */
	public void setContract_sub_bh(java.lang.String string) {
		contract_sub_bh = string;
	}

	/**
	 * @param string
	 */
	public void setCust_acct_name(String string) {
		cust_acct_name = string;
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
	public void setCust_name(String string) {
		cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setCust_no(String string) {
		cust_no = string;
	}

	/**
	 * @param integer
	 */
	public void setCust_type(Integer integer) {
		cust_type = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(java.lang.Integer integer) {
		end_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setExchange_amount(java.math.BigDecimal decimal) {
		exchange_amount = decimal;
	}

	/**
	 * @param integer
	 */
	public void setFirbid_flag(Integer integer) {
		firbid_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setFlag(Integer integer) {
		flag = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFrozen_money(BigDecimal decimal) {
		frozen_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setFrozen_tmp(java.math.BigDecimal decimal) {
		frozen_tmp = decimal;
	}

	/**
	 * @param integer
	 */
	public void setFunction_id(java.lang.Integer integer) {
		function_id = integer;
	}

	/**
	 * @param string
	 */
	public void setHt_status_name(String string) {
		ht_status_name = string;
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
	public void setIs_transferential(Integer integer) {
		is_transferential = integer;
	}

	/**
	 * @param string
	 */
	public void setJk_type(java.lang.String string) {
		jk_type = string;
	}

	/**
	 * @param string
	 */
	public void setJk_type_name(java.lang.String string) {
		jk_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setList_id(java.lang.Integer integer) {
		list_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setModi_bank_date(Integer integer) {
		modi_bank_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setModi_check_man(Integer integer) {
		modi_check_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setModi_check_time(java.sql.Timestamp timestamp) {
		modi_check_time = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setModi_man(Integer integer) {
		modi_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setModi_time(java.sql.Timestamp timestamp) {
		modi_time = timestamp;
	}

	/**
	 * @param string
	 */
	public void setNew_acct_name(String string) {
		new_acct_name = string;
	}

	/**
	 * @param string
	 */
	public void setNew_bank_acct(String string) {
		new_bank_acct = string;
	}

	/**
	 * @param string
	 */
	public void setNew_bank_id(String string) {
		new_bank_id = string;
	}

	/**
	 * @param string
	 */
	public void setNew_bank_sub_name(String string) {
		new_bank_sub_name = string;
	}

	/**
	 * @param string
	 */
	public void setNew_status(String string) {
		new_status = string;
	}

	/**
	 * @param string
	 */
	public void setOld_acct_name(String string) {
		old_acct_name = string;
	}

	/**
	 * @param string
	 */
	public void setOld_bank_acct(String string) {
		old_bank_acct = string;
	}

	/**
	 * @param string
	 */
	public void setOld_bank_id(String string) {
		old_bank_id = string;
	}

	/**
	 * @param string
	 */
	public void setOld_bank_sub_name(String string) {
		old_bank_sub_name = string;
	}

	/**
	 * @param string
	 */
	public void setOld_status(String string) {
		old_status = string;
	}

	/**
	 * @param integer
	 */
	public void setPeriod_unit(Integer integer) {
		period_unit = integer;
	}

	/**
	 * @param string
	 */
	public void setProduct_code(String string) {
		product_code = string;
	}

	/**
	 * @param string
	 */
	public void setProduct_exp_rate(String string) {
		product_exp_rate = string;
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
	public void setProduct_name(String string) {
		product_name = string;
	}

	/**
	 * @param integer
	 */
	public void setProduct_period(Integer integer) {
		product_period = integer;
	}

	/**
	 * @param string
	 */
	public void setProduct_qx(String string) {
		product_qx = string;
	}

	/**
	 * @param string
	 */
	public void setProv_level(java.lang.String string) {
		prov_level = string;
	}

	/**
	 * @param string
	 */
	public void setProv_level_name(java.lang.String string) {
		prov_level_name = string;
	}

	/**
	 * @param integer
	 */
	public void setQs_date(Integer integer) {
		qs_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setRemain_amount(java.math.BigDecimal decimal) {
		remain_amount = decimal;
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
	public void setSerial_no(java.lang.Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setSt_chg_reason(String string) {
		st_chg_reason = string;
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
	public void setSummary(java.lang.String string) {
		summary = string;
	}

	/**
	 * @param string
	 */
	public void setSy_address(String string) {
		sy_address = string;
	}

	/**
	 * @param string
	 */
	public void setSy_card_id(String string) {
		sy_card_id = string;
	}

	/**
	 * @param string
	 */
	public void setSy_card_type_name(String string) {
		sy_card_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setSy_cust_name(String string) {
		sy_cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setSy_cust_no(String string) {
		sy_cust_no = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_acct_name(String string) {
		temp_acct_name = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_bank_acct(java.lang.String string) {
		temp_bank_acct = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_bank_id(java.lang.String string) {
		temp_bank_id = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_bank_sub_name(java.lang.String string) {
		temp_bank_sub_name = string;
	}

	/**
	 * @param integer
	 */
	public void setTemp_bonus_flag(Integer integer) {
		temp_bonus_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setTemp_status(java.lang.String string) {
		temp_status = string;
	}

	/**
	 * @param decimal
	 */
	public void setTo_amount(java.math.BigDecimal decimal) {
		to_amount = decimal;
	}

	/**
	 * @param string
	 */
	public void setTrans_type(String string) {
		trans_type = string;
	}

	/**
	 * @param integer
	 */
	public void setTransfer_flag(Integer integer) {
		transfer_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setValid_period(Integer integer) {
		valid_period = integer;
	}

	/**
	 * @param string
	 */
	public void setWt_address(String string) {
		wt_address = string;
	}

	/**
	 * @param string
	 */
	public void setWt_card_id(String string) {
		wt_card_id = string;
	}

	/**
	 * @param string
	 */
	public void setWt_card_type_name(String string) {
		wt_card_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setWt_cust_name(String string) {
		wt_cust_name = string;
	}
	/**
	 * @return 返回 product_status。
	 */
	public String getProduct_status() {
		return product_status;
	}
	/**
	 * @param product_status 要设置的 product_status。
	 */
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public Integer getProv_flag() {
		return prov_flag;
	}

	public void setProv_flag(Integer provFlag) {
		prov_flag = provFlag;
	}

	public Integer getSub_product_id() {
		return sub_product_id;
	}

	public void setSub_product_id(Integer subProductId) {
		sub_product_id = subProductId;
	}
	
	/**
	 * @return 返回 df_contract_bh。
	 */
	public String getDf_contract_bh() {
		return df_contract_bh;
	}
	/**
	 * @param df_contract_bh 要设置的 df_contract_bh。
	 */
	public void setDf_contract_bh(String df_contract_bh) {
		this.df_contract_bh = df_contract_bh;
	}
	/**
	 * @return 返回 df_product_id。
	 */
	public Integer getDf_product_id() {
		return df_product_id;
	}
	/**
	 * @param df_product_id 要设置的 df_product_id。
	 */
	public void setDf_product_id(Integer df_product_id) {
		this.df_product_id = df_product_id;
	}
	/**
	 * @return 返回 export_summary。
	 */
	public String getExport_summary() {
		return export_summary;
	}
	/**
	 * @param export_summary 要设置的 export_summary。
	 */
	public void setExport_summary(String export_summary) {
		this.export_summary = export_summary;
	}
	
	
	/**
	 * @return 返回 new_bank_name。
	 */
	public String getNew_bank_name() {
		return new_bank_name;
	}
	/**
	 * @param new_bank_name 要设置的 new_bank_name。
	 */
	public void setNew_bank_name(String new_bank_name) {
		this.new_bank_name = new_bank_name;
	}
	/**
	 * @return 返回 old_bank_name。
	 */
	public String getOld_bank_name() {
		return old_bank_name;
	}
	/**
	 * @param old_bank_name 要设置的 old_bank_name。
	 */
	public void setOld_bank_name(String old_bank_name) {
		this.old_bank_name = old_bank_name;
	}
	
	
	
	/**
	 * @return 返回 modi_man_name。
	 */
	public String getModi_man_name() {
		return modi_man_name;
	}
	/**
	 * @param modi_man_name 要设置的 modi_man_name。
	 */
	public void setModi_man_name(String modi_man_name) {
		this.modi_man_name = modi_man_name;
	}
	/**
	 * @return 返回 new_bonus_flag_name。
	 */
	public String getNew_bonus_flag_name() {
		return new_bonus_flag_name;
	}
	/**
	 * @param new_bonus_flag_name 要设置的 new_bonus_flag_name。
	 */
	public void setNew_bonus_flag_name(String new_bonus_flag_name) {
		this.new_bonus_flag_name = new_bonus_flag_name;
	}
	/**
	 * @return 返回 old_bonus_flag_name。
	 */
	public String getOld_bonus_flag_name() {
		return old_bonus_flag_name;
	}
	/**
	 * @param old_bonus_flag_name 要设置的 old_bonus_flag_name。
	 */
	public void setOld_bonus_flag_name(String old_bonus_flag_name) {
		this.old_bonus_flag_name = old_bonus_flag_name;
	} 
	
	
	/**
	 * @return 返回 tran_times。
	 */
	public Integer getTran_times() {
		return tran_times;
	}
	/**
	 * @param tran_times 要设置的 tran_times。
	 */
	public void setTran_times(Integer tran_times) {
		this.tran_times = tran_times;
	}
	
	
	/**
	 * @return 返回 query_flag。
	 */
	public Integer getQuery_flag() {
		return query_flag;
	}
	/**
	 * @param query_flag 要设置的 query_flag。
	 */
	public void setQuery_flag(Integer query_flag) {
		this.query_flag = query_flag;
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
