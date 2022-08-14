/*
 * 创建日期 2010-6-15
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author taochen
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ToMoneyAccountVO {
    private Integer book_code = new Integer(0); //	Int N 帐套代码
    private Integer serial_no = new Integer(0); //	Int N 序号、自动生成
    private Integer product_id = new Integer(0); //	Int N 产品ID号
    private String contract_bh = ""; //VARCHAR 16 N 合同编号（已经修改为合同序号）
    private String contract_sub_bh = "";//VARCHAR 50 合同编号
    private Integer cust_id = new Integer(0); //CUST_ID INT N 客户ID
    private String cust_name = ""; //CUST_NAME VARCHAR 100 N 客户名称
    private Integer dz_date; //	DATE N 到帐日期/起息日期
    private Integer end_date; //	DATE N
    private BigDecimal to_money = new BigDecimal(0); //	Money N 到帐资金/计息金额
    private BigDecimal to_amount = new BigDecimal(0); //	Float N 新增份额
    private String jk_type = ""; //	VARCHAR 10 N 缴款方式(1114)
    private String jk_type_name = "";
    private Integer check_flag = new Integer(0);
    private String sub_code = "";
    private Integer input_man = new Integer(0); //	Int N 录入人员
    private Integer to_bank_date = new Integer(0); //	Int N 录入人员
    private Integer pz_flag = new Integer(0); //	Int N 录入人员
    private BigDecimal fee = new BigDecimal(0); //	Float N 费用
    private String product_name;
    //GEQI20051109
    private BigDecimal bond1;//发行期利息
    private BigDecimal bond2;//发行期实付利息发行期利息税
    private BigDecimal bond_tax;//
    private Integer js_date;
    private Integer bond_date;
    private Integer bond_days;
    private java.math.BigDecimal max_to_money;
    private java.math.BigDecimal min_to_money;
    private String group_id;
    private String group_id_out;
    private Integer post_flag;
    private java.math.BigDecimal fee_money;
    private Integer fee_type;//1认购，2申购，3赎回，4份额转增
    private String summary;
    private Integer sbf_serial_no;
    
    
    
    
	/**
	 * @return 返回 sbf_serial_no。
	 */
	public Integer getSbf_serial_no() {
		return sbf_serial_no;
	}
	/**
	 * @param sbf_serial_no 要设置的 sbf_serial_no。
	 */
	public void setSbf_serial_no(Integer sbf_serial_no) {
		this.sbf_serial_no = sbf_serial_no;
	}
	/**
	 * @return 返回 bond_date。
	 */
	public Integer getBond_date() {
		return bond_date;
	}
	/**
	 * @param bond_date 要设置的 bond_date。
	 */
	public void setBond_date(Integer bond_date) {
		this.bond_date = bond_date;
	}
	/**
	 * @return 返回 bond_days。
	 */
	public Integer getBond_days() {
		return bond_days;
	}
	/**
	 * @param bond_days 要设置的 bond_days。
	 */
	public void setBond_days(Integer bond_days) {
		this.bond_days = bond_days;
	}
	/**
	 * @return 返回 bond_tax。
	 */
	public BigDecimal getBond_tax() {
		return bond_tax;
	}
	/**
	 * @param bond_tax 要设置的 bond_tax。
	 */
	public void setBond_tax(BigDecimal bond_tax) {
		this.bond_tax = bond_tax;
	}
	/**
	 * @return 返回 bond1。
	 */
	public BigDecimal getBond1() {
		return bond1;
	}
	/**
	 * @param bond1 要设置的 bond1。
	 */
	public void setBond1(BigDecimal bond1) {
		this.bond1 = bond1;
	}
	/**
	 * @return 返回 bond2。
	 */
	public BigDecimal getBond2() {
		return bond2;
	}
	/**
	 * @param bond2 要设置的 bond2。
	 */
	public void setBond2(BigDecimal bond2) {
		this.bond2 = bond2;
	}
	/**
	 * @return 返回 book_code。
	 */
	public Integer getBook_code() {
		return book_code;
	}
	/**
	 * @param book_code 要设置的 book_code。
	 */
	public void setBook_code(Integer book_code) {
		this.book_code = book_code;
	}
	/**
	 * @return 返回 check_flag。
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}
	/**
	 * @param check_flag 要设置的 check_flag。
	 */
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/**
	 * @return 返回 contract_bh。
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh 要设置的 contract_bh。
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
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
	/**
	 * @return 返回 dz_date。
	 */
	public Integer getDz_date() {
		return dz_date;
	}
	/**
	 * @param dz_date 要设置的 dz_date。
	 */
	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}
	/**
	 * @return 返回 end_date。
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date 要设置的 end_date。
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return 返回 fee。
	 */
	public BigDecimal getFee() {
		return fee;
	}
	/**
	 * @param fee 要设置的 fee。
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * @return 返回 fee_money。
	 */
	public java.math.BigDecimal getFee_money() {
		return fee_money;
	}
	/**
	 * @param fee_money 要设置的 fee_money。
	 */
	public void setFee_money(java.math.BigDecimal fee_money) {
		this.fee_money = fee_money;
	}
	/**
	 * @return 返回 fee_type。
	 */
	public Integer getFee_type() {
		return fee_type;
	}
	/**
	 * @param fee_type 要设置的 fee_type。
	 */
	public void setFee_type(Integer fee_type) {
		this.fee_type = fee_type;
	}
	/**
	 * @return 返回 group_id。
	 */
	public String getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id 要设置的 group_id。
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return 返回 group_id_out。
	 */
	public String getGroup_id_out() {
		return group_id_out;
	}
	/**
	 * @param group_id_out 要设置的 group_id_out。
	 */
	public void setGroup_id_out(String group_id_out) {
		this.group_id_out = group_id_out;
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
	 * @return 返回 jk_type。
	 */
	public String getJk_type() {
		return jk_type;
	}
	/**
	 * @param jk_type 要设置的 jk_type。
	 */
	public void setJk_type(String jk_type) {
		this.jk_type = jk_type;
	}
	/**
	 * @return 返回 jk_type_name。
	 */
	public String getJk_type_name() {
		return jk_type_name;
	}
	/**
	 * @param jk_type_name 要设置的 jk_type_name。
	 */
	public void setJk_type_name(String jk_type_name) {
		this.jk_type_name = jk_type_name;
	}
	/**
	 * @return 返回 js_date。
	 */
	public Integer getJs_date() {
		return js_date;
	}
	/**
	 * @param js_date 要设置的 js_date。
	 */
	public void setJs_date(Integer js_date) {
		this.js_date = js_date;
	}
	/**
	 * @return 返回 max_to_money。
	 */
	public java.math.BigDecimal getMax_to_money() {
		return max_to_money;
	}
	/**
	 * @param max_to_money 要设置的 max_to_money。
	 */
	public void setMax_to_money(java.math.BigDecimal max_to_money) {
		this.max_to_money = max_to_money;
	}
	/**
	 * @return 返回 min_to_money。
	 */
	public java.math.BigDecimal getMin_to_money() {
		return min_to_money;
	}
	/**
	 * @param min_to_money 要设置的 min_to_money。
	 */
	public void setMin_to_money(java.math.BigDecimal min_to_money) {
		this.min_to_money = min_to_money;
	}
	/**
	 * @return 返回 post_flag。
	 */
	public Integer getPost_flag() {
		return post_flag;
	}
	/**
	 * @param post_flag 要设置的 post_flag。
	 */
	public void setPost_flag(Integer post_flag) {
		this.post_flag = post_flag;
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
	 * @return 返回 pz_flag。
	 */
	public Integer getPz_flag() {
		return pz_flag;
	}
	/**
	 * @param pz_flag 要设置的 pz_flag。
	 */
	public void setPz_flag(Integer pz_flag) {
		this.pz_flag = pz_flag;
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
	 * @return 返回 sub_code。
	 */
	public String getSub_code() {
		return sub_code;
	}
	/**
	 * @param sub_code 要设置的 sub_code。
	 */
	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
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
	 * @return 返回 to_amount。
	 */
	public BigDecimal getTo_amount() {
		return to_amount;
	}
	/**
	 * @param to_amount 要设置的 to_amount。
	 */
	public void setTo_amount(BigDecimal to_amount) {
		this.to_amount = to_amount;
	}
	/**
	 * @return 返回 to_bank_date。
	 */
	public Integer getTo_bank_date() {
		return to_bank_date;
	}
	/**
	 * @param to_bank_date 要设置的 to_bank_date。
	 */
	public void setTo_bank_date(Integer to_bank_date) {
		this.to_bank_date = to_bank_date;
	}
	/**
	 * @return 返回 to_money。
	 */
	public BigDecimal getTo_money() {
		return to_money;
	}
	/**
	 * @param to_money 要设置的 to_money。
	 */
	public void setTo_money(BigDecimal to_money) {
		this.to_money = to_money;
	}
}
