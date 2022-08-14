/*
 * 创建日期 2008-7-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class AssetVaryVO {
	private Integer book_code;
	private String asset_no;
	private String asset_name;
	private Integer begin_date;
	private Integer end_date;
	private String vary_type;
	private String vary_type_name;
	private Integer refer_flag;//bit型
	private Integer check_flag;
	private Integer card_id;
	private Integer vary_id;
	
	private Integer create_time;
	private Integer create_man;
	private Integer refer_date;
	private Integer refer_man;
	private Integer check_man;
	private Integer check_date;
	private String remark;
	private Integer depart_id;
	private String put_address;
	private Integer input_man;
	/**
	 * @return
	 */
	public String getAsset_name() {
		return asset_name;
	}

	/**
	 * @return
	 */
	public String getAsset_no() {
		return asset_no;
	}

	/**
	 * @return
	 */
	public Integer getBegin_date() {
		return begin_date;
	}

	/**
	 * @return
	 */
	public Integer getBook_code() {
		return book_code;
	}

	/**
	 * @return
	 */
	public Integer getCard_id() {
		return card_id;
	}

	/**
	 * @return
	 */
	public Integer getCheck_date() {
		return check_date;
	}

	/**
	 * @return
	 */
	public Integer getCheck_flag() {
		return check_flag;
	}

	/**
	 * @return
	 */
	public Integer getCheck_man() {
		return check_man;
	}

	/**
	 * @return
	 */
	public Integer getCreate_man() {
		return create_man;
	}

	/**
	 * @return
	 */
	public Integer getCreate_time() {
		return create_time;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @return
	 */
	public Integer getRefer_date() {
		return refer_date;
	}

	/**
	 * @return
	 */
	public Integer getRefer_flag() {
		return refer_flag;
	}

	/**
	 * @return
	 */
	public Integer getRefer_man() {
		return refer_man;
	}

	/**
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return
	 */
	public Integer getVary_id() {
		return vary_id;
	}

	/**
	 * @return
	 */
	public String getVary_type() {
		return vary_type;
	}

	/**
	 * @return
	 */
	public String getVary_type_name() {
		return vary_type_name;
	}

	/**
	 * @param string
	 */
	public void setAsset_name(String string) {
		asset_name = string;
	}

	/**
	 * @param string
	 */
	public void setAsset_no(String string) {
		asset_no = string;
	}

	/**
	 * @param integer
	 */
	public void setBegin_date(Integer integer) {
		begin_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer) {
		book_code = integer;
	}

	/**
	 * @param integer
	 */
	public void setCard_id(Integer integer) {
		card_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_date(Integer integer) {
		check_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setCheck_man(Integer integer) {
		check_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCreate_man(Integer integer) {
		create_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setCreate_time(Integer integer) {
		create_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setRefer_date(Integer integer) {
		refer_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setRefer_flag(Integer integer) {
		refer_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setRefer_man(Integer integer) {
		refer_man = integer;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string) {
		remark = string;
	}

	/**
	 * @param integer
	 */
	public void setVary_id(Integer integer) {
		vary_id = integer;
	}

	/**
	 * @param string
	 */
	public void setVary_type(String string) {
		vary_type = string;
	}

	/**
	 * @param string
	 */
	public void setVary_type_name(String string) {
		vary_type_name = string;
	}

	/**
	 * @return
	 */
	public Integer getDepart_id() {
		return depart_id;
	}

	/**
	 * @return
	 */
	public String getPut_address() {
		return put_address;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param string
	 */
	public void setPut_address(String string) {
		put_address = string;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

}
