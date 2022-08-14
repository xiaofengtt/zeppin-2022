/*
 * 创建日期 2012-3-19
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SalesResultForStatisticVO {

	private Integer serial_no; //序号

	private Integer pre_serial_no; //预约表主键(EFCRM..TPRECONTRACT.SERIAL_NO)

	private Integer dz_date; //到帐日期

	private BigDecimal dz_money; //到帐金额

	private Integer refund_date; //退款日期

	private BigDecimal refund_money; //退款金额

	private String jk_type; //缴款方式(1114)

	private String jk_type_name; //缴款方式名称
	
	private Integer pre_product_id; //产品ID

	private Integer input_man; //操作员
	
	private Integer start_date;
	
	private Integer end_date;
	
	private String cust_name;
	
	private String pre_status;
	
	private String sms1_customer;
	
	private String sms2_serviceman;
	
	private String pre_level;
	
	private String pre_level_name;
	
	private Integer team_id;
	
	private Integer is_onway; // 在途资金标志:1是
	
	private String dz_time; // 到帐时间,精确到分
	
	private Integer sbf_serial_no; //到账银行账号序号
	
	private Integer modiquota; //是否自动修改预约及释放配额
	
	private String jk_account; //缴款银行账号
	
	private String jk_bank_id; //缴款银行ID
	
	private String jk_bank_branch; //缴款银行分行/支行
	
	private Integer pre_product_type;
	
	private String pre_product_type_name;
	
	private Integer fk_tpremoneydetail;
	
	private Integer status;
	
	private String rg_product_name;
	
	private Integer rg_cust_type;
	
	private Integer rg_service_man;

	public Integer getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}

	public Integer getPre_serial_no() {
		return pre_serial_no;
	}

	public void setPre_serial_no(Integer pre_serial_no) {
		this.pre_serial_no = pre_serial_no;
	}

	public Integer getDz_date() {
		return dz_date;
	}

	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}

	public BigDecimal getDz_money() {
		return dz_money;
	}

	public void setDz_money(BigDecimal dz_money) {
		this.dz_money = dz_money;
	}

	public Integer getRefund_date() {
		return refund_date;
	}

	public void setRefund_date(Integer refund_date) {
		this.refund_date = refund_date;
	}

	public BigDecimal getRefund_money() {
		return refund_money;
	}

	public void setRefund_money(BigDecimal refund_money) {
		this.refund_money = refund_money;
	}

	public String getJk_type() {
		return jk_type;
	}

	public void setJk_type(String jk_type) {
		this.jk_type = jk_type;
	}

	public String getJk_type_name() {
		return jk_type_name;
	}

	public void setJk_type_name(String jk_type_name) {
		this.jk_type_name = jk_type_name;
	}

	public Integer getPre_product_id() {
		return pre_product_id;
	}

	public void setPre_product_id(Integer pre_product_id) {
		this.pre_product_id = pre_product_id;
	}

	public Integer getInput_man() {
		return input_man;
	}

	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}

	public Integer getStart_date() {
		return start_date;
	}

	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}

	public Integer getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getPre_status() {
		return pre_status;
	}

	public void setPre_status(String pre_status) {
		this.pre_status = pre_status;
	}

	public String getSms1_customer() {
		return sms1_customer;
	}

	public void setSms1_customer(String sms1_customer) {
		this.sms1_customer = sms1_customer;
	}

	public String getSms2_serviceman() {
		return sms2_serviceman;
	}

	public void setSms2_serviceman(String sms2_serviceman) {
		this.sms2_serviceman = sms2_serviceman;
	}

	public String getPre_level() {
		return pre_level;
	}

	public void setPre_level(String pre_level) {
		this.pre_level = pre_level;
	}

	public String getPre_level_name() {
		return pre_level_name;
	}

	public void setPre_level_name(String pre_level_name) {
		this.pre_level_name = pre_level_name;
	}

	public Integer getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}

	public Integer getIs_onway() {
		return is_onway;
	}

	public void setIs_onway(Integer is_onway) {
		this.is_onway = is_onway;
	}

	public String getDz_time() {
		return dz_time;
	}

	public void setDz_time(String dz_time) {
		this.dz_time = dz_time;
	}

	public Integer getSbf_serial_no() {
		return sbf_serial_no;
	}

	public void setSbf_serial_no(Integer sbf_serial_no) {
		this.sbf_serial_no = sbf_serial_no;
	}

	public Integer getModiquota() {
		return modiquota;
	}

	public void setModiquota(Integer modiquota) {
		this.modiquota = modiquota;
	}

	public String getJk_account() {
		return jk_account;
	}

	public void setJk_account(String jk_account) {
		this.jk_account = jk_account;
	}

	public String getJk_bank_id() {
		return jk_bank_id;
	}

	public void setJk_bank_id(String jk_bank_id) {
		this.jk_bank_id = jk_bank_id;
	}

	public String getJk_bank_branch() {
		return jk_bank_branch;
	}

	public void setJk_bank_branch(String jk_bank_branch) {
		this.jk_bank_branch = jk_bank_branch;
	}

	public Integer getPre_product_type() {
		return pre_product_type;
	}

	public void setPre_product_type(Integer pre_product_type) {
		this.pre_product_type = pre_product_type;
	}

	public String getPre_product_type_name() {
		return pre_product_type_name;
	}

	public void setPre_product_type_name(String pre_product_type_name) {
		this.pre_product_type_name = pre_product_type_name;
	}

	public Integer getFk_tpremoneydetail() {
		return fk_tpremoneydetail;
	}

	public void setFk_tpremoneydetail(Integer fk_tpremoneydetail) {
		this.fk_tpremoneydetail = fk_tpremoneydetail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRg_product_name() {
		return rg_product_name;
	}

	public void setRg_product_name(String rg_product_name) {
		this.rg_product_name = rg_product_name;
	}

	public Integer getRg_cust_type() {
		return rg_cust_type;
	}

	public void setRg_cust_type(Integer rg_cust_type) {
		this.rg_cust_type = rg_cust_type;
	}

	public Integer getRg_service_man() {
		return rg_service_man;
	}

	public void setRg_service_man(Integer rg_service_man) {
		this.rg_service_man = rg_service_man;
	}
}