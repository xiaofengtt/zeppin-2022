/*
 * 创建日期 2011-5-12
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author my
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class NonProductVO {
	
	private Integer nonproduct_id;				//主键
	private String nonproduct_name;				//非信托产品名称
	private String investment_direction;		//投向（字典1155）
	private String investment_direction_name;	//投向名称
	private Integer valid_priod_unit;			//期限单位：1天2月3季4年9无
	private Integer valid_priod;				//期限
	private Integer start_date;					//起始日期
	private Integer end_date;					//结束日期
	private Integer fact_end_date;				//实际结束日期
	private BigDecimal expect_money;			//预期产品金额
	private BigDecimal expect_rate1;			//预期收益1
	private BigDecimal expect_rate2;			//预期收益2
	private BigDecimal face_moeny;				//实际产品金额
	private String investment_manager;			//投资管理人
	private Integer partner_cust_id;			//合伙人企业ID（TCustomers.CUST_ID）
	private String status; 						//状态（使用信托产品状态，字典1102）
	private Integer operator;					//录入操作员
	private java.sql.Timestamp input_time;		//录入时间
	private Integer check_flag;					//审核标志1未审核2已审核
	private Integer check_man;					//审核操作员
	private java.sql.Timestamp check_time;		//审核时间
	private Integer out_nonproduct_id;
	private Integer input_man;
	private String partner_cust_name;			//合伙人
	private String description;                //简介
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPartner_cust_name() {
		return partner_cust_name;
	}
	public void setPartner_cust_name(String partnerCustName) {
		partner_cust_name = partnerCustName;
	}
	public Integer getOut_nonproduct_id() {
		return out_nonproduct_id;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public void setOut_nonproduct_id(Integer outNonproductId) {
		out_nonproduct_id = outNonproductId;
	}
	public Integer getNonproduct_id() {
		return nonproduct_id;
	}
	public void setNonproduct_id(Integer nonproductId) {
		nonproduct_id = nonproductId;
	}
	public String getNonproduct_name() {
		return nonproduct_name;
	}
	public void setNonproduct_name(String nonproductName) {
		nonproduct_name = nonproductName;
	}
	public String getInvestment_direction() {
		return investment_direction;
	}
	public void setInvestment_direction(String investmentDirection) {
		investment_direction = investmentDirection;
	}
	public String getInvestment_direction_name() {
		return investment_direction_name;
	}
	public void setInvestment_direction_name(String investmentDirectionName) {
		investment_direction_name = investmentDirectionName;
	}
	public Integer getValid_priod_unit() {
		return valid_priod_unit;
	}
	public void setValid_priod_unit(Integer validPriodUnit) {
		valid_priod_unit = validPriodUnit;
	}
	public Integer getValid_priod() {
		return valid_priod;
	}
	public void setValid_priod(Integer validPriod) {
		valid_priod = validPriod;
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
	public Integer getFact_end_date() {
		return fact_end_date;
	}
	public void setFact_end_date(Integer factEndDate) {
		fact_end_date = factEndDate;
	}
	public BigDecimal getExpect_money() {
		return expect_money;
	}
	public void setExpect_money(BigDecimal expectMoney) {
		expect_money = expectMoney;
	}
	public BigDecimal getExpect_rate1() {
		return expect_rate1;
	}
	public void setExpect_rate1(BigDecimal expectRate1) {
		expect_rate1 = expectRate1;
	}
	public BigDecimal getExpect_rate2() {
		return expect_rate2;
	}
	public void setExpect_rate2(BigDecimal expectRate2) {
		expect_rate2 = expectRate2;
	}
	public BigDecimal getFace_moeny() {
		return face_moeny;
	}
	public void setFace_moeny(BigDecimal faceMoeny) {
		face_moeny = faceMoeny;
	}
	public String getInvestment_manager() {
		return investment_manager;
	}
	public void setInvestment_manager(String investmentManager) {
		investment_manager = investmentManager;
	}
	public Integer getPartner_cust_id() {
		return partner_cust_id;
	}
	public void setPartner_cust_id(Integer partnerCustId) {
		partner_cust_id = partnerCustId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	public void setInput_time(java.sql.Timestamp inputTime) {
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
	public java.sql.Timestamp getCheck_time() {
		return check_time;
	}
	public void setCheck_time(java.sql.Timestamp checkTime) {
		check_time = checkTime;
	}
	
	
	
}
