/*
 * 创建日期 2016-2-24
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.score;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
	 * @return 返回 activity_id。
	 */
	public Integer getActivity_id() {
		return activity_id;
	}
	/**
	 * @param activity_id 要设置的 activity_id。
	 */
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	/**
	 * @return 返回 activity_status。
	 */
	public Integer getActivity_status() {
		return activity_status;
	}
	/**
	 * @param activity_status 要设置的 activity_status。
	 */
	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
	}
	/**
	 * @return 返回 date_begin。
	 */
	public Integer getDate_begin() {
		return date_begin;
	}
	/**
	 * @param date_begin 要设置的 date_begin。
	 */
	public void setDate_begin(Integer date_begin) {
		this.date_begin = date_begin;
	}
	/**
	 * @return 返回 date_end。
	 */
	public Integer getDate_end() {
		return date_end;
	}
	/**
	 * @param date_end 要设置的 date_end。
	 */
	public void setDate_end(Integer date_end) {
		this.date_end = date_end;
	}
	/**
	 * @return 返回 df_activity_id。
	 */
	public Integer getDf_activity_id() {
		return df_activity_id;
	}
	/**
	 * @param df_activity_id 要设置的 df_activity_id。
	 */
	public void setDf_activity_id(Integer df_activity_id) {
		this.df_activity_id = df_activity_id;
	}
	/**
	 * @return 返回 score。
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score 要设置的 score。
	 */
	public void setScore(Integer score) {
		this.score = score;
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
