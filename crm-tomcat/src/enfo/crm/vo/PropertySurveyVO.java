/*
 * 创建日期 2014-6-18
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

/**
 * @author zhou
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

import java.math.BigDecimal;

public class PropertySurveyVO {
	private java.lang.Integer serial_no;//序号
	private java.lang.Integer cust_id;//客户ID
	private java.lang.Integer survey_date;//调查日期
	private java.lang.String survey_man;//调查人
	private java.lang.String cust_name;//客户名称
	private java.lang.String link_address;//联系地址
	private java.lang.String link_phone;//联系电话
	private java.lang.String gr_nation;//国籍
	private java.lang.String profession;//职业 行业
	private java.lang.String business;//经营范围(机构有效)
	private BigDecimal capital;//注册资金(机构有效)
	private java.lang.String swdjhm;//税务登记号码(机构有效)
	private java.lang.String fr_name;//法定代表人(负责人)姓名(机构有效)
	private java.lang.String fz_card_type;//机构:法定代表人（负责人）身份证件类型 个人:身份证件类型
	private java.lang.String fz_card_type_name;//机构:法定代表人（负责人）身份证件类型名称 个人:身份证件类型名称
	private java.lang.String fz_card_id;//机构:法定代表人（负责人）身份证件号码 个人:身份证件号码
	private java.lang.Integer fz_card_yxq;//机构:法定代表人（负责人）身份证件有效期 个人:身份证件有效期限
	private java.lang.String gd_name;//控股股东或实际控制人姓名/名称(机构有效)
	private java.lang.String gd_card_type;//控股股东或实际控制人身份证件类型(机构有效)
	private java.lang.String gd_card_type_name;//控股股东或实际控制人身份证件类型名称(机构有效)
	private java.lang.String gd_card_id;//控股股东或实际控制人身份证件号码(机构有效)
	private java.lang.Integer gd_card_yxq;//控股股东或实际控制人身份证件有效期(机构有效)
	private java.lang.String bl_name;//授权业务办理人姓名
	private java.lang.String bl_card_type;//授权业务办理人身份证件类型
	private java.lang.String bl_card_type_name;//授权业务办理人身份证件类型名称
	private java.lang.String bl_card_id;//授权业务办理人身份证件号码
	private java.lang.Integer bl_card_yxq;//授权业务办理人身份证件有效期
	private java.lang.String property_source;//信托财产来源 2013(INTRUST:1167)
	private java.lang.Integer khfxdj;//客户风险等级 1高 2中 3低
	private java.lang.Integer modi_no;//修改次数
	private java.lang.Integer modi_man;//修改人
	private java.sql.Timestamp modi_time;//修改时间
	private java.lang.Integer input_man;//操作员
	private java.sql.Timestamp input_time;//操作时间
	
	
	/**
	 * @return 返回 bl_card_id。
	 */
	public java.lang.String getBl_card_id() {
		return bl_card_id;
	}
	/**
	 * @param bl_card_id 要设置的 bl_card_id。
	 */
	public void setBl_card_id(java.lang.String bl_card_id) {
		this.bl_card_id = bl_card_id;
	}
	/**
	 * @return 返回 bl_card_type。
	 */
	public java.lang.String getBl_card_type() {
		return bl_card_type;
	}
	/**
	 * @param bl_card_type 要设置的 bl_card_type。
	 */
	public void setBl_card_type(java.lang.String bl_card_type) {
		this.bl_card_type = bl_card_type;
	}
	/**
	 * @return 返回 bl_card_type_name。
	 */
	public java.lang.String getBl_card_type_name() {
		return bl_card_type_name;
	}
	/**
	 * @param bl_card_type_name 要设置的 bl_card_type_name。
	 */
	public void setBl_card_type_name(java.lang.String bl_card_type_name) {
		this.bl_card_type_name = bl_card_type_name;
	}
	/**
	 * @return 返回 bl_card_yxq。
	 */
	public java.lang.Integer getBl_card_yxq() {
		return bl_card_yxq;
	}
	/**
	 * @param bl_card_yxq 要设置的 bl_card_yxq。
	 */
	public void setBl_card_yxq(java.lang.Integer bl_card_yxq) {
		this.bl_card_yxq = bl_card_yxq;
	}
	/**
	 * @return 返回 bl_name。
	 */
	public java.lang.String getBl_name() {
		return bl_name;
	}
	/**
	 * @param bl_name 要设置的 bl_name。
	 */
	public void setBl_name(java.lang.String bl_name) {
		this.bl_name = bl_name;
	}
	/**
	 * @return 返回 business。
	 */
	public java.lang.String getBusiness() {
		return business;
	}
	/**
	 * @param business 要设置的 business。
	 */
	public void setBusiness(java.lang.String business) {
		this.business = business;
	}
	/**
	 * @return 返回 capital。
	 */
	public BigDecimal getCapital() {
		return capital;
	}
	/**
	 * @param capital 要设置的 capital。
	 */
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}
	/**
	 * @return 返回 cust_id。
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(java.lang.Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return 返回 cust_name。
	 */
	public java.lang.String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name 要设置的 cust_name。
	 */
	public void setCust_name(java.lang.String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return 返回 fr_name。
	 */
	public java.lang.String getFr_name() {
		return fr_name;
	}
	/**
	 * @param fr_name 要设置的 fr_name。
	 */
	public void setFr_name(java.lang.String fr_name) {
		this.fr_name = fr_name;
	}
	/**
	 * @return 返回 fz_card_id。
	 */
	public java.lang.String getFz_card_id() {
		return fz_card_id;
	}
	/**
	 * @param fz_card_id 要设置的 fz_card_id。
	 */
	public void setFz_card_id(java.lang.String fz_card_id) {
		this.fz_card_id = fz_card_id;
	}
	/**
	 * @return 返回 fz_card_type。
	 */
	public java.lang.String getFz_card_type() {
		return fz_card_type;
	}
	/**
	 * @param fz_card_type 要设置的 fz_card_type。
	 */
	public void setFz_card_type(java.lang.String fz_card_type) {
		this.fz_card_type = fz_card_type;
	}
	/**
	 * @return 返回 fz_card_type_name。
	 */
	public java.lang.String getFz_card_type_name() {
		return fz_card_type_name;
	}
	/**
	 * @param fz_card_type_name 要设置的 fz_card_type_name。
	 */
	public void setFz_card_type_name(java.lang.String fz_card_type_name) {
		this.fz_card_type_name = fz_card_type_name;
	}
	/**
	 * @return 返回 fz_card_yxq。
	 */
	public java.lang.Integer getFz_card_yxq() {
		return fz_card_yxq;
	}
	/**
	 * @param fz_card_yxq 要设置的 fz_card_yxq。
	 */
	public void setFz_card_yxq(java.lang.Integer fz_card_yxq) {
		this.fz_card_yxq = fz_card_yxq;
	}
	/**
	 * @return 返回 gd_card_id。
	 */
	public java.lang.String getGd_card_id() {
		return gd_card_id;
	}
	/**
	 * @param gd_card_id 要设置的 gd_card_id。
	 */
	public void setGd_card_id(java.lang.String gd_card_id) {
		this.gd_card_id = gd_card_id;
	}
	/**
	 * @return 返回 gd_card_type。
	 */
	public java.lang.String getGd_card_type() {
		return gd_card_type;
	}
	/**
	 * @param gd_card_type 要设置的 gd_card_type。
	 */
	public void setGd_card_type(java.lang.String gd_card_type) {
		this.gd_card_type = gd_card_type;
	}
	/**
	 * @return 返回 gd_card_type_name。
	 */
	public java.lang.String getGd_card_type_name() {
		return gd_card_type_name;
	}
	/**
	 * @param gd_card_type_name 要设置的 gd_card_type_name。
	 */
	public void setGd_card_type_name(java.lang.String gd_card_type_name) {
		this.gd_card_type_name = gd_card_type_name;
	}
	/**
	 * @return 返回 gd_card_yxq。
	 */
	public java.lang.Integer getGd_card_yxq() {
		return gd_card_yxq;
	}
	/**
	 * @param gd_card_yxq 要设置的 gd_card_yxq。
	 */
	public void setGd_card_yxq(java.lang.Integer gd_card_yxq) {
		this.gd_card_yxq = gd_card_yxq;
	}
	/**
	 * @return 返回 gd_name。
	 */
	public java.lang.String getGd_name() {
		return gd_name;
	}
	/**
	 * @param gd_name 要设置的 gd_name。
	 */
	public void setGd_name(java.lang.String gd_name) {
		this.gd_name = gd_name;
	}
	/**
	 * @return 返回 gr_nation。
	 */
	public java.lang.String getGr_nation() {
		return gr_nation;
	}
	/**
	 * @param gr_nation 要设置的 gr_nation。
	 */
	public void setGr_nation(java.lang.String gr_nation) {
		this.gr_nation = gr_nation;
	}
	/**
	 * @return 返回 input_man。
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(java.lang.Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 input_time。
	 */
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time 要设置的 input_time。
	 */
	public void setInput_time(java.sql.Timestamp input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return 返回 khfxdj。
	 */
	public java.lang.Integer getKhfxdj() {
		return khfxdj;
	}
	/**
	 * @param khfxdj 要设置的 khfxdj。
	 */
	public void setKhfxdj(java.lang.Integer khfxdj) {
		this.khfxdj = khfxdj;
	}
	/**
	 * @return 返回 link_address。
	 */
	public java.lang.String getLink_address() {
		return link_address;
	}
	/**
	 * @param link_address 要设置的 link_address。
	 */
	public void setLink_address(java.lang.String link_address) {
		this.link_address = link_address;
	}
	/**
	 * @return 返回 link_phone。
	 */
	public java.lang.String getLink_phone() {
		return link_phone;
	}
	/**
	 * @param link_phone 要设置的 link_phone。
	 */
	public void setLink_phone(java.lang.String link_phone) {
		this.link_phone = link_phone;
	}
	/**
	 * @return 返回 modi_man。
	 */
	public java.lang.Integer getModi_man() {
		return modi_man;
	}
	/**
	 * @param modi_man 要设置的 modi_man。
	 */
	public void setModi_man(java.lang.Integer modi_man) {
		this.modi_man = modi_man;
	}
	/**
	 * @return 返回 modi_no。
	 */
	public java.lang.Integer getModi_no() {
		return modi_no;
	}
	/**
	 * @param modi_no 要设置的 modi_no。
	 */
	public void setModi_no(java.lang.Integer modi_no) {
		this.modi_no = modi_no;
	}
	/**
	 * @return 返回 modi_time。
	 */
	public java.sql.Timestamp getModi_time() {
		return modi_time;
	}
	/**
	 * @param modi_time 要设置的 modi_time。
	 */
	public void setModi_time(java.sql.Timestamp modi_time) {
		this.modi_time = modi_time;
	}
	/**
	 * @return 返回 profession。
	 */
	public java.lang.String getProfession() {
		return profession;
	}
	/**
	 * @param profession 要设置的 profession。
	 */
	public void setProfession(java.lang.String profession) {
		this.profession = profession;
	}
	/**
	 * @return 返回 property_source。
	 */
	public java.lang.String getProperty_source() {
		return property_source;
	}
	/**
	 * @param property_source 要设置的 property_source。
	 */
	public void setProperty_source(java.lang.String property_source) {
		this.property_source = property_source;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public java.lang.Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(java.lang.Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 survey_date。
	 */
	public java.lang.Integer getSurvey_date() {
		return survey_date;
	}
	/**
	 * @param survey_date 要设置的 survey_date。
	 */
	public void setSurvey_date(java.lang.Integer survey_date) {
		this.survey_date = survey_date;
	}
	/**
	 * @return 返回 survey_man。
	 */
	public java.lang.String getSurvey_man() {
		return survey_man;
	}
	/**
	 * @param survey_man 要设置的 survey_man。
	 */
	public void setSurvey_man(java.lang.String survey_man) {
		this.survey_man = survey_man;
	}
	/**
	 * @return 返回 swdjhm。
	 */
	public java.lang.String getSwdjhm() {
		return swdjhm;
	}
	/**
	 * @param swdjhm 要设置的 swdjhm。
	 */
	public void setSwdjhm(java.lang.String swdjhm) {
		this.swdjhm = swdjhm;
	}
}
