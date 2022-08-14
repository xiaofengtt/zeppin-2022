/*
 * 创建日期 2010-2-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author taochen
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 * 团队名称 	TEAM_NAME		表 TManagerTeams
销售配额 	QUOTAMONEY		表TTEAMQUOTA
团队成员 	
 */
public class SaleParameterVO {
	private Integer product_id;
	private String product_code;
	private String product_name;
	private BigDecimal pre_money;
	private Integer pre_num;
	private BigDecimal min_money;
	private Integer pre_start_date;
	private Integer pre_end_date;
	private Integer fact_end_date;
	private Integer period_unit;
	
	private String team_name;
	private Integer serial_no;
	private Integer team_id;
	private BigDecimal quota_money;
	private BigDecimal alreadysale;
	private Integer serial_no_subscribe;
	private Integer quota_qualified_num;
	private Integer already_qualified_num;
	private Integer cust_id;
	private BigDecimal rg_money;
	private Integer pre_product_id;
	private Integer service_man;
	
	private Integer preproduct_id;
	private Integer function_id;
	private Integer team_id2;
	private Integer list_id;
	private Integer sub_product_id;
	private Integer input_man;
	
	private String cust_name;
	private Integer team_type;
	
	private Integer tz_qualified_num;
	
	private Integer queryAll;
	
	/**
	 * @return 返回 tz_qualified_num。
	 */
	public Integer getTz_qualified_num() {
		return tz_qualified_num;
	}
	/**
	 * @param tz_qualified_num 要设置的 tz_qualified_num。
	 */
	public void setTz_qualified_num(Integer tz_qualified_num) {
		this.tz_qualified_num = tz_qualified_num;
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
	 * @return 返回 list_id。
	 */
	public Integer getList_id() {
		return list_id;
	}
	/**
	 * @param list_id 要设置的 list_id。
	 */
	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}
	/**
	 * @return 返回 function_id。
	 */
	public Integer getFunction_id() {
		return function_id;
	}
	/**
	 * @param function_id 要设置的 function_id。
	 */
	public void setFunction_id(Integer function_id) {
		this.function_id = function_id;
	}
	/**
	 * @param integer
	 */
	public void setProductID(Integer integer) {
		product_id = integer;
	}
	
	/**
	 * @return
	 */
	public Integer getProductID() {
		return product_id;
	}
		
	/**
	 * @param string
	 */
	public void setProductCode(String string) {
		product_code = string;
	}
	
	/**
	 * @return
	 */
	public String getProductCode() {
		return product_code;
	}

	/**
	 * @param string
	 */
	public void setProductName(String string) {
		product_name = string;
	}
	
	/**
	 * @return
	 */
	public String getProductName() {
		return product_name;
	}
	
	/**
	 * @param decimal
	 */
	public void setPreMoney(BigDecimal decimal) {
		pre_money = decimal;
	}

	/**
	 * @return
	 */
	public BigDecimal getPreMoney() {
		return pre_money;
	}
	
	/**
	 * @param integer
	 */
	public void setPreNum(Integer integer) {
		pre_num = integer;
	}
	
	/**
	 * @return
	 */
	public Integer getPreNum() {
		return pre_num;
	}
	
	/**
	 * @param decimal
	 */
	public void setMinMoney(BigDecimal decimal) {
		min_money = decimal;
	}
	
	/**
	 * @return
	 */
	public BigDecimal getMinMoney() {
		return min_money;
	}

	/**
	 * @param string
	 */
	public void setPreStartDate(Integer integer) {
		pre_start_date = integer;
	}
	
	/**
	 * @return
	 */
	public Integer getPreStartDate() {
		return pre_start_date;
	}
	
	/**
	 * @param string
	 */
	public void setPreEndDate(Integer integer) {
		pre_end_date = integer;
	}

	/**
	 * @return
	 */
	public Integer getPreEndDate() {
		return pre_end_date;
	}
	
