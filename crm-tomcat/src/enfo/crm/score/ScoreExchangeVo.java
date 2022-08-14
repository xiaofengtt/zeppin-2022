/*
 * 创建日期 2016-2-29
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
public class ScoreExchangeVo {
	private Integer cust_id;
	private Integer exchange_date;
	private Integer list_id;
	private Integer score;
	private Integer input_man;
	private String summary;
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
	 * @return 返回 exchange_date。
	 */
	public Integer getExchange_date() {
		return exchange_date;
	}
	/**
	 * @param exchange_date 要设置的 exchange_date。
	 */
	public void setExchange_date(Integer exchange_date) {
		this.exchange_date = exchange_date;
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
	 * @return 返回 list_id。
	 */
	public Integer getList_id() {
		return list_id;
	}
	/**
	 * @param list_id 要设置的 list_id。
	 */
	public void setList_id(Integer list_id) {
		this.list_id = list_id;
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
