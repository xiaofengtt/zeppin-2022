/*
 * �������� 2010-6-15
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author taochen
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class ToMoneyAccountVO {
    private Integer book_code = new Integer(0); //	Int N ���״���
    private Integer serial_no = new Integer(0); //	Int N ��š��Զ�����
    private Integer product_id = new Integer(0); //	Int N ��ƷID��
    private String contract_bh = ""; //VARCHAR 16 N ��ͬ��ţ��Ѿ��޸�Ϊ��ͬ��ţ�
    private String contract_sub_bh = "";//VARCHAR 50 ��ͬ���
    private Integer cust_id = new Integer(0); //CUST_ID INT N �ͻ�ID
    private String cust_name = ""; //CUST_NAME VARCHAR 100 N �ͻ�����
    private Integer dz_date; //	DATE N ��������/��Ϣ����
    private Integer end_date; //	DATE N
    private BigDecimal to_money = new BigDecimal(0); //	Money N �����ʽ�/��Ϣ���
    private BigDecimal to_amount = new BigDecimal(0); //	Float N �����ݶ�
    private String jk_type = ""; //	VARCHAR 10 N �ɿʽ(1114)
    private String jk_type_name = "";
    private Integer check_flag = new Integer(0);
    private String sub_code = "";
    private Integer input_man = new Integer(0); //	Int N ¼����Ա
    private Integer to_bank_date = new Integer(0); //	Int N ¼����Ա
    private Integer pz_flag = new Integer(0); //	Int N ¼����Ա
    private BigDecimal fee = new BigDecimal(0); //	Float N ����
    private String product_name;
    //GEQI20051109
    private BigDecimal bond1;//��������Ϣ
    private BigDecimal bond2;//������ʵ����Ϣ��������Ϣ˰
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
    private Integer fee_type;//1�Ϲ���2�깺��3��أ�4�ݶ�ת��
    private String summary;
    private Integer sbf_serial_no;
    
    
    
    
	/**
	 * @return ���� sbf_serial_no��
	 */
	public Integer getSbf_serial_no() {
		return sbf_serial_no;
	}
	/**
	 * @param sbf_serial_no Ҫ���õ� sbf_serial_no��
	 */
	public void setSbf_serial_no(Integer sbf_serial_no) {
		this.sbf_serial_no = sbf_serial_no;
	}
	/**
	 * @return ���� bond_date��
	 */
	public Integer getBond_date() {
		return bond_date;
	}
	/**
	 * @param bond_date Ҫ���õ� bond_date��
	 */
	public void setBond_date(Integer bond_date) {
		this.bond_date = bond_date;
	}
	/**
	 * @return ���� bond_days��
	 */
	public Integer getBond_days() {
		return bond_days;
	}
	/**
	 * @param bond_days Ҫ���õ� bond_days��
	 */
	public void setBond_days(Integer bond_days) {
		this.bond_days = bond_days;
	}
	/**
	 * @return ���� bond_tax��
	 */
	public BigDecimal getBond_tax() {
		return bond_tax;
	}
	/**
	 * @param bond_tax Ҫ���õ� bond_tax��
	 */
	public void setBond_tax(BigDecimal bond_tax) {
		this.bond_tax = bond_tax;
	}
	/**
	 * @return ���� bond1��
	 */
	public BigDecimal getBond1() {
		return bond1;
	}
	/**
	 * @param bond1 Ҫ���õ� bond1��
	 */
	public void setBond1(BigDecimal bond1) {
		this.bond1 = bond1;
	}
	/**
	 * @return ���� bond2��
	 */
	public BigDecimal getBond2() {
		return bond2;
	}
	/**
	 * @param bond2 Ҫ���õ� bond2��
	 */
	public void setBond2(BigDecimal bond2) {
		this.bond2 = bond2;
	}
	/**
	 * @return ���� book_code��
	 */
	public Integer getBook_code() {
		return book_code;
	}
	/**
	 * @param book_code Ҫ���õ� book_code��
	 */
	public void setBook_code(Integer book_code) {
		this.book_code = book_code;
	}
	/**
	 * @return ���� check_flag��
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}
	/**
	 * @param check_flag Ҫ���õ� check_flag��
	 */
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/**
	 * @return ���� contract_bh��
	 */
	public String getContract_bh() {
		return contract_bh;
	}
	/**
	 * @param contract_bh Ҫ���õ� contract_bh��
	 */
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/**
	 * @return ���� contract_sub_bh��
	 */
	public String getContract_sub_bh() {
		return contract_sub_bh;
	}
	/**
	 * @param contract_sub_bh Ҫ���õ� contract_sub_bh��
	 */
	public void setContract_sub_bh(String contract_sub_bh) {
		this.contract_sub_bh = contract_sub_bh;
	}
	/**
	 * @return ���� cust_id��
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� cust_name��
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return ���� dz_date��
	 */
	public Integer getDz_date() {
		return dz_date;
	}
	/**
	 * @param dz_date Ҫ���õ� dz_date��
	 */
	public void setDz_date(Integer dz_date) {
		this.dz_date = dz_date;
	}
	/**
	 * @return ���� end_date��
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date Ҫ���õ� end_date��
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return ���� fee��
	 */
	public BigDecimal getFee() {
		return fee;
	}
	/**
	 * @param fee Ҫ���õ� fee��
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * @return ���� fee_money��
	 */
	public java.math.BigDecimal getFee_money() {
		return fee_money;
	}
	/**
	 * @param fee_money Ҫ���õ� fee_money��
	 */
	public void setFee_money(java.math.BigDecimal fee_money) {
		this.fee_money = fee_money;
	}
	/**
	 * @return ���� fee_type��
	 */
	public Integer getFee_type() {
		return fee_type;
	}
	/**
	 * @param fee_type Ҫ���õ� fee_type��
	 */
	public void setFee_type(Integer fee_type) {
		this.fee_type = fee_type;
	}
	/**
	 * @return ���� group_id��
	 */
	public String getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id Ҫ���õ� group_id��
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return ���� group_id_out��
	 */
	public String getGroup_id_out() {
		return group_id_out;
	}
	/**
	 * @param group_id_out Ҫ���õ� group_id_out��
	 */
	public void setGroup_id_out(String group_id_out) {
		this.group_id_out = group_id_out;
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
	 * @return ���� jk_type��
	 */
	public String getJk_type() {
		return jk_type;
	}
	/**
	 * @param jk_type Ҫ���õ� jk_type��
	 */
	public void setJk_type(String jk_type) {
		this.jk_type = jk_type;
	}
	/**
	 * @return ���� jk_type_name��
	 */
	public String getJk_type_name() {
		return jk_type_name;
	}
	/**
	 * @param jk_type_name Ҫ���õ� jk_type_name��
	 */
	public void setJk_type_name(String jk_type_name) {
		this.jk_type_name = jk_type_name;
	}
	/**
	 * @return ���� js_date��
	 */
	public Integer getJs_date() {
		return js_date;
	}
	/**
	 * @param js_date Ҫ���õ� js_date��
	 */
	public void setJs_date(Integer js_date) {
		this.js_date = js_date;
	}
	/**
	 * @return ���� max_to_money��
	 */
	public java.math.BigDecimal getMax_to_money() {
		return max_to_money;
	}
	/**
	 * @param max_to_money Ҫ���õ� max_to_money��
	 */
	public void setMax_to_money(java.math.BigDecimal max_to_money) {
		this.max_to_money = max_to_money;
	}
	/**
	 * @return ���� min_to_money��
	 */
	public java.math.BigDecimal getMin_to_money() {
		return min_to_money;
	}
	/**
	 * @param min_to_money Ҫ���õ� min_to_money��
	 */
	public void setMin_to_money(java.math.BigDecimal min_to_money) {
		this.min_to_money = min_to_money;
	}
	/**
	 * @return ���� post_flag��
	 */
	public Integer getPost_flag() {
		return post_flag;
	}
	/**
	 * @param post_flag Ҫ���õ� post_flag��
	 */
	public void setPost_flag(Integer post_flag) {
		this.post_flag = post_flag;
	}
	/**
	 * @return ���� product_id��
	 */
	public Integer getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id Ҫ���õ� product_id��
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return ���� product_name��
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name Ҫ���õ� product_name��
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return ���� pz_flag��
	 */
	public Integer getPz_flag() {
		return pz_flag;
	}
	/**
	 * @param pz_flag Ҫ���õ� pz_flag��
	 */
	public void setPz_flag(Integer pz_flag) {
		this.pz_flag = pz_flag;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� sub_code��
	 */
	public String getSub_code() {
		return sub_code;
	}
	/**
	 * @param sub_code Ҫ���õ� sub_code��
	 */
	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
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
	/**
	 * @return ���� to_amount��
	 */
	public BigDecimal getTo_amount() {
		return to_amount;
	}
	/**
	 * @param to_amount Ҫ���õ� to_amount��
	 */
	public void setTo_amount(BigDecimal to_amount) {
		this.to_amount = to_amount;
	}
	/**
	 * @return ���� to_bank_date��
	 */
	public Integer getTo_bank_date() {
		return to_bank_date;
	}
	/**
	 * @param to_bank_date Ҫ���õ� to_bank_date��
	 */
	public void setTo_bank_date(Integer to_bank_date) {
		this.to_bank_date = to_bank_date;
	}
	/**
	 * @return ���� to_money��
	 */
	public BigDecimal getTo_money() {
		return to_money;
	}
	/**
	 * @param to_money Ҫ���õ� to_money��
	 */
	public void setTo_money(BigDecimal to_money) {
		this.to_money = to_money;
	}
}
