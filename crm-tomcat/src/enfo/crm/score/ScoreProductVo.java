/*
 * �������� 2016-2-29
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.score;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ScoreProductVo {
	private Integer rule_id;
	private Integer product_id;
	private Integer subproduct_id;
	private BigDecimal score_rate;
	private String summary;
	/**
	 * @return ���� product_id��
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id Ҫ���õ� product_id��
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return ���� subproduct_id��
	 */
	public Integer getSubproduct_id() {
		return subproduct_id;
	}
	/**
	 * @param subproduct_id Ҫ���õ� subproduct_id��
	 */
	public void setSubproduct_id(Integer subproduct_id) {
		this.subproduct_id = subproduct_id;
	}
	/**
	 * @return ���� rule_id��
	 */
	public Integer getRule_id() {
		return rule_id;
	}
	/**
	 * @param rule_id Ҫ���õ� rule_id��
	 */
	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}
	/**
	 * @return ���� score_rate��
	 */
	public BigDecimal getScore_rate() {
		return score_rate;
	}
	/**
	 * @param score_rate Ҫ���õ� score_rate��
	 */
	public void setScore_rate(BigDecimal score_rate) {
		this.score_rate = score_rate;
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
}
