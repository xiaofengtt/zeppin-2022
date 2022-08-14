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
public class RedeemVO {
	private Integer serial_no;
	private Integer ben_serail_no;//对应TBENIFITOR表中的SERIAL_NO
	private Integer product_id;
	private String contract_bh;
	private Integer list;
	private BigDecimal redeem_amout;//申请赎回份额
	private Integer sq_date;
	private BigDecimal fee;
	private BigDecimal gs_fee;//费用的公司占比部分
	private BigDecimal nav_price;//赎回单位净值
	private BigDecimal redeem_money;//赎回金额
	private Integer trans_date;//赎回处理日期（开放日）
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag; // 1未审核 2已审核 3 已财务审核 4已兑付
	private Integer check_man;
	private Integer check_time;
	private Integer check_man2;
	private Integer check_time2;

	private Integer list_id;//受益人ID
	private Integer start_date;
	private Integer end_date;
	private Integer flag;

	private BigDecimal redeem_rate;//赎回比例
	private BigDecimal share_money;//浮动信托报酬 add by lk 20091023
	private BigDecimal fdyj_share_money;//浮动业绩报酬 add by lk 20091023
	
	private String cust_name;
	
	private Integer transfer_product_id;
	private Integer transfer_sub_product_id;
	private BigDecimal transfer_money;
	
	/**
	 * @return 返回 cust_name。
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name 要设置的 cust_name。
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return
	 */
	public Integer getBen_serail_no() {
		return ben_serail_no;
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
	public Integer getCheck_man2() {
		return check_man2;
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
	public Integer getCheck_time2() {
		return check_time2;
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
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getFdyj_share_money() {
		return fdyj_share_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getFee() {
		return fee;
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
	public BigDecimal getGs_fee() {
		return gs_fee;
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
	public Integer getList() {
		return list;
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
	public BigDecimal getNav_price() {
		return nav_price;
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
	public BigDecimal getRedeem_amout() {
		return redeem_amout;
	}

	/**
	 * @return
	 */
	public BigDecimal getRedeem_money() {
		return redeem_money;
	}

	/**
	 * @return
	 */
	public BigDecimal getRedeem_rate() {
		return redeem_rate;
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
	public BigDecimal getShare_money() {
		return share_money;
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
	public Integer getTrans_date() {
		return trans_date;
	}

	/**
	 * @param integer
	 */
	public void setBen_serail_no(Integer integer) {
		ben_serail_no = integer;
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
	public void setCheck_man2(Integer integer) {
		check_man2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_time(Integer integer) {
		check_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_time2(Integer integer) {
		check_time2 = integer;
	}

	/**
	 * @param string
	 */
	public void setContract_bh(String string) {
		contract_bh = string;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setFdyj_share_money(BigDecimal decimal) {
		fdyj_share_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setFee(BigDecimal decimal) {
		fee = decimal;
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
	public void setGs_fee(BigDecimal decimal) {
		gs_fee = decimal;
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
	public void setList(Integer integer) {
		list = integer;
	}

	/**
	 * @param integer
	 */
	public void setList_id(Integer integer) {
		list_id = integer;
	}

	/**
	 * @param decimal
	 */
	public void setNav_price(BigDecimal decimal) {
		nav_price = decimal;
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
	public void setRedeem_amout(BigDecimal decimal) {
		redeem_amout = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setRedeem_money(BigDecimal decimal) {
		redeem_money = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setRedeem_rate(BigDecimal decimal) {
		redeem_rate = decimal;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param decimal
	 */
	public void setShare_money(BigDecimal decimal) {
		share_money = decimal;
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
	 * @param integer
	 */
	public void setTrans_date(Integer integer) {
		trans_date = integer;
	}

	/**
	 * @return 返回 transfer_money。
	 */
	public BigDecimal getTransfer_money() {
		return transfer_money;
	}
	/**
	 * @param transfer_money 要设置的 transfer_money。
	 */
	public void setTransfer_money(BigDecimal transfer_money) {
		this.transfer_money = transfer_money;
	}
	/**
	 * @return 返回 transfer_product_id。
	 */
	public Integer getTransfer_product_id() {
		return transfer_product_id;
	}
	/**
	 * @param transfer_product_id 要设置的 transfer_product_id。
	 */
	public void setTransfer_product_id(Integer transfer_product_id) {
		this.transfer_product_id = transfer_product_id;
	}
	/**
	 * @return 返回 transfer_sub_product_id。
	 */
	public Integer getTransfer_sub_product_id() {
		return transfer_sub_product_id;
	}
	/**
	 * @param transfer_sub_product_id 要设置的 transfer_sub_product_id。
	 */
	public void setTransfer_sub_product_id(Integer transfer_sub_product_id) {
		this.transfer_sub_product_id = transfer_sub_product_id;
	}
}
