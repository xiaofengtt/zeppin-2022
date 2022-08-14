/*
 * 创建日期 2011-8-11
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
public class TcoProductVO {
	
	private Integer coProduct_id;
	private String coProduct_name;
	private Integer team_id;
	private Integer coProduct_manager;
	private Integer publish_date;
	private String  coProduct_type;
	private String coProduct_type_name;
	private String selfMade_type;
	private String selfMade_type_name;
	private String coProduct_summary;
	private BigDecimal coProduct_price;
	private Integer input_man;
	private Integer input_time;
	private Integer check_flag;
	private Integer check_man;
	private Integer check_date;
	
	
	/**
	 * @return 返回 check_date。
	 */
	public Integer getCheck_date() {
		return check_date;
	}
	/**
	 * @param check_date 要设置的 check_date。
	 */
	public void setCheck_date(Integer check_date) {
		this.check_date = check_date;
	}
	/**
	 * @return 返回 check_flag。
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}
	/**
	 * @param check_flag 要设置的 check_flag。
	 */
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/**
	 * @return 返回 check_man。
	 */
	public Integer getCheck_man() {
		return check_man;
	}
	/**
	 * @param check_man 要设置的 check_man。
	 */
	public void setCheck_man(Integer check_man) {
		this.check_man = check_man;
	}
	/**
	 * @return 返回 coProduct_id。
	 */
	public Integer getCoProduct_id() {
		return coProduct_id;
	}
	/**
	 * @param coProduct_id 要设置的 coProduct_id。
	 */
	public void setCoProduct_id(Integer coProduct_id) {
		this.coProduct_id = coProduct_id;
	}
	/**
	 * @return 返回 coProduct_manager。
	 */
	public Integer getCoProduct_manager() {
		return coProduct_manager;
	}
	/**
	 * @param coProduct_manager 要设置的 coProduct_manager。
	 */
	public void setCoProduct_manager(Integer coProduct_manager) {
		this.coProduct_manager = coProduct_manager;
	}
	/**
	 * @return 返回 coProduct_name。
	 */
	public String getCoProduct_name() {
		return coProduct_name;
	}
	/**
	 * @param coProduct_name 要设置的 coProduct_name。
	 */
	public void setCoProduct_name(String coProduct_name) {
		this.coProduct_name = coProduct_name;
	}
	/**
	 * @return 返回 coProduct_price。
	 */
	public BigDecimal getCoProduct_price() {
		return coProduct_price;
	}
	/**
	 * @param coProduct_price 要设置的 coProduct_price。
	 */
	public void setCoProduct_price(BigDecimal coProduct_price) {
		this.coProduct_price = coProduct_price;
	}
	/**
	 * @return 返回 coProduct_summary。
	 */
	public String getCoProduct_summary() {
		return coProduct_summary;
	}
	/**
	 * @param coProduct_summary 要设置的 coProduct_summary。
	 */
	public void setCoProduct_summary(String coProduct_summary) {
		this.coProduct_summary = coProduct_summary;
	}
	/**
	 * @return 返回 coProduct_type。
	 */
	public String getCoProduct_type() {
		return coProduct_type;
	}
	/**
	 * @param coProduct_type 要设置的 coProduct_type。
	 */
	public void setCoProduct_type(String coProduct_type) {
		this.coProduct_type = coProduct_type;
	}
	/**
	 * @return 返回 coProduct_type_name。
	 */
	public String getCoProduct_type_name() {
		return coProduct_type_name;
	}
	/**
	 * @param coProduct_type_name 要设置的 coProduct_type_name。
	 */
	public void setCoProduct_type_name(String coProduct_type_name) {
		this.coProduct_type_name = coProduct_type_name;
	}
	
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer teamId) {
		team_id = teamId;
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
	 * @return 返回 input_time。
	 */
	public Integer getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time 要设置的 input_time。
	 */
	public void setInput_time(Integer input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return 返回 publish_date。
	 */
	public Integer getPublish_date() {
		return publish_date;
	}
	/**
	 * @param publish_date 要设置的 publish_date。
	 */
	public void setPublish_date(Integer publish_date) {
		this.publish_date = publish_date;
	}
	/**
	 * @return 返回 selfMade_type。
	 */
	public String getSelfMade_type() {
		return selfMade_type;
	}
	/**
	 * @param selfMade_type 要设置的 selfMade_type。
	 */
	public void setSelfMade_type(String selfMade_type) {
		this.selfMade_type = selfMade_type;
	}
	/**
	 * @return 返回 selfMade_type_name。
	 */
	public String getSelfMade_type_name() {
		return selfMade_type_name;
	}
	/**
	 * @param selfMade_type_name 要设置的 selfMade_type_name。
	 */
	public void setSelfMade_type_name(String selfMade_type_name) {
		this.selfMade_type_name = selfMade_type_name;
	}
}
