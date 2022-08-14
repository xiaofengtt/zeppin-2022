package enfo.crm.intrust;

import java.math.BigDecimal;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;

public interface PurconfigLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询申购、认购产品费率信息
	 * @throws Exception
	 */
	void listPurConfig() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询申购、认购产品费率计算类别信息
	 * @throws Exception
	 */
	void listPurConfigType() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 查询申购、认购费率详细信息
	 * @throws Exception
	 */
	void load() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 添加申购、认购费率详细信息
	 * @throws Exception
	 */
	void append() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 计算费率类别设置
	 * @throws Exception
	 */
	void updateFreeType() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 修改申购、认购费率详细信息
	 * @throws Exception
	 */
	void updatePurConfig() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 删除费率
	 * @throws Exception
	 */
	void delPurConfig() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 申购、认购费率详细信息
	 * @throws Exception
	 */
	boolean getNext() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 费率类别
	 * @throws Exception
	 */
	boolean getNextFeeType() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * SP_INNER_CAL_PRODUCTFEE
	 * @IN_PRODUCT_ID INT,
	 * @IN_CUST_ID INT,
	 * @IN_FEE_TYPE INT,
	 * @IN_TO_MONEY DECIMAL(16,2),
	 * @IN_SY_DATE INT,
	 * @OUT_FEE_MONEY DECIMAL(16,2) OUTPUT,
	 * @OUT_GS_RATE DECIMAL(5,4) = 0.00 OUTPUT
	 * @throws BusiException
	 */
	void queryPurconfig() throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 between_month。
	 */
	Integer getBetween_month();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            between_month。
	 */
	void setBetween_month(Integer between_month);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 cust_id。
	 */
	Integer getCust_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_id。
	 */
	void setCust_id(Integer cust_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 description。
	 */
	String getDescription();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            description。
	 */
	void setDescription(String description);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 end_date。
	 */
	Integer getEnd_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            end_date。
	 */
	void setEnd_date(Integer end_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee_amount。
	 */
	BigDecimal getFee_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_amount。
	 */
	void setFee_amount(BigDecimal fee_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee_money。
	 */
	Integer getFee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_money。
	 */
	void setFee_money(Integer fee_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee_rate。
	 */
	BigDecimal getFee_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_rate。
	 */
	void setFee_rate(BigDecimal fee_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 fee_type。
	 */
	Integer getFee_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_type。
	 */
	void setFee_type(Integer fee_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 gs_rate。
	 */
	BigDecimal getGs_rate();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            gs_rate。
	 */
	void setGs_rate(BigDecimal gs_rate);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 input_man。
	 */
	Integer getInput_man();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            input_man。
	 */
	void setInput_man(Integer input_man);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 product_id。
	 */
	Integer getProduct_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            product_id。
	 */
	void setProduct_id(Integer product_id);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 purchanse_fee_type。
	 */
	Integer getPurchanse_fee_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            purchanse_fee_type。
	 */
	void setPurchanse_fee_type(Integer purchanse_fee_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_end_money。
	 */
	Integer getRg_end_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_end_money。
	 */
	void setRg_end_money(Integer rg_end_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_money。
	 */
	BigDecimal getRg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_money。
	 */
	void setRg_money(BigDecimal rg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 rg_start_money。
	 */
	Integer getRg_start_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            rg_start_money。
	 */
	void setRg_start_money(Integer rg_start_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 serial_no。
	 */
	Integer getSerial_no();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            serial_no。
	 */
	void setSerial_no(Integer serial_no);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 start_date。
	 */
	Integer getStart_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            start_date。
	 */
	void setStart_date(Integer start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 sy_date。
	 */
	Integer getSy_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            sy_date。
	 */
	void setSy_date(Integer sy_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 to_money。
	 */
	BigDecimal getTo_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            to_money。
	 */
	void setTo_money(BigDecimal to_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jsfs。
	 */
	String getJsfs();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 value。
	 */
	BigDecimal getValue();

}