/*
 * �������� 2016-3-1
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
public class CustActivityVo {
	private Integer cust_id;
	private Integer activity_id;
	private Integer score_date;
	private Integer score;
	private Integer input_man;
	private String summary;
	/**
	 * @return ���� activity_id��
	 */
	public Integer getActivity_id() {
		return activity_id;
	}
	/**
	 * @param activity_id Ҫ���õ� activity_id��
	 */
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
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
	 * @return ���� score_date��
	 */
	public Integer getScore_date() {
		return score_date;
	}
	/**
	 * @param score_date Ҫ���õ� score_date��
	 */
	public void setScore_date(Integer score_date) {
		this.score_date = score_date;
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
