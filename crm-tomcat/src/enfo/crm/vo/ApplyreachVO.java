/*
 * 创建日期 2009-12-10
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
public class ApplyreachVO {
	private Integer book_code;
	private Integer serial_no;
	private Integer product_id;
	private String product_code;
	private String product_name;
	private String currency_id;
	private Integer cust_id;//客户内部ID
	private String contract_bh;
	private String contract_sub_bh;
	private String touch_type;//联系方式 user_id=5 用
	private String tocuh_type_name ;//联系方式说明
	private Integer valid_period;//合同期限
	private Integer sq_date;//合同签署日期
	private Integer start_date; //起始日期
	private String end_date;
	private BigDecimal sg_money;//合同申购金额
	private BigDecimal sg_price;//
	private BigDecimal to_money;
	private BigDecimal to_amount;
	private BigDecimal sg_fee;
	private String jk_type;
	private String jk_type_name;
	private Integer jk_status;//缴款标志 1未缴 2已缴
	private Integer jk_date;
	private Integer to_bank_date;
	private String bank_id;//信托利益银行编号（1103）
	private String bank_name;
	private String bank_sub_name;
	private String bank_acct;
	private String gain_acct;
	private Integer input_man;
	private Integer input_time;
	private Integer modi_man;
	private Integer modi_time;
	private Integer check_flag;//1未复核 2已复核
	private Integer check_man;
	private Integer check_time;
	private Integer zj_check_flag;
	private Integer zj_check_man;
	private Integer zj_check_time;
	private String ht_status;
	private Integer link_man;
	private Integer service_man;
	private String summary;
	private Integer city_serial_no;
	private String city_name;
	private Integer terminate_date;
	private Integer original_end_date;//合同原到期日期
	private Integer fee_jk_type;//0无需交 1从本金扣 2另外交
	
	private String cust_name;
	private Integer cntr_serial_no;//contract_表中的serial_no;
	private String bank_acct_type;//银行账号类型
	private Integer bonus_flag;
	private Integer sub_product_id;
	
	private Integer with_bank_flag;
	private String ht_bank_id;
	private String ht_bank_sub_name;
	private Integer with_security_flag;
	private Integer with_private_flag;
	private BigDecimal bonus_rate;
	private Integer prov_flag;
	private String pro_level;
	private Integer channel_id;
	private String channel_type;
	private String channel_memo;
	private String prov_level;
	private Integer flag1;
	private String channel_cooperation;
	private BigDecimal market_trench_money;
	private Integer bzj_flag;
	
	private Integer recommend_man;
	private String bank_province;
	private String bank_city;
	private String recommend_man_name;
	private BigDecimal expect_ror_lower; //预期收益率(起)
	private BigDecimal expect_ror_upper; //预期收益率(止)
	private Integer contact_id;
	private Integer period_unit;
	
	private String ht_cust_name;
	private String ht_cust_tel;
	private String ht_cust_address;
	
	private Integer spot_deal;
	private String property_souce;
	private String other_explain;
	
	private String money_origin;//资产来源
	private String sub_money_origin;//资产来源细分
	private Integer contract_type;
	
	
	/**
	 * @return 返回 other_explain。
	 */
	public String getOther_explain() {
		return other_explain;
	}
	/**
	 * @param other_explain 要设置的 other_explain。
	 */
	public void setOther_explain(String other_explain) {
		this.other_explain = other_explain;
	}
	/**
	 * @return 返回 property_souce。
	 */
	public String getProperty_souce() {
		return property_souce;
	}
	/**
	 * @param property_souce 要设置的 property_souce。
	 */
	public void setProperty_souce(String property_souce) {
		this.property_souce = property_souce;
	}
	/**
	 * @return 返回 ht_cust_address。
	 */
	public String getHt_cust_address() {
		return ht_cust_address;
	}
	/**
	 * @param ht_cust_address 要设置的 ht_cust_address。
	 */
	public void setHt_cust_address(String ht_cust_address) {
		this.ht_cust_address = ht_cust_address;
	}
	/**
	 * @return 返回 ht_cust_name。
	 */
	public String getHt_cust_name() {
		return ht_cust_name;
	}
	/**
	 * @param ht_cust_name 要设置的 ht_cust_name。
	 */
	public void setHt_cust_name(String ht_cust_name) {
		this.ht_cust_name = ht_cust_name;
	}
	/**
	 * @return 返回 ht_cust_tel。
	 */
	public String getHt_cust_tel() {
		return ht_cust_tel;
	}
	/**
	 * @param ht_cust_tel 要设置的 ht_cust_tel。
	 */
	public void setHt_cust_tel(String ht_cust_tel) {
		this.ht_cust_tel = ht_cust_tel;
	}
	/**
	 * @return 返回 contact_id。
	 */
	public Integer getContact_id() {
		return contact_id;
	}
	/**
	 * @param contact_id 要设置的 contact_id。
	 */
	public void setContact_id(Integer contact_id) {
		this.contact_id = contact_id;
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
	 * @return 返回 recommend_man。
	 */
	public Integer getRecommend_man() {
		return recommend_man;
	}
	/**
	 * @param recommend_man 要设置的 recommend_man。
	 */
	public void setRecommend_man(Integer recommend_man) {
		this.recommend_man = recommend_man;
	}
	public Integer getBzj_flag() {
		return bzj_flag;
	}
	public void setBzj_flag(Integer bzjFlag) {
		bzj_flag = bzjFlag;
	}
	public String getChannel_cooperation() {
		return channel_cooperation;
	}
	public void setChannel_cooperation(String channelCooperation) {
		channel_cooperation = channelCooperation;
	}
	public String getProv_level() {
		return prov_level;
	}
	public void setProv_level(String provLevel) {
		prov_level = provLevel;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channelId) {
		channel_id = channelId;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channelType) {
		channel_type = channelType;
	}
	public String getChannel_memo() {
		return channel_memo;
	}
	public void setChannel_memo(String channelMemo) {
		channel_memo = channelMemo;
	}
	/**
	 * @return 返回 pro_level。
	 */
	public String getPro_level() {
		return pro_level;
	}
	/**
	 * @param pro_level 要设置的 pro_level。
	 */
	public void setPro_level(String proLevel) {
		pro_level = proLevel;
	}
	public Integer getProv_flag() {
		return prov_flag;
	}

	public void setProv_flag(Integer provFlag) {
		prov_flag = provFlag;
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
	public String getBank_acct_type() {
		return bank_acct_type;
	}


	public String getHt_bank_id() {
		return ht_bank_id;
	}

	public void setHt_bank_id(String htBankId) {
		ht_bank_id = htBankId;
	}

	public String getHt_bank_sub_name() {
		return ht_bank_sub_name;
	}

	public void setHt_bank_sub_name(String htBankSubName) {
		ht_bank_sub_name = htBankSubName;
	}

	public BigDecimal getBonus_rate() {
		return bonus_rate;
	}

	public void setBonus_rate(BigDecimal bonusRate) {
		bonus_rate = bonusRate;
	}

	/**
	 * @return
	 */
	public String getBank_id() {
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
	public String getBank_sub_name() {
		return bank_sub_name;
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
	public Integer getBook_code() {
		return book_code;
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
	public Integer getCheck_time() {
		return check_time;
	}

	/**
	 * @return
	 */
	public String getCity_name() {
		return city_name;
	}

	/**
	 * @return
	 */
	public Integer getCity_serial_no() {
		return city_serial_no;
	}

	/**
	 * @return
	 */
	public Integer getCntr_serial_no() {
		return cntr_serial_no;
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
	public String getContract_sub_bh() {
		return contract_sub_bh;
	}

	/**
	 * @return
	 */
	public String getCurrency_id() {
		return currency_id;
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
	public String getCust_name() {
		return cust_name;
	}

	/**
	 * @return
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public Integer getFee_jk_type() {
		return fee_jk_type;
	}

	/**
	 * @return
	 */
	public String getGain_acct() {
		return gain_acct;
	}

	/**
	 * @return
	 */
	public String getHt_status() {
		return ht_status;
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
	public Integer getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getJk_date() {
		return jk_date;
	}

	/**
	 * @return
	 */
	public Integer getJk_status() {
		return jk_status;
	}

	/**
	 * @return
	 */
	public String getJk_type() {
		return jk_type;
	}

	/**
	 * @return
	 */
	public String getJk_type_name() {
		return jk_type_name;
	}

	/**
	 * @return
	 */
	public Integer getLink_man() {
		return link_man;
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
	public Integer getOriginal_end_date() {
		return original_end_date;
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
	public Integer getProduct_id() {
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
	public Integer getSerial_no() {
		return serial_no;
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
	public BigDecimal getSg_fee() {
		return sg_fee;
	}

	/**
	 * @return
	 */
	public BigDecimal getSg_money() {
		return sg_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getSg_price() {
		return sg_price;
	}

	/**
	 * @return
	 */
	public Integer getSq_date() {
		return sq_date;
	}

	/**
	 * @return
	 */
	public Integer getStart_date() {
		return start_date;
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
	public Integer getTerminate_date() {
		return terminate_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getTo_amount() {
		return to_amount;
	}

	/**
	 * @return
	 */
	public Integer getTo_bank_date() {
		return to_bank_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getTo_money() {
		return to_money;
	}

	/**
	 * @return
	 */
	public String getTocuh_type_name() {
		return tocuh_type_name;
	}

	/**
	 * @return
	 */
	public String getTouch_type() {
		return touch_type;
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
	public Integer getZj_check_flag() {
		return zj_check_flag;
	}

	/**
	 * @return
	 */
	public Integer getZj_check_man() {
		return zj_check_man;
	}

	/**
	 * @return
	 */
	public Integer getZj_check_time() {
		return zj_check_time;
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
	public void setBank_acct_type(String string) {
		bank_acct_type = string;
	}

	/**
	 * @param string
	 */
	public void setBank_id(String string) {
		bank_id = string;
	}

	/**
	 * @param string
	 */
	public void setBank_name(String string) {
		bank_name = string;
	}

	/**
	 * @param string
	 */
	public void setBank_sub_name(String string) {
		bank_sub_name = string;
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
	public void setBook_code(Integer integer) {
		book_code = integer;
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
	public void setCheck_time(Integer integer) {
		check_time = integer;
	}

	/**
	 * @param string
	 */
	public void setCity_name(String string) {
		city_name = string;
	}

	/**
	 * @param integer
	 */
	public void setCity_serial_no(Integer integer) {
		city_serial_no = integer;
	}

	/**
	 * @param integer
	 */
	public void setCntr_serial_no(Integer integer) {
		cntr_serial_no = integer;
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
	public void setContract_sub_bh(String string) {
		contract_sub_bh = string;
	}

	/**
	 * @param string
	 */
	public void setCurrency_id(String string) {
		currency_id = string;
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
	public void setCust_name(String string) {
		cust_name = string;
	}

	/**
	 * @param string
	 */
	public void setEnd_date(String string) {
		end_date = string;
	}

	/**
	 * @param integer
	 */
	public void setFee_jk_type(Integer integer) {
		fee_jk_type = integer;
	}

	/**
	 * @param string
	 */
	public void setGain_acct(String string) {
		gain_acct = string;
	}

	/**
	 * @param string
	 */
	public void setHt_status(String string) {
		ht_status = string;
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
	public void setInput_time(Integer integer) {
		input_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setJk_date(Integer integer) {
		jk_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setJk_status(Integer integer) {
		jk_status = integer;
	}

	/**
	 * @param string
	 */
	public void setJk_type(String string) {
		jk_type = string;
	}

	/**
	 * @param string
	 */
	public void setJk_type_name(String string) {
		jk_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setLink_man(Integer integer) {
		link_man = integer;
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
	 * @param integer
	 */
	public void setOriginal_end_date(Integer integer) {
		original_end_date = integer;
	}

	/**
	 * @param string
	 */
	public void setProduct_code(String string) {
		product_code = string;
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
	public void setProduct_name(String string) {
		product_name = string;
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
	public void setService_man(Integer integer) {
		service_man = integer;
	}

	/**
	 * @param decimal
	 */
	public void setSg_fee(BigDecimal decimal) {
		sg_fee = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSg_money(BigDecimal decimal) {
		sg_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSg_price(BigDecimal decimal) {
		sg_price = decimal;
	}

	/**
	 * @param integer
	 */
	public void setSq_date(Integer integer) {
		sq_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(Integer integer) {
		start_date = integer;
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
	public void setTerminate_date(Integer integer) {
		terminate_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTo_amount(BigDecimal decimal) {
		to_amount = decimal;
	}

	/**
	 * @param integer
	 */
	public void setTo_bank_date(Integer integer) {
		to_bank_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTo_money(BigDecimal decimal) {
		to_money = decimal;
	}

	/**
	 * @param string
	 */
	public void setTocuh_type_name(String string) {
		tocuh_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setTouch_type(String string) {
		touch_type = string;
	}

	/**
	 * @param integer
	 */
	public void setValid_period(Integer integer) {
		valid_period = integer;
	}

	/**
	 * @param integer
	 */
	public void setZj_check_flag(Integer integer) {
		zj_check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setZj_check_man(Integer integer) {
		zj_check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setZj_check_time(Integer integer) {
		zj_check_time = integer;
	}

	/**
	 * @return
	 */
	public Integer getSub_product_id() {
		return sub_product_id;
	}

	/**
	 * @param integer
	 */
	public void setSub_product_id(Integer integer) {
		sub_product_id = integer;
	}
	public Integer getWith_bank_flag() {
		return with_bank_flag;
	}
	public void setWith_bank_flag(Integer withBankFlag) {
		with_bank_flag = withBankFlag;
	}
	public Integer getWith_security_flag() {
		return with_security_flag;
	}
	public void setWith_security_flag(Integer withSecurityFlag) {
		with_security_flag = withSecurityFlag;
	}
	public Integer getWith_private_flag() {
		return with_private_flag;
	}
	public void setWith_private_flag(Integer withPrivateFlag) {
		with_private_flag = withPrivateFlag;
	}

	public Integer getFlag1() {
		return flag1;
	}
	public void setFlag1(Integer integer) {
		flag1 = integer;
	}
	/**
	 * @return 返回 market_trench_money。
	 */
	public BigDecimal getMarket_trench_money() {
		return market_trench_money;
	}
	/**
	 * @param market_trench_money 要设置的 market_trench_money。
	 */
	public void setMarket_trench_money(BigDecimal market_trench_money) {
		this.market_trench_money = market_trench_money;
	}
	
	
	/**
	 * @return 返回 recommend_man_name。
	 */
	public String getRecommend_man_name() {
		return recommend_man_name;
	}
	/**
	 * @param recommend_man_name 要设置的 recommend_man_name。
	 */
	public void setRecommend_man_name(String recommend_man_name) {
		this.recommend_man_name = recommend_man_name;
	}
	/**
	 * @return 返回 expect_ror_lower。
	 */
	public BigDecimal getExpect_ror_lower() {
		return expect_ror_lower;
	}
	/**
	 * @param expect_ror_lower 要设置的 expect_ror_lower。
	 */
	public void setExpect_ror_lower(BigDecimal expect_ror_lower) {
		this.expect_ror_lower = expect_ror_lower;
	}
	/**
	 * @return 返回 expect_ror_upper。
	 */
	public BigDecimal getExpect_ror_upper() {
		return expect_ror_upper;
	}
	/**
	 * @param expect_ror_upper 要设置的 expect_ror_upper。
	 */
	public void setExpect_ror_upper(BigDecimal expect_ror_upper) {
		this.expect_ror_upper = expect_ror_upper;
	}
	
	
	/**
	 * @return 返回 period_unit。
	 */
	public Integer getPeriod_unit() {
		return period_unit;
	}
	/**
	 * @param period_unit 要设置的 period_unit。
	 */
	public void setPeriod_unit(Integer period_unit) {
		this.period_unit = period_unit;
	}
	/**
	 * @return 返回 spot_deal。
	 */
	public Integer getSpot_deal() {
		return spot_deal;
	}
	/**
	 * @param spot_deal 要设置的 spot_deal。
	 */
	public void setSpot_deal(Integer spot_deal) {
		this.spot_deal = spot_deal;
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
	public Integer getContract_type() {
		return contract_type;
	}
	public void setContract_type(Integer contract_type) {
		this.contract_type = contract_type;
	}
}
