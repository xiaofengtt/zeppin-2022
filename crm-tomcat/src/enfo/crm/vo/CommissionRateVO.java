/*
 * �������� 2013-6-7
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class CommissionRateVO {   
    private Integer productId;
    private Integer subproductId; 
    private Integer period; // ����ֵ
    private Integer periodUnit; // 0������ 1�� 2�� 3��
    private Integer tradeStartMoney; // ��ͬ�ݶ�����
    private Integer tradeEndMoney; // ��ͬ�ݶ�����
    private BigDecimal feeRate; // ��ɱ���
    private BigDecimal feeAmount; // ��ɹ̶����
    private String summary;
    private Integer inputMan;
    
    
	/**
	 * @return ���� feeAmount��
	 */
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	/**
	 * @param feeAmount Ҫ���õ� feeAmount��
	 */
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	/**
	 * @return ���� feeRate��
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	/**
	 * @param feeRate Ҫ���õ� feeRate��
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
	/**
	 * @return ���� inputMan��
	 */
	public Integer getInputMan() {
		return inputMan;
	}
	/**
	 * @param inputMan Ҫ���õ� inputMan��
	 */
	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}
	/**
	 * @return ���� period��
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period Ҫ���õ� period��
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return ���� periodUnit��
	 */
	public Integer getPeriodUnit() {
		return periodUnit;
	}
	/**
	 * @param periodUnit Ҫ���õ� periodUnit��
	 */
	public void setPeriodUnit(Integer periodUnit) {
		this.periodUnit = periodUnit;
	}
	/**
	 * @return ���� productId��
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId Ҫ���õ� productId��
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return ���� subproductId��
	 */
	public Integer getSubproductId() {
		return subproductId;
	}
	/**
	 * @param subproductId Ҫ���õ� subproductId��
	 */
	public void setSubproductId(Integer subproductId) {
		this.subproductId = subproductId;
	}
	/**
	 * @return ���� summary��
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary Ҫ���õ� summary��
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return ���� tradeEndMoney��
	 */
	public Integer getTradeEndMoney() {
		return tradeEndMoney;
	}
	/**
	 * @param tradeEndMoney Ҫ���õ� tradeEndMoney��
	 */
	public void setTradeEndMoney(Integer tradeEndMoney) {
		this.tradeEndMoney = tradeEndMoney;
	}
	/**
	 * @return ���� tradeStartMoney��
	 */
	public Integer getTradeStartMoney() {
		return tradeStartMoney;
	}
	/**
	 * @param tradeStartMoney Ҫ���õ� tradeStartMoney��
	 */
	public void setTradeStartMoney(Integer tradeStartMoney) {
		this.tradeStartMoney = tradeStartMoney;
	}
}
