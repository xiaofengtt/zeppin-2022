/*
 * 创建日期 2016-3-1
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
public class CustActivityVo {
	private Integer cust_id;
	private Integer activity_id;
	private Integer score_date;
	private Integer score;
	private Integer input_man;
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
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
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
	 * @return 返回 score_date。
	 */
	public Integer getScore_date() {
		return score_date;
	}
	/**
	 * @param score_date 要设置的 score_date。
	 */
	public void setScore_date(Integer score_date) {
		this.score_date = score_date;
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
