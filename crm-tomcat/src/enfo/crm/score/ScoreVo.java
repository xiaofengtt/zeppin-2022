/*
 * 创建日期 2016-1-28
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
public class ScoreVo {
	
	private Integer custId;
	private String custName;
	
	private Integer score;
	private Integer input_man;
	private String summary;
	
	/**
	 * @return 返回 custId。
	 */
	public Integer getCustId() {
		return custId;
	}
	/**
	 * @param custId 要设置的 custId。
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	/**
	 * @return 返回 custName。
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName 要设置的 custName。
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
