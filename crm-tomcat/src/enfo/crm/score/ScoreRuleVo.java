/*
 * 创建日期 2016-2-16
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
public class ScoreRuleVo {
	private Integer rule_id;
	private Integer more_amount;
	private BigDecimal unitscore;
	private BigDecimal dayscore;
	private String summary;

	/**
	 * @return 返回 dayscore。
	 */
	public BigDecimal getDayscore() {
		return dayscore;
	}
	/**
	 * @param dayscore 要设置的 dayscore。
	 */
	public void setDayscore(BigDecimal dayscore) {
		this.dayscore = dayscore;
	}
	/**
	 * @return 返回 more_amount。
	 */
	public Integer getMore_amount() {
		return more_amount;
	}
	/**
	 * @param more_amount 要设置的 more_amount。
	 */
	public void setMore_amount(Integer more_amount) {
		this.more_amount = more_amount;
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
	 * @return 返回 unitscore。
	 */
	public BigDecimal getUnitscore() {
		return unitscore;
	}
	/**
	 * @param unitscore 要设置的 unitscore。
	 */
	public void setUnitscore(BigDecimal unitscore) {
		this.unitscore = unitscore;
	}
}
