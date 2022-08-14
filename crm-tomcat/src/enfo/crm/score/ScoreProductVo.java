/*
 * 创建日期 2016-2-29
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.score;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ScoreProductVo {
	private Integer rule_id;
	private Integer product_id;
	private Integer subproduct_id;
	private BigDecimal score_rate;
	private String summary;
	/**
	 * @return 返回 product_id。
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id 要设置的 product_id。
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return 返回 subproduct_id。
	 */
	public Integer getSubproduct_id() {
		return subproduct_id;
	}
	/**
	 * @param subproduct_id 要设置的 subproduct_id。
	 */
	public void setSubproduct_id(Integer subproduct_id) {
		this.subproduct_id = subproduct_id;
	}
	/**
	 * @return 返回 rule_id。
	 */
	public Integer getRule_id() {
		return rule_id;
	}
	/**
	 * @param rule_id 要设置的 rule_id。
	 */
	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}
	/**
	 * @return 返回 score_rate。
	 */
	public BigDecimal getScore_rate() {
		return score_rate;
	}
	/**
	 * @param score_rate 要设置的 score_rate。
	 */
	public void setScore_rate(BigDecimal score_rate) {
		this.score_rate = score_rate;
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
}
