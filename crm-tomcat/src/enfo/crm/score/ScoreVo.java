/*
 * �������� 2016-1-28
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
public class ScoreVo {
	
	private Integer custId;
	private String custName;
	
	private Integer score;
	private Integer input_man;
	private String summary;
	
	/**
	 * @return ���� custId��
	 */
	public Integer getCustId() {
		return custId;
	}
	/**
	 * @param custId Ҫ���õ� custId��
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	/**
	 * @return ���� custName��
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName Ҫ���õ� custName��
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
