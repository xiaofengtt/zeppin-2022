/*
 * 创建日期 2011-8-24
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
public class TcoContractMaintainVO {
	private Integer maintain_id;
	private String coContractMaintain_sub_bh;
	private Integer cust_id;
	private Integer main_period;
	private Integer main_period_unit;
	private String main_pro_name;
	private Integer collect_time;
	private Integer start_date;
	private Integer end_date;
	private BigDecimal   ht_money;
	private BigDecimal 	 wh_money;
	private String main_summary;
	private String bank_id;
	private String bank_name;
	private String bank_acct;
	private String acct_name;
	private Integer invoice_time;
	private BigDecimal invoice_money;
	private Integer invoice_man;
	private Integer arriveMoney_flag;
	private BigDecimal arrive_money;
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag;
	private Integer check_man;
	private Integer check_date;
	private Integer problemId;
	private Integer collect_time_begin;
	private Integer Collect_time_end;
	private Integer start_date_begin;
	private Integer start_date_end;
	private Integer end_date_begin;
	private Integer end_date_end;
	private String cust_name;
	private Integer invoice_time_begin;
	private Integer invoice_time_end;
	private String invoice_summary;
	
	//modi 20111029
	private String sub_bh;
	
	
	public Integer getInvoice_time_begin() {
		return invoice_time_begin;
	}
	public void setInvoice_time_begin(Integer invoiceTimeBegin) {
		invoice_time_begin = invoiceTimeBegin;
	}
	public Integer getInvoice_time_end() {
		return invoice_time_end;
	}
	public void setInvoice_time_end(Integer invoiceTimeEnd) {
		invoice_time_end = invoiceTimeEnd;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String custName) {
		cust_name = custName;
	}
	public Integer getCollect_time_begin() {
		return collect_time_begin;
	}
	public void setCollect_time_begin(Integer collectTimeBegin) {
		collect_time_begin = collectTimeBegin;
	}
	public Integer getCollect_time_end() {
		return Collect_time_end;
	}
	public void setCollect_time_end(Integer collectTimeEnd) {
		Collect_time_end = collectTimeEnd;
	}
	public Integer getStart_date_begin() {
		return start_date_begin;
	}
	public void setStart_date_begin(Integer startDateBegin) {
		start_date_begin = startDateBegin;
	}
	public Integer getStart_date_end() {
		return start_date_end;
	}
	public void setStart_date_end(Integer startDateEnd) {
		start_date_end = startDateEnd;
	}

	public Integer getEnd_date_begin() {
		return end_date_begin;
	}
	public void setEnd_date_begin(Integer endDateBegin) {
		end_date_begin = endDateBegin;
	}
	public Integer getEnd_date_end() {
		return end_date_end;
	}
	public void setEnd_date_end(Integer endDateEnd) {
		end_date_end = endDateEnd;
	}
	public Integer getMaintain_id() {
		return maintain_id;
	}
	public void setMaintain_id(Integer maintainId) {
		maintain_id = maintainId;
	}
	public String getCoContractMaintain_sub_bh() {
		return coContractMaintain_sub_bh;
	}
	public void setCoContractMaintain_sub_bh(String coContractMaintainSubBh) {
		coContractMaintain_sub_bh = coContractMaintainSubBh;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public void setCust_id(Integer custId) {
		cust_id = custId;
	}
	public Integer getMain_period() {
		return main_period;
	}
	public void setMain_period(Integer mainPeriod) {
		main_period = mainPeriod;
	}
	public Integer getMain_period_unit() {
		return main_period_unit;
	}
	public void setMain_period_unit(Integer mainPeriodUnit) {
		main_period_unit = mainPeriodUnit;
	}
	public String getMain_pro_name() {
		return main_pro_name;
	}
	public void setMain_pro_name(String mainProName) {
		main_pro_name = mainProName;
	}
	public Integer getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(Integer collectTime) {
		collect_time = collectTime;
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
	public BigDecimal getHt_money() {
		return ht_money;
	}
	public void setHt_money(BigDecimal htMoney) {
		ht_money = htMoney;
	}
	public BigDecimal getWh_money() {
		return wh_money;
	}
	public void setWh_money(BigDecimal whMoney) {
		wh_money = whMoney;
	}
	public String getMain_summary() {
		return main_summary;
	}
	public void setMain_summary(String mainSummary) {
		main_summary = mainSummary;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bankId) {
		bank_id = bankId;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	public String getBank_acct() {
		return bank_acct;
	}
	public void setBank_acct(String bankAcct) {
		bank_acct = bankAcct;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acctName) {
		acct_name = acctName;
	}
	public Integer getInvoice_time() {
		return invoice_time;
	}
	public void setInvoice_time(Integer invoiceTime) {
		invoice_time = invoiceTime;
	}
	public BigDecimal getInvoice_money() {
		return invoice_money;
	}
	public void setInvoice_money(BigDecimal invoiceMoney) {
		invoice_money = invoiceMoney;
	}
	public Integer getInvoice_man() {
		return invoice_man;
	}
	public void setInvoice_man(Integer invoiceMan) {
		invoice_man = invoiceMan;
	}
	public Integer getArriveMoney_flag() {
		return arriveMoney_flag;
	}
	public void setArriveMoney_flag(Integer arriveMoneyFlag) {
		arriveMoney_flag = arriveMoneyFlag;
	}
	public BigDecimal getArrive_money() {
		return arrive_money;
	}
	public void setArrive_money(BigDecimal arriveMoney) {
		arrive_money = arriveMoney;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public Integer getInput_time() {
		return input_time;
	}
	public void setInput_time(Integer inputTime) {
		input_time = inputTime;
	}
	public Integer getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(Integer checkFlag) {
		check_flag = checkFlag;
	}
	public Integer getCheck_man() {
		return check_man;
	}
	public void setCheck_man(Integer checkMan) {
		check_man = checkMan;
	}
	public Integer getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Integer checkDate) {
		check_date = checkDate;
	}
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	
	/**
	 * @return 返回 sub_bh。
	 */
	public String getSub_bh() {
		return sub_bh;
	}
	/**
	 * @param sub_bh 要设置的 sub_bh。
	 */
	public void setSub_bh(String sub_bh) {
		this.sub_bh = sub_bh;
	}
	/**
	 * @return 返回 invoice_summary。
	 */
	public String getInvoice_summary() {
		return invoice_summary;
	}
	/**
	 * @param invoice_summary 要设置的 invoice_summary。
	 */
	public void setInvoice_summary(String invoice_summary) {
		this.invoice_summary = invoice_summary;
	}
}
