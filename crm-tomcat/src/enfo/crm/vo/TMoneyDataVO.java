/*
 * �������� 2012-4-11
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TMoneyDataVO {
	//����
	private Integer book_code;
	private Integer serial_no;
	private Integer product_id;
	private Integer sub_product_id;
	private Integer cust_id;
	private Integer city_serialno;
	private Integer input_man;
	private Integer sbf_serial_no;
	private String serial_no_list;
	
	private String cust_name;
	private Integer cust_type;
	
	private String bank_id;
	private String bank_sub_name;
	private String bank_acct;
	private String gain_acct;
	private String bank_acct_type;
	private Integer bonus_flag;
	private BigDecimal bonus_rate;
	private Integer with_bank_flag;
	private String ht_bank_id;
	private String ht_bank_sub_name;
	private Integer with_security_flag;
	private Integer with_private_flag;
	
	private Integer jk_date;
	private String jk_type;
	private String contract_sub_bh;
	private String contract_bh;
	private Integer qs_date;
	private Integer prov_flag; 
	private String prov_level;	
	
	private Integer check_flag;
	private Integer complete_flag;
	private Integer pz_flag;
	private String summary;
	private String applyreach_flag;
	
	private Integer input_check_flag;
	private String check_summary;
	private Integer input_check_man;	
	
	private String dz_time;
	private Integer pre_serial_no;
	private Integer onway_flag;
	
	private Integer ht_type;
	
	private Integer contract_id;
	private BigDecimal fee_money; //���깺����
	
	private String contract_way;
	
	private Integer first_flag;
	
	private Integer ben_serial_no;
	private String money_origin; //�ʽ���Դ
	private String sub_money_origin;//�ʽ���Դ
	
	 
	/**
	 * @return ���� money_origin��
	 */
	public String getMoney_origin() {
		return money_origin;
	}
	/**
	 * @param money_origin Ҫ���õ� money_origin��
	 */
	public void setMoney_origin(String money_origin) {
		this.money_origin = money_origin;
	}
	/**
	 * @return ���� sub_money_origin��
	 */
	public String getSub_money_origin() {
		return sub_money_origin;
	}
	/**
	 * @param sub_money_origin Ҫ���õ� sub_money_origin��
	 */
	public void setSub_money_origin(String sub_money_origin) {
		this.sub_money_origin = sub_money_origin;
	}
	/**
	 * @return ���� ben_serial_no��
	 */
	public Integer getBen_serial_no() {
		return ben_serial_no;
	}
	/**
	 * @param ben_serial_no Ҫ���õ� ben_serial_no��
	 */
	public void setBen_serial_no(Integer ben_serial_no) {
		this.ben_serial_no = ben_serial_no;
	}
	/**
	 * @return ���� contract_way��
	 */
	public String getContract_way() {
		return contract_way;
	}
	/**
	 * @param contract_way Ҫ���õ� contract_way��
	 */
	public void setContract_way(String contract_way) {
		this.contract_way = contract_way;
	}
	/**
	 * @return ���� first_flag��
	 */
	public Integer getFirst_flag() {
		return first_flag;
	}
	/**
	 * @param first_flag Ҫ���õ� first_flag��
	 */
	public void setFirst_flag(Integer first_flag) {
		this.first_flag = first_flag;
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
	 * @return ���� fee_money��
	 */
	public BigDecimal getFee_money() {
		return fee_money;
	}
	/**
	 * @param fee_money Ҫ���õ� fee_money��
	 */
	public void setFee_money(BigDecimal fee_money) {
		this.fee_money = fee_money;
	}
	/**
	 * @return ���� ht_type��
	 */
	public Integer getHt_type() {
		return ht_type;
	}
	/**
	 * @param ht_type Ҫ���õ� ht_type��
	 */
	public void setHt_type(Integer ht_type) {
		this.ht_type = ht_type;
	}
	/**
	 * @return ���� contract_id��
	 */
	public Integer getContract_id() {
		return contract_id;
	}
	/**
	 * @param contract_id Ҫ���õ� contract_id��
	 */
	public void setContract_id(Integer contract_id) {
		this.contract_id = contract_id;
	}
	
	/**
	 * @return ���� check_summary��
	 */
	public String getCheck_summary() {
		return check_summary;
	}
	/**
	 * @param check_summary Ҫ���õ� check_summary��
	 */
	public void setCheck_summary(String check_summary) {
		this.check_summary = check_summary;
	}
	/**
	 * @return ���� dz_time��
	 */
	public String getDz_time() {
		return dz_time;
	}
	/**
	 * @param dz_time Ҫ���õ� dz_time��
	 */
	public void setDz_time(String dz_time) {
		this.dz_time = dz_time;
	}
	/**
	 * @return ���� onway_flag��
	 */
	public Integer getOnway_flag() {
		return onway_flag;
	}
	/**
	 * @param onway_flag Ҫ���õ� onway_flag��
	 */
	public void setOnway_flag(Integer onway_flag) {
		this.onway_flag = onway_flag;
	}
	/**
	 * @return ���� pre_serial_no��
	 */
	public Integer getPre_serial_no() {
		return pre_serial_no;
	}
	/**
	 * @param pre_serial_no Ҫ���õ� pre_serial_no��
	 */
	public void setPre_serial_no(Integer pre_serial_no) {
		this.pre_serial_no = pre_serial_no;
	}
	/**
	 * @return ���� input_check_flag��
	 */
	public Integer getInput_check_flag() {
		return input_check_flag;
	}
	/**
	 * @param input_check_flag Ҫ���õ� input_check_flag��
	 */
	public void setInput_check_flag(Integer input_check_flag) {
		this.input_check_flag = input_check_flag;
	}
	/**
	 * @return ���� input_check_man��
	 */
	public Integer getInput_check_man() {
		return input_check_man;
	}
	/**
	 * @param input_check_man Ҫ���õ� input_check_man��
	 */
	public void setInput_check_man(Integer input_check_man) {
		this.input_check_man = input_check_man;
	}
	/**
	 * @return ���� prov_flag��
	 */
	public Integer getProv_flag() {
		return prov_flag;
	}
	/**
	 * @return ���� prov_level��
	 */
	public String getProv_level() {
		return prov_level;
	}
	/**
	 * @param prov_level Ҫ���õ� prov_level��
	 */
	public void setProv_level(String prov_level) {
		this.prov_level = prov_level;
	}
	/**
	 * @param prov_flag Ҫ���õ� prov_flag��
	 */
	public void setProv_flag(Integer prov_flag) {
		this.prov_flag = prov_flag;
	}
	/**
	 * @return ���� qs_date��
	 */
	public Integer getQs_date() {
		return qs_date;
	}
	/**
	 * @param qs_date Ҫ���õ� qs_date��
	 */
	public void setQs_date(Integer qs_date) {
		this.qs_date = qs_date;
	}
	private BigDecimal to_money;
	private BigDecimal to_amount;
	
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
	 * @return ���� city_serialno��
	 */
	public Integer getCity_serialno() {
		return city_serialno;
	}
	/**
	 * @param city_serialno Ҫ���õ� city_serialno��
	 */
	public void setCity_serialno(Integer city_serialno) {
		this.city_serialno = city_serialno;
	}
	/**
	 * @return ���� complete_flag��
	 */
	public Integer getComplete_flag() {
		return complete_flag;
	}
	/**
	 * @param complete_flag Ҫ���õ� complete_flag��
	 */
	public void setComplete_flag(Integer complete_flag) {
		this.complete_flag = complete_flag;
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
	 * @return ���� cust_type��
	 */
	public Integer getCust_type() {
		return cust_type;
	}
	/**
	 * @param cust_type Ҫ���õ� cust_type��
	 */
	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
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
	 * @return ���� jk_date��
	 */
	public Integer getJk_date() {
		return jk_date;
	}
	/**
	 * @param jk_date Ҫ���õ� jk_date��
	 */
	public void setJk_date(Integer jk_date) {
		this.jk_date = jk_date;
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
	 * @return ���� sub_product_id��
	 */
	public Integer getSub_product_id() {
		return sub_product_id;
	}
	/**
	 * @param sub_product_id Ҫ���õ� sub_product_id��
	 */
	public void setSub_product_id(Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
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
	/**
	 * @return ���� serial_no_list��
	 */
	public String getSerial_no_list() {
		return serial_no_list;
	}
	/**
	 * @param serial_no_list Ҫ���õ� serial_no_list��
	 */
	public void setSerial_no_list(String serial_no_list) {
		this.serial_no_list = serial_no_list;
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
	 * @return ���� bank_acct��
	 */
	public String getBank_acct() {
		return bank_acct;
	}
	/**
	 * @param bank_acct Ҫ���õ� bank_acct��
	 */
	public void setBank_acct(String bank_acct) {
		this.bank_acct = bank_acct;
	}
	/**
	 * @return ���� bank_acct_type��
	 */
	public String getBank_acct_type() {
		return bank_acct_type;
	}
	/**
	 * @param bank_acct_type Ҫ���õ� bank_acct_type��
	 */
	public void setBank_acct_type(String bank_acct_type) {
		this.bank_acct_type = bank_acct_type;
	}
	/**
	 * @return ���� bank_id��
	 */
	public String getBank_id() {
		return bank_id;
	}
	/**
	 * @param bank_id Ҫ���õ� bank_id��
	 */
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	/**
	 * @return ���� bank_sub_name��
	 */
	public String getBank_sub_name() {
		return bank_sub_name;
	}
	/**
	 * @param bank_sub_name Ҫ���õ� bank_sub_name��
	 */
	public void setBank_sub_name(String bank_sub_name) {
		this.bank_sub_name = bank_sub_name;
	}
	/**
	 * @return ���� bonus_flag��
	 */
	public Integer getBonus_flag() {
		return bonus_flag;
	}
	/**
	 * @param bonus_flag Ҫ���õ� bonus_flag��
	 */
	public void setBonus_flag(Integer bonus_flag) {
		this.bonus_flag = bonus_flag;
	}
	/**
	 * @return ���� bonus_rate��
	 */
	public BigDecimal getBonus_rate() {
		return bonus_rate;
	}
	/**
	 * @param bonus_rate Ҫ���õ� bonus_rate��
	 */
	public void setBonus_rate(BigDecimal bonus_rate) {
		this.bonus_rate = bonus_rate;
	}
	/**
	 * @return ���� gain_acct��
	 */
	public String getGain_acct() {
		return gain_acct;
	}
	/**
	 * @param gain_acct Ҫ���õ� gain_acct��
	 */
	public void setGain_acct(String gain_acct) {
		this.gain_acct = gain_acct;
	}
	/**
	 * @return ���� ht_bank_id��
	 */
	public String getHt_bank_id() {
		return ht_bank_id;
	}
	/**
	 * @param ht_bank_id Ҫ���õ� ht_bank_id��
	 */
	public void setHt_bank_id(String ht_bank_id) {
		this.ht_bank_id = ht_bank_id;
	}
	/**
	 * @return ���� ht_bank_sub_name��
	 */
	public String getHt_bank_sub_name() {
		return ht_bank_sub_name;
	}
	/**
	 * @param ht_bank_sub_name Ҫ���õ� ht_bank_sub_name��
	 */
	public void setHt_bank_sub_name(String ht_bank_sub_name) {
		this.ht_bank_sub_name = ht_bank_sub_name;
	}
	/**
	 * @return ���� with_bank_flag��
	 */
	public Integer getWith_bank_flag() {
		return with_bank_flag;
	}
	/**
	 * @param with_bank_flag Ҫ���õ� with_bank_flag��
	 */
	public void setWith_bank_flag(Integer with_bank_flag) {
		this.with_bank_flag = with_bank_flag;
	}
	/**
	 * @return ���� with_private_flag��
	 */
	public Integer getWith_private_flag() {
		return with_private_flag;
	}
	/**
	 * @param with_private_flag Ҫ���õ� with_private_flag��
	 */
	public void setWith_private_flag(Integer with_private_flag) {
		this.with_private_flag = with_private_flag;
	}
	/**
	 * @return ���� with_security_flag��
	 */
	public Integer getWith_security_flag() {
		return with_security_flag;
	}
	/**
	 * @param with_security_flag Ҫ���õ� with_security_flag��
	 */
	public void setWith_security_flag(Integer with_security_flag) {
		this.with_security_flag = with_security_flag;
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
	 * @return ���� applyreach_flag��
	 */
	public String getApplyreach_flag() {
		return applyreach_flag;
	}
	/**
	 * @param applyreach_flag Ҫ���õ� applyreach_flag��
	 */
	public void setApplyreach_flag(String applyreach_flag) {
		this.applyreach_flag = applyreach_flag;
	}
}
