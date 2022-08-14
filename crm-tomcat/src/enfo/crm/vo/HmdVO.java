package enfo.crm.vo;

public class HmdVO {
	private String full_name_c;//中文全称
	private String for_short_c; //中文简称
	private String full_name_e; //英文全称
	private String for_short_e; //英文简称
	private String other_lang_name; //其他语言名称
	private String classification_no; //
	private String classification_name; //中文全称
	private String category_no; //类别编号
	private String category_name;//类别名称,个人、组织
	private String card_type; //证件类型
	private String card_no; //证件编号
	private String country; //所属国家
	private String explanation; //简要说明
	private Integer input_man; //操作员
	private Integer serial_no; //流水号
	private Integer birth_name_id; //别名id   
	
	private String file_name;
	private String file_date;
	/**
	 * @return
	 */
	public String getCard_no() {
		return card_no;
	}

	/**
	 * @return
	 */
	public String getCard_type() {
		return card_type;
	}

	/**
	 * @return
	 */
	public String getClassification_name() {
		return classification_name;
	}

	/**
	 * @return
	 */
	public String getClassification_no() {
		return classification_no;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return
	 */
	public String getFor_short_c() {
		return for_short_c;
	}

	/**
	 * @return
	 */
	public String getFor_short_e() {
		return for_short_e;
	}

	/**
	 * @return
	 */
	public String getFull_name_e() {
		return full_name_e;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public String getOther_lang_name() {
		return other_lang_name;
	}

	/**
	 * @param string
	 */
	public void setCard_no(String string) {
		card_no = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type(String string) {
		card_type = string;
	}

	/**
	 * @param string
	 */
	public void setClassification_name(String string) {
		classification_name = string;
	}

	/**
	 * @param integer
	 */
	public void setClassification_no(String string) {
		classification_no = string;
	}

	/**
	 * @param string
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param string
	 */
	public void setFor_short_c(String string) {
		for_short_c = string;
	}

	/**
	 * @param string
	 */
	public void setFor_short_e(String string) {
		for_short_e = string;
	}

	/**
	 * @param string
	 */
	public void setFull_name_e(String string) {
		full_name_e = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param string
	 */
	public void setOther_lang_name(String string) {
		other_lang_name = string;
	}

	/**
	 * @return
	 */
	public String getFull_name_c() {
		return full_name_c;
	}

	/**
	 * @param string
	 */
	public void setFull_name_c(String string) {
		full_name_c = string;
	}

	/**
	 * @return
	 */
	public String getCategory_name() {
		return category_name;
	}

	/**
	 * @param string
	 */
	public void setCategory_name(String string) {
		category_name = string;
	}

	/**
	 * @param string
	 */
	public void setExplanation(String string) {
		explanation = string;
	}

	/**
	 * @return
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
	 * @return
	 */
	public String getCategory_no() {
		return category_no;
	}

	/**
	 * @param integer
	 */
	public void setCategory_no(String string) {
		category_no = string;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getBirth_name_id() {
		return birth_name_id;
	}

	/**
	 * @param integer
	 */
	public void setBirth_name_id(Integer integer) {
		birth_name_id = integer;
	}

	/**
	 * @return
	 */
	public String getFile_date() {
		return file_date;
	}

	/**
	 * @return
	 */
	public String getFile_name() {
		return file_name;
	}

	/**
	 * @param string
	 */
	public void setFile_date(String string) {
		file_date = string;
	}

	/**
	 * @param string
	 */
	public void setFile_name(String string) {
		file_name = string;
	}

}
