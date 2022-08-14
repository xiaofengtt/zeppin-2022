package enfo.crm.vo;

/**
 * @author dingyj
 * @description 企业客户证件类型信息
 */
public class EntCustCardVO {

	private Integer serial_no;

	private Integer cust_id;

	private String card_type;

	private String card_type_name;

	private String card_id;

	private Integer valid_date;

	private String lssued_org;

	private Integer input_man;

	private Integer lssued_date;

	/**
	 * @return 返回 lssued_date。
	 */
	public Integer getLssued_date() {
		return lssued_date;
	}

	/**
	 * @param lssued_date
	 *            要设置的 lssued_date。
	 */
	public void setLssued_date(Integer lssued_date) {
		this.lssued_date = lssued_date;
	}

	/**
	 * @return 返回 card_id。
	 */
	public String getCard_id() {
		return card_id;
	}

	/**
	 * @param card_id
	 *            要设置的 card_id。
	 */
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	/**
	 * @return 返回 card_type。
	 */
	public String getCard_type() {
		return card_type;
	}

	/**
	 * @param card_type
	 *            要设置的 card_type。
	 */
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	/**
	 * @return 返回 card_type_name。
	 */
	public String getCard_type_name() {
		return card_type_name;
	}

	/**
	 * @param card_type_name
	 *            要设置的 card_type_name。
	 */
	public void setCard_type_name(String card_type_name) {
		this.card_type_name = card_type_name;
	}

	/**
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @param cust_id
	 *            要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}

	/**
	 * @return 返回 lssued_org。
	 */
	public String getLssued_org() {
		return lssued_org;
	}

	/**
	 * @param lssued_org
	 *            要设置的 lssued_org。
	 */
	public void setLssued_org(String lssued_org) {
		this.lssued_org = lssued_org;
	}

	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param serial_no
	 *            要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}

	/**
	 * @return 返回 valid_date。
	 */
	public Integer getValid_date() {
		return valid_date;
	}

	/**
	 * @param valid_date
	 *            要设置的 valid_date。
	 */
	public void setValid_date(Integer valid_date) {
		this.valid_date = valid_date;
	}

	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param input_man
	 *            要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
}