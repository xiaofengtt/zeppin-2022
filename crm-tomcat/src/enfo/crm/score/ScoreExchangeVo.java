/*
 * �������� 2016-2-29
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.score;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ScoreExchangeVo {
	private Integer cust_id;
	private Integer exchange_date;
	private Integer list_id;
	private Integer score;
	private Integer input_man;
	private String summary;
	/**
	 * @return ���� cust_id��
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� exchange_date��
	 */
	public Integer getExchange_date() {
		return exchange_date;
	}
	/**
	 * @param exchange_date Ҫ���õ� exchange_date��
	 */
	public void setExchange_date(Integer exchange_date) {
		this.exchange_date = exchange_date;
	}
	/**
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� list_id��
	 */
	public Integer getList_id() {
		return list_id;
	}
	/**
	 * @param list_id Ҫ���õ� list_id��
	 */
	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}
	/**
	 * @return ���� score��
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score Ҫ���õ� score��
	 */
	public void setScore(Integer score) {
		this.score = score;
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
