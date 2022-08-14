package enfo.crm.intrust;

import java.math.BigDecimal;

import enfo.crm.dao.IBusiFullLocal;

public interface GainLevelLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 新增收益级别 SP_ADD_TGAINLEVEL
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @IN_PROV_FLAG
	 * @IN_PROV_LEVEL NVARCHAR(10) = NULL,
	 * @IN_PROV_LEVEL_NAME NVARCHAR(20) = NULL,
	 * @IN_LOWER_LIMIT
	 * @IN_UPPER_LIMIT
	 * @IN_SUMMARY NVARCHAR(200) = NULL
	 * @IN_INPUT_MAN
	 * @param param
	 * @throws Exception
	 */
	void add() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改收益级别信息 SP_MODI_TGAINLEVEL
	 * 
	 * @IN_SERIAL_NO
	 * @IN_PROV_FLAG
	 * @IN_PROV_LEVEL
	 * @IN_PROV_LEVEL_NAME
	 * @IN_LOWER_LIMIT
	 * @IN_UPPER_LIMIT
	 * @IN_SUMMARY
	 * @IN_INPUT_MAN
	 * @param param
	 * @throws Exception
	 */
	void update() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除收益级别信息 SP_DEL_TGAINLEVEL
	 * 
	 * @IN_SERIAL_NO
	 * @IN_INPUT_MAN
	 * @param param
	 * @throws Exception
	 */
	void delete() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询收益级别信息 SP_QUERY_TGAINLEVEL
	 * 
	 * @IN_SERIAL_NO
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @IN_PROV_LEVEL
	 * @param param
	 * @throws Exception
	 */
	void query() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询已设置的优先级 SP_QUERY_TGAINLEVELFLAG
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @param param
	 * @throws Exception
	 */
	void queryLevelFlag() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag 1 表示为收益级别分配计算  2 表示为收益级别结算计算
	 * @throws Exception
	 */
	void gainLevelCal(Integer flag) throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * IN(输入参数)	IN_BOOK_CODE	INTEGER	账套
	IN_PRODUCT_ID	INTEGER	产品ID
	IN_SUB_PRODUCT_ID	INTEGER	子产品ID
	IN_SY_TYPE	NVARCHAR(10)	收益类别
	IN_CHECK_FLAG	INTEGER	审核标志
	IN_SY_DATE	INTEGER	收益日期
	IN_INPUT_MAN	INTEGER	操作员
	OUT_BALANCE_4104	DECIMAL(16,2)	未分配利润
	
	 * @throws Exception
	 */

	BigDecimal[] queryGainLevelCal() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextGainLevelCal() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 按收益级别分配_审核 SP_CHECK_TGAINDETAIL_LEVEL
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @IN_SY_DATE INTEGER	收益分配日期
	 * @IN_SY_TYPE NVARCHAR(10)	收益类别1116
	 * @IN_INPUT_MAN
	 * @IN_CHECK_FLAG	INTEGER	1通过，2不通过
	 * @param param
	 * @throws Exception
	 */
	void gainLevelCheck() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 中间收益时间段明细查询 SP_QUERY_TGAINDETAILLIST
	 * 
	 * @SERIAL_NO
	 * @param param
	 * @throws Exception
	 */
	void queryGainDetail() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextGainDetailList() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 返回结果集
	 */
	boolean getNextLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProvFlag() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询已设置的收益优先级 SP_QUERY_TGAINLEVELPROV
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @IN_PROV_FLAG 
	 * @param param
	 * @throws Exception
	 */
	void queryProvLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws Exception
	 */
	boolean getNextProvLevel() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_man。
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man 要设置的 input_man。
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 lower_limit。
	 */
	BigDecimal getLower_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param lower_limit 要设置的 lower_limit。
	 */
	void setLower_limit(BigDecimal lower_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id 要设置的 product_id。
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_flag。
	 */
	Integer getProv_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag 要设置的 prov_flag。
	 */
	void setProv_flag(Integer prov_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level。
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level 要设置的 prov_level。
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_level_name。
	 */
	String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level 要设置的 prov_level_name。
	 */
	void setProv_level_name(String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 serial_no。
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no 要设置的 serial_no。
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sub_product_id。
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_id 要设置的 sub_product_id。
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 summary。
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary 要设置的 summary。
	 */
	void setSummary(String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 upper_limit。
	 */
	BigDecimal getUpper_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param upper_limit 要设置的 upper_limit。
	 */
	void setUpper_limit(BigDecimal upper_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 prov_flag_name。
	 */
	String getProv_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag_name 要设置的 prov_flag_name。
	 */
	void setProv_flag_name(String prov_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cal_type。
	 */
	Integer getCal_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cal_type 要设置的 cal_type。
	 */
	void setCal_type(Integer cal_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 daysayear。
	 */
	Integer getDaysayear();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param daysayear 要设置的 daysayear。
	 */
	void setDaysayear(Integer daysayear);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_date。
	 */
	Integer getSy_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_date 要设置的 sy_date。
	 */
	void setSy_date(Integer sy_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_money。
	 */
	BigDecimal getSy_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_money 要设置的 sy_money。
	 */
	void setSy_money(BigDecimal sy_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 book_code。
	 */
	Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code 要设置的 book_code。
	 */
	void setBook_code(Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 check_flag。
	 */
	Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_flag 要设置的 check_flag。
	 */
	void setCheck_flag(Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_type。
	 */
	String getSy_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_type 要设置的 sy_type。
	 */
	void setSy_type(String sy_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 bond_tax。
	 */
	BigDecimal getBond_tax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bond_tax 要设置的 bond_tax。
	 */
	void setBond_tax(BigDecimal bond_tax);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_bh。
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_bh 要设置的 contract_bh。
	 */
	void setContract_bh(String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_name。
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name 要设置的 cust_name。
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 last_gain_date。
	 */
	Integer getLast_gain_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_gain_date 要设置的 last_gain_date。
	 */
	void setLast_gain_date(Integer last_gain_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_amount。
	 */
	BigDecimal getSy_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_amount 要设置的 sy_amount。
	 */
	void setSy_amount(BigDecimal sy_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 days。
	 */
	Integer getDays();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param days 要设置的 days。
	 */
	void setDays(Integer days);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date。
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date 要设置的 end_date。
	 */
	void setEnd_date(Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date。
	 */
	Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date 要设置的 start_date。
	 */
	void setStart_date(Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_type_name。
	 */
	String getSy_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_type_name 要设置的 sy_type_name。
	 */
	void setSy_type_name(String sy_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 asfund_flag。
	 */
	Integer getAsfund_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param asfund_flag 要设置的 asfund_flag。
	 */
	void setAsfund_flag(Integer asfund_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gain_flag。
	 */
	Integer getGain_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gain_flag 要设置的 gain_flag。
	 */
	void setGain_flag(Integer gain_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_type。
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type 要设置的 cust_type。
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_rate。
	 */
	BigDecimal getSy_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_rate 要设置的 sy_rate。
	 */
	void setSy_rate(BigDecimal sy_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_start_date。
	 */
	Integer getSy_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_start_date 要设置的 sy_start_date。
	 */
	void setSy_start_date(Integer sy_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 float_gain。
	 */
	BigDecimal getFloat_gain();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param float_gain 要设置的 float_gain。
	 */
	void setFloat_gain(BigDecimal float_gain);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 security_money。
	 */
	BigDecimal getSecurity_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param security_money 要设置的 security_money。
	 */
	void setSecurity_money(BigDecimal security_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getProduct_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param productCode
	 */
	void setProduct_code(String productCode);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getProduct_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param productName
	 */
	void setProduct_name(String productName);

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @return
	 */
	BigDecimal getGain_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gain_rate
	 */
	void setGain_rate(BigDecimal gain_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return
	 */
	String getSub_product_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param subProductName
	 */
	void setSub_product_name(String subProductName);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 exp_rate。
	 */
	BigDecimal getExp_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate 要设置的 exp_rate。
	 */
	void setExp_rate(BigDecimal exp_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 open_date。
	 */
	Integer getOpen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_date 要设置的 open_date。
	 */
	void setOpen_date(Integer open_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 redeem_end_date。
	 */
	Integer getRedeem_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param redeem_end_date 要设置的 redeem_end_date。
	 */
	void setRedeem_end_date(Integer redeem_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 redeem_start_date。
	 */
	Integer getRedeem_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param redeem_start_date 要设置的 redeem_start_date。
	 */
	void setRedeem_start_date(Integer redeem_start_date);
	
	public int getRows() throws Exception;

}