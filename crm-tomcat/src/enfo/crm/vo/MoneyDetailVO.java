/*
 * 创建日期 2009-12-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import enfo.crm.tools.Utility;

/**
 * 缴款详细对象对应MoneyDetailVO对象
 * @author dingyj
 * @since 2009-12-11
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class MoneyDetailVO {

	private java.lang.Integer book_code;
	private java.lang.Integer serial_no;
	private java.lang.Integer product_id;
	private java.lang.String contract_bh;
	private java.lang.String contract_sub_bh;
	private Integer dz_date;
	private java.math.BigDecimal to_money;
	private java.math.BigDecimal to_amount;
	private java.lang.Integer to_bank_date;
	private java.lang.String jk_type;
	private java.lang.String jk_type_name;
	private java.lang.Integer input_man;
	private java.sql.Timestamp input_time;
	private java.lang.Integer check_flag;
	private java.lang.Integer check_man;
	private java.lang.Integer check_date;
	private java.lang.Integer bond_date;
	private java.lang.Integer bond_days;
	private java.math.BigDecimal rate;
	private java.math.BigDecimal bond1;
	private java.math.BigDecimal bond2;
	private java.lang.Integer js_man;
	private java.lang.Integer js_date;
	private java.lang.Integer bond_check_flag;
	private java.lang.Integer bond_check_man;
	private Integer bond_check_time;
	private java.lang.String summary;
	private java.lang.Integer cust_id;
	private java.lang.String cust_name;
	private java.lang.String cust_no;
	private java.math.BigDecimal rg_money;
	private java.math.BigDecimal jk_money;
	private java.lang.Integer list_id;
	private java.math.BigDecimal max_to_money;
	private java.math.BigDecimal min_to_money;

	private java.lang.Integer card_lost_flag;
	private java.lang.Integer card_lost_date;
	private java.lang.Integer fee_check_flag;

	private java.math.BigDecimal fee_money;
	private java.lang.Integer fee_type;
	private java.math.BigDecimal nav_price;

	private java.lang.String ben_card_no;
	private java.lang.String product_name;
	private String group_id;
	private String group_id_out;
	private Integer post_flag;
	private Integer fee_date;

	private String product_code;
	
	private Integer start_date;
	private Integer end_date;

    private Integer cell_flag;
    private Integer cell_id;
    private Integer depart_id;
    private Integer intrust_flag;
    private Integer sub_product_id;

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
	 * @return
	 */
	public java.lang.String getBen_card_no() {
		return ben_card_no;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBond_check_flag() {
		return bond_check_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBond_check_man() {
		return bond_check_man;
	}

	/**
	 * @return
	 */
	public Integer getBond_check_time() {
		return bond_check_time;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBond_date() {
		return bond_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getBond_days() {
		return bond_days;
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
	public java.lang.Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCard_lost_date() {
		return card_lost_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCard_lost_flag() {
		return card_lost_flag;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCheck_date() {
		return check_date;
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
	public Integer getDz_date() {
		return dz_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getFee_check_flag() {
		return fee_check_flag;
	}

	/**
	 * @return
	 */
	public Integer getFee_date() {
		return fee_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getFee_money() {
		return fee_money;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getFee_type() {
		return fee_type;
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
	public java.math.BigDecimal getJk_money() {
		return jk_money;
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
	public java.lang.Integer getJs_date() {
		return js_date;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getJs_man() {
		return js_man;
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
	public java.math.BigDecimal getMax_to_money() {
		return max_to_money;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getMin_to_money() {
		return min_to_money;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getNav_price() {
		return nav_price;
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
	public java.math.BigDecimal getRate() {
		return rate;
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
	public java.lang.String getSummary() {
		return summary;
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
	public java.lang.Integer getTo_bank_date() {
		return to_bank_date;
	}

	/**
	 * @return
	 */
	public java.math.BigDecimal getTo_money() {
		return to_money;
	}

	/**
	 * @param string
	 */
	public void setBen_card_no(java.lang.String string) {
		ben_card_no = string;
	}

	/**
	 * @param integer
	 */
	public void setBond_check_flag(java.lang.Integer integer) {
		bond_check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setBond_check_man(java.lang.Integer integer) {
		bond_check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setBond_check_time(Integer integer) {
		bond_check_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setBond_date(java.lang.Integer integer) {
		bond_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBond_days(java.lang.Integer integer) {
		bond_days = integer;
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
	public void setBook_code(java.lang.Integer integer) {
		book_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setCard_lost_date(java.lang.Integer integer) {
		card_lost_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCard_lost_flag(java.lang.Integer integer) {
		card_lost_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_date(java.lang.Integer integer) {
		check_date = integer;
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
	public void setDz_date(Integer integer) {
		dz_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setFee_check_flag(java.lang.Integer integer) {
		fee_check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setFee_date(Integer integer) {
		fee_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFee_money(java.math.BigDecimal decimal) {
		fee_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setFee_type(java.lang.Integer integer) {
		fee_type = integer;
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
	 * @param decimal
	 */
	public void setJk_money(java.math.BigDecimal decimal) {
		jk_money = decimal;
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
	public void setJs_date(java.lang.Integer integer) {
		js_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setJs_man(java.lang.Integer integer) {
		js_man = integer;
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
	public void setMax_to_money(java.math.BigDecimal decimal) {
		max_to_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setMin_to_money(java.math.BigDecimal decimal) {
		min_to_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setNav_price(java.math.BigDecimal decimal) {
		nav_price = decimal;
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
	 * @param decimal
	 */
	public void setRate(java.math.BigDecimal decimal) {
		rate = decimal;
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
	public void setSummary(java.lang.String string) {
		summary = string;
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
	public void setTo_bank_date(java.lang.Integer integer) {
		to_bank_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTo_money(java.math.BigDecimal decimal) {
		to_money = decimal;
	}
	/**
	 * @return 返回 end_dte。
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_dte 要设置的 end_dte。
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
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
	 * @return 返回 intrust_flag。
	 */
	public Integer getIntrust_flag() {
		return intrust_flag;
	}
	/**
	 * @param intrust_flag 要设置的 intrust_flag。
	 */
	public void setIntrust_flag(Integer intrust_flag) {
		this.intrust_flag = intrust_flag;
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
