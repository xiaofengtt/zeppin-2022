/*
 * 创建日期 2011-8-26
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
public class TcoContractRecordVO {
	private Integer mainRecord_id;
	private Integer cust_id;
	private String main_status;
	private String main_status_name;
	private String main_pro_name;
	private String main_content;
	private Integer team_id;
	private Integer coProduct_manager;
	private Integer record_date;
	private Integer input_man;
	private Integer input_time;
	private Integer problemId;
	private Integer record_date_begin;
	private Integer record_date_end;
	private String cust_name;
	
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String custName) {
		cust_name = custName;
	}
	public Integer getRecord_date_begin() {
		return record_date_begin;
	}
	public void setRecord_date_begin(Integer recordDateBegin) {
		record_date_begin = recordDateBegin;
	}
	public Integer getRecord_date_end() {
		return record_date_end;
	}
	public void setRecord_date_end(Integer recordDateEnd) {
		record_date_end = recordDateEnd;
	}
	public Integer getMainRecord_id() {
		return mainRecord_id;
	}
	public void setMainRecord_id(Integer mainRecordId) {
		mainRecord_id = mainRecordId;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public void setCust_id(Integer custId) {
		cust_id = custId;
	}
	public String getMain_status() {
		return main_status;
	}
	public void setMain_status(String mainStatus) {
		main_status = mainStatus;
	}
	public String getMain_status_name() {
		return main_status_name;
	}
	public void setMain_status_name(String mainStatusName) {
		main_status_name = mainStatusName;
	}
	public String getMain_pro_name() {
		return main_pro_name;
	}
	public void setMain_pro_name(String mainProName) {
		main_pro_name = mainProName;
	}
	public String getMain_content() {
		return main_content;
	}
	public void setMain_content(String mainContent) {
		main_content = mainContent;
	}
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer teamId) {
		team_id = teamId;
	}
	public Integer getCoProduct_manager() {
		return coProduct_manager;
	}
	public void setCoProduct_manager(Integer coProductManager) {
		coProduct_manager = coProductManager;
	}
	public Integer getRecord_date() {
		return record_date;
	}
	public void setRecord_date(Integer recordDate) {
		record_date = recordDate;
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
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	
}
