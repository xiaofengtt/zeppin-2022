/*
 * 创建日期 2011-8-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class GainLevelRateVO {

	private Integer serial_no ;
	
	private Integer df_serial_no ;
	
	private BigDecimal gain_rate ;
	
	private Integer input_man ;
	
	private Integer start_date ;
	
	private Integer end_date ;
	
	private BigDecimal float_rate;
 
	
	private Integer product_id;
	private Integer sub_product_id;
	private Integer share_flag;
	private Integer open_begin_date;
	private Integer open_end_date;
	private Integer pz_flag;
	private Integer open_status;
	
	public Integer getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(Integer serialNo) {
		serial_no = serialNo;
	}

	public Integer getDf_serial_no() {
		return df_serial_no;
	}

	public void setDf_serial_no(Integer dfSerialNo) {
		df_serial_no = dfSerialNo;
	}

	public BigDecimal getGain_rate() {
		return gain_rate;
	}

	public void setGain_rate(BigDecimal gainRate) {
		gain_rate = gainRate;
	}

	public Integer getInput_man() {
		return input_man;
	}

	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}

	public Integer getStart_date() {
		return start_date;
	}

	public void setStart_date(Integer startDate) {
		start_date = startDate;
	}

	public Integer getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Integer endDate) {
		end_date = endDate;
	}

	public BigDecimal getFloat_rate() {
		return float_rate;
	}

	public void setFloat_rate(BigDecimal floatRate) {
		float_rate = floatRate;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public Integer getSub_product_id() {
		return sub_product_id;
	}

	public void setSub_product_id(Integer subProductId) {
		sub_product_id = subProductId;
	}

	public Integer getShare_flag() {
		return share_flag;
	}

	public void setShare_flag(Integer shareFlag) {
		share_flag = shareFlag;
	}

	public Integer getOpen_begin_date() {
		return open_begin_date;
	}

	public void setOpen_begin_date(Integer openBeginDate) {
		open_begin_date = openBeginDate;
	}

	public Integer getOpen_end_date() {
		return open_end_date;
	}

	public void setOpen_end_date(Integer openEndDate) {
		open_end_date = openEndDate;
	}

	public Integer getPz_flag() {
		return pz_flag;
	}

	public void setPz_flag(Integer pzFlag) {
		pz_flag = pzFlag;
	}

	public Integer getOpen_status() {
		return open_status;
	}

	public void setOpen_status(Integer openStatus) {
		open_status = openStatus;
	}
		
	
}
