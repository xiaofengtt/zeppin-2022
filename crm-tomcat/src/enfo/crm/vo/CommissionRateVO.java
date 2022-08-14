/*
 * 创建日期 2013-6-7
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
public class CommissionRateVO {   
    private Integer productId;
    private Integer subproductId; 
    private Integer period; // 期限值
    private Integer periodUnit; // 0无期限 1天 2月 3年
    private Integer tradeStartMoney; // 合同份额下限
    private Integer tradeEndMoney; // 合同份额上限
    private BigDecimal feeRate; // 提成比例
    private BigDecimal feeAmount; // 提成固定金额
    private String summary;
    private Integer inputMan;
    
    
	/**
	 * @return 返回 feeAmount。
	 */
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @param feeAmount 要设置的 feeAmount。
	 */
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	/**
	 * @return 返回 feeRate。
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	/**
	 * @param feeRate 要设置的 feeRate。
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	/**
	 * @return 返回 inputMan。
	 */
	public Integer getInputMan() {
		return inputMan;
	}
	/**
	 * @param inputMan 要设置的 inputMan。
	 */
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	/**
	 * @return 返回 period。
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period 要设置的 period。
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return 返回 periodUnit。
	 */
	public Integer getPeriodUnit() {
		return periodUnit;
	}
	/**
	 * @param periodUnit 要设置的 periodUnit。
	 */
	public void setPeriodUnit(Integer periodUnit) {
		this.periodUnit = periodUnit;
	}
	/**
	 * @return 返回 productId。
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId 要设置的 productId。
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return 返回 subproductId。
	 */
	public Integer getSubproductId() {
		return subproductId;
	}
	/**
	 * @param subproductId 要设置的 subproductId。
	 */
	public void setSubproductId(Integer subproductId) {
		this.subproductId = subproductId;
	}
	/**
	 * @return 返回 summary。
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary 要设置的 summary。
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return 返回 tradeEndMoney。
	 */
	public Integer getTradeEndMoney() {
		return tradeEndMoney;
	}
	/**
	 * @param tradeEndMoney 要设置的 tradeEndMoney。
	 */
	public void setTradeEndMoney(Integer tradeEndMoney) {
		this.tradeEndMoney = tradeEndMoney;
	}
	/**
	 * @return 返回 tradeStartMoney。
	 */
	public Integer getTradeStartMoney() {
		return tradeStartMoney;
	}
	/**
	 * @param tradeStartMoney 要设置的 tradeStartMoney。
	 */
	public void setTradeStartMoney(Integer tradeStartMoney) {
		this.tradeStartMoney = tradeStartMoney;
	}
}
