package enfo.crm.vo;

import java.math.BigDecimal;

public class EntRelationVO {
	
	private Integer serial_no;//��¼��
	
	private Integer book_code;//����
	
	private Integer ent_cust_id;//�ͻ�
	
	private String relation_type;//��ϵ
	
	private String relation_type_name;//��ϵ˵��
	
	private Integer relation_cust_id;//��ϵ��
	
    private java.lang.Integer input_man;
    
    private Integer kg_flag;
    
    private BigDecimal cg_rate;


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
	 * @return ���� ent_cust_id��
	 */
	public Integer getEnt_cust_id() {
		return ent_cust_id;
	}
	/**
	 * @param ent_cust_id Ҫ���õ� ent_cust_id��
	 */
	public void setEnt_cust_id(Integer ent_cust_id) {
		this.ent_cust_id = ent_cust_id;
	}
	/**
	 * @return ���� relation_cust_id��
	 */
	public Integer getRelation_cust_id() {
		return relation_cust_id;
	}
	/**
	 * @param relation_cust_id Ҫ���õ� relation_cust_id��
	 */
	public void setRelation_cust_id(Integer relation_cust_id) {
		this.relation_cust_id = relation_cust_id;
	}
	/**
	 * @return ���� relation_type��
	 */
	public String getRelation_type() {
		return relation_type;
	}
	/**
	 * @param relation_type Ҫ���õ� relation_type��
	 */
	public void setRelation_type(String relation_type) {
		this.relation_type = relation_type;
	}
	/**
	 * @return ���� relation_type_name��
	 */
	public String getRelation_type_name() {
		return relation_type_name;
	}
	/**
	 * @param relation_type_name Ҫ���õ� relation_type_name��
	 */
	public void setRelation_type_name(String relation_type_name) {
		this.relation_type_name = relation_type_name;
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
	 * @return ���� cg_rate��
	 */
	public BigDecimal getCg_rate() {
		return cg_rate;
	}
	/**
	 * @param cg_rate Ҫ���õ� cg_rate��
	 */
	public void setCg_rate(BigDecimal cg_rate) {
		this.cg_rate = cg_rate;
	}
	/**
	 * @return ���� kg_flag��
	 */
	public Integer getKg_flag() {
		return kg_flag;
	}
	/**
	 * @param kg_flag Ҫ���õ� kg_flag��
	 */
	public void setKg_flag(Integer kg_flag) {
		this.kg_flag = kg_flag;
	}
}
