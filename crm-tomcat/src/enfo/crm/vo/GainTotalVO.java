/*
 * �������� 2013-5-16
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author jincw
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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

    private java.lang.String prov_level_name; //���漶��
    
    
	/**
	 * @return ���� after_price��
	 */
	public java.math.BigDecimal getAfter_price() {
		return after_price;
	}
	/**
	 * @param after_price Ҫ���õ� after_price��
	 */
	public void setAfter_price(java.math.BigDecimal after_price) {
		this.after_price = after_price;
	}
	/**
	 * @return ���� before_price��
	 */
	public java.math.BigDecimal getBefore_price() {
		return before_price;
	}
	/**
	 * @param before_price Ҫ���õ� before_price��
	 */
	public void setBefore_price(java.math.BigDecimal before_price) {
		this.before_price = before_price;
	}
	/**
	 * @return ���� book_code��
	 */
	public java.lang.Integer getBook_code() {
		return book_code;
	}
	/**
	 * @param book_code Ҫ���õ� book_code��
	 */
	public void setBook_code(java.lang.Integer book_code) {
		this.book_code = book_code;
	}
	/**
	 * @return ���� input_man��
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(java.lang.Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� product_id��
	 */
	public java.lang.Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id Ҫ���õ� product_id��
	 */
	public void setProduct_id(java.lang.Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return ���� prov_level��
	 */
	public java.lang.String getProv_level() {
		return prov_level;
	}
	/**
	 * @param prov_level Ҫ���õ� prov_level��
	 */
	public void setProv_level(java.lang.String prov_level) {
		this.prov_level = prov_level;
	}
	/**
	 * @return ���� prov_level_name��
	 */
	public java.lang.String getProv_level_name() {
		return prov_level_name;
	}
	/**
	 * @param prov_level_name Ҫ���õ� prov_level_name��
	 */
	public void setProv_level_name(java.lang.String prov_level_name) {
		this.prov_level_name = prov_level_name;
	}
	/**
	 * @return ���� sub_product_id��
	 */
	public java.lang.Integer getSub_product_id() {
		return sub_product_id;
	}
	/**
	 * @param sub_product_id Ҫ���õ� sub_product_id��
	 */
	public void setSub_product_id(java.lang.Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
	/**
	 * @return ���� sy_date��
	 */
	public java.lang.Integer getSy_date() {
		return sy_date;
	}
	/**
	 * @param sy_date Ҫ���õ� sy_date��
	 */
	public void setSy_date(java.lang.Integer sy_date) {
		this.sy_date = sy_date;
	}
	/**
	 * @return ���� sy_money��
	 */
	public java.math.BigDecimal getSy_money() {
		return sy_money;
	}
	/**
	 * @param sy_money Ҫ���õ� sy_money��
	 */
	public void setSy_money(java.math.BigDecimal sy_money) {
		this.sy_money = sy_money;
	}
	/**
	 * @return ���� sy_rate��
	 */
	public java.math.BigDecimal getSy_rate() {
		return sy_rate;
	}
	/**
	 * @param sy_rate Ҫ���õ� sy_rate��
	 */
	public void setSy_rate(java.math.BigDecimal sy_rate) {
		this.sy_rate = sy_rate;
	}
	/**
	 * @return ���� sy_type��
	 */
	public java.lang.String getSy_type() {
		return sy_type;
	}
	/**
	 * @param sy_type Ҫ���õ� sy_type��
	 */
	public void setSy_type(java.lang.String sy_type) {
		this.sy_type = sy_type;
	}
	/**
	 * @return ���� sy_type_name��
	 */
	public java.lang.String getSy_type_name() {
		return sy_type_name;
	}
	/**
	 * @param sy_type_name Ҫ���õ� sy_type_name��
	 */
	public void setSy_type_name(java.lang.String sy_type_name) {
		this.sy_type_name = sy_type_name;
	}
	/**
	 * @return ���� un_money��
	 */
	public java.math.BigDecimal getUn_money() {
		return un_money;
	}
	/**
	 * @param un_money Ҫ���õ� un_money��
	 */
	public void setUn_money(java.math.BigDecimal un_money) {
		this.un_money = un_money;
	}
}
