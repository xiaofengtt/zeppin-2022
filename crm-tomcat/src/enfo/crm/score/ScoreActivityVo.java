/*
 * �������� 2016-2-24
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
public class ScoreActivityVo {
	private Integer activity_id;
	private Integer score;
	private Integer date_begin;
	private Integer date_end;
	private Integer activity_status;
	private Integer df_activity_id;
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
	 * @return ���� activity_status��
	 */
	public Integer getActivity_status() {
		return activity_status;
	}
	/**
	 * @param activity_status Ҫ���õ� activity_status��
	 */
	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
	}
	/**
	 * @return ���� date_begin��
	 */
	public Integer getDate_begin() {
		return date_begin;
	}
	/**
	 * @param date_begin Ҫ���õ� date_begin��
	 */
	public void setDate_begin(Integer date_begin) {
		this.date_begin = date_begin;
	}
	/**
	 * @return ���� date_end��
	 */
	public Integer getDate_end() {
		return date_end;
	}
	/**
	 * @param date_end Ҫ���õ� date_end��
	 */
	public void setDate_end(Integer date_end) {
		this.date_end = date_end;
	}
	/**
	 * @return ���� df_activity_id��
	 */
	public Integer getDf_activity_id() {
		return df_activity_id;
	}
	/**
	 * @param df_activity_id Ҫ���õ� df_activity_id��
	 */
	public void setDf_activity_id(Integer df_activity_id) {
		this.df_activity_id = df_activity_id;
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
