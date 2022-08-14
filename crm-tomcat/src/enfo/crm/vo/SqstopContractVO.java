/*
 * 创建日期 2009-12-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * 发行期退款申请对象对应SqstopContractVO对象
 * @author dingyj
 * @since 2009-12-11
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SqstopContractVO {

	private Integer serial_no;
	private Integer ht_serial_no;//对应合同表中的serial_no;
	private Integer product_id;
	private String contract_bh;
	private String reason;//退款原因
	private Integer sq_date;
	private BigDecimal sq_money;
	private BigDecimal fee;//退款手续费
	private Integer t_rg_fee;//是否退认购费 0不退 1退
	private BigDecimal rg_fee;
	private BigDecimal gs_rate;
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag;// 1未审核；2已审核； 3财务审核
	private Integer check_man;//审核人
	private Integer check_time;//审核时间
	private Integer check_man2;//财务审核人
	private Integer check_time2;//财务审核时间
	
	private Integer trans_date;//记账日期
	
	private BigDecimal rg_money;
	
	private Integer rebate_flag;	
	
	private Integer sub_product_id;
	
	private BigDecimal ht_fee;

	/**
	 * @return 返回 ht_fee。
	 */
	public BigDecimal getHt_fee() {
		return ht_fee;
	}
	/**
	 * @param ht_fee 要设置的 ht_fee。
	 */
	public void setHt_fee(BigDecimal ht_fee) {
		this.ht_fee = ht_fee;
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
	public Integer getRebate_flag() {
		return rebate_flag;
	}

	public void setRebate_flag(Integer rebate_flag) {
		this.rebate_flag = rebate_flag;
	}
	public Integer getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(Integer transDate) {
		trans_date = transDate;
	}

	public BigDecimal getRg_money() {
		return rg_money;
	}

	public void setRg_money(BigDecimal rgMoney) {
		rg_money = rgMoney;
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
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * @return
	 */
	public BigDecimal getGs_rate() {
		return gs_rate;
	}

	/**
	 * @return
	 */
	public Integer getHt_serial_no() {
		return ht_serial_no;
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
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @return
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return
	 */
	public BigDecimal getRg_fee() {
		return rg_fee;
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
	public Integer getSq_date() {
		return sq_date;
	}

	/**
	 * @return
	 */
	public BigDecimal getSq_money() {
		return sq_money;
	}

	/**
	 * @return
	 */
	public Integer getT_rg_fee() {
		return t_rg_fee;
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
	 * @param decimal
	 */
	public void setFee(BigDecimal decimal) {
		fee = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setGs_rate(BigDecimal decimal) {
		gs_rate = decimal;
	}

	/**
	 * @param integer
	 */
	public void setHt_serial_no(Integer integer) {
		ht_serial_no = integer;
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
	public void setProduct_id(Integer integer) {
		product_id = integer;
	}

	/**
	 * @param string
	 */
	public void setReason(String string) {
		reason = string;
	}

	/**
	 * @param decimal
	 */
	public void setRg_fee(BigDecimal decimal) {
		rg_fee = decimal;
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
	public void setSq_date(Integer integer) {
		sq_date = integer;
	}

	/**
	 * @param decimal
	 */
	public void setSq_money(BigDecimal decimal) {
		sq_money = decimal;
	}

	/**
	 * @param integer
	 */
	public void setT_rg_fee(Integer integer) {
		t_rg_fee = integer;
	}

}
