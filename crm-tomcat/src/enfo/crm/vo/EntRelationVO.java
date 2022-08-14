package enfo.crm.vo;

import java.math.BigDecimal;

public class EntRelationVO {
	
	private Integer serial_no;//记录号
	
	private Integer book_code;//账套
	
	private Integer ent_cust_id;//客户
	
	private String relation_type;//关系
	
	private String relation_type_name;//关系说明
	
	private Integer relation_cust_id;//关系人
	
    private java.lang.Integer input_man;
    
    private Integer kg_flag;
    
    private BigDecimal cg_rate;


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
	 * @return 返回 ent_cust_id。
	 */
	public Integer getEnt_cust_id() {
		return ent_cust_id;
	}
	/**
	 * @param ent_cust_id 要设置的 ent_cust_id。
	 */
	public void setEnt_cust_id(Integer ent_cust_id) {
		this.ent_cust_id = ent_cust_id;
	}
	/**
	 * @return 返回 relation_cust_id。
	 */
	public Integer getRelation_cust_id() {
		return relation_cust_id;
	}
	/**
	 * @param relation_cust_id 要设置的 relation_cust_id。
	 */
	public void setRelation_cust_id(Integer relation_cust_id) {
		this.relation_cust_id = relation_cust_id;
	}
	/**
	 * @return 返回 relation_type。
	 */
	public String getRelation_type() {
		return relation_type;
	}
	/**
	 * @param relation_type 要设置的 relation_type。
	 */
	public void setRelation_type(String relation_type) {
		this.relation_type = relation_type;
	}
	/**
	 * @return 返回 relation_type_name。
	 */
	public String getRelation_type_name() {
		return relation_type_name;
	}
	/**
	 * @param relation_type_name 要设置的 relation_type_name。
	 */
	public void setRelation_type_name(String relation_type_name) {
		this.relation_type_name = relation_type_name;
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
	 * @return 返回 cg_rate。
	 */
	public BigDecimal getCg_rate() {
		return cg_rate;
	}
	/**
	 * @param cg_rate 要设置的 cg_rate。
	 */
	public void setCg_rate(BigDecimal cg_rate) {
		this.cg_rate = cg_rate;
	}
	/**
	 * @return 返回 kg_flag。
	 */
	public Integer getKg_flag() {
		return kg_flag;
	}
	/**
	 * @param kg_flag 要设置的 kg_flag。
	 */
	public void setKg_flag(Integer kg_flag) {
		this.kg_flag = kg_flag;
	}
}
