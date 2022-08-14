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
public class PreDoubleRecordInfoVO {

	private Integer serial_no; //序号

	private Integer pre_serial_no; //预约表主键(EFCRM..TPRECONTRACT.SERIAL_NO)

	private Integer dz_date; //到帐日期

	private BigDecimal dz_money; //到帐金额

	private Integer refund_date; //退款日期

	private BigDecimal refund_money; //退款金额

	private String sl_type; //缴款方式(1114)

	private String sl_type_name; //缴款方式名称
	
	private Integer pre_product_id; //产品ID

	private Integer input_man; //操作员
	
	private Integer start_date;
	
	private Integer end_date;
	
	private String cust_name;
	
	private String pre_status;
	
	private String pre_level;
	
	private String pre_level_name;
	
	private Integer team_id;
	
	private Integer is_onway; // 在途资金标志:1是
	
	private String sl_time; // 到帐时间,精确到分
	
	private Integer sbf_serial_no; //到账银行账号序号
	
	private Integer modiquota; //是否自动修改预约及释放配额
	
	private Integer status;
	
	private String statusName;
	
	/**
	 * @return 返回 modiquota。
	 */
	public Integer getModiquota() {
		return modiquota;
	}
	/**
	 * @param modiquota 要设置的 modiquota。
	 */
	public void setModiquota(Integer modiquota) {
		this.modiquota = modiquota;
	}
	/**
	 * @return 返回 sbf_serial_no。
	 */
	public Integer getSbf_serial_no() {
		return sbf_serial_no;
	}
	/**
	 * @param sbf_serial_no 要设置的 sbf_serial_no。
	 */
	public void setSbf_serial_no(Integer sbf_serial_no) {
		this.sbf_serial_no = sbf_serial_no;
	}
	/**
	 * @return 返回 pre_status。
	 */
	public String getPre_status() {
		return pre_status;
	}
	/**
	 * @param pre_status 要设置的 pre_status。
	 */
	public void setPre_status(String pre_status) {
		this.pre_status = pre_status;
	}
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
	 * @return 返回 end_date。
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date 要设置的 end_date。
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
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
	 * @return 返回 dz_date。
	 */
	public Integer getDz_date() {
		return dz_date;
	}
	/**
	 * @param dz_date 要设置的 dz_date。
	 */
	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}
	/**
	 * @return 返回 dz_money。
	 */
	public BigDecimal getDz_money() {
		return dz_money;
	}
	/**
	 * @param dz_money 要设置的 dz_money。
	 */
	public void setDz_money(BigDecimal dz_money) {
		this.dz_money = dz_money;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 pre_serial_no。
	 */
	public Integer getPre_serial_no() {
		return pre_serial_no;
	}
	/**
	 * @param pre_serial_no 要设置的 pre_serial_no。
	 */
	public void setPre_serial_no(Integer pre_serial_no) {
		this.pre_serial_no = pre_serial_no;
	}
	/**
	 * @return 返回 refund_date。
	 */
	public Integer getRefund_date() {
		return refund_date;
	}
	/**
	 * @param refund_date 要设置的 refund_date。
	 */
	public void setRefund_date(Integer refund_date) {
		this.refund_date = refund_date;
	}
	/**
	 * @return 返回 refund_money。
	 */
	public BigDecimal getRefund_money() {
		return refund_money;
	}
	/**
	 * @param refund_money 要设置的 refund_money。
	 */
	public void setRefund_money(BigDecimal refund_money) {
		this.refund_money = refund_money;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 pre_product_id。
	 */
	public Integer getPre_product_id() {
		return pre_product_id;
	}
	/**
	 * @param pre_product_id 要设置的 pre_product_id。
	 */
	public void setPre_product_id(Integer pre_product_id) {
		this.pre_product_id = pre_product_id;
	}

    /**
     * @return 返回 pre_level。
     */
    public String getPre_level() {
        return pre_level;
    }
    /**
     * @param pre_level 要设置的 pre_level。
     */
    public void setPre_level(String pre_level) {
        this.pre_level = pre_level;
    }
    /**
     * @return 返回 pre_level_name。
     */
    public String getPre_level_name() {
        return pre_level_name;
    }
    /**
     * @param pre_level_name 要设置的 pre_level_name。
     */
    public void setPre_level_name(String pre_level_name) {
        this.pre_level_name = pre_level_name;
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
	 * @return 返回 is_onway。
	 */
	public Integer getIs_onway() {
		return is_onway;
	}
	/**
	 * @param is_onway 要设置的 is_onway。
	 */
	public void setIs_onway(Integer is_onway) {
		this.is_onway = is_onway;
	}
	
	public String getSl_type() {
		return sl_type;
	}
	
	public void setSl_type(String sl_type) {
		this.sl_type = sl_type;
	}
	
	public String getSl_type_name() {
		return sl_type_name;
	}
	
	public void setSl_type_name(String sl_type_name) {
		this.sl_type_name = sl_type_name;
	}
	
	public String getSl_time() {
		return sl_time;
	}
	
	public void setSl_time(String sl_time) {
		this.sl_time = sl_time;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}