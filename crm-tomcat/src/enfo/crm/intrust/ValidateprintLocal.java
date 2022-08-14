package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ValidateprintLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 确认单打印----（分页）查询
	 * 
	 * SP_QUERY_TBENAMOUNTDETAIL_CRM IN_SERIAL_NO INT 序号 IN_PRODUCT_ID INT 产品ID
	 * IN_CONTRACT_BH VARCHAR(16) 合同编号 IN_CUST_NAME VARCHAR(100) 客户名称
	 * IN_CHG_TYPE INT 份额变动业务类别 1认购 2申购 3赎回 4份额转增 IN_START_DATE INT 起始日期
	 * IN_END_DATE INT 终止日期 IN_INPUT_MAN INT 操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 方正确认单打印----（分页）查询
	 * 
	 * SP_QUERY_TMONEYDETAIL_4CUST
	 *   @IN_PRODUCT_ID           INT,                            --产品ID
	 * 	 @IN_SUB_PRODUCT_ID       INT ,                           --子产品ID   20111112  LUOHH
	 * 	 @IN_PRODUCT_NAME         NVARCHAR(100) ,                 --产品名称
	 *   @IN_CUST_ID              INTEGER ,                       --客户ID   
	 *   @IN_CUST_NAME            NVARCHAR(100) ,                 --客户名称
	 *   @IN_CONTRACT_SUB_BH      NVARCHAR(50) ,                  --实际合同编号
	 *   @IN_DZ_DATE              INTEGER         = NULL,         --缴款时间区间上限
	 *   @IN_END_DATE             INTEGER         = 0,            --缴款时间区间下限
	 *   @IN_CHECK_FLAG           INTEGER,                        --审核标志
	 *   @IN_INPUT_MAN            INTEGER                         --操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAllFz(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 信托确认单打印----（分页）查询
	 * 
	 * SP_QUERY_HCUSTZJBD IN_SERIAL_NO INT 序号 IN_PRODUCT_ID INT 产品ID
	 * IN_CONTRACT_SUB_BH VARCHAR(16) 合同编号 IN_CUST_NAME VARCHAR(100) 客户名称
	 * IN_CHG_TYPE INT 份额变动业务类别 1认购 2申购 IN_START_DATE INT 起始日期
	 * IN_END_DATE INT 终止日期 IN_INPUT_MAN INT 操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll1(int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 确认单打印----（单一）查询
	 * 
	 * SP_QUERY_TBENAMOUNTDETAIL_CRM IN_SERIAL_NO INT 序号 IN_PRODUCT_ID INT 产品ID
	 * IN_CONTRACT_BH VARCHAR(16) 合同编号 IN_CUST_NAME VARCHAR(100) 客户名称
	 * IN_CHG_TYPE INT 份额变动业务类别 1认购 2申购 3赎回 4份额转增 IN_START_DATE INT 起始日期
	 * IN_END_DATE INT 终止日期 IN_INPUT_MAN INT 操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySql() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 确认单打印----（单一）查询
	 * 
	 * SP_QUERY_HCUSTZJBD IN_SERIAL_NO INT 序号 IN_PRODUCT_ID INT 产品ID
	 * IN_CONTRACT_SUB_BH VARCHAR(16) 合同编号 IN_CUST_NAME VARCHAR(100) 客户名称
	 * IN_CHG_TYPE INT 份额变动业务类别 1认购 2申购 IN_START_DATE INT 起始日期
	 * IN_END_DATE INT 终止日期 IN_INPUT_MAN INT 操作员
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List listBySql1() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 份额变动记录---增加
	 * 
	 * SP_ADD_TBENAMOUNTDETAIL IN_PRODUCT_ID INT 产品ID IN_CONTRACT_BH VARCHAR(16)
	 * 合同编号 IN_CUST_ID VARCHAR(100) 客户ID IN_CHG_TYPE INT 份额变动业务类别 1认购 2申购 3赎回
	 * 4份额转增 IN_APPL_AMOUNT DECIMAL(16,3) 申请金额 IN_FEE_MONEY DECIMAL(16,3) 费用
	 * IN_PRICE DECIMAL(16,3) 价格（净值） IN_CHG_MONEY DECIMAL(16,3) 变动金额
	 * IN_CHG_AMOUNT DECIMAL(16,3) 变动份额 IN_AFTER_MONEY DECIMAL(16,3) 变动后金额
	 * IN_AFTER_AMOUNT DECIMAL(16,3) 变动后份额 IN_SQ_DATE INT 申请日期（发生日期） IN_DZ_DATE
	 * INT 资金到帐日期 IN_HK_DATE INT 资金划款日期 IN_JK_TYPE VARCHAR(10) 缴款方式（1114）
	 * IN_INPUT_MAN INT 操作员
	 * 
	 * @throws BusiException
	 */
	void append() throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 确认单修改打印标志
	 * @param print_flag
	 * @param serial_no
	 * @throws BusiException
	 */
	void updatePrintFlag(Integer print_flag, Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 确认单修改打印标志
	 * @param print_flag
	 * @param serial_no
	 * @throws BusiException
	 */
	void updatePrintFlag1(Integer print_flag, Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 after_amount。
	 */
	BigDecimal getAfter_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            after_amount。
	 */
	void setAfter_amount(BigDecimal after_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 after_money。
	 */
	BigDecimal getAfter_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            after_money。
	 */
	void setAfter_money(BigDecimal after_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 appl_amout。
	 */
	BigDecimal getAppl_amout();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            appl_amout。
	 */
	void setAppl_amout(BigDecimal appl_amout);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 ben_start_date。
	 */
	Integer getBen_start_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            ben_start_date。
	 */
	void setBen_start_date(Integer ben_start_date);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 chg_amount。
	 */
	BigDecimal getChg_amount();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            chg_amount。
	 */
	void setChg_amount(BigDecimal chg_amount);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 chg_money。
	 */
	BigDecimal getChg_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            chg_money。
	 */
	void setChg_money(BigDecimal chg_money);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 chg_type。
	 */
	Integer getChg_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            chg_type。
	 */
	void setChg_type(Integer chg_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 chg_type_name。
	 */
	String getChg_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            chg_type_name。
	 */
	void setChg_type_name(String chg_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_bh。
	 */
	String getContract_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            contract_bh。
	 */
	void setContract_bh(String contract_bh);

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
	 * @return 返回 cust_name。
	 */
	String getCust_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            cust_name。
	 */
	void setCust_name(String cust_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 dz_date。
	 */
	Integer getDz_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            dz_date。
	 */
	void setDz_date(Integer dz_date);

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
	 * @return 返回 fee_money。
	 */
	BigDecimal getFee_money();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            fee_money。
	 */
	void setFee_money(BigDecimal fee_money);

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
	 * @return 返回 hk_date。
	 */
	Integer getHk_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            hk_date。
	 */
	void setHk_date(Integer hk_date);

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
	 * @return 返回 jk_type。
	 */
	String getJk_type();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_type。
	 */
	void setJk_type(String jk_type);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 jk_type_name。
	 */
	String getJk_type_name();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            jk_type_name。
	 */
	void setJk_type_name(String jk_type_name);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 price。
	 */
	BigDecimal getPrice();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            price。
	 */
	void setPrice(BigDecimal price);

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
	 * @return 返回 sq_date。
	 */
	Integer getSq_date();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param 设置
	 *            sq_date。
	 */
	void setSq_date(Integer sq_date);

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
	 * @return
	 */
	Integer getPrint_flag();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param printFlag
	 */
	void setPrint_flag(Integer printFlag);

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
	Integer getSub_product_id();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param subProductId
	 */
	void setSub_product_id(Integer subProductId);

	/**
	 * @ejb.interface-method view-type = "local"
	 * @return 返回 contract_sub_bh。
	 */
	String getContract_sub_bh();

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param contract_sub_bh 要设置的 contract_sub_bh。
	 */
	void setContract_sub_bh(String contract_sub_bh);

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

}