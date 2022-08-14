/*
 * 创建日期 2009-12-16
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * 受益权转让VO对应BenChangeVO
 * @author dingyj
 * @since 2009-12-16
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class BenChangeVO {
	
	private Integer book_code = new Integer(0);
		private Integer serial_no = new Integer(0);
		private Integer product_id = new Integer(0);
		private String contract_bh = "";
		private String contract_sub_bh = "";//2008/17 lzhd 合同编号，原来的实际合同编号
		private Integer from_cust_id = new Integer(0);
		private String jk_type = "";
		private java.math.BigDecimal to_amount;
		private java.math.BigDecimal sx_fee;
		private Integer to_cust_id = new Integer(0);
		private String summary = "";
		private Integer input_man = new Integer(0);
		private java.sql.Timestamp input_time;
		private Integer check_flag = new Integer(0);
		private String check_summary;
		private Integer check_man = new Integer(0);
		private java.sql.Timestamp check_time;
		private String trans_flag = "";
		private String trans_flag_name = "";

		private String to_cust_name;
		private String from_cust_no;
		private String from_cust_name;
		private String to_cust_no;

		private String bank_id;
		private String bank_name;
		private String bank_sub_name;
		private String bank_acct;
		private String bank_acct_name; //帐户名 
		
		private Integer change_date;
		//////谭鸿20050426添加begin记录发行期利息和未分配收益是否转让给新受益人begin///
		private Integer fx_change_flag; //1转让，2不转让
		private Integer sy_change_flag; //1转让，2不转让
		private String trans_type = ""; //转让类别 捐赠，转让，继承
		private String trans_type_name = "";
		//////谭鸿20050426添加begin记录发行期利息和未分配收益是否转让给新受益人begin///

		//陶然 2005-8-2 begin
		private Integer trans_date_begin;
		private Integer trans_date_end;
		private Integer change_qs_date;
		//end
		/////////yingxj 20050224
		private Integer form_list_id;
		private Integer to_list_id = new Integer(0);

		private String zqr_name;
		private Integer change_end_date;

		private Integer pl_flag;
		private String group_id;
		private java.math.BigDecimal frozen_tmp;
		private java.math.BigDecimal frozen_money;
		private java.math.BigDecimal frozen_money2;
		private Integer cust_id;
		private String cust_name;
		private Integer cust_type;
		private String card_id;
		private String address;
		private String card_type;
		private java.math.BigDecimal rate;
		private Integer from_list_id;
	
		//20070306 中泰 3个费用 by wangc
		private java.math.BigDecimal sx_fee1;
		private java.math.BigDecimal sx_fee2;
		private java.math.BigDecimal sx_fee3;
		private Integer reg_date;
	
		private String card_type_name;
		private BigDecimal from_prior_amount;
		private Integer projectid;
		private Integer rp_flag;
		private BigDecimal rp_money;
        
        private Integer sub_product_id;        
        
        private Integer srf_money_type;
        
        private String money_origin;
        
        private String sub_money_origin;
        
        
        
        
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
		 * @return 返回 srf_money_type。
		 */
		public Integer getSrf_money_type() {
			return srf_money_type;
		}
		/**
		 * @param srf_money_type 要设置的 srf_money_type。
		 */
		public void setSrf_money_type(Integer srf_money_type) {
			this.srf_money_type = srf_money_type;
		}
		/**
		 * @return 返回 bank_acct_name。
		 */
		public String getBank_acct_name() {
			return bank_acct_name;
		}
		/**
		 * @param bank_acct_name 要设置的 bank_acct_name。
		 */
		public void setBank_acct_name(String bank_acct_name) {
			this.bank_acct_name = bank_acct_name;
		}
		
		/**
		 * @return 返回 check_summary。
		 */
		public String getCheck_summary() {
			return check_summary;
		}
		/**
		 * @param check_summary 要设置的 check_summary。
		 */
		public void setCheck_summary(String check_summary) {
			this.check_summary = check_summary;
		}
		/**
		 * @return 返回 rp_flag。
		 */
		public Integer getRp_flag() {
			return rp_flag;
		}
		/**
		 * @param rp_flag 要设置的 rp_flag。
		 */
		public void setRp_flag(Integer rp_flag) {
			this.rp_flag = rp_flag;
		}
		/**
		 * @return 返回 rp_money。
		 */
		public BigDecimal getRp_money() {
			return rp_money;
		}
		/**
		 * @param rp_money 要设置的 rp_money。
		 */
		public void setRp_money(BigDecimal rp_money) {
			this.rp_money = rp_money;
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
	public Integer getBook_code() {
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
		public String getCard_type() {
			return card_type;
		}

		/**
		 * @return
		 */
		public String getCard_type_name() {
			return card_type_name;
		}

		/**
		 * @return
		 */
		public Integer getChange_date() {
			return change_date;
		}

		/**
		 * @return
		 */
		public Integer getChange_end_date() {
			return change_end_date;
		}

		/**
		 * @return
		 */
		public Integer getChange_qs_date() {
			return change_qs_date;
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
		public java.sql.Timestamp getCheck_time() {
			return check_time;
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
		public Integer getCust_type() {
			return cust_type;
		}

		/**
		 * @return
		 */
		public Integer getForm_list_id() {
			return form_list_id;
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
		public String getFrom_cust_name() {
			return from_cust_name;
		}

		/**
		 * @return
		 */
		public String getFrom_cust_no() {
			return from_cust_no;
		}

		/**
		 * @return
		 */
		public Integer getFrom_list_id() {
			return from_list_id;
		}

		/**
		 * @return
		 */
		public BigDecimal getFrom_prior_amount() {
			return from_prior_amount;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getFrozen_money() {
			return frozen_money;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getFrozen_money2() {
			return frozen_money2;
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
		public Integer getFx_change_flag() {
			return fx_change_flag;
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
		public Integer getInput_man() {
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
		public String getJk_type() {
			return jk_type;
		}

		/**
		 * @return
		 */
		public Integer getPl_flag() {
			return pl_flag;
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
		public java.math.BigDecimal getRate() {
			return rate;
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
		public Integer getSerial_no() {
			return serial_no;
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
		public java.math.BigDecimal getSx_fee() {
			return sx_fee;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSx_fee1() {
			return sx_fee1;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSx_fee2() {
			return sx_fee2;
		}

		/**
		 * @return
		 */
		public java.math.BigDecimal getSx_fee3() {
			return sx_fee3;
		}

		/**
		 * @return
		 */
		public Integer getSy_change_flag() {
			return sy_change_flag;
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
		public Integer getTo_cust_id() {
			return to_cust_id;
		}

		/**
		 * @return
		 */
		public String getTo_cust_name() {
			return to_cust_name;
		}

		/**
		 * @return
		 */
		public String getTo_cust_no() {
			return to_cust_no;
		}

		/**
		 * @return
		 */
		public Integer getTo_list_id() {
			return to_list_id;
		}

		/**
		 * @return
		 */
		public Integer getTrans_date_begin() {
			return trans_date_begin;
		}

		/**
		 * @return
		 */
		public Integer getTrans_date_end() {
			return trans_date_end;
		}

		/**
		 * @return
		 */
		public String getTrans_flag() {
			return trans_flag;
		}

		/**
		 * @return
		 */
		public String getTrans_flag_name() {
			return trans_flag_name;
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
		public String getTrans_type_name() {
			return trans_type_name;
		}

		/**
		 * @return
		 */
		public String getZqr_name() {
			return zqr_name;
		}

		/**
		 * @param string
		 */
		public void setAddress(String string) {
			address = string;
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
	public void setBook_code(Integer integer) {
		book_code = integer;
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
		 * @param string
		 */
		public void setCard_type_name(String string) {
			card_type_name = string;
		}

		/**
		 * @param integer
		 */
		public void setChange_date(Integer integer) {
			change_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setChange_end_date(Integer integer) {
			change_end_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setChange_qs_date(Integer integer) {
			change_qs_date = integer;
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
		 * @param timestamp
		 */
		public void setCheck_time(java.sql.Timestamp timestamp) {
			check_time = timestamp;
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
		 * @param integer
		 */
		public void setCust_type(Integer integer) {
			cust_type = integer;
		}

		/**
		 * @param integer
		 */
		public void setForm_list_id(Integer integer) {
			form_list_id = integer;
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
		public void setFrom_cust_name(String string) {
			from_cust_name = string;
		}

		/**
		 * @param string
		 */
		public void setFrom_cust_no(String string) {
			from_cust_no = string;
		}

		/**
		 * @param integer
		 */
		public void setFrom_list_id(Integer integer) {
			from_list_id = integer;
		}

		/**
		 * @param decimal
		 */
		public void setFrom_prior_amount(BigDecimal decimal) {
			from_prior_amount = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setFrozen_money(java.math.BigDecimal decimal) {
			frozen_money = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setFrozen_money2(java.math.BigDecimal decimal) {
			frozen_money2 = decimal;
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
		public void setFx_change_flag(Integer integer) {
			fx_change_flag = integer;
		}

		/**
		 * @param string
		 */
		public void setGroup_id(String string) {
			group_id = string;
		}

		/**
		 * @param integer
		 */
		public void setInput_man(Integer integer) {
			input_man = integer;
		}

		/**
		 * @param timestamp
		 */
		public void setInput_time(java.sql.Timestamp timestamp) {
			input_time = timestamp;
		}

		/**
		 * @param string
		 */
		public void setJk_type(String string) {
			jk_type = string;
		}

		/**
		 * @param integer
		 */
		public void setPl_flag(Integer integer) {
			pl_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setProduct_id(Integer integer) {
			product_id = integer;
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
		public void setReg_date(Integer integer) {
			reg_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setSerial_no(Integer integer) {
			serial_no = integer;
		}

		/**
		 * @param string
		 */
		public void setSummary(String string) {
			summary = string;
		}

		/**
		 * @param decimal
		 */
		public void setSx_fee(java.math.BigDecimal decimal) {
			sx_fee = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setSx_fee1(java.math.BigDecimal decimal) {
			sx_fee1 = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setSx_fee2(java.math.BigDecimal decimal) {
			sx_fee2 = decimal;
		}

		/**
		 * @param decimal
		 */
		public void setSx_fee3(java.math.BigDecimal decimal) {
			sx_fee3 = decimal;
		}

		/**
		 * @param integer
		 */
		public void setSy_change_flag(Integer integer) {
			sy_change_flag = integer;
		}

		/**
		 * @param decimal
		 */
		public void setTo_amount(java.math.BigDecimal decimal) {
			to_amount = decimal;
		}

		/**
		 * @param integer
		 */
		public void setTo_cust_id(Integer integer) {
			to_cust_id = integer;
		}

		/**
		 * @param string
		 */
		public void setTo_cust_name(String string) {
			to_cust_name = string;
		}

		/**
		 * @param string
		 */
		public void setTo_cust_no(String string) {
			to_cust_no = string;
		}

		/**
		 * @param integer
		 */
		public void setTo_list_id(Integer integer) {
			to_list_id = integer;
		}

		/**
		 * @param integer
		 */
		public void setTrans_date_begin(Integer integer) {
			trans_date_begin = integer;
		}

		/**
		 * @param integer
		 */
		public void setTrans_date_end(Integer integer) {
			trans_date_end = integer;
		}

		/**
		 * @param string
		 */
		public void setTrans_flag(String string) {
			trans_flag = string;
		}

		/**
		 * @param string
		 */
		public void setTrans_flag_name(String string) {
			trans_flag_name = string;
		}

		/**
		 * @param string
		 */
		public void setTrans_type(String string) {
			trans_type = string;
		}

		/**
		 * @param string
		 */
		public void setTrans_type_name(String string) {
			trans_type_name = string;
		}

		/**
		 * @param string
		 */
		public void setZqr_name(String string) {
			zqr_name = string;
		}

		/**
		 * @return 返回 projectid。
		 */
		public Integer getProjectid() {
			return projectid;
		}
		/**
		 * @param projectid 要设置的 projectid。
		 */
		public void setProjectid(Integer projectid) {
			this.projectid = projectid;
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
