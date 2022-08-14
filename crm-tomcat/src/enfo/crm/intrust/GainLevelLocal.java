package enfo.crm.intrust;

import java.math.BigDecimal;

import enfo.crm.dao.IBusiFullLocal;

public interface GainLevelLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �������漶�� SP_ADD_TGAINLEVEL
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
	 * �޸����漶����Ϣ SP_MODI_TGAINLEVEL
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
	 * ɾ�����漶����Ϣ SP_DEL_TGAINLEVEL
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
	 * ��ѯ���漶����Ϣ SP_QUERY_TGAINLEVEL
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
	 * ��ѯ�����õ����ȼ� SP_QUERY_TGAINLEVELFLAG
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @param param
	 * @throws Exception
	 */
	void queryLevelFlag() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param flag 1 ��ʾΪ���漶��������  2 ��ʾΪ���漶��������
	 * @throws Exception
	 */
	void gainLevelCal(Integer flag) throws Exception;

	/**
	 *  @ejb.interface-method view-type = "local"
	 * IN(�������)	IN_BOOK_CODE	INTEGER	����
	IN_PRODUCT_ID	INTEGER	��ƷID
	IN_SUB_PRODUCT_ID	INTEGER	�Ӳ�ƷID
	IN_SY_TYPE	NVARCHAR(10)	�������
	IN_CHECK_FLAG	INTEGER	��˱�־
	IN_SY_DATE	INTEGER	��������
	IN_INPUT_MAN	INTEGER	����Ա
	OUT_BALANCE_4104	DECIMAL(16,2)	δ��������
	
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
	 * �����漶�����_��� SP_CHECK_TGAINDETAIL_LEVEL
	 * 
	 * @IN_PRODUCT_ID
	 * @IN_SUB_PRODUCT_ID
	 * @IN_SY_DATE INTEGER	�����������
	 * @IN_SY_TYPE NVARCHAR(10)	�������1116
	 * @IN_INPUT_MAN
	 * @IN_CHECK_FLAG	INTEGER	1ͨ����2��ͨ��
	 * @param param
	 * @throws Exception
	 */
	void gainLevelCheck() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * �м�����ʱ�����ϸ��ѯ SP_QUERY_TGAINDETAILLIST
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
	 * ���ؽ����
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
	 * ��ѯ�����õ��������ȼ� SP_QUERY_TGAINLEVELPROV
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
	 * @return ���� input_man��
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param input_man Ҫ���õ� input_man��
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� lower_limit��
	 */
	BigDecimal getLower_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param lower_limit Ҫ���õ� lower_limit��
	 */
	void setLower_limit(BigDecimal lower_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� product_id��
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param product_id Ҫ���õ� product_id��
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_flag��
	 */
	Integer getProv_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag Ҫ���õ� prov_flag��
	 */
	void setProv_flag(Integer prov_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level��
	 */
	String getProv_level();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level Ҫ���õ� prov_level��
	 */
	void setProv_level(String prov_level);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_level_name��
	 */
	String getProv_level_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_level Ҫ���õ� prov_level_name��
	 */
	void setProv_level_name(String prov_level_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� serial_no��
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sub_product_id��
	 */
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sub_product_id Ҫ���õ� sub_product_id��
	 */
	void setSub_product_id(Integer sub_product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� summary��
	 */
	String getSummary();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param summary Ҫ���õ� summary��
	 */
	void setSummary(String summary);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� upper_limit��
	 */
	BigDecimal getUpper_limit();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param upper_limit Ҫ���õ� upper_limit��
	 */
	void setUpper_limit(BigDecimal upper_limit);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� prov_flag_name��
	 */
	String getProv_flag_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param prov_flag_name Ҫ���õ� prov_flag_name��
	 */
	void setProv_flag_name(String prov_flag_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cal_type��
	 */
	Integer getCal_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cal_type Ҫ���õ� cal_type��
	 */
	void setCal_type(Integer cal_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� daysayear��
	 */
	Integer getDaysayear();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param daysayear Ҫ���õ� daysayear��
	 */
	void setDaysayear(Integer daysayear);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_date��
	 */
	Integer getSy_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_date Ҫ���õ� sy_date��
	 */
	void setSy_date(Integer sy_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_money��
	 */
	BigDecimal getSy_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_money Ҫ���õ� sy_money��
	 */
	void setSy_money(BigDecimal sy_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� book_code��
	 */
	Integer getBook_code();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param book_code Ҫ���õ� book_code��
	 */
	void setBook_code(Integer book_code);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� check_flag��
	 */
	Integer getCheck_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param check_flag Ҫ���õ� check_flag��
	 */
	void setCheck_flag(Integer check_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_type��
	 */
	String getSy_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_type Ҫ���õ� sy_type��
	 */
	void setSy_type(String sy_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� bond_tax��
	 */
	BigDecimal getBond_tax();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param bond_tax Ҫ���õ� bond_tax��
	 */
	void setBond_tax(BigDecimal bond_tax);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� contract_bh��
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_bh Ҫ���õ� contract_bh��
	 */
	void setContract_bh(String contract_bh);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_name��
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� last_gain_date��
	 */
	Integer getLast_gain_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param last_gain_date Ҫ���õ� last_gain_date��
	 */
	void setLast_gain_date(Integer last_gain_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_amount��
	 */
	BigDecimal getSy_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_amount Ҫ���õ� sy_amount��
	 */
	void setSy_amount(BigDecimal sy_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� days��
	 */
	Integer getDays();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param days Ҫ���õ� days��
	 */
	void setDays(Integer days);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� end_date��
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param end_date Ҫ���õ� end_date��
	 */
	void setEnd_date(Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� start_date��
	 */
	Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param start_date Ҫ���õ� start_date��
	 */
	void setStart_date(Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_type_name��
	 */
	String getSy_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_type_name Ҫ���õ� sy_type_name��
	 */
	void setSy_type_name(String sy_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� asfund_flag��
	 */
	Integer getAsfund_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param asfund_flag Ҫ���õ� asfund_flag��
	 */
	void setAsfund_flag(Integer asfund_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� gain_flag��
	 */
	Integer getGain_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param gain_flag Ҫ���õ� gain_flag��
	 */
	void setGain_flag(Integer gain_flag);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� cust_type��
	 */
	Integer getCust_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param cust_type Ҫ���õ� cust_type��
	 */
	void setCust_type(Integer cust_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_rate��
	 */
	BigDecimal getSy_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_rate Ҫ���õ� sy_rate��
	 */
	void setSy_rate(BigDecimal sy_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� sy_start_date��
	 */
	Integer getSy_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param sy_start_date Ҫ���õ� sy_start_date��
	 */
	void setSy_start_date(Integer sy_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� float_gain��
	 */
	BigDecimal getFloat_gain();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param float_gain Ҫ���õ� float_gain��
	 */
	void setFloat_gain(BigDecimal float_gain);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� security_money��
	 */
	BigDecimal getSecurity_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param security_money Ҫ���õ� security_money��
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
	 * @return ���� exp_rate��
	 */
	BigDecimal getExp_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param exp_rate Ҫ���õ� exp_rate��
	 */
	void setExp_rate(BigDecimal exp_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� open_date��
	 */
	Integer getOpen_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param open_date Ҫ���õ� open_date��
	 */
	void setOpen_date(Integer open_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� redeem_end_date��
	 */
	Integer getRedeem_end_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param redeem_end_date Ҫ���õ� redeem_end_date��
	 */
	void setRedeem_end_date(Integer redeem_end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return ���� redeem_start_date��
	 */
	Integer getRedeem_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param redeem_start_date Ҫ���õ� redeem_start_date��
	 */
	void setRedeem_start_date(Integer redeem_start_date);
	
	public int getRows() throws Exception;

}