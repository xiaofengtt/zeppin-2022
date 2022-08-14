/*
 * 创建日期 2009-12-29
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * 收益分配明细VO
 * @author dingyj
 * @since 2009-12-29
 * @version 1.0fg
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class DeployVO {

	private java.lang.Integer serial_no;
		private java.lang.Integer book_code;
		private java.lang.Integer cust_id;
		private java.lang.String sy_type;
		private java.lang.String sy_type_name;
		private java.math.BigDecimal sy_money;
		private java.math.BigDecimal sy_rate;

		private java.math.BigDecimal bond1;
		private java.math.BigDecimal bond2;
		private java.math.BigDecimal gain1;
		private java.math.BigDecimal gain2;

		private java.math.BigDecimal to_money2;
		private java.math.BigDecimal total_money;

		private java.lang.Integer sy_date;
		private java.lang.String jk_type;
		private java.lang.String jk_type_name;
		private java.lang.Integer fp_flag;
		private java.lang.String contract_bh;
		private java.lang.String contract_sub_bh;

		private Integer product_id;
		private Integer input_man;
		private String cust_name;
		private String card_id;

		private String bank_id;
		private String bank_name;
		private String bank_acct;
		private String ben_status_name;
		////////yingxj20041029 增加
		private java.math.BigDecimal rate = new java.math.BigDecimal(0); //到期利率；
		private Integer bond_date; ///到期利息计算日期
		private Integer check_flag; //	Int			N	1	审核标志 1 未审核 2 已审核
		private Integer end_date; //结清日期
		private Integer begin_date;
		private Integer bond_days; //结清日期
		////////////////////////
		private java.math.BigDecimal tax_rate = new java.math.BigDecimal(0);
		//利息税率；
		private java.math.BigDecimal bond_tax = new java.math.BigDecimal(0); //扣税；
		private java.math.BigDecimal sy_amount = new java.math.BigDecimal(0); //本金；
		/////////////yingxj 20050223
		private java.lang.String prov_level;
		private java.lang.String prov_level_name; /////收益级别

		private String product_code;
		private String product_name;
		private String bank_sub_name;
		/////////////20050510谭鸿添加//////////
		private Integer list_id;
		private String cust_no;
		private String sub_code;
		///////////////end//////////////

		//2005-8-1 陶然 begin
		private String cust_acct_name;
		//end

		private Integer startdate;
		private Integer enddate;

		//20050926
		private Integer pz_flag;
		private Integer is_transferential;

		private java.lang.Integer fp_date; //兑付日期

		private String group_id;
		private String group_id_out;
		private Integer post_flag;

		//20070315 by wangc	
		private Integer qx_date;
		private Integer fp_type;
		private java.math.BigDecimal amount = new java.math.BigDecimal(0);
	
		private Integer bonus_flag;
        
        private Integer sub_product_id;
        private Integer link_man;

        private Integer prov_flag;
        /**
		 * @return
		 */
		public java.math.BigDecimal getAmount() {
			return amount;
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
		public Integer getBegin_date() {
			return begin_date;
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
		public Integer getBond_date() {
			return bond_date;
		}

		/**
		 * @return
		 */
		public Integer getBond_days() {
			return bond_days;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getBond_tax() {
			return bond_tax;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getBond1() {
			return bond1;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getBond2() {
			return bond2;
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
		public String getCard_id() {
			return card_id;
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
		public Integer getEnd_date() {
			return end_date;
		}

		/**
		 * @return
		 */
		public Integer getEnddate() {
			return enddate;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getFp_date() {
			return fp_date;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getFp_flag() {
			return fp_flag;
		}

		/**
		 * @return
		 */
		public Integer getFp_type() {
			return fp_type;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getGain1() {
			return gain1;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getGain2() {
			return gain2;
		}

		/**
		 * @return
		 */
		public String getGroup_id() {
			return group_id;
		}

		/**
		 * @return
		 */
		public String getGroup_id_out() {
			return group_id_out;
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
		public Integer getList_id() {
			return list_id;
		}

		/**
		 * @return
		 */
		public Integer getPost_flag() {
			return post_flag;
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
		public Integer getPz_flag() {
			return pz_flag;
		}

		/**
		 * @return
		 */
		public Integer getQx_date() {
			return qx_date;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getRate() {
			return rate;
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
		public Integer getStartdate() {
			return startdate;
		}

		/**
		 * @return
		 */
		public String getSub_code() {
			return sub_code;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSy_amount() {
			return sy_amount;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getSy_date() {
			return sy_date;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSy_money() {
			return sy_money;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSy_rate() {
			return sy_rate;
		}

		/**
		 * @return
		 */
		public java.lang.String getSy_type() {
			return sy_type;
		}

		/**
		 * @return
		 */
		public java.lang.String getSy_type_name() {
			return sy_type_name;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getTax_rate() {
			return tax_rate;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getTo_money2() {
			return to_money2;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getTotal_money() {
			return total_money;
		}

		/**
		 * @param decimal
		 */
		public void setAmount(java.math.BigDecimal decimal) {
			amount = decimal;
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
		public void setBegin_date(Integer integer) {
			begin_date = integer;
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
		public void setBond_date(Integer integer) {
			bond_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setBond_days(Integer integer) {
			bond_days = integer;
		}

		/**
		 * @param decimal
		 */
		public void setBond_tax(java.math.BigDecimal decimal) {
			bond_tax = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setBond1(java.math.BigDecimal decimal) {
			bond1 = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setBond2(java.math.BigDecimal decimal) {
			bond2 = decimal;
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
		public void setCard_id(String string) {
			card_id = string;
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
		public void setEnd_date(Integer integer) {
			end_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setEnddate(Integer integer) {
			enddate = integer;
		}

		/**
		 * @param integer
		 */
		public void setFp_date(java.lang.Integer integer) {
			fp_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setFp_flag(java.lang.Integer integer) {
			fp_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setFp_type(Integer integer) {
			fp_type = integer;
		}

		/**
		 * @param decimal
		 */
		public void setGain1(java.math.BigDecimal decimal) {
			gain1 = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setGain2(java.math.BigDecimal decimal) {
			gain2 = decimal;
		}

		/**
		 * @param string
		 */
		public void setGroup_id(String string) {
			group_id = string;
		}

		/**
		 * @param string
		 */
		public void setGroup_id_out(String string) {
			group_id_out = string;
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
		public void setList_id(Integer integer) {
			list_id = integer;
		}

		/**
		 * @param integer
		 */
		public void setPost_flag(Integer integer) {
			post_flag = integer;
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
		public void setPz_flag(Integer integer) {
			pz_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setQx_date(Integer integer) {
			qx_date = integer;
		}

		/**
		 * @param decimal
		 */
		public void setRate(java.math.BigDecimal decimal) {
			rate = decimal;
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
		public void setStartdate(Integer integer) {
			startdate = integer;
		}

		/**
		 * @param string
		 */
		public void setSub_code(String string) {
			sub_code = string;
		}

		/**
		 * @param decimal
		 */
		public void setSy_amount(java.math.BigDecimal decimal) {
			sy_amount = decimal;
		}

		/**
		 * @param integer
		 */
		public void setSy_date(java.lang.Integer integer) {
			sy_date = integer;
		}

		/**
		 * @param decimal
		 */
		public void setSy_money(java.math.BigDecimal decimal) {
			sy_money = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setSy_rate(java.math.BigDecimal decimal) {
			sy_rate = decimal;
		}

		/**
		 * @param string
		 */
		public void setSy_type(java.lang.String string) {
			sy_type = string;
		}

		/**
		 * @param string
		 */
		public void setSy_type_name(java.lang.String string) {
			sy_type_name = string;
		}

		/**
		 * @param decimal
		 */
		public void setTax_rate(java.math.BigDecimal decimal) {
			tax_rate = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setTo_money2(java.math.BigDecimal decimal) {
			to_money2 = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setTotal_money(java.math.BigDecimal decimal) {
			total_money = decimal;
		}

        
		/**
		 * @return 返回 link_man。
		 */
		public Integer getLink_man() {
			return link_man;
		}
		/**
		 * @param link_man 要设置的 link_man。
		 */
		public void setLink_man(Integer link_man) {
			this.link_man = link_man;
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
        
        
		/**
		 * @return 返回 prov_flag。
		 */
		public Integer getProv_flag() {
			return prov_flag;
		}
		/**
		 * @param prov_flag 要设置的 prov_flag。
		 */
		public void setProv_flag(Integer prov_flag) {
			this.prov_flag = prov_flag;
		}
}
