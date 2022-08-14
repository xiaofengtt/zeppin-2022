package enfo.crm.vo;

import java.math.BigDecimal;

/**
 *  
 * @author Felix
 * @since 2008-5-20
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class FixedAssetVO {

	/**
	 * Book_Code	TINYINT			N		帐套代码
	Card_ID	INT			N		卡片ID,自动增长
	Asset_No	VARCHAR	20		N		资产编号
	Asset_Name	VARCHAR	60		N		资产名称
	Asset_Type	VARCHAR	10		N		资产类别(对应FA_AssetType. TYPE_NO)
	Model_Type	VARCHAR	60				规格型号
	Depart_ID	INT					使用部门
	Custos	SMALLINT					保管员(对应员工表的OP_CODE)
	Put_Address	VARCHAR	60				存放地点
	Buy_Date	INT			N		购建日期
	Start_Date	INT			N		开始使用日期
	Use_Limit	int					使用年限
	Unit	varchar	8				计量单位
	Quantity	int					数量
	Card_Status	VARCHAR	10		N	212101	卡片状态(2121)
	Card_Status_Name	VARCHAR	30		N	正常	卡片状态说明
	Depr_Flag	BIT			N	1	折旧计提标志 0不计提 1计提
	Depr_Type	VARCHAR	10		N		折旧类别(1913)
	Depr_Method	VARCHAR	30		N		折旧类别说明
	StartDepr_Month	INT					开始计提月份
	LastDepr_Month	INT					最近计提月份
	Original_Value	DECIMAL	16	3	N		原值
	Scrap _Rate	DECIMAL	5	4	N		净残值率
	Scrap_value	DECIMAL	16	3	N		净残值
	Total_Depr	DECIMAL	16	3	N		累计折旧
	Devalue_Prep	DECIMAL	16	3	N		减值准备
	Net_Value	DECIMAL	16	3	N		账面净值
	Net_Quantum	DECIMAL	16	3	N		账面净额
	mDepr_Quantum	DECIMAL	16	3	N		月折旧额
	mDepr_Rate	DECIMAL	5	4	N		月折旧率
	Input_Man	INT			N		录入员（增加录入）
	Input_Time	INT			N	CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))	录入时间
	Clean_Man	INT					处置人（清理处置）
	Clean_Time	INT					处置时间
	Curr_Vary_ID	int					当前卡片变更ID
	Last_Jour_ID	int					最新流水ID
	Remark	VARCHAR	200				备注
	
	 * */

	private Integer book_code;
	private Integer card_id;
	private String asset_no;
	private String asset_name;
	private String asset_type;
	private String type_name;
	private String model_type;
	private Integer depart_id;
	private Integer custos;
	private String put_address;
	private Integer buy_date;
	private Integer start_date;
	private Integer use_limit;
	private String unit;
	private Integer quantity;
	private String card_status;
	private String card_status_name;
	private Integer depr_flag;
	private String depr_type;
	private String depr_method;
	private Integer start_depr_month;
	private Integer last_depr_month;
	private BigDecimal original_value;
	private BigDecimal scrap_rate;
	private BigDecimal scrap_value;
	private BigDecimal total_depr;
	private BigDecimal devalue_prep;
	private BigDecimal net_value;
	private BigDecimal net_quantum;
	private BigDecimal month_depr_quantum;
	private BigDecimal month_depr_rate;
	private Integer input_man;
	private Integer input_time;
	private Integer clean_man;
	private Integer clean_time;
	private Integer curr_vary_id;
	private Integer last_jour_id;
	private String remark;
	private Integer begin_date;
	private Integer end_date;
	private String vary_type;
	private String vary_type_name;	
	private Integer refer_flag;
   private Integer check_flag;

    private Integer vary_id;
    private BigDecimal income_amount;
    private BigDecimal fare_amount;
    

	private Integer current_month;
	private Integer reg_date;
	
	private String enter_subcode;
	private String enter_subname;
	private String temp_subcode;
	private String temp_subname;
	private String income_subcode;
	private String income_subname;
	private String fare_subcode;
	private String fare_subname;



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
	public String getAsset_type() {
		return asset_type;
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
	public Integer getBuy_date() {
		return buy_date;
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
	public String getCard_status() {
		return card_status;
	}

	/**
	 * @return
	 */
	public String getCard_status_name() {
		return card_status_name;
	}

	/**
	 * @return
	 */
	public Integer getClean_man() {
		return clean_man;
	}

	/**
	 * @return
	 */
	public Integer getClean_time() {
		return clean_time;
	}

	/**
	 * @return
	 */
	public Integer getCurr_vary_id() {
		return curr_vary_id;
	}

	/**
	 * @return
	 */
	public Integer getCustos() {
		return custos;
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
	public Integer getDepr_flag() {
		return depr_flag;
	}

	/**
	 * @return
	 */
	public String getDepr_method() {
		return depr_method;
	}

	/**
	 * @return
	 */
	public String getDepr_type() {
		return depr_type;
	}

	/**
	 * @return
	 */
	public BigDecimal getDevalue_prep() {
		return devalue_prep;
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
	public Integer getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public Integer getLast_depr_month() {
		return last_depr_month;
	}

	/**
	 * @return
	 */
	public Integer getLast_jour_id() {
		return last_jour_id;
	}

	/**
	 * @return
	 */
	public String getModel_type() {
		return model_type;
	}

	/**
	 * @return
	 */
	public BigDecimal getMonth_depr_quantum() {
		return month_depr_quantum;
	}

	/**
	 * @return
	 */
	public BigDecimal getMonth_depr_rate() {
		return month_depr_rate;
	}

	/**
	 * @return
	 */
	public BigDecimal getNet_quantum() {
		return net_quantum;
	}

	/**
	 * @return
	 */
	public BigDecimal getNet_value() {
		return net_value;
	}

	/**
	 * @return
	 */
	public BigDecimal getOriginal_value() {
		return original_value;
	}

	/**
	 * @return
	 */
	public String getPut_address() {
		return put_address;
	}

	/**
	 * @return
	 */
	public Integer getQuantity() {
		return quantity;
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
	public BigDecimal getScrap_rate() {
		return scrap_rate;
	}

	/**
	 * @return
	 */
	public BigDecimal getScrap_value() {
		return scrap_value;
	}

	/**
	 * @return
	 */
	public Integer getStart_date() {
		return start_date;
	}

	/**
	 * @return
	 */
	public Integer getStart_depr_month() {
		return start_depr_month;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotal_depr() {
		return total_depr;
	}

	/**
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @return
	 */
	public Integer getUse_limit() {
		return use_limit;
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
	 * @param string
	 */
	public void setAsset_type(String string) {
		asset_type = string;
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
	public void setBuy_date(Integer integer) {
		buy_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setCard_id(Integer integer) {
		card_id = integer;
	}

	/**
	 * @param string
	 */
	public void setCard_status(String string) {
		card_status = string;
	}

	/**
	 * @param string
	 */
	public void setCard_status_name(String string) {
		card_status_name = string;
	}

	/**
	 * @param integer
	 */
	public void setClean_man(Integer integer) {
		clean_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setClean_time(Integer integer) {
		clean_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setCurr_vary_id(Integer integer) {
		curr_vary_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setCustos(Integer integer) {
		custos = integer;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setDepr_flag(Integer integer) {
		depr_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setDepr_method(String string) {
		depr_method = string;
	}

	/**
	 * @param string
	 */
	public void setDepr_type(String string) {
		depr_type = string;
	}

	/**
	 * @param decimal
	 */
	public void setDevalue_prep(BigDecimal decimal) {
		devalue_prep = decimal;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param integer
	 */
	public void setInput_time(Integer integer) {
		input_time = integer;
	}

	/**
	 * @param integer
	 */
	public void setLast_depr_month(Integer integer) {
		last_depr_month = integer;
	}

	/**
	 * @param integer
	 */
	public void setLast_jour_id(Integer integer) {
		last_jour_id = integer;
	}

	/**
	 * @param string
	 */
	public void setModel_type(String string) {
		model_type = string;
	}

	/**
	 * @param decimal
	 */
	public void setMonth_depr_quantum(BigDecimal decimal) {
		month_depr_quantum = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setMonth_depr_rate(BigDecimal decimal) {
		month_depr_rate = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setNet_quantum(BigDecimal decimal) {
		net_quantum = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setNet_value(BigDecimal decimal) {
		net_value = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setOriginal_value(BigDecimal decimal) {
		original_value = decimal;
	}

	/**
	 * @param string
	 */
	public void setPut_address(String string) {
		put_address = string;
	}

	/**
	 * @param integer
	 */
	public void setQuantity(Integer integer) {
		quantity = integer;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string) {
		remark = string;
	}

	/**
	 * @param decimal
	 */
	public void setScrap_rate(BigDecimal decimal) {
		scrap_rate = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setScrap_value(BigDecimal decimal) {
		scrap_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setStart_date(Integer integer) {
		start_date = integer;
	}

	/**
	 * @param integer
	 */
	public void setStart_depr_month(Integer integer) {
		start_depr_month = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTotal_depr(BigDecimal decimal) {
		total_depr = decimal;
	}

	/**
	 * @param string
	 */
	public void setUnit(String string) {
		unit = string;
	}

	/**
	 * @param integer
	 */
	public void setUse_limit(Integer integer) {
		use_limit = integer;
	}

	/**
	 * @return
	 */
	public Integer getEnd_date() {
		return end_date;
	}

	/**
	 * @param integer
	 */
	public void setEnd_date(Integer integer) {
		end_date = integer;
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
	public Integer getRefer_flag() {
		return refer_flag;
	}

	/**
	 * @return
	 */
	public String getVary_type() {
		return vary_type;
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
	public void setRefer_flag(Integer integer) {
		refer_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setVary_type(String string) {
		vary_type = string;
	}

	/**
	 * @return
	 */
	public Integer s() {
		return begin_date;
	}

	/**
	 * @return
	 */
	public Integer getVary_id() {
		return vary_id;
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
	public void setVary_id(Integer integer) {
		vary_id = integer;
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
	public BigDecimal getFare_amount() {
		return fare_amount;
	}

	/**
	 * @return
	 */
	public BigDecimal getIncome_amount() {
		return income_amount;
	}

	/**
	 * @param decimal
	 */
	public void setFare_amount(BigDecimal decimal) {
		fare_amount = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setIncome_amount(BigDecimal decimal) {
		income_amount = decimal;
	}


	/**
	 * @return
	 */
	public Integer getCurrent_month() {
		return current_month;
	}

	/**
	 * @param integer
	 */
	public void setCurrent_month(Integer integer) {
		current_month = integer;
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
	public void setVary_type_name(String string) {
		vary_type_name = string;
	}

	/**
	 * @return
	 */
	public String getType_name() {
		return type_name;
	}

	/**
	 * @param string
	 */
	public void setType_name(String string) {
		type_name = string;
	}

	/**
	 * @return
	 */
	public Integer getReg_date() {
		return reg_date;
	}

	/**
	 * @param integer
	 */
	public void setReg_date(Integer integer) {
		reg_date = integer;
	}

	/**
	 * @return
	 */
	public String getEnter_subcode() {
		return enter_subcode;
	}

	/**
	 * @return
	 */
	public String getEnter_subname() {
		return enter_subname;
	}

	/**
	 * @return
	 */
	public String getFare_subcode() {
		return fare_subcode;
	}

	/**
	 * @return
	 */
	public String getFare_subname() {
		return fare_subname;
	}

	/**
	 * @return
	 */
	public String getIncome_subcode() {
		return income_subcode;
	}

	/**
	 * @return
	 */
	public String getIncome_subname() {
		return income_subname;
	}

	/**
	 * @return
	 */
	public String getTemp_subcode() {
		return temp_subcode;
	}

	/**
	 * @return
	 */
	public String getTemp_subname() {
		return temp_subname;
	}

	/**
	 * @param string
	 */
	public void setEnter_subcode(String string) {
		enter_subcode = string;
	}

	/**
	 * @param string
	 */
	public void setEnter_subname(String string) {
		enter_subname = string;
	}

	/**
	 * @param string
	 */
	public void setFare_subcode(String string) {
		fare_subcode = string;
	}

	/**
	 * @param string
	 */
	public void setFare_subname(String string) {
		fare_subname = string;
	}

	/**
	 * @param string
	 */
	public void setIncome_subcode(String string) {
		income_subcode = string;
	}

	/**
	 * @param string
	 */
	public void setIncome_subname(String string) {
		income_subname = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_subcode(String string) {
		temp_subcode = string;
	}

	/**
	 * @param string
	 */
	public void setTemp_subname(String string) {
		temp_subname = string;
	}

}
