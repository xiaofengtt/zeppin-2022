/*
 * �������� 2016-2-16
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
public class ScoreRuleVo {
	private Integer rule_id;
	private Integer more_amount;
	private BigDecimal unitscore;
	private BigDecimal dayscore;
	private String summary;

	/**
	 * @return ���� dayscore��
	 */
	public BigDecimal getDayscore() {
		return dayscore;
	}
	/**
	 * @param dayscore Ҫ���õ� dayscore��
	 */
	public void setDayscore(BigDecimal dayscore) {
		this.dayscore = dayscore;
	}
	/**
	 * @return ���� more_amount��
	 */
	public Integer getMore_amount() {
		return more_amount;
	}
	/**
	 * @param more_amount Ҫ���õ� more_amount��
	 */
	public void setMore_amount(Integer more_amount) {
		this.more_amount = more_amount;
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
	 * @return ���� unitscore��
	 */
	public BigDecimal getUnitscore() {
		return unitscore;
	}
	/**
	 * @param unitscore Ҫ���õ� unitscore��
	 */
	public void setUnitscore(BigDecimal unitscore) {
		this.unitscore = unitscore;
	}
}
