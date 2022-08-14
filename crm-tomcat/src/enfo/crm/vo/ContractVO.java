
/*
 * 创建日期 2009-11-25
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
public class ContractVO {
	private java.lang.Integer book_code;
		private java.lang.Integer serial_no;
		private java.lang.String product_code;
		private java.lang.String product_name;
		private java.lang.String currency_id;
		private java.lang.Integer cust_id;
		private java.lang.String pre_code;
		private java.lang.String contract_bh;
		private java.lang.Integer link_man;
		private java.lang.Integer valid_period;
		private java.lang.Integer qs_date;
		private java.lang.Integer start_date;
		private java.lang.Integer end_date;
		private java.math.BigDecimal rg_money;
		private java.math.BigDecimal to_money;
		private java.math.BigDecimal to_amount;
		private java.lang.String jk_type;
		private java.lang.String jk_type_name;
		private java.lang.Integer jk_status;
		private java.lang.Integer jk_date;
		private java.lang.String bank_id;
		private java.lang.String bank_name;
		private java.lang.String bank_sub_name;
		private java.lang.String bank_acct;
		private java.lang.String gain_acct;
		private java.lang.Integer input_man;
		private java.sql.Timestamp input_time;
	 
		private java.lang.Integer zj_check_flag;
		private java.lang.Integer zj_check_man;
	
		private java.lang.Integer check_flag;
	
		private java.lang.String trans_flag;
		private java.lang.String trans_flag_name;
		private java.lang.Integer last_trans_date;
		private java.lang.String ht_status;
		private java.lang.String ht_status_name;
		private java.lang.Integer service_man;
		private java.lang.String summary;

		private java.lang.Integer product_id;
		private java.lang.String cust_no;
		private java.lang.String cust_name;
		private java.lang.String card_id;
		private java.lang.String currency_name;
		private java.lang.String check_flag_name;
		private java.math.BigDecimal pre_money;
		private java.lang.Integer temp_end_date;
		private java.math.BigDecimal temp_rg_money;
	
		private java.lang.Integer only_thisproduct = new Integer(0);   //参数暂时没有使用
		private java.lang.Integer cust_type = new Integer(0);
	
		//
		private java.lang.String benifitor_name;
		private java.lang.String  benifitor_card_id;
		private java.lang.String  str_qs_date;
		private java.lang.String  str_jk_date;
		private java.lang.String  str_valid_period;
		private java.lang.String acct_user;
	
	
		private java.lang.Integer check_man;
		private java.sql.Timestamp check_time;
		private java.lang.Integer pre_flag;   //预约标志
		private java.lang.Integer contract_id;
		private java.lang.Integer list_id;
	
		private int ben_rec_no;
	
		//20050114 By CaiYuan
		private String prov_level ;  //受益级别
		 //	20050514 By 　谭鸿
		private String entity_name ;  //财产名称
		private String entity_type ;  //财产类别
		private String entity_type_name ;  //财产名称
		private String contract_sub_bh ;  //财产实际合同编号
		private java.math.BigDecimal entity_price ;  //财产价格
		private java.lang.Integer pz_flag;//财产信托合同生成凭证标志
		private java.lang.Integer city_serialno; 
		private java.lang.Integer terminate_date;//终止日期
		private String city_name ; 
		private java.math.BigDecimal max_rg_money ;
		private java.math.BigDecimal min_rg_money ; 
		private Integer df_cust_id;
		private int self_ben_flag;
	
	
		private String touch_type;
		private String touch_type_name;
	
		private int fee_jk_type;//费用缴款方式：1从本金扣，2另外交,0不交
		private String bank_acct_type;//银行账号类型（9920）
	
		private BigDecimal rg_money2;
		private BigDecimal rg_fee_rate;
		private BigDecimal rg_fee_money;
		private BigDecimal jk_total_money;
	
		//2009-08-14
		private String wtr_cust_type_name;
		private String wtr_contact_methed;
		private String wtr_address;
		private String wtr__post;
		private String wtr_card_type_name;
		private String wtr_lpr;
		private String wtr_telephone;
		private String wtr_handset_number;
		private String wtr_Email;
	
		private String benifitor_cust_type_name;
		private String benifitor_contact_methed;
		private String benifitor_address;
		private String benifitor_post;
		private String benifitor_card_type_name;
		private String benifitor_lpr;
		private String benifitor_telephone ;
		private String benifitor_handset_number ;
		private String benifitor_Email ;
	
		private String property_type_name ;
		private String property_name;
	
		private Integer bonus_flag;//收益分配方式
		private Integer contract_type;
		private Integer contract_zjflag;
		
		private Integer cust_group_id;
		private Integer classdetail_id;
		
		private BigDecimal max_rg_money2;
		private BigDecimal min_rg_money2;
		
		private Integer sub_product_id;
		private Integer with_bank_flag;
		private String ht_bank_id;
		private String ht_bank_sub_name;
		private Integer channel_id;
		private String channel_type;
		private String channel_memo;
		//20111213
		private String channel_cooperation;
		
		private Integer with_secuity_flag;
        private Integer with_private_flag;
        private BigDecimal bonus_rate;
        private Integer prov_flag;     
        private Integer managerID;
        private Integer zjFlag;
        private Integer intrustFlag1;
        private BigDecimal market_trench_money;
        private Integer bzj_flag;
        
        private Integer recommend_man;
        private String bank_province;
        private String bank_city;
        private BigDecimal expect_ror_lower;
        private BigDecimal expect_ror_upper;
        private String recommend_man_name;//外部推荐人
        //20121024
        private String is_ykgl;//用款方关联标志
        private String xthtyjsyl;//信托合同预计收益率 
        private Integer contact_id;
        
        private Integer cell_flag;
        private Integer cell_id;
        private Integer depart_id;
        private Integer sq_date_start;
        private Integer sq_date_end;
        private String admin_manager;
        
        private String jg_wtrlx2;
        private Integer period_unit;
        
        private String ht_cust_name;
        private String ht_cust_tel;
        private String ht_cust_address;
        private Integer team_id;
        
        private Integer spot_deal;
        private String money_origin = "";
        private String sub_money_origin = "";
        private String property_source = "";
        private String other_explain = "";
        private Integer sync_benifitor;
        
        private String cust_ids;
        private Integer crm_pre_no; //CRM的预约记录号
        
        
		/**
		 * @return 返回 crm_pre_no。
		 */
		public Integer getCrm_pre_no() {
			return crm_pre_no;
		}
		/**
		 * @param crm_pre_no 要设置的 crm_pre_no。
		 */
		public void setCrm_pre_no(Integer crm_pre_no) {
			this.crm_pre_no = crm_pre_no;
		}
		/**
		 * @return 返回 cust_ids。
		 */
		public String getCust_ids() {
			return cust_ids;
		}
		/**
		 * @param cust_ids 要设置的 cust_ids。
		 */
		public void setCust_ids(String cust_ids) {
			this.cust_ids = cust_ids;
		}
		/**
		 * @return 返回 sync_benifitor。
		 */
		public Integer getSync_benifitor() {
			return sync_benifitor;
		}
		/**
		 * @param sync_benifitor 要设置的 sync_benifitor。
		 */
		public void setSync_benifitor(Integer sync_benifitor) {
			this.sync_benifitor = sync_benifitor;
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
		/**
		 * @return 返回 zjFlag。
		 */
		public Integer getZjFlag() {
			return zjFlag;
		}
		/**
		 * @param zjFlag 要设置的 zjFlag。
		 */
		public void setZjFlag(Integer zjFlag) {
			this.zjFlag = zjFlag;
		}
		/**
		 * @return 返回 managerID。
		 */
		public Integer getManagerID() {
			return managerID;
		}
		/**
		 * @param managerID 要设置的 managerID。
		 */
		public void setManagerID(Integer managerID) {
			this.managerID = managerID;
		}
		public Integer getWith_secuity_flag() {
			return with_secuity_flag;
		}

		public void setWith_secuity_flag(Integer withSecuityFlag) {
			with_secuity_flag = withSecuityFlag;
		}

		public Integer getWith_private_flag() {
			return with_private_flag;
		}

		public void setWith_private_flag(Integer withPrivateFlag) {
			with_private_flag = withPrivateFlag;
		}

		public BigDecimal getBonus_rate() {
			return bonus_rate;
		}

		public void setBonus_rate(BigDecimal bonusRate) {
			bonus_rate = bonusRate;
		}

		public Integer getProv_flag() {
			return prov_flag;
		}

		public void setProv_flag(Integer provFlag) {
			prov_flag = provFlag;
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

		public Integer getCust_group_id() {
			return cust_group_id;
		}

		public void setCust_group_id(Integer cust_group_id) {
			this.cust_group_id = cust_group_id;
		}

		public Integer getSub_product_id() {
			return sub_product_id;
		}

		public void setSub_product_id(Integer sub_product_id) {
			this.sub_product_id = sub_product_id;
		}

		public Integer getWith_bank_flag() {
			return with_bank_flag;
		}

		public void setWith_bank_flag(Integer with_bank_flag) {
			this.with_bank_flag = with_bank_flag;
		}

		public String getHt_bank_id() {
			return ht_bank_id;
		}

		public void setHt_bank_id(String ht_bank_id) {
			this.ht_bank_id = ht_bank_id;
		}

		public String getHt_bank_sub_name() {
			return ht_bank_sub_name;
		}

		public void setHt_bank_sub_name(String ht_bank_sub_name) {
			this.ht_bank_sub_name = ht_bank_sub_name;
		}

		public Integer getChannel_id() {
			return channel_id;
		}

		public void setChannel_id(Integer channel_id) {
			this.channel_id = channel_id;
		}

		public Integer getClassdetail_id() {
			return classdetail_id;
		}

		public void setClassdetail_id(Integer classdetail_id) {
			this.classdetail_id = classdetail_id;
		}

		public Integer getContract_zjflag() {
			return contract_zjflag;
		}

		public void setContract_zjflag(Integer contract_zjflag) {
			this.contract_zjflag = contract_zjflag;
		}

		/**
		 * @return
		 */
		public java.lang.String getAcct_user() {
			return acct_user;
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
		public int getBen_rec_no() {
			return ben_rec_no;
		}

		/**
		 * @return
		 */
		public String getBenifitor_address() {
			return benifitor_address;
		}

		/**
		 * @return
		 */
		public java.lang.String getBenifitor_card_id() {
			return benifitor_card_id;
		}

		/**
		 * @return
		 */
		public String getBenifitor_card_type_name() {
			return benifitor_card_type_name;
		}

		/**
		 * @return
		 */
		public String getBenifitor_contact_methed() {
			return benifitor_contact_methed;
		}

		/**
		 * @return
		 */
		public String getBenifitor_cust_type_name() {
			return benifitor_cust_type_name;
		}

		/**
		 * @return
		 */
		public String getBenifitor_Email() {
			return benifitor_Email;
		}

		/**
		 * @return
		 */
		public String getBenifitor_handset_number() {
			return benifitor_handset_number;
		}

		/**
		 * @return
		 */
		public String getBenifitor_lpr() {
			return benifitor_lpr;
		}

		/**
		 * @return
		 */
		public java.lang.String getBenifitor_name() {
			return benifitor_name;
		}

		/**
		 * @return
		 */
		public String getBenifitor_post() {
			return benifitor_post;
		}

		/**
		 * @return
		 */
		public String getBenifitor_telephone() {
			return benifitor_telephone;
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
		public java.lang.String getCard_id() {
			return card_id;
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
		public java.lang.String getCheck_flag_name() {
			return check_flag_name;
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
		public java.sql.Timestamp getCheck_time() {
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
		public java.lang.Integer getCity_serialno() {
			return city_serialno;
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
		public String getContract_sub_bh() {
			return contract_sub_bh;
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
		public java.lang.String getCurrency_name() {
			return currency_name;
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
		public java.lang.String getCust_no() {
			return cust_no;
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
		public Integer getDf_cust_id() {
			return df_cust_id;
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
		public String getEntity_name() {
			return entity_name;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getEntity_price() {
			return entity_price;
		}

		/**
		 * @return
		 */
		public String getEntity_type() {
			return entity_type;
		}

		/**
		 * @return
		 */
		public String getEntity_type_name() {
			return entity_type_name;
		}

		/**
		 * @return
		 */
		public int getFee_jk_type() {
			return fee_jk_type;
		}

		/**
		 * @return
		 */
		public java.lang.String getGain_acct() {
			return gain_acct;
		}

		/**
		 * @return
		 */
		public java.lang.String getHt_status() {
			return ht_status;
		}

		/**
		 * @return
		 */
		public java.lang.String getHt_status_name() {
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
		public java.lang.Integer getJk_date() {
			return jk_date;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getJk_status() {
			return jk_status;
		}

		/**
		 * @return
		 */
		public BigDecimal getJk_total_money() {
			return jk_total_money;
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
		public java.lang.Integer getLast_trans_date() {
			return last_trans_date;
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
		public java.lang.Integer getList_id() {
			return list_id;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getMax_rg_money() {
			return max_rg_money;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getMin_rg_money() {
			return min_rg_money;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getOnly_thisproduct() {
			return only_thisproduct;
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
		public java.lang.Integer getPre_flag() {
			return pre_flag;
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
		public java.lang.String getProduct_code() {
			return product_code;
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
		public String getProperty_name() {
			return property_name;
		}

		/**
		 * @return
		 */
		public String getProperty_type_name() {
			return property_type_name;
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
		public java.lang.Integer getPz_flag() {
			return pz_flag;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getQs_date() {
			return qs_date;
		}

		/**
		 * @return
		 */
		public BigDecimal getRg_fee_money() {
			return rg_fee_money;
		}

		/**
		 * @return
		 */
		public BigDecimal getRg_fee_rate() {
			return rg_fee_rate;
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
		public BigDecimal getRg_money2() {
			return rg_money2;
		}

		/**
		 * @return
		 */
		public int getSelf_ben_flag() {
			return self_ben_flag;
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
		public java.lang.Integer getService_man() {
			return service_man;
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
		public java.lang.String getStr_jk_date() {
			return str_jk_date;
		}

		/**
		 * @return
		 */
		public java.lang.String getStr_qs_date() {
			return str_qs_date;
		}

		/**
		 * @return
		 */
		public java.lang.String getStr_valid_period() {
			return str_valid_period;
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
		public java.lang.Integer getTemp_end_date() {
			return temp_end_date;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getTemp_rg_money() {
			return temp_rg_money;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getTerminate_date() {
			return terminate_date;
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
		public java.math.BigDecimal getTo_money() {
			return to_money;
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
		public String getTouch_type_name() {
			return touch_type_name;
		}

		/**
		 * @return
		 */
		public java.lang.String getTrans_flag() {
			return trans_flag;
		}

		/**
		 * @return
		 */
		public java.lang.String getTrans_flag_name() {
			return trans_flag_name;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getValid_period() {
			return valid_period;
		}

		/**
		 * @return
		 */
		public String getWtr__post() {
			return wtr__post;
		}

		/**
		 * @return
		 */
		public String getWtr_address() {
			return wtr_address;
		}

		/**
		 * @return
		 */
		public String getWtr_card_type_name() {
			return wtr_card_type_name;
		}

		/**
		 * @return
		 */
		public String getWtr_contact_methed() {
			return wtr_contact_methed;
		}

		/**
		 * @return
		 */
		public String getWtr_cust_type_name() {
			return wtr_cust_type_name;
		}

		/**
		 * @return
		 */
		public String getWtr_Email() {
			return wtr_Email;
		}

		/**
		 * @return
		 */
		public String getWtr_handset_number() {
			return wtr_handset_number;
		}

		/**
		 * @return
		 */
		public String getWtr_lpr() {
			return wtr_lpr;
		}

		/**
		 * @return
		 */
		public String getWtr_telephone() {
			return wtr_telephone;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getZj_check_flag() {
			return zj_check_flag;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getZj_check_man() {
			return zj_check_man;
		}

		/**
		 * @param string
		 */
		public void setAcct_user(java.lang.String string) {
			acct_user = string;
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
		 * @param i
		 */
		public void setBen_rec_no(int i) {
			ben_rec_no = i;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_address(String string) {
			benifitor_address = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_card_id(java.lang.String string) {
			benifitor_card_id = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_card_type_name(String string) {
			benifitor_card_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_contact_methed(String string) {
			benifitor_contact_methed = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_cust_type_name(String string) {
			benifitor_cust_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_Email(String string) {
			benifitor_Email = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_handset_number(String string) {
			benifitor_handset_number = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_lpr(String string) {
			benifitor_lpr = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_name(java.lang.String string) {
			benifitor_name = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_post(String string) {
			benifitor_post = string;
		}

		/**
		 * @param string
		 */
		public void setBenifitor_telephone(String string) {
			benifitor_telephone = string;
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
		 * @param string
		 */
		public void setCard_id(java.lang.String string) {
			card_id = string;
		}

		/**
		 * @param integer
		 */
		public void setCheck_flag(java.lang.Integer integer) {
			check_flag = integer;
		}

		/**
		 * @param string
		 */
		public void setCheck_flag_name(java.lang.String string) {
			check_flag_name = string;
		}

		/**
		 * @param integer
		 */
		public void setCheck_man(java.lang.Integer integer) {
			check_man = integer;
		}

		/**
		 * @param timestamp
		 */
		public void setCheck_time(java.sql.Timestamp timestamp) {
			check_time = timestamp;
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
		public void setCity_serialno(java.lang.Integer integer) {
			city_serialno = integer;
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
		public void setContract_sub_bh(String string) {
			contract_sub_bh = string;
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
		public void setCurrency_name(java.lang.String string) {
			currency_name = string;
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
		public void setCust_no(java.lang.String string) {
			cust_no = string;
		}

		/**
		 * @param integer
		 */
		public void setCust_type(java.lang.Integer integer) {
			cust_type = integer;
		}

		/**
		 * @param integer
		 */
		public void setDf_cust_id(Integer integer) {
			df_cust_id = integer;
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
		public void setEntity_name(String string) {
			entity_name = string;
		}

		/**
		 * @param decimal
		 */
		public void setEntity_price(java.math.BigDecimal decimal) {
			entity_price = decimal;
		}

		/**
		 * @param string
		 */
		public void setEntity_type(String string) {
			entity_type = string;
		}

		/**
		 * @param string
		 */
		public void setEntity_type_name(String string) {
			entity_type_name = string;
		}

		/**
		 * @param i
		 */
		public void setFee_jk_type(int i) {
			fee_jk_type = i;
		}

		/**
		 * @param string
		 */
		public void setGain_acct(java.lang.String string) {
			gain_acct = string;
		}

		/**
		 * @param string
		 */
		public void setHt_status(java.lang.String string) {
			ht_status = string;
		}

		/**
		 * @param string
		 */
		public void setHt_status_name(java.lang.String string) {
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
		public void setJk_date(java.lang.Integer integer) {
			jk_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setJk_status(java.lang.Integer integer) {
			jk_status = integer;
		}

		/**
		 * @param decimal
		 */
		public void setJk_total_money(BigDecimal decimal) {
			jk_total_money = decimal;
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
		public void setLast_trans_date(java.lang.Integer integer) {
			last_trans_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setLink_man(java.lang.Integer integer) {
			link_man = integer;
		}

		/**
		 * @param integer
		 */
		public void setList_id(java.lang.Integer integer) {
			list_id = integer;
		}

		/**
		 * @param decimal
		 */
		public void setMax_rg_money(java.math.BigDecimal decimal) {
			max_rg_money = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setMin_rg_money(java.math.BigDecimal decimal) {
			min_rg_money = decimal;
		}

		/**
		 * @param integer
		 */
		public void setOnly_thisproduct(java.lang.Integer integer) {
			only_thisproduct = integer;
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
		public void setPre_flag(java.lang.Integer integer) {
			pre_flag = integer;
		}

		/**
		 * @param decimal
		 */
		public void setPre_money(java.math.BigDecimal decimal) {
			pre_money = decimal;
		}

		/**
		 * @param string
		 */
		public void setProduct_code(java.lang.String string) {
			product_code = string;
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
		public void setProperty_name(String string) {
			property_name = string;
		}

		/**
		 * @param string
		 */
		public void setProperty_type_name(String string) {
			property_type_name = string;
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
		public void setPz_flag(java.lang.Integer integer) {
			pz_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setQs_date(java.lang.Integer integer) {
			qs_date = integer;
		}

		/**
		 * @param decimal
		 */
		public void setRg_fee_money(BigDecimal decimal) {
			rg_fee_money = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setRg_fee_rate(BigDecimal decimal) {
			rg_fee_rate = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setRg_money(java.math.BigDecimal decimal) {
			rg_money = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setRg_money2(BigDecimal decimal) {
			rg_money2 = decimal;
		}

		/**
		 * @param i
		 */
		public void setSelf_ben_flag(int i) {
			self_ben_flag = i;
		}

		/**
		 * @param integer
		 */
		public void setSerial_no(java.lang.Integer integer) {
			serial_no = integer;
		}

		/**
		 * @param integer
		 */
		public void setService_man(java.lang.Integer integer) {
			service_man = integer;
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
		public void setStr_jk_date(java.lang.String string) {
			str_jk_date = string;
		}

		/**
		 * @param string
		 */
		public void setStr_qs_date(java.lang.String string) {
			str_qs_date = string;
		}

		/**
		 * @param string
		 */
		public void setStr_valid_period(java.lang.String string) {
			str_valid_period = string;
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
		public void setTemp_end_date(java.lang.Integer integer) {
			temp_end_date = integer;
		}

		/**
		 * @param decimal
		 */
		public void setTemp_rg_money(java.math.BigDecimal decimal) {
			temp_rg_money = decimal;
		}

		/**
		 * @param integer
		 */
		public void setTerminate_date(java.lang.Integer integer) {
			terminate_date = integer;
		}

		/**
		 * @param decimal
		 */
		public void setTo_amount(java.math.BigDecimal decimal) {
			to_amount = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setTo_money(java.math.BigDecimal decimal) {
			to_money = decimal;
		}

		/**
		 * @param string
		 */
		public void setTouch_type(String string) {
			touch_type = string;
		}

		/**
		 * @param string
		 */
		public void setTouch_type_name(String string) {
			touch_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setTrans_flag(java.lang.String string) {
			trans_flag = string;
		}

		/**
		 * @param string
		 */
		public void setTrans_flag_name(java.lang.String string) {
			trans_flag_name = string;
		}

		/**
		 * @param integer
		 */
		public void setValid_period(java.lang.Integer integer) {
			valid_period = integer;
		}

		/**
		 * @param string
		 */
		public void setWtr__post(String string) {
			wtr__post = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_address(String string) {
			wtr_address = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_card_type_name(String string) {
			wtr_card_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_contact_methed(String string) {
			wtr_contact_methed = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_cust_type_name(String string) {
			wtr_cust_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_Email(String string) {
			wtr_Email = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_handset_number(String string) {
			wtr_handset_number = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_lpr(String string) {
			wtr_lpr = string;
		}

		/**
		 * @param string
		 */
		public void setWtr_telephone(String string) {
			wtr_telephone = string;
		}

		/**
		 * @param integer
		 */
		public void setZj_check_flag(java.lang.Integer integer) {
			zj_check_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setZj_check_man(java.lang.Integer integer) {
			zj_check_man = integer;
		}

		/**
		 * @return
		 */
		public Integer getContract_type() {
			return contract_type;
		}

		/**
		 * @param integer
		 */
		public void setContract_type(Integer integer) {
			contract_type = integer;
		}

		public BigDecimal getMax_rg_money2() {
			return max_rg_money2;
		}

		public void setMax_rg_money2(BigDecimal max_rg_money2) {
			this.max_rg_money2 = max_rg_money2;
		}

		public BigDecimal getMin_rg_money2() {
			return min_rg_money2;
		}

		public void setMin_rg_money2(BigDecimal min_rg_money2) {
			this.min_rg_money2 = min_rg_money2;
		}

		/**
		 * @return 返回 channel_cooperation。
		 */
		public String getChannel_cooperation() {
			return channel_cooperation;
		}
		/**
		 * @param channel_cooperation 要设置的 channel_cooperation。
		 */
		public void setChannel_cooperation(String channel_cooperation) {
			this.channel_cooperation = channel_cooperation;
		}
		/**
		 * @return 返回 intrustFlag1。
		 */
		public Integer getIntrustFlag1() {
			return intrustFlag1;
		}
		/**
		 * @param intrustFlag1 要设置的 intrustFlag1。
		 */
		public void setIntrustFlag1(Integer intrustFlag1) {
			this.intrustFlag1 = intrustFlag1;
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
		 * @return 返回 is_ykgl。
		 */
		public String getIs_ykgl() {
			return is_ykgl;
		}
		/**
		 * @param is_ykgl 要设置的 is_ykgl。
		 */
		public void setIs_ykgl(String is_ykgl) {
			this.is_ykgl = is_ykgl;
		}
		/**
		 * @return 返回 xthtyjsyl。
		 */
		public String getXthtyjsyl() {
			return xthtyjsyl;
		}
		/**
		 * @param xthtyjsyl 要设置的 xthtyjsyl。
		 */
		public void setXthtyjsyl(String xthtyjsyl) {
			this.xthtyjsyl = xthtyjsyl;
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
		 * @return 返回 cell_flag。
		 */
		public Integer getCell_flag() {
			return cell_flag;
		}
		/**
		 * @param cell_flag 要设置的 cell_flag。
		 */
		public void setCell_flag(Integer cell_flag) {
			this.cell_flag = cell_flag;
		}
		/**
		 * @return 返回 cell_id。
		 */
		public Integer getCell_id() {
			return cell_id;
		}
		/**
		 * @param cell_id 要设置的 cell_id。
		 */
		public void setCell_id(Integer cell_id) {
			this.cell_id = cell_id;
		}
		/**
		 * @return 返回 depart_id。
		 */
		public Integer getDepart_id() {
			return depart_id;
		}
		/**
		 * @param depart_id 要设置的 depart_id。
		 */
		public void setDepart_id(Integer depart_id) {
			this.depart_id = depart_id;
		}
		/**
		 * @return 返回 sq_date_end。
		 */
		public Integer getSq_date_end() {
			return sq_date_end;
		}
		/**
		 * @param sq_date_end 要设置的 sq_date_end。
		 */
		public void setSq_date_end(Integer sq_date_end) {
			this.sq_date_end = sq_date_end;
		}
		/**
		 * @return 返回 sq_date_start。
		 */
		public Integer getSq_date_start() {
			return sq_date_start;
		}
		/**
		 * @param sq_date_start 要设置的 sq_date_start。
		 */
		public void setSq_date_start(Integer sq_date_start) {
			this.sq_date_start = sq_date_start;
		}
        
        
		/**
		 * @return 返回 admin_manager。
		 */
		public String getAdmin_manager() {
			return admin_manager;
		}
		/**
		 * @param admin_manager 要设置的 admin_manager。
		 */
		public void setAdmin_manager(String admin_manager) {
			this.admin_manager = admin_manager;
		}
        
        
		/**
		 * @return 返回 jg_wtrlx2。
		 */
		public String getJg_wtrlx2() {
			return jg_wtrlx2;
		}
		/**
		 * @param jg_wtrlx2 要设置的 jg_wtrlx2。
		 */
		public void setJg_wtrlx2(String jg_wtrlx2) {
			this.jg_wtrlx2 = jg_wtrlx2;
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
		 * @return 返回 property_source。
		 */
		public String getProperty_source() {
			return property_source;
		}
		/**
		 * @param property_source 要设置的 property_source。
		 */
		public void setProperty_source(String property_source) {
			this.property_source = property_source;
		}
}
