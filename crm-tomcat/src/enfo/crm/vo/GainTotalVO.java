/*
 * 创建日期 2013-5-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

/**
 * @author jincw
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class GainTotalVO {
	private java.lang.Integer book_code;

    private java.lang.Integer product_id;
    
    private java.lang.Integer sub_product_id;

    private java.lang.String sy_type;

    private java.lang.String sy_type_name;

    private java.math.BigDecimal before_price;

    private java.math.BigDecimal sy_rate;

    private java.math.BigDecimal sy_money;

    private java.math.BigDecimal un_money;

    private java.math.BigDecimal after_price;

    private java.lang.Integer sy_date;

    private java.lang.Integer input_man;

    /////////yingxj 20050223
    private java.lang.String prov_level;

    private java.lang.String prov_level_name; //受益级别
    
    
	/**
	 * @return 返回 after_price。
	 */
	public java.math.BigDecimal getAfter_price() {
		return after_price;
	}
	/**
	 * @param after_price 要设置的 after_price。
	 */
	public void setAfter_price(java.math.BigDecimal after_price) {
		this.after_price = after_price;
	}
	/**
	 * @return 返回 before_price。
	 */
	public java.math.BigDecimal getBefore_price() {
		return before_price;
	}
	/**
	 * @param before_price 要设置的 before_price。
	 */
	public void setBefore_price(java.math.BigDecimal before_price) {
		this.before_price = before_price;
	}
	/**
	 * @return 返回 book_code。
	 */
	public java.lang.Integer getBook_code() {
		return book_code;
	}
	/**
	 * @param book_code 要设置的 book_code。
	 */
	public void setBook_code(java.lang.Integer book_code) {
		this.book_code = book_code;
	}
	/**
	 * @return 返回 input_man。
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(java.lang.Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 product_id。
	 */
	public java.lang.Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id 要设置的 product_id。
	 */
	public void setProduct_id(java.lang.Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return 返回 prov_level。
	 */
	public java.lang.String getProv_level() {
		return prov_level;
	}
	/**
	 * @param prov_level 要设置的 prov_level。
	 */
	public void setProv_level(java.lang.String prov_level) {
		this.prov_level = prov_level;
	}
	/**
	 * @return 返回 prov_level_name。
	 */
	public java.lang.String getProv_level_name() {
		return prov_level_name;
	}
	/**
	 * @param prov_level_name 要设置的 prov_level_name。
	 */
	public void setProv_level_name(java.lang.String prov_level_name) {
		this.prov_level_name = prov_level_name;
	}
	/**
	 * @return 返回 sub_product_id。
	 */
	public java.lang.Integer getSub_product_id() {
		return sub_product_id;
	}
	/**
	 * @param sub_product_id 要设置的 sub_product_id。
	 */
	public void setSub_product_id(java.lang.Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
	/**
	 * @return 返回 sy_date。
	 */
	public java.lang.Integer getSy_date() {
		return sy_date;
	}
	/**
	 * @param sy_date 要设置的 sy_date。
	 */
	public void setSy_date(java.lang.Integer sy_date) {
		this.sy_date = sy_date;
	}
	/**
	 * @return 返回 sy_money。
	 */
	public java.math.BigDecimal getSy_money() {
		return sy_money;
	}
	/**
	 * @param sy_money 要设置的 sy_money。
	 */
	public void setSy_money(java.math.BigDecimal sy_money) {
		this.sy_money = sy_money;
	}
	/**
	 * @return 返回 sy_rate。
	 */
	public java.math.BigDecimal getSy_rate() {
		return sy_rate;
	}
	/**
	 * @param sy_rate 要设置的 sy_rate。
	 */
	public void setSy_rate(java.math.BigDecimal sy_rate) {
		this.sy_rate = sy_rate;
	}
	/**
	 * @return 返回 sy_type。
	 */
	public java.lang.String getSy_type() {
		return sy_type;
	}
	/**
	 * @param sy_type 要设置的 sy_type。
	 */
	public void setSy_type(java.lang.String sy_type) {
		this.sy_type = sy_type;
	}
	/**
	 * @return 返回 sy_type_name。
	 */
	public java.lang.String getSy_type_name() {
		return sy_type_name;
	}
	/**
	 * @param sy_type_name 要设置的 sy_type_name。
	 */
	public void setSy_type_name(java.lang.String sy_type_name) {
		this.sy_type_name = sy_type_name;
	}
	/**
	 * @return 返回 un_money。
	 */
	public java.math.BigDecimal getUn_money() {
		return un_money;
	}
	/**
	 * @param un_money 要设置的 un_money。
	 */
	public void setUn_money(java.math.BigDecimal un_money) {
		this.un_money = un_money;
	}
}
