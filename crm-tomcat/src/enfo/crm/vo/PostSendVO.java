/*
 * 创建日期 2009-11-30
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @autho jinchongwei
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PostSendVO {
	private Integer input_date; // 邮寄日期
	private Integer product_id;
	private String contract_sub_bh; // 合同编号
	private String post_no; // 邮寄单号
	private String post_content; // 邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
	private String summary; // 备注
	private String product_name;
	private Integer input_man;
	private Integer serial_no;
	private String cust_name;

	

	/**
	 * @return 返回 product_name。
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name 要设置的 product_name。
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return 返回 contract_sub_bh。
	 */
	public String getContract_sub_bh() {
		return contract_sub_bh;
	}
	/**
	 * @param contract_sub_bh 要设置的 contract_sub_bh。
	 */
	public void setContract_sub_bh(String contract_sub_bh) {
		this.contract_sub_bh = contract_sub_bh;
	}
	/**
	 * @return 返回 input_date。
	 */
	public Integer getInput_date() {
		return input_date;
	}
	/**
	 * @param input_date 要设置的 input_date。
	 */
	public void setInput_date(Integer input_date) {
		this.input_date = input_date;
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
	 * @return 返回 post_content。
	 */
	public String getPost_content() {
		return post_content;
	}
	/**
	 * @param post_content 要设置的 post_content。
	 */
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	/**
	 * @return 返回 post_no。
	 */
	public String getPost_no() {
		return post_no;
	}
	/**
	 * @param post_no 要设置的 post_no。
	 */
	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}
	/**
	 * @return 返回 product_id。
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id 要设置的 product_id。
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 cust_name。
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name 要设置的 cust_name。
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
}