	/**
	 * @param string
	 */
	public void setPreFactEndDate(Integer integer) {
		fact_end_date = integer;
	}
		
	/**
	 * @return
	 */
	public Integer getFactEndDate() {
		return fact_end_date;
	}
	
	/**
	 * @param string
	 */
	public void setPeriodUnit(Integer integer) {
		period_unit = integer;
	}
		
	/**
	 * @return
	 */
	public Integer getPeriodUnit() {
		return period_unit;
	}
	/**
	 * @param string
	 */
	
	public void setTeamName(String string) {
		team_name = string;
	}
		
	/**
	 * @return
	 */
	public String getTeamName() {
		return team_name;
	}
	
	/**
	 * @param string
	 */
	public void setTeamID(Integer integer) {
		team_id = integer;
	}
		
	/**
	 * @return
	 */
	public Integer getTeamID() {
		return team_id;
	}
	
	/**
	 * @param string
	 */
	public void setSerial_NO(Integer integer) {
		serial_no = integer;
	}
		
	/**
	 * @return
	 */
	public Integer getSerial_NO() {
		return serial_no;
	}
	
	/**
	 * @param decimal
	 */
	public void setQuotaMoney(BigDecimal decimal) {
		quota_money = decimal;
	}
	
	/**
	 * @return
	 */
	public BigDecimal getQuotaMoney() {
		return quota_money;
	}
	
	/**
	 * @param decimal
	 */
	public void setAlreadysale(BigDecimal decimal) {
		alreadysale = decimal;
	}
	
	/**
	 * @return
	 */
	public BigDecimal getAlreadysale() {
		return alreadysale;
	}

	public Integer getSerial_no_subscribe() {
		return serial_no_subscribe;
	}

	public void setSerial_no_subscribe(Integer serialNoSubscribe) {
		serial_no_subscribe = serialNoSubscribe;
	}

	public Integer getQuota_qualified_num() {
		return quota_qualified_num;
	}

	public void setQuota_qualified_num(Integer quotaQualifiedNum) {
		quota_qualified_num = quotaQualifiedNum;
	}

	public Integer getAlready_qualified_num() {
		return already_qualified_num;
	}

	public void setAlready_qualified_num(Integer alreadyQualifiedNum) {
		already_qualified_num = alreadyQualifiedNum;
	}

	public Integer getCust_id() {
		return cust_id;
	}

	public void setCust_id(Integer custId) {
		cust_id = custId;
	}

	public BigDecimal getRg_money() {
		return rg_money;
	}

	public void setRg_money(BigDecimal rgMoney) {
		rg_money = rgMoney;
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
	 * @return 返回 service_man。
	 */
	public Integer getService_man() {
		return service_man;
	}
	/**
	 * @param service_man 要设置的 service_man。
	 */
	public void setService_man(Integer service_man) {
		this.service_man = service_man;
	}

	/**
	 * @return 返回 preproduct_id。
	 */
	public Integer getPreproduct_id() {
		return preproduct_id;
	}
	/**
	 * @param preproduct_id 要设置的 preproduct_id。
	 */
	public void setPreproduct_id(Integer preproduct_id) {
		this.preproduct_id = preproduct_id;
	}
	/**
	 * @return 返回 team_id2。
	 */
	public Integer getTeam_id2() {
		return team_id2;
	}
	/**
	 * @param team_id2 要设置的 team_id2。
	 */
	public void setTeam_id2(Integer team_id2) {
		this.team_id2 = team_id2;
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
	 * @return 返回 team_type。
	 */
	public Integer getTeam_type() {
		return team_type;
	}
	/**
	 * @param team_type 要设置的 team_type。
	 */
	public void setTeam_type(Integer team_type) {
		this.team_type = team_type;
	}
	
	/**
	 * @return 返回 queryAll。
	 */
	public Integer getQueryAll() {
		return queryAll;
	}
	/**
	 * @param queryAll 要设置的 queryAll。
	 */
	public void setQueryAll(Integer queryAll) {
		this.queryAll = queryAll;
	}
}
