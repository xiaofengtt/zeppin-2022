/*
 * �������� 2009-11-30
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @autho jinchongwei
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class PostSendVO {
	private Integer input_date; // �ʼ�����
	private Integer product_id;
	private String contract_sub_bh; // ��ͬ���
	private String post_no; // �ʼĵ���
	private String post_content; // �ʼ����ݣ�1��������2ȷ�ϵ�3������4��ʱ��Ϣ��¶5��ֹ����6�����������ж��������|�����
	private String summary; // ��ע
	private String product_name;
	private Integer input_man;
	private Integer serial_no;
	private String cust_name;

	

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
	 * @return ���� input_date��
	 */
	public Integer getInput_date() {
		return input_date;
	}
	/**
	 * @param input_date Ҫ���õ� input_date��
	 */
	public void setInput_date(Integer input_date) {
		this.input_date = input_date;
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
	 * @return ���� post_content��
	 */
	public String getPost_content() {
		return post_content;
	}
	/**
	 * @param post_content Ҫ���õ� post_content��
	 */
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	/**
	 * @return ���� post_no��
	 */
	public String getPost_no() {
		return post_no;
	}
	/**
	 * @param post_no Ҫ���õ� post_no��
	 */
	public void setPost_no(String post_no) {
		this.post_no = post_no;
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
}
