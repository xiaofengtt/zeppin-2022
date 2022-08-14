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
public class TcoMaintainDetailVO {
	private Integer maintainDetail_id;
	private Integer maintain_id;
	private Integer coContract_id;
	private String main_pro_name;
	private BigDecimal   xm_ht_money;
	private BigDecimal 	 main_rate;
	private BigDecimal 	 main_money;
	private Integer input_man;
	private Integer input_time;
	private Integer coProduct_id;
	private String  coProduct_name;
	
	public Integer getCoProduct_id() {
		return coProduct_id;
	}
	public void setCoProduct_id(Integer coProductId) {
		coProduct_id = coProductId;
	}
	public String getCoProduct_name() {
		return coProduct_name;
	}
	public void setCoProduct_name(String coProductName) {
		coProduct_name = coProductName;
	}
	public Integer getMaintainDetail_id() {
		return maintainDetail_id;
	}
	public void setMaintainDetail_id(Integer maintainDetailId) {
		maintainDetail_id = maintainDetailId;
	}
	public Integer getMaintain_id() {
		return maintain_id;
	}
	public void setMaintain_id(Integer maintainId) {
		maintain_id = maintainId;
	}
	public Integer getCoContract_id() {
		return coContract_id;
	}
	public void setCoContract_id(Integer coContractId) {
		coContract_id = coContractId;
	}
	public String getMain_pro_name() {
		return main_pro_name;
	}
	public void setMain_pro_name(String mainProName) {
		main_pro_name = mainProName;
	}
	public BigDecimal getXm_ht_money() {
		return xm_ht_money;
	}
	public void setXm_ht_money(BigDecimal xmHtMoney) {
		xm_ht_money = xmHtMoney;
	}
	public BigDecimal getMain_rate() {
		return main_rate;
	}
	public void setMain_rate(BigDecimal mainRate) {
		main_rate = mainRate;
	}
	public BigDecimal getMain_money() {
		return main_money;
	}
	public void setMain_money(BigDecimal mainMoney) {
		main_money = mainMoney;
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
	
}
